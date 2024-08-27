#!/bin/bash
FILE="$HOME/.aws/credentials"
/bin/cat <<EOM >$FILE
[default]
aws_access_key_id=$1
aws_secret_access_key=$2
EOM
FILE="$HOME/.aws/config"
/bin/cat <<EOM >$FILE
[default]
region=$3
output=$4
EOM

FILE="credentials"
/bin/cat <<EOM >$FILE
[default]
aws_access_key_id=$1
aws_secret_access_key=$2
EOM
FILE="config"
/bin/cat <<EOM >$FILE
[default]
region=$3
output=$4
EOM