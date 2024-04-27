/**
 * BinaryOps
 */
public class BinaryOps extends Token {
    String ops;
    public BinaryOps(String o){
        ops = o;
    }
    public String toString(int t){
        return ops;
    }
    public boolean isArith(){
        return ops.equals("+") || ops.equals("-") || ops.equals("*") || ops.equals("/");
    }
    public boolean isRel(){
        return ops.equals("<") || ops.equals(">") || ops.equals("<=") || ops.equals(">=") || ops.equals("!=") || ops.equals("==");
    }
    public boolean isLog(){
        return ops.equals("||") || ops.equals("&&") ;
    }

}