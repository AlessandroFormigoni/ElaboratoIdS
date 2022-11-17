package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.InputDati;

public class UserController {
	
	private UserBaseController userController;
	private CategoryController categoryController;
	private AppointmentBaseController appointmentController;
	private AppointmentController appointmentView;
	private Utente currentUser;
	
	public UserController(UserBaseController controller, CategoryController categoryController, AppointmentBaseController appointmentController) {
		this.userController = controller;
		this.categoryController = categoryController;
		this.appointmentController = appointmentController;
		this.appointmentView = new AppointmentController();
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
								UserView.printInvalidChar();
								inctr = true;
							}
						} while(inctr);
					
				}
	} while(tryAgain);
		if(loginSuccessful && currentUser.isAuthorized()) confMenu();
		else if (loginSuccessful) fruitoreMenu();
		else UserView.printTermination();
	}
	
	private void confMenu() {
		UserView.viewGreeting(currentUser.nome, "configuratore");
		boolean stay = true;
		do {
			UserView.viewConfigMenu();
			int choice = InputDati.leggiIntero(UserView.INSERISCI_IL_NUMERO);
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
					UserView.printIllegalCmd();
					break;
				
			}
			UserView.printSeparatore();
		} while(stay);
	}
	
	private void fruitoreMenu() {
		UserView.viewGreeting(currentUser.nome, "fruitore");
		boolean stay = true;
		do {
			UserView.viewFruitoreMenu();
			int choice = InputDati.leggiIntero("\n"+UserView.INSERISCI_IL_NUMERO);
			switch(choice) {
				case 1:
					AppointmentView.viewAppointments(appointmentController);
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
					UserView.printIllegalCmd();
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
				UserView.printSeparatore();
				login();
			} else if (yn.toUpperCase().equals("N")) {
				UserView.printExit();
				break;
			} else {
				hasDecided = false;
				UserView.printInvalidChar();
			}
		} while(!hasDecided);
	}

	private void creaCategoriaView() {
		String nome = InputDati.leggiStringaNonVuota("Per creare una nuova categoria, inserisci un nome: ");
		String desc = InputDati.leggiStringaNonVuota("Inserisci ora una breve descrizione: ");
		categoryController.aggiungiNuovaCategoria(nome, desc);
	}
	
	private void modificaCategoriaView() {
		UserView.printSeparatore();
		boolean stay = true;
		do {
			UserView.viewModificaCategoriaMenu();
			int choice = InputDati.leggiIntero(UserView.INSERISCI_IL_NUMERO);
			switch(choice) {
				case 1:
					Categoria categoria = ViewUtility.leggiCategoria(categoryController.getCategorie(), "Inserisci nome categoria: ");
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
					Categoria radice = ViewUtility.leggiCategoria(categoryController.getCategorie(), "Inserisci nome categoria radice: ");
					Categoria cat = ViewUtility.leggiCategoria(categoryController.getCategorie(), "Inserisci nome categoria madre: ");
					if(radice!=null&&cat!=null) {
						String nomeRadice = radice.getNomeCategoria();
						String nomeCat = cat.getNomeCategoria();
						String nomeStCat = InputDati.leggiStringaNonVuota("Inserisci nome della nuova sottocategoria: ");
						String descLibStCat = InputDati.leggiStringaNonVuota("Inserisci la descrizione della nuova sottocategoria: ");
						categoryController.aggiungiSottocategoria(nomeRadice, nomeCat, nomeStCat, descLibStCat);
					}
					else UserView.printCancelOp();
					break;
				case 3:
					stay = false;
					break;
				default:
					break;
			}
			UserView.printSeparatore();
		} while(stay);
	}
	
	private void logout(Utente u) {
		UserView.printArrivederci();
		userController.userLogout(u);
	}
	
	public void creaFruitore() {
		String nomeFruitore = InputDati.leggiStringaNonVuota("Nome nuovo fruitore: ");
		String password = InputDati.leggiStringaNonVuota("Password: ");
		userController.newFruitore(nomeFruitore, password);
	}
	
	public void accessMenu() {
		boolean stay = true;
		do {
		UserView.viewAccessMenu();
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
			UserView.printExit();
			break;
			
		
		}
		}while(stay);
	}

}


