public class Program extends Token{
	Expr ex;
	public Program(Expr e){
		ex = e;
	}

	@Override
	public String toString(int t){
		return "Program: \n" + ex.toString(t+1) + "\n";
	}
}
