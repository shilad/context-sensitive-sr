#!/usr/bin/python -O

import sys

import correlation
import utils

survey = utils.Survey()

wordsim = {}
for line in open('dat/general.txt'):
    tokens = line.split('\t')
    pair = tuple(sorted([t.strip() for t in tokens[:2]]))
    wordsim[pair] = float(tokens[2])


for condition in ('all', 'mturk', 'scholar'):
    ratings = survey.get_ratings_by_condition('general', condition)
    X = []
    Y = []
    for (pair_id, pair_ratings) in ratings.items():
        pair = tuple(sorted(survey.id_to_phrases(pair_id)))
        if pair in wordsim and len(pair_ratings) > 10:
            X.append(utils.mean([r.response for r in pair_ratings if r.has_response()]))
            Y.append(wordsim[pair])
        else:
            print 'unknown pair:', pair
    print condition, correlation.pearson_rho(X, Y), correlation.spearman_rho_tr(X, Y)
