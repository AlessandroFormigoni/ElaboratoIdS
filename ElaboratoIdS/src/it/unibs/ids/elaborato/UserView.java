package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.BelleStringhe;
import it.unibs.fp.mylib.InputDati;

public class UserView {
	
	Controller controller;
	Utente currentUser;
	
	public UserView(Controller controller) {
		this.controller = controller;
	}
	
	public void login() {
		String username;
		do {
		username = InputDati.leggiStringaNonVuota("Inserisci il nome: ");
		currentUser = controller.userLogin(username);
		} while(!controller.hasUser(username));
		if (currentUser.isAuthorized) {
			confMenu();
		}
	}
	
	public void confMenu() {
		System.out.println("-------------------------");
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
						System.out.println("Arrivederci");
						currentUser = null;
						login();
					} else if (yn.toUpperCase().equals("N")) {
						break;
					} else {
						hasDecided = false;
						System.out.println("Carattere non valido");
					}
				} while(!hasDecided);
			default:
				System.out.println("Comando illegale");
				break;
			
		}
	}
	
	public void logout(Utente u) {
		controller.userLogout(u);
	}

}
