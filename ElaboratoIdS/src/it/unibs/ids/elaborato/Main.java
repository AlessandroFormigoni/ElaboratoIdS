package it.unibs.ids.elaborato;

import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		
		CategoryReader.readCategories();
		UserController c = new UserController(UserRegistryReader.getReadUserReg());
		CategoryController d = new CategoryController();
		d.setCategorie(new ArrayList<>(CategoryReader.reconstructedTree()));
		UserView uw = new UserView(c, d);
		uw.viewStartupScreen();
		uw.login();
		WriteUserRegistry.write(c.getListaUtenti());
		WriteCategorie.write(d.getCategorie());
		
	}

}
