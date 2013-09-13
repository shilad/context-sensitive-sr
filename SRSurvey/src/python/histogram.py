#!/usr/bin/python -O

"""
    Generates a histogram of the ratinsg distribution for each condition.
"""


import string
import utils
import correlation

from utils import warn

f = open('results/histogram.txt', 'w')

s = utils.Survey()

def write(message):
    print message
    f.write(message + '\n')

for ktype in utils.SPECIFICITIES:
    for condition in utils.CONDITIONS:
        pair_ratings = s.get_ratings_by_condition(ktype, condition)
        values = []
        for ratings in pair_ratings.values():
            values.extend([r.response for r in ratings])
        if not values:
            continue
        hist = [0] * 6
        for r in values:
            hist[r] += 1
        write('condition %s, %s:'% (ktype, condition))
        write('\tn: %d' % len(values))
        write('\tmean: %.3f' % utils.mean(values))
        write('\tdev: %.3f' % utils.dev(values))
        tokens = [
            '%d=%d' % (i, hist[i])
            for i in range(1,6)
        ]
        write('\tcounts: %s' % string.join(tokens))
        tokens = [
            '%d=%.3f' % (i, 1.0 * hist[i] / len(values))
            for i in range(1,6)
        ]
        write('\thist: %s' % string.join(tokens))

