package it.unibs.ids.elaborato;

import java.util.*;

import it.unibs.fp.mylib.InputDati;

public class AppointmentView {
	
	private static final String SEPARATORE = "-------------------------";
	private static final float timeUpperLimit = 23.599999999999f;
	private static final float minutesUpperLimit = 0.599999999999f;
	
	public void makeAppointment(AppointmentController ac) {
		System.out.println(SEPARATORE);
		String piazza = InputDati.leggiStringaNonVuota("Inserisci il nome della piazza: ");
		String luoghi = InputDati.leggiStringaNonVuota("Inserisci i luoghi, separati da una virgola: ");
		String giorni;
		do {
		giorni = InputDati.leggiStringaNonVuota("Inserisci i giorni, separati da una virgola: ");
		} while(!containsGiorno(giorni));
		String orari;
		do {
		orari = InputDati.leggiStringaNonVuota("Inserisci gli intervalli orari, nel formato xx.yy, separati da una virgola: ");
		} while(isValidTime(orari) || (floatSeparator(orari).size()%2) != 0);
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
	
	private boolean containsGiorno(String input) {
		try {
			for(String giorno : stringSeparator(input.toUpperCase()))
				GiornoSettimana.valueOf(giorno);
			return true;
		} catch(IllegalArgumentException e) {
			System.out.println("Errore di input: giorno inesistente");
		}
		return false;
		
	}
	
	private boolean isValidTime(String input) {
		for(Float orario : floatSeparator(input)) {
			if(orario.floatValue()<0.0f || orario.floatValue()>timeUpperLimit || (orario.floatValue() % 1)>minutesUpperLimit) {
				System.out.println("Errore di input: Orario digitato invalido");
				return true;
			}
		}
		return false;
	}
}
