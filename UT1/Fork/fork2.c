#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void main(){
	
	pid_t pid,pid2,pid3;
	
	pid=fork();
	
	if(pid==0){
		
		pid2=fork();
		
		if(pid2==0){
			
			pid3=fork();
			
			if(pid3==0){
				
				sleep(5);//esperamos para que le de al padre ejecutar el wait y no dejar ningun huerfano
				printf("Soy el proceso P4:\n\tmi pid es %d,el pid de mi padre es %d, y la suma de ambos da %d.\n",getpid(),getppid(),getpid()+getppid());
				
			}else{
				
				wait(NULL);
				printf("Soy el proceso P3:\n\tmi pid es %d,el pid de mi padre es %d, y la suma de ambos da %d.\n",getpid(),getppid(),getpid()+getppid());
				
			}
			
		}else{
			
			wait(NULL);
			printf("Soy el proceso P2:\n\tmi pid es %d,el pid de mi padre es %d, y la suma de ambos da %d.\n",getpid(),getppid(),getpid()+getppid());
			
		}
		
	}else{
		
		//al establecer que este padre espere a su hijo encadenamos wait para todos los procesos hijos, que a su vez usan wait
		wait(NULL);
		printf("Soy el proceso P1:\n\tmi pid es %d,el pid de mi padre es %d, y la suma de ambos da %d.\n",getpid(),getppid(),getpid()+getppid());
			
	}

	exit(0);
	
}
