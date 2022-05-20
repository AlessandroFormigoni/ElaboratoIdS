package it.unibs.ids.elaborato;

public abstract class Utente {
	public String nome;
	public String password;
	
	public Utente(String nome, String password) {
		this.nome = nome;
		this.password = password;
	}
	
	public String userSecrets() {
		return this.nome + " " + this.password;
	}

}
