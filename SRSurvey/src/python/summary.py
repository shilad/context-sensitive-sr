#!/usr/bin/python -O

from utils import *

def summarize_users(survey, users):
    print '\ttotal users who clicked link: %d users' % len([u for u in users if u.responded()])
    print '\ttotal ratings: %d' % sum([u.num_ratings() for u in users])
    for n in (1, 10, 25, 50, 69, 100):
        print '\tusers with >= %d ratings: %d' % (n, len([u for u in users if u.num_ratings() >= n]))

def summarize_ratings(survey, ratings):
    users = set()
    viewed = collections.defaultdict(set)   # phrase pair to set of users who viewed it
    rated = collections.defaultdict(set)    # phrase pair to set of users who rated it

    for r in ratings:
        viewed[r.get_phrase_pair()].add(r.user)
        if r.has_response():
            rated[r.get_phrase_pair()].add(r.user)
            users.add(r.user)

    vcounts = [len(viewers) for viewers in viewed.values()]
    rcounts = [len(raters) for raters in rated.values()]
    print '\t%s pairs rated by %s distinct users' % (len(rated), len(users))

    print '\tviewer distribution: min=%d median=%d mean=%.2f max=%d' % (min(vcounts), median(vcounts), mean(vcounts), max(vcounts))
    print '\trater distribution: min=%d median=%d mean=%.2f max=%d' % (min(rcounts), median(rcounts), mean(rcounts), max(rcounts))

def summary():
    survey = Survey()

    print 'total invitations:', len(survey.invites)
    print
    print

    print 'summary of all users:'
    summarize_users(survey, survey.users.values())
    print

    for field in FIELDS + ['scholar', 'general']:
        print 'summary of condition %s (%s)' % (field, GROUP_DESCRIPTION[field])
        summarize_users(survey, [u for u in survey.users.values() if u.condition == field])

    print
    print

    print 'summary of ALL ratings'
    ratings = sum([u.shown for u in survey.users.values()], [])
    summarize_ratings(survey, ratings)
    print

    for field in ['validation', 'general']:
        print 'breaking of ratings for %s:' % field
        field_ratings = [r for r in ratings if r.field == field]
        summarize_ratings(survey, field_ratings)
        print

    for field in FIELDS:
        print 'breaking of ratings for %s:' % field
        print

        print '\tsummary of ALL %s ratings' % (field)
        field_ratings = [r for r in ratings if r.field == field]
        summarize_ratings(survey, field_ratings)
        print

        print '\tsummary of %s IN group ratings' % (field)
        in_ratings = [r for r in ratings if r.field == field and r.in_group]
        summarize_ratings(survey, in_ratings)
        print

        print '\tsummary of %s OUT group ratings' % (field)
        out_ratings = [r for r in ratings if r.field == field and not r.in_group]
        summarize_ratings(survey, out_ratings)
        print

if __name__ == '__main__':
    summary()
