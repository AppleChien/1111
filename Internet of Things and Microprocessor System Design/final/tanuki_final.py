from gtts import gTTS
import RPi.GPIO as GPIO
import time
import os
import speech_recognition as sr
import threading
import subprocess
import sqlite3
from tokenize import String
import datetime
import random
#from datetime import datetime,timezone,timedelta
import requests
import json
from flask import Flask, render_template, Response
#import cv2

#ahoy
import cv2
import numpy as np
import os
recognizer = cv2.face.LBPHFaceRecognizer_create()
recognizer.read('tanuki_face2_train.yml')
cascadePath = "shu772.xml"
faceCascade = cv2.CascadeClassifier(cascadePath);
#

GPIO.setwarnings(False)

#obtain audio from the microphone
r=sr.Recognizer()

Motor_R1_Pin = 16
Motor_R2_Pin = 18
Motor_L1_Pin = 11
Motor_L2_Pin = 13
t = 0.5
aa = 2
voll = "-2000"

app = Flask(__name__)

GPIO.setmode(GPIO.BOARD)
GPIO.setup(Motor_R1_Pin, GPIO.OUT, initial=GPIO.LOW)
GPIO.setup(Motor_R2_Pin, GPIO.OUT, initial=GPIO.LOW)
GPIO.setup(Motor_L1_Pin, GPIO.OUT, initial=GPIO.LOW)
GPIO.setup(Motor_L2_Pin, GPIO.OUT, initial=GPIO.LOW)


def stop():
    GPIO.output(Motor_R1_Pin, False)
    GPIO.output(Motor_R2_Pin, False)
    GPIO.output(Motor_L1_Pin, False)
    GPIO.output(Motor_L2_Pin, False)


def forward():
    GPIO.output(Motor_R1_Pin, True)
    GPIO.output(Motor_R2_Pin, False)
    GPIO.output(Motor_L1_Pin, True)
    GPIO.output(Motor_L2_Pin, False)
    time.sleep(aa)
    stop()


def backward():
    GPIO.output(Motor_R1_Pin, False)
    GPIO.output(Motor_R2_Pin, True)
    GPIO.output(Motor_L1_Pin, False)
    GPIO.output(Motor_L2_Pin, True)
    time.sleep(aa)
    stop()


def turnRight():
    GPIO.output(Motor_R1_Pin, False)
    GPIO.output(Motor_R2_Pin, False)
    GPIO.output(Motor_L1_Pin, True)
    GPIO.output(Motor_L2_Pin, False)
    time.sleep(t)
    stop()

def turnLeft():
    GPIO.output(Motor_R1_Pin, True)
    GPIO.output(Motor_R2_Pin, False)
    GPIO.output(Motor_L1_Pin, False)
    GPIO.output(Motor_L2_Pin, False)
    time.sleep(t)
    stop()

@app.route('/')
def index():
    return "Hello World"

if __name__ == "__main__":

    print("\n\n開始執行:\n")

    while True:

        with sr.Microphone() as source:
            print("Please wait. Calibrating microphone...")
            r.adjust_for_ambient_noise(source, duration=1)
            r.energy_threshold=1000
            print("Say something!")
            audio=r.listen(source)

        try:
            print("Google Speech Recognition thinks you said:")
            words = r.recognize_google(audio, language='zh-TW')
            print(words)
            if "前進" in words:
                tts = gTTS(text='前進中', lang='zh-TW')
                tts.save('car.mp3')
                os.system('omxplayer --vol -100  -o local -p car.mp3 > /dev/null 2>&1')
                forward()
                print("\n前進中\n")
            elif "左轉" in words:
                tts = gTTS(text='車輛左轉', lang='zh-TW')
                tts.save('car.mp3')
                os.system('omxplayer --vol -100  -o local -p car.mp3 > /dev/null 2>&1')
                turnLeft()
                print("\n車輛左轉\n")
            elif "右轉" in words:
                tts = gTTS(text='車輛右轉', lang='zh-TW')
                tts.save('car.mp3')
                os.system('omxplayer --vol -100  -o local -p car.mp3 > /dev/null 2>&1')
                turnRight()
                print("\n車輛右轉\n")
            elif "後退" in words:
                tts = gTTS(text='倒車中', lang='zh-TW')
                tts.save('car.mp3')
                os.system('omxplayer --vol -100  -o local -p car.mp3 > /dev/null 2>&1')
                backward()
                print("\n倒車中，後方車輛請注意\n")
            elif "結束" in words:
                print("\nQuit\n")
                subprocess.call(['pkill','-P',str(proc1.pid)])####
                proc1.kill()####
                GPIO.cleanup()
                quit()
            elif "隨機放音樂" in words or "隨機播音樂" in words or "隨機播放" in words:
                print('enter:')
                tmp = random.randint(1,10)
                cnt = 1
                for s in os.listdir('music'):
                    if tmp == cnt:
                        print('\n現在播放 ' + s[0:-4] + '\n')
                        tts = gTTS(text="現在播放"+s[0:-4], lang='zh-TW')
                        tts.save('music.mp3')
                        os.system('omxplayer -o local -p music.mp3 --vol -5000 > /dev/null 2>&1')
                        proc1 = subprocess.Popen(args=['omxplayer','--no-osd','-b','--aspect-mode','fill', "./music/"+s ,'--vol',voll])
                        break
                    cnt+=1
            elif "停止播放" in words:
                print('\n停止播放\n')
                subprocess.call(['pkill','-P',str(proc1.pid)])
                proc1.kill()
            elif "播放" in words or "聽" in words:
                index = 0
                name = ""
                if "播放" in words:
                    index = words.find("播放")
                    index += 2
                else:
                    index = words.find("聽")
                    index += 1
                name = words[index:]
                proc1 = subprocess.Popen(args=['omxplayer','--no-osd','-b','--aspect-mode','fill', "./music/"+name+".mp3" ,'--vol',voll])
                exist = False
                for s in os.listdir('music'):
                    if name in s:
                        exist = True
                if exist == False:
                    print('\n查無歌曲\n')
                    tts = gTTS(text="查無歌曲", lang='zh-TW')
                    tts.save('music.mp3')
                    os.system('omxplayer -o local -p music.mp3 --vol -5000 > /dev/null 2>&1')
                else:
                    print('\n播放 ' + name + '\n')
            elif "音樂清單" in words:
                cnt = 1
                for s in os.listdir('music'):
                    print(s)
                    tts = gTTS(text="第"+str(cnt)+"首是"+s[0:-4], lang='zh-TW')
                    tts.save('music.mp3')
                    os.system('omxplayer -o local -p music.mp3 --vol -5000 > /dev/null 2>&1')
                    cnt += 1
            elif "指令清單" in words:
                print('\n前進、左轉、右轉、後退、隨機播放、播放、停止播放、音樂清單、指令清單\n')
                tts = gTTS(text="前進、左轉、右轉、後退、隨機播放、播放、停止播放、音樂清單、指令清單", lang='zh-TW')
                tts.save('music.mp3')
                os.system('omxplayer -o local -p music.mp3 --vol -5000 > /dev/null 2>&1')
            elif "備忘錄" in words:
                #這次判斷成誰
                who = ''
                flag=0
                while True:
                    if flag==1:
                        break
                    #影像辨識
                    id = 0
                    names = ['None', 'Kelly', 'Irene', 'Apple']
                    camema = cv2.VideoCapture(0)
                    camema.set(3, 640) # 設定影片寬度
                    camema.set(4, 480) # 設定影片高度
                    minW = 0.1*camema.get(3)
                    minH = 0.1*camema.get(4)
                    blue = (255,0,0)
                    green = (0,255,0)
                    red = (0,0,255)
                    count_name = [0, 0, 0, 0]       
                    while True:
                        ret, img = camema.read()
                        img = cv2.flip(img, 1)
                        gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
                        faces = faceCascade.detectMultiScale(gray, scaleFactor = 1.2, minNeighbors = 5, minSize = (int(minW), int(minH)) )
                        for(x,y,w,h) in faces:
                            cv2.rectangle(img, (x,y), (x+w,y+h), green, 2)
                            id, confidence = recognizer.predict(gray[y:y+h,x:x+w])
                            if (confidence < 100):
                                name = names[id]
                                count_name[id] += 1
                                confidence = str(100 - round(confidence)) +"%"
                            else:
                                name = "未知"
                                confidence = str(100 - round(confidence)) +"%"
                            
                            cv2.putText(img, str(name), (x, y-15), cv2.FONT_HERSHEY_SIMPLEX, 2, (255, 0, 0), 2)
                        cv2.imshow('image',img) 

                        if cv2.waitKey(1) & 0xFF == ord('q'):
                            break   
                    print("\n人臉辨識程式結束")
                    camema.release()
                    cv2.destroyAllWindows()

                    if count_name[1] > count_name[2] and count_name[1] > count_name[3]:
                        who = names[1]
                    elif count_name[2] > count_name[1] and count_name[2] > count_name[3]:
                        who = names[2]                
                    else:
                        who = names[3]
                    print("檢測結果為: ", who)
                    #-------
                    #who=input("使用者名稱:")
                    stop=False
                    istime=datetime.datetime.today()
                    pretime=istime.minute
                    print("Time = {:d}:{:02d}".format(istime.hour, istime.minute))

                    # recognize speech using Google Speech Recognition
                    while True:
                        istime=datetime.datetime.today()
                        if pretime!=istime.minute:
                            pretime=istime.minute
                            print("Time = {:d}:{:02d}".format(istime.hour, istime.minute))
                        #print(istime.hour)
                        conn = sqlite3.connect('test1.db')
                        c = conn.cursor()
                        sele='SELECT ID, NAME, todo, time  from COMPANY where NAME=?'
                        cursor=c.execute(sele,(who,))

                        for row in cursor:
                            if '點' in row[3]:
                                sqltime=row[3].split('點')
                            elif '.' in row[3]:
                                sqltime=row[3].split('.')
                            if int(sqltime[0])<istime.hour:
                                tts = gTTS(text='提醒'+row[3]+row[2], lang='zh-TW')
                                tts.save('listtest_tw.mp3')
                                os.system('omxplayer -o local -p listtest_tw.mp3 > /dev/null 2>&1')
                                dele='DELETE from COMPANY where time=? and NAME=?'
                                c.execute(dele,(row[3],who))
                                conn.commit()
                                print ("指令", row[3],row[2], "已過時\n")
                            elif int(sqltime[0])==istime.hour and int(sqltime[1])<=istime.minute:
                                tts = gTTS(text='提醒'+row[3]+row[2], lang='zh-TW')
                                tts.save('listtest_tw.mp3')
                                os.system('omxplayer -o local -p listtest_tw.mp3 > /dev/null 2>&1')
                                dele='DELETE from COMPANY where time=? and NAME=?'
                                c.execute(dele,(row[3],who))
                                conn.commit()
                                print ("指令", row[3],row[2], "已過時\n")
                        conn.close()

            ###########################################################################################################
                        results=[]
                        while True:
                            r=sr.Recognizer()
                            with sr.Microphone() as source:
                                print("Please wait. Calibrating microphone...")
                                #listen for 1 seconds and create the ambient noise energy level
                                r.adjust_for_ambient_noise(source, duration=1)
                                r.energy_threshold = 4000
                                print("指令列表：\n1.「(時間)」提醒我「(事項)」：新增資料\n2.刪除「(事項)」：刪除資料\n3.查詢清單：查詢資料\n4.退出：結束該使用者\n5.回主選單：結束備忘錄")
                                print("請輸入指令:")
                                audio=r.listen(source)
                                results = r.recognize_google(audio, language='zh-TW')
                                if results!=[]:
                                    print("301行內容: ", results)
                                    break

                        #results = r.recognize_google(audio, language='zh-TW')
                        print("輸入指令為:")
                        print(results)

                        if '提醒我' in results:
                            conn = sqlite3.connect('test1.db')
                            c = conn.cursor()
                            arr = results.split('提醒我')
                            print(arr)
                            if arr[0]=='' or arr[1] =='':
                                continue

                            query = """
                                INSERT INTO COMPANY
                                    (NAME, todo, time)
                                VALUES
                                    (?, ?, ?)
                                    """
                            data =  [who, arr[1], arr[0]]

                            c.execute(query, data)
                            conn.commit()
                            print ("数据操作成功")
                            conn.close()
                            time.sleep(3)
                        
                        elif '退出' in results:
                            break

                        elif '主選單' in results:
                            flag=1
                            break
                        
                        elif '刪除' in results:
                            conn = sqlite3.connect('test1.db')
                            c = conn.cursor()
                            arr = results.split('刪除')
                            print(arr)
                            dele='DELETE from COMPANY where todo=? and NAME=?'
                            c.execute(dele,(arr[1],who))
                            conn.commit()
                            print ("数据刪除成功")
                            conn.close()
                            time.sleep(3)
                            
                        elif '查詢' in results:
                            conn = sqlite3.connect('test1.db')
                            c = conn.cursor()
                            sele='SELECT NAME, todo, time  from COMPANY where NAME=?'
                            cursor=c.execute(sele,(who,))
                            for row in cursor:
                                print ("NAME = ", row[0])
                                print ("todo = ", row[1])
                                print ("time = ", row[2], "\n")
                            conn.close()
                            time.sleep(3)

                       
        #except sr.UnknownValueError:
        #    print("Google Speech Recognition could not understand audio")

        except sr.RequestError as e:
            print("No response from Google Speech Recognition service: {0}".format(e))
            subprocess.call(['pkill','-P',str(proc1.pid)])
            proc1.kill()
          