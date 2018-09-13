#!/bin/env bash
a=1;
for i in `seq 1 35`;do 
	a=`echo $a+$a| bc`;
	echo "insert into a0 (a) (select a+$a from a0);"
done
