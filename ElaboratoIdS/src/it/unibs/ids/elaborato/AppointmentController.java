package it.unibs.ids.elaborato;
import java.util.*;

public class AppointmentController {
	private List<ConfAppointment> appointmentList;
	
	public AppointmentController() {
		this.appointmentList = new ArrayList<>();
	}
	
	public void creaAppuntamento(String piazza, List<String> luoghi, List<String> giorni, List<Float> intervalliOrari) {
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
	
	public void addIntervallo(String piazza, float intervallo) {
		getAppointment(piazza).addIntervallo(Float.valueOf(intervallo));
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
}
