#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <fcntl.h>
#include <stdlib.h>
#include <ctype.h>
#include "structures.h"

#define BUFFER_SIZE 512


int main(int argc, char *argv[])
{
	if (argc != 3) {
		fprintf(stderr, "2 arguments required: {pid} struct_name={page | vm_area_struct}\n");
		return 1;
	}

	int pid = atoi(argv[1]);
	char *struct_name = argv[2];
	int struct_id;

	data_1 net_device_structure;
	data_2 thread_struct_structure;

	if (pid == 0 && !isdigit(argv[1][0])) {
		fprintf(stderr, "{pid} should be a number\n");
		return 1;
	}

	if (strcmp(struct_name, "net_device_struct") == 0)
		struct_id = 0;
	else if (strcmp(struct_name, "thread_struct") == 0)
		struct_id = 1;
	else {
		fprintf(stderr, "{struct_name} should be /net_device_struct/ or /thread_struct/\n");
		return 1;
	}
	
	int fd = open("/proc/my_module/struct_info", O_RDWR);
	if (fd == -1) {
		fprintf(stderr, "fopen: /proc/my_module opening error\n");
		close(fd);
		return 1;
	}
	char buf[BUFFER_SIZE];
	sprintf(buf, "%d %d", pid, struct_id);

	if (write(fd, buf, strlen(buf)) == -1) {
		fprintf(stderr, "Writing buffer=\"%s\" to fd=%d failed\n", buf, fd);
		close(fd);
		return 1;
	}

	if (read(fd, buf, BUFFER_SIZE) == -1) {
		fprintf(stderr, "Reading from fd=%d failed\n", fd);
		close(fd);
		return 1;
	}

	printf("--- PID=%d STRUCT=%s ---\n\n", pid, struct_name);

	if (struct_id == 0) {
		memcpy(&net_device_structure, buf, sizeof(net_device_structure));
 		printf("dev_name: %d\n", net_device_structure.dev_name);
        	printf("dev_base_address: %lu\n", net_device_structure.dev_base_address);
        	printf("dev_mem_start: %lu\n", net_device_structure.dev_mem_start);
        	printf("dev_mem_end: %lu\n", net_device_structure.dev_mem_end);
	}
	else if (struct_id == 1) {
		memcpy(&thread_struct_structure, buf, sizeof(thread_struct_structure));
		printf("thread_sp: %lu\n", thread_struct_structure.thread_sp);
        	printf("thread_es: %u\n", thread_struct_structure.thread_es);
        	printf("thread_ds: %u\n", thread_struct_structure.thread_ds);
        	printf("thread_iobm: %lu\n", thread_struct_structure.thread_iobm);
        	printf("thread_ts_error_code: %lu\n", thread_struct_structure.thread_ts_error_code);
	}

	close(fd);

	return 0;
}
