package main

import (
	"fmt"
	"time"
)

const (
	EDGE = 10000
)

func judge(i, j, k uint64) {
	if i*i+j*j == k*k {
		fmt.Printf("%d, %d, %d\n", i, j, k)
	}
}

func gg1(i, j uint64) {
	var k, t uint64
	if i*i/2 < EDGE {
		t = i*i/2 + 2
	} else {
		t = EDGE
	}
	for k = j + 1; k <= t; k++ {
		judge(i, j, k)
	}
}

func gg2(i uint64) {
	//	fmt.Printf("Trying %d\n", i)
	var j, t uint64
	if i*i/2 < EDGE {
		t = i*i/2 + 2
	} else {
		t = EDGE
	}
	for j = i + 1; j < t; j++ {
		gg1(i, j)
	}
}

func main() {
	var i uint64
	for i = 1; i < EDGE-1; i++ { /*
			for j = i + 1; j < EDGE; j++ {
				for k = j + 1; k <= EDGE; k++ {
					if i*i+j*j == k*k {
						fmt.Printf("%d, %d, %d\n", i, j, k)
					}
				}
			}*/
		go gg2(i)
	}
	fmt.Printf("%s\n", "I am here")
	time.Sleep(5 * time.Minute)
}
