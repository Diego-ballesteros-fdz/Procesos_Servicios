#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(){
	const char *fifo1 = "pipe02";
    int numero;
    unsigned long long factorial=1;

	//lo creamos de nuevo por si no ha sido creado
	mkfifo(fifo1, 0666);
    
    printf("Esperando número del escritor...\n");
    
    // Abrir FIFO para lectura y escritura
    int fd1 = open(fifo1, O_RDWR);
    if (fd1 == -1) {
        printf("lector: error al abrir pipe02");
        exit(EXIT_FAILURE);
    }
    
    // Leer el número enviado
   if (read(fd1, &numero, sizeof(numero)) == -1) {
        printf("lector: error al leer");
        close(fd1);
        exit(EXIT_FAILURE);
    }
   
   //en este punto el pipe02 a funcionado la primera vez
    printf("Número recibido: %d\n", numero);
		// calculamos el factorial
		for(int i = 1; i <= numero; i++) {
			
			factorial *= i;
			
		}
		
	//ahora debemos mandar dicho factorial por el mismo pipe02
	if (write(fd1,&factorial,sizeof(factorial))== -1){
	
		printf("escritor: error al escribir");
        close(fd1);
        exit(EXIT_FAILURE);
	
	}
	
	printf("Factorial enviado\n");
	
	 //lo eliminams en fifo21 que acabara más tarde

		
	
	return 0;
}
