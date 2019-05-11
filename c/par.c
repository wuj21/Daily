#include <stdio.h>
int main(int argc, char** argv) {
	double sum = 0.0;
	long i;
	double flag = 1.0;
	for (i = 1; i < 999999999L; i += 2) {
		sum += 4.0 / (i * flag);
		flag = -flag;
	}
	printf ("%lf\n", sum);
	return 0;
}
