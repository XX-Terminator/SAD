import java.io.*;
import java.lang.Thread;

public class ConnectionClient{

	public static final int SERVER_PORT = 10;

	public static void main(String[] args){
		MySocket mysocket = new MySocket("localhost", SERVER_PORT);
		new Thread(){ //Thread Input
			public void run(){
				String message;
				BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
				try{
					while((message = input.readLine()) != null){
						mysocket.print_line(message);
					}
					mysocket.close();
				}catch(IOException e){
					System.out.println("Ha habido un error en la connexión");
				}
			}
		}.start();
		new Thread(){//Thread Output
			public void run(){
				String output;
				while((output = mysocket.read()) != null){
					System.out.println(output); 
				}
			}
		}.start();
	}
}
