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
		//descriptores para los pipes
		int df1[2],df2[2],df3[2];
		//buffer del mensaje
		char buffer[100];
		//creacion del pipe1
		pipe(df1);
		//creacion de p2
		p2=fork();
		
		if(p2==-1){//error
		
			printf("error al crear p2\n");
		
		}else if(p2==0){//bloque p2
			
			//creacion de pipe2 y pipe3
			pipe(df2);
			pipe(df3);
		
			p3=fork();
		
			if(p3==-1){ //error
			
				printf("error al crear p3\n");
			
			}else if(p3==0){//bloque p3
				
				//bloque de ejecución p3
				//recibe datos de p2
				close(df2[1]);
				
				//leemos
				read(df2[0],buffer,sizeof(buffer));
					
				//printf("leemos %s",buffer);
				
				//dividimos
				int nume1=0,nume2=0,nume3=0,nume4=0,nume5=0,nume6=0,nume7=0,numeros[7];
				
				// se bloque aqui, la comento para que al menos veas el resto de cosas pedidas. se enviara todo con cero
				sscanf(buffer,"%d %d %d %d %d %d %d",&nume1,&nume2,&nume3,&nume4,&nume5,&nume6,&nume7); //se bloquea aqui!!
					
					
				numeros[0]=nume1;
				numeros[1]=nume2;
				numeros[2]=nume3;
				numeros[3]=nume4;
				numeros[4]=nume5;
				numeros[5]=nume6;
				numeros[6]=nume7;
				
				int contador,suma;
				
				
				//verificamos el multiplo de 5
				for(int i=0;i<7;i++){
					
					if(numeros[i]%5==0){
						suma+=numeros[i];
					}else{
						contador++;
					}
					
				}	
				
				//enviamos al proceso p2 los datos obtenidos
				//creamos el mensaje
				sprintf(buffer,"%d %d",contador,suma);
				//cerramos el de lectura
				close(df3[0]);
				
				
				write(df3[1],buffer,sizeof(buffer));
				
				
			
			}else{//bloque p2
			
				//codigo que ejecuta el p2
				int numeroVeces,numero;
				
				//debemos leer el pipe
				//cerramos el descriptor de escritura
				close(df1[1]);
				
				//leemos
				read(df1[0],buffer,sizeof(buffer));
				
				//transformamos el mensaje a entero
				numeroVeces=atoi(buffer);
				printf("p2 lee %d \n",numeroVeces);
				
				//creamos un array con el numeroVeces de tamaño
				int numeros[numeroVeces];
				
				//leemos numeroveces al usuario
				for(int i=1;i<=numeroVeces;i++){
					
					printf("Introduce número: \n");
					scanf("%d",&numero);
					//almacenamos en array numeros
					numeros[i-1]=numero;
				}
				//pasamos a numeros individuales
				int num1=-1,num2=-1,num3=-1,num4=-1,num5=-1,num6=-1,num7=-1;
				num1=numeros[0];
				num2=numeros[1];
				num3=numeros[2];
				num4=numeros[3];
				num5=numeros[4];
				num6=numeros[5];
				num7=numeros[6];
				//escribimos en el pipe2 los datos.
				//pasamos a string los numeros
				sprintf(buffer,"%d %d %d %d %d %d %d",num1,num2,num3,num4,num5,num6,num7);
				//cerramos descriptor de lectura
				close(df2[0]);
				
				//printf("P2 escribe %s\n",buffer);
				
				//escribimos en el pipe2
				write(df2[1],buffer,sizeof(buffer));
				
				//en este punto debemos recibir la info gestionada de p3
				close(df3[1]);
				
				read(df3[0],buffer,sizeof(buffer));
				printf("%s",buffer);
				
				
				
				int contador,sum;
				
				sscanf(buffer,"%d %d",&contador,&sum);
				
				//escribimos por pantalla
				printf("La suma de los múltiplos de 5 es igual a = %d\n",sum);
				printf("Se han procesado %d números no múltiplos de 5\n", contador);
				
				
				wait(NULL);
			
			}
		
		}else{//bloque p1
		
			//codigo que ejecuta el p1
			//leemos el numero de veces que debemos leer un numero en el p2
			int numeroVeces;
			printf("Introduce la cantidad de números a procesar: \n");
			scanf("%d",&numeroVeces);
						//printf("p1 recoge %d",numeroVeces);
			
			//ahora debemos enviar numeroveces por el pipe
			//pasamos el numero a string
			sprintf(buffer,"%d",numeroVeces);
						//printf("p1 envia %s",buffer);
			
			//cerramos el de lectura
			close(df1[0]);
			
			//escribimos en el pipe1
			write(df1[1],buffer,sizeof(buffer));
			
			//esperamos a que el hijo p2 acabe, es importante esperar ya que con el write solo ->
			//->esperara a ser leido no a acabar su ejecución
			wait(NULL);
		
		}
	}
