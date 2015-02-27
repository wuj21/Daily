#!/bin/env bash
if [ $# -eq 0 ] ; then
    echo "usage http_proxy.sh on|off"
    exit 0
fi

if [ $1 == "on" ] ; then 
    echo "enable http_proxy and https_proxy";
    export http_proxy=http://10.32.16.123:3128
    export https_proxy=$http_proxy
fi

if [ $1 == "off" ] ; then
    echo "disable http_proxy and https_proxy"
    unset http_proxy
    unset https_proxy
fi
