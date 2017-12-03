package main

import "time"

func main() {
	i := 0
	for i < 100 {
		i++
		go func() {
			time.Sleep(1000000000000)
		}()
	}
	time.Sleep(10000000000000)
}
