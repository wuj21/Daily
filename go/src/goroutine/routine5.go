package main

import (
	"fmt"
	"os"
	"strings"
	"sync"
)

func main() {
	var w sync.WaitGroup
	for _, v := range os.Args {
		w.Add(1)
		go func(str string) {
			fmt.Printf("%s\n", strings.ToUpper(str))
			w.Done()
		}(v)
	}
	w.Wait()
}
