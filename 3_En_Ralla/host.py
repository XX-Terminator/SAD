import socket 
import pickle 

from Definiciones_3_En_Ralla import Tres_En_Ralla                                #Importamos el juego

HOST = '127.0.0.1'                                                      #Dirección IP del localhost
PORT = 1024                                                            #Puerto   

#servidor
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)                   #Tipo de direción IP: AF_INET = IPv4; protocolo socket: SOCK_STREAM = TCP
s.bind((HOST, PORT))                                            
s.listen(5)
                                            
socket_client, direccion_cliente = s.accept()                           #aceptamos la conexión con el cliente
print(f"\nConnected to {direccion_cliente}!")

jugador = Tres_En_Ralla("X")
play = True                                                             #Esta variable nos permite jugar
while play == True:

    jugador.welcoming()
    while jugador.ganar("X") == False and jugador.ganar("O") == False and jugador.empate() == False:

        jugador.print_turno()
        jugador.tablero()
        jugador.colocarFixa()
        jugador.tablero()                                               #Dibujamos el tablero 

        simbolX = pickle.dumps(jugador.Fichas_en_Tablero)               #selecciona la lista de fichas en el tablero para convertirla en una secuencia de bytes, para poder enviarla
        socket_client.send(simbolX)                                     #enviamos al host la informacion

        if jugador.ganar("X") == True or jugador.empate() == True:      #si hay empate o victoria salimos del bucle, porque se ha acabado el juego
          jugador.tablero()  
          break

        print(f"\nWaiting for the rival to move his token...")
        simbolO = socket_client.recv(1024)
        simbolO = pickle.loads(simbolO)
        jugador.actualitza_Tablero(simbolO)

    jugador.print_resultado()

    venganza = jugador.input_cont_jugando()                         
    send_venganza = pickle.dumps(venganza)                        
    socket_client.send(send_venganza)
    venganza_rival = ""

    if venganza == "No":
        play = False

    else:
     
        print(f"Waiting for the rival's response...")
        venganza_rival = socket_client.recv(1024)
        venganza_rival = pickle.loads(venganza_rival)
 
        if venganza_rival == "No":
            print(f"\nThe rival does not want revenge, he/she is a loser.")
            play = False
        elif venganza_rival =="Yes":
            jugador.reiniciar_juego()
        


enter = input(f"\nThank You For Playing!!\nPress ENTER to skip...\n")

socket_client.close()
