package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.InputDati;

public class UserView {
	
	private static final String INSERISCI_IL_NUMERO = "Inserisci il numero: ";
	private static final String EXIT = "Uscendo...";
	private static final String SEPARATORE = "-------------------------";
	private final static String MESSAGGIO_DI_BENVENUTO = "Benvenuti alla applicazione del barattolo v0.0.2, inserite le vostre credenziali per inizare";
	private UserController userController;
	private CategoryController categoryController;
	private AppointmentController appointmentController;
	private AppointmentView appointmentView;
	private Utente currentUser;
	
	public UserView(UserController controller, CategoryController categoryController, AppointmentController appointmentController) {
		this.userController = controller;
		this.categoryController = categoryController;
		this.appointmentController = appointmentController;
		this.appointmentView = new AppointmentView();
	}
	
	public void login() {
		String username = "";
		boolean tryAgain = false;
		boolean loginSuccessful = false;
		do {
			try {
				username = InputDati.leggiStringaNonVuota("Inserisci il nome: ");
				currentUser = userController.userLogin(username);
				if (!currentUser.equals(null)) {
					tryAgain = false;
					loginSuccessful = true;
				}
			}
				catch (Exception e) {
						boolean inctr = false;
						do {
							String yn = InputDati.leggiStringaNonVuota("Qualcosa e andato storto. Si desidera continuare? [Y/n]");
							if(yn.toUpperCase().equals("Y")) {
								tryAgain = true;
							} else if (yn.toUpperCase().equals("N")) {
								loginSuccessful = false;
								break;
							} else {
								tryAgain = false;
								System.out.println("Carattere non valido");
								inctr = true;
							}
						} while(inctr);
					
				}
	} while(tryAgain);
		if(loginSuccessful && currentUser.isAuthorized()) confMenu();
		else if (loginSuccessful) fruitoreMenu();
		else System.out.println("Terminazione programma");
	}
	private void confMenu() {
		System.out.println(SEPARATORE);
		System.out.println("Benvenuto configuratore " + currentUser.nome + " queste sono le tue funzionalita: ");
		boolean stay = true;
		do {
			System.out.println();
			System.out.println("1. Crea categoria radice");
			System.out.println("2. Modifica categoria");
			System.out.println("3. Mostra categorie");
			System.out.println("4. Crea nuovo appuntamento");
			System.out.println("5. Visuallizza Offerte della Categoria Foglia specificata");
			System.out.println("6. Esegui logout");
			System.out.println();
			int choice = InputDati.leggiIntero("Inserisci numero: ");
			switch (choice) {
				case 1:
					creaCategoriaView();
					break;
				case 2:
					modificaCategoriaView();
					break;
				case 3:
					printCategorie();
					break;
				case 4:
					appointmentView.makeAppointment(appointmentController);
					break;
				case 5:
					visualizzaOfferteAperteFoglia();
					break;
				case 6:
					stay = false;
					logoutView();
					break;
				default:
					System.out.println("Comando illegale");
					break;
				
			}
			System.out.println(SEPARATORE);
		} while(stay);
	}
	
	private void fruitoreMenu() {
		System.out.println(SEPARATORE);
		System.out.println("Benvenuto fruitore " + currentUser.nome + " queste sono le tue funzionalita: ");
		boolean stay = true;
		do {
			System.out.println();
			System.out.println("1. Visualizza dettagli appuntamenti");
			System.out.println("2. Visualizza categorie");
			System.out.println("3. Pubblica Articolo");
			System.out.println("4. Visuallizza le tue Offerte");
			System.out.println("5. Visuallizza Offerte della Categoria Foglia specificata");
			System.out.println("6. Ritira un'Offerta");
			System.out.println("7. Fai una proposta di scambio");
			System.out.println("8. Esegui logout");
			int choice = InputDati.leggiIntero("\nInserisci numero: ");
			switch(choice) {
				case 1:
					appointmentView.viewAppointments(appointmentController);
					break;
				case 2:
					printCategorie();
					break;
				case 3: 
					pubblicaArticolo();
					break;
				case 4:
					visualizzaOfferte();
					break;
				case 5:
					visualizzaOfferteAperteFoglia();
					break;
				case 6:
					ritiraOfferta();
					break;
				case 7:
					creaOfferta();
					break;
				case 8:
					logoutView();
					stay = false;
					break;
				default:
					System.out.println("Comando illegale");
					break;
			}
		}while(stay);
		
	}

	private void logoutView() {
		logout(currentUser);
		System.out.println();
		boolean hasDecided = true;
		do {
			String yn = InputDati.leggiStringaNonVuota("Logout eseguito con successo. Vuoi effettuare un nuovo login? [Y/n]");
			if(yn.toUpperCase().equals("Y")) {
				currentUser = null;
				System.out.println(SEPARATORE);
				login();
			} else if (yn.toUpperCase().equals("N")) {
				System.out.println(EXIT);
				break;
			} else {
				hasDecided = false;
				System.out.println("Carattere non valido");
			}
		} while(!hasDecided);
	}

	private void creaCategoriaView() {
		String nome = InputDati.leggiStringaNonVuota("Per creare una nuova categoria, inserisci un nome: ");
		String desc = InputDati.leggiStringaNonVuota("Inserisci ora una breve descrizione: ");
		categoryController.aggiungiNuovaCategoria(nome, desc);
	}
	
	private void modificaCategoriaView() {
		System.out.println(SEPARATORE);
		boolean stay = true;
		do {
			System.out.println("Come vuole modificare la categoria?");
			System.out.println("1. Aggiungi campo");
			System.out.println("2. Aggiungi sottocategoria");
			System.out.println("3. Esci");
			int choice = InputDati.leggiIntero(INSERISCI_IL_NUMERO);
			switch(choice) {
				case 1:
					String nomeCategoria = InputDati.leggiStringaNonVuota("Inserisci nome categoria: ");
					String nomeCampo = InputDati.leggiStringaNonVuota("Inserisci nome campo: ");
					String descrizione = InputDati.leggiStringaNonVuota("Inserisci la descrizione: ");
					boolean modificabile = leggiBool("modificabile? ");
					boolean mandatory = leggiBool("Obbligatorio? ");
					categoryController.aggiungiCampo(nomeCategoria, nomeCampo, descrizione, modificabile, mandatory);
					break;
				case 2:
					String nomeRadice = InputDati.leggiStringaNonVuota("Inserisci nome categoria radice: ");
					String nomeCat = InputDati.leggiStringaNonVuota("Inserisci nome categoria madre: ");
					String nomeStCat = InputDati.leggiStringaNonVuota("Inserisci nome della nuova sottocategoria: ");
					String descLibStCat = InputDati.leggiStringaNonVuota("Inserisci la descrizione della nuova sottocategoria: ");
	
					categoryController.aggiungiSottocategoria(nomeRadice, nomeCat, nomeStCat, descLibStCat);
					break;
				case 3:
					stay = false;
					break;
				default:
					break;
			}
			System.out.println(SEPARATORE);
		} while(stay);
	}
	
	private void printCategorie() {
		System.out.println("Lista delle categorie: ");
		System.out.println(SEPARATORE);
		for(Categoria categoria : categoryController.getCategorie()) {
			System.out.println(CategoriaStringheFormattate.categoriaConDescr(categoria));
			System.out.println(CategoriaStringheFormattate.tuttiCampi(categoria));
			System.out.println(CategoriaStringheFormattate.conSottoCategorie(categoria));
			System.out.println(SEPARATORE);

		}
	}
	
	private boolean leggiBool(String messaggio) {
		do {
			String yn = InputDati.leggiStringaNonVuota(messaggio + "[Y/n]");
			if(yn.toUpperCase().equals("Y")) {
				return true;
			} else if (yn.toUpperCase().equals("N")) {
				return false;
			} else {
				System.out.println("Carattere non valido");
			}
		} while(true);
	}
	
	private void logout(Utente u) {
		System.out.println("Arrivederci");
		userController.userLogout(u);
	}
	
	public void viewStartupScreen() {
		System.out.println(MESSAGGIO_DI_BENVENUTO);
		System.out.println(SEPARATORE);
	}
	
	public void creaFruitore() {
		String nomeFruitore = InputDati.leggiStringaNonVuota("Nome nuovo fruitore: ");
		String password = InputDati.leggiStringaNonVuota("Password: ");
		userController.newFruitore(nomeFruitore, password);
	}
	
	public void accessMenu() {
		boolean stay = true;
		do {
		System.out.println("Effettuare login o creare nuovo account fruitore");
		System.out.println("1. Login");
		System.out.println("2. Nuovo fruitore");
		System.out.println("3. Esci");
		int choice = InputDati.leggiIntero(INSERISCI_IL_NUMERO);
		switch(choice) {
		
		case 1:
			login();
			break;
		
		case 2:
			creaFruitore();
			break;
			
		case 3:
			stay = false;
			System.out.println(EXIT);
			break;
			
		
		}
		}while(stay);
	}
	
	private void stampaCategorieFoglie() {
		System.out.println(SEPARATORE);
		for(Categoria c : categoryController.getCategorie()) {
			if(!c.hasSottoCategorie())System.out.println(CategoriaStringheFormattate.categoriaConDescr(c));
		}
		System.out.println();
	}
	
	private void pubblicaArticolo() {
		for(Categoria c  : categoryController.getCategorie()) {
			if(!c.hasSottoCategorie()) System.out.println(CategoriaStringheFormattate.percorso(c));
		}
		Categoria catSelezionata = leggiCategoria("Inserire Categoria a cui appartiene l'Articolo: ");
		if(catSelezionata!=null) {
			String nomeArticolo = InputDati.leggiStringaNonVuota("Inserisci il nome del tuo Articolo: ");
			Articolo nuovoArt = categoryController.creaArticolo(nomeArticolo, catSelezionata, currentUser);
			System.out.println(CategoriaStringheFormattate.tuttiCampi(nuovoArt.getCategoriaArticolo()));
			modificaCampi(nuovoArt);
		}
		else {
			System.out.println("Operazione annullata");
		}
	}
	
	private void visualizzaOfferte() {
		System.out.println(SEPARATORE);
		if(!categoryController.offerteAttive(currentUser).isEmpty()) {
			for(Articolo art : categoryController.offerteAttive(currentUser)) {
			System.out.println("[Nome]: "+art.getNomeArticolo()+", [stato dell'offerta]: "+art.getStatoOfferta());
			}
		}
		else System.out.println("Non hai ancora pubblicato alcuna Offerta!");
	}
	
	private void visualizzaOfferteAperteFoglia() {
		stampaCategorieFoglie();
		boolean tryAgain;
		do {
			tryAgain=false;
			Categoria foglia = leggiCategoria("Inserire Categoria di cui si vuole esplorare gli articoli: ");
			if(foglia!=null) {
				System.out.println();
				if(!foglia.hasSottoCategorie() && categoryController.categoryHasArticoli(foglia)) {
					System.out.println("Ecco tutte le Offerte della Categoria "+foglia.getNomeCategoria());
					for(Articolo art : categoryController.articoli) {
						if(art.getCategoriaArticolo().getNomeCategoria().equals(foglia.getNomeCategoria()) && art.getStatoOfferta().equals(StatiOfferta.APERTA))
							System.out.println(art.getNomeArticolo()+" pubblicato da "+art.getCreatore().getName());
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
	
	
	
	
	private void ritiraOfferta() {
		if(!categoryController.offerteAttive(currentUser).isEmpty()) {
			visualizzaOfferte();
			Articolo articolo = leggiArticolo("Inserire l'Articolo da ritirare: ");
			if(articolo!=null){
				categoryController.ritiraOfferta(articolo);
				System.out.println("Offerta ritirata con successo");
			}
			else System.out.println("Operazione annullata");
			
		}
		else System.out.println("Non hai ancora pubblicato alcuna Offerta!");
	}
	
	private void modificaCampi(Articolo art) {
		System.out.println("Adesso devi compilare i campi obbligatori");
		for(Campo campo : art.getCampiArticolo()) {
			if(campo.isMandatory()) {
				System.out.println(campo.getNome()+": "+campo.getDescrizione());
				campo.setDescrizione(InputDati.leggiStringaNonVuota("Modifica la descrizione del campo obbligatorio: "));
			}
		}
		while(InputDati.yesOrNo("Vuoi modificare un altro campo?")) {
			for(Campo c : art.getCampiArticolo()) {
				System.out.println(c.getNome()+": "+c.getDescrizione());
			}
			Campo daModificare = leggiCampo(art);
			if(daModificare!=null)daModificare.setDescrizione(InputDati.leggiStringaNonVuota("Inserire nuova descrizione: "));
		}
	}
	
	private Categoria leggiCategoria(String messaggio) {
		boolean tryAgain;
		Categoria cat = null;
		do {
			tryAgain=false;
			cat = categoryController.getCategoria(InputDati.leggiStringaNonVuota(messaggio));
			if(categoryController.getCategorie().contains(cat)) tryAgain=false;
			else {
				System.out.println("La Categoria inserita non è valida!");
				if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
				else tryAgain=false;
			}
		}while(tryAgain);
		return cat;
	}
	
	private Campo leggiCampo(Articolo articolo) {
		boolean tryAgain;
		Campo campo = null;
		do {
			tryAgain=false;
			campo = articolo.getCampoFromNome(InputDati.leggiStringaNonVuota("Inserire Campo: "));
			if(articolo.getCampiArticolo().contains(campo)) tryAgain = false;
			else {
				System.out.println("La Categoria inserita non è valida!");
				if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
				else tryAgain=false;
			}
		}while(tryAgain);
		return campo;
	}
	
	private Articolo leggiArticolo(String messaggio) {
		boolean tryAgain;
		do {
			tryAgain = false;
			boolean errore = false;
			String nomeArticolo = InputDati.leggiStringaNonVuota(messaggio);
			for(Articolo art : categoryController.articoli) {
				if(art.getNomeArticolo().equals(nomeArticolo)) return art;
				else errore = true;
			 }
			if(errore) {
				System.out.println("L'Articolo inserito non è valido!");
				if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
				else tryAgain=false;
			}
			
		}while(tryAgain);
		return null;
	}
	
	private void creaOfferta() {
		System.out.println(SEPARATORE);
		stampaCategorieFoglie();
		Categoria cat = leggiCategoria("Seleziona la categoria da cui scelgiere un articolo: ");
		for(Articolo art : categoryController.articoli) {
			if(art.getCategoriaArticolo()==cat&&art.getCreatore()!=currentUser&&art.getStatoOfferta()==StatiOfferta.APERTA) {
				System.out.println(art.getNomeArticolo());
			}
		}
		Articolo artA = leggiArticolo("Inserire un articolo che desideri: ");
		for(Articolo art : categoryController.articoli) {
			if(art.getCategoriaArticolo()==cat&&art.getCreatore()==currentUser&&art.getStatoOfferta()==StatiOfferta.APERTA) {
				System.out.println(art.getNomeArticolo());
			}
		}
		Articolo artB = leggiArticolo("Inserire l'articolo da barattare: ");
		
		long scadenza = InputDati.leggiInteroConMinimo("Inserire la scadenza dell'offerta: ", 1);
		appointmentController.creaOfferta(scadenza, artA, artB);		
	}
}
