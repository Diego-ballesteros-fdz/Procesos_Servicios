#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>


void main(){
pid_t pid;

	pid=fork();
	
	if(pid==0){//el hijo
			
		printf("soy el proceso hijo y el alumno se llama DiegoBallesteros \n");
			
	}else{ //proceso padre
		
		printf("Esperando a que el proceso hijo acabe \n");
		wait(NULL);
		printf("\nSoy el proceso padre:\n\t Mi PID es %d, El PID de mi hijo es: %d.\n", getppid(),  getpid());
	} 
	exit(0);
}  
