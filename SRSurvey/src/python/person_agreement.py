#!/usr/bin/python -O

"""
    Measures agreement between two randomly chosen PEOPLE (spearman and pearson).
    Results are grouped by question type (general, specific) and condition (mturk, scholar, scholar-in, all)
"""

import random
import collections

import utils
import correlation

from utils import warn

f = open('results/person_agreement.txt', 'w')

s = utils.Survey()

def write(message):
    print message
    f.write(message + '\n')

users = {}
# Build up user data structure
#
# ktype ->
#   condition ->
#       list of
#           { pair_id -> rating }
for ktype in 'general', 'specific':
    users[ktype] = {}
    for condition in ('mturk', 'scholar', 'scholar-in', 'all'):
        users[ktype][condition] = []
        for u in s.users.values():
            if not u.valid():
                continue
            elif condition in ('scholar', 'scholar-in') and not u.scholar:
                continue
            elif condition == 'mturk' and not u.mturk:
                continue

            ratings = []        # list of rating objects
            if ktype == 'general':
                ratings = [r for r in u.rated if r.field == 'general']
            else:
                ratings = [r for r in u.rated if r.field in utils.FIELDS]

            if condition == 'scholar-in':
                ratings = [r for r in ratings if r.in_group]
            elif condition == 'scholar':
                ratings = [r for r in ratings if not r.in_group]

            if ratings:
                users[ktype][condition].append(
                    dict([(r.pair_id, r.response) for r in ratings])
                )
        warn('condition %s, %s has %s uses with %s ratings' %  (
                ktype, condition,
                len(users[ktype][condition]),
                sum([len(u) for u in users[ktype][condition]]),
        ))


for ktype in 'general', 'specific':
    write('question type: %s' % ktype)
    for (condition1, users1) in users[ktype].items():
        for (condition2, users2) in users[ktype].items():
            if not users1 or not users2:
                continue
            write('\tcondition %s, %s' % (condition1, condition2))
            pearsons = []
            spearmans = []
            samples_per_user = 10
            for u1 in users1:
                for u2 in users2:
                    if id(u1) == id(u2):
                        continue
                    common_pair_ids = list(set(u1.keys()).intersection(u2.keys()))
                    if len(common_pair_ids) >= 5:
                        up = [] # user pearson samples
                        us = [] # user spearman samples
                        for i in xrange(samples_per_user):
                            sample_pair_ids = random.sample(common_pair_ids, 5)
                            X = [u1[pid] for pid in sample_pair_ids]
                            Y = [u2[pid] for pid in sample_pair_ids]
                            if len(set(X)) != 1 and len(set(Y)) != 1:
                                p = correlation.pearson_rho(X, Y)
                                s = correlation.spearman_rho_tr(X, Y)
                                up.append(p)
                                us.append(s)
                        if up and us:
                            pearsons.append(utils.mean(up))
                            spearmans.append(utils.mean(us))
            write('\t\td unique pairs of users: %d' % len(spearmans))
            write('\t\tspearman: mean=%.3f, dev=%s' % (utils.mean(spearmans), utils.dev(spearmans)))
            write('\t\tpearson: mean=%.3f, dev=%s' % (utils.mean(pearsons), utils.dev(pearsons)))
