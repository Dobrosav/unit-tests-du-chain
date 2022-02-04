package simulator;

import java.util.ArrayList;

// Klasa koja Ä�uva promenljive koje treba ukljuÄ�iti u simulaciji
public class VarsToInclude {
	
	private ArrayList<String> vars;
	
	public VarsToInclude() {
		vars = new ArrayList<>();
	}
	
	// Odvaja promenljive razdvojene razmakom
	public void scanVars(String varsToInclude) {
		int i = 0;
		String var = "";
		
		while (i < varsToInclude.length()) {
			if (varsToInclude.charAt(i) != ' ') 
				var += varsToInclude.charAt(i);
			else {
				if (var != "") vars.add(var);
				var = "";
			}
			i++;
		}
		if (var != "") vars.add(var);
	}
	
	// Provera da li promenljivu treba ukljuÄ�iti u simulaciju
	public boolean isIncluded(String var) {
		if (vars.contains(var)) return true;
		return false;
	}
	
	// Ako nijedna promenljiva nije navedena, potrebno je ukljuÄ�iti sve
	public boolean includeAll() {
		if (vars.size() == 0) return true;
		return false;
	}
	
	// Ispis promenljivih (koristi se samo za testiranje)
	

}
