import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class EditableBufferedReader extends BufferedReader {
    
    //Constants
    private static final int RIGHT = 'C';
    private static final int LEFT = 'D';
    private static final int HOME = 'H';
    private static final int END = 'F';
    private static final int DEL = '3';
    private static final int INS = '2';
    private static final int BKSP = 127;
 
    InputStreamReader inputStreamReader;
    
    public EditableBufferedReader(InputStreamReader inputStreamReader) {

        super(inputStreamReader);
        this.inputStreamReader = inputStreamReader;

    } 
     
    public static void setRaw() { // put terminal in raw mode
        try {
            Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "stty -echo raw </dev/tty" });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unsetRaw() { // restore terminal to cooked mode
        try {
            Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "stty echo cooked </dev/tty" });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int read() throws IOException{
        String str = "";
        while(!this.ready()){ }
        while(this.ready()) {
            str += (char)super.read();
        }
        switch(str) {
            case "\033[2~":
            case "\033[3~": 
            case "\033[C": 
            case "\033[D":  
            case "\033[F":   
            case "\033[H":  return -str.charAt(2);
            default:        return  str.charAt(str.length() - 1);
        }
    }

    @Override
    public String readLine() throws IOException{
        try{
            setRaw();
            Line line = new Line();
            int key;
            while ((key = this.read()) != '\r'){
                switch(key)
                {
                    case -RIGHT: line.right();
                    break;
                    case -LEFT: line.left();
                    break;
                    case -HOME: line.home();
                    break;
                    case -END: line.end();
                    break;
                    case -INS: line.insert();
                    break;
                    case -DEL: line.delete();
                    break;
                    case BKSP: line.backSpace();
                    break;
                    default: line.addCharacter((char)key);
                }
            }
            return line.toString();
        } catch (IOException e){
            throw e;
        } finally {
            unsetRaw();
        }
    }
} 
