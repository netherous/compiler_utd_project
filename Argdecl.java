import java.util.ArrayList;

public class Argdecl extends Token{
    boolean brak;
    String type;
    String id; 
    public Argdecl(String type, String id, boolean brak){
        this.type = type;
        this.id = id;
        this.brak = brak;
    }

    public String toString(int t){
        return type + " " + id + (brak? "[]" : "");
    }

    public String typeCheck() throws Exception{
        symbolTable.addVar(id,(brak ? "array" : ""), type, new ArrayList<>());
        System.err.println(symbolTable.get(id).toString());
        return "";
    }
}
