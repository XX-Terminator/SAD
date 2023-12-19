import java.io.*;
import java.net.ServerSocket;

public class MyServerSocket{

	private ServerSocket serv_socket;

	public MyServerSocket(int serverPort){//Creamos un socket servidor 
		try{
			this.serv_socket = new ServerSocket(serverPort);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public MySocket accept(){
		try{ //escucha las conexiones con el host
			return new MySocket(serv_socket.accept());
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}

	public void close(){
		try{
			this.serv_socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}