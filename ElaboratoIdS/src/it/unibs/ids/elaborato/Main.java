package it.unibs.ids.elaborato;

public class Main {

	public static void main(String[] args) {
		
		//UserRegistryReader.chooseFilename();
		try {
		for (Utente u : UserRegistryReader.getReadUserReg()) {
			System.out.println(u.userSecrets());
		}
		} catch(Exception e) {
			
		}
	}

}
