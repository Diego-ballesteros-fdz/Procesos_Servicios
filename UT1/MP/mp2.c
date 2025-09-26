#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/wait.h>

void main(){
	
	pid_t pid;
	pid_t pidNieto;
	
	pid=fork();
	
	if(pid==-1){
		printf("\nalgo salio mal");
	}else if(pid==0){ //aqui tenemos al hijo P2
		printf("\n\tsoy el proceso hijo(P2), mi pid es %d y el de mi padre %d\n",getpid(),getppid());
		pidNieto=fork();
		if(pidNieto==-1){
			printf("\nalgo salio mal");
		}else if(pidNieto==0){ //aqui tenemos al proceso nieto P3
			printf("\n\tsoy el proceso nieto(P3), mi pid es %d y el de mi padre %d\n",getpid(),getppid());
		}else{//proceso hijo esperando al nieto
			wait(NULL);
		}
	}else{//aqui solo llega el proceso padre P1
		printf("\n\tsoy el proceso padre(P1), mi pid es %d y el de mi hijo es %d\n",getpid(),pid);
		wait(NULL);
	
	}	
	exit(0);
}
