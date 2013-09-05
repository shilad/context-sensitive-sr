#!/usr/bin/python

import httplib
import psycopg2
import sys
import time
import urllib

conn = psycopg2.connect("dbname=macademia_prod user=grails password=grails")
cur = conn.cursor()

inDept = eval(sys.argv[1])
assert(inDept == True or inDept == False)


for email in sys.stdin:
	email = email.strip()
	cur.execute('select full_name from person where email = %s;', (email,))
	if cur.rowcount == 0:
		print 'did not find person with email', `email`
		continue
	name = cur.fetchone()[0]
	print 'sending email to', email, name

	params = { 'name' : name, 'email' : email, }
	if inDept:
		params['inDept'] = 'true'
	else:
		params['inDept'] = ''
	
	encoded = urllib.urlencode(params)
	http = httplib.HTTPConnection("macademia.macalester.edu")
	url = '/SRSurvey/email/invite?' + encoded
	http.request('GET', url)

	time.sleep(10)
