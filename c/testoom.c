#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <unistd.h>

int main(int argc, char** argv) {
	int p;
	int* m;
	while (1) {
		p = fork();
		if (0 != p) {
			m = (int*) malloc (10000 * sizeof(int));
		}	
	}
	return 0;
}
