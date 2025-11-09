//funciones usadas:

	//- obtener fecha y hora:
	time_t hora;
	char *fecha ;
	time(&hora);
	fecha = ctime(&hora);
	
	//- funciones de cadena de caracteres:
	
		//-dividimos la cadena formada por el padre
		scanf(buffer,"%d %d",&numero1,&numero2);
		//- Copiar cadena al lugar que ocupaba otra
		//si el origen es mas grande que el destino sobreescribiendo memoria
		strcpy(destino, origen);
		
		//-saber el tamaño de una cadena:
		int tamaño=strlen(cadena);
		
		//-comparar cadenas, devolvera 0 si las cadenas son iguales
		//si la primera es menore devolvera numero negativo y si es mayor positivo
		int iguales=strcmp(cadena1, cadena2);
		
	//-funciones de conversion:
		//-convierte de cadena a entero
		int num= atoi(cadena);
		
		//-convierte de entero acadena de caracteres:
		//tambien sirve para unir variables en una cadena
		//de esta forma unimos en un solo buffer toda la información
		sprintf(buffer_guardar,"cadena con %d,%s,%c",int,char *nombre,char);
		
	//-funcion de creación int aleatorio:
		time_t t;
		int numero1:
		srand((unsigned) time(&t));
		//Generamos numero aleatorio entre 1 y 50
		numero1=rand() % 50;
		
	//-lectura por pantalla:
		variable  = scanf("%formato",variable);
		variable  = fgets(variable, MAX_SIZE, stdin);//stdin canal por el que recibe
													 //max_size-1 siempre (40-1)
													 //variable de tipo char[]
		//formatos de tipos:
			%d	int 	Lee un entero decimal.
			%f	float 	Lee un número de punto flotante (float).
			%lf	double Lee un número de punto flotante de doble precisión (double).
			//todos los anteriores necesitas &
			%c	char 	Lee un carácter simple.
			%s	char   (array/puntero)	Lee una cadena de texto, hasta el espacio o salto de linea
			//estos no
			scanf(formato(%...),variable del tipo deseado);
		
	//-calcular letra de dni:
		int dni;
		char letra[] = "TRWAGMYFPDXBNJZSQVHLCKE";
		dni = “12345678”;
		dni %= 23;
		printf("%c",letra[dni]);
		
		
		
		
//comandos de consola útiles:

	//- para ver señales: kill -l
	//- para ver procesos de usuario: ps -u
	//- para matar pocesos kill -9 pidProceso
	//- para ver el manual de señales: man 7 signal
	
	
//includes:
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <sys/stat.h>
#include <signal.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>
	

//estructuras de creacion de procesos con control de errores:

	void main(){
		pid_t p1,p2,p3;
		
		p1=getpid();
		
		p2=fork();
		
		if(p2==-1){ 
		
			printf("error al crear p2\n");
		
		}else if(p2==0){//bloque p2
		
			//codigo que ejecuta p2
		
		}else{//bloque p1
		
			//codigo que ejecuta el p1
		
		}
	}

//estructura manejador señales:

	void sign_fun(int signal){

		if(signal==SEñAL){
			if(getpid()==p*){//sirve para usar pid_t globales
				//codigo que hara al recibir SEñAL
			}
		}else if(signal==SEñAL){
			if(getpid()==p*){//sirve para usar pid_t globales
				//codigo que hara al recibir SEñAL
			}
		}

		//reactivamos las señales
		signal(SEñAL,sig_fun);
	}
	
	//en el main para capturar cada una de las señales deseadas
	signal(SEñAL,sign_fun);
	
//estructura pipe anonimos:
//(En los pipes las funciones write y read son bloqueantes)
//(El mensaje debe ser String o char para enviarlo, si no usaremos la misma refiriendonos al puntero &variable)

	void main{
		pid_t p1,p2,p3;
		int df[2];
		char mensaje[30];//solo si es char[]
	
		//creación del pipe
		pipe(df);
		
		p1=getpid();
		
		//creación del hijo
		fork(p2);
		
		if(p2==-1){ 
		
			printf("error al crear p2\n");
		
		}else if(p2==0){//bloque p2
		
			//cerrar el descriptor escritor
			close(df[1]);
			//leer
			read(df[0],mensaje,sizeof(mensaje));
		
		}else{//bloque p1
		
			//cerrar descriptor lector
			close(df[0]);
			//escribimos
			write(df[1],mensaje,sizeof(mensaje));
			//o usar strlen(mensaje) + 1 en lugar de sizeof
		
		}
	}
	
//estructura pipe FIFO:
//(tambien se puede usar O_RDWR , esto permitira escribir y leer del mismo FIFO)
//(IMPORTANTE, aunque no se vaya a leer o escribir, si hemos creado un FIFO debemos
//hacer que todos los procesos lo abran, si no se bloqueara todo el programa)
	
	void main(){
		const char *fifo1 = "FIFO1";//declaramos el fifo
		const char *fifo2 = "FIFO2";
		
		//creamos el fifo
		mkfifo(fifo1, 0666);
		
		//abrimos el canal
		int fd1 = open(fifo1, O_WRONLY);
		
		if (fd1 == -1) {
			printf("escritor: error al abrir FIFO");
			exit(EXIT_FAILURE);
		}
		
		//pasamos la info
		if (write(fd1, &variable, sizeof(variable)) == -1) {
			
			//error al enviar
			printf("escritor: error al escribir");
			
		}	
		//abrir el fifo2 para poder leer en el receptor
		int fd2= open(fifo2,O_RDONLY);
		 if (read(fd2, &variable, sizeof(variable)) == -1) {
			printf("lector: error al leer");
			close(fd2);
			exit(EXIT_FAILURE);
		}
		
		//seguimos con el codigo
		
		//para acabar debemos cerrar el descriptor y eliminar el fifo creado
		close(fd1);
		unlink("FIFO1");
		
	}
		
	//codigo de p2 no familiar de p1
	void main(){
		const char *fifo1 = "FIFO1";
		const char *fifo2 = "FIFO2";
		
		mkdir ("FIFO2",0666);
		// Abrir FIFO para lectura (bloquea hasta que el escritor escriba)
		int fd1 = open(fifo1, O_RDONLY);
		if (fd1 == -1) {
			printf("lector: error al abrir FIFO1");
			exit(EXIT_FAILURE);
		}
		
		// Leer el número enviado
		if (read(fd1, &variable, sizeof(variable)) == -1) {
			printf("lector: error al leer");
			close(fd1);
			exit(EXIT_FAILURE);
		}
		
		//en este punto el fifo1 a funcionado
		printf("Número recibido: %d\n", numero);
		
		//ahora debemos mandar dicho factorial por el fifo2
		int fd2= open(fifo2,O_WRONLY);
		if (write(fd2,&factorial,sizeof(factorial))== -1){
		
			printf("escritor: error al escribir");
			close(fd2);
			exit(EXIT_FAILURE);
	
		}
		
		//para acabar debemos cerrar el descriptor y eliminar el fifo creado
		close(fd2);
		unlink("FIFO2");
		
	}
		
		
		
		
	
