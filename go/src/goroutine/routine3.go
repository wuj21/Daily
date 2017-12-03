package main

import (
	"fmt"
	"sync"
	"time"
)

func main() {
	m := make(map[int]string)
	m[2] = "First Value"
	var mutex sync.Mutex
	cv := sync.NewCond(&mutex)
	updateComplete := false
	go func() {
		cv.L.Lock()
		m[2] = "Second Value"
		updateComplete = true
		cv.Signal()
		cv.L.Unlock()
	}()
	time.Sleep(1000000)
	cv.L.Lock()
	for !updateComplete {
		cv.Wait()
	}
	v := m[2]
	cv.L.Unlock()
	fmt.Printf("%s\n", v)
}
