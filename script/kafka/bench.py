import socket
import sys
import time
import signal
import logging

address = ('spark-master', 9999)
s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)


def sig_handler(signum, fname):
    s.close()
    exit(0)


signal.signal(signal.SIGINT, sig_handler)
signal.signal(signal.SIGHUP, sig_handler)
signal.signal(signal.SIGTERM, sig_handler)

MSG = "%d\t451\t210.35.99.246\t40719\t182.254.92.141\t80\tP\t" \
      "182.254.92.141\t\tMicroMessenger Client\tapplication/octet-stream\n"


def main():
    if len(sys.argv) < 2 or not int(sys.argv[1]) > 0:
        print 'usage: %s <k_per_sec>' % sys.argv[0]
        exit(1)

    count = int(sys.argv[1]) * 1000

    while True:

        t = time.time()
        for i in xrange(count):
            s.sendto(MSG % int(t), address)
        logging.info('%d messages sent. [%f]' % (count, t))
        time.sleep(t + 1 - time.time())

if __name__ == '__main__':
    logging.basicConfig(level=logging.INFO,
                        format='%(asctime)s '
                               '%(filename)s '
                               '[%(levelname)s] '
                               '%(message)s',
                        datefmt='%b %d %Y %a %H:%M:%S',
                        filename='logs/%s.log' % sys.argv[0],
                        filemode='w')
    main()

