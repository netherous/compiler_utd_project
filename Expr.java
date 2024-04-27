public class Expr extends Token {
    int it,prod;
    double dl;
    char car;
    Expr ex,ex2,ex3;
    Name name;
    String str;
    Args args;
    boolean flag;
    BinaryOps ops;

    public Expr( Name name){
        this.name = name;
        prod = 1;
    }

    public Expr( String str, int type){
        this.str = str;
        prod = type;
    }

    public Expr( String str, Args args){
        this.str = str;
        this.args = args;
        prod = 3;
    }

    public Expr(int i){
        this.it = i;
        prod = 4;
    }
    public Expr(double d){
        this.dl = d;
        prod = 7;
    }

    public Expr(Boolean d){
        this.flag = d;
        prod = 8;
    }

    public Expr(Expr e){
        this.ex = e;
        prod = 10;
    }

    public Expr(Expr e, int type){
        this.ex = e;
        prod = type;
    }

    public Expr( String type, Expr e){
        this.ex = e;
        this.str = type;
        prod = 14;
    }

    public Expr(Expr e1,Expr e2, BinaryOps ops){
        this.ex = e1;
        this.ex2 = e2;
        this.ops = ops;
        prod = 15;
    }

    public Expr(Expr e1,Expr e2, Expr e3){
        this.ex = e1;
        this.ex2 = e2;
        this.ex3 = e3;
        prod = 16;
    }

    public String toString(int tab){
        String ret = "";
        switch(this.prod){
            case 1:
                ret = name.toString(tab);
                break;
            case 2:
                ret =  "(" + str + ")";
                break;
            case 3:
                ret =  str +"(" + args.toString(tab) + ")";
                break;
            case 4:
                ret =  ""+it;
                break;
            case 5: case 6:
                ret =  str;
                break;
            case 7:
                ret =  ""+dl;
                break;
            case 8:
                ret =  flag? "true" : "false";
                break;
            case 9:
                ret =  "";
                break;
            case 10:
                ret =  "(" + ex.toString(tab) +")";
                break;
            case 11:
                ret =  "~" + ex.toString(tab);
                break;
            case 12:
                ret =  "-" + ex.toString(tab);
                break;
            case 13:
                ret =  "+" + ex.toString(tab);
                break;
            case 14:
                ret =  "(" + str + ")" + ex.toString(tab);
                break;
            case 15:
                ret =  ex.toString(tab) + ops.toString(tab) + ex2.toString(tab);
                break;
            case 16:
                ret =  "(" + ex.toString(tab) + "?" + ex2.toString(tab) + ":" + ex3.toString(tab) + ")";
                break;
        }
        ret = "(" + ret + ")";
        return ret;
    }

    public boolean isArray() throws Exception{
        return name != null && name.isArray() || prod == 14 && ex.isArray();
    }

    public boolean isFinal() throws Exception{
        return name != null && name.isFinal() || prod == 14 && ex.isFinal();
    }

    public boolean isMethod()throws Exception{
        return name != null && name.isMethod() || prod == 14 && ex.isMethod();
    }

    public String typeCheck() throws Exception{
        String ret = "";
        switch(this.prod){
            case 1:
                ret = name.typeCheck();
                break;
            case 2:
                Var meth = symbolTable.get(str);
                if(meth.type != "meth"){
                    throw new Exception("Error: cannot call on non-function type: " + str);
                }
                ret = meth.typeCheck();
                break;
            case 3:
                meth = symbolTable.get(str);
                if(meth.type != "meth"){
                    throw new Exception("Error: cannot call on non-function type: " + str);
                }
                if(args.dq.size() != meth.nested.size())
                    throw new Exception("Error: number of arguments miss-matched: " + meth.toString());
                for(int i = 0; i< args.dq.size(); i++){
                    Expr from = args.dq.get(i);
                    Var to = meth.nested.get(i);
                    if((from.isArray() != to.isArray()) || !castTo(from.typeCheck(), to.typeCheck()))
                        throw new Exception("Error: argument types mis-matched: " + meth.toString());
                }
                ret = meth.typeCheck(); 
                break;
            case 4:
                ret = "int";
                break;
            case 5: 
                ret =  "char";
                break;
            case 6:
                ret =  "str";
                break;
            case 7:
                ret =  "float";
                break;
            case 8:
                ret =  "bool";
                break;
            case 9:
                ret =  "";
                break;
            case 10:
                ret =  ex.typeCheck();
                break;
            case 11:
                if(!ex.typeCheck().equals("bool"))
                    throw new Exception("Error: negation cannot be applied to none bool type: " + ex.toString(0));
                ret =  "bool";
                break;
            case 12:
                ret = ex.typeCheck(); 
                if(!ex.typeCheck().equals("float") && !ex.typeCheck().equals("int"))
                    throw new Exception("Error: operator cannot be apply to this type: " + ex.typeCheck());
                break;
            case 13:
                ret = ex.typeCheck(); 
                if(!ex.typeCheck().equals("float") && !ex.typeCheck().equals("int"))
                    throw new Exception("Error: operator cannot be apply to this type: " + ex.typeCheck());
                ret = ex.typeCheck(); 
                break;
            case 14:
            // casting
                ret = str;
                break;
            case 15:
            //binary ops
                String op = ops.ops;
                String t1 = ex.typeCheck();
                String t2 = ex2.typeCheck();
                if(ex.typeCheck().equals("str") || ex.typeCheck().equals("str")){
                    if(!op.equals("+")){

                        throw new Exception("Error: type mis-matched for operator: " 
                        + op + " " + ex.toString(0) + "," + ex2.toString(0));
                    }
                    ret = "str";
                } 
                if(ops.isArith()){
                    if (castTo(t1, "int"))
                        t1 = "int";
                    if (castTo(t2, "int"))
                        t2 = "int";
                    if (!floatOrInt(t1) || !floatOrInt(t2)){

                        throw new Exception("Error: type mis-matched for operator: " + op + " " + ex.toString(0) + "," + ex2.toString(0));
                    }
                    ret = t1.equals("float") || t2.equals("float")? "float":"int";
                }else if(ops.isRel()){
                    if (!floatOrInt(ex.typeCheck()) || !floatOrInt(ex2.typeCheck()))
                        throw new Exception("Error: type mis-matched for operator: " + op + " " + ex.toString(0) + "," + ex2.toString(0));
                    ret = "bool";
                }else if (ops.isLog()){
                    if(!isBool(t2)|| !isBool(t1))
                        throw new Exception("Error: type mis-matched for operator: " + op + " " + ex.toString(0) + "," + ex2.toString(0));
                    ret = "bool";
                }
                break;
            case 16:
                t1 = ex.typeCheck();
                t2 = ex2.typeCheck();
                String t3 = ex3.typeCheck();
                if (!isBool(t1))
                    throw new Exception("Error: type is not a bool for terinary: " + ops.ops);
                if (!t2.equals(t3))
                    throw new Exception("Error: type is not consistent for terinary: " + ops.ops);
                ret = t2;
                break;
        }
        return ret;
    }
}
