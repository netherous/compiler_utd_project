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
}
