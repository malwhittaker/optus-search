#!/bin/bash

curl -X POST http://localhost:8080/counter-api/search -d@searchList.txt -H"Content-Type: application/json"
