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
            return id + "[" + ind.toString(t) + "]";
        }
    }

    public boolean isArray() throws Exception {
        return symbolTable.get(id).isArray() && ind == null;
    }

    public boolean isFinal() throws Exception {
        return symbolTable.get(id).isFinal();
    }

    public boolean isMethod() throws Exception {
        return symbolTable.get(id).isMethod();
    }
    public String typeCheck() throws Exception{
        if(ind == null || ind.typeCheck().equals("int")){
            return symbolTable.get(id).type;
        }else{
            throw new Exception("Error: invalid index : " + toString(0));
        }
    }
}
