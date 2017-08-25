package main

import (
	"fmt"
	//"time"
)

const (
	EDGE = 10000
)

var (
	c chan uint64
)

func isSquire(i uint64) (res uint64, err bool) {
	var l, h, m uint64
	l = 1
	h = i
	m = (l + h) / 2
	for l <= h {
		//		fmt.Printf("debug: %d,%d,%d\n", l, h,m)
		m = (l + h) / 2
		//		fmt.Printf("debug: %d,%d,%d\n", l, h, m)
		if i/m == m && i%m == 0 {
			return m, true
		}
		if i/m > m {
			l = m + 1
		} else {
			h = m - 1
		}
		/*
			if i/m == m && i%m == 0 {
				return m, true
			}*/
	}

	if i/m == m && i%m == 0 {
		return m, true
	}
	return 0, false
}

func judge(i, j, k uint64) {
	if i*i+j*j == k*k {
		fmt.Printf("%d, %d, %d\n", i, j, k)
	}
}

func gg1(i, j uint64) {
	//var k uint64
	/*
			if i*i/2 < EDGE {
				t = i*i/2 + 2
			} else {
				t = EDGE
			}

		for k = j + 1; k <= i*i/2+3; k++ {
			judge(i, j, k)
		}**/
	res, ris := isSquire(i*i + j*j)
	if ris {
		fmt.Printf("%d, %d, %d, %t\n", i, j, res, i*i+j*j == res*res)
	}
}

func gg2(i uint64) {
	//	fmt.Printf("Trying %d\n", i)
	var j uint64
	/*
		if i*i/2 < EDGE {
			t = i*i/2 + 2
		} else {
			t = EDGE
		}*/
	for j = i + 1; j < i*i/2+2; j++ {
		gg1(i, j)
	}
	c <- i
}

func main() {
	var i uint64
	var count uint32
	c = make(chan uint64, 1600)
	for i = 1; i < EDGE+1; i++ { /*
			for j = i + 1; j < EDGE; j++ {
				for k = j + 1; k <= EDGE; k++ {
					if i*i+j*j == k*k {
						fmt.Printf("%d, %d, %d\n", i, j, k)
					}
				}
			}*/
		go gg2(i)
		/*res, ris := isSquire(i)
		if ris {
			fmt.Printf("%d, %d\n", i, res)
		}*/
	}
	fmt.Printf("%s\n", "I am here")
	//time.Sleep(10 * time.Minute)
	//var count uint32
	for count = 0; count < EDGE; count++ {
		//		count++
		result := <-c
		//fmt.Printf("finished %d\n", result)
		result++
	}
}
