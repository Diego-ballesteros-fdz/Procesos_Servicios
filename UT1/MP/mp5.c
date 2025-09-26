#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void main(){
	
	pid_t pid1, pid2;

	printf("AAA \n");

	pid1 = fork();

		if (pid1==0){
			
			printf("BBB \n");

		}else{
			
			pid2 = fork();
			wait(NULL); //esperamos a uno de los hijos
			
			if(pid2==0){
				
				printf("soy el segundo hijo\n");
				
			}else{
				
				wait(NULL); //esperamos al segundo hijo
				printf("CCC \n");
			}
				
		}

	exit(0);

}
