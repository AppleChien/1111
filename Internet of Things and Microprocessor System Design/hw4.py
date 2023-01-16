import RPi.GPIO as GPIO
import time
import serial

ser = serial.Serial('/dev/ttyAMA1', baudrate=9600,
parity=serial.PARITY_NONE,
stopbits=serial.STOPBITS_ONE,
bytesize=serial.EIGHTBITS
)

GPIO.setmode(GPIO.BOARD)

LED_PIN=12
GPIO.setup(LED_PIN, GPIO.OUT)
BUZZ_PIN = 16
pitches = [262, 294, 330, 349, 392, 440, 493]
# pitches = [262, 294, 330, 349, 392, 440, 493, 523, 587, 659, 698, 784, 880, 932, 988]
GPIO.setup(BUZZ_PIN, GPIO.OUT)

pwm = GPIO.PWM(BUZZ_PIN, pitches[0])
pwm.start(0)

pwm2 = GPIO.PWM(LED_PIN, 100)
pwm2.start(0)

bright=0

def play(bri):
    global bright
    bright+=bri
    if bright>100:
        bright=100
    elif bright<0:
        bright=0
    pwm2.ChangeCycle(int(bright))


try:
    while True:
        #pwm2.ChangeDutyCycle(int(bright))
        data = ser.readline()
        print(data.decode("utf-8").strip())
        ser.write(data)
        ser.flushInput()
        #time.sleep()

        if data.decode("utf-8").strip()=="play c":
            pwm.ChangeDutyCycle(50)
            pwm.ChangeFrequency(pitches[0])
        elif data.decode("utf-8").strip()=="play d":
            pwm.ChangeDutyCycle(50)
            pwm.ChangeFrequency(pitches[1])
        elif data.decode("utf-8").strip()=="play e":
            pwm.ChangeDutyCycle(50)
            pwm.ChangeFrequency(pitches[2])
        elif data.decode("utf-8").strip()=="play f":
            pwm.ChangeDutyCycle(50)
            pwm.ChangeFrequency(pitches[3])
        elif data.decode("utf-8").strip()=="play g":
            pwm.ChangeDutyCycle(50)
            pwm.ChangeFrequency(pitches[4])
        elif data.decode("utf-8").strip()=="play a":
            pwm.ChangeDutyCycle(50)
            pwm.ChangeFrequency(pitches[5])
        elif data.decode("utf-8").strip()=="play b":
            pwm.ChangeDutyCycle(50)
            pwm.ChangeFrequency(pitches[6])
        else:   
            pwm.stop()
            #brightness = input("Set brightness (0 ~ 100):")
            if not data.decode("utf-8").strip().isdigit() or int(data.decode("utf-8").strip()) > 100 or int(data.decode("utf-8").strip()) < 0:
                ser.write("Please enter an integer between 0 ~ 100.")
                continue      
            else:
                pwm2.ChangeDutyCycle(int(data))
        

except KeyboardInterrupt:
    pass
finally:
    ser.close()
pwm.stop()
GPIO.cleanup()