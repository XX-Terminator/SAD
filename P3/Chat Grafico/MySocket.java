import java.io.*;
import java.net.Socket;

public class MySocket{

	private Socket socket;
	private BufferedReader Buff_Reader; // Para recibir mensajes
	private PrintWriter Print_Writer; // Para enviar mensajes
	private String name;
	
	public MySocket(String host, int hostPort){//Creamos el socket
		try{
			this.socket = new Socket(host, hostPort);
			Buff_Reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Print_Writer = new PrintWriter(socket.getOutputStream(), true);
			name=host;
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public MySocket(Socket new_socket){
		try{
			this.socket = new_socket;
			Buff_Reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			Print_Writer = new PrintWriter(socket.getOutputStream(), true);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public String read(){ //leer mensajes
		String message = null;
		try{
			message = Buff_Reader.readLine();
		}catch(IOException e){
			e.printStackTrace();
		}
		return message;
	}

	public String getName(){   
        return name;
    }
	
	public void print_line(String string){ //imprimir mensajes
		Print_Writer.println(string);
	}
	
	public void close(){//Cierra el socket 
		try{
			socket.close();
			Buff_Reader.close();
			Print_Writer.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}