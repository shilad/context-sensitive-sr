#!/usr/bin/python -O 

import string
import utils

s = utils.Survey()

print string.join([
            'u_email',
            'u_gender',
            'u_education',
            'u_scholar',
            'u_condition',
            'r_id',
            'r_concept1',
            'r_concept2',
            'r_field',
            'r_mean',
            'r_in',
            'r_unknown1',
            'r_unknown2',
            'r_rating'
    ], '\t')

for u in s.users.values():
    if u.valid():
        for r in u.rated:
            if r.field not in utils.FIELDS + ['general']:
                continue
            tokens = [
                r.user.email,
                r.user.gender,
                r.user.education,
                r.user.scholar,
                r.user.condition,
                r.pair_id,
                r.phrase1,
                r.phrase2,
                r.field,
                r.mean,
                r.in_group,
                r.knows1,
                r.knows2,
                r.response,
            ]
            print string.join(map(str, tokens), '\t')
