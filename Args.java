import java.util.*;
public class Args extends Token {
    List<Expr> dq;

    public Args(){
        dq = new LinkedList<Expr>();
    }

    public Args prepend(Expr e){
        dq.add(0, e);
        return this;
    }

    public String toString (int t){
        int i = 0;
        String ret ="";
        for(Expr e: dq){
            i++;
            ret += e.toString(t);
            if(i != dq.size()){
                ret += ",";
            }
        }
        return ret;
    }
}
