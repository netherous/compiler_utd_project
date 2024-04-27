abstract class Token {

  protected static SymbolTable symbolTable;

  protected String getTabs(int t) {
    String tabs = "";
    for (int i = 0; i < t; i++)
      tabs = tabs + "\t";
    return tabs;
  }

  public String toString(int t) {
    return "";
  }

  public String typeCheck() throws Exception{
    return "";
  };

  public static boolean castTo(String from, String to) {
    if (from.equals(to))
      return true;
    if (to.equals("bool") && (from.equals("int") || from.equals("float")))
      return true;
    if (from.equals("int") && to.equals("float"))
      return true;
    if (to.equals("str"))
      return true;
    return false;
  }

  public static boolean floatOrInt(String tp) {
    return tp.equals("float") || tp.equals("int");
  }
  public static boolean isBool(String tp) {
    return tp.equals("bool");
  }
}
