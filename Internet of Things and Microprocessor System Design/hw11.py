from bluepy.btle import Scanner, DefaultDelegate
import time


class ScanDelegate(DefaultDelegate):
    def __init__(self):
        DefaultDelegate.__init__(self)


scanner = Scanner().withDelegate(ScanDelegate())
while True:

    devices = scanner.scan(1.0)
    for dev in devices:
        # print("Device %s (%s), RSSI=%d dB" % (dev.addr, dev.addrType, dev.rssi))
        for (adtype, desc, value) in dev.getScanData():
            # print("  %s = %s" % (desc, value))
            if (desc == "Manufacturer" and value == "4c0002153b88dedea3a3b6ac103e27d76fceebce03780378c5"):
                # if(dev.addr=="74:59:44:4f:50:a3"):
                # time.sleep(1)
                print("RSSI=%d dB" % (dev.rssi))
