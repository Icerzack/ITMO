#include <linux/kernel.h> 
#include <linux/init.h> 
#include <linux/module.h> 
#include <linux/kdev_t.h> 
#include <linux/fs.h> 
#include <linux/cdev.h> 
#include <linux/slab.h>               
#include <linux/uaccess.h>              
#include <linux/proc_fs.h> 
#include <linux/pid.h> 
#include <linux/sched.h> 
#include <linux/sched/signal.h> 
#include <linux/netdevice.h> 
#include <linux/device.h>
#include <linux/init.h>
#include <linux/netdevice.h>
#include <linux/seq_file.h>
#include <linux/module.h>
#include <asm/thread_info.h>
#include <asm/processor.h>
#include "structures.h"

#define PROCFS_ENTRY_NAME "my_module"
#define KBUF_SIZE 512

static int pid = 0;
static int struct_id = 0;

static struct proc_dir_entry *parent;

static size_t write_net_device_struct(char __user *ubuf) {
	char *kbuf = kmalloc(KBUF_SIZE, GFP_KERNEL);
        unsigned long nbytes;
	static char buf[KBUF_SIZE];
	size_t len = 0;
	static struct net_device *dev;
	read_lock(&dev_base_lock);
	dev = first_net_device(&init_net);
	printk(KERN_INFO "I am here");
        data_1 net_device_structure;
	while (dev) {
		net_device_structure.dev_name = dev->name;
		net_device_structure.dev_base_address = dev->base_addr;
		net_device_structure.dev_mem_start = dev->mem_start;
		net_device_structure.dev_mem_end = dev->mem_end;
		dev = next_net_device(dev);
	}
	read_unlock(&dev_base_lock);
	memcpy(kbuf, &net_device_structure, sizeof(net_device_structure));
	nbytes = copy_to_user(ubuf, kbuf, sizeof(net_device_structure));
	if (nbytes) {
		kfree(kbuf);
		printk("copy2buf_page: copy_to_user can't copy %lu bytes\n", nbytes);
		return 0;
	}

	kfree(kbuf);
    	return sizeof (net_device_structure);
}

static ssize_t write_thread_struct(struct file * file, char __user * buff, size_t count, loff_t * offset) {
	char *kbuf = kmalloc(KBUF_SIZE, GFP_KERNEL);
	unsigned long nbytes;
	static char buf[KBUF_SIZE];
	pr_info("Reading TS Info");
	static int read_status = 0;
	if(read_status != 0) {
		read_status = 0;
		return 0;
	}
	printk("TS read input : %s\n", buf);
	long seeked_pid = 1;
	kstrtol(buf, 10, &seeked_pid);
	printk("TS read input atoi: %d\n", seeked_pid);
	struct task_struct *task;
	int found_flag = 0;
	for_each_process(task) {
		if(task->pid == seeked_pid) {
			found_flag = 1;
			break;
		}
	}
    	data_2 thread_struct_structure;
	if(found_flag == 1) {
		struct thread_struct found_thread = task->thread;
		
		unsigned long sp = found_thread.sp;
		unsigned short es = found_thread.es;
		unsigned short ds = found_thread.ds;
		unsigned long iobm = found_thread.io_bitmap_ptr;
		unsigned long ts_error_code = found_thread.error_code;

        	thread_struct_structure.thread_sp = sp;
        	thread_struct_structure.thread_es = es;
        	thread_struct_structure.thread_ds = ds;
        	thread_struct_structure.thread_iobm = iobm;
        	thread_struct_structure.thread_ts_error_code = ts_error_code;
	}
	memcpy(kbuf, &thread_struct_structure, sizeof(thread_struct_structure));
	nbytes = copy_to_user(buff, kbuf, sizeof(thread_struct_structure));
	if (nbytes) {
		kfree(kbuf);
		printk("copy2buf_thread_struct_structure: copy_to_user can't copy %lu bytes\n", nbytes);
		return 0;
	}
	
	kfree(kbuf);

	return sizeof(thread_struct_structure);
}


static ssize_t proc_read(struct file *file, char __user *ubuf, size_t ubuf_size, loff_t *ppos)
{
	char *kbuf = kmalloc(KBUF_SIZE, GFP_KERNEL);
	ssize_t actual_kbuf_size = 0;

	struct pid *pid_struct;
	struct task_struct *task_struct;
	struct mm_struct *mm_struct;
	
	pid_struct = find_get_pid(pid);
	if (NULL == pid_struct)	{
		printk(KERN_INFO "Process with pid=%d doesn't exist\n", pid);
		actual_kbuf_size = sprintf(kbuf, "Process with pid=%d doesn't exist\n", pid);
		copy_to_user(ubuf, kbuf, actual_kbuf_size);
		kfree(kbuf);
		return actual_kbuf_size;
	}

	task_struct = pid_task(pid_struct, PIDTYPE_PID);
	if (NULL == task_struct) {
		printk(KERN_INFO "Failed to get task_struct with pid=%d\n", pid);
		actual_kbuf_size = sprintf(kbuf, "Failed to get task_struct with pid=%d\n", pid);
		copy_to_user(ubuf, kbuf, actual_kbuf_size);
		kfree(kbuf);
		return actual_kbuf_size;
	}

	mm_struct = task_struct->mm;
	if (NULL == mm_struct) {
		printk(KERN_INFO "mm_struct is NULL | pid=%d\n", pid);
                actual_kbuf_size = sprintf(kbuf, "mm_struct is NULL | pid=%d\n", pid);
                copy_to_user(ubuf, kbuf, actual_kbuf_size);
		kfree(kbuf);
                return actual_kbuf_size;
	}
	
	if (struct_id == 0)
		actual_kbuf_size = write_net_device_struct(ubuf);
	else if (struct_id == 1)
		actual_kbuf_size =  write_thread_struct(file, ubuf, ubuf_size, ppos);

	kfree(kbuf);
	return actual_kbuf_size;
}


static ssize_t proc_write(struct file *file, const char __user *ubuf, size_t ubuf_len, loff_t *offset)
{
	char *kbuf = kmalloc(KBUF_SIZE, GFP_KERNEL);
	int arg1, arg2, args_num;
	
	unsigned long nbytes = copy_from_user(kbuf, ubuf, ubuf_len);
	if (nbytes) {
		printk("proc_write: copy_from_user can't copy %lu bytes\n", nbytes);
		kfree(kbuf);
		return 0;
	}
	printk(KERN_INFO "proc_write: write %zu bytes\n", ubuf_len);

	args_num = sscanf(kbuf, "%d %d", &arg1, &arg2);
	if (args_num == 2) {
		printk(KERN_INFO "Arguments have been read: arg1 = %d, arg2 = %d\n", arg1, arg2);
		pid = arg1;
		struct_id = arg2;
	}
	else {
		printk(KERN_INFO "sscanf failed: %d argument(s) have been read", args_num);
	}
	
	kfree(kbuf);
	return ubuf_len;
}


static const struct file_operations proc_fops = {
	.read = proc_read,
	.write = proc_write,
};

static int __init my_module_init(void)
{
       parent = proc_mkdir("my_module",NULL); 
 
    if( parent == NULL ) 
    { 
        pr_info("Error creating proc entry"); 
        return -1; 
    } 
 
    proc_create("struct_info", 0666, parent, &proc_fops); 
 
    pr_info("Device Driver Insert...Done!!!!!!\n"); 
    return 0; 
}


static void __exit my_module_cleanup(void)
{
        proc_remove(parent);
        printk(KERN_INFO "/proc/%s removed", PROCFS_ENTRY_NAME);
}


module_init(my_module_init);
module_exit(my_module_cleanup);


MODULE_LICENSE("GPL");
