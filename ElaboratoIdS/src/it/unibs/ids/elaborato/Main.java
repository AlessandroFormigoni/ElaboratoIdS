package it.unibs.ids.elaborato;

import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		
		UserBaseController userBaseController = new UserBaseController(new ArrayList<>());
		CategoryController categoryController = new CategoryController();
		AppointmentBaseController appointmentBaseController = new AppointmentBaseController();
		UserController userController = new UserController(userBaseController, categoryController, appointmentBaseController);
		readData(userBaseController, categoryController, appointmentBaseController);
		UserView.viewStartupScreen();
		userController.accessMenu();
		writeData(userBaseController, categoryController, appointmentBaseController);
		XMLFileImporter.deleteFiles();
	}

	private static void readData(UserBaseController userBaseController, CategoryController categoryController,
			AppointmentBaseController appointmentBaseController) {
		XMLFileImporter.importFiles(categoryController, userBaseController, appointmentBaseController, appointmentBaseController.getOfferteUpdate());
		userBaseController.setListaUtenti(UserRegistryReader.getReadUserReg());
		categoryController.setCategorie(CategoryReader.readCategories());
		categoryController.setArticoli(new ArrayList<Articolo>(ArticoloReader.readArticoli(categoryController, userBaseController)));
		appointmentBaseController.setAppointments(AppuntamentiReader.readAppuntamenti());
		appointmentBaseController.setOfferteList(OfferteReader.readOfferte(categoryController, appointmentBaseController));
	}

	private static void writeData(UserBaseController userBaseController, CategoryController categoryController, AppointmentBaseController appointmentBaseController) {
		WriteUserRegistry.write(userBaseController.getListaUtenti());
		WriteCategorie.write(categoryController.getCategorie());
		WriteArticoli.write(categoryController.getArticoli());
		WriteAppuntamenti.write(appointmentBaseController.getAppointmentList());
		WriteOfferte.write(appointmentBaseController.getOfferteList(), appointmentBaseController.getOfferteUpdate());
	}

}
