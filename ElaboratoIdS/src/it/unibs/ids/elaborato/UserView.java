package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.InputDati;

public class UserView {
	
	static final String INSERISCI_IL_NUMERO = "Inserisci il numero: ";
	static final String EXIT = "Uscendo...";
	public static final String SEPARATORE = "-------------------------";
	private final static String MESSAGGIO_DI_BENVENUTO = "Benvenuti alla applicazione del barattolo v0.0.2, inserite le vostre credenziali per inizare";
	private UserController userController;
	CategoryController categoryController;
	AppointmentController appointmentController;
	AppointmentView appointmentView;
	Utente currentUser;
	
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
					ViewUtility.printCategorie(categoryController);
					break;
				case 4:
					appointmentView.makeAppointment(appointmentController);
					break;
				case 5:
					appointmentView.visualizzaOfferteFoglia(this.currentUser, categoryController);
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
			System.out.println("8. Gestione offerte Attive");
			System.out.println("9. Visualizza ultima risposta di un Offerta in Scambio");
			System.out.println("0. Esegui logout");
			int choice = InputDati.leggiIntero("\nInserisci numero: ");
			switch(choice) {
				case 1:
					appointmentView.viewAppointments(appointmentController);
					break;
				case 2:
					ViewUtility.printCategorie(categoryController);
					break;
				case 3: 
					appointmentView.pubblicaArticolo(this.currentUser, categoryController);
					break;
				case 4:
					appointmentView.visualizzaOfferte(this.currentUser, categoryController);
					break;
				case 5:
					appointmentView.visualizzaOfferteFoglia(this.currentUser, categoryController);
					break;
				case 6:
					appointmentView.ritiraOfferta(this.currentUser, categoryController);
					break;
				case 7:
					appointmentView.creaOfferta(this.appointmentController, this.currentUser, categoryController);
					break;
				case 8:
					appointmentView.offerteAttive(this.currentUser, categoryController, this.appointmentController);
					break;
				case 9:
					appointmentView.visualizzaUltimaRisposta(this.currentUser, this.appointmentController);
					break;
				case 0:
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
					Categoria categoria = ViewUtility.leggiCategoria(categoryController, "Inserisci nome categoria: ");
					if(categoria!=null) {
						String nomeCategoria = categoria.getNomeCategoria();
						String nomeCampo = InputDati.leggiStringaNonVuota("Inserisci nome campo: ");
						String descrizione = InputDati.leggiStringaNonVuota("Inserisci la descrizione: ");
						boolean modificabile = ViewUtility.leggiBool("modificabile? ");
						boolean mandatory = ViewUtility.leggiBool("Obbligatorio? ");
						categoryController.aggiungiCampo(nomeCategoria, nomeCampo, descrizione, modificabile, mandatory);
					}
					else System.out.println("Opearazione annullata");
					
					break;
				case 2:
					Categoria radice = ViewUtility.leggiCategoria(categoryController, "Inserisci nome categoria radice: ");
					Categoria cat = ViewUtility.leggiCategoria(categoryController, "Inserisci nome categoria madre: ");
					if(radice!=null&&cat!=null) {
						String nomeRadice = radice.getNomeCategoria();
						String nomeCat = cat.getNomeCategoria();
						String nomeStCat = InputDati.leggiStringaNonVuota("Inserisci nome della nuova sottocategoria: ");
						String descLibStCat = InputDati.leggiStringaNonVuota("Inserisci la descrizione della nuova sottocategoria: ");
						categoryController.aggiungiSottocategoria(nomeRadice, nomeCat, nomeStCat, descLibStCat);
					}
					else System.out.println("Operazione annullata");
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
		int choice = InputDati.leggiIntero(UserView.INSERISCI_IL_NUMERO);
		switch(choice) {
		
		case 1:
			login();
			break;
		
		case 2:
			creaFruitore();
			break;
			
		case 3:
			stay = false;
			System.out.println(UserView.EXIT);
			break;
			
		
		}
		}while(stay);
	}

}


