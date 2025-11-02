#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>
#include <time.h>


//variables globales
pid_t p2,p3;//pid para los hijos.

//Funcion manejadora de las señales
void fun_signal(int signal){
	//capturaremos la señal sigint
	if(signal==SIGINT){//PARA ACABAR PROCESOS
		if(p2!=0 && p3!=0){//proceso p1
			//mandamos la señal a p2 y p3
			kill(p2,SIGINT);
			kill(p3,SIGINT);
			//esperamso a que terminen los hijos
			wait(NULL);
			wait(NULL);
			//salimos
			printf("deteniendo proceso p1\n");
			exit(0);
		}else{
			if(p2==0){
				printf("deteniendo proceso p2\n");
				exit(0);
			}else{
				printf("deteniendo proceso p3\n");
				exit(0);
			}
		}
	}
}



//metodo main
void main(){
	//declaración de variables
	int df1[2],df2[2];//descriptor para los pipe anonimos.
	int num;//numero que debemos pasar
	char numero[30];//numero en String
	char buffer[30];//buffer de almacenaje del pipe
	//variables para el random
	time_t t;
	srand((unsigned) time(&t));
	
	// Esta línea registra la función manejadora para la señal SIGINT. 
	//A partir de aquí, si se pulsa Ctrl+C, se ejecutará fun_signal(), en todos los procesos.
	signal(SIGINT,fun_signal);
	
	//creamos el pipe1
	pipe(df1); 
	
	//creamos el primer hijo
	p2=fork();
	
	if(p2==0){//proceso p2
	
		//creamos el numero aleatorio para enviarlo, maximo 10 minimo 1
		num=rand() % 10 +1;
		
		//convertimos a string
		sprintf(numero,"%d",num);
		
		//cerramos el descriptor en modo lectura
		close(df1[0]);
		
		//escribimos en el pipe, en este punto se para el proceso hasta que lea el lector
		write(df1[1],numero,sizeof(numero));
		
		//se queda esperando la señal que mandara el padre
		while(1){
			pause();
		}
		
		
	}else{//proceso p1
		
		//cerramos el descriptor para escritura
		close(df1[1]);
		
		//leemos el pipe
		read(df1[0],numero,sizeof(numero));
		
		//convertimos a num
		num= atoi(numero);
		
		printf("Soy el proceso padre p1 y he recibido el numero: %d\n",num);
		
		//creación pipe2
		pipe(df2);

		//creación del p3 hijo de p1
		p3=fork();
		
		if(p3==0){//proceso p3
			//cerramos el descriptor df1 y el de escritura de df2
			close(df1[0]);
			close(df1[1]);
			close(df2[1]);
			
			//recibimos numero
			read(df2[0],numero,sizeof(numero));
			
			//convertimos a num
			num= atoi(numero);
			
			//creamos n lineas por el num recibido
			for(int i=1;i<=num;i++){
				printf("%d\n",i);
			}
			
			//se queda esperando la señal que mandara el padre
			while(1){
				pause();
			}
			
			
		}else{
			//cerramos descriptor de escritura
			close(df2[0]);
			
			//escribirmos en el pipe2 usando la variable char[]
			write(df2[1],numero,sizeof(numero));
			
			//se queda esperando la señal
			while(1){
				pause();
			}
		}
	}
}
