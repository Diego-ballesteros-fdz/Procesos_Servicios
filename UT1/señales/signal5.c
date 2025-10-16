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
			kill(getpid(),SIGINT);
		}else if(p3==0){//proceso p3
			printf("Mandando señal de terminación al proceso hijo P3 con pid %d\n",getpid());
			kill(getpid(),SIGINT);
		}	
	}
	if(sig==SIGUSR1){//P2
		
		fd=open(fifo1,O_RDONLY);
		read(fd, &num, sizeof(num));
		printf("\nNúmero par %d recibido por el proceso P2 con pid %d\n",num,getpid());
		
	}
	
	if(sig==SIGUSR2){//P3
		
		fd=open(fifo1,O_RDONLY);
		read(fd, &num, sizeof(num));
		printf("\nNúmero impar %d recibido por el proceso P3 con pid %d\n",num,getpid());
	}
	//reactivamos las señales ¿¿parece que en sistemas modernos es redundante??
	signal(SIGUSR1,capturador);
	signal(SIGUSR2,capturador);
}

int main(){
	//creamos el fifo
	mkfifo(fifo1, 0666);
	
	p2=fork();
	
	if(p2==0){//p2
		sigset_t set2;
		int sign2;

		// Inicializamos el conjunto de señales
		sigemptyset(&set2);
		sigaddset(&set2, SIGUSR1); // señal para leer número
		
		signal(SIGUSR1,capturador);//activamos el capturador para p2
		signal(SIGINT,capturador);//capturamos el sigint para p2
		//sigprocmask(SIG_BLOCK, &set2, NULL);
		
		do{
			
			sigwait(&set2,&sign2);
			
		}while(num!=0);
		
	}else{//p1
		p3=fork();
		
		if(p3==0){//p3
			sigset_t set3;
			int sign3;

			// Inicializamos el conjunto de señales
			sigemptyset(&set3);
			sigaddset(&set3, SIGUSR1); // señal para leer número
		
			signal(SIGUSR2,capturador);//activamos el capturador para p3
			signal(SIGINT,capturador);//capturamos el sigint para p3
			//sigprocmask(SIG_BLOCK, &set3, NULL);
			do{
				
				sigwait(&set3,&sign3);
				
			}while(num!=0);
		}else{//p1
			
			fd=open(fifo1, O_WRONLY);
			
			do{
				
				printf("Introduce número:");
				scanf("%d",&num);
				if(num%2==0){//es par para el p2
					
					kill(p2,SIGUSR1);//mandamos la señal al p2 para que entre al captor
					
				}else if(num==0){
					
					kill(p2,SIGINT);//mandamos la señal de matar proceso p2
					kill(p3,SIGINT);//mandamos la señal de matar proceso p3					
					
				}else{
					
					kill(p3,SIGUSR2);// para que entre al captor p3
				}
				
				if(num!=0){
					
					write(fd,&num,sizeof(num));//en caso de no ser 0 escribira en el pipe
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
