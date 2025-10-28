#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <time.h>

//los inicializamos de forma global

	time_t t;               // variable para guardar el tiempo actual
    struct tm *info;        // estructura para desglosar el tiempo
    char buffer[80];        // buffer para almacenar la fecha/hora formateada

void captor(int signal){
	// Obtener la hora actual
    time(&t);

    // Convertir a hora local
    info = localtime(&t);

    // Formatear la fecha y hora
    strftime(buffer, sizeof(buffer), "%d/%m/%Y %H:%M:%S", info);
	
	printf("Fin del proceso %d: %s\n",getpid(),buffer);
	
	exit(0);
	}

int main() {
	    
    signal(SIGINT,captor); // Esta línea registra la función manejadora para la señal SIGINT. 
						   // A partir de aquí, si se pulsa Ctrl+C, se ejecutará fun_signal()

    // Obtener la hora actual
    time(&t);
    // Convertir a hora local
    info = localtime(&t);
    // Formatear la fecha y hora
    strftime(buffer, sizeof(buffer), "%d/%m/%Y %H:%M:%S", info);
	printf("Inicio del proceso %d: %s\n",getpid(),buffer);
	
	//damos tiempo a que el usuario haga la señal
	sleep(30);
	exit(0);
	}
