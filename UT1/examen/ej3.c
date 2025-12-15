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
		int df[2], df1[2], df2[2],df3[2], num,total;
		char mensaje[30];//solo si es char[]
	
		//creación del pipe
		pipe(df);
		pipe(df1);
		pipe(df2);
		pipe(df3);
		
		p1=getpid();
		
		//creación del hijo
		p2=fork();
		
		if(p2==-1){ 
		
			printf("error al crear p2\n");
		
		}else if(p2==0){//bloque p2
		
			//cerrar el descriptor escritor
			close(df[1]);
			//leer
			read(df[0],mensaje,sizeof(mensaje));
			
			//printf("mensaje recibido por p2: %s\n",mensaje);
			
			
			//calculamos el factorial
			total=calcularFactorial(atoi(mensaje));
			//se lo pasamos a p1
			close(df1[0]);
			//printf("%d",total);
			
			sprintf(mensaje,"%d",total);
			
			//printf("%s",mensaje);
			
			write(df1[1],mensaje,sizeof(mensaje));
			
			close(df2[1]);
			//leemos del padre p2
			int n=read(df2[0],mensaje,strlen(mensaje)+1);
			if(!n<=0){
					
				printf("El factorial es par: %s\n",mensaje);
				
			}
			exit(0);
			
		
		}else{//bloque p1
			p3=fork();
			
			if(p3==-1){ 
			
				printf("error al crear p2\n");
			
			}else if(p3==0){//bloque p3
			
				//cerrar el descriptor escritor
				close(df3[1]);
				//leer
				int n=read(df3[0],mensaje,strlen(mensaje)+1);
				
				if(!n<=0){
					
					printf("El factorial es impar: %s\n",mensaje);
				
				}
				
				exit(0);
			
			}else{//bloque p1
				
				printf("Introduce un numero: \n");
				scanf("%d",&num);
				
				sprintf(mensaje,"%d",num);			
				//cerrar descriptor lector
				close(df[0]);
				//escribimos
				write(df[1],mensaje,sizeof(mensaje));
				
				close(df1[1]);
			
				read(df1[0],mensaje,sizeof(mensaje));
				
				//printf("factorial: %s\n",mensaje);
				total=atoi(mensaje);
				
				//verificamos si es par o impar
				if(total%2==0){
					
					//es par lo mandamos al p2
					close(df2[0]);
					sprintf(mensaje,"%d", total);
					write(df2[1],mensaje,sizeof(mensaje));
					
				}else{
					
					//es impar lo mandamos al p3
					sprintf(mensaje,"%d", total);
					close(df3[0]);
					write(df3[1],mensaje,sizeof(mensaje));
					
				}
				
				
				wait(NULL);
				exit(0);
			
			}		
		}
	}
	
	int calcularFactorial(int num){
		int total=1;
		
		for(int i=1;i<=num;i++){
			
			total*= i;
			
		}
		return total;
		}
