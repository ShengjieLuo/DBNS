#include <stdio.h>
#include <sys/resource.h>
#include <unistd.h>

int main()
{	
	struct rlimit limit;
	getrlimit(RLIMIT_NOFILE, &limit);
	int a = limit.rlim_cur;
	printf ("this process can open %d files \n",a);
	return 0;
}
