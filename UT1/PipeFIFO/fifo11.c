#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(){

	time_t t;
	unsigned long long factorial=1;
	srand((unsigned) time(&t));//inicializamos la semilla para el random
    const char *fifo1 = "FIFO1";
    const char *fifo2 = "FIFO2";
    int numero;
	
	mkfifo(fifo1, 0666);
	
	//creamos el numero aleatorios en el pipe
	numero=rand() % 11;
	
	//abrimos el canal
	int fd1 = open(fifo1, O_WRONLY);
    if (fd1 == -1) {
        printf("escritor: error al abrir FIFO");
        exit(EXIT_FAILURE);
    }
    
    //pasamos el numero por pipe 1
	if (write(fd1, &numero, sizeof(numero)) == -1) {
        printf("escritor: error al escribir");
    } else {
        printf(" Número %d enviado al lector.\n", numero);
    }	
    
    //en este punto debemos abrir el fifo2 para poder leer
    int fd2= open(fifo2,O_RDONLY);
     if (read(fd2, &factorial, sizeof(factorial)) == -1) {
        printf("lector: error al leer");
        close(fd2);
        exit(EXIT_FAILURE);
    }
    
    //en este punto hemos recibido los datos
    printf("Factorial recibido: %d\n", factorial);
    close(fd1);
    
    //eliminamos el archivo
    unlink("FIFO1");
	return 0;
}
