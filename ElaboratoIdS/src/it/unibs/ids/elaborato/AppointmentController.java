package it.unibs.ids.elaborato;
import java.util.*;

public class AppointmentController {
	public static final long MILLISEC_GIORNO = 86400000;
	private List<ConfAppointment> appointmentList;
	private List<Offerta> offerteList;
	
	public AppointmentController() {
		this.appointmentList = new ArrayList<>();
		this.offerteList = new ArrayList<>();
	}
	
	public void creaAppuntamento(String piazza, List<String> luoghi, List<String> giorni, List<Float[]> intervalliOrari) {
		try {
		ConfAppointment appuntamento = new ConfAppointment(piazza);
		appuntamento.setLuoghi(luoghi);
		appuntamento.setGiorni(giorni);
		appuntamento.setIntervalliOrari(intervalliOrari);
		appointmentList.add(appuntamento);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addLuogo(String piazza, String luogo) {
		getAppointment(piazza).addLuogo(luogo);
	}
	
	public void addGiorno(String piazza, String luogo) {
		getAppointment(piazza).addGiorno(luogo);
	}
	
	public void addIntervallo(String piazza, float inizio, float fine) {
		getAppointment(piazza).addIntervallo(Float.valueOf(inizio), Float.valueOf(fine));
	}
	
	public void setScadenza(String piazza, int scadenza) {
		getAppointment(piazza).setScadenza(scadenza);
	}
	private ConfAppointment getAppointment(String piazza) {
		return this.appointmentList.stream().filter(a -> a.getPiazza().equals(piazza)).findFirst().get();
	}
	
	public List<ConfAppointment> getAppuntamenti() {
		return this.appointmentList;
	}
	
	public void creaOfferta(long scadenza, Articolo artA, Articolo artB) {
		Calendar currentTime = Calendar.getInstance();
        
        for(int i=1; i<=scadenza; i++) {
	        long init = currentTime.getTimeInMillis();
	        init += MILLISEC_GIORNO;
	        currentTime.setTimeInMillis(init);
        }
        Offerta nuova = new Offerta(currentTime, artA, artB);
        nuova.accoppiaOfferta();
        this.offerteList.add(nuova);
	}
	
	public void accettaOfferta(int idOfferta, String nomeUtente, ConfAppointment appuntamento, boolean accettato) {
		Calendar callTime = Calendar.getInstance();
		Offerta off = getOffertaFromID(idOfferta);
			if(off.getCreatoreArticolo(1).getName().equals(nomeUtente) && callTime.before(off.getScadenza())) {
				if(accettato) {
					off.accettaOfferta();
					off.setAppointment(appuntamento);
				}
				else off.rifiutaOfferta();
			}
			else
				System.out.println("Errore");
	}
	
	public void proponiNuovoAppuntamento(int idOfferta, String nomeUtente, ConfAppointment appuntamento) {
		Calendar callTime = Calendar.getInstance();
		Offerta off = getOffertaFromID(idOfferta);
		if(callTime.before(off.getScadenza())) {
			off.setAppointment(appuntamento);
		}
	}
	
	public void accettaAppuntamento(int idOfferta, String nomeUtente, boolean accettato) {
		Calendar callTime = Calendar.getInstance();
		Offerta off = getOffertaFromID(idOfferta);
			if((off.getCreatoreArticolo(0).getName().equals(nomeUtente)) || off.getCreatoreArticolo(1).getName().equals(nomeUtente)) {		
				if(callTime.before(off.scadenza) && accettato) off.accettaAppuntamento();
				else if(callTime.after(off.scadenza) || !accettato) off.rifiutaOfferta();
			}
			else
				System.out.println("Errore");
		
	}
	
	public List<Offerta> getOfferteDaNome(String nomeUtente) {
		List<Offerta> offerte = new ArrayList<>();
		for(Offerta off : offerteList) {
			if(off.getCreatoreArticolo(0).getName().equals(nomeUtente) || off.getCreatoreArticolo(1).getName().equals(nomeUtente))
				offerte.add(off);
		}
		return offerte;
	}
	
	public Offerta getOffertaFromID(int id) {
		return offerteList.stream().filter(off -> off.getId()==id).findAny().get();
	}
	
	public List<Offerta> getOfferteList(){
		return this.offerteList;
	}
	
}
