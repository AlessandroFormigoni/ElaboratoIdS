package it.unibs.ids.elaborato;

import java.util.*;
import java.util.stream.Collectors;

import it.unibs.fp.mylib.InputDati;

public class AppointmentController {
	
	private static final float timeUpperLimit = 23.599999999999f;
	private static final float minutesUpperLimit = 0.599999999999f;
	
	public void makeAppointment(AppointmentBaseController ac) {
		UserView.printSeparatore();
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
			AppointmentView.stampaGiornoInesistente();
		}
		return false;
		
	}
	
	private boolean isValidTime(String input) {
		for(Float orario : floatSeparator(input)) {
			if(orario.floatValue()<0.0f || orario.floatValue()>timeUpperLimit || (orario.floatValue() % 1)>minutesUpperLimit) {
				AppointmentView.stampaOrarioDigitatoInvalido();
				return true;
			}
		}
		return false;
	}
	private boolean isValidTime(List<Float[]> input) {
		for(Float[] f : input) {
			for(int i=0;i<f.length;i++) {
				if(f[i].floatValue()<0.0f || f[i].floatValue()>timeUpperLimit || (f[i].floatValue() % 1)>minutesUpperLimit) {
					AppointmentView.stampaOrarioDigitatoInvalido();
					return true;
				}
			}
		}
		
		return false;
	}

	void pubblicaArticolo(Utente currentUser, CategoryController categoryController) {
		for(Categoria c  : categoryController.getCategorie()) {
			if(!c.hasSottoCategorie()&&!c.isRoot()) System.out.println(CategoriaStringheFormattate.percorso(c));
		}
		Categoria catSelezionata = ViewUtility.leggiCategoria(categoryController.getCategorie().stream().filter(c->!c.hasSottoCategorie()&&!c.isRoot()).toList(), "Inserire Categoria a cui appartiene l'Articolo: ");
		if(catSelezionata!=null) {
			String nomeArticolo = InputDati.leggiStringaNonVuota("Inserisci il nome del tuo Articolo: ");
			Articolo nuovoArt = categoryController.creaArticolo(nomeArticolo, catSelezionata, currentUser);
			System.out.println(CategoriaStringheFormattate.tuttiCampi(nuovoArt.getCategoriaArticolo()));
			ViewUtility.modificaCampi(nuovoArt);
		}
		else {
			UserView.printCancelOp();
		}
	}

	void visualizzaOfferte(Utente currentUser, CategoryController categoryController) {
		UserView.printSeparatore();
		if(!categoryController.getOfferteUtente(currentUser).isEmpty()) {
			for(Articolo art : categoryController.getOfferteUtente(currentUser)) {
			System.out.println("[Nome]: "+art.getNomeArticolo()+", [Categoria]: "+art.getCategoriaArticolo().getNomeCategoria()+", [Stato dell'Offerta]: "+art.getStatoOfferta());
			}
		}
		else AppointmentView.stampaOffertaNonPubblicata();
	}

	void ritiraOfferta(Utente currentUser, CategoryController categoryController) {
		if(!categoryController.getOfferteUtente(currentUser).isEmpty()) {
			visualizzaOfferte(currentUser, categoryController);
			Articolo articolo = ViewUtility.leggiArticolo("Inserire l'Articolo da ritirare: ", categoryController.getOfferteUtente(currentUser).stream().filter(art->art.getStatoOfferta().equals(StatiOfferta.APERTA)).collect(Collectors.toList()));
			if(articolo!=null){
				categoryController.ritiraOfferta(articolo);
				AppointmentView.stampaOffertaRitirata();
			}
			else UserView.printCancelOp();
			
		}
		else AppointmentView.stampaOffertaNonPubblicata();
	}

	void creaOfferta(AppointmentBaseController appointmentController, Utente currentUser, CategoryController categoryController) {
		ViewUtility.stampaCategorieFoglie(categoryController);
		Categoria cat = ViewUtility.leggiCategoria(categoryController.getCategorie().stream().filter(c->!c.hasSottoCategorie()&&!c.isRoot()).toList(), "Seleziona la categoria da cui scelgiere un articolo: ");
		UserView.printSeparatore();
		if(cat!=null) {
			AppointmentView.stampaArticoliBarattabili(currentUser, categoryController, cat);
			Articolo daBarattare = ViewUtility.leggiArticolo("\nInserire l'articolo da barattare: ", categoryController.getOfferteUtente(currentUser).stream().filter(art->art.getCategoriaArticolo().equals(cat)&&art.getStatoOfferta().equals(StatiOfferta.APERTA)).collect(Collectors.toList()));
			AppointmentView.stampaArticoliDisponibili(currentUser, categoryController, cat);
			Articolo daRicevere = ViewUtility.leggiArticolo("\nInserire un articolo che desideri: ", categoryController.getArticoli().stream().filter(art->art.getCreatore()!=currentUser&&art.getCategoriaArticolo().equals(cat)&&art.getStatoOfferta().equals(StatiOfferta.APERTA)).collect(Collectors.toList()));
			
			long scadenza = InputDati.leggiInteroConMinimo("\nInserire la scadenza dell'offerta: ", 1);
			if(daBarattare!=null&&daRicevere!=null) {
				appointmentController.creaOfferta(scadenza, daBarattare, daRicevere);
				
				UserView.printOpCompletedNewline();
			}
			else UserView.printCancelOpNewline();
			
		}
		else UserView.printCancelOpNewline();
				
	}

	void offerteAttive(Utente currentUser, CategoryController categoryController, AppointmentBaseController appointmentController){
		
		if(!appointmentController.getOfferteDaNome(currentUser.nome).isEmpty()){
			for(Offerta offerta: appointmentController.getOfferteDaNome(currentUser.nome)) {
				AppointmentView.stampaOfferta(offerta);
			}
			
			List<Offerta> offerte = appointmentController.getOfferteList().stream().filter(off->off.getCreatoreArticolo(0).equals(currentUser)||off.getCreatoreArticolo(1).equals(currentUser)).collect(Collectors.toList());
			Offerta offertaSelezionata = ViewUtility.leggiOffertaFromId("Seleziona un'Offerta inserendo l'Id: ", offerte);
			
			if(offertaSelezionata!=null) {
				Articolo[] coppia = offertaSelezionata.coppiaArticoli;
				
				if(coppia[1].getCreatore().nome.equals(currentUser.nome)&&coppia[1].getStatoOfferta()==StatiOfferta.SELEZIONATA) {
					boolean risposta = InputDati.yesOrNo("Accetti l'offerta? ");
					if(risposta){
						AppointmentView.stampaMessaggioPropAppuntamento("");
						AppointmentView.viewAppointments(appointmentController);
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
									AppointmentView.stampaOrarioNonValido();
									if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
									else tryAgain=false;
								}
							}while(tryAgain);
							if(luogo!=null&&giorno!=null&&ora!=null) {
								Float[] fintoIntervallo = {ora, ora};
								appointmentController.accettaOfferta(offertaSelezionata.getId(), currentUser.getName(), appointmentController.creaUnicoAppuntamento(piazza, luogo, giorno, fintoIntervallo, appointment.getScadenza()), risposta);
							}
							else UserView.printCancelOp();
						}
						else UserView.printCancelOp();
					}
				}
				else if(appointmentController.checkUpadate(offertaSelezionata, currentUser.getName())&&coppia[0].getStatoOfferta()!=StatiOfferta.CHIUSA) {
					ConfAppointment app = offertaSelezionata.getAppuntamento();
					boolean ok =true;
					AppointmentView.stampaAppuntamentoProposto();
					AppointmentView.stampaAppuntamento(app);
					boolean risposta = InputDati.yesOrNo("Accetti l'appuntamento? ");
					if(!risposta) {
						AppointmentView.stampaMessaggioPropAppuntamento("alternativo");
						AppointmentView.viewAppointments(appointmentController);
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
									AppointmentView.stampaOrarioNonValido();
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
					if(ok)appointmentController.accettaAppuntamento(offertaSelezionata.getId(), currentUser.nome, risposta, app);
					else UserView.printCancelOp();
				}
			}
		}
		else AppointmentView.stampaNoOfferteAttive();
	}

	void visualizzaOfferteFoglia(Utente currentUser, CategoryController categoryController) {
		ViewUtility.stampaCategorieFoglie(categoryController);
		boolean tryAgain;
		do {
			tryAgain=false;
			Categoria foglia = ViewUtility.leggiCategoria(categoryController.getCategorie().stream().filter(c->!c.hasSottoCategorie()&&!c.isRoot()).toList(), "Inserire Categoria di cui si vuole esplorare gli articoli: ");
			if(foglia!=null) {
				System.out.println();
				if(!foglia.hasSottoCategorie() && categoryController.categoryHasArticoli(foglia)) {
					AppointmentView.stampaMessaggioListaCat(foglia);
					for(Articolo art : categoryController.getArticoli()) {
						if(art.getCategoriaArticolo().getNomeCategoria().equals(foglia.getNomeCategoria()))
							if(currentUser.isAuthorized()) {
								if(art.getStatoOfferta()==StatiOfferta.APERTA||art.getStatoOfferta()==StatiOfferta.IN_SCAMBIO||art.getStatoOfferta()==StatiOfferta.CHIUSA)
									AppointmentView.stampaArticoliPubblici(art);
	
							}
							else if(art.getStatoOfferta()==StatiOfferta.APERTA)
								AppointmentView.stampaArticoliAperti(art);
						}
					}
				else {
					AppointmentView.stampaCategoriaInvalida();
					if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
					else tryAgain=false;
					}
			}
		}while(tryAgain);
	}

	void visualizzaUltimaRisposta(Utente currentUser, AppointmentBaseController appointmentController) {
		UserView.printSeparatore();
		if(appointmentController.getOfferteDaNome(currentUser.getName()).stream().anyMatch(off->off.coppiaArticoli[0].getStatoOfferta()==StatiOfferta.IN_SCAMBIO)) {
			AppointmentView.stampaMessaggioArticoliInScambio();
			for(Offerta offerta : appointmentController.getOfferteDaNome(currentUser.getName())) {
				Articolo art = offerta.coppiaArticoli[0];
				if(art.getStatoOfferta()==StatiOfferta.IN_SCAMBIO) {
					AppointmentView.stampaOfferta(offerta);
				}
			}
			List<Offerta> offerte = appointmentController.getOfferteList().stream().filter(offerta->(offerta.getCreatoreArticolo(0).equals(currentUser)||offerta.getCreatoreArticolo(1).equals(currentUser))&&offerta.coppiaArticoli[0].getStatoOfferta().equals(StatiOfferta.IN_SCAMBIO)).collect(Collectors.toList());
			Offerta off = ViewUtility.leggiOffertaFromId("Inserire l'ID dell'offerta di cui desideri visualizzare l'ultima risposta: ", offerte);
			if(off!=null) {
				if(appointmentController.checkUpadate(off, currentUser.getName())) AppointmentView.stampaMessaggioUltimaProposta("ricevuto");
				else AppointmentView.stampaMessaggioUltimaProposta("inviato");
				AppointmentView.stampaAppuntamento(off.getAppuntamento());
			}
			else UserView.printCancelOp();
			
		}
		else AppointmentView.stampaMessaggioNoArticoliInScambio();
	}
	
}
