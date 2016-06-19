#!/bin/bash

COUNT=$1

curl -X GET http://localhost:8080/counter-api/top/${COUNT} -H"Accept: application/json"
echo
curl -X GET http://localhost:8080/counter-api/top/${COUNT} -H"Accept: text/csv"
