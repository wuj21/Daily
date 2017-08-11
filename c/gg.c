#include <stdio.h>

int main (int argc, char** argv) {
  long i, j, k, m, n;
  for (i = 1; i < 10000 - 1; i ++) {
    for (j = i + 1; j < 10000; j ++) {
      for (k = j + 1; k <= 10000; k ++) {
        if ( i * i + j * j == k * k) {
          if ((k + i) % 2 == 0) {
            m = (k + i) / 2;
            n = (k - i) / 2;
          } else if ((k + j) % 2 ==0) {
            m = (k + j) / 2;
            n = (k - j) / 2;
          } else {
            m = -1;
            n = -1;
          }
          printf ("%d, %d, %d, %d, %d\n", i, j, k, m, n);
        }
      }
    }
  }
}
