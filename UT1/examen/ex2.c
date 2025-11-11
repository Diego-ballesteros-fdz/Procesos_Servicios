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
			
			p3=fork();
		
			if(p3==-1){ 
		
			printf("error al crear p3\n");
		
			}else if(p3==0){//bloque p3
			
				//codigo del p3
			
			}else{//bloque p2
			
				//leemos del pipe1
				close(df[1]);
				read(df[0],&numveces,sizeof(numveces));
				printf("%d\n",numveces);
			
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
