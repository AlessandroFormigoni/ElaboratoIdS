package it.unibs.ids.elaborato;

import it.unibs.fp.mylib.InputDati;

public class Configuratore extends Utente {
	
	
	public Configuratore(String nome, String password) {
		super(nome, password);
		this.isFirstAccess = true;
		this.isAuthorized = true;
	}
	
	public Categoria creaCategoria(String nomeCategoria) {
		
		return null;
	}
	
	public Categoria modificaCategoria() {
		return null;
	}
	
	public void cambiaCredenziali() {
		this.nome = InputDati.leggiStringaNonVuota("Inserisci il tuo nuovo nickname: ");
		this.password = InputDati.leggiStringaNonVuota("Inserisci la tua nuova password: ");
		this.isFirstAccess = false;
	}

}
