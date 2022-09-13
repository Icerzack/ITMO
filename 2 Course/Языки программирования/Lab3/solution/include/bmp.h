#pragma once

#include "inner_format.h"

enum input_state {
    READ_OK = 0,
    READ_ERR
};

enum output_state {
    WRITE_OK = 0,
    WRITE_ERR
};

enum input_state read_from_bmp_file(FILE* input, struct image* img);
enum output_state write_to_bmp_file(FILE* output, struct image const* img);
