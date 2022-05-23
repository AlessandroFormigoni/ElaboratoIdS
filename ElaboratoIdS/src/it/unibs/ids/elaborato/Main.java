package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.InputDati;

public class Main {

	public static void main(String[] args) {
		
		Controller c = new Controller(UserRegistryReader.getReadUserReg());
		UserView uw = new UserView(c);
		uw.login();
		
	}

}
