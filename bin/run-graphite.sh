#!/bin/sh

# from http://graphite.readthedocs.io/en/latest/install.html
# adjust as necessary to run it on windows

docker run -d \
 --name graphite \
 --restart=always \
 -p 80:80 \
 -p 2003-2004:2003-2004 \
 -p 2023-2024:2023-2024 \
 -p 8125:8125/udp \
 -p 8126:8126 \
 graphiteapp/graphite-statsd
