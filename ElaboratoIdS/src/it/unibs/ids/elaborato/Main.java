package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.InputDati;

public class Main {

	public static void main(String[] args) {
		
		Controller c = new Controller(UserRegistryReader.getReadUserReg());
		String nome1 = InputDati.leggiStringa("Inserisci un nome: ");
		c.userLogin(nome1);
		String nome2 = InputDati.leggiStringa("Inserisci un nome: ");
		c.userLogin(nome2);
		
	}

}
