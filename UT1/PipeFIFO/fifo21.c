#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(){

	time_t t;
	unsigned long long factorial=1;
	srand((unsigned) time(&t));//inicializamos la semilla para el random
    const char *fifo1 = "pipe02";
    int numero;
	
	mkfifo(fifo1, 0666);
	
	//creamos el numero aleatorios en el pipe
	numero=rand() % 11;
	
	//abrimos el canal en modo lectura y escritura
	int fd1 = open(fifo1, O_RDWR);
    if (fd1 == -1) {
        printf("escritor: error al abrir pipe02");
        exit(EXIT_FAILURE);
    }
    
    //pasamos el numero por pipe 1
	if (write(fd1, &numero, sizeof(numero)) == -1) {
        printf("escritor: error al escribir");
    } else {
        printf(" Número %d enviado al lector.\n", numero);
    }	
    
    //en este punto leemos el valor del pipe02 para poder leer
    if (read(fd1, &factorial, sizeof(factorial)) == -1) {
        printf("lector: error al leer");
    } 
    
    //en este punto hemos recibido los datos
    printf("Factorial recibido: %lld\n", factorial);
   
     close(fd1);
    
    //eliminamos el archivo
    unlink("pipe02");
	
    
	return 0;
}
