#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <time.h>

	time_t t;               
    struct tm *info;        
    char buffer[80];
    int cont=0,veces=0,seg=0;        

void captor(int senial){
	
		cont++;
		time(&t);
		info = localtime(&t);
		strftime(buffer, sizeof(buffer), "%d/%m/%Y %H:%M:%S", info);
		printf("Señal de alarma recibida a las %s\n",buffer);
		if (cont < veces) {
			// Reprogramamos la próxima alarma
			alarm(seg);
		} else {
			printf("Alarma desactivada\n");
			exit(0);
		}
	}

int main() {
    signal(SIGALRM,captor);
	printf("¿Cuántas veces sonará la alarma?:\n");
	scanf("%d",&veces);
	printf("¿Cada cuántos segundos se repetirá la alarma?:\n");
	scanf("%d",&seg);
	printf("Alarma activada\n");
	alarm(seg);
	//mantenemos el programa vivo
	while(1){
		sleep(1);
	}
}
