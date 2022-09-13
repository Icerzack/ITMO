#include "bmp.h"
#include "img_operations.h"
// Заголовки bmp файла
uint32_t BI_SIZE = 40;
uint16_t BF_TYPE = 0x4d42;
uint16_t BI_BIT_COUNT = 24;
uint16_t BI_PLANES = 1;
uint32_t HEADER_SIZE = 54;

// Сама структура

struct __attribute__((packed)) bmp_header {
        uint16_t bfType;
        uint32_t bfileSize;
        uint32_t bfReserved;
        uint32_t bOffBits;
        uint32_t biSize;
        uint32_t biWidth;
        uint32_t biHeight;
        uint16_t biPlanes;
        uint16_t biBitCount;
        uint32_t biCompression;
        uint32_t biSizeImage;
        uint32_t biXPelsPerMeter;
        uint32_t biYPelsPerMeter;
        uint32_t biClrUsed;
        uint32_t biClrImportant;
};

// Методы для работы с изображением

static uint32_t get_size(uint32_t width, uint32_t height, uint32_t padding) {
    return (width * height * sizeof(struct pixel) + padding * height);
}

static uint32_t get_padding(uint32_t width) {
    uint32_t width_bytes = sizeof(struct pixel) * width;
    if (width_bytes % 4 == 0) {
        return 0;
    }
    return (4 - (width_bytes % 4));
}

// Методы для чтения заголовка

static enum input_state read_header(FILE* file, struct bmp_header* header) {
    size_t header_count = fread(header, sizeof(struct bmp_header), 1, file);
    if (header_count == 1) {
        return READ_OK;
    }
    return READ_ERR;
}

static enum input_state check_header(struct bmp_header* header) {
    if ((header->bfType != BF_TYPE) || (header->bOffBits != HEADER_SIZE) || (header->biPlanes != BI_PLANES) || (header->biBitCount != BI_BIT_COUNT) || (header->biSize != BI_SIZE)) {
        return READ_ERR;
    }
    return READ_OK;
}

static enum input_state read_img_data(FILE* file, struct image* img) {
    uint32_t width = img->width;
    uint32_t height = img->height;
    uint32_t padding = get_padding(width);
    if (width == 0 || height == 0){
        return READ_ERR;
    }
    struct pixel* data = malloc( width * height * sizeof(struct pixel));
    if (data == NULL) {
        return READ_ERR;
    }
    struct pixel* data_copy = data;
    for (size_t i=0; i<height; i++) {
        size_t pixels = fread( data_copy, sizeof(struct pixel), width, file);
        size_t paddings = fseek(file, padding, SEEK_CUR);
        if ((pixels != width) || (paddings != 0)) {
            free(data_copy);
            return READ_ERR;
        }
        data_copy = data_copy + width;
    }
    img->data = data;
    return READ_OK;
}

// Методы для записи заголовка

static enum output_state write_header(FILE* file, uint32_t width, uint32_t height, uint32_t padding) {
    uint32_t data_size = get_size(width, height, padding);
    struct bmp_header header = {
        .bfType = BF_TYPE,
        .bfileSize = HEADER_SIZE + data_size,
        .bfReserved = 0,
        .bOffBits = HEADER_SIZE,
        .biSize = BI_SIZE,
        .biWidth = width,
        .biHeight = height,
        .biPlanes = BI_PLANES,
        .biBitCount = BI_BIT_COUNT,
        .biCompression = 0,
        .biSizeImage = data_size,
        .biXPelsPerMeter = 0,
        .biYPelsPerMeter = 0,
        .biClrUsed = 0,
        .biClrImportant = 0
    };
    size_t header_count = fwrite(&header, sizeof(struct bmp_header), 1, file);
    if (header_count == 1){
        return WRITE_OK;
    }
    return WRITE_ERR;
}

static enum output_state write_img_data(FILE* file, uint32_t width, uint32_t height, uint32_t padding, struct pixel* pointer) {
    for (size_t i=0; i<height; i++) {
        size_t pixels = fwrite(pointer, sizeof(struct pixel), width, file);
        size_t paddings = fwrite(pointer, padding, 1, file);
        if ((pixels != width) || (paddings != 1 && padding != 0) || (paddings !=0 && padding == 0)) {
            return WRITE_ERR;
        }
        pointer = pointer + width;
    }
    return WRITE_OK;
}

// Методы, выполняющие операцию чтения и записи bmp
enum input_state read_from_bmp_file(FILE* input, struct image* img){
    struct bmp_header bmp_header = {0};
    enum input_state header_state = read_header(input, &bmp_header);
    enum input_state check_state = check_header(&bmp_header);
    if ((header_state != READ_OK) || (check_state != READ_OK)) {
        return READ_ERR;
    }
    img->width = bmp_header.biWidth;
    img->height = bmp_header.biHeight;
    enum input_state data_state = read_img_data(input, img);
    return data_state;
}

enum output_state write_to_bmp_file(FILE* output, struct image const* img) {
    uint32_t width = img->width;
    uint32_t height = img->height;
    uint32_t padding = get_padding(width);
    enum output_state header_state = write_header(output, width, height, padding);
    if (header_state == WRITE_OK) {
        header_state = write_img_data(output, width, height, padding, img->data);
    }
    return header_state;
}
