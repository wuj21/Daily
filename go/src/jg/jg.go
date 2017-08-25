package main

import "fmt"

func main() {
	var in, i uint64
	for i = 1048577; i <= 1048577+999; i++ {
		in = i
		for in > 1 {
			fmt.Printf("%d->", in)
			if in%2 == 0 {
				in /= 2
			} else {
				in = in*3 + 1
			}
		}
		fmt.Printf("%d\n----------------------------------------------------\n", in)
	}
}
