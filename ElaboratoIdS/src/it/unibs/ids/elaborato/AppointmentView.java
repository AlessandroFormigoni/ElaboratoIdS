package it.unibs.ids.elaborato;

public class AppointmentView {
	private static final String GIORNO_INESISTENTE = "Errore di input: giorno inesistente";
	public static final String ORARIO_DIGITATO_INVALIDO = "Errore di input: Orario digitato invalido";
	public static final String APPUNTAMENTO_PROPOSTO = "\nAppuntamento proposto";
	public static final String ORARIO_NON_VALIDO = "L'orario inserito non � valido!";
	public static final String NO_OFFERTA_PUBBLICATA = "Non hai ancora pubblicato alcuna Offerta!";
	public static final String OFFERTA_RITIRATA = "Offerta ritirata con successo";
	private static final String NO_OFFERTE_ATTIVE = "Non ci sono Offerte attive.";
	public static final String CATEGORIA_INVALIDA = "La Categoria inserita non � valida!";
	
	public static void viewAppointments(AppointmentBaseController ac) {
		ac.getAppuntamenti().stream().forEach(a -> {
			UserView.printSeparatore();
			System.out.println("Piazza: " + a.getPiazza());
			System.out.println("Luoghi: " + a.getLuoghi());
			System.out.println("Giorni: " + a.getGiorni());
			System.out.println("Orari: " + stampaOrari(a));
		});
		UserView.printSeparatore();
	}
	
	private static String stampaOrari(ConfAppointment ca) {
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		for(Float[] f : ca.getIntervalliOrari()) {
			sb.append(f[0]+"-"+f[1]);
			if(ca.getIntervalliOrari().indexOf(f)!=ca.getIntervalliOrari().size()-1) sb.append(", ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	public static void stampaOfferta(Offerta offerta) {
		Articolo[] coppia = offerta.coppiaArticoli;
		System.out.println("[Id Offerta]: "+offerta.getId()+")");
		System.out.println("[Articolo A]: "+coppia[0].getNomeArticolo()+" [Stato Offerta]: "+coppia[0].getStatoOfferta());
		System.out.println("[Articolo B]: "+coppia[1].getNomeArticolo()+" [Stato Offerta]: "+coppia[1].getStatoOfferta());
	}
	
	public static void stampaAppuntamento(ConfAppointment app) {
		System.out.println("[Piazza]: "+app.getPiazza());
		System.out.println("[Luogo]: "+app.getLuoghi().get(0));
		System.out.println("[Giorno]: "+app.getGiorni().get(0));
		Float[] orario = app.getIntervalliOrari().get(0);
		System.out.println("[Orario]: "+orario[0]);
		System.out.println("[Scadenza]: "+app.getScadenza());
	}
	
	public static void stampaMessaggioPropAppuntamento(String alternativo) {
		System.out.println("Ora devi proporre un appuntamento " + alternativo);
		System.out.println("Questi sono luoghi, date e orari disponibili");
	}
	public static void stampaGiornoInesistente() {
		System.out.println(GIORNO_INESISTENTE);
	}
	
	public static void stampaOrarioDigitatoInvalido() {
		System.out.println(ORARIO_DIGITATO_INVALIDO);
	}
	
	public static void stampaAppuntamentoProposto() {
		System.out.println(APPUNTAMENTO_PROPOSTO);
	}
	
	public static void stampaOrarioNonValido() {
		System.out.println(ORARIO_NON_VALIDO);
	}
	
	public static void stampaOffertaNonPubblicata() {
		System.out.println(NO_OFFERTA_PUBBLICATA);
	}
	
	public static void stampaOffertaRitirata() {
		System.out.println(OFFERTA_RITIRATA);
	}
	
	public static void stampaNoOfferteAttive() {
		System.out.println(NO_OFFERTE_ATTIVE);
	}
	
	public static void stampaCategoriaInvalida() {
		System.out.println(CATEGORIA_INVALIDA);
	}
	
	public static void stampaArticoliAperti(Articolo art) {
		System.out.println("[Articolo]: "+art.getNomeArticolo()+", [Autore]: "+art.getCreatore().getName());
	}

	public static void stampaArticoliPubblici(Articolo art) {
		System.out.println("[Articolo]: "+art.getNomeArticolo()+", [Autore]: "+art.getCreatore().getName()+", [Stato offerta]: "+art.getStatoOfferta());
	}

	public static void stampaMessaggioListaCat(Categoria foglia) {
		System.out.println("Ecco tutte le Offerte della Categoria "+foglia.getNomeCategoria()+"\n");
	}
	
	public static void stampaMessaggioArticoliInScambio() {
		System.out.println("Questi sono i tuoi articoli attualmente in scambio:");
	}
	
	public static void stampaMessaggioUltimaProposta(String completamento) {
		System.out.println("\nQuesta � l'ultima proposta di appuntamento che hai " + completamento);
	}
	
	public static void stampaMessaggioNoArticoliInScambio() {
		System.out.println("Non hai articoli in scambio");
	}
	
	public static void stampaArticoliDisponibili(Utente currentUser, CategoryController categoryController, Categoria cat) {
		System.out.println("\nArticoli disponibili ");
		for(Articolo art : categoryController.getArticoli()) {
			if(art.getCategoriaArticolo()==cat&&art.getCreatore()!=currentUser&&art.getStatoOfferta()==StatiOfferta.APERTA) {
			
				System.out.println(art.getNomeArticolo());
				for(Campo c : art.getCampiArticolo()) {
					System.out.println("["+c.getNome()+"]: "+c.getDescrizione());
				}
			}
		}
	}

	public static void stampaArticoliBarattabili(Utente currentUser, CategoryController categoryController, Categoria cat) {
		System.out.println("\nArticoli che puoi barattare ");
		for(Articolo art : categoryController.getArticoli()) {
			if(art.getCategoriaArticolo()==cat&&art.getCreatore()==currentUser&&art.getStatoOfferta()==StatiOfferta.APERTA) {
				System.out.println(art.getNomeArticolo());
				for(Campo c : art.getCampiArticolo()) {
					System.out.println("["+c.getNome()+"]: "+c.getDescrizione());
				}
			}
		}
	}
}
