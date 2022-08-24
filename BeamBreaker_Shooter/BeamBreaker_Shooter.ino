#define BEAMBREAKER1 2
#define BEAMBREAKER2 BEAMBREAKER1+1

#define BALLDIAMETER 9.5
#define INCHESPERMILLISECONDTOMETERPERSECOND 25.4
volatile bool flag = 0;
bool counter1 = 0;
bool counter2 = 0;
unsigned long timer = 0;
int timePassed = 0;
double velocity = 0;

void setup() {
  Serial.begin(115200);
  attachInterrupt(digitalPinToInterrupt(BEAMBREAKER1), isr1, RISING);
  attachInterrupt(digitalPinToInterrupt(BEAMBREAKER2), isr2, FALLING);

}

void loop() {
  if (flag && !counter1) {
    counter1 = 1;
    counter2 = 0;
    timer = millis();
  }
  else if (!flag && !counter2) {
    counter2 = 1;
    counter1 = 0;
    timePassed = (millis()) - (timer);
    velocity = (BALLDIAMETER) / (timePassed);
    velocity =  INCHESPERMILLISECONDTOMETERPERSECOND * velocity;
    if (velocity < 100) {
      Serial.println(velocity);
    }
  }
  //  if (Serial.available()) {
  //    if (Serial.read() == 'g') {
  //      Serial.print(velocity);
  //    }
  //  }

}


void isr1() {
  flag = 1;
}

void isr2() {
  flag = 0;
}




    // public double rateLimiter(double current, double prev, double max){
    //     double delta = current-prev;
        
    //     if(delta > max){
    //         delta = max;
    //     }
    //     else if(delta < -max){
    //         delta = -max;
    //     }
        
    //     return (prev + delta);
    // }


                // turn = Math.pow(turn, 1.72);
            // turn = turn * 0.7;
            
            // throttle = Math.pow(throttle, 1.72);
            
            // throttle = rateLimiter(throttle, prevThrottle, 0.5);
            // turn = rateLimiter(turn, prevTurn, 0.5);  
