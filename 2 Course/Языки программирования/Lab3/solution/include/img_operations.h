#pragma once

#include "inner_format.h"

struct image rotate(struct image const source);

struct image img_empty();

struct image img_create(uint32_t width, uint32_t height, struct pixel* data);
