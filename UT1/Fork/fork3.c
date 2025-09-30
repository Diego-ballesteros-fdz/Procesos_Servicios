#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void main(){
	
	
	//variables con nombres directos
	pid_t pid,pid2,pid3,pidP2;
	pid_t pidP1=getpid();
	
	
	pid=fork();
	
	if(pid==0){//P2
		
		pidP2=getpid();
		
		pid2=fork();
		
		if(pid2==0){//P3
			
			pid3=fork();
			
			if(pid3==0){//P5
				
				sleep(3);//le hacemos esperar para que de tiempo a crear los procesos hijo ||no es una solucion||
				//printf("5");
				printf("Soy el proceso P5:\n\tMi pid es %d y el de mi abuelo es %d\n",getpid(),pidP2);
				
			}else{//P3
				
				wait(NULL);
				//printf("3");
				printf("Soy el proceso P3:\n\tMi pid es %d y el de mi abuelo es %d\n",getpid(),pidP1);
				
			}
			
		}else{//P2
			
			pid2=fork();
			
			if(pid2==0){//P4
				
				pid3=fork();
			
				if(pid3==0){//P6
					
					//printf("6");
					printf("Soy el proceso P6:\n\tMi pid es %d y el de mi abuelo es %d\n",getpid(),pidP2);
					
				}else{//P4
					
					wait(NULL);
					//printf("4");
					printf("Soy el proceso P4:\n\tMi pid es %d y el de mi abuelo es %d\n",getpid(),pidP1);
					
				}
				
			}else{//P2
				
				wait(NULL);
				wait(NULL);//espera dos veces porque tiene dos procesos hijo
				//printf("2");
				printf("soy el proceso P2:\n\tMi pid es %d y no tengo abuelo\n",getpid());
				
			}
		}
		
		
	}else{//P1 se queda esperando
		
		wait(NULL);
		//printf("1");
		printf("Soy el proceso P1.\n\tMi pid es %d\n",getpid());
		
	}
	
	exit(0);

}
