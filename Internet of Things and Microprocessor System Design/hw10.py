import speech_recognition as sr
from gtts import gTTS
import os
import RPi.GPIO as GPIO
import time
LED_PIN = 32
GPIO.setmode(GPIO.BOARD)
GPIO.setup(LED_PIN, GPIO.OUT)
#obtain audio from the microphone


# recognize speech using Google Speech Recognition
try:
    while True:
        r=sr.Recognizer()
        with sr.Microphone(device_index = 2, sample_rate = 48000) as source:
            print("Please wait. Calibrating microphone...")
            r.energy_threshold = 4000
            print("Say something!")
            audio=r.listen(source)

        print("Google Speech Recognition thinks you said:")
        results = r.recognize_google(audio, language='zh-TW')        
        #results 字串
        if results.find('開燈') != -1:
            GPIO.output(LED_PIN, GPIO.HIGH)
            tts = gTTS(text='燈已開啟', lang='zh-TW')
            tts.save('open_tk.mp3')
            os.system('omxplayer -o local -p open_tk.mp3 > /dev/null 2>&1')
        elif results.find('關燈')  != -1:
            GPIO.output(LED_PIN, GPIO.LOW)
            tts = gTTS(text='燈已關閉', lang='zh-TW')
            tts.save('close_tk.mp3')
            os.system('omxplayer -o local -p close_tk.mp3 > /dev/null 2>&1')

        #results = r.recognize_google(audio, language='en-US')
        print(results)
        time.sleep(0.5)
except sr.UnknownValueError:
    print("Google Speech Recognition could not understand audio")
except sr.RequestError as e:
    print("No response from Google Speech Recognition service: {0}".format(e))
    