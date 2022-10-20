package it.unibs.ids.elaborato;

import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		
		UserController c = new UserController(UserRegistryReader.getReadUserReg());
		CategoryController d = new CategoryController();
		AppointmentController e = new AppointmentController();
		d.setCategorie(CategoryReader.readCategories());
		d.articoli = new ArrayList<Articolo>(ArticoloReader.readArticoli(d, c));
		e.setAppointments(AppuntamentiReader.readAppuntamenti());
		e.setOfferteList(OfferteReader.readOfferte(d, e));
		UserView uw = new UserView(c, d, e);
		uw.viewStartupScreen();
		uw.accessMenu();
		WriteUserRegistry.write(c.getListaUtenti());
		WriteCategorie.write(d.getCategorie());
		WriteArticoli.write(d.articoli);
		WriteAppuntamenti.write(e.getAppointmentList());
		WriteOfferte.write(e.getOfferteList(), e.getOfferteUpdate());
		
	}

}
