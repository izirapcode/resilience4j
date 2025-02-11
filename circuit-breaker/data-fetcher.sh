#!/bin/sh

while true; do
  response=$(curl -s http://gateway-service:8080/fetch-data)
  echo "$response"
  sleep 1
done