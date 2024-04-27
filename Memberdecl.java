public class Memberdecl extends Token{

    FielddeclList fd;

    MethoddeclList md;
    public Memberdecl(Fielddecl fd, Memberdecl mb){
        if(mb.fd != null)
            this.fd = mb.fd.prepend(fd);
        else
            {
                this.fd = new FielddeclList();
                this.fd.prepend(fd);
            }
        this.md = mb.md;
    }
    public Memberdecl(MethoddeclList md){
        this.fd = new FielddeclList();
        this.md = md;
    }
    public String toString(int t){
        String ret = "";
        for(Fielddecl f: fd.fds){
            ret += f.toString(t) + "\n";
        }        
        for(Methoddecl f: md.fds){
            ret += f.toString(t) + "\n";
        }        
        return ret;
    }
    public String typeCheck() throws Exception{
        for (Fielddecl f : fd.fds)
            f.typeCheck();
        for (Methoddecl m : md.fds)
            m.typeCheck();
        symbolTable.endScope();
        return "";
    }
}
