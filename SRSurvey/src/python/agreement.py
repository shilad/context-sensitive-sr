#!/usr/bin/python -O

import random
import utils
import collections

# in_group -> pair_id -> responses

by_pair = {
    'mturk' : collections.defaultdict(list),
    'scholar' : collections.defaultdict(list),
    'in_group' : collections.defaultdict(list),
}

s = utils.Survey()
for u in s.users.values():
    if u.valid():
        for r in u.rated:
            if r.field == 'general' and r.has_response():
                group = None
                if u.condition in ('scholar', 'mturk'):
                    group = u.condition
                elif u.condition in utils.FIELDS:
                    if r.in_group:
                        group = 'in_group'
                    else:
                        group = 'scholar'
                if group:
                    by_pair[group][r.pair_id].append(r.response)

for condition, pairs in by_pair.items():
    if not pairs:
        continue
    keys = list(pairs.keys())
    n = 100000
    k = 0
    diff_counts = [0] * 5
    mae = 0.0
    for i in xrange(n):
        pair_id = random.choice(keys)
        (x, y) = tuple(random.sample(pairs[pair_id], 2))
        if x == y:
            k += 1
        diff_counts[abs(x-y)] += 1
        mae += abs(x - y)
    print 'condition', condition
    print '\thit rate is', k, 'of', n, 'mae', (mae/n)
    print '\tdiffs:', [1.0 * d / n * 100.0 for d in diff_counts]
