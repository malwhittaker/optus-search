#!/bin/bash

echo
echo JSON format
curl -X POST http://localhost:8080/counter-api/search -u optus:candidates -d@scripts/searchList.json -H"Content-Type: application/json"

echo
echo Text format
curl -X POST http://localhost:8080/counter-api/search -u optus:candidates -d@scripts/searchList.json -H"Content-Type: application/json" -H"Accept: text/csv"
