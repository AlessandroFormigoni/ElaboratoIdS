package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.InputDati;

public class UserView {
	
	private static final String SEPARATORE = "-------------------------";
	private final static String MESSAGGIO_DI_BENVENUTO = "Benvenuti alla applicazione del barattolo v0.0.1, inserite le vostre credenziali per inizare";
	Controller controller;
	Utente currentUser;
	
	public UserView(Controller controller) {
		this.controller = controller;
	}
	
	public void login() {
		String username = "";
		boolean tryAgain = false;
		do {
			try {
				username = InputDati.leggiStringaNonVuota("Inserisci il nome: ");
				currentUser = controller.userLogin(username);
				if (!currentUser.equals(null) && currentUser.isAuthorized) tryAgain = false;
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
		confMenu();
	}
	public void confMenu() {
		System.out.println(SEPARATORE);
		System.out.println("Benvenuto configuratore " + currentUser.nome + " queste sono le tue funzionalita: ");
		System.out.println("1. Crea categoria");
		System.out.println("2. Modifica categoria");
		System.out.println("3. Elimina categoria");
		System.out.println("4. Esegui logout");
		
		int choice = InputDati.leggiIntero("Inserisci numero: ");
		switch (choice) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
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
				break;
				
			default:
				System.out.println("Comando illegale");
				break;
			
		}
	}
	
	public void logout(Utente u) {
		System.out.println("Arrivederci");
		controller.userLogout(u);
	}
	
	public void viewStartupScreen() {
		System.out.println(MESSAGGIO_DI_BENVENUTO);
		System.out.println(SEPARATORE);
	}

}
