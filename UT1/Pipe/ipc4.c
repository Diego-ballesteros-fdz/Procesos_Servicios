#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>


void main(){

	int df1[2],df2[2];
	char mensajedf1[30],mensajedf2[30];
	pid_t P2;
	time_t t;
	int numero1;
	unsigned long long factorial=1;
	srand((unsigned) time(&t));//inicializamos la semilla para el random
	
	pipe(df1);
	pipe(df2);
	P2=fork();
	
	if(P2==0){//P2
		
		close(df1[1]);
		close(df2[0]);
		read(df1[0],mensajedf1,sizeof(mensajedf1));//leemos el num aleatorio en mensajedf1
		numero1 = atoi(mensajedf1);  
		// convierte string a entero, si usaba scanf se quedaba pillado el programa esperando aleer del usuario
		
		// calculamos el factorial
		for(int i = 1; i <= numero1; i++) {
			
			factorial *= i;
			
		}
		//casteamos el factorial a string
		sprintf(mensajedf2,"%llu",factorial);
		
		write(df2[1],mensajedf2,sizeof(mensajedf2));//enviamos por el pipe2 el factorial
		close(df1[0]);
		close(df2[1]);
		
	
	}else{//P1
		
		close(df1[0]);//cerramos el pipe lector del pipe1
		close(df2[1]);//cerramos el pipe escritor del pipe2
		//enviamos la información
		//creamos el numero aleatorios en el pipe
		numero1=rand() % 11;
		sprintf(mensajedf1,"%d",numero1);
		write(df1[1],mensajedf1,sizeof(mensajedf1));
		printf("El proceso padre genera el numero %d en el pipe1\n",numero1);
		wait(NULL);//esperamos a que el hijo calcule
		read(df2[0],mensajedf2,sizeof(mensajedf2));//recibimos el resultado
		printf("El factorial calculado por el proceso hijo: %d != %s\n",numero1,mensajedf2);
		close(df1[1]);
		close(df2[0]);
		
	}

	exit(0);
}
