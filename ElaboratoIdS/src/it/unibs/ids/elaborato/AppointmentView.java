package it.unibs.ids.elaborato;

import java.util.*;

import it.unibs.fp.mylib.InputDati;

public class AppointmentView {
	
	private static final String SEPARATORE = "-------------------------";
	
	public void makeAppointment(AppointmentController ac) {
		System.out.println(SEPARATORE);
		String piazza = InputDati.leggiStringaNonVuota("Inserisci il nome della piazza: ");
		String luoghi = InputDati.leggiStringaNonVuota("Inserisci i luoghi, separati da una virgola: ");
		String giorni = InputDati.leggiStringaNonVuota("Inserisci i giorni, separati da una virgola: ");
		String orari = InputDati.leggiStringaNonVuota("Inserisci gli intervalli orari, nel formato xx.yy, separati da una virgola: ");
		ac.creaAppuntamento(piazza, stringSeparator(luoghi), stringSeparator(giorni), floatSeparator(orari));
	}
	
	public void viewAppointments(AppointmentController ac) {
		ac.getAppuntamenti().stream().forEach(a -> {
			System.out.println(SEPARATORE);
			System.out.println("Piazza: " + a.getPiazza());
			System.out.println("Luoghi: " + a.getLuoghi());
			System.out.println("Giorni: " + a.getGiorni());
			System.out.println("Orari: " + a.getIntervalliOrari());
		});
		System.out.println(SEPARATORE);
	}
	
	private List<String> stringSeparator(String input) {
		return Arrays.asList(input.split(","));
		
	}
	
	private List<Float> floatSeparator(String input) {
		List<Float> list = new ArrayList<>();
		stringSeparator(input).stream().forEach(f -> list.add(Float.parseFloat(f)));
		return list;
	}
}
