#!/bin/bash

curl -v -X POST http://localhost:8080/counter-api/analyze -T src/test/resources/testdata.txt -H"Content-Type: text/plain"
