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
	var k uint64
	for k = j + 1; k <= EDGE; k++ {
		judge(i, j, k)
	}
}

func gg2(i uint64) {
	var j uint64
	for j = i + 1; j < EDGE; j++ {
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
	time.Sleep(5 * time.Minute)
}
