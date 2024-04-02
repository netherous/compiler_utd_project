public class Fielddecl extends Token{
    String fin, type, id;
    Expr ex;
    int it;
    boolean prod;
    public Fielddecl(String fin, String type, String id, Expr ex, int it, boolean prod){
        this.fin = fin;
        this.type = type;
        this.id = id;
        this.ex = ex;
        this.it = it;
        this.prod = prod;
        // System.err.println(type + " " + id + " " + it);
    }

    public String toString(int t){
        String ret = "";
        if(prod){
            ret = getTabs(t) + fin + " " + type + " " + id + (ex != null? "=" + ex.toString(t): "") + ";";          
        }else{
            ret = getTabs(t) + type + " " + id + "[" + it + "];";
        }
        return ret;
    }
}
