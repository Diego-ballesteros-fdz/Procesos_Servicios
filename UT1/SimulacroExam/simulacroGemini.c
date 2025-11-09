#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>

void main(){
	pid_t p2;
	char mensaje[30];
	int codigo;
	const char *fifo1 = "FIFO1";
	
	//creamos el pipe FIFO, asi evitamos crear dos anonimos
	mkfifo(fifo1, 0666);
	
	//abrimos el pipe para lectura y escritura
	int fd = open(fifo1, O_RDWR);
	
	
	p2=fork();
		
	if(p2==-1){ 
	
		printf("error al crear p2\n");
	
	}else if(p2==0){//bloque p2
	
		//leemos del pipe
		if (read(fd, mensaje, sizeof(mensaje)) == -1) {
			printf("lector: error al leer1");
			close(fd);
			exit(EXIT_FAILURE);
		}else{
			
			//hemos leido correctamente
			//creamos el codigoReal
			char codigoReal[30]="1234";
			
			//comprobamos
			if(strcmp(codigoReal, mensaje)==0){
				
				//es correcto enviamos confirm
				//preparamos el mensaje
				strcpy(mensaje,"ACCESO_OK");
				
				if (write(fd, mensaje, sizeof(mensaje)) == -1) {
			
				//error al enviar
				printf("escritor: error al escribir2");
				
				}
				
			}else{
				
				//es erroneo
				//preparamos el mensaje
				strcpy(mensaje,"ACCESO_FALLIDO");
				
				if (write(fd, mensaje, sizeof(mensaje)) == -1) {
			
				//error al enviar
				printf("escritor: error al escribir2");
					
				}
			}
		}	
		
		//acabamos el proceso
		exit(0);
		
	
	}else{//bloque p1
	
		time_t t;
		srand((unsigned) time(&t));
		codigo=rand() % 9000+1000;
		
		//convertimos a cadena
		sprintf(mensaje,"%d",codigo);
		
		//escribimos en el pipe
		if (write(fd, mensaje, sizeof(mensaje)) == -1) {
			
			//error al enviar
			printf("escritor: error al escribir1");
			
		}else{
			
			//p1 ha escrito escrito correctamente
			
			//esperamos a que el hijo acabe
			wait(NULL);
			//debemos leer de vuelta para poder ver el resultado
			if (read(fd, mensaje, sizeof(mensaje)) == -1) {
			printf("lector: error al leer2");
			close(fd);
			exit(EXIT_FAILURE);
			}else{
				
				//p1 ha leido
				//leemos fecha y hora
				time_t hora;
				char *fecha ;
				time(&hora);
				fecha = ctime(&hora);
				size_t len = strlen(fecha);
				
				//construimos el log
				char logFinal[100];
				sprintf(logFinal,"Fecha: %ld - PID hijo: %d - Estatus: %s \n",len,p2,mensaje);
				
				//lo mostramos en pantalla
				printf("%s",logFinal);
				
				//eliminamos el FIFO y salimos
				close(fd);
				unlink("FIFO1");
				
				exit(0);

			}
		}
	}	
}
