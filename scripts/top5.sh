#!/bin/bash

curl -X GET http://localhost:8080/counter-api/top/5 -H"Accept: application/json"
echo
curl -X GET http://localhost:8080/counter-api/top/5 -H"Accept: text/csv"
