#!/bin/bash

curl -X GET http://localhost:8080/counter-api/top/5 -u optus:candidates -H"Accept: application/json"
echo
echo
curl -X GET http://localhost:8080/counter-api/top/5 -u optus:candidates -H"Accept: text/csv"
