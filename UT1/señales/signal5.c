#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>

//inicializamos los p para poder usarlos en la funcion capturador
pid_t p2=0,p3=0;

void capturador(int signal){
	
	if(p2==0){//proceso p2
		printf("Mandando señal de terminación al proceso hijo P2 con pid %d\n",getpid());
		exit(0);
	}else if(p3==0){//proceso p3
		printf("Mandando señal de terminación al proceso hijo P3 con pid %d\n",getpid());
		exit(0);
	}	
}
int main(){
	int fd,num;
	const char *fifo1 = "pipe1";
	//creamos el fifo
	mkfifo(fifo1, 0666);
	
	signal(SIGUSR1,capturador);//activamos el capturador
	
	p2=fork();
	
	if(p2==0){//p2
		do{
			fd=open(fifo1,O_RDONLY);
			read(fd, &num, sizeof(num));
			if(num==0){
				raise(SIGUSR1);//lanzamos la señal
			}else if(num%2==0){
				printf("\nNúmero par %d recibido por el proceso P2 con pid %d\n",num,getpid());
			}
		}while(num!=0);
		
	}else{//p1
		p3=fork();
		
		if(p3==0){//p3
			do{
				fd=open(fifo1,O_RDONLY);
				read(fd, &num, sizeof(num));
				
				if(num==0){
					raise(SIGUSR1);//lanzamos la señal
				}else if(num%2!=0){
					printf("\nNúmero impar %d recibido por el proceso P3 con pid %d\n",num,getpid());
				}
			}while(num!=0);
		}else{//p1
			
			fd=open(fifo1, O_WRONLY);
			
			do{
				
				printf("Introduce número:");
				scanf("%d",&num);
				write(fd,&num,sizeof(num));
				//write(fd,&num,sizeof(num));//lo escribimos por segunda vez para el segundo hijo pueda leerlo
				
			}while(num!=0);
			wait(NULL);//esperamos a un hijo
			wait(NULL);//esperamos al segundo
			printf("Fin proceso padre con pid %d\n",getpid());
			exit(0);
		}
	}
	
	
}
