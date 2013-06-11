function urlencode() {
    python -c "import sys, urllib as ul; print ul.quote_plus(sys.argv[1])" $1
}

EMAIL_FILE=$1
APP_URL="http://localhost:8080/SRSurvey"
<<<<<<< HEAD
SUB_RAW="ThisIsTheWorkingVersionOfTheSRSurveyScriptOnFriday"
=======
SUB_RAW="TestingFromtheSurveyTeam!!!!!"
>>>>>>> 8a43998a06a8628cd747b3d69b4b18c029ffb0b6
SUBJECT=`urlencode $SUB_RAW`
BASE_URL_RAW="http://localhost:8080/SRSurvey/interest/consent"
BASE_URL=`urlencode $BASE_URL_RAW`

echo EMAIL_FILE=$1
echo APP_URL=$APP_URL

while read email; do
    echo "inviting $email"
    urlEmail=`urlencode $email`
    wget -O - "$APP_URL/email/invite?email=$urlEmail&subject=$SUBJECT&baseUrl=$BASE_URL"
    echo "Sleeping"
    echo
    sleep 60
done <$EMAIL_FILE
