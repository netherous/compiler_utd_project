public class Methoddecl extends Token {

    String type, id;
    ArgdeclList adl;
    FielddeclList fds;
    StmtList sts;
    boolean os;

    public Methoddecl(String type, String id, ArgdeclList adl, FielddeclList fds, StmtList sts, boolean os) {
        this.type = type;
        this.id = id;
        this.adl = adl;
        this.fds = fds;
        this.sts = sts;
        this.os = os;
    }

    public String toString(int t) {
        String ret = getTabs(t) +type + " " + id + "(" + adl.toString(t) + ")\n";
        ret += getTabs(t) + "{ \n";
        ret += getTabs(t+1) + fds.toString(t+1) + sts.toString(t+1) + "\n";
        ret += getTabs(t) + "}" + (os? ";" : "");
        return ret;
    }
}
