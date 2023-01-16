import requests
import random
import time
import sys
import RPi.GPIO as GPIO

GPIO.setmode(GPIO.BOARD)
BTN_PIN = 11
GPIO.setup(BTN_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)
previousStatus = None
temp=1

'''
global variables
'''

ENDPOINT = "industrial.api.ubidots.com"
DEVICE_LABEL = "weather-station"
VARIABLE_LABEL = "led"
TOKEN = "BBFF-9OTFpyKlLcZj7A6s8ekpyNU3sFdb5e" # replace with your TOKEN
DELAY = 0.1  # Delay in seconds


def post_var(payload, url=ENDPOINT, device=DEVICE_LABEL, token=TOKEN):
    try:
        url = "http://{}/api/v1.6/devices/{}".format(url, device)
        headers = {"X-Auth-Token": token, "Content-Type": "application/json"}

        attempts = 0
        status_code = 400

        while status_code >= 400 and attempts < 5:
            
            print("[INFO] Sending data, attempt number: {}".format(payload))
            req = requests.post(url=url, headers=headers,
                                json=payload)
            status_code = req.status_code
            attempts += 1
            time.sleep(0.5)

        print("[INFO] Results:")
        print(req.text)
    except Exception as e:
        print("[ERROR] Error posting, details: {}".format(e))
    

def main():
    # Simulates sensor values
    
    #sensor_value = random.random() * 100

    # Builds Payload and top穩c
    payload = {VARIABLE_LABEL: temp}

    # Sends data
    post_var(payload)


if __name__ == "__main__":
    if TOKEN == "...":
        print("Error: replace the TOKEN string with your API Credentials.")
        sys.exit()
    try:
        while True:
            input = GPIO.input(BTN_PIN)
            if input == GPIO.LOW and previousStatus == GPIO.HIGH:
                print("Button pressed ",temp," @", time.ctime())
                if temp == 0:
                    temp=1
                elif temp==1:
                    temp=0
            previousStatus = input
            main()
            time.sleep(DELAY)
    except KeyboardInterrupt:
        print("Exception: KeyboardInterrupt")
    finally:
        GPIO.cleanup()
        
        