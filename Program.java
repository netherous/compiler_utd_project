public class Program extends Token{
	String id;
	Memberdecl mbs;
	int empty = 0;
	StmtList sts;
	public Program(String id, Memberdecl mbs){
		this.id = id;
		this.mbs = mbs;
		symbolTable = new SymbolTable();
	}
	public Program(){
		
	}  
	public Program(StmtList sts){
		this.sts = sts;
	}

	public String typeCheck() throws Exception{
		return "";
	}

	@Override
	public String toString(int t){
		if(empty == 1) return "Program\n"; 
		if(sts != null) return getTabs(t) + "Program: \n" + sts.toString(t+1) + "\n";
		return "Program: \n" + getTabs(t)+ "class "+id+ "{\n"+mbs.toString(t+1)+"\n"+getTabs(t)+"}" + "\n";
	}
}
