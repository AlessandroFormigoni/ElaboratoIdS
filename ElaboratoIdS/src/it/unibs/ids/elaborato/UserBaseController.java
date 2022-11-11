package it.unibs.ids.elaborato;

import java.util.*;

import it.unibs.fp.mylib.InputDati;

public class UserBaseController {
	
	private static final int PASSWD_TRIAL_NUM = 3;
	private static final String GENERIC_PASSWORD_ERROR = "Inserimento password fallito";
	private static final String INSERT_PASSWORD = "Inserisci password: ";
	private static final String GENERIC_LOGIN_ERROR = "Errore di login";
	private static final String LOGIN_SUCCESS = "Login avvenuto con successo";
	private List<Utente> listaUtenti;
	private List<Utente> loggedUsers;
	
	public UserBaseController(List<Utente> listaUtenti) {
		this.listaUtenti = listaUtenti;
		this.loggedUsers = new ArrayList<>();
	}
	
	public Utente userLogin(String nome) {
		try {
		Utente user = getUser(nome);
		if(!user.equals(null) && !loggedUsers.contains(user) && loginAuthentication(user)) {
			System.out.println(LOGIN_SUCCESS);
			loggedUsers.add(user);
			if(user.isFirstAccess && user.isAuthorized()) {
				firstConfigLogin((Configuratore) user);
			}
			return user;
		}
		} catch(Exception e) {
			System.out.println(GENERIC_LOGIN_ERROR);
		}
		return null;
	}
	
	public void newFruitore(String nome, String password) {
		if(getUser(nome)!=null) {
			System.out.println("Il nome utente e' gia in uso");
		} else {
		Fruitore fruitore = new Fruitore(nome, password);
		fruitore.isFirstAccess = false;
		this.listaUtenti.add(fruitore);
		}
	}
	
	private void firstConfigLogin(Configuratore conf) {
		conf.cambiaCredenziali();
	}
	
	private boolean loginAuthentication(Utente requestingUser) {
		boolean isAuthenticated = false;
		
		for(int chances = PASSWD_TRIAL_NUM; chances>0 && isAuthenticated==false; chances--)
				isAuthenticated = checkPassword(requestingUser, InputDati.leggiStringa(INSERT_PASSWORD));
		
		if(isAuthenticated==false) System.out.println(GENERIC_PASSWORD_ERROR);
		
		return isAuthenticated;
	}
	
	public void userLogout(Utente utente) {
		loggedUsers.remove(utente);
	}
	
	protected Utente getUser(String username) {
		for(Utente u : listaUtenti) {
			if (u.getName().equals(username)) return u;
		}
		return null;
	}
	
	private boolean checkPassword(Utente utente, String password) {
		return utente.getPassword().equals(password);
	}
	
	public void importUsers(List<Utente> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}
	
	public boolean hasUser(String nome) {
		return listaUtenti.contains(getUser(nome));
	}
	
	public List<Utente> getListaUtenti() {
		return this.listaUtenti;
	}

	public void setListaUtenti(List<Utente> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}

}
