package it.unibs.ids.elaborato;
import java.util.*;

public class AppointmentBaseController {
	public static final long MILLISEC_GIORNO = 86400000;
	private List<ConfAppointment> appointmentList;
	private List<Offerta> offerteList;
	private HashMap<Offerta, String> offerteUpdate;
	
	public AppointmentBaseController() {
		this.appointmentList = new ArrayList<>();
		this.offerteList = new ArrayList<>();
		this.offerteUpdate = new HashMap<>();
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
	
	public ConfAppointment creaUnicoAppuntamento(String piazza, String luogo, String giorno, Float[] orario, int scadenza) {
		try {
		ConfAppointment appuntamento = new ConfAppointment(piazza);
		
		ArrayList<String> luoghi = new ArrayList<String>();
		luoghi.add(luogo);
		appuntamento.setLuoghi(luoghi);
		ArrayList<String> giorni = new ArrayList<String>();
		giorni.add(giorno);
		appuntamento.setGiorni(giorni);
		ArrayList<Float[]> intervalliOrari = new ArrayList<Float[]>();
		intervalliOrari.add(orario);
		appuntamento.setIntervalliOrari(intervalliOrari);
		appuntamento.setScadenza(scadenza);
		return appuntamento;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	public ConfAppointment getAppointment(String piazza) {
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
        this.offerteUpdate.put(nuova, artA.getCreatore().getName());
	}
	
	public void accettaOfferta(int idOfferta, String nomeUtente, ConfAppointment appuntamento, boolean accettato) {
		Calendar callTime = Calendar.getInstance();
		Offerta off = getOffertaFromID(idOfferta);
		offerteUpdate.replace(off, nomeUtente);
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
		offerteUpdate.replace(off, nomeUtente);
		if(callTime.before(off.getScadenza())) {
			off.setAppointment(appuntamento);
		}
		else {
			System.out.println("Offerta scaduta");
			off.rifiutaOfferta();
		}
	}
	
	public void accettaAppuntamento(int idOfferta, String nomeUtente, boolean accettato, ConfAppointment appuntamento) {
		Calendar callTime = Calendar.getInstance();
		Offerta off = getOffertaFromID(idOfferta);
		offerteUpdate.replace(off, nomeUtente);
			if((off.getCreatoreArticolo(0).getName().equals(nomeUtente)) || off.getCreatoreArticolo(1).getName().equals(nomeUtente)) {		
				if(callTime.before(off.scadenza) && accettato) off.accettaAppuntamento();
				else if(callTime.before(off.scadenza) && !accettato) proponiNuovoAppuntamento(idOfferta, nomeUtente, appuntamento);
				else off.rifiutaOfferta();
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
	
	public boolean checkUpadate(Offerta offerta, String nomeUser){
		 if(!offerteUpdate.get(offerta).equals(nomeUser)) return true;
		 else return false;
	}
	
	public List<String> getListaPiazze(){
		ArrayList<String> piazzeList = new ArrayList<>();
		for(ConfAppointment ca : appointmentList) {
			piazzeList.add(ca.getPiazza());
		}
		return piazzeList;
	}
	
	public boolean controllaOra(float ora, List<Float[]> intervalliOrari) {
		for(Float[] intervallo:intervalliOrari) {
			if(intervallo[0]<=ora&&ora<intervallo[1]) return true;
		}
		return false;
	}
	
	public void setAppointments(List<ConfAppointment> appointments) {
		this.appointmentList = appointments;
	}

	public List<ConfAppointment> getAppointmentList() {
		return appointmentList;
	}

	public void setOfferteList(List<Offerta> offerteList) {
		this.offerteList = offerteList;
	}
	
	public void setUpdate(Offerta key, String value) {
		this.offerteUpdate.put(key, value);
	}

	public HashMap<Offerta, String> getOfferteUpdate() {
		return offerteUpdate;
	}
	
}
