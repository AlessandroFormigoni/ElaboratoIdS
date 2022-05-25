package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.InputDati;

public class UserView {
	
	private static final String SEPARATORE = "-------------------------";
	private final static String MESSAGGIO_DI_BENVENUTO = "Benvenuti alla applicazione del barattolo v0.0.1, inserite le vostre credenziali per inizare";
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
		do {
			try {
				username = InputDati.leggiStringaNonVuota("Inserisci il nome: ");
				currentUser = userController.userLogin(username);
				if (!currentUser.equals(null)) tryAgain = false;
			}
				catch (Exception e) {
						boolean inctr = false;
						do {
							String yn = InputDati.leggiStringaNonVuota("Qualcosa e andato storto. Si desidera continuare? [Y/n]");
							if(yn.toUpperCase().equals("Y")) {
								tryAgain = true;
							} else if (yn.toUpperCase().equals("N")) {
								break;
							} else {
								tryAgain = false;
								System.out.println("Carattere non valido");
								inctr = true;
							}
						} while(inctr);
					
				}
	} while(tryAgain);
		if(currentUser.isAuthorized) confMenu();
	}
	private void confMenu() {
		System.out.println(SEPARATORE);
		System.out.println("Benvenuto configuratore " + currentUser.nome + " queste sono le tue funzionalita: ");
		boolean stay = true;
		do {
			System.out.println("1. Crea categoria");
			System.out.println("2. Modifica categoria");
			System.out.println("3. Elimina categoria");
			System.out.println("4. Mostra categorie");
			System.out.println("5. Esegui logout");
			System.out.println("6. Esci");
			int choice = InputDati.leggiIntero("Inserisci numero: ");
			switch (choice) {
				case 1:
					creaCategoriaView();
					break;
				case 2:
					modificaCategoriaView();
					break;
				case 3:
					break;
				case 4:
					System.out.println("Lista delle categorie: ");
					System.out.println(BelleStringhe.incolonna("Nome", 20) + " | " + BelleStringhe.incolonna("Descrizione", 20));
					for(Categoria categoria : categoryController.getCategorie()) {
						System.out.println(BelleStringhe.incolonna(categoria.getNomeCategoria(), 20) + " | " + BelleStringhe.incolonna(categoria.getDescrizioneCategoria(), 20));

					}
					break;
				case 5:
					stay = logoutView(stay);
					break;
				case 6:
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

	private boolean logoutView(boolean stay) {
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
				stay = false;
				break;
			} else {
				hasDecided = false;
				System.out.println("Carattere non valido");
			}
		} while(!hasDecided);
		return stay;
	}

	private void creaCategoriaView() {
		String nome = InputDati.leggiStringaNonVuota("Per creare una nuova categoria, inserisci un nome: ");
		String desc = InputDati.leggiStringaNonVuota("Inserisci ora una breve descrizione: ");
		categoryController.aggiungiNuovaCategoria(nome, desc);
	}
	
	private void modificaCategoriaView() {
		System.out.println("Come vuole modificare la categoria?");
		System.out.println("1. Aggiungi campo");
		System.out.println("2. Rimuovi campo");
		System.out.println("3. Aggiungi sottocategoria");
		System.out.println("4. Rimuovi sottocategoria");
	}
	
	private void logout(Utente u) {
		System.out.println("Arrivederci");
		userController.userLogout(u);
	}
	
	public void viewStartupScreen() {
		System.out.println(MESSAGGIO_DI_BENVENUTO);
		System.out.println(SEPARATORE);
	}

}
