package it.unibs.ids.elaborato;

import java.util.*;

import it.unibs.fp.mylib.InputDati;

public class Controller {
	
	private static final String GENERIC_PASSWORD_ERROR = "Inserimento password fallito";
	private static final String INSERT_PASSWORD = "Inserisci password: ";
	private static final String GENERIC_LOGIN_ERROR = "Errore di login";
	private static final String LOGIN_SUCCESS = "Login avvenuto con successo";
	private List<Utente> listaUtenti;
	private List<Utente> loggedUsers;
	
	public Controller(List<Utente> listaUtenti) {
		this.listaUtenti = listaUtenti;
		this.loggedUsers = new ArrayList<>();
	}
	
	public Utente userLogin(String nome) {
		try {
		Utente user = getUser(nome);
		if(!user.equals(null) && !loggedUsers.contains(user) && loginAuthentication(user)) {
			System.out.println(LOGIN_SUCCESS);
			loggedUsers.add(user);
			return user;
		}
		} catch(Exception e) {
			System.out.println(GENERIC_LOGIN_ERROR);
		}
		return null;
	}
	
	private boolean loginAuthentication(Utente requestingUser) {
		boolean isAuthenticated = false;
		
		for(int chances = 3; chances>0 && isAuthenticated==false; chances--)
				isAuthenticated = checkPassword(requestingUser, InputDati.leggiStringa(INSERT_PASSWORD));
		
		if(isAuthenticated==false) System.out.println(GENERIC_PASSWORD_ERROR);
		
		return isAuthenticated;
	}
	
	private void userLogout(Utente utente) {
		loggedUsers.remove(utente);
	}
	
	private Utente getUser(String username) {
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

}
