#!/usr/bin/python -O

"""
    Measures agreement between two randomly chosen ratings for the same question.
    Results are grouped by question type (general, specific) and condition (mturk, scholar, scholar-in, all)
"""

import random
import utils
import collections

f = open('results/rating_agreement.txt', 'w')

by_pair = {}

for ktype in 'general', 'specific':
    by_pair[ktype] = {
        'mturk' : collections.defaultdict(list),
        'scholar' : collections.defaultdict(list),
        'scholar-in' : collections.defaultdict(list),
        'all' : collections.defaultdict(list),
    }

s = utils.Survey()
for u in s.users.values():
    if u.valid():
        for r in u.rated:
            if r.field in ['general'] + utils.FIELDS and r.has_response():
                group = None
                if u.condition in ('scholar', 'mturk'):
                    group = u.condition
                elif u.condition in utils.FIELDS:
                    if r.in_group:
                        group = 'scholar-in'
                    else:
                        group = 'scholar'
                if group:
                    ktype = 'general' if r.field == 'general' else 'specific'
                    by_pair[ktype][group][r.pair_id].append(r.response)
                    by_pair[ktype]['all'][r.pair_id].append(r.response)

for ktype in by_pair.keys():
    f.write('results for %s knowledge\n' % ktype)
    for (c1, pairs1) in by_pair[ktype].items():
        for (c2, pairs2) in by_pair[ktype].items():
            if (c1 == 'all' or c2 == 'all') and c1 != c2:
                continue
            f.write('\n\tresults for conditions %s, %s\n' % ( c1, c2))
            if not pairs1 or not pairs2:
                f.write('\t\tno data\n')
                continue
    
            keys = set(pairs1.keys()).intersection(pairs2.keys())
            keys = list(keys)   # order it
    
            n = 1000000
            k = 0
            diff_counts = [0] * 5
            mae = 0.0
            for i in xrange(n):
                pair_id = random.choice(keys)
                if c1 == c2:
                    (x, y) = random.sample(pairs1[pair_id], 2)
                else:
                    x = random.choice(pairs1[pair_id])
                    y = random.choice(pairs2[pair_id])
                if x == y:
                    k += 1
                diff_counts[abs(x-y)] += 1
                mae += abs(x - y)
            f.write('\t\thit rate is %s of %s, mae=%.3f\n' % (k, n, mae/n))
            f.write('\t\tdiffs: %s\n' % ([1.0 * d / n * 100.0 for d in diff_counts]))
            f.write('\t\tmajor disagreements on %.3f%%\n' % (100.0 * sum(diff_counts[2:]) / n))
    f.write('\n')
