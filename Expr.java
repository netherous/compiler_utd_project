public class Expr extends Token {
    Integer it;
    public Expr( int i){
        it = i;
    }

    public String toString(int tab){
        return getTabs(tab) + it.toString();
    }
}
