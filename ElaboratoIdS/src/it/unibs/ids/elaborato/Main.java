package it.unibs.ids.elaborato;

public class Main {

	public static void main(String[] args) {
		
		UserController c = new UserController(UserRegistryReader.getReadUserReg());
		CategoryController d = new CategoryController();
		AppointmentController e = new AppointmentController();
		d.setCategorie(CategoryReader.readCategories());
		UserView uw = new UserView(c, d, e);
		uw.viewStartupScreen();
		uw.accessMenu();
		WriteUserRegistry.write(c.getListaUtenti());
		WriteCategorie.write(d.getCategorie());
		
	}

}
