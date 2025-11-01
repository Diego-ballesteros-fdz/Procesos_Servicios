#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>

//Funcion manejadora de las señales
void fun_signal(int signal){
	
	//capturaremos la señal sigint
	
	
	
	
	}


//metodo main
int main(){
	//declaración de variables
	pid_t p2,p3;//pid para los hijos.
	int df1[2],df2[2]//descriptor para los pipe anonimos.
	int num;//numero que debemos pasar
	char buffer[30];//buffer de almacenaje del pipe
	
	// Esta línea registra la función manejadora para la señal SIGINT. 
	//A partir de aquí, si se pulsa Ctrl+C, se ejecutará fun_signal(), en todos los procesos.
	signal(SIGINT,fun_signal);
	
	//creamos el pipe1
	pipe(df1); 
	
	//creamos el primer hijo
	p2=fork();
	
	if(p2!=0){//proceso p1
		//cerramos el descriptor en modo lectura
		close(df1[0]);
		
		
		//creación del p3 hijo de p1
		p3=fork();
		
		if(p3!=0 && p2=!0){//proceso p1
			
			
		}else{//proceso p3
			
			
		}
		
		
	}else{//proceso p2
		
		
		
	}
	
	
	
	
	return 0;
	}
