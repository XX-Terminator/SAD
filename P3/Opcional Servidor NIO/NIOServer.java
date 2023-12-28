import java.io.IOException;
import java.util.Set;
import java.util.Iterator;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.net.InetSocketAddress;

public class NIOServer{
    private static Selector selec = null;
    
    public static void main(String[] args) {
        try {
            ServerSocketChannel server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress("localhost", 10));
            server.configureBlocking(false);
            selec = Selector.open();
            int validops = server.validOps();
            server.register(selec, validops, null);
            
            while (true) {
                selec.select();
                Set<SelectionKey> keys = selec.selectedKeys();
                Iterator<SelectionKey> iterator = keys.iterator();

                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    if (key.isAcceptable()) {                  
                        newUser(server);

                    }else if (key.isReadable()) {
                        message(key);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void message(SelectionKey selectionkey) throws IOException {//Parte encargada de los mensajes entre el servidor y clientes
        SocketChannel client = (SocketChannel) selectionkey.channel();
        ByteBuffer buff = (ByteBuffer) selectionkey.attachment();
        int read = client.read(buff);
        buff.flip();

        if (read > 0) {
            byte[] bytes = new byte[read];    
            int i = 0;

            while(buff.hasRemaining()){
                bytes[i] = (byte) buff.get(); 
                i++;
            }
            String message = new String(bytes);
            System.out.print(message);
            send(message, selectionkey);
        }
    }

    private static void newUser(ServerSocketChannel socket) throws IOException {//accepta y añade al nuevo cliente
        SocketChannel client = socket.accept();
        int set = SelectionKey.OP_WRITE | SelectionKey.OP_READ;
        client.configureBlocking(false);                                    
        ByteBuffer buff = ByteBuffer.allocate(2048);
        client.register(selec, set, buff);
        System.out.println("Un nuevo usuario entro en el chat.");
    }

    private static void send(String data, SelectionKey Key){
        ByteBuffer buff =ByteBuffer.wrap(data.getBytes());

        for(SelectionKey key : selec.keys()) {
            try{
                
                if(!key.equals(Key) && (key.channel() instanceof SocketChannel) && key.isWritable()) {
                    SocketChannel socket=(SocketChannel) key.channel();
                    socket.write(buff);
                    buff.rewind();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }   
        }
    }
}