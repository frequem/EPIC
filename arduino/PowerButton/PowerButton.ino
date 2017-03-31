#include "LowPower.h"

const int NFET_DISPLAY = 5;
const int NFET_RASPBERRY = 7;
const int NPN_SHUTDOWN_OUT = 4;
const int NPN_RPI_POWER_IN = 3;
const int BUTTON_POWER = 2;

const int STATE_ASLEEP = 0;
const int STATE_SHUTDOWN = 1;

const int STATE_AWAKE = 2;
const int STATE_SHUTDOWN_READY = 3;

const int TIMER_STEP = 20; //delay time when awake
const int TIME_TURNOFF = 3000; //time the button needs to be pressed to turnoff the pi

int state = STATE_ASLEEP;
int timer = 0;

int state_display = LOW;
int state_pi = LOW;

void button_pressed();
void rpi_down();

void setup() {
  pinMode(NFET_DISPLAY, OUTPUT);
  pinMode(NFET_RASPBERRY, OUTPUT);
  pinMode(NPN_SHUTDOWN_OUT, OUTPUT);
  
  pinMode(BUTTON_POWER, INPUT);
  pinMode(NPN_RPI_POWER_IN, INPUT);
  
  attachInterrupt(digitalPinToInterrupt(BUTTON_POWER), button_pressed, RISING);
  attachInterrupt(digitalPinToInterrupt(NPN_RPI_POWER_IN), rpi_down, FALLING);

  digitalWrite(NFET_DISPLAY, state_display);
  digitalWrite(NFET_RASPBERRY, state_pi);
  digitalWrite(NPN_SHUTDOWN_OUT, LOW);
}

void loop() {
  if(state >= STATE_AWAKE){
    if(state == STATE_SHUTDOWN_READY){ //turn off power for display and raspberry
      state_display = LOW;
      state_pi = LOW;
      digitalWrite(NPN_SHUTDOWN_OUT, LOW);
      digitalWrite(NFET_RASPBERRY, state_pi);
      digitalWrite(NFET_DISPLAY, state_display);
      state = STATE_ASLEEP;
      return;
    }
    if(timer < TIME_TURNOFF && digitalRead(BUTTON_POWER) == LOW){ //button was released in time, toggle display if pi is on
      if(state_pi == HIGH){
        state_display = state_display ^ 1;
        digitalWrite(NFET_DISPLAY, state_display);
      }
      state = STATE_ASLEEP;
    }else if(digitalRead(BUTTON_POWER) == LOW){ //button was released after turnoff time
      if(state_pi == HIGH){
        digitalWrite(NPN_SHUTDOWN_OUT, HIGH);
        state = STATE_SHUTDOWN;
      }else{
        state_pi = HIGH;
        state_display = HIGH;
        digitalWrite(NFET_RASPBERRY, state_pi);
        digitalWrite(NFET_DISPLAY, state_display);
        state = STATE_ASLEEP;
      }
    }
    delay(TIMER_STEP);
    timer+=TIMER_STEP;
  }else{
    LowPower.powerDown(SLEEP_FOREVER, ADC_OFF, BOD_OFF); 
  }  
}

void button_pressed(){
  if(state == STATE_ASLEEP){
    state = STATE_AWAKE;
    timer = 0;
  }
}

void rpi_down(){
  if(state == STATE_SHUTDOWN){
    state = STATE_SHUTDOWN_READY;
    timer = 0;
  }
}

