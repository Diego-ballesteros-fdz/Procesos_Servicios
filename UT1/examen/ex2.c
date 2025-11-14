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
		int numveces, df[2],df2[2],df3[2];//variable numveces y descriptores para los pipes
		char buffer[50];//mensaje a enviar por el pipe
		
		pipe(df);//abrimos el pipe1
		
		
		p2=fork();
		
		if(p2==-1){ 
		
			printf("error al crear p2\n");
		
		}else if(p2==0){//bloque p2
			
			pipe(df2);//abrimos el pipe2
			pipe(df3);//abrimos el pipe3
			
			p3=fork();
		
			if(p3==-1){ 
		
			printf("error al crear p3\n");
		
			}else if(p3==0){//bloque p3
			
				//codigo del p3
				//recibimos la información de p2
				close(df2[0]);
				read(df2[1],&numveces,sizeof(numveces));
				//creamos el array a recibir
				int numeros[numveces];
				
				//volvemos a leer esta vez el array
				read(df2[1],&numveces,sizeof(numveces));
				printf("%ls",&numveces);
				
				//una vez tenemos los numeros que queremos
				
			
			}else{//bloque p2
			
				//leemos del pipe1
				close(df[1]);
				read(df[0],&numveces,sizeof(numveces));
				//le pasamos numveces al p3 para que pueda saber cuantos numeros recibir en el futuro
				close(df2[0]);
				write(df2[1],&numveces,sizeof(numveces));
				//printf("%d\n",numveces);
				//ahora debemos leer numveces
				int numeros[numveces];
				for(int i=1;i>=numveces;i++){

					printf("Escriba un numero:\n");
					scanf("%ls",&numeros[i-1]);

				}
				//ahora debemos pasarselo a p3 por el pipe2
				write(df2[1],&numeros,sizeof(numeros));
			
		}
		
		}else{//bloque p1
		
			//comenzamos solicitando al usuario un numero
			printf("Indique la cantidad de numeros que desea:\n");
			scanf("%d",&numveces);
			//ahora lo enviamos al p2
			close(df[0]);//cerramos descriptor lector
			write(df[1],&numveces,sizeof(numveces));
			
			//esperamos a que su hijo acabe
			wait(NULL);
		
		}
	}
