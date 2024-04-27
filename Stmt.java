public class Stmt extends Token {
   StmtList sts1, sts2;
   FielddeclList fds;
   Args ar;
   ReadList rl;
   Expr ex;
   Name nm;
   String str;
   int prod;
   boolean opSemi;

   public Stmt(Expr e, StmtList sts1, StmtList sts2) {
      this.ex = e;
      this.sts1 = sts1;
      this.sts2 = sts2;
      prod = 1;
   }

   public Stmt(Expr e, StmtList sts1) {
      this.ex = e;
      this.sts1 = sts1;
      prod = 2;
   }

   public Stmt(Name n, Expr e) {
      ex = e;
      nm = n;
      prod = 3;
   }

   public Stmt(ReadList rl) {
      this.rl = rl;
      prod = 4;
   }

   public Stmt(Args pl, int prod) {
      this.ar = pl;
      this.prod = prod;
   }

   public Stmt(String id, Args ar) {
      this.ar = ar;
      this.str = id;
      this.prod = ar ==null ?7:8;
   }

   public Stmt( int prod, Expr e) {
      this.ex = e;
      this.prod = prod;
   }

   public Stmt(Name nm, int prod) {
      this.nm= nm;
      this.prod = prod;
   }

   public Stmt(FielddeclList fds, StmtList sts, boolean flag) {
      this.sts1= sts;
      this.opSemi = flag;
      this.fds = fds;
      this.prod = 13;
   }

   public String toString(int t) {
      String ret = "";
      switch (this.prod){
         case 1:
            ret = getTabs(t)+"if(" + ex.toString(t) +")\n";
            ret += getTabs(t)+"{\n" + sts1.toString(t+1) +"\n"+ getTabs(t)+"}";
            ret += sts2 != null? "\n" + getTabs(t) + "else{\n" + sts2.toString(t+1)+ "\n"+getTabs(t)+"}" : "";
            break;
         case 2:
            ret = getTabs(t)+"while(" + ex.toString(t) +")\n";
            ret += getTabs(t)+"{\n" + sts1.toString(t+1) +"\n"+ getTabs(t)+"}";
            break;
         case 3:
            ret = getTabs(t)+nm.toString(t) + "=" + ex.toString(t) + ";";
            break;
         case 4:
            ret =getTabs(t)+ "read(" + rl.toString(t) +");";
            break;
         case 5:
            ret =getTabs(t)+ "print(" + ar.toString(t) +");";
            break;
         case 6:
            ret =getTabs(t)+ "printline(" + ar.toString(t) +");";
            break;
         case 7:
            ret =getTabs(t)+ str+"();";
            break;
         case 8:
            ret =getTabs(t)+ str+"("+ar.toString(t) + ");";
            break;
         case 9:
            ret =getTabs(t)+ "return ();"; 
            break;
         case 10:
            ret =getTabs(t)+ "return ("+ex.toString(t) + ");";
            break;
         case 11:
            ret =getTabs(t)+ nm.toString(t) + "++;";
            break;
         case 12:
            ret =getTabs(t)+ nm.toString(t) + "--;";
            break;
         case 13:
            ret =getTabs(t)+ "{\n" + fds.toString(t+1)+ sts1.toString(t+1) +"\n"+getTabs(t)+"}";
            ret += opSemi? ";" : "";
            break;
      }
      return ret;
   }

   public boolean isRet(){
      return prod ==9 || prod == 10;
   }

   public String typeCheck() throws Exception {
      switch (this.prod) {
         case 1: // if
            if (!isBool(ex.typeCheck()))
               throw new Exception("Error: boolean type required for if stmt");
            symbolTable.startScope();
            sts1.typeCheck();
            symbolTable.endScope();
            if (sts2 != null) {
               symbolTable.startScope();
               sts2.typeCheck();
               symbolTable.endScope();
            }
            break;
         case 2: // while
            if (!isBool(ex.typeCheck()))
               throw new Exception("Error: boolean type required for while stmt");
            symbolTable.startScope();
            sts1.typeCheck();
            symbolTable.endScope();
            break;
         case 3: // assignment
            if (!castTo(ex.typeCheck(), nm.typeCheck()))
               throw new Exception("Error: assignent type mis-matched");
            if (symbolTable.get(nm.id).isFinal())
               throw new Exception("Error: re-assignent to final variables");
            break;
         case 4: // read
            for (Name n : rl.nms) {
               if (n.isArray() || n.isFinal())
                  throw new Exception("Error: cannot read an array or final.");
            }
            break;
         case 5:
         case 6:// print and printline
            for (Expr e : ar.dq) {
               e.typeCheck();
               if (e.isArray() || e.isMethod())
                  throw new Exception("Error: cannot print an array or function.");
            }
            break;
         case 7: // function call no args
            if (!symbolTable.get(str).isMethod())
               throw new Exception("Error: calling variable is not a method");
            break;
         case 8: // function call with args
            Var fun = symbolTable.get(str);
            if (!fun.isMethod())
               throw new Exception("Error: calling variable is not a method");
            if (fun.nested.size() != ar.dq.size())
               throw new Exception("Error: method argument amount mis-matched");
            for (int i = 0; i < ar.dq.size(); i++) {
               String from = ar.dq.get(i).typeCheck();
               String to = fun.nested.get(i).typeCheck();
               if (!castTo(from,to))
               throw new Exception("Error: method argument type mis-matched");
            }
            break;
         case 9: // return;
            return "void";
         case 10: // return with value
            return ex.typeCheck();
         case 11:
         case 12: // INC and DEC
            if (!floatOrInt(nm.typeCheck()))
               throw new Exception("Error: this unary operation cannot be applied to non-numeric type");
            if (symbolTable.get(nm.id).isFinal())
               throw new Exception("Error: re-assignent to final variables");
            break;
         case 13: // statement block
            symbolTable.startScope();
            for (Fielddecl f : fds.fds)
               f.typeCheck();
            for (Stmt st : sts1.st)
               st.typeCheck();
            symbolTable.endScope();
            break;
      }
      return "";
   }

}
