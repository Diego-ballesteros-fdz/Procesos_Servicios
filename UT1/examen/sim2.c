

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
	int num, cont, suma;
	char mensaje[30];
	const char *fifo1 = "FIFO1";//declaramos el fifo
	const char *fifo2 = "FIFO2";
	
	p2=fork();
	//creamos el fifo
	mkfifo(fifo1, 0666);
	mkfifo(fifo2, 0666);
	
	if(p2!=0){//p1
		
		//preparamos fifo
		//enviamos por el fifo
		int fd1 = open(fifo1, O_WRONLY);
		int fd2 = open(fifo2, O_RDONLY);

		if (fd1 == -1) {
			printf("escritor: error al abrir FIFO");
			exit(EXIT_FAILURE);
		}
		
		//bucle hasta -1
		do{
			//solicitamos por pantalla
			printf("Escriba un número, si desea salir escriba -1\n");
			scanf("%d",&num);
			//pasamos la info
			if (write(fd1, &num, sizeof(num)) == -1) {
				
				//error al enviar
				printf("escritor: error al escribir");
				
			}	
		}while(num!=-1);
		
		//recibimos resultados
		
		
		if (read(fd2, mensaje, sizeof(mensaje)) == -1) {
			printf("lector: error al leer");
			close(fd2);
			exit(EXIT_FAILURE);
		}
		
		
		//printf("mensaje recibido: %s",mensaje);
		
		sscanf(mensaje,"%d %d",&cont,&suma);
		
		//escribimos el resultado por consola
		printf("La cantidad de numeros negativos es de: %d\n",cont);
		printf("La suma de numeros positivos es: %d\n",suma);
		
		
		wait(NULL);
		close(fd1);
		close(fd2);
		unlink("FIFO1");
		exit(0);
		
		
		
	}else{//p2
		
		//abrir el fifo2 para poder leer en el receptor
		int fd1= open(fifo1,O_RDONLY);
		int fd2= open(fifo2,O_WRONLY);
		
		int cont=0,suma=0;
		
		do{
		if (read(fd1, &num, sizeof(num)) == -1) {
			printf("lector: error al leer");
			close(fd1);
			exit(EXIT_FAILURE);
		}
		
		//printf("mensaje recibido en p2: %d",num);
		
		//calculamos lo solicitado
		
		if(num>0){//positivos
			
			suma+=num;
			
		}else{//negativos
			
			if(num!=-1){//solo si el valor es distinto que -1
				cont++;
			}
			
		}
	
		}while(num!=-1);
		//formamos el mensaje
		sprintf(mensaje,"%d %d",cont,suma);
		//enviamos al p1
		
		if (write(fd2, mensaje, sizeof(mensaje)) == -1) {
			printf("lector: error al leer");
			close(fd2);
			exit(EXIT_FAILURE);
		}
		
		
		
	}
	
}
