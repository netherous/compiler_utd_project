JAVA=java
JAVAC=javac
JFLEX=$(JAVA) -jar jflex-full-1.8.2.jar
CUPJAR=./java-cup-11b.jar
CUP=$(JAVA) -jar $(CUPJAR)
CP=.:$(CUPJAR)

default: run

.SUFFIXES: $(SUFFIXES) .class .java

.java.class:
		$(JAVAC) -cp $(CP) $*.java

FILE=    Lexer.java      parser.java    sym.java \
    ScannerTest.java \
	Expr.java Args.java Name.java BinaryOps.java Program.java \
	Stmt.java StmtList.java ReadList.java Methoddecl.java \
	Fielddecl.java FielddeclList.java MethoddeclList.java \
	Argdecl.java ArgdeclList.java Memberdecl.java 

# run: basicTest.txt basicFails.txt basicRegex.txt basicRegex.txt basicTerminals.txt

all: Lexer.java parser.java $(FILE:java=class)

phase1 : Phase1_expressions Phase1_order_of_ops Phase1_statements
phase2 : Phase2_empty Phase2_fields Phase2_full Phase2_methods

Phase1_expressions: all
		$(JAVA) -cp $(CP) ScannerTest Phase1Tests/Phase1_expressions.txt > output.txt
		cat Phase1Tests/Phase1_expressions.txt 
		cat -n output.txt 

Phase1_order_of_ops: all
		$(JAVA) -cp $(CP) ScannerTest Phase1Tests/Phase1_order_of_ops.txt > output.txt
		cat Phase1Tests/Phase1_order_of_ops.txt 
		cat -n output.txt 

Phase1_statements: all
		$(JAVA) -cp $(CP) ScannerTest Phase1Tests/Phase1_statements.txt > output.txt
		cat Phase1Tests/Phase1_statements.txt
		cat -n output.txt 

Phase2_empty: all
		$(JAVA) -cp $(CP) ScannerTest Phase2Tests/Phase2_empty.txt > output.txt
		cat Phase2Tests/Phase2_empty.txt
		cat -n output.txt 

Phase2_fields: all
		$(JAVA) -cp $(CP) ScannerTest Phase2Tests/Phase2_fields.txt > output.txt
		cat Phase2Tests/Phase2_fields.txt
		cat -n output.txt 

Phase2_full: all
		$(JAVA) -cp $(CP) ScannerTest Phase2Tests/Phase2_full.txt > output.txt
		cat Phase2Tests/Phase2_full.txt
		cat -n output.txt 

Phase2_methods: all
		$(JAVA) -cp $(CP) ScannerTest Phase2Tests/Phase2_methods.txt > output.txt
		cat Phase2Tests/Phase2_methods.txt
		cat -n output.txt 

clean:
		rm -f *.class *~ *.bak Lexer.java parser.java sym.java

Lexer.java: tokens.jflex
		$(JFLEX) tokens.jflex

parser.java: grammar.cup
		$(CUP) -interface < grammar.cup

parserD.java: grammar.cup
		$(CUP) -interface -dump < grammar.cup
