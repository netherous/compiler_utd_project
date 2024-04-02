import java.util.*;

public class ReadList {
    List<Name> nms;

   public ReadList (){
    nms = new LinkedList<Name>();
   }

    public ReadList prepend(Name n) {
        nms.add(0, n);
        return this;
    }

    public String toString(int t) {
        String ret = "";
        int i = 0;
        for (Name s : nms) {
            i++;
            ret += s.toString(t);
            ret += i == nms.size() ? "" : ",";
        }
        return ret;
    }
}