#include<stdio.h>
#include<unistd.h>
#include<signal.h>

int tiempo=5;//es lo que se pedia?

void captor(int signum){
  printf("Han pasado %d segundos.\n",tiempo);
  tiempo+=5;
  alarm(5);
}


int main(){
  signal(SIGALRM,captor); 
  alarm(5);
  for(int i=1;;i++){
	  
    sleep(1); 
    
  }
  return 0;
}
