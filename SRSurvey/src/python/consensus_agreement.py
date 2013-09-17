#!/usr/bin/python -O

"""
    Measures agreement between two randomly chosen PEOPLE (spearman and pearson).
    Results are grouped by question type (general, specific) and condition (mturk, scholar, scholar-in, all)
"""

import random
import collections
import string

from scipy import stats

import utils
import correlation

from utils import warn

f = open('results/consensus_agreement.txt', 'w')

s = utils.Survey()

def write(message):
    print message
    f.write(message + '\n')

pair_ratings = {}

pair_min_counts = collections.defaultdict(lambda: 100000)

for ktype in utils.SPECIFICITIES:
    pair_ratings[ktype] = {}
    for condition in utils.CONDITIONS:
        cratings = s.get_ratings_by_condition(ktype, condition)

        warn('condition %s, %s has %s pairs with %s ratings' %  (
                ktype, condition,
                len(cratings),
                sum([len(r) for r in    cratings.values()])
        ))


        for (pair_id, ratings) in cratings.items():
            pair_min_counts[pair_id] = min(pair_min_counts[pair_id], len(ratings))
            cratings[pair_id] = [r.response for r in ratings]

        pair_ratings[ktype][condition] = cratings

pair_means = {}
for ktype in pair_ratings.keys():
    write('question type: %s' % ktype)
    pair_means[ktype] = {}
    for condition in pair_ratings[ktype].keys():
        pair_means[ktype][condition] = {}
        n = 10
        means = [[] for i in range(n)]
        for (pair_id, ratings) in pair_ratings[ktype][condition].items():
            total = 0
            for i in range(n):
                pm = pair_min_counts[pair_id]
                means[i].append(utils.mean(utils.sample_with_replacement(ratings, pm)))
                total += utils.mean(random.sample(ratings, pm))
            pair_means[ktype][condition][pair_id] = 1.0 * total / n

        if means:
            spearmans = []
            for X in means:
                for Y in means:
                    if len(set(X)) >= 1 and len(set(Y)) >= 1:
                        spearmans.append(correlation.spearman_rho_tr(X, Y))
            write('\t%s, %s: bootstrapped spearman: %.3f (dev=%.3f)' %
                (condition, condition, utils.mean(spearmans), utils.dev(spearmans)))

for ktype in pair_means.keys():
    write('question type: %s' % ktype)
    for (condition1, consensus1) in pair_means[ktype].items():
        for (condition2, consensus2) in pair_means[ktype].items():
            if not consensus1 or not consensus2 or condition1 == condition2 or condition1 == 'all' or condition2 == 'all':
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
                ratings1 = pair_ratings[ktype][condition1][pid]
                ratings2 = pair_ratings[ktype][condition2][pid]
                (t, p) = stats.ttest_ind(ratings1, ratings2)
                
                write('\t\t\t%s, %s: %.2f to %.2f (ttest t=%.5f, p=%.5f)' % (pair[0], pair[1], x, y, t, p))
