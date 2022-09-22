package it.unibs.ids.elaborato;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		
		List<Articolo> a;
		UserController c = new UserController(UserRegistryReader.getReadUserReg());
		CategoryController d = new CategoryController();
		AppointmentController e = new AppointmentController();
		d.setCategorie(CategoryReader.readCategories());
		a = ArticoloReader.readArticoli(d, c);
		UserView uw = new UserView(c, d, e);
		uw.viewStartupScreen();
		uw.accessMenu();
		WriteUserRegistry.write(c.getListaUtenti());
		WriteCategorie.write(d.getCategorie());
		WriteArticoli.write(a);
		
	}

}
