#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>
#include <time.h>
#include <string.h>


void main(){

	int df1[2],df2[2],numeroDNI;
	char buffer1[30];
	pid_t P2;
	char numero[20],letraFinal,letra[] = "TRWAGMYFPDXBNJZSQVHLCKE";

	pipe(df1);
	pipe(df2);
	P2=fork();
	
	if(P2==0){//P2
		
		close(df1[0]);
		close(df2[1]);
		printf("Introduce el número de tu DNI: ");
		scanf("%8s",numero);//verificamos que sea un numero de 8 caracteres y no usamos & porque numero ya es un puntero en sí
		write(df1[1],numero,sizeof(numero));
		//recibimos el mensaje de pipe2
		read(df2[0],&letraFinal,sizeof(letraFinal));
		printf("la letra del NIF es %c\n",letraFinal);
		close(df1[1]);
		close(df2[0]);
	
	}else{//P1
		
		close(df1[1]);//cerramos el pipe escritor del pipe1
		close(df2[0]);//cerramos el pipe lector del pipe2
		read(df1[0],buffer1,sizeof(buffer1));
		numeroDNI=atoi(buffer1);//casteamos a int
		numeroDNI%=23;
		letraFinal=letra[numeroDNI];
		write(df2[1],&letraFinal,sizeof(letraFinal));
		wait(NULL);
		close(df1[0]);
		close(df2[1]);
	
	}

	exit(0);
}
