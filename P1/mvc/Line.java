import java.util.ArrayList;
import java.util.Observable;

public class Line extends Observable {
  
  int position;
  boolean insert;
  ArrayList<Character> line;
  Console console;

  public Line (){
    line = new ArrayList<>();
    insert = false;
    position = 0;
    console = new Console();
    this.addObserver(console);
  }

  public void addCharacter(char character){
    if (!insert || position >= line.size()) {
      line.add(position, character);      
      System.out.print("\033[@");
    }
    else {
      line.set(position, character);
    }
    position++;
    this.setChanged();
    this.notifyObservers(character);     
  }

  public void home(){
    while(position>0){
      this.left();
    }     
  }

  public void end(){
    while(position<line.size()){
      this.right();
    }
  }

  public void right(){
    if (position < line.size()){
      position++;
      this.setChanged();
      this.notifyObservers("\033[C");
    } 
  }

  public void left(){
    if (position > 0){
      position--;
      this.setChanged();
      this.notifyObservers("\033[D");
    } 
  }

  public void insert(){    
    insert = !insert;
  }

  public void delete(){
    if(position < line.size())
    {
      line.remove(position);
      this.setChanged();
      this.notifyObservers("\033[P");
    }
  }

  public void backSpace(){
    if(position > 0)
    {
      position--;      
      line.remove(position);
      this.left();
      this.delete();
    }    
  }

  @Override
  public String toString(){
    StringBuilder str = new StringBuilder();
    for (char s : line) 
      str = str.append(s);
    return str.toString();
  }
}

