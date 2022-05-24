package it.unibs.ids.elaborato;

public class TestCategoria {
	
	public static void main(String[] args) {
		
		
		
		Categoria libro = new Categoria("Libro", true);
		libro.aggiungiCampo(new Campo("Autore", "", true, true));
		libro.aggiungiCampo(new Campo("Titolo", "", true, false));
		libro.aggiungiCampo(new Campo("Colore copertina", "", true, false));
		
		Categoria romanzo = new Categoria("Romanzo", false);
		romanzo.aggiungiCampo(new Campo("Numero capitoli", "", true, true));
		romanzo.aggiungiCampo(new Campo("Numero pagine", "", true, false));
		
		Categoria poema = new Categoria("Poema", false);
		poema.aggiungiCampo(new Campo("Numero versi", "", true, true));
		poema.aggiungiCampo(new Campo("Numero canti", "", false, false));
		
		Categoria epica = new Categoria("Epica", false);
		poema.aggiungiSottoCategoria(epica);
		libro.aggiungiSottoCategoria(romanzo);
		libro.aggiungiSottoCategoria(poema);
		
		System.out.println("Facciamo finta che un configuratore abbia creato una categoria\n\n");
		
		StringBuilder sb = new StringBuilder();
		sb.append(libro.nomeCategoria+"\n");
		
		for(Campo c : libro.campi) {
			sb.append(c.getNome()+": ");
			sb.append(c.getDescrizione()+"\n");
		}
		
		for(Categoria sottoCategoria : libro.sottoCategorie) {
			sb.append(sottoCategoria.nomeCategoria+"\n");
			if(sottoCategoria.hasSottoCategorie()) {
				for(Categoria sottoSottoCategoria : sottoCategoria.sottoCategorie ) {
					sb.append("\t"+sottoSottoCategoria.nomeCategoria+"\n");
				}
			}
		}
			
		System.out.println(sb.toString());
		
		libro.modificaDescrizioneCampo("Stato di Conservazione", "Perfettamente conservato");
		libro.modificaDescrizioneCampo("Descrizione libera", "Bellissimo!!!");
		libro.modificaDescrizioneCampo("Autore", "Italo Calvino");
		libro.modificaDescrizioneCampo("Titolo", "Il Barone Rampante");
		
		System.out.println("\n\nFacciamo finta che un fruitore abbia compilato i campi\n\n");
		sb.delete(0, sb.length());
		sb.append(libro.nomeCategoria+"\n");
		for(Campo c : libro.campi) {
			sb.append(c.getNome()+": ");
			sb.append(c.getDescrizione()+"\n");
		}
		
		for(Categoria c : libro.sottoCategorie) {
			sb.append(c.nomeCategoria+"\n");
		}
			
		System.out.println(sb.toString());
			
	}

}
