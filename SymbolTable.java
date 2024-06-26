import java.util.*;
import java.util.Map.Entry;

public class SymbolTable {
    // most inner -> innert -> outter
    LinkedList<HashMap<String, Var>> table;

    public SymbolTable() {
        this.table = new LinkedList<>();
        startScope();
    }

    public Var get(String s) throws Exception {
        for (HashMap<String, Var> layer : table) {
            if (layer.containsKey(s))
                return layer.get(s);
        }
        throw new Exception("Error: variable not declared " + s);
    }

    public void addVar(String id, String fin, String tp, ArrayList<Var> varList) throws Exception {

        if (table.getFirst().containsKey(id)) {
            throw new Exception("Error: tried to redeclare variable " + id);
        }
        table.getFirst().put(id, new Var(id, fin, tp, varList));
        return;
    }

    public void startScope() {
        table.addFirst(new HashMap<>());
    }

    public void endScope() {
        table.removeFirst();
    }

    public String toString() {
        String ret = "";
        String t = "";
        for (HashMap<String, Var> layer : table) {
            for (Entry<String, Var> p : layer.entrySet())
                ret += t + p.getKey() + " " + p.getValue().fin + " " + p.getValue().type + "\n";
            t += "\t";
        }
        return ret;
    }


}