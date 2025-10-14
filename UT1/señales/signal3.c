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

void captor(int senial){
	signal(SIGINT, SIG_IGN);//ignoramos la señal mandada para evitar conflictos
	FILE *fichero;
	fichero = fopen("Fichero.txt", "a");
    time(&t);
    info = localtime(&t);
    strftime(buffer, sizeof(buffer), "%d/%m/%Y %H:%M:%S", info);
	fprintf(fichero, "Señal SIGINT recibida a las %s\n",buffer);
	fclose(fichero);
	signal(SIGINT, captor);//volvemos a permitir la señal para futuros usos
	}

int main() {
	remove("Fichero.txt");//eliminamos el fichero por si estubiera ya creado
    signal(SIGINT,captor);
	//damos tiempo a que el usuario haga la señal
	while(1){
		sleep(1);
	}
}
