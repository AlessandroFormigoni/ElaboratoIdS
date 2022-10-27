package it.unibs.ids.elaborato;

import java.util.List;

import it.unibs.fp.mylib.InputDati;

public class ViewUtility {
	
	static final String SEPARATORE = "-------------------------";

	
	public static Categoria leggiCategoria(CategoryController categoryController, String messaggio ) {
		boolean tryAgain;
		Categoria cat = null;
		do {
			tryAgain=false;
			cat = categoryController.getCategoria(InputDati.leggiStringaNonVuota(messaggio));
			if(categoryController.getCategorie().contains(cat)) tryAgain=false;
			else {
				System.out.println("La Categoria inserita non � valida!");
				if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
				else tryAgain=false;
			}
		}while(tryAgain);
		return cat;
	}
	
	public static Campo leggiCampo(Articolo articolo) {
		boolean tryAgain;
		Campo campo = null;
		do {
			tryAgain=false;
			campo = articolo.getCampoFromNome(InputDati.leggiStringaNonVuota("Inserire Campo: "));
			if(articolo.getCampiArticolo().contains(campo)) tryAgain = false;
			else {
				System.out.println("La Categoria inserita non � valida!");
				if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
				else tryAgain=false;
			}
		}while(tryAgain);
		return campo;
	}
	
	public static Articolo leggiArticolo(String messaggio, List<Articolo> articoli) {
		boolean tryAgain;
		do {
			tryAgain = false;
			boolean errore = false;
			String nomeArticolo = InputDati.leggiStringaNonVuota(messaggio);
			for(Articolo art : articoli) {
				if(art.getNomeArticolo().equals(nomeArticolo)) return art;
				else errore = true;
			 }
			if(errore) {
				System.out.println("L'Articolo inserito non � valido!");
				if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
				else tryAgain=false;
			}
			
		}while(tryAgain);
		return null;
	}

	public static void modificaCampi(Articolo art) {
		System.out.println("Adesso devi compilare i campi obbligatori");
		for(Campo campo : art.getCampiArticolo()) {
			if(campo.isMandatory()) {
				System.out.println(campo.getNome()+": "+campo.getDescrizione());
				campo.setDescrizione(InputDati.leggiStringaNonVuota("Modifica la descrizione del campo obbligatorio: "));
			}
		}
		while(InputDati.yesOrNo("Vuoi modificare un altro campo?")) {
			for(Campo c : art.getCampiArticolo()) {
				System.out.println(c.getNome()+": "+c.getDescrizione());
			}
			Campo daModificare = leggiCampo(art);
			if(daModificare!=null)daModificare.setDescrizione(InputDati.leggiStringaNonVuota("Inserire nuova descrizione: "));
		}
	}

	public static void stampaCategorieFoglie(CategoryController categoryController) {
		System.out.println(SEPARATORE);
		for(Categoria c : categoryController.getCategorie()) {
			if(!c.hasSottoCategorie())System.out.println(CategoriaStringheFormattate.categoriaConDescr(c));
		}
		System.out.println();
	}

	public static String leggiStringaConVerifica(String messaggio, List<String> daControllare) {
		boolean tryAgain;
		do {
			tryAgain = false;
			String input = InputDati.leggiStringaNonVuota(messaggio);
			if(daControllare.contains(input)) return input;
			else {
				System.out.println("Errore di inserimento");
				if(InputDati.yesOrNo("Vuoi riprovare?")) tryAgain=true;
				else tryAgain=false;
			}
			
		}while(tryAgain);
		return null;
	}

	public static boolean leggiBool(String messaggio) {
		do {
			String yn = InputDati.leggiStringaNonVuota(messaggio + "[Y/n]");
			if(yn.toUpperCase().equals("Y")) {
				return true;
			} else if (yn.toUpperCase().equals("N")) {
				return false;
			} else {
				System.out.println("Carattere non valido");
			}
		} while(true);
	}

	public static void printCategorie(CategoryController categoryController) {
		System.out.println("Lista delle categorie: ");
		System.out.println(UserView.SEPARATORE);
		for(Categoria categoria : categoryController.getCategorie()) {
			System.out.println(CategoriaStringheFormattate.categoriaConDescr(categoria));
			System.out.println(CategoriaStringheFormattate.tuttiCampi(categoria));
			System.out.println(CategoriaStringheFormattate.conSottoCategorie(categoria));
			System.out.println(UserView.SEPARATORE);
	
		}
	}

}
