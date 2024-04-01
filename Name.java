public class Name extends Token{
    String id;
    Expr ind;
    public Name(String i){
        id = i;
        ind = null;
    }
    public Name(String i, Expr e){
        id = i;
        ind = e;
    }

    public String toString(int t){
        if(ind == null){
            return id;
        }else{
            return id + "[" + ind.toString() + "]";
        }
    }
}
