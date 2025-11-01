#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>

void main(){
	
	int df[2];
	char buffer[30];
	pid_t P2;
	time_t t;// variable para guardar el tiempo en segundos
    struct tm *info;//estructura del sistema para visualizar el tiempo
	
	//creamos el pipe
	pipe(df);
	
	P2=fork();
	
	if(P2==0){//P2
		
		close(df[1]);//cerramos el descriptor de escritora
		read(df[0],buffer,sizeof(buffer));
		printf("soy el proceso hijo con pid %d\n",getpid());
		printf("el mensaje recibido es:\n\t%s\n",buffer);
		close(df[0]);
	
	}else{//P1
		
		time(&t);//obtenemos el tiempo
		info=localtime(&t);
		//funcion que toma la estructura info y escribe en el buffer una cadena formateada segun el formato dado
		strftime(buffer, sizeof(buffer), "Fecha/Hora: %d/%m/%Y %H:%M:%S", info);
		close(df[0]);//cerramos el pipe lector
		write(df[1],buffer,sizeof(buffer));
		wait(NULL);
		close(df[1]);
	}
	
	exit(0);

}
