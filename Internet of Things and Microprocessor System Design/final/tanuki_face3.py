import cv2
import numpy as np
import os
from PIL import Image, ImageDraw, ImageFont

recognizer = cv2.face.LBPHFaceRecognizer_create()  #使用舊版opencv-contrib-python，例如：版本3.4.3.18
recognizer.read('tanuki_face2_train.yml')
cascadePath = "shu772.xml"
faceCascade = cv2.CascadeClassifier(cascadePath);
#font = ImageFont.truetype('NotoSerifCJK-Regular.ttc', 40)  #sudo apt-get install fonts-noto-cjk
id = 0
names = ['None', 'Kelly', 'Irene', 'Apple'] #id為1，對應名字為建庭
camema = cv2.VideoCapture(0)
camema.set(3, 640) # 設定影片寬度
camema.set(4, 480) # 設定影片高度
minW = 0.1*camema.get(3)
minH = 0.1*camema.get(4)
blue = (255,0,0)
green = (0,255,0)
red = (0,0,255)
while True:
    ret, img = camema.read()
    img = cv2.flip(img, 1)
    gray = cv2.cvtColor(img,cv2.COLOR_BGR2GRAY)
    faces = faceCascade.detectMultiScale(gray, scaleFactor = 1.2, minNeighbors = 5, minSize = (int(minW), int(minH)) )
    for(x,y,w,h) in faces:
        cv2.rectangle(img, (x,y), (x+w,y+h), green, 2) #畫框
        id, confidence = recognizer.predict(gray[y:y+h,x:x+w])
        if (confidence < 100):
            name = names[id]
            confidence = str(100 - round(confidence)) +"%"
        else:
            name = "未知"
            confidence = str(100 - round(confidence)) +"%"
        
        print(name)
        cv2.putText(img, str(name), (x, y-15), cv2.FONT_HERSHEY_SIMPLEX, 2, (255, 0, 0), 2)
    cv2.imshow('image',img)
    k = cv2.waitKey(10) & 0xff #等待0.01秒
    if k == 27:
        break

print("\n程式結束")
camema.release()
cv2.destroyAllWindows()