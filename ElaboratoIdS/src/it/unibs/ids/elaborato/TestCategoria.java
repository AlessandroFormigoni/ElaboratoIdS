package it.unibs.ids.elaborato;

import java.util.*;

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
		Categoria fantascienza = new Categoria("Fantascienza", "romanzo scifi", romanzo);
		Categoria noir = new Categoria("Noir", "mistero", giallo);
		Categoria romana = new Categoria("Romana", "", epica);
		
		poema.aggiungiSottoCategoria(epica);
		romanzo.aggiungiSottoCategoria(giallo);
		libro.aggiungiSottoCategoria(romanzo);
		libro.aggiungiSottoCategoria(poema);
		epica.aggiungiSottoCategoria(greca);
		epica.aggiungiSottoCategoria(romana);
		romanzo.aggiungiSottoCategoria(fantascienza);
		giallo.aggiungiSottoCategoria(noir);
		
		//Categoria risultato = libro.findLeaf(libro, "Greca");
		//System.out.println(risultato.getNomeCategoria());
		
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
		
		giallo.creaMappa();
		/*for(Categoria c : libro.getMappa().keySet()) {
			System.out.println(c.getNomeCategoria());
		}
		System.out.println("\n\n\n");*/
		greca.creaMappa();
		Map<Categoria, Set<Categoria>> test = new TreeMap<>();
		//List<Categoria> list = new ArrayList<>();
		Categoria cat1 = new Categoria("cat1", "a");
		Categoria cat2 = new Categoria("cat2", "a", cat1);
		Categoria cat3 = new Categoria("cat3", "a", cat1);
		Categoria cat4 = new Categoria("cat4", "a", cat2);
		Categoria cat5 = new Categoria("cat5", "a", cat3);
		Categoria cat6 = new Categoria("cat6", "a");
		test.put(cat1, cat1.sottoCategorie);
		test.put(cat2, cat2.sottoCategorie);
		test.put(cat3, cat3.sottoCategorie);
		test.put(cat4, cat4.sottoCategorie);
		test.put(cat5, cat5.sottoCategorie);
		test.put(cat6, cat6.sottoCategorie);
		List<Categoria> list = new ArrayList<>();
		list.addAll(test.keySet());
		WriteCategorie.write(list);
		for(Categoria c : libro.getMappa().keySet()) {
			System.out.println(CategoriaStringheFormattate.categoriaConDescr(c));
		}
		//libro.treeReconstructor(libro.getMappa());
		//cat1.treeReconstructor(test);
		cat1.treeReconstructor(test);
		CategoryReader.readCategories();
		//Set<Categoria> c = CategoryReader.reconstructedTree();
		
		
			
	}

}
