#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>

int main(){
	const char *fifo1 = "FIFO1";
    const char *fifo2 = "FIFO2";
    int numero;
    unsigned long long factorial=1;

    // Crear FIFO2 si no existe
    mkfifo(fifo2, 0666);
    
    printf("Esperando número del escritor...\n");
    
    // Abrir FIFO para lectura (bloquea hasta que el escritor escriba)
    int fd1 = open(fifo1, O_RDONLY);
    if (fd1 == -1) {
        printf("lector: error al abrir FIFO1");
        exit(EXIT_FAILURE);
    }
    
    // Leer el número enviado
    if (read(fd1, &numero, sizeof(numero)) == -1) {
        printf("lector: error al leer");
        close(fd1);
        exit(EXIT_FAILURE);
    }
    
    //en este punto el fifo1 a funcionado
    printf("Número recibido: %d\n", numero);
		// calculamos el factorial
		for(int i = 1; i <= numero; i++) {
			
			factorial *= i;
			
		}
		
	//ahora debemos mandar dicho factorial por el fifo2
	int fd2= open(fifo2,O_WRONLY);
	if (write(fd2,&factorial,sizeof(factorial))== -1){
	
		printf("escritor: error al escribir");
        close(fd2);
        exit(EXIT_FAILURE);
	
	}
	
	printf("Factorial enviado\n");
	close(fd2);
	//eliminams el archivo
	unlink("FIFO2");

		
	
	return 0;
}
