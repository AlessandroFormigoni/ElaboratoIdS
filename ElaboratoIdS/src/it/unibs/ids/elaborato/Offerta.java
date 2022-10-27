package it.unibs.ids.elaborato;

import java.util.*;

public class Offerta {
	Calendar scadenza;
	Articolo[] coppiaArticoli = new Articolo[2];
	private ConfAppointment appuntamento = null;
	private static int globalNum = 0;
	private int id;
	
	public Offerta(Calendar scadenza, Articolo articoloA, Articolo articoloB) {
		this.scadenza = scadenza;
		this.coppiaArticoli[0] = articoloA;
		this.coppiaArticoli[1] = articoloB;
		this.id = globalNum;
		globalNum++;
	}
	
	public void accoppiaOfferta() {
		this.coppiaArticoli[0].changeStatoOfferta(StatiOfferta.ACCOPPIATA);
		this.coppiaArticoli[1].changeStatoOfferta(StatiOfferta.SELEZIONATA);
	}
	
	public void accettaOfferta() {
		this.coppiaArticoli[0].changeStatoOfferta(StatiOfferta.IN_SCAMBIO);
		this.coppiaArticoli[1].changeStatoOfferta(StatiOfferta.IN_SCAMBIO);
	}
	public void rifiutaOfferta() {
		this.coppiaArticoli[0].changeStatoOfferta(StatiOfferta.APERTA);
		this.coppiaArticoli[1].changeStatoOfferta(StatiOfferta.APERTA);
	}
	
	public void accettaAppuntamento() {
		this.coppiaArticoli[0].changeStatoOfferta(StatiOfferta.CHIUSA);
		this.coppiaArticoli[1].changeStatoOfferta(StatiOfferta.CHIUSA);
	}
	
	public void appuntamentoTimeout() {
		this.coppiaArticoli[0].changeStatoOfferta(StatiOfferta.APERTA);
		this.coppiaArticoli[1].changeStatoOfferta(StatiOfferta.APERTA);
	}
	
	public boolean isScaduta() {
		Calendar currentDate = Calendar.getInstance();
		return currentDate.after(scadenza);
	}
	
	public StatiOfferta statoArticoloAccoppiato() {
		return this.coppiaArticoli[0].getStatoOfferta();
	}
	
	public StatiOfferta statoArticoloSelezionato() {
		return this.coppiaArticoli[1].getStatoOfferta();
	}
	
	public Calendar getScadenza() {
		return this.scadenza;
	}
	
	public Utente getCreatoreArticolo(int index) {
		return this.coppiaArticoli[index].getCreatore();
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setAppointment(ConfAppointment app) {
		this.appuntamento = app;
		Calendar currentTime= Calendar.getInstance();
		Double giorniRimasti = (double)(scadenza.getTimeInMillis() - currentTime.getTimeInMillis())/AppointmentBaseController.MILLISEC_GIORNO;
		this.appuntamento.setScadenza((int)Math.round(giorniRimasti));
	}
	
	public ConfAppointment getAppuntamento() {
		return appuntamento;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
