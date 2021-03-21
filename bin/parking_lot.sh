#!/usr/bin/bash
SCRIPT=$(readlink -f "$0")
SCRIPTPATH=$(dirname "$SCRIPT")
TARGETPATH=`echo $SCRIPTPATH|sed -e 's/bin/target/'`
java -jar $TARGETPATH/parking_lot-1.0-SNAPSHOT.jar $1 $2 $3
