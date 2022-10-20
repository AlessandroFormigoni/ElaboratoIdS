package it.unibs.ids.elaborato;

import java.util.ArrayList;
import java.util.List;

public class ConfAppointment {
	
	public static final float IN_PUNTO = 0.0f;
	public static final float MEZZA = 0.3f;
	public static final float QUASI_MEZZA =0.29999f;
	
	
	private String piazza;
	private List<String> luoghi;
	private List<String> giorni;
	private List<Float[]> intervalliOrari;
	private int scadenza;
	
	ConfAppointment(String piazza){
		this.piazza = piazza;
		this.luoghi = new ArrayList<>();
		this.giorni = new ArrayList<>();
		this.intervalliOrari = new ArrayList<>();
	}
	
	public String getPiazza() {
		return piazza;
	}
	
	public void setPiazza(String piazza) {
		this.piazza = piazza;
	}
	
	public List<String> getLuoghi() {
		return luoghi;
	}
	
	public void setLuoghi(List<String> luoghi) {
		this.luoghi = luoghi;
	}
	
	public List<String> getGiorni() {
		return giorni;
	}
	
	public void setGiorni(List<String> giorni) {
		this.giorni = giorni;
	}
	
	public List<Float[]> getIntervalliOrari() {
		return intervalliOrari;
	}
	
	public void setIntervalliOrari(List<Float[]> intervalliOrari) {
		this.intervalliOrari = intervalliOrari;
	}
	
	public int getScadenza() {
		return scadenza;
	}
	
	public void setScadenza(int scadenza) {
		this.scadenza = scadenza;
	}
	
	public void addIntervallo(float inizio, float fine) {
		int parteIntInizio = (int)inizio;
		float daControllareInizio = inizio-parteIntInizio;
		int parteIntFine = (int)fine;
		float daControllareFine = fine-parteIntFine;
		if((QUASI_MEZZA<=daControllareInizio&&daControllareInizio<=MEZZA)||daControllareInizio==IN_PUNTO)
			if((QUASI_MEZZA<=daControllareFine&&daControllareFine<=MEZZA)||daControllareFine==IN_PUNTO) {
				Float[] intervallo = {inizio, fine};
				intervalliOrari.add(intervallo);
			}
		
	}
	
	public void addGiorno(String giorno) {
		if(!giorni.contains(giorno)) giorni.add(giorno);
	}
	
	public void addGiorno(GiornoSettimana giorno) {
		if(!giorni.contains(giorno.name())) giorni.add(giorno.name());
	}
	
	public void addLuogo(String luogo) {
		if(!luoghi.contains(luogo))luoghi.add(luogo);
		
		
	}	

}
