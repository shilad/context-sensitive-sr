#!/usr/bin/python -O

import collections
import utils

s = utils.Survey()

f = open('results/demographics.txt', 'w')
def write(message):
    f.write(message + '\n')
    print(message)

male = ('m', 'male')
female = ('f', 'female')

gender = collections.defaultdict(int)
for u in s.users.values():
    if u.valid():
        g = 'other'
        if u.gender.lower() in male:
            g = 'male'
        elif u.gender.lower() in female:
            g = 'female'
        gender[g] += 1
 
total = sum(gender.values())

write('gender:')
for g in gender:
    write('\t%s: %d: %.1f' % (g, gender[g], 100.0*gender[g]/total))

education = {
    'all' : collections.defaultdict(int),
    'mturk' : collections.defaultdict(int),
    'scholar' : collections.defaultdict(int),
}

for u in s.users.values():
    if u.valid():
        education['all'][u.education] += 1
        if u.mturk and not u.scholar:
            education['mturk'][u.education] += 1
        if u.scholar and not u.mturk:
            education['scholar'][u.education] += 1
    

for c in education:
    write('education distribution for %s subjects:' % c)
    total = sum(education[c].values())
    for (e, n) in education[c].items():
        write('\t%s: %d: %.1f' % (e, n, 100.0*n/total))

for u in s.users.values():
    if u.scholar and u.interests:
        in_groups = [i for i in u.interests if i.lower() in utils.FIELDS]
        if len(in_groups) > 1:
            print in_groups

f.close()
