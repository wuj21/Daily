#!/bin/env bash
filename=$1
n=1
m=`wc -l $filename | awk '{print $1}'`
echo $n, $m
while [ $n -le $m ]
do 
	e=$(( $n + 9))
	a=`printf "%05d\n" $n`
	#echo "\\timing on" > $filename$a.sql
	echo "delete from a0 where " >> $filename$a.sql
	t=1
	for p in `sed -n "$n,${e}p" $filename`; 
	do 
		if [ $t -lt 10 ] ; then
			echo " (a<>$p and a%$p=0) or" >> $filename$a.sql
		else
			echo " (a<>$p and a%$p=0) ; " >> $filename$a.sql
		fi
		t=$(( $t+1 ))
	done
	n=$(( $n + 10))
done
