#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void main(){
	
	pid_t P2,P3,P4,P5;
	pid_t acumulado=getpid();
	
	P2=fork();
	
	if(P2==0){ //P2
		
		P5=fork();
		
		if(P5==0){//P5
			
			if(getpid()%2==0){
			
				printf("soy el P5 y mi acumulado es igual a %d\n",acumulado+10);
			
			}else{
				
				printf("Soy el P5 y mi acumulado es igual a %d\n",acumulado-100);
				
			}
			
		}else{//P2
			
			wait(NULL);
			
			if(getpid()%2==0){
			
				printf("soy el P2 y mi acumulado es igual a %d\n",acumulado+10);
			
			}else{
				
				printf("Soy el P2 y mi acumulado es igual a %d\n",acumulado-100);
				
			}
		}
		
		
	}else{ //P1
		
		P3=fork();
		
		if(P3==0){//P3
			
			P4=fork();
			
			if(P4==0){//P4
				
				if(getpid()%2==0){
			
				printf("soy el P4 y mi acumulado es igual a %d\n",acumulado+10);
			
				}else{
				
				printf("Soy el P4 y mi acumulado es igual a %d\n",acumulado-100);
				
				}
					
			}else{//P3
				
				wait(NULL);
				
				if(getpid()%2==0){
			
					printf("soy el P3 y mi acumulado es igual a %d\n",acumulado+10);
			
				}else{
				
					printf("Soy el P3 y mi acumulado es igual a %d\n",acumulado-100);
				
				}
				
			}
			
		}else{//P1
			
			wait(NULL);
			wait(NULL);//esperamos al segundo hijo
			printf("Soy el P1 y he acabado\n");
			
		}
		
	}
	
	exit(0);
	
	}
