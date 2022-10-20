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
			//System.out.println("Scadenza: " + a.getScadenza());
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
	
	void stampaAppuntamento(ConfAppointment app) {
		System.out.println("[Piazza]: "+app.getPiazza());
		System.out.println("[Luogo]: "+app.getLuoghi().get(0));
		System.out.println("[Giorno]: "+app.getGiorni().get(0));
		Float[] orario = app.getIntervalliOrari().get(0);
		System.out.println("[Orario]: "+orario[0]);
		System.out.println("[Scadenza]: "+app.getScadenza());
	}

	void pubblicaArticolo(Utente currentUser, CategoryController categoryController) {
		for(Categoria c  : categoryController.getCategorie()) {
			if(!c.hasSottoCategorie()) System.out.println(CategoriaStringheFormattate.percorso(c));
		}
		Categoria catSelezionata = ViewUtility.leggiCategoria(categoryController, "Inserire Categoria a cui appartiene l'Articolo: ");
		if(catSelezionata!=null) {
			String nomeArticolo = InputDati.leggiStringaNonVuota("Inserisci il nome del tuo Articolo: ");
			Articolo nuovoArt = categoryController.creaArticolo(nomeArticolo, catSelezionata, currentUser);
			System.out.println(CategoriaStringheFormattate.tuttiCampi(nuovoArt.getCategoriaArticolo()));
			ViewUtility.modificaCampi(nuovoArt);
		}
		else {
			System.out.println("Operazione annullata");
		}
	}

	void visualizzaOfferte(Utente currentUser, CategoryController categoryController) {
		System.out.println(UserView.SEPARATORE);
		if(!categoryController.getOfferteUtente(currentUser).isEmpty()) {
			for(Articolo art : categoryController.getOfferteUtente(currentUser)) {
			System.out.println("[Nome]: "+art.getNomeArticolo()+", [Categoria]: "+art.getCategoriaArticolo().getNomeCategoria()+", [Stato dell'Offerta]: "+art.getStatoOfferta());
			}
		}
		else System.out.println("Non hai ancora pubblicato alcuna Offerta!");
	}

	void ritiraOfferta(Utente currentUser, CategoryController categoryController) {
		if(!categoryController.getOfferteUtente(currentUser).isEmpty()) {
			visualizzaOfferte(currentUser, categoryController);
			Articolo articolo = ViewUtility.leggiArticolo("Inserire l'Articolo da ritirare: ", categoryController.getOfferteUtente(currentUser));
			if(articolo!=null){
				categoryController.ritiraOfferta(articolo);
				System.out.println("Offerta ritirata con successo");
			}
			else System.out.println("Operazione annullata");
			
		}
		else System.out.println("Non hai ancora pubblicato alcuna Offerta!");
	}

	void creaOfferta(AppointmentController appointmentController, Utente currentUser, CategoryController categoryController) {
		ViewUtility.stampaCategorieFoglie(categoryController);
		Categoria cat = ViewUtility.leggiCategoria(categoryController, "Seleziona la categoria da cui scelgiere un articolo: ");
		System.out.println(UserView.SEPARATORE);
		if(cat!=null) {
			System.out.println("\nArticoli che puoi barattare ");
			for(Articolo art : categoryController.articoli) {
				if(art.getCategoriaArticolo()==cat&&art.getCreatore()==currentUser&&art.getStatoOfferta()==StatiOfferta.APERTA) {
					System.out.println(art.getNomeArticolo());
					for(Campo c : art.getCampiArticolo()) {
						System.out.println("["+c.getNome()+"]: "+c.getDescrizione());
					}
				}
			}
			Articolo daBarattare = ViewUtility.leggiArticolo("\nInserire l'articolo da barattare: ", categoryController.getOfferteUtente(currentUser).stream().filter(art->art.getCategoriaArticolo().equals(cat)).toList());
			System.out.println("\nArticoli disponibili ");
			for(Articolo art : categoryController.articoli) {
				if(art.getCategoriaArticolo()==cat&&art.getCreatore()!=currentUser&&art.getStatoOfferta()==StatiOfferta.APERTA) {
				
					System.out.println(art.getNomeArticolo());
					for(Campo c : art.getCampiArticolo()) {
						System.out.println("["+c.getNome()+"]: "+c.getDescrizione());
					}
				}
			}
			Articolo daRicevere = ViewUtility.leggiArticolo("\nInserire un articolo che desideri: ", categoryController.articoli.stream().filter(art->art.getCreatore()!=currentUser&&art.getCategoriaArticolo().equals(cat)).toList());
			
			long scadenza = InputDati.leggiInteroConMinimo("\nInserire la scadenza dell'offerta: ", 1);
			if(daBarattare!=null&&daRicevere!=null) {
				appointmentController.creaOfferta(scadenza, daBarattare, daRicevere);
				
				System.out.println("\nOperazione completata con successo");
			}
			else System.out.println("\nOperazione annullata");
			
		}
		else System.out.println("\nOperazione annullata");
				
	}

	void offerteAttive(Utente currentUser, CategoryController categoryController, AppointmentController appointmentController){
		
		if(!appointmentController.getOfferteDaNome(currentUser.nome).isEmpty()){
			for(Offerta offerta: appointmentController.getOfferteDaNome(currentUser.nome)) {
				stampaOfferta(offerta);
			}
			int selected = InputDati.leggiInteroNonNegativo("Seleziona un'Offerta inserendo l'Id: ");
			Offerta offertaSelezionata = appointmentController.getOffertaFromID(selected);
			Articolo[] coppia = offertaSelezionata.coppiaArticoli;
			
			
			if(coppia[1].getCreatore().nome.equals(currentUser.nome)&&coppia[1].getStatoOfferta()==StatiOfferta.SELEZIONATA) {
				boolean risposta = InputDati.yesOrNo("Accetti l'offerta? ");
				if(risposta){
					System.out.println("Ora devi proporre un appuntamento");
					System.out.println("Questi sono luoghi, date e orari disponibili");
					viewAppointments(appointmentController);
					String piazza = ViewUtility.leggiStringaConVerifica("Inserire una Piazza: ", appointmentController.getListaPiazze());
					if(piazza!=null) {
						ConfAppointment appointment = appointmentController.getAppointment(piazza);
						String luogo = ViewUtility.leggiStringaConVerifica("Inserire un luogo: ", appointment.getLuoghi());
						String giorno = ViewUtility.leggiStringaConVerifica("Inserire un giorno: ", appointment.getGiorni());
						Float ora;
						boolean tryAgain;
						
						do{
							ora = (float) InputDati.leggiDouble("Inserire un orario: ");
							if(appointmentController.controllaOra(ora, appointment.getIntervalliOrari())) tryAgain=false;
							else {
								ora=null;
								System.out.println("L'orario inserito non è valido!");
								if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
								else tryAgain=false;
							}
						}while(tryAgain);
						if(luogo!=null&&giorno!=null&&ora!=null) {
							Float[] fintoIntervallo = {ora, ora};
							appointmentController.accettaOfferta(selected, currentUser.getName(), appointmentController.creaUnicoAppuntamento(piazza, luogo, giorno, fintoIntervallo, appointment.getScadenza()), risposta);
						}
						else System.out.println("Operazione annullata");
					}
					else System.out.println("Operazione annullata");
				}
			}
			else if(appointmentController.checkUpadate(offertaSelezionata, currentUser.getName())&&coppia[0].getStatoOfferta()!=StatiOfferta.CHIUSA) {
				ConfAppointment app = offertaSelezionata.getAppuntamento();
				boolean ok =true;
				System.out.println("\nAppuntamento proposto");
				stampaAppuntamento(app);
				boolean risposta = InputDati.yesOrNo("Accetti l'appuntamento? ");
				if(!risposta) {
					System.out.println("Ora devi proporre un appuntamento alternativo");
					System.out.println("Questi sono luoghi, date e orari disponibili");
					viewAppointments(appointmentController);
					String piazza = ViewUtility.leggiStringaConVerifica("Inserire una Piazza: ", appointmentController.getListaPiazze());
					if(piazza!=null) {
						ConfAppointment appointment = appointmentController.getAppointment(piazza);
						String luogo = ViewUtility.leggiStringaConVerifica("Inserire un luogo: ", appointment.getLuoghi());
						String giorno = ViewUtility.leggiStringaConVerifica("Inserire un giorno: ", appointment.getGiorni());
						Float ora;
						boolean tryAgain;
						
						do{
							ora = (float) InputDati.leggiDouble("Inserire un orario: ");
							if(appointmentController.controllaOra(ora, appointment.getIntervalliOrari())) tryAgain=false;
							else {
								ora=null;
								System.out.println("L'orario inserito non è valido!");
								if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
								else tryAgain=false;
							}
						}while(tryAgain);
						if(piazza!=null&&luogo!=null&&giorno!=null&&ora!=null) {
							Float[] fintoIntervallo = {ora, ora};
							ConfAppointment alternativa = appointmentController.creaUnicoAppuntamento(piazza, luogo, giorno, fintoIntervallo, appointment.getScadenza());
							app=alternativa;
							ok=true;
						}
						else ok=false;
					}
					else ok = false;
					
				}
				if(ok)appointmentController.accettaAppuntamento(selected, currentUser.nome, risposta, app);
				else System.out.println("Operazione annullata");
			}
		}
		else {
			System.out.println("Non ci sono Offerte attive.");
		}
	}

	void visualizzaOfferteFoglia(Utente currentUser, CategoryController categoryController) {
		ViewUtility.stampaCategorieFoglie(categoryController);
		boolean tryAgain;
		do {
			tryAgain=false;
			Categoria foglia = ViewUtility.leggiCategoria(categoryController, "Inserire Categoria di cui si vuole esplorare gli articoli: ");
			if(foglia!=null) {
				System.out.println();
				if(!foglia.hasSottoCategorie() && categoryController.categoryHasArticoli(foglia)) {
					System.out.println("Ecco tutte le Offerte della Categoria "+foglia.getNomeCategoria()+"\n");
					for(Articolo art : categoryController.articoli) {
						if(art.getCategoriaArticolo().getNomeCategoria().equals(foglia.getNomeCategoria()))
							if(currentUser.isAuthorized()) {
								if(art.getStatoOfferta()==StatiOfferta.APERTA||art.getStatoOfferta()==StatiOfferta.IN_SCAMBIO||art.getStatoOfferta()==StatiOfferta.CHIUSA)
									System.out.println("[Articolo]: "+art.getNomeArticolo()+", [Autore]: "+art.getCreatore().getName()+", [Stato offerta]: "+art.getStatoOfferta());
	
							}
							else if(art.getStatoOfferta()==StatiOfferta.APERTA) System.out.println("[Articolo]: "+art.getNomeArticolo()+", [Autore]: "+art.getCreatore().getName());
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

	void visualizzaUltimaRisposta(Utente currentUser, AppointmentController appointmentController) {
		System.out.println(UserView.SEPARATORE);
		if(appointmentController.getOfferteDaNome(currentUser.getName()).stream().anyMatch(off->off.coppiaArticoli[0].getStatoOfferta()==StatiOfferta.IN_SCAMBIO)) {
			System.out.println("Questi sono i tuoi articoli attualmente in scambio:");
			for(Offerta offerta : appointmentController.getOfferteDaNome(currentUser.getName())) {
				Articolo art = offerta.coppiaArticoli[0];
				if(art.getStatoOfferta()==StatiOfferta.IN_SCAMBIO) {
					stampaOfferta(offerta);
				}
			}
			Offerta off = appointmentController.getOffertaFromID(InputDati.leggiInteroNonNegativo("Inserire l'ID dell'offerta di cui desideri visualizzare l'ultima risposta: "));
			if(appointmentController.checkUpadate(off, currentUser.getName())) System.out.println("\nQuesta è l'ultima proposta di appuntamento che hai ricevuto");
			else System.out.println("\nQuesta è l'ultima proposta di appuntamento che hai inviato");
			stampaAppuntamento(off.getAppuntamento());
		}
		else System.out.println("Non hai articoli in scambio");
	}
	
}
