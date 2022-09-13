#include "img_operations.h"
#include "bmp.h"

char* read_state_message[] = {
    [READ_OK] = "Succeeded to read file \n",
    [READ_ERR] = "Failed to read file\n"
};

char* write_state_message[] = {
    [WRITE_OK] = "Succeeded to write file\n",
    [WRITE_ERR] = "Failed to write file \n"
};

int main(int argc, char** argv) {
    if (argc == 3) {
        FILE* input_file = NULL;
        FILE* output_file = NULL;
        input_file = fopen(argv[1], "rb");
        if (input_file != NULL){
            fprintf(stderr, "%s", "Opened input_file successfully\n");
            output_file = fopen(argv[2], "wb");
            if(output_file != NULL){
                fprintf(stderr, "%s", "Opened output_file successfully\n");
                struct image image = img_empty();
                enum input_state input_state = read_from_bmp_file(input_file, &image);
                fprintf(stderr, "%s", read_state_message[input_state]);
                if (input_state == READ_OK) {
                    struct image new_img = rotate(image);
                    enum output_state output_state = write_to_bmp_file(output_file, &new_img);
                    fprintf(stderr, "%s", write_state_message[output_state]);
                    free(new_img.data);
                    free(image.data);
                }
                fclose(input_file);
                fclose(output_file);
            } else {
                fclose (input_file);
                fprintf(stderr, "%s", "Failed to open output_file\n");
            }
        } else fprintf(stderr, "%s", "Failed to open input_file\n");
    } else fprintf(stderr, "%s", "Expected 3 arguments\n");
    return 0;
}
