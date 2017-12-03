package main

import (
	"fmt"
	"sync"
	"time"
)

func main() {
	m := make(map[int]string)
	m[2] = "First Value"
	var lock sync.Mutex
	go func() {
		lock.Lock()
		m[2] = "Second Value"
		lock.Unlock()
	}()
	time.Sleep(1000000)
	lock.Lock()
	v := m[2]
	lock.Unlock()
	fmt.Printf("%s\n", v)
}
