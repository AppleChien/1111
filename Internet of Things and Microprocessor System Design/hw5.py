import RPi.GPIO as GPIO

import smbus
import time
import struct

LED_PIN = 12
GPIO.setmode(GPIO.BOARD)
GPIO.setup(LED_PIN, GPIO.OUT)
pwm = GPIO.PWM(LED_PIN, 100)
pwm.start(0)

def Light(degree):
    tmp= degree*110
    if(tmp>100):
        tmp=100
    elif(tmp<0):
        tmp=0
    pwm.ChangeDutyCycle(int(tmp))

bus = smbus.SMBus(1)
deviceID = bus.read_byte_data(0x53, 0x00)
print("ID: %x" % deviceID)

bus.write_byte_data(0x53, 0x2D, 0x00)
bus.write_byte_data(0x53, 0x2D, 0x08)

# Full resolution, Range = +/-2g
bus.write_byte_data(0x53, 0x31, 0x08)
time.sleep(0.5)
move_y1 = 0
move_y2 = 0
led = True
try:
    while True:
        accel = {'x' : 0, 'y' : 0, 'z': 0}
        # X-Axis LSB, X-Axis MSB
        data0 = bus.read_byte_data(0x53, 0x32)
        data1 = bus.read_byte_data(0x53, 0x33)
        # Convert the data to 10-bits
        xAccl = struct.unpack('<h', bytes([data0, data1]))[0]
        accel['x'] = xAccl / 256

        # Y-Axis LSB, Y-Axis MSB
        data0 = bus.read_byte_data(0x53, 0x34)
        data1 = bus.read_byte_data(0x53, 0x35)
        # Convert the data to 10-bits
        yAccl = struct.unpack('<h', bytes([data0, data1]))[0]
        accel['y'] = yAccl / 256
        if move_y1 == 0:
            move_y1 = yAccl / 256
            move_y2 = yAccl / 256
        else:
            move_y2 = yAccl / 256

        # Z-Axis LSB, Z-Axis MSB
        data0 = bus.read_byte_data(0x53, 0x36)
        data1 = bus.read_byte_data(0x53, 0x37)
        # Convert the data to 10-bits
        zAccl = struct.unpack('<h', bytes([data0, data1]))[0]
        accel['z'] = zAccl / 256

        # Output data to screen
        print ("Ax Ay Az: %.3f %.3f %.3f" % (accel['x'], accel['y'], accel['z']))
        if move_y2 - move_y1 > 0.2 or move_y2 - move_y1 < -0.2 and move_y2 - move_y1 != 0:
            if led:
                led = False
            else:
                led = True
                
        if led:
            Light(accel['x'])
        else:
            pwm.ChangeDutyCycle(int(0))

        time.sleep(0.5)
except KeyboardInterrupt:
    print("Ctrl+C Break")