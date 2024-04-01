import java.util.*;
public class StmtList extends Token{
   List<Stmt> st; 
   public StmtList (){
    st = new LinkedList<Stmt>();
   }
   public StmtList prepend(Stmt s){
    st.add(0, s);
    return this;
   }
   public String toString(int t){
    String ret = "";
    int i = 0;
    for(Stmt s: st){
        i++;
        ret += s.toString(t) ;
        ret += i == st.size()? "" : "\n";
    }
    return ret;
   }
}
