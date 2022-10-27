package it.unibs.ids.elaborato;

import java.util.ArrayList;


public class Main {

	public static void main(String[] args) {
		
		UserBaseController c = new UserBaseController(UserRegistryReader.getReadUserReg());
		CategoryController d = new CategoryController();
		AppointmentBaseController e = new AppointmentBaseController();
		d.setCategorie(CategoryReader.readCategories());
		d.articoli = new ArrayList<Articolo>(ArticoloReader.readArticoli(d, c));
		e.setAppointments(AppuntamentiReader.readAppuntamenti());
		e.setOfferteList(OfferteReader.readOfferte(d, e));
		UserController uw = new UserController(c, d, e);
		UserView.viewStartupScreen();
		uw.accessMenu();
		WriteUserRegistry.write(c.getListaUtenti());
		WriteCategorie.write(d.getCategorie());
		WriteArticoli.write(d.articoli);
		WriteAppuntamenti.write(e.getAppointmentList());
		WriteOfferte.write(e.getOfferteList(), e.getOfferteUpdate());
		
	}

}
