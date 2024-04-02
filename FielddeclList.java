import java.util.*;
public class FielddeclList extends Token {
   List<Fielddecl> fds; 
   public FielddeclList(){
    fds = new LinkedList<>();
   }
   public FielddeclList prepend(Fielddecl fd){
    fds.add(0, fd);
    return this;
   }
   public String toString(int t){
    String ret = "";
    int i = 0;
    for(Fielddecl s: fds){
        i++;
        ret += s.toString(t) ;
        ret += "\n";
    }
    return ret;
   }
}
