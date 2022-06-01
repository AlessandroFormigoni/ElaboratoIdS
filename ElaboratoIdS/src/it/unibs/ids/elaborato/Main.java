package it.unibs.ids.elaborato;

public class Main {

	public static void main(String[] args) {
		
		UserController c = new UserController(UserRegistryReader.getReadUserReg());
		CategoryController d = new CategoryController();
		UserView uw = new UserView(c, d);
		uw.viewStartupScreen();
		uw.login();
		WriteUserRegistry.write(c.getListaUtenti());
		
	}

}
