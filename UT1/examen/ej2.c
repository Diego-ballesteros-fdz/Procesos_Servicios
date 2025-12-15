#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>
pid_t P1=-2,P2=-2,P3=-2;//globales
int num, fd1[2];

void sign_fun(int sig){

	}

void main(){
		pid_t p1,p2,p3;
		
		
		P1=getpid();
		
		pipe(fd1);
		
		p2=fork();
		
		
		
		if(p2==-1){ 
		
			printf("error al crear p2\n");
		
		}else if(p2==0){//bloque p2
		
			//codigo que ejecuta p2
			P2=getpid();
			
			signal(SIGUSR1,sign_fun);
			
			close(fd1[1]);
			//realizamos el bucle infinito que hara el pause
			while(1){
				
				pause();
				read(fd1[0],&num,sizeof(num));
				printf("Número par %d recibido por el proceso p2 con pid %d",num,getpid());
				
			}
			
		
		}else{//bloque p1
		
			p3=fork();
			
		
			if(p3==-1){ 
		
				printf("error al crear p3\n");
		
			}else if(p3==0){//bloque p3
				P3=getpid();
				//codigo que ejecuta p3
				
				signal(SIGUSR2,sign_fun);
				
				close(fd1[1]);
				//realizamos el bucle infinito que hara el pause
				while(1){
					
					pause();
					read(fd1[0],&num,sizeof(num));
					printf("Número impar %d recibido por el proceso p3 con pid %d",num,getpid());
					
					
				}
			
		
			}else if(p2!=0 && p3!=0){//bloque p1
		
			//codigo que ejecuta el p1
			close(fd1[0]);
			do{
			printf("Introduce un numero: \n");
			scanf("%d",&num);
			
			
			if(num%2==0){//es par lanzamos la señal 1
				printf("%d",p2);
				write(fd1[1],&num,sizeof(num));
				kill(p2,SIGUSR1);
				
				
			}else if(num==0){//lanzamos la señal de SIGINT
				
				kill(p2,SIGINT);
				kill(p3,SIGINT);
				
			}else{//es impar lanzamos la señal 2
				printf("%d",p3);
				write(fd1[1],&num,sizeof(num));
				kill(p3,SIGUSR2);
				
				
			}
		}while(num!=0);
			
			
			exit(0);
		
			}
		
		}
	}




	
	
