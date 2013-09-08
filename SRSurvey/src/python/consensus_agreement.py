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

f = open('results/consensus_agreement.txt', 'w')

s = utils.Survey()

def write(message):
    print message
    f.write(message + '\n')

consensus = {}

# Build up user data structure
#
# ktype ->
#   condition ->
#       list of
#           { pair_id -> rating }
for ktype in 'general', 'specific':
    consensus[ktype] = {}
    for condition in ('mturk', 'scholar', 'scholar-in', 'all'):
        pair_ratings = collections.defaultdict(list)

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

            for r in ratings:
                pair_ratings[r.pair_id].append(r.response)

        warn('condition %s, %s has %s pairs with %s ratings' %  (
                ktype, condition,
                len(pair_ratings),
                sum([len(r) for r in pair_ratings.values()])
        ))

        for (pair_id, ratings) in pair_ratings.items():
            pair_ratings[pair_id] = utils.mean(ratings)

        consensus[ktype][condition] = pair_ratings



for ktype in 'general', 'specific':
    write('question type: %s' % ktype)
    for (condition1, consensus1) in consensus[ktype].items():
        for (condition2, consensus2) in consensus[ktype].items():
            if not consensus1 or not consensus2:
                continue
            write('\tcondition %s, %s' % (condition1, condition2))
            
            common_ids = list(set(consensus1.keys()).intersection(consensus2.keys()))
            X = [consensus1[pid] for pid in common_ids]
            Y = [consensus2[pid] for pid in common_ids]

            write('\t\tpearson: %.3f' % (correlation.pearson_rho(X, Y)))
            write('\t\tspearman: %.3f' % (correlation.spearman_rho_tr(X, Y)))
