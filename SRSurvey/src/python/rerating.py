#!/usr/bin/python -O

import correlation
import collections
import utils

s = utils.Survey()

deltas = collections.defaultdict(list)
spearmans = collections.defaultdict(list)

f = open('results/rerating.txt', 'w')
def write(message):
    f.write(message + '\n')
    print(message)

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
            if u.mturk and not u.scholar:
                spearmans['mturk'].append(rho)
            if u.scholar and not u.mturk:
                spearmans['scholar'].append(rho)

write('intra-rater spearman correlations:')
for c in spearmans:
    write('\t%s: %.3f n=%d, dev=%.3f' % (
            c,
            utils.mean(spearmans[c]),
            len(spearmans[c]),
            utils.dev(spearmans[c])
        ))

for spec in utils.SPECIFICITIES:
    write('mean absolute errors for %s questions:' % spec)
    for c in utils.CONDITIONS:
        diffs = []
        for (pair_id, ratings) in s.get_ratings_by_condition(spec, c).items():
            for reratings in utils.group_by(ratings, lambda r: r.user).values():
                if len(reratings) >= 2:
                    diffs.append(abs(reratings[0].response - reratings[1].response))
        if diffs:
            write('\tcondition %s: mae=%.3f, n=%d, dev=%.3f' %
                ( c, utils.mean(diffs), len(diffs), utils.dev(diffs)))
f.close()
