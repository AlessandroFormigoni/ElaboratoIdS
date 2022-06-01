package it.unibs.ids.elaborato;

public class TestCategoria {
	
	public static void main(String[] args) {
		
		
		
		Categoria libro = new Categoria("Libro", "Opera cartacea scritta");
		libro.aggiungiCampo(new Campo("Autore", "", true, true));
		libro.aggiungiCampo(new Campo("Titolo", "", true, false));
		libro.aggiungiCampo(new Campo("Colore copertina", "", true, false));
		
		Categoria romanzo = new Categoria("Romanzo", "Genere della narrativa scritto in prosa", libro);
		romanzo.aggiungiCampo(new Campo("Numero capitoli", "", true, true));
		romanzo.aggiungiCampo(new Campo("Numero pagine", "", true, false));
		
		Categoria poema = new Categoria("Poema", "Genere della narrativa scritto in versi", libro);
		poema.aggiungiCampo(new Campo("Numero versi", "", true, true));
		poema.aggiungiCampo(new Campo("Numero canti", "", false, false));
		
	
		
		Categoria epica = new Categoria("Epica", "Narrazione poetica di gesta eroiche", poema);
		Categoria giallo = new Categoria("Giallo", "romanzo poliziesco", romanzo);
		Categoria greca = new Categoria("Greca", " ", epica);
		
		poema.aggiungiSottoCategoria(epica);
		romanzo.aggiungiSottoCategoria(giallo);
		libro.aggiungiSottoCategoria(romanzo);
		libro.aggiungiSottoCategoria(poema);
		epica.aggiungiSottoCategoria(greca);
		
		Categoria risultato = libro.findLeaf(libro, "Greca");
		System.out.println(risultato.getNomeCategoria());
		
		/*System.out.println(CategoriaStringheFormattate.percorso(giallo));
		System.out.println();
		System.out.println(CategoriaStringheFormattate.percorso(greca));
		System.out.println();
		System.out.println(CategoriaStringheFormattate.tuttiCampi(greca));
		
		epica.aggiungiCampo(new Campo("Periodo di creazione", "", false, false));*/
		
		//pensare a rendere modifica unica per ogni articolo
		/*
		libro.modificaDescrizioneCampo("Descrizione libera", "noioso");
		System.out.println(CategoriaStringheFormattate.categoriaConDescr(libro)+"\n"+CategoriaStringheFormattate.conSottoCategorie(libro));
		System.out.println();
		System.out.println(CategoriaStringheFormattate.tuttiCampi(greca));
		System.out.println();
		System.out.println(CategoriaStringheFormattate.categoriaConDescr(libro)+"\n"+CategoriaStringheFormattate.tuttiCampi(libro));
		*/
		
	
		
		
			
	}

}
