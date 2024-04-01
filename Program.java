public class Program extends Token{
	StmtList sts;
	public Program(StmtList ss){
		sts = ss;
		System.err.println("program start");
	}

	@Override
	public String toString(int t){
		return "Program: \n" + sts.toString(t+1) + "\n";
	}
}
