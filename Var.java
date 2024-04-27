    import java.util.*;
    public class Var extends Token {
        public ArrayList<Var> nested;
        public String fin, id, type;

        public Var(String id, String fin, String type, ArrayList<Var> nested) {
            this.id = id;
            this.fin = fin;
            this.type = type;
            this.nested = nested;
        }

        public boolean isFinal() {
            return fin.equals("final");
        }

        public boolean isArray() {
            return fin.equals("array");
        }

        public boolean isMethod() {
            return fin.equals("meth");
        }

        public String toString() {
            String ret = fin + " " + type + " " + id + "(";
            for (Var v : nested)
                ret += v.toString() + ",";
            ret += ")";
            return ret;
        }

        public String typeCheck() throws Exception {
            return type;
        }

    }
