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
}