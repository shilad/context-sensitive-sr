#!/usr/bin/python -O

import random
import utils
import collections

# in_group -> pair_id -> responses

by_pair = {
    True : collections.defaultdict(list),
    False : collections.defaultdict(list),
}

s = utils.Survey()
for u in s.users.values():
    if u.valid():
        for r in u.rated:
            if r.field in utils.FIELDS and r.has_response():
                by_pair[r.in_group][r.pair_id].append(r.response)

for is_in in (True, False):
    keys = list(by_pair[is_in])
    n = 1000000
    k = 0
    diff_counts = [0] * 5
    mae = 0.0
    for i in xrange(n):
        pair_id = random.choice(keys)
        (x, y) = tuple(random.sample(by_pair[is_in][pair_id], 2))
        if x == y:
            k += 1
        diff_counts[abs(x-y)] += 1
        mae += abs(x - y)
    print 'hit rate is', k, 'of', n, 'mae', (mae/n)
    print 'diffs:', [1.0 * d / n * 100.0 for d in diff_counts]
