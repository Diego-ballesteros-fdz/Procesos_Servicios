#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>

//funcion para manejar las operaciones
int operacion(char operador,int num1,int num2){
	int resultado=0;
	switch(operador){
		case '+':
		resultado=num1+num2;
		printf("operacion: %d %c %d = %d\n",num1,operador,num2,resultado);
		return resultado;
		case '-':
		resultado=num1-num2;
		printf("operacion: %d %c %d = %d\n",num1,operador,num2,resultado);
		return resultado;
		case '*':
		resultado=num1*num2;
		printf("operacion: %d %c %d = %d\n",num1,operador,num2,resultado);
		return resultado;
		case '/':
		resultado=num1/num2;
		printf("operacion: %d %c %d = %d\n",num1,operador,num2,resultado);
		return resultado;
		default:
		printf("no se pudo realizar la operación\n");
		return 0;
	}
}

void main(){
	
	pid_t p1=getpid(),p2,p3;//pid_t de los procesos para usar fork()
	int df1[2],df2[2];//defaultfile para los pipe1 y pipe2
	char buffer[30];//buffer del pipe
	
	char operador;
	int num1,num2,resultado,seleccion;
	
	//creamos el pipe
	pipe(df1);
	
	//hacemos el hijo p2
	p2=fork();
	
	if(p2!=0){//bloque p1
		//cerramos el descriptor de lectura
		close(df1[0]);
		//realizamos el formulario solicitado
		do{
		printf("Proceso P1 pid=%d\n",p1);
		printf("** CALCULADORA **\n");
		printf("1-Sumar.\n");
		printf("2-Restar.\n");
		printf("3-Multiplicar.\n");
		printf("4-División.\n");
		printf("Seleccione la opción deseada: \n");
		scanf("%d2",&seleccion);
		switch(seleccion){
			case 1:
			operador='+';
			break;
			case 2:
			operador='-';
			break;
			case 3:
			operador='*';
			break;
			case 4:
			operador='/';
			break;
			default:
			printf("Valor no valido, debe ser entre 1 y 4.\n");
			break;
		}
	}while (seleccion < 1 || seleccion > 4);
		//tras esto solicitamos los numeros
		printf("Introduce el primer operador:\n");
		scanf("%d",&num1);
		printf("Introduce el segundo operador:\n");
		scanf("%d",&num2);
		//juntamos los tres datos en un char[] para poder enviarlo
		sprintf(buffer,"%d%c%d",num1,operador,num2);
		//escribimos el mensaje y nos quedamos esperando a que p2, el hijo acabe		
		write(df1[1],&buffer,sizeof(buffer));
		wait(NULL);
		//terminamos el P1
		printf("Termina proceso P1.\n");
		exit(0);
	}else{//bloque p2
		//creamos el pipe2
		pipe(df2);
		//hacemos el hijo p3
		p3=fork();
		
		if(p3!=0){//p2
			//cerramos el descriptor de escritura para el pipe 1
			close(df1[1]);
			read(df1[0],buffer,sizeof(buffer));
			//transformamos a los distintos tipos
			sscanf(buffer, "%d%c%d", &num1, &operador, &num2);
			//escribimos el mensaje y llamamos a la funcion operacion()
			printf("Proceso P2 pid = %d-Proceso padre pid = %d\n",getpid(),p1);
			resultado=operacion(operador,num1,num2);
			//cerramos el descriptor de lectura y enviamos la información a p3
			close(df2[0]);
			write(df2[1],&resultado,sizeof(resultado));
			//esperamos a que su hijo acabe, el write paraliza pero al ser leido se continua.
			wait(NULL);
			//terminamos el proceso
			printf("Termina proceso P2.\n");
			exit(0);
			
		}else{//p3
			//cerramos el descriptor de escritura
			close(df2[1]);
			//leemos el pipe
			read(df2[0],&resultado,sizeof(resultado));			
			
			printf("Proceso P3 pid = %d-Proceso padre pid = %d\n",getpid(),p2);
			//revisamos si resultado es par o no
			if(resultado%2==0){
				printf("El resultado de la operación %d es par.\n",resultado);
			}else{
				printf("El resultado de la operación %d es impar.\n",resultado);
			}
			printf("Termina proceso P3.\n");
			exit(0);
		}
		
	}
	
}
