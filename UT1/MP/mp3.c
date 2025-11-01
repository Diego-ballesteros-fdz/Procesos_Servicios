#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>


void main(){
	
	pid_t pid,pid2;
	
	pid=fork();
	
	if(pid==-1){
		
		printf("\n algo salio mal\n");
		
	}else if(pid==0){//proceso hijo
		
		sleep(10);
		printf("\nDespierto\n");
		
	}else{
		
		pid2=fork();
		
		if(pid2==-1){
			
			printf("\n algo salio mal\n");
			
		}else if(pid2==0){//proceso hijo P3
			
			printf("\nSoy el proceso hijo(P3)\n\t Mi PID es %d, El PID de mi padre es: %d.\n",  getpid(), getppid());
			
		}else{
			
			wait(NULL);//esperaq a cualquiera P2 P3
			wait(NULL);//espera al segundo hijo
			
		}
	
	}
	
	exit(0);
	
}
