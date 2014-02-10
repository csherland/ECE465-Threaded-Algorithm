#!/usr/bin/python

# Christian Sherland

import random

if __name__ == "__main__":

    f = open('test.txt', 'w')

    for i in range(1, 40000000):
        num = random.randrange(1,100000000)
        f.write(str(num) + '\n')

