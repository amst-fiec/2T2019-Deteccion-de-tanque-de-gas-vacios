
int pin = 8;
int minimo = 430;
int maximo = 440;
int contador_inferior=0;
int contador_superior=0;
int contador_bateria=0;
int azul = 5;
int rojo = 4;
float voltaje =0 ;
void setup(){
  pinMode(azul,OUTPUT);
  pinMode(rojo,OUTPUT);
  pinMode(pin, OUTPUT);
  Serial.begin(9600);
  digitalWrite(azul,HIGH);
}

void loop() {
  int presion = analogRead(A0);
  delay(1000);
  contador_bateria++;
  Serial.println(presion);
  if (contador_inferior ==0){
    if(presion <= minimo){
      enviar();
      contador_inferior=1;
      contador_superior=0;
    }
  }
  if (contador_superior ==0){
    if(presion >= maximo){
      enviar();
      contador_inferior=0;
      contador_superior=1;
    }
  }
  if(contador_bateria>600){ // 600 segundos igual a 10 minutos
     voltaje = analogRead(A1);
     if(voltaje < 307){
          digitalWrite(rojo,HIGH);
          digitalWrite(azul,LOW);
     }
     else{
      digitalWrite(azul,HIGH);
      digitalWrite(rojo,LOW);
     }
     contador_bateria =0; // se vuelve a encerar el contador 
  }
}

void enviar(){
   digitalWrite(pin, HIGH);
   delay(3000);
   digitalWrite(pin, LOW);
   Serial.println("Se activo el ibutton");
}
