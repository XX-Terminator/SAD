import socket 
import pickle 

from Definiciones_3_En_Ralla import Tres_En_Ralla

HOST = '127.0.0.1'                                                  #dirección IP del localhost
PORT = 1024                                                         #Puerto

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)               #Tipo de direción IP: AF_INET = IPv4; protocolo socket: SOCK_STREAM = TCP
s.connect((HOST, PORT))                                             #connexión con el host
print(f"\nConnected to {s.getsockname()}!")

jugador = Tres_En_Ralla("O")

play = True

while play == True:
    
    jugador.welcoming()
    jugador.tablero()

    print(f"\nWaiting for the rival to move his token...")
    posiciones_Fichas = s.recv(1024)
    posiciones_Fichas = pickle.loads(posiciones_Fichas)                       
    jugador.actualitza_Tablero(posiciones_Fichas)


    while jugador.ganar("O") == False and jugador.ganar("X") == False and jugador.empate() == False:
      
        jugador.print_turno()
        jugador.tablero()
        jugador.colocarFixa()
        jugador.tablero()

        posiciones_Fichas = pickle.dumps(jugador.Fichas_en_Tablero)      #selecciona la lista de fichas en el tablero para convertirla en una secuencia de bytes, para poder enviarla
        s.send(posiciones_Fichas)                                        #enviamos al host la informacion

        if jugador.ganar("O") == True or jugador.empate() == True:
            break

        print(f"\nWaiting for the rival to move his token...")
        simbolX = s.recv(1024)
        simbolX = pickle.loads(simbolX)                                 #coge la secuencia de bytes y convertirla en un objeto
        jugador.actualitza_Tablero(simbolX)
        

    jugador.print_resultado()

    print(f"Waiting for the rival's response...")                   
    venganza_rival = s.recv(1024)                                       #recivimos la respuesta del Host
    venganza_rival = pickle.loads(venganza_rival)                       #coge la secuencia de bytes y la converte en un objeto
    venganza = ""

    if venganza_rival == "Yes":
        print(f"\nThe rival wants revenge! JE JE JE")
        venganza = jugador.input_cont_jugando()

        send_venganza = pickle.dumps(venganza)
        s.send(send_venganza)

        if venganza == "Yes":
            jugador.reiniciar_juego()

        else:
            play = False
    else:
        print(f"\nThe rival does not want revenge, he/she is a loser.")
        play = False

spacer = input(f"\nThank You For Playing!!\nPress ENTER to skip...\n")

s.close()
