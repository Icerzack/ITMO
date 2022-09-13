#include "img_operations.h"

struct image rotate(struct image const source) {
    struct image new_img = img_create(source.height, source.width, NULL);
    new_img.data = malloc(new_img.width * new_img.height * sizeof(struct pixel));
    for (size_t i=0; i<source.height; i++) {
        for (size_t j=0; j<source.width; j++) {
            new_img.data[j * source.height + (source.height-i-1)] = source.data[i * source.width + j];
        }
    }
    return new_img;
}

struct image img_empty() {
    return (struct image) {
        .width = 0,
        .height = 0,
        .data = NULL
    };
}

struct image img_create(uint32_t width, uint32_t height, struct pixel* data) {
    return (struct image) {
        .width = width,
        .height = height,
        .data = data
    };
}
