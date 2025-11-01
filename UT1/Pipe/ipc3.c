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
	int numero1,numero2,resultado1,resultado2,resultado3,resultado4;
	char mensaje[30];
	srand((unsigned) time(&t));//inicializamos la semilla para el random
	
	pipe(df);
	P2=fork();
	
	if(P2==0){//P2
		
		close(df[0]);//cerramos el pipe lector
		//creamos los numeros aleatorios
		numero1=rand() % 50;
		numero2=rand() % 50;
		sprintf(mensaje,"%d %d",numero1,numero2);
		//enviamos la información
		write(df[1],mensaje,strlen(mensaje));
		close(df[1]);
	
	}else{//P1
		
		close(df[1]);//cerramos el descriptor de escritora
		wait(NULL);//esperamos a que el hijo acabe
		read(df[0],buffer,sizeof(buffer));
		//dividimos la cadena formada por el padre
		scanf(buffer,"%d %d",&numero1,&numero2);
		//realizamos las operaciones
		resultado1=numero1+numero2;
		resultado2=numero1-numero2;
		resultado3=numero1*numero2;
		resultado4=numero1/numero2;
		//los mostrams por pantalla
		printf("%d + %d = %d\n",numero1,numero2,resultado1);
		printf("%d * %d = %d\n",numero1,numero2,resultado2);
		printf("%d * %d = %d\n",numero1,numero2,resultado3);
		printf("%d / %d = %d\n",numero1,numero2,resultado4);
		close(df[0]);
		
	}
	
	
	
	exit(0);
	
}
