#!/bin/sh

while true; do
  curl -s http://gateway:8080/request-worker-1 &
  curl -s http://gateway:8080/request-worker-2 &
  curl -s http://gateway:8080/request-worker-3 &
  sleep 1
done