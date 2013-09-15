#!/usr/bin/python -O

import collections
import utils

s = utils.Survey()

total = collections.defaultdict(set)
num_unknown = 0
unknown = collections.defaultdict(set)
num_total = 0


for u in s.users.values():
    if u.valid():
        for r in u.rated:
            total[r.phrase1].add(r.user)
            total[r.phrase2].add(r.user)
            if r.knows1:
                unknown[r.phrase1].add(r.user)
            if r.knows2:
                unknown[r.phrase2].add(r.user)

            num_total += 1
            if r.knows1 or r.knows2:
                num_unknown += 1

items = unknown.items()
items.sort(lambda i1, i2: cmp(len(i1[1]), len(i2[1])))
items.reverse()

f = open('results/unknown.txt', 'w')

def write(msg):
    f.write(msg + '\n')
    print(msg)

write('overall percent unknown: %.3f' % (100.0 * num_unknown / num_total))
for (i, (p, users)) in enumerate(items[:20]):
    write('%d. %s: %d (%.1f%%)' %
            ((i+1), p, len(users), 100.0 * len(users) / len(total[p])))
f.close()
