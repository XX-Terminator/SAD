
class Tres_En_Ralla():

    def __init__(self, ficha):                                          #Inicializamos las varibles iniciales de la clase 
        
        self.Fichas_en_Tablero = []
        for i in range(9):                          
            self.Fichas_en_Tablero.append(" ") 
        self.ficha = ficha   
       
    def welcoming(self):
        print(f"\n       LET'S PLAY \n     TRES EN RALLA!! ")

    def print_turno(self):
        print(f"\n     It's you turn!")
    
    def tablero(self):                                                  #Forma del tablero
        print("\n       1   2   3")
        print("      -----------")
   
        fila = "   1 | " + self.Fichas_en_Tablero[0]
        fila += " | " + self.Fichas_en_Tablero[1]
        fila += " | " + self.Fichas_en_Tablero[2] + " | " 
        print(fila)

        print("      ---+---+---")

        fila = "   2 | " + self.Fichas_en_Tablero[3]
        fila += " | " + self.Fichas_en_Tablero[4]
        fila += " | " + self.Fichas_en_Tablero[5] + " | "
        print(fila)

        print("      ---+---+---")
 
        fila = "   3 | " + self.Fichas_en_Tablero[6]
        fila += " | " + self.Fichas_en_Tablero[7]
        fila += " | " + self.Fichas_en_Tablero[8] + " | "
        print(fila)
        print("      -----------")

    def colocarFixa(self):
        ocupado=True
        while ocupado ==True:
            print("Place The Token\n")
            col= input(f"Col: ")
            fila=input(f"Fila: ")
            posTablero = 0

            for i in range(1,4,1):
                if fila == "i":    
                    if col == "1":
                        posTablero = 3*i-3
                    elif col == "2":
                        posTablero = 3*i-2
                    elif col == "3":
                         posTablero = 3*i-1
            if self.Fichas_en_Tablero[posTablero] == " ":
                ocupado=False
                self.Fichas_en_Tablero[posTablero] = self.ficha         #coloca la fitxa del jugador q crida la funcio

    def actualitza_Tablero(self, nuevas_Fichas_en_Tablero):             #actualiza la llista 
        for i in range(9):
            self.Fichas_en_Tablero[i] = nuevas_Fichas_en_Tablero[i]


    def ganar(self, ficha):                                             #función que devuelve True si ganas y false si no                           
        vec = []                                            
        for i in range(9):                                              #añadimos los simbolos al vector vec para comprovar si se da alguna combinación del 3 en ralla
            vec.append(self.Fichas_en_Tablero[i])                 
        sym = ficha
        for i in range(3):                                              #Combinación filas
            if vec[3*i] == sym and vec[3*i+1] == sym and vec[3*i+2] == sym:                     
                return True
        for i in range(3):                                              #Combinación columnas
            if vec[i] == sym and vec[i+3] == sym and vec[i+6] == sym:
                return True
        for i in range(2):                                              #Combinación diagonales
            if vec[2*i] == sym and vec[4] == sym and vec[8-2*i] == sym:
                return True    
        
        return False                                                    #Si no hay ninguna combinación, se devuelve False

    def tablero_lleno(self):                                            #Nos indica si el tablero esta lleno 
        for i in range(9):
            if  self.Fichas_en_Tablero[i] == " ":
                return False
        else:
            return True                                                 #Quedan aun huecos vacios

    def empate(self):                                                   #Determina si hay empate
        if self.tablero_lleno()==True and self.ganar("X")==False  and self.ganar("O")== False:
            return True
        else:
            return False
    
    def reiniciar_juego(self):                                          #Reinicio del tablero para volver al jugar
        for i in range(9):
            self.Fichas_en_Tablero[i] = " "

    
    

    def print_resultado(self):
        if self.ganar(self.ficha) == True:
            print(f"\nYou are the WINNER! CONGRATULATIONS!")
        elif self.empate() == True:
         print(f"\n It's a TIEEEEE")
        else:
            print(f"\nYou've LOST!GOOD LUCK NEXT TIME!")
        
    def input_cont_jugando(self):
        respuesta_valida= False
        while respuesta_valida==False:
            respuesta=input(f"\nDo you wanna continue playing? Yes or No\n ")
            if respuesta =="Yes" or respuesta =="No":
                respuesta_valida=True
        return respuesta
    
