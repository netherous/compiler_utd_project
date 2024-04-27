import java.util.*;

import javax.swing.text.Style;
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

    public String typeCheck() throws Exception {
        // create list of params for function definition
        ArrayList<Var> params = new ArrayList<>();

        for (Argdecl arg : adl.al){
            // SymbolTable.Var v = new SymbolTable.Var(arg.id,(arg.brak ? "array" : ""), arg.type, new ArrayList<>());
            params.add(new Var(arg.id,(arg.brak ? "array" : ""), arg.type, new ArrayList<>()));
        }
        symbolTable.addVar(id, "meth", type, params);

        symbolTable.startScope();

        for (Argdecl arg : adl.al)
            arg.typeCheck();

        for (Fielddecl f : fds.fds)
            f.typeCheck();
        // verify whether return is needed for function
        boolean needsReturn = !type.equals("void");
        // typecheck inner statements
        for (Stmt stmt : sts.st) {
            if (stmt.isRet()) {
                // satisfy return requirement
                needsReturn = false;
                // check that returntype == function type
                if (!castTo(stmt.typeCheck(),(type)))
                    throw new Exception("Error: return type mis-matched" );
            } else
                stmt.typeCheck();
        }

        // throw exception if a return statement was not found
        if (needsReturn)
            throw new Exception("Error: missing return value" );

        symbolTable.endScope();
        return "";
    }

}
