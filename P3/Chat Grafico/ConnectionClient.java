import java.lang.Thread;

public class ConnectionClient{
	public static final int SERVER_PORT = 10;

	public static void main(String[] args){
		MySocket mysocket = new MySocket("localhost", SERVER_PORT);
		SwingCliente scliente = new SwingCliente( mysocket);
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