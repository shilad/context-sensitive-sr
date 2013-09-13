#!/usr/bin/python -O

"""
    Measures agreement between two randomly chosen PEOPLE (spearman and pearson).
    Results are grouped by question type (general, specific) and condition (mturk, scholar, scholar-in, all)
"""

import random
import collections
import string

import utils
import correlation

from utils import warn

f = open('results/consensus_agreement.txt', 'w')

s = utils.Survey()

def write(message):
    print message
    f.write(message + '\n')

consensus = {}

pair_min_counts = collections.defaultdict(lambda: 100000)

for ktype in utils.SPECIFICITIES:
    consensus[ktype] = {}
    for condition in utils.CONDITIONS:
        pair_ratings = s.get_ratings_by_condition(ktype, condition)

        warn('condition %s, %s has %s pairs with %s ratings' %  (
                ktype, condition,
                len(pair_ratings),
                sum([len(r) for r in pair_ratings.values()])
        ))


        for (pair_id, ratings) in pair_ratings.items():
            pair_min_counts[pair_id] = min(pair_min_counts[pair_id], len(ratings))
            pair_ratings[pair_id] = [r.response for r in ratings]

        consensus[ktype][condition] = pair_ratings

for ktype in consensus.keys():
    for condition in consensus[ktype].keys():
        for (pair_id, ratings) in consensus[ktype][condition].items():
            n = 10
            total = 0
            for i in range(n):
                total += utils.mean(random.sample(ratings, pair_min_counts[pair_id]))
            consensus[ktype][condition][pair_id] = 1.0 * total / n

for ktype in consensus.keys():
    write('question type: %s' % ktype)
    for (condition1, consensus1) in consensus[ktype].items():
        for (condition2, consensus2) in consensus[ktype].items():
            if not consensus1 or not consensus2:
                continue
            write('\tcondition %s, %s' % (condition1, condition2))
            
            common_ids = list(set(consensus1.keys()).intersection(consensus2.keys()))
            X = [consensus1[pid] for pid in common_ids]
            Y = [consensus2[pid] for pid in common_ids]

            diffs = [(abs(x - y), x, y, pid) for (pid, x, y) in zip(common_ids, X, Y)]
            diffs.sort()
            diffs.reverse()

            write('\t\tpearson: %.3f' % (correlation.pearson_rho(X, Y)))
            write('\t\tspearman: %.3f' % (correlation.spearman_rho_tr(X, Y)))
            for (d, x, y, pid) in diffs[:10]:
                pair = s.id_to_phrases(pid)
                write('\t\t\t%s, %s: %.2f to %.2f' % (pair[0], pair[1], x, y))
