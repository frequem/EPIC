#!/bin/python
#Pi Shutdown button (frequem)

import RPi.GPIO as GPIO
import time
import os

GPIO_SHUTDOWN_IN = 23
GPIO_SHUTDOWN_READY_OUT = 24

GPIO.setmode(GPIO.BCM) #Broadcom SOC Pin numbers

GPIO.setup(GPIO_SHUTDOWN_IN, GPIO.IN, pull_up_down = GPIO.PUD_UP)

GPIO.setup(GPIO_SHUTDOWN_READY_OUT, GPIO.OUT)
GPIO.output(GPIO_SHUTDOWN_READY_OUT, GPIO.HIGH) #set pin to high, as soon as shutdown is completed, this will be low again

def shutdown(channel):
	os.system("sudo shutdown -h now")
	
GPIO.add_event_detect(GPIO_SHUTDOWN_IN, GPIO.FALLING, callback=shutdown, bouncetime=2000)

while 1:
	time.sleep(1)

