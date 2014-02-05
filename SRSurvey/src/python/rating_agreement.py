#!/usr/bin/python -O

"""
    Measures agreement between two randomly chosen ratings for the same question.
    Results are grouped by question type (general, specific) and condition (mturk, scholar, scholar-in, all)
"""

from multiprocessing import Pool
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
    
            k = 0
            maes = []
            num_ratings = sum([len(pairs1[k]) + len(pairs2[k]) for k in keys])
            min_ratings = sum([min(len(pairs1[k]), len(pairs2[k])) for k in keys])
            num_pairs = sum([len(pairs1[k]) * len(pairs2[k]) for k in keys])

            # a single boottrapping run
            def g(x):
                diff_counts = [0] * 5
                if x % 100 == 0:
                    print('doing %s, %s' % (ktype, x))
                mae_sum = 0.0
                for j in xrange(num_pairs):
                    pair_id = random.choice(keys)
                    if c1 == c2:
                        (x, y) = random.sample(pairs1[pair_id], 2)
                    else:
                        x = random.choice(pairs1[pair_id])
                        y = random.choice(pairs2[pair_id])
                    delta = abs(x - y)
                    mae_sum += delta
                    diff_counts[delta] += 1
                return (mae_sum / num_pairs, [100.0 * d / num_pairs for d in diff_counts])

            n = 1000
            p = Pool(8)
            maes = []
            majors = []
            diff_counts = [[] for i in range(5)]
            for (m, dc) in p.map(g, range(n)):
                maes.append(m)
                for i in range(len(dc)):
                    diff_counts[i].append(dc[i])
                majors.append(sum(dc[2:4]))

            majors.sort()
            maes.sort()
            for dcs in diff_counts:
                dcs.sort()
            p5 = int(n * 5 / 100)
            p95 = int(n * 95 / 100)

            f.write('\t\tnum pairs is %d, total ratings is %d, min ratings is %d\n' % (num_pairs, num_ratings, min_ratings))
            f.write('\t\tmae=%.3f 95%%cis=[%.3f,%3f]\n' % (utils.mean(maes), maes[p5], maes[p95]))
            for i in range(len(diff_counts)):
                dcs = diff_counts[i]
                f.write('\t\tdiffs of %d=%.3f 95%%cis=[%.3f,%3f]\n' % (i, utils.mean(dcs), dcs[p5], dcs[p95]))
            f.write('\t\tmajor_disagreements=%.3f 95%%cis=[%.3f,%3f]\n' % (utils.mean(majors), majors[p5], majors[p95]))
                
            f.flush()
    f.write('\n')
