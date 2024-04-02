import java.util.*;
public class MethoddeclList extends Token{
    
   List<Methoddecl> fds; 
   public MethoddeclList(){
    fds = new LinkedList<>();
   }
   public MethoddeclList prepend(Methoddecl fd){
    fds.add(0, fd);
    return this;
   }
   public String toString(int t){
    String ret = "";
    int i = 0;
    for(Methoddecl s: fds){
        i++;
        ret += s.toString(t) ;
        ret +=  "\n";
    }
    return ret;
   }
}
