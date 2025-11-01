#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
//corregir que el p1 espere a todos

void main(){
	
	pid_t pid,pid2,pid3;
	
	pid=fork();
	
	if (pid==0){ //P2
				
		if(getpid()%2==0){//par
				
				printf("Soy el primer hijo\n\tMi pid es %d y el de mi padre es %d\n",getpid(),getppid());
				
			}else{//impar
				
				printf("Soy el primero hijo\n\tMi pid es %d\n",getpid());
			
			}	

	}else{ //P1
				
		pid2 = fork();
		wait(NULL); //esperamos a uno de los hijos
				
		if(pid2==0){ // P3
			
			if(getpid()%2==0){//par
				
				printf("Soy el segundo hijo\n\tMi pid es %d y el de mi padre es %d\n",getpid(),getppid());
				
			}else{//impar
				
				printf("Soy el segundo hijo\n\tMi pid es %d\n",getpid());
			
			}	
		}else{
				
			pid3=fork();//creamos P4		
			wait(NULL); //esperamos al segundo hijo
			
			if(pid3==0){//P4
				
				if(getpid()%2==0){//par
				
				printf("Soy el nieto de P1\n\tMi pid es %d y el de mi padre es %d\n",getpid(),getppid());
				
			}else{//impar
				
				printf("Soy el nieto\n\tMi pid es %d\n",getpid());
			
			}	
				
			}else{//P1
				
				wait(NULL);
				printf("Soy el padre de todos y soy el último en escribir\n\tMi pid es %d\n",getpid());
				
			}
		}	
		
	exit(0);
	
	}
}
