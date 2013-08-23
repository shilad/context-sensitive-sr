#!/usr/bin/python -O

import collections
import utils

s = utils.Survey()

unknown = collections.defaultdict(set)

for u in s.users.values():
	if u.valid():
		for r in u.rated:
			if r.knows1:
				unknown[r.phrase1].add(r.user)
			if r.knows2:
				unknown[r.phrase2].add(r.user)

items = unknown.items()
items.sort(lambda i1, i2: cmp(len(i1[1]), len(i2[1])))
items.reverse()

for (p, users) in items:
	print len(users), p
