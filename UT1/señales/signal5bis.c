#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>
pid_t p2=1,p3=0;
int fd=0;
const char *fifo1="pipe1";


void captor(int sign){
	int num;
	
	if(sign==SIGINT){//PARA CAPTURAR LA SIGINT
		
		if(p2==0){//p2
			printf("Mandando señal de terminación al proceso hijo P2 con pid %d\n",getpid());
			exit(0);
		}
		else if(p3==0){//p3
			printf("Mandando señal de terminación al proceso hijo P3 con pid %d\n",getpid());
			exit(0);
		}
		else{
			wait(NULL);//esperamos a un hijo
			wait(NULL);//esperamos al segundo
			printf("Fin proceso padre con pid %d\n",getpid());
			//eliminamos el fifo
			close(fd);
			unlink("pipe1");
			exit(0);
		}
		
	}
	if(sign==SIGUSR1){//PARA CAPTURAR LA SIGUSR1
		
		read(fd, &num, sizeof(num));
		printf("\nNúmero par %d recibido por el proceso P2 con pid %d\n",num,getpid());
		
	}
	if(sign==SIGUSR2){//PARA CAPTURAR LA SIGUSR2
		read(fd, &num, sizeof(num));
		printf("\nNúmero impar %d recibido por el proceso P3 con pid %d\n",num,getpid());
		
	}
}

int main(){
	int num;
	//preparamos las capturas
	signal(SIGINT,captor);
	signal(SIGUSR1,captor);
	signal(SIGUSR2,captor);
	
	//creamos el fifo
	mkfifo(fifo1, 0666);
	
	//creamos p2
	p2=fork();
	
	if(p2!=0){//p1
		//creamos a p3
		p3=fork();
	}
	//en este punto la estructura de procesos esta terminada
	//vamos con el bucle para lo pedido en el ejercicio
		do{
			if(p2!=0 && p3!=0){//p1
				fd=open(fifo1,O_WRONLY);//abrimos el pipe
				
				//solicitamos el numero
				printf("Introduce número:");
				scanf("%d",&num);
				
				//printf("antes de escribir el num es %d",num);
				//escribimos el numero para que los hijos puedan leer
				write(fd,&num,sizeof(num));
				
				//tratamos el numero para enviar las señales
				if(num%2==0 && num!=0){//en este caso el num es par
					kill(p2,SIGUSR1);
				}
				else if(num%2!=0){//en este caso num es impar
					kill(p3,SIGUSR2);
				}	
				else{//el usuario a pulsado 0
					//mandamos el sigint a todos los procesos asociados
					kill(0,SIGINT);
				}
				
				sleep(1);//esperamos para que la estructura del printf fluya como queremos
			}
			else if(p3==0){//p3
				
				fd=open(fifo1,O_RDONLY);//abrimos el pipe
				
				pause();//esperamos la señal
				
			}
			else if(p2==0){//p2
				
				fd=open(fifo1,O_RDONLY);//abrimos el pipe
			
				pause();//esperando la señal
			}
			close(fd);//cerramos en cada interaccion
		}while(1);	
	//nunca llegaremos aqui.
	exit(0);
}
