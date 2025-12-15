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
	pid_t p1,p2,p3;
	int df[2], df1[2], df2[2], num, nveces,suma,multi;
	char mensaje[30];//solo si es char[]

	//creación del pipe
	pipe(df);
	pipe(df1);
	pipe(df2);
	
	p2=fork();
	
	if(p2==-1){ 
	
		printf("error al crear p2\n");
	
	}else if(p2==0){//bloque p2
	
		p3=fork();
	
		if(p3==-1){ 
		
			printf("error al crear p3\n");
		
		}else if(p3==0){//bloque p3
		
			//codigo que ejecuta p3
			//cerrar el descriptor escritor
			close(df1[1]);
			//leer
			read(df1[0],mensaje,sizeof(mensaje));
			
			//printf("P3 %s",mensaje);
			//array de numeros para trabajar con ellos
			int numeros[30];
			nveces=atoi(mensaje);
			for(int i=1;i<=nveces;i++){
				
				//leemos del pipe num a num
				read(df1[0],mensaje,sizeof(mensaje));
				//printf("P3, %s\n",mensaje);
				//almacenamos en el array
				numeros[i-1]=atoi(mensaje);
				
			}
			
			suma=0,multi=1;
			//realizar calculo
			for(int i=0;i<nveces;i++){
			
				if(numeros[i]%2==0){//pares
					suma+=numeros[i];
				}else{//impares
					multi*=numeros[i];
				}
				
			 }
			 
			//printf("suma %d y multi %d",suma,multi);
			
			//preparamos el mensaje
			sprintf(mensaje,"%d %d",suma,multi);
			 
			//devolcer a p2
			close(df2[0]);
			
			write(df2[1],mensaje,sizeof(mensaje));
			
			//printf("%s",mensaje);
			
			
			close(df1[0]);
			close(df2[1]);
			exit(0);
			
		
		}else{//bloque p2
		
			//codigo que ejecuta el p2
			//recibimos n numeros
			//cerrar el descriptor escritor
			close(df[1]);
			//leer
			read(df[0],mensaje,sizeof(mensaje));
			//enviamos a p3 n numeros
			close(df1[0]);
			//escribir
			write(df1[1],mensaje,sizeof(mensaje));
			
			//printf("P2 %s",mensaje);
			
			//for para solicitar n numeros
			nveces=atoi(mensaje);
			for(int i=1;i<=nveces;i++){
				
				printf("Escriba un número: \n");
				scanf("%d",&num);
				sprintf(mensaje,"%d",num);
				//escribimos del pipe num a num
				write(df1[1],mensaje,sizeof(mensaje));
				
			}
			
			//recibimos resultados
			close(df2[1]);
			
			read(df2[0],mensaje,sizeof(mensaje));
			
			//dividimos el mensaje
			sscanf(mensaje,"%d %d",&suma,&multi);
			
			printf("El total de la suma de lo números pares es: %d\n",suma);
			printf("El total de la multiplicacion de los números impares es: %d\n",multi);
			
			
			close(df[0]);
			close(df1[1]);
			close(df2[0]);
			wait(NULL);
			exit(0);
		
		}
	
	}else{//bloque p1
	
		//codigo que ejecuta el p1
		//solicitar por consola n numeros
		printf("Introduce un numero para establecer la cantidad de numeros solicitados: \n");
		scanf("%d",&num);
		//enviarlo al p2
		sprintf(mensaje,"%d",num);
		//cerrar descriptor lector
		close(df[0]);
		//escribimos
		write(df[1],mensaje,sizeof(mensaje));
		
		close(df[1]);
		wait(NULL);
		exit(0);
	
	}
}


