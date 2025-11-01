#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
void main(){
	
	printf("CCC \n");

		if (fork()!=0){
			
			wait(NULL);
			printf("AAA \n");
			
		} else {
			
			sleep(5);
			printf("BBB \n");
			
		}
		
	exit(0);
}
