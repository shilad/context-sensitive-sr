import collections
import itertools
import re
import sys

FIELDS = ['history', 'psychology', 'biology']
IS_NUM = re.compile('^\d+$').match

GROUP_DESCRIPTION = {
    'biology' : 'biology researchers',
    'psychology' : 'psychology researchers',
    'history' : 'history researchers',
    'scholar' : 'researchers in a field other than biology, history, or psychology',
    'general' : 'people who are not researchers',
    'mturk' : 'subjects recruited through mechanical turk',
}

class LogRecord:
    def __init__(self, line, cleaned):
        tokens = [t.strip() for t in line.split('\t')]

        # fix up ordering in javascript log lines
        if IS_NUM(tokens[4]):
            tokens = list(tokens)
            action = tokens[6]
            del tokens[6]
            tokens.insert(4, action)
            tokens = tuple(tokens)

        self.tstamp = tokens[0] # todo: parse if necessary
        self.ip = tokens[1]
        self.user_id = tokens[2]
        self.email = tokens[3]
        self.action = tokens[4]
        self.params = list(tokens[5:])

        # clean up phrases if necessary
        if self.action == 'showRating':
            self.clean_phrase(cleaned, 5)
            self.clean_phrase(cleaned, 6)

        if self.action == 'rating':
            self.clean_phrase(cleaned, 3)
            self.clean_phrase(cleaned, 4)

    def clean_phrase(self, cleaned, i):
        if self.params[i] not in cleaned.values():
            self.params[i] = cleaned[self.params[i]]



class User:
    def __init__(self, records):
        self.user_id = records[-1].user_id
        self.email = records[-1].email
        self.gender = None
        self.scholar = None
        self.education = None
        self.interests = None
        self.condition = None
        self.group1 = None
        self.group2 = None
        self.scholar = None
        self.mturk = False
        self.shown = []
        self.rated = []   # a subset of shown
        self.records = records

        for r in records:
            if r.action == 'gender':
                self.gender = clean_gender(r.params[0])
            if r.action == 'education':
                self.education = r.params[0]
            if r.action == 'scholar':
                self.scholar = r.params[0].lower() == 'true'
            if r.action == 'interests':
                self.interests = r.params
            if r.action == 'mturk':
                self.mturk = True
            if r.action == 'groups':
                self.condition = r.params[0]
                self.group1 = r.params[1]
                self.group2 = r.params[2]

        self.build_ratings()

        # work around: groups for non-scholars not logged
        if not self.scholar:
            self.condition = 'general'
            ratedGroups = list(set([r.field for r in self.shown]).intersection(FIELDS))
            if len(ratedGroups) > 0:
                self.group1 = ratedGroups[0]
            if len(ratedGroups) > 1:
                self.group1 = ratedGroups[1]

    def has_consent(self):
        return len([r for r in self.records if r.action == 'consent']) > 0

    def valid(self):
        correct = 0
        incorrect = 0

        for r in self.rated:
            if r.field == 'validation' and r.response:
                if r.pmi > 3 and r.response >= 4:
                    correct += 1
                elif r.pmi < 3 and r.response <= 2:
                    correct += 1
                else:
                    incorrect += 1

        return incorrect == 0 and correct >= 4

    def build_ratings(self):
        # Hack: 
        # Question num links displayed ratings to actual ratings,
        # but it restarts at 0 for every "round"
        # To find an actual rating after a displayed rating,
        # look for the first actual rating with the question num
        by_question_num = collections.defaultdict(list)
        round = -1
        for r in self.records:
            if r.action == 'rating':
                question_num = r.params[2]
                by_question_num[(round, question_num)].append(r)

            if r.action == 'showRating': # hack: get most recent round from shown log
                rate = Rating(self, r, None)
                if rate.round < round:
                    raise 'round went from %s to %s' % (round, rate.round)
                round = max(round, rate.round)
                
        for r in self.records:
            if r.action == 'showRating':
                rating = Rating(self, r, None)
                key = (rating.round, rating.question_num)
                if key in by_question_num:
                    last = by_question_num[key][-1] # most recent rating
                    rating = Rating(self, r, last)
                self.shown.append(rating)
                if rating.response != None:
                    self.rated.append(rating)

    def responded(self):
        return len([r for r in self.records if r.action != 'invite']) > 0

    def num_ratings(self):
        return len(self.rated)

class Rating:
    def __init__(self, user, showRecord, rateRecord):
        self.user = user
        self.showRecord = showRecord
        self.rateRecord = rateRecord
        self.id = showRecord.params[0]
        self.round = showRecord.params[1]
        self.page = showRecord.params[2]
        self.question_num = showRecord.params[3]
        self.field = showRecord.params[4]
        self.in_group = (self.field in FIELDS and user.condition == self.field)
        self.phrase1 = showRecord.params[5]
        self.phrase2 = showRecord.params[6]
        self.pmi = float(showRecord.params[7])
        self.mean = 0.0 # mean of all OTHER ratings for this phrase pair

        if rateRecord:
            assert(showRecord.params[3] == rateRecord.params[2])
            self.knows1 = rateRecord.params[5] == 'true'
            self.knows2 = rateRecord.params[6] == 'true'
            if rateRecord.params[7] == 'undefined':
                self.response = -1
            else:
                self.response = int(rateRecord.params[7])
        else:
            self.response = None
            self.knows1 = None
            self.knows2 = None
        self.pair_id = hash(self.get_phrase_pair())

    def get_phrase_pair(self):
        return tuple(sorted([self.phrase1, self.phrase2]))

    def has_response(self):
        return self.response != None and self.response > 0


class Survey:
    def __init__(self):
        self.invites = {}   # email to 'out' or 'in'
        self.records = []   # list of Record objects in log
        self.users = {}     # user id to User
        self.cleaned = {}   # dirty phrase to cleaned phrase
        self.read_cleaned()
        self.read_log()
        self.read_invites()
        self.build_users()
        self.build_pair_means()

    def read_cleaned(self):
        self.cleaned = {}
        for line in open('dat/cleaned_phrases.txt'):
            tokens = line.split('\t')
            self.cleaned[tokens[0].strip()] = tokens[1].strip()

    def read_log(self):
        self.records = []
        for line in open('phrasepairs-log.txt'):
            self.records.append(LogRecord(line, self.cleaned))
   
    def read_invites(self):
        self.invites = {}
        for line in open('dat/pilot.txt'):
            self.invites[line.strip()] = 'out'
        for line in open('dat/main_outgroup_emails.txt'):
            self.invites[line.strip()] = 'out'
        for line in open('dat/in_emails.txt'):
            self.invites[line.strip()] = 'in'

    def build_users(self):
        keyfn = lambda r: r.user_id
        records = sorted(self.records, key=keyfn)
        no_consent = 0
        for uid, urecords in itertools.groupby(records, keyfn):
            u = User(list(urecords))
            if not u.has_consent():
                no_consent += 1
                continue

            if u.mturk:
                if u.condition == 'general':
                    u.condition = 'mturk'
                else:
                    warn('skipping turker %s in condition %s' % (u.email, u.condition))
                    continue

            self.users[uid] = u
        warn('%d users total, %d did not consent' % (no_consent + len(self.users), no_consent))
        
    def build_pair_means(self):
        sums = collections.defaultdict(float)
        counts = collections.defaultdict(int)
        for u in self.users.values():
            for r in u.rated:
                if r.has_response():
                    sums[r.pair_id] += r.response
                    counts[r.pair_id] += 1

        for u in self.users.values():
            for r in u.rated:
                s = sums[r.pair_id]
                n = counts[r.pair_id]
                if r.has_response():
                    r.mean = 1.0 * (s - r.response) / (n - 1)
                else:
                    r.mean = 1.0 * s / n
        
def clean_gender(g):
    g = g.strip().lower()
    if g == 'no_response':
        return None
    elif 'f' in g:
        return 'female'
    else:
        return 'male'

def mean(X):
    if X:
        return 1.0 * sum(X) / len(X)
    else:
        return 0.0

def median(X):
    if X:
        return sorted(X)[len(X) / 2]
    else:
        return 0

def warn(message):
    sys.stderr.write(message + '\n')
