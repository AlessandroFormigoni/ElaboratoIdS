package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.InputDati;

public class UserView {
	
	private static final String SEPARATORE = "-------------------------";
	private final static String MESSAGGIO_DI_BENVENUTO = "Benvenuti alla applicazione del barattolo v0.0.2, inserite le vostre credenziali per inizare";
	private UserController userController;
	private CategoryController categoryController;
	private Utente currentUser;
	
	public UserView(UserController controller, CategoryController categoryController) {
		this.userController = controller;
		this.categoryController = categoryController;
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
			System.out.println("1. Crea categoria");
			System.out.println("2. Modifica categoria");
			System.out.println("3. Mostra categorie");
			System.out.println("4. Esegui logout");
			System.out.println("5. Esci");
			int choice = InputDati.leggiIntero("Inserisci numero: ");
			switch (choice) {
				case 1:
					creaCategoriaView();
					break;
				case 2:
					modificaCategoriaView();
					break;
				case 3:
					System.out.println("Lista delle categorie: ");
					System.out.println(BelleStringhe.incolonna("Nome", 20) + " | " + BelleStringhe.incolonna("Descrizione", 20));
					for(Categoria categoria : categoryController.getCategorie()) {
						System.out.println(CategoriaStringheFormattate.categoriaConDescr(categoria));
						System.out.println(CategoriaStringheFormattate.conCampi(categoria));
						System.out.println(CategoriaStringheFormattate.conSottoCategorie(categoria));

					}
					break;
				case 4:
					stay = false;
					logoutView();
					break;
				case 5:
					System.out.println("Uscendo...");
					stay = false;
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
			System.out.println("1. Visualizza dettagli appuntamenti");
			System.out.println("2. Esegui logout");
			System.out.println("3. Esci");
			int choice = InputDati.leggiIntero("Inserisci numero: ");
			switch(choice) {
				case 1:
					break;
				case 2:
					logoutView();
					stay = false;
					break;
				case 3:
					stay = false;
					System.out.println("Uscendo...");
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
		String statoDiConservazione = InputDati.leggiStringaNonVuota("Inserisci lo stato di conservazione: ");
		String descrizioneLibera = InputDati.leggiStringaNonVuota("Inserisci descrizione libera: ");
		categoryController.setCampiNativi(categoryController.getCategoria(nome), descrizioneLibera, statoDiConservazione);
	}
	
	private void modificaCategoriaView() {
		System.out.println(SEPARATORE);
		boolean stay = true;
		do {
			System.out.println("Come vuole modificare la categoria?");
			System.out.println("1. Aggiungi campo");
			System.out.println("2. Aggiungi sottocategoria");
			System.out.println("3. Esci");
			int choice = InputDati.leggiIntero("Inserisci il numero: ");
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
	
	private boolean leggiBool(String messaggio) {
		boolean stay = true;
		do {
			String yn = InputDati.leggiStringaNonVuota(messaggio + "[Y/n]");
			if(yn.toUpperCase().equals("Y")) {
				stay = false;
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
		int choice = InputDati.leggiIntero("Inserisci il numero: ");
		switch(choice) {
		
		case 1:
			login();
			break;
		
		case 2:
			creaFruitore();
			break;
			
		case 3:
			stay = false;
			System.out.println("Uscendo...");
			break;
			
		
		}
		}while(stay);
	}
}
