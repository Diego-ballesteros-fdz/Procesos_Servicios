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
	int fd1[2],fd2[2];
	int operando,num1,num2,total;
	char mensaje[30];
	
	pipe(fd1);
	pipe(fd2);
	
	p2=fork();
	
	if(p2==-1){ 
	
		printf("error al crear p2\n");
	
	}else if(p2==0){//bloque p2
	
		p3=fork();
	
		if(p3==-1){ 
		
			printf("error al crear p2\n");
		
		}else if(p3==0){//bloque p3
		
			close(fd2[1]);
			read(fd2[0],&total,sizeof(total));
			
			//printf("p3 recibe: %d",total);
			//calculamos si es par o impar
			printf("Proceso p2 pid = %d - Proceso padre pid = %d\n",getpid(),getppid());
			if(total%2==0){
				printf("El resultado de la operacion %d es par\n",total);
			}else{
				printf("El resultado de la operacion %d es impar\n",total);
			}
			printf("El proceso P3 termina\n");
			exit(0);
		
		}else{//bloque p2
		
			close(fd1[1]);
			read(fd1[0],mensaje,sizeof(mensaje));
			
			//printf("p2: %s",mensaje);
			//ahora debemos dividir el mensaje y realizar la operacion
			sscanf(mensaje,"%d,%d,%d",&operando,&num1,&num2);
			
			
			//printf("total: %d",total);
			//mostramos los datos
			printf("Proceso P2 pid = %d - Proceso padre %d\n",getpid(),getppid());
			total = calcularOperacion(operando,num1,num2);
			close(fd2[0]);
			write(fd2[1],&total,sizeof(total));

			wait(NULL);
			printf("El proceso P2 termina\n");
			exit(0);
		}
	
	}else{//bloque p1
		
		
		do{
		
		printf("Proceso p1 PID = %d\n",getpid());
		printf("**CALCULADORA**\n");
		printf("1. Sumar \n2. Restar \n3.Multiplicar \n4. División \nSeleccione la opción deseada: \n");
		scanf("%d",&operando);
		
		if(operando>0 && operando<5){
		
			//preguntamos operadores
			printf("Introduce el primer operando: \n");
			scanf("%d",&num1);
			printf("Introduce el segundo operando: \n");
			scanf("%d",&num2);
		
		}else{
		
			printf("Debe introducir un operador válido\n");
		
		}
		
		}while(operando<1 || operando>4);
		
		//ahora debemos pasarle la información al p2
		close(fd1[0]);
		
		sprintf(mensaje,"%d,%d,%d",operando,num1,num2);
		
		//printf("%s",mensaje);
		
		write(fd1[1],mensaje,sizeof(mensaje));
				
		wait(NULL);
		printf("El proceso P1 termina\n");
		exit(0);
	
	}
}

int calcularOperacion(int operando,int num1, int num2){
	
	int total=0;
	
	switch(operando){
		case 1:
			total=num1+num2;
			printf("Operacion %d + %d = %d\n",num1,num2,total);
			break;
		case 2:
			total=num1-num2;
			printf("Operacion %d - %d = %d\n",num1,num2,total);
			break;
		case 3:
			total=num1*num2;
			printf("Operacion %d * %d = %d\n",num1,num2,total);
			break;
		case 4:
			total=num1/num2;
			printf("Operacion %d / %d = %d\n",num1,num2,total);
			break;			
	}
	return total;
}
