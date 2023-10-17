import java.util.ArrayList;

public class Line {
  
  boolean insert;
  ArrayList<Character> line;
  int position;

  public Line () {
    line = new ArrayList<>();
    insert = false;
    position = 0;
  }

  public void addCharacter(char character) {
    if (!insert || position >= line.size()) {
      line.add(position, character);      
      System.out.print("\033[@");
    }
    else {
      line.set(position, character);
    }
    System.out.print(character);    
    position++;
  }

  public void home() {
    if(position > 0) {
      System.out.print("\033[" + position + "D");
      position = 0;
    }      
  }

  public void end() {
    if(position < line.size()) {
      System.out.print("\033[" + (line.size() - position) + "C");
      position = line.size();
    }
  }

  public void right() {
    if (position < line.size()) {
      position++;
      System.out.print("\033[C");
    } 
  }

  public void left() {
    if (position > 0) {
      position--;
      System.out.print("\033[D");
    } 
  }

  public void insert() {    
    insert = !insert;
  }

  public void delete() {
    if(position < line.size()) {
      line.remove(position);
      System.out.print("\033[P");
    }
  }

  public void backSpace() {
    if(position > 0) {
      position--;      
      line.remove(position); 
      System.out.print("\033[D");              
      System.out.print("\033[P");
    }    
  }

  @Override
  public String toString() {
    StringBuilder str = new StringBuilder();
    for (char s : line) 
      str = str.append(s);
    return str.toString();
  }
}
