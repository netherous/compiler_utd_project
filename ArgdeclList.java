import java.util.*;;

public class ArgdeclList extends Token {
    List<Argdecl> al;

    public ArgdeclList() {
        al = new LinkedList<>();
    }

    public ArgdeclList prepend(Argdecl ad){
       al.add(0, ad); 
       return this;
    }

    public String toString(int t){

        String ret = "";
        int i = 0;
        for (Argdecl s : al) {
            i++;
            ret += s.toString(t);
            ret += i == al.size() ? "" : ",";
        }
        return ret;
    }
}
