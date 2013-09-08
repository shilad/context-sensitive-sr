#!/bin/bash

FILES="
phrasepairs-log.txt
dat/pilot.txt
dat/main_outgroup_emails.txt
dat/in_emails.txt
"
for file in $FILES; do
    rsync -avz poliwiki:usr/context-sensitive-sr/SRSurvey/$file $file
done

python ./src/python/make_consistency_csv.py >./dat/ratings.tsv 
