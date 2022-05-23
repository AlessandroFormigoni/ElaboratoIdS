package it.unibs.ids.elaborato;

public class Main {

	public static void main(String[] args) {
		
		Controller c = new Controller(UserRegistryReader.getReadUserReg());
		UserView uw = new UserView(c);
		uw.viewStartupScreen();
		uw.login();
		
	}

}
