#!/usr/bin/python -O

import correlation
import collections
import utils

s = utils.Survey()

deltas = collections.defaultdict(list)
spearmans = collections.defaultdict(list)

for u in s.users.values():
    if u.valid():
        ratings = {}
        X = []
        Y = []
        for r in u.rated:
            if r.has_response():
                if r.pair_id in ratings:
                    X.append(ratings[r.pair_id])
                    Y.append(r.response)
                    d = abs(ratings[r.pair_id] - r.response)
                    deltas['all'].append(d)
                    deltas[r.condition].append(d)
                    deltas[(r.condition, (r.field == 'general'))].append(d)
                else:
                    ratings[r.pair_id] = r.response

        if len(X) == len(Y) == 5 and len(set(X)) > 1 and len(set(Y)) > 1:
            rho = correlation.spearman_rho_tr(X, Y)
            spearmans['all'].append(rho)
            if u.mturk:
                spearmans['mturk'].append(rho)
            if u.scholar:
                spearmans['scholar-out'].append(rho)


f = open('results/rerating.txt', 'w')
for (c, abs_diff) in deltas.items():
    corrs = spearmans[c]
    hist = [0.0] * 4
    for d in abs_diff:
        hist[d] += 100.0 / len(abs_diff)
    s = '%s n_users=%s mean_spearman=%.3f n_ratings=%s mae=%.3f %s' % (
            c,
            len(spearmans[c]), utils.mean(spearmans[c]),
            len(abs_diff), utils.mean(abs_diff), hist
        )
    f.write(s + '\n')
    print(s)
f.close()
