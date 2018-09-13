#!/bin/env bash
n=1
m=`wc -l all | awk '{print $1}'`
echo $n, $m
while [ $n -le $m ]
do 
	e=$(( $n + 7))
	a=`printf "%05d\n" $n`
	echo "\\timing on" > all$a.sql
	echo "delete from a0 where " >> all$a.sql
	t=1
	for p in `sed -n "$n,${e}p" all`; 
	do 
		if [ $t -lt 8 ] ; then
			echo " (a<>$p and a%$p=0) or" >> all$a.sql
		else
			echo " (a<>$p and a%$p=0) ; " >> all$a.sql
		fi
		t=$(( $t+1 ))
	done
	n=$(( $n + 8))
done
