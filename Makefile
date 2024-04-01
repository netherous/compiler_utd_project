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
	Stmt.java StmtList.java

# run: basicTest.txt basicFails.txt basicRegex.txt basicRegex.txt basicTerminals.txt

all: Lexer.java parser.java $(FILE:java=class)

phase1 : Phase1_expressions Phase1_order_of_ops Phase1_statements

Phase1_expressions: all
		$(JAVA) -cp $(CP) ScannerTest Phase1Tests/Phase1_expressions.txt > Phase1_expressions-output.txt
		cat Phase1Tests/Phase1_expressions.txt 
		cat -n Phase1_expressions-output.txt 

Phase1_order_of_ops: all
		$(JAVA) -cp $(CP) ScannerTest Phase1Tests/Phase1_order_of_ops.txt > Phase1_expressions-output.txt
		cat Phase1Tests/Phase1_order_of_ops.txt 
		cat -n Phase1_expressions-output.txt 

Phase1_statements: all
		$(JAVA) -cp $(CP) ScannerTest Phase1Tests/Phase1_statements.txt > Phase1_expressions-output.txt
		cat Phase1Tests/Phase1_statements.txt
		cat -n Phase1_expressions-output.txt 

clean:
		rm -f *.class *~ *.bak Lexer.java parser.java sym.java

Lexer.java: tokens.jflex
		$(JFLEX) tokens.jflex

parser.java: grammar.cup
		$(CUP) -interface < grammar.cup

parserD.java: grammar.cup
		$(CUP) -interface -dump < grammar.cup
