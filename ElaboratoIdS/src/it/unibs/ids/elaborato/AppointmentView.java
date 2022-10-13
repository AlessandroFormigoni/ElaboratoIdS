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
		orari = InputDati.leggiStringaNonVuota("Inserisci gli intervalli orari, nel formato xx.yy-xx.yy, separati da una virgola: ");
		} while(isValidTime(creaOrari(orari)));
		ac.creaAppuntamento(piazza, stringSeparator(luoghi), stringSeparator(giorni), creaOrari(orari));
	}
	
	public void viewAppointments(AppointmentController ac) {
		ac.getAppuntamenti().stream().forEach(a -> {
			System.out.println(SEPARATORE);
			System.out.println("Piazza: " + a.getPiazza());
			System.out.println("Luoghi: " + a.getLuoghi());
			System.out.println("Giorni: " + a.getGiorni());
			System.out.println("Orari: " + stampaOrari(a));
			System.out.println("Scadenza: " + a.getScadenza());
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
	
	public List<Float[]> creaOrari(String input){
		List<Float[]> orari = new ArrayList<>();
		List<String> list = new ArrayList<>();
		
		for(String s : stringSeparator(input)) {
			list = Arrays.asList(s.split("-"));
				float inizio = Float.valueOf(list.get(0));
				float fine = Float.valueOf(list.get(1));
				Float[] intervallo = {inizio, fine};
				orari.add(intervallo);
		}
		
		
		return orari;
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
	private boolean isValidTime(List<Float[]> input) {
		for(Float[] f : input) {
			for(int i=0;i<f.length;i++) {
				if(f[i].floatValue()<0.0f || f[i].floatValue()>timeUpperLimit || (f[i].floatValue() % 1)>minutesUpperLimit) {
					System.out.println("Errore di input: Orario digitato invalido");
					return true;
				}
			}
		}
		
		return false;
	}
	
	private String stampaOrari(ConfAppointment ca) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Float[] f : ca.getIntervalliOrari()) {
			sb.append(f[0]+"-"+f[1]);
			if(ca.getIntervalliOrari().indexOf(f)!=ca.getIntervalliOrari().size()-1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}

	void stampaOfferta(Offerta offerta) {
		Articolo[] coppia = offerta.coppiaArticoli;
		System.out.println("[Id Offerta]: "+offerta.getId()+")");
		System.out.println("[Articolo A]: "+coppia[0].getNomeArticolo()+" [Stato Offerta]: "+coppia[0].getStatoOfferta());
		System.out.println("[Articolo B]: "+coppia[1].getNomeArticolo()+" [Stato Offerta]: "+coppia[1].getStatoOfferta());
	}

	void visualizzaOfferteAperteFoglia(UserView userView) {
		userView.stampaCategorieFoglie();
		boolean tryAgain;
		do {
			tryAgain=false;
			Categoria foglia = userView.leggiCategoria("Inserire Categoria di cui si vuole esplorare gli articoli: ");
			if(foglia!=null) {
				System.out.println();
				if(!foglia.hasSottoCategorie() && userView.categoryController.categoryHasArticoli(foglia)) {
					System.out.println("Ecco tutte le Offerte della Categoria "+foglia.getNomeCategoria()+"\n");
					for(Articolo art : userView.categoryController.articoli) {
						if(art.getCategoriaArticolo().getNomeCategoria().equals(foglia.getNomeCategoria()) && art.getStatoOfferta().equals(StatiOfferta.APERTA))
							System.out.println("[Ariticolo]: "+art.getNomeArticolo()+" [Autore]: "+art.getCreatore().getName());
						}
					}
				else {
					System.out.println("La Categoria inserita non è valida!");
					if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
					else tryAgain=false;
					}
			}
		}while(tryAgain);
	}
	
}
