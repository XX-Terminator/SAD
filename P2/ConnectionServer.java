import java.io.*;
import java.lang.Thread;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;
import java.util.Map;

public class ConnectionServer{
	private static Lock lock = new ReentrantLock();
	private static HashMap<String, MySocket> users = new HashMap<>();
	private static final int SERVER_PORT = 10;

	public static void main(String[] args){
		
		System.out.println("Servidor Arrancado.\nEsperando Usuarios...");
		MyServerSocket socket = new MyServerSocket(SERVER_PORT);
		
		while(true){
			MySocket client = socket.accept();

			new Thread(){
				public void run(){
					client.print_line("Escriba su nombre: ");
					String user = client.read();
					addUser(user, client);
					String message;
					while((message = client.read()) != null){
						lock.lock();
						for(Map.Entry<String, MySocket> entry : users.entrySet()){
							MySocket mysocket = entry.getValue();
							if(!user.equals(entry.getKey())){
								mysocket.print_line(user+": "+message);
							}
						}
						lock.unlock();
						System.out.println(user + ": "+ message);
					}
					remove_user(user);
					client.close();
				}
			}.start();
		}
	}
	public static void printClient(String message, String user){
		lock.lock();
		for(Map.Entry<String, MySocket> entry : users.entrySet()){
			MySocket mysocket = entry.getValue();
			if(!user.equals(entry.getKey())){
				mysocket.print_line(user+": "+message);
			}
		}
		lock.unlock();
	}

	public static void addUser(String user, MySocket mysocket){
		lock.lock();
		users.put(user, mysocket);
		System.out.println( user + " entro en el chat.");
		lock.unlock();
	}

	public static void remove_user(String user){
		lock.lock();
		users.remove(user);
		System.out.println(user + " salio del chat.");
		lock.unlock();
	}
}
