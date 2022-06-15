package it.unibs.ids.elaborato;

public class Fruitore extends Utente {

	public Fruitore(String nome, String password) {
		super(nome, password);
		this.isFirstAccess = true;
		this.authorization = false;
	}
	
	protected void cambiaCredenziali() {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean isAuthorized() {
		return this.authorization;
	}

}
