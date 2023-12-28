import java.util.Scanner;

public class ConnectionClient{
	public static final int SERVER_PORT = 10;

	public static void main(String[] args){
		MySocket mysocket = new MySocket("localhost", SERVER_PORT);
		Scanner keyboard= new Scanner(System.in);
		System.out.println("Escriba su nombre: ");
		String name= keyboard.nextLine();
		SwingCliente scliente = new SwingCliente( name, mysocket);
		scliente.GUI();

		new Thread(){ 
			public void run(){
				String message;
				while((message=mysocket.read()) != null){
					scliente.addMessage(message);
				}
			}
		}.start();
	}
}