import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
BTN_PIN = 7
WAIT_TIME = 0.2
GPIO.setup(BTN_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
previousStatus = None

LED_PINS = [13, 7]
GPIO.setmode(GPIO.BOARD)
GPIO.setup(LED_PINS[0], GPIO.OUT)
GPIO.setup(LED_PINS[1], GPIO.OUT)

count = 1
def ButtonPressed(btn):
	global count
	count += 1
	if count == 4:
		count = 1

GPIO.setmode(GPIO.BOARD)
BTN_PIN = 15
GPIO.setup(BTN_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
GPIO.add_event_detect(BTN_PIN, GPIO.FALLING, ButtonPressed, 200)

try:
	while True:
		if count == 1:
			GPIO.output(LED_PINS[0], GPIO.HIGH)
			GPIO.output(LED_PINS[1], GPIO.HIGH)
		elif count == 2:
			GPIO.output(LED_PINS[0], GPIO.LOW)
			GPIO.output(LED_PINS[1], GPIO.LOW)
		elif count == 3:
			counter = 1
			while True:
				if counter == 1:
					GPIO.output(LED_PINS[0], GPIO.HIGH)
					GPIO.output(LED_PINS[1], GPIO.LOW)
					counter += 1
					time.sleep(1)
				else:
					GPIO.output(LED_PINS[1], GPIO.HIGH)
					GPIO.output(LED_PINS[0], GPIO.LOW)
					counter = 1
					time.sleep(1)
    
				if count != 3:
					break
except KeyboardInterrupt:
	print("Exception: KeyboardInterrupt")
finally:
	GPIO.cleanup()