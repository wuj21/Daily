package main

import "fmt"

func main() {
	var in, i, start uint64
  start = 1073741825 * 2 + 11
	for i = start; i <= start + 1000; i++ {
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
