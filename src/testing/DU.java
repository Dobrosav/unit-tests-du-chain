package testing;

public class DU {
	private int rowNoDef=3;
	private int  rowNoUse=3;
	String variable;
	DUChainInterFace d;
	public DU(String variable, int rowNoDef, int rowNoUse) {
		super();
		this.variable = variable;
		this.rowNoDef = rowNoDef;
		this.rowNoUse = rowNoUse;
	}
	
	public DUChainInterFace getD() {
		return d;
	}

	public void setD(DUChainInterFace d) {
		this.d = d;
	}

	public String getVariable() {
		return d.getVariable();
	}
	
	public int getRowNoDef() {
		return d.getRowNoDef();
	}
	
	public int getRowNoUse() {
		return d.getRowNoUse();
	}
}

