package it.unibs.ids.elaborato;

public abstract class Utente {
	public String nome;
	public String password;
	public boolean isFirstAccess;
	public boolean authorization;
	
	public Utente(String nome, String password) {
		this.nome = nome;
		this.password = password;
	}
	
	public String userSecrets() {
		return this.nome + " " + this.password;
	}
	
	public String getName() {
		return this.nome;
	}
	
	public String getPassword() {
		return this.password;
	}

	protected abstract void cambiaCredenziali();
	protected abstract boolean isAuthorized();
	
}
