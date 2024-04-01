public class Stmt extends Token{
   Expr ex;
   Name nm;
   public Stmt(Name n, Expr e){
    ex = e;
    nm = n;
   } 
   public String toString(int t){
    return nm.toString(t) +"="+ex.toString(t)+";";
   }
}
