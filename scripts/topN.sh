#!/bin/bash

COUNT=$1

echo JSON Format
curl http://localhost:8080/counter-api/top/${COUNT} -u optus:candidates -H"Accept: application/json"
echo
echo Text Format
curl http://localhost:8080/counter-api/top/${COUNT} -u optus:candidates -H"Accept: text/csv"
