package it.unibs.ids.elaborato;

public class UserView {
	public static final String ARRIVEDERCI = "Arrivederci";
	static final String INSERISCI_IL_NUMERO = "Inserisci il numero: ";
	static final String EXIT = "Uscendo...";
	public static final String SEPARATORE = "-------------------------";
	public static final String MESSAGGIO_DI_BENVENUTO = "Benvenuti alla applicazione del barattolo v0.0.2, inserite le vostre credenziali per inizare";
	public static final String ILLEGAL_CMD = "Comando illegale";
	public static final String CARATTERE_NON_VALIDO = "Carattere non valido";
	public static final String TERMINAZIONE = "Terminazione programma";
	public static final String OP_ANNULLATA = "Operazione annullata";
	public static final String OP_COMPLETATA = "Operazione completata con successo";
	
	public static void viewStartupScreen() {
		System.out.println(MESSAGGIO_DI_BENVENUTO);
		System.out.println(SEPARATORE);
	}
	
	public static void viewConfigMenu() {
		System.out.println();
		System.out.println("1. Crea categoria radice");
		System.out.println("2. Modifica categoria");
		System.out.println("3. Mostra categorie");
		System.out.println("4. Crea nuovo appuntamento");
		System.out.println("5. Visuallizza Offerte della Categoria Foglia specificata");
		System.out.println("6. Esegui logout");
		System.out.println();
	}
	
	public static void viewFruitoreMenu() {
		System.out.println();
		System.out.println("1. Visualizza dettagli appuntamenti");
		System.out.println("2. Visualizza categorie");
		System.out.println("3. Pubblica Articolo");
		System.out.println("4. Visuallizza le tue Offerte");
		System.out.println("5. Visuallizza Offerte della Categoria Foglia specificata");
		System.out.println("6. Ritira un'Offerta");
		System.out.println("7. Fai una proposta di scambio");
		System.out.println("8. Gestione offerte Attive");
		System.out.println("9. Visualizza ultima risposta di un Offerta in Scambio");
		System.out.println("0. Esegui logout");
	}
	
	public static void viewModificaCategoriaMenu() {
		System.out.println("Come vuole modificare la categoria?");
		System.out.println("1. Aggiungi campo");
		System.out.println("2. Aggiungi sottocategoria");
		System.out.println("3. Esci");
	}
	
	public static void viewAccessMenu() {
		System.out.println("Effettuare login o creare nuovo account fruitore");
		System.out.println("1. Login");
		System.out.println("2. Nuovo fruitore");
		System.out.println("3. Esci");
	}
	
	public static void viewGreeting(String nome, String ruolo) {
		System.out.println(SEPARATORE);
		System.out.println("Benvenuto " + ruolo +" " + nome + " queste sono le tue funzionalita: ");
	}
	
	public static void printSeparatore() {
		System.out.println(SEPARATORE);
	}
	
	public static void printIllegalCmd() {
		System.out.println(ILLEGAL_CMD);
	}
	
	public static void printExit() {
		System.out.println(EXIT);
	}
	
	public static void printInvalidChar() {
		System.out.println(CARATTERE_NON_VALIDO);
	}
	
	public static void printTermination() {
		System.out.println(TERMINAZIONE);
	}
	
	public static void printCancelOp() {
		System.out.println(OP_ANNULLATA);
	}
	
	public static void printCancelOpNewline() {
		System.out.println("\n"+UserView.OP_ANNULLATA);
	}
	
	public static void printArrivederci() {
		System.out.println(ARRIVEDERCI);
	}
	
	public static void printOpCompletedNewline() {
		System.out.println("\n"+OP_COMPLETATA);
	}
}
