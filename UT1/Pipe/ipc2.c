#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>


void main(){

	int df[2];
	char buffer[30];
	pid_t P2;
	time_t t;
	int numero1,numero2,numero3,resultado;
	char operador;
	char mensaje[30];
	srand((unsigned) time(&t));//inicializamos la semilla para el random
	
	pipe(df);
	P2=fork();
	
	if(P2==0){//P2
		
		close(df[1]);//cerramos el descriptor de escritora
		read(df[0],buffer,sizeof(buffer));
		//dividimos la cadena formada por el padre
		sscanf(buffer,"%d %d %d %c",&numero1,&numero2,&numero3,&operador);
		//mostramos en pantalla los numeros
		printf("Numero a sumar:%d\n",numero1);
		printf("Numero a sumar:%d\n",numero2);
		printf("Numero a sumar:%d\n",numero3);
		printf("recibido caracter %c\n",operador);
		resultado=numero1+numero2+numero3;//realizamos la operación
		printf("La suma total es: %d\n",resultado);
	
	}else{//P1
		
		close(df[0]);//cerramos el pipe lector
		//creamos los numeros aleatorios
		numero1=rand() % 50;
		numero2=rand() % 50;
		numero3=rand() % 50;
		operador='+';
		sprintf(mensaje,"%d %d %d %c",numero1,numero2,numero3,operador);
		//enviamos la información
		write(df[1],mensaje,strlen(mensaje));
		wait(NULL);
		
	}
	
	exit(0);
	
}
