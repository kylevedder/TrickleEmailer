#Trickle Emailer

Send batches of emails with personalization.

Use the message parameter syntax to add parameters to emails. Use single quotes (`'`) to escape curly braces and other single quotes.

## Setup

To setup, create an [application specific password](https://security.google.com/settings/security/apppasswords?pli=1), and enter that as the `password` property in your credentials properties file, along with your full email address as the `username` property.

## Sample Usage

`java -jar TrickleEmailer.jar "creds.properties" "message.txt" "personal.txt" "5000"`