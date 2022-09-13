#pragma once

#include "stdlib.h"
#include "stdint.h"
#include "stdio.h"

struct image {
    uint64_t width, height;
    struct pixel* data;
};

struct pixel {uint8_t b,g,r;};


