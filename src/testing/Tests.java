package testing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.*;
import helpers.BreakStmt;
import helpers.IfElseStmt;
import helpers.Jump;
import helpers.SwitchCases;
import helpers.BreakStmt.BreakType;
import simulator.DUChain;
import simulator.Definition;
import simulator.Lcsaj;
import simulator.Lexer;
import simulator.Simulator;
import simulator.Token;
import simulator.Use;
import simulator.VarsToInclude;
import symtable.Scope;
import symtable.Scope.ScopeType;
import symtable.SymbolTable;

public class Tests {
	private static BreakStmt breakStmt=new BreakStmt(2, 3, BreakType.LOOP_BREAK, null);
	private static IfElseStmt ifElseStmt=new IfElseStmt(2);
	private static Jump jump=new Jump(3, 10, false, 1);
	private static SwitchCases switchCases=new SwitchCases(4);
	private static Scope scope=new Scope(null, 5, ScopeType.REGULAR);
	private static SymbolTable symbolTable=new SymbolTable();
	private static Definition definition=new Definition("Varijabla1", 7, scope);
	private static DUChain duChain=new DUChain("Varijabla2", 5, 20);
	private static Lcsaj lcsaj=new Lcsaj(12, 15, 30);
	private static Lexer lexer=new Lexer();
	private static Simulator simulator=new Simulator();
	private static Token token=new Token("token", 3);
	private static Use use=new Use("Varijabla3", 10, 'c', scope);
	private static VarsToInclude varsToInclude=new VarsToInclude();
	private static ArrayList<IfElseStmt> listIfElse=new ArrayList<>();
	private static ArrayList<BreakStmt> listBreakStmt=new ArrayList<>();
	private static String s="int x;\n"+"int y;\n"+"y=5;\n"+"x=2;\n"+"y=y+x;\n"+"{string str=\"etf\";\n\t"+"for(int i=0;i<5;i++)\n"+"char a=\'a\'\n";
	private static Scope s1=new Scope(scope, 2, ScopeType.LOOP);
	private static Scope s2=new Scope(scope,2,ScopeType.LOOP);
	private static Scope s3=new Scope(s1,2,ScopeType.IF_ELSE);
	private static Scope s4=new Scope(s1,2,ScopeType.SWITCH);
	private static Scope s5=new Scope(s1,2,ScopeType.CASE);
	private static ArrayList<Jump>listaJump=new ArrayList<>();
	private static String kod2="double rabat, ukupno, finalnaCena, popust, cena;\n"+"cena=0.1;\n"+"ukupno=0;\n"+
	"input(cena)\n"+"while(cena!=-1){\n"+"ukupno+=cena;\n"+"input(cena);\n"+"}\n"+"print(\"Ukupna cena: \""+"+ ukupno);\n"+
	"if(ukupno>15.00)\n"+"popust=(rabat+ukupno)+0.50;\n"+"else\n"+"popust=rabat*ukupno;\n"+"finalnaCena=ukupno-popust;\n"+
	"print(\"Finalni iznos: \"+finalnaCena);\n";
	private static String vars2="rabat ukupno finalnaCena popust cena";
	private static String kod3="int K;\n"+"int M[];\n"+"int N;\n"+"int ID=0;\n"+"int IG=N-1;\n"+"while(ID<=IG){\n"+"int IS=(ID+IG)/2;\n"+
	"if(K==M[IS]) return IS;\n"+"else if(K<M[IS]) IG=IS;\n"+"else ID=IS;\n"+"}\n"+"return -1;\n";
	private static String vars3="K M N ID IG IS";
	private static String kod4="public void f(int x,int y,char c);\n"+"final int i=5;\n"+"do{\n"+"int a=this->br;\n"+"double b=klasa.polje;\n"+"i--;\n"+"}while(i>2)\n";
	private static String vars4="i a b";
	private static ArrayList<String> columnValues = new ArrayList<String>();
	private static ArrayList<String> columnValues2 = new ArrayList<String>();

	
	@Before
	public void before() {
		listIfElse.add(ifElseStmt);
		IfElseStmt i2=new IfElseStmt(4);
		i2.setIfScope(1);
		listIfElse.add(i2);
		
		listBreakStmt.add(breakStmt);
		Scanner scanner;
		try {
			scanner = new Scanner(new File("C:\\Users\\vd180005d\\Desktop\\Book1.csv"));
		
        while(scanner.hasNextLine()) {
            String values = scanner.nextLine();
            columnValues.add(values);
        }
        scanner = new Scanner(new File("C:\\Users\\vd180005d\\Desktop\\Book2.csv"));
        while(scanner.hasNextLine()) {
            String values = scanner.nextLine();
            columnValues2.add(values);
        }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//lexer.getTopObject("a");

	}
	
	@Test
	public void testBreakStmt(){
		assertEquals(2, breakStmt.getRowNo());
		assertEquals(3,breakStmt.getScopeId());
		assertEquals(BreakType.LOOP_BREAK,breakStmt.getType());
		assertEquals(null,breakStmt.getBelongsToScope());
	}
	@Test
	public void testSetStartSeq() {
		for(int i=1;i<4;i++) {
			String[] s=columnValues.get(i).split(",");
			int start=Integer.parseInt(s[0]);
			//System.out.println(start);
			int expected=Integer.parseInt(s[1]);
			lcsaj.setStartSeq(start);
			assertEquals(expected,lcsaj.getStartSeq());
		}
	}
	@Test
	public void testIsKeyWordLexer() {
		for(int i=1;i<5;i++) {
			String[] s=columnValues2.get(i).split(",");
			String rec=s[0];
			boolean b=Boolean.parseBoolean(s[1].toLowerCase());
			assertEquals(b,lexer.isKeyword(rec));
		}
			//System.out.println(columnValues2.get(i));
		//assertEquals(true,lexer.isKeyword("enum"));
	}

	@Test
	public void testIfElseStmt(){
		assertEquals(2,ifElseStmt.getParentIfElse());
		assertEquals(-1,ifElseStmt.getElseScope());
		assertEquals(-1,ifElseStmt.getIfScope());

		ifElseStmt.setElseScope(5);
		assertEquals(5,ifElseStmt.getElseScope());
		ifElseStmt.setIfScope(2);
		assertEquals(2,ifElseStmt.getIfScope());
		int a=5,b=2;
		assertEquals(true,ifElseStmt.areExclusive(a, b));
		assertEquals(true,ifElseStmt.areExclusive(b, a));
		assertEquals(false,ifElseStmt.areExclusive(1, 2));
	}
	@Test
	public void testJump() {
		assertEquals(3, jump.getStart());
		assertEquals(10,jump.getEnd());
		assertEquals(false,jump.isMandatory());
		assertEquals(1,jump.getPriority());
	}
	@Test
	public void testSwitchCases() {
		assertEquals(4,switchCases.getSwitchId());
		switchCases.addCase(5);
		assertEquals(false,switchCases.contains(2));
		assertEquals(true,switchCases.contains(5));
	}
	
	@Test
	public void testScope() {
		assertEquals(null,scope.getParentScope());
		assertEquals(0,scope.getId());
		assertEquals(5,scope.getLevel());
		assertEquals(ScopeType.REGULAR,scope.getType());
		scope.setRowStart(1);
		scope.setRowEnd(5);
		assertEquals(1,scope.getRowStart());
		assertEquals(5,scope.getRowEnd());
		assertEquals(false,scope.isDefault());
		scope.setDefault(true);
		assertEquals(true,scope.isDefault());
		assertEquals(false,scope.isDoLoop());
		scope.setDoLoop(true);
		assertEquals(true,scope.isDoLoop());
		assertEquals(false,scope.isElse());
		scope.setElse(true);
		assertEquals(true,scope.isElse());
		assertEquals(false,scope.isOneStmtScope());
		scope.setOneStmtScope(true);
		assertEquals(true,scope.isOneStmtScope());
		scope.addVar("asd");
		assertEquals(true,scope.containsVar("asd"));
		scope.addVar("qwe");
		assertEquals("asd qwe ",scope.getVars());
	}
	@Test
	public void testSymbolTable() {
		assertEquals(0,symbolTable.numberOfScopes());
		symbolTable.addScope(scope);
		assertEquals(scope, symbolTable.getScope(0));
		assertEquals(1,symbolTable.numberOfScopes());
		assertEquals(false,symbolTable.areInSameSwitch(1, 2));
		assertEquals(true,symbolTable.isFirstCase(1, 6));
		assertEquals(ifElseStmt,symbolTable.belongsToIfElse(listIfElse, -1));
		assertEquals(null,symbolTable.belongsToIfElse(listIfElse, 2));
		listIfElse.clear();
		assertEquals(null,symbolTable.belongsToIfElse(listIfElse, 1));
		assertEquals(scope,symbolTable.getScopeWithId(0));
		assertEquals(null,symbolTable.getScopeWithId(-1));
		s2.setRowStart(1);
		s2.setDoLoop(true);
		s3.setRowEnd(1);
		symbolTable.addScope(s5);
		ArrayList<Integer> lista=symbolTable.findSeqStarts();
		symbolTable.addScope(s1);
		symbolTable.addScope(s2);
		symbolTable.addScope(s3);
		symbolTable.addScope(s4);
		Scope s6=new Scope(scope,6,ScopeType.CASE);
		s6.setRowStart(5);
		symbolTable.addScope(s6);
		lista=symbolTable.findSeqStarts();
		assertEquals(false,symbolTable.isFirstCase(0, 2000));
		assertEquals(false,symbolTable.areInSameSwitch(1, 2));
		assertEquals(false,symbolTable.areInSameSwitch(4, 4));
		BreakStmt b1=new BreakStmt(1, 2, BreakType.SWITCH_BREAK, s1);
		BreakStmt b2=new BreakStmt(1,2,BreakType.LOOP_CONT,s2);
		listBreakStmt.add(b1);
		listBreakStmt.add(b2);
		listaJump=symbolTable.findJumps(listIfElse, listBreakStmt);
	}
	
	@Test
	public void testDefinition() {
		assertEquals("Varijabla1",definition.getVariable());
		assertEquals(7,definition.getRowNo());
		assertEquals(scope,definition.getScope());
	}
	@Test
	public void testDUChain() {
		assertEquals("Varijabla2",duChain.getVariable());
		assertEquals(5,duChain.getRowNoDef());
		assertEquals(20,duChain.getRowNoUse());
	}
	@Test
	public void testLcsaj() {
		assertEquals(12,lcsaj.getStartSeq());
		assertEquals(15,lcsaj.getEndSeq());
		assertEquals(30,lcsaj.getJump());
		lcsaj.setStartSeq(1);
		lcsaj.setEndSeq(2);
		lcsaj.setJump(3);
		assertEquals(1,lcsaj.getStartSeq());
		assertEquals(2,lcsaj.getEndSeq());
		assertEquals(3,lcsaj.getJump());
	}
	@Test
	public void testLexer() {
		assertEquals(null,lexer.nextToken());
		assertEquals(null,lexer.peakNextToken());
		lexer.scanCode(s);
		assertEquals(true,lexer.isKeyword("continue"));
		assertEquals(false,lexer.isKeyword("saaa"));
		assertEquals(true,lexer.isType("Boolean"));
		assertEquals(true,lexer.isType("boolean"));
		assertEquals(false, lexer.isType("asd"));
		lexer.addUserType("asd");
		assertEquals(true,lexer.isType("asd"));
		assertEquals(true,lexer.isAccessModifier("private"));
		assertEquals(false,lexer.isAccessModifier("asdadsd"));
		assertEquals(true,lexer.isLiteral("static"));
		assertEquals(false,lexer.isLiteral("ddd"));
		assertEquals(true,lexer.isOperator1('('));
		assertEquals(false,lexer.isOperator1('1'));
		assertEquals(true,lexer.isOperator2('+'));
		assertEquals(false,lexer.isOperator2('2'));
		assertEquals(true,lexer.isBlockBracket('{'));
		assertEquals(false,lexer.isBlockBracket('a'));
		assertEquals(true,lexer.isSemiColon(';'));
		assertEquals(false,lexer.isSemiColon('a'));
		assertEquals(true,lexer.isValue("null"));
		assertEquals(false,lexer.isValue("asdasd"));
		assertEquals(true,lexer.isValue("\'"));
		assertEquals(true,lexer.isValue("1"));
		assertEquals(false,lexer.isConst("qwe"));
		lexer.addConstant("qwe");
		assertEquals(true,lexer.isConst("qwe"));
		assertEquals(true,lexer.isVariable("S"));
		assertEquals(true,lexer.isVariable("b"));
		assertEquals(true,lexer.isVariable("_"));
		assertEquals(true,lexer.isVariable("this"));
		assertEquals(false,lexer.isVariable("1"));
		
		assertEquals(true,lexer.hasThis("this->a"));
		assertEquals(false,lexer.hasThis("a"));
		assertEquals("asd",lexer.getTopObject("asd.qwe"));
	}
	@Test
	public void testSimulator() {
		simulator.startStepByStep();
		simulator.analyseCode(s,"x y str c" );
		simulator.analyseCode(kod2, vars2);
		simulator.addDuChain("ID", 1, 3);
		simulator.addDuChain("ID", 1, 4);
		simulator.addDuChain("ID", 7, 3);
		simulator.addDuChain("ID", 7, 4);
		simulator.addDuChain("IG", 2, 3);
		simulator.addDuChain("IG", 2, 4);
		simulator.addDuChain("IG", 6, 3);
		simulator.addDuChain("IG", 6, 4);
		Scope sc1=new Scope(null, 1, ScopeType.SWITCH);
		Scope sc2=new Scope(sc1,2,ScopeType.LOOP);
		Token t1=new Token("K", 1);
		Token t2=new Token("N",3);
		simulator.addDefinition(t1, sc1);
		simulator.addDefinition(t2, sc2);
		simulator.analyseCode(kod3, vars3);
		simulator.analyseCode(kod4, vars4);
		simulator.addBreakStmt(sc2, 2);
		simulator.addBreakStmt(sc1, 3);
		simulator.addBreakStmt(s3, 5);
		simulator.addBreakStmt(null, 2);
		simulator.addContinueStmt(null, 2);
		simulator.addContinueStmt(sc2, 5);
		simulator.addContinueStmt(sc1, 5);
		assertEquals(true,simulator.isInScope(scope, 0));
		assertEquals(false,simulator.isInScope(sc1, 0));
		assertEquals(true,simulator.isMandatory(scope, scope, scope));
		assertEquals(false,simulator.isMandatory(sc2, sc1, scope));
		assertEquals(true,simulator.isMandatory(scope, sc1, sc2));
		Scope sc3=new Scope(sc2,2,ScopeType.CLASS);
		Scope sc4=new Scope(sc3,5,ScopeType.CASE);
		assertEquals(28,simulator.getClassScope(sc4));
		assertEquals(0,simulator.getClassScope(null));
		Scope sc5=new Scope(sc2,1,ScopeType.FUNCTION);
		Scope sc6=new Scope(sc5,3,ScopeType.CASE);
		assertEquals(30,simulator.getFunctionScope(sc6));
		assertEquals(0,simulator.getFunctionScope(null));
		assertEquals(null,simulator.getLoopScopeCond(null, 1));
		assertEquals(sc2,simulator.getScopeWithLoop(sc4, sc6));
		assertEquals(s1,simulator.getLoopScopeCond(s1, 0));
		assertEquals(null,simulator.getScopeWithLoop(sc4, sc5));
		Scope sc7=new Scope(sc1,2,ScopeType.CASE);
		Scope sc8=new Scope(sc7,5,ScopeType.CLASS);
		Scope sc9=new Scope(sc1,1,ScopeType.FUNCTION);
		Scope sc10=new Scope(sc9,3,ScopeType.IF_ELSE);
		assertEquals(sc1,simulator.getScopeWithSwitch(sc8, sc10));
		assertEquals(null,simulator.getScopeWithSwitch(sc9, sc1));
		assertEquals(true,simulator.sameVars("this->a", "this->a"));
		assertEquals(false,simulator.sameVars("this->b", "this->a"));
		simulator.addLcsaj(1, 2, 5);
		assertEquals(false,simulator.jumpExistsBetween(listaJump, 100, 200, 20));
		String brDef="y (red 3)\nx (red 4)\ny (red 5)\ny (red 3)\nx (red 4)\ny (red 5)\ncena (red 2)\nukupno (red 3)\nukupno (red 6)\npopust (red 11)\npopust (red 13)\nfinalnaCena (red 14)\ny (red 3)\nx (red 4)\ny (red 5)\ncena (red 2)\nukupno (red 3)\nukupno (red 6)\npopust (red 11)\npopust (red 13)\nfinalnaCena (red 14)\nM (red 2)\nID (red 4)\nIG (red 5)\nIS (red 7)\nIG (red 9)\nID (red 10)\ny (red 3)\nx (red 4)\ny (red 5)\ni (red 7)\ni (red 7)\ncena (red 2)\nukupno (red 3)\nukupno (red 6)\npopust (red 11)\npopust (red 13)\nfinalnaCena (red 14)\nM (red 2)\nID (red 4)\nIG (red 5)\nIS (red 7)\nIG (red 9)\nID (red 10)\na (red 4)\nb (red 5)\n\nBroj definicija: 46";
		assertEquals(brDef,simulator.getDefinitions());
		String cpUpotreba="y (red 5, c-upotreba)\n" + 
				"x (red 5, c-upotreba)\n" + 
				"y (red 5, c-upotreba)\n" + 
				"x (red 5, c-upotreba)\n" + 
				"cena (red 4, p-upotreba)\n" + 
				"cena (red 5, p-upotreba)\n" + 
				"ukupno (red 6, c-upotreba)\n" + 
				"cena (red 6, p-upotreba)\n" + 
				"cena (red 7, p-upotreba)\n" + 
				"ukupno (red 9, p-upotreba)\n" + 
				"ukupno (red 10, p-upotreba)\n" + 
				"popust (red 11, p-upotreba)\n" + 
				"rabat (red 11, p-upotreba)\n" + 
				"ukupno (red 11, p-upotreba)\n" + 
				"rabat (red 13, p-upotreba)\n" + 
				"ukupno (red 13, p-upotreba)\n" + 
				"ukupno (red 14, p-upotreba)\n" + 
				"popust (red 14, p-upotreba)\n" + 
				"finalnaCena (red 15, p-upotreba)\n" + 
				"y (red 5, c-upotreba)\n" + 
				"x (red 5, c-upotreba)\n" + 
				"cena (red 4, p-upotreba)\n" + 
				"cena (red 5, p-upotreba)\n" + 
				"ukupno (red 6, c-upotreba)\n" + 
				"cena (red 6, p-upotreba)\n" + 
				"cena (red 7, p-upotreba)\n" + 
				"ukupno (red 9, p-upotreba)\n" + 
				"ukupno (red 10, p-upotreba)\n" + 
				"popust (red 11, p-upotreba)\n" + 
				"rabat (red 11, p-upotreba)\n" + 
				"ukupno (red 11, p-upotreba)\n" + 
				"rabat (red 13, p-upotreba)\n" + 
				"ukupno (red 13, p-upotreba)\n" + 
				"ukupno (red 14, p-upotreba)\n" + 
				"popust (red 14, p-upotreba)\n" + 
				"finalnaCena (red 15, p-upotreba)\n" + 
				"N (red 5, p-upotreba)\n" + 
				"ID (red 6, p-upotreba)\n" + 
				"IG (red 6, p-upotreba)\n" + 
				"ID (red 7, p-upotreba)\n" + 
				"IG (red 7, p-upotreba)\n" + 
				"K (red 8, p-upotreba)\n" + 
				"M (red 8, p-upotreba)\n" + 
				"IS (red 8, p-upotreba)\n" + 
				"IS (red 8, p-upotreba)\n" + 
				"K (red 9, p-upotreba)\n" + 
				"M (red 9, p-upotreba)\n" + 
				"IS (red 9, p-upotreba)\n" + 
				"IG (red 9, p-upotreba)\n" + 
				"IS (red 9, p-upotreba)\n" + 
				"IS (red 10, p-upotreba)\n" + 
				"y (red 5, c-upotreba)\n" + 
				"x (red 5, c-upotreba)\n" + 
				"i (red 7, p-upotreba)\n" + 
				"i (red 7, c-upotreba)\n" + 
				"cena (red 4, p-upotreba)\n" + 
				"cena (red 5, p-upotreba)\n" + 
				"ukupno (red 6, c-upotreba)\n" + 
				"cena (red 6, p-upotreba)\n" + 
				"cena (red 7, p-upotreba)\n" + 
				"ukupno (red 9, p-upotreba)\n" + 
				"ukupno (red 10, p-upotreba)\n" + 
				"popust (red 11, p-upotreba)\n" + 
				"rabat (red 11, p-upotreba)\n" + 
				"ukupno (red 11, p-upotreba)\n" + 
				"rabat (red 13, p-upotreba)\n" + 
				"ukupno (red 13, p-upotreba)\n" + 
				"ukupno (red 14, p-upotreba)\n" + 
				"popust (red 14, p-upotreba)\n" + 
				"finalnaCena (red 15, p-upotreba)\n" + 
				"N (red 5, p-upotreba)\n" + 
				"ID (red 6, p-upotreba)\n" + 
				"IG (red 6, p-upotreba)\n" + 
				"ID (red 7, p-upotreba)\n" + 
				"IG (red 7, p-upotreba)\n" + 
				"K (red 8, p-upotreba)\n" + 
				"M (red 8, p-upotreba)\n" + 
				"IS (red 8, p-upotreba)\n" + 
				"IS (red 8, p-upotreba)\n" + 
				"K (red 9, p-upotreba)\n" + 
				"M (red 9, p-upotreba)\n" + 
				"IS (red 9, p-upotreba)\n" + 
				"IG (red 9, p-upotreba)\n" + 
				"IS (red 9, p-upotreba)\n" + 
				"IS (red 10, p-upotreba)\n" + 
				"x (red 1, p-upotreba)\n"+
				"y (red 1, p-upotreba)\n"+
				"c (red 1, p-upotreba)\n"+
				"\n" + 
				"Broj c-upotreba: 12\n" + 
				"Broj p-upotreba: 76";
		assertEquals(cpUpotreba,simulator.getUses());
		String brDU="[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 6, 6]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 6, 6]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[ukupno, 6, 6]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[M, 2, 8]\n" + 
				"[M, 2, 9]\n" + 
				"[ID, 4, 6]\n" + 
				"[ID, 4, 7]\n" + 
				"[IG, 5, 6]\n" + 
				"[IG, 5, 7]\n" + 
				"[IG, 5, 9]\n" + 
				"[IS, 7, 8]\n" + 
				"[IS, 7, 8]\n" + 
				"[IS, 7, 9]\n" + 
				"[IS, 7, 9]\n" + 
				"[IS, 7, 10]\n" + 
				"[IG, 9, 6]\n" + 
				"[IG, 9, 7]\n" + 
				"[IG, 9, 9]\n" + 
				"[ID, 10, 6]\n" + 
				"[ID, 10, 7]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 6, 6]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[ukupno, 6, 6]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[M, 2, 8]\n" + 
				"[M, 2, 9]\n" + 
				"[ID, 4, 6]\n" + 
				"[ID, 4, 7]\n" + 
				"[IG, 5, 6]\n" + 
				"[IG, 5, 7]\n" + 
				"[IG, 5, 9]\n" + 
				"[IS, 7, 8]\n" + 
				"[IS, 7, 8]\n" + 
				"[IS, 7, 9]\n" + 
				"[IS, 7, 9]\n" + 
				"[IS, 7, 10]\n" + 
				"[IG, 9, 6]\n" + 
				"[IG, 9, 7]\n" + 
				"[IG, 9, 9]\n" + 
				"[ID, 10, 6]\n" + 
				"[ID, 10, 7]\n" + 
				"[y, 3, 5]\n" + 
				"[x, 4, 5]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[cena, 2, 4]\n" + 
				"[cena, 2, 5]\n" + 
				"[cena, 2, 6]\n" + 
				"[cena, 2, 7]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 3, 6]\n" + 
				"[ukupno, 3, 9]\n" + 
				"[ukupno, 3, 10]\n" + 
				"[ukupno, 3, 11]\n" + 
				"[ukupno, 3, 13]\n" + 
				"[ukupno, 3, 14]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[ukupno, 6, 6]\n" + 
				"[ukupno, 6, 9]\n" + 
				"[ukupno, 6, 10]\n" + 
				"[ukupno, 6, 11]\n" + 
				"[ukupno, 6, 13]\n" + 
				"[ukupno, 6, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 11, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[popust, 13, 14]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[finalnaCena, 14, 15]\n" + 
				"[M, 2, 8]\n" + 
				"[M, 2, 9]\n" + 
				"[ID, 4, 6]\n" + 
				"[ID, 4, 7]\n" + 
				"[IG, 5, 6]\n" + 
				"[IG, 5, 7]\n" + 
				"[IG, 5, 9]\n" + 
				"[IS, 7, 8]\n" + 
				"[IS, 7, 8]\n" + 
				"[IS, 7, 9]\n" + 
				"[IS, 7, 9]\n" + 
				"[IS, 7, 10]\n" + 
				"[IG, 9, 6]\n" + 
				"[IG, 9, 7]\n" + 
				"[IG, 9, 9]\n" + 
				"[ID, 10, 6]\n" + 
				"[ID, 10, 7]\n" +
				"\n" + 
				"Broj DU-lanaca: 329";
		assertEquals(brDU,simulator.getDuChains());
		String resenje="(1, 5, 9)\n" + 
				"(1, 8, 5)\n" + 
				"(5, 5, 9)\n" + 
				"(5, 8, 5)\n" + 
				"(1, 5, 9)\n" + 
				"(1, 8, 5)\n" + 
				"(1, 5, 9)\n" + 
				"(1, 8, 5)\n" + 
				"(1, 6, 12)\n" + 
				"(5, 5, 9)\n" + 
				"(5, 8, 5)\n" + 
				"(5, 5, 9)\n" + 
				"(5, 8, 5)\n" + 
				"(5, 6, 12)\n" + 
				"(6, 8, 5)\n" + 
				"(6, 8, 5)\n" + 
				"(6, 6, 12)\n" + 
				"(9, 11, 6)\n" + 
				"(10, 11, 6)\n" + 
				"(11, 11, 6)\n" + 
				"(1, 5, 9)\n" + 
				"(1, 8, 5)\n" + 
				"(1, 5, 9)\n" + 
				"(1, 8, 5)\n" + 
				"(1, 6, 12)\n" + 
				"(1, 5, 9)\n" + 
				"(1, 8, 5)\n" + 
				"(1, 6, 12)\n" + 
				"(1, 7, 3)\n" + 
				"(5, 5, 9)\n" + 
				"(5, 8, 5)\n" + 
				"(5, 5, 9)\n" + 
				"(5, 8, 5)\n" + 
				"(5, 6, 12)\n" + 
				"(5, 5, 9)\n" + 
				"(5, 8, 5)\n" + 
				"(5, 6, 12)\n" + 
				"(5, 7, 3)\n" + 
				"(6, 8, 5)\n" + 
				"(6, 8, 5)\n" + 
				"(6, 6, 12)\n" + 
				"(6, 8, 5)\n" + 
				"(6, 6, 12)\n" + 
				"(6, 7, 3)\n" + 
				"(9, 11, 6)\n" + 
				"(9, 11, 6)\n" + 
				"(10, 11, 6)\n" + 
				"(10, 11, 6)\n" + 
				"(11, 11, 6)\n" + 
				"(11, 11, 6)\n" + 
				"(1, 2, 5)\n" + 
				"\n" + 
				"Broj LCSAJ sekvenci: 51";
		assertEquals(resenje,simulator.getLcsaj());
		assertEquals(true,simulator.isStepByStep());
		assertEquals(false,simulator.breakExistsBetween(new Definition("x",4,sc9), new Use("x", 1,'a', sc9), 3));
		assertEquals(0,simulator.getScopeWhereVarIsDef(sc9, kod4));
	}
	@Test
	public void testToken() {
		assertEquals("token",token.getToken());
		assertEquals(3,token.getRowNo());
		token.setRowNo(2);
		token.setToken("token1");
		assertEquals(2,token.getRowNo());
		assertEquals("token1",token.getToken());
	}
	@Test
	public void testUse() {
		assertEquals("Varijabla3",use.getVariable());
		assertEquals('c',use.getType());
		assertEquals(10,use.getRowNo());
		assertEquals(scope,use.getScope());
		assertEquals(-1,use.getConditionFor());
		use.setConditionFor(2);
		assertEquals(2,use.getConditionFor());
	}
	@Test
	public void testVars() {
		assertEquals(true,varsToInclude.includeAll());
		varsToInclude.scanVars("double x,y,z;");
		assertEquals(false,varsToInclude.isIncluded("x"));
		assertEquals(true,varsToInclude.isIncluded("double"));
		assertEquals(false,varsToInclude.includeAll());
	
	}
	
}
