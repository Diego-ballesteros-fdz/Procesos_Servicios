#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>
void main(){
		pid_t p1,p2;
		int df1[2],df2[2];
		char mensaje[30];//solo si es char[]
		int numDNI;
	
		//creación del pipe
		pipe(df1);
		pipe(df2);
		
		p1=getpid();
		
		//creación del hijo
		p2=fork();
		
		if(p2==-1){ 
		
			printf("error al crear p2\n");
		
		}else if(p2==0){//bloque p2
			
			printf("Escriba el numero de su DNI sin letra: \n");
			scanf("%d",&numDNI);
		
			//cerrar el descriptor escritor
			close(df1[0]);
			close(df2[1]);
			//pasamos a string
			sprintf(mensaje,"%d",numDNI);
			//escribimos en el pipe
			write(df1[1],mensaje,sizeof(mensaje));
			//leer
			
			
			
			read(df2[0],mensaje,sizeof(mensaje));
			
			printf("La letra de tu DNI es: %s",mensaje);
		
		}else{//bloque p1
		
			//cerrar descriptor lector
			close(df1[1]);
			close(df2[0]);
			
			//recibimos del pipe
			read(df1[0],mensaje,sizeof(mensaje));
			//printf("recibido mensaje del p2: %s",mensaje);
			//calculamos la letra
			int dni;
			char letra[] = "TRWAGMYFPDXBNJZSQVHLCKE";
			dni = atoi(mensaje);
			dni %= 23;
			//printf("%c",letra[dni]);
			char l = letra[dni];
			//escribimos
			write(df2[1],&l,sizeof(&l));
			//o usar strlen(mensaje) + 1 en lugar de sizeof
			
			wait(NULL);
		
		}
	}
