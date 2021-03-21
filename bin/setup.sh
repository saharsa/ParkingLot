#!/usr/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")
CODEPATH=$(dirname "$SCRIPTPATH")
mvn -f $CODEPATH/pom.xml clean install
