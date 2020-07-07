tcp-ip
SYN_SEND ---> SYN=1 seq=x   CLOSED

SYN_SEND <---- SYN=1 seq=y ack=x+1 ACK=1  SYN_RCVD

ESTABLISHED -----> SYN=1 ACK=1 seq=x+1 ack=y+1


ESTABLISHED -----> FIN=1 seq=j ESTABLISHED
FIN-WAIT-1  <----  ACK=1 seq=k ack=j+1 CLOSE-WAIT
FIN-WAIT-2 <---- FIN=1 seq=u ack=j+1  CLOSE-WAIT
TIME-WAIT  -----> ACK=1 seq=j+1 ack=u+1 LAST-ACK
                        
