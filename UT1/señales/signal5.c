#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>

//inicializamos los p para poder usarlos en la funcion capturador
pid_t p2=0,p3=0;
int num=0,fd=0;
const char *fifo1 = "pipe1";


void capturador(int sig){
	
	if(sig==SIGINT){//PARA ACABAR PROCESOS
		if(p2==0){//proceso p2
			printf("Mandando señal de terminación al proceso hijo P2 con pid %d\n",getpid());
			exit(0);
		}else if(p3==0){//proceso p3
			printf("Mandando señal de terminación al proceso hijo P3 con pid %d\n",getpid());
			exit(0);
		}	
	}
	if(sig==SIGUSR1){//P2
		
		
		read(fd, &num, sizeof(num));
		printf("\nNúmero par %d recibido por el proceso P2 con pid %d\n",num,getpid());
		
		
	}
	
	if(sig==SIGUSR2){//P3
		
		
		read(fd, &num, sizeof(num));
		printf("\nNúmero impar %d recibido por el proceso P3 con pid %d\n",num,getpid());
		
	}
	
	//reactivamos las señales ¿¿parece que en sistemas modernos es redundante??
	//signal(SIGUSR1,capturador);
	//signal(SIGUSR2,capturador);
	
}

int main(){
	//creamos el fifo
	mkfifo(fifo1, 0666);
	signal(SIGUSR1,capturador);
	signal(SIGUSR2,capturador);
	signal(SIGINT,capturador);
	
	p2=fork();
	
	if(p2==0){//p2
		fd=open(fifo1, O_RDONLY);
		
		do{
			
			pause();
			
		}while(num!=0);
		
	}else{//p1
		p3=fork();
		
		if(p3==0){//p3
			fd=open(fifo1, O_RDONLY);
			
			do{
				
				pause();
				
			}while(num!=0);
		}else{//p1
			
			fd=open(fifo1, O_WRONLY);
			
			do{
				
				printf("Introduce número:");
				scanf("%d",&num);
				if(num%2==0 && num!=0){//es par para el p2
					
					kill(p2,SIGUSR1);//mandamos la señal al p2 para que entre al captor
					
				}else if(num==0){
					//printf("entra en 0");
					
					kill(p2,SIGINT);//mandamos la señal de matar proceso p2
					kill(p3,SIGINT);//mandamos la señal de matar proceso p3					
					
				}else{
					
					kill(p3,SIGUSR2);// para que entre al captor p3
				}
				
				if(num!=0){
					
					write(fd,&num,sizeof(num));//en caso de no ser 0 escribira en el pipe
					sleep(1);
				}
				
			}while(num!=0);
			
			close(fd);//cerramos el pipe
			
			wait(NULL);//esperamos a un hijo
			wait(NULL);//esperamos al segundo
			
			printf("Fin proceso padre con pid %d\n",getpid());
			
			exit(0);
		}
	}
}
