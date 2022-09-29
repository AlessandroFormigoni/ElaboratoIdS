package it.unibs.ids.elaborato;

import java.util.ArrayList;
import java.util.*;

public class CategoryController {
private List<Categoria> listaCategorie;
public ArrayList<Articolo> articoli = new ArrayList<>();
	
	public CategoryController() {
		this.listaCategorie = new ArrayList<>();
	}
	
	public void aggiungiCategoria(Categoria categoria) {
		listaCategorie.add(categoria);
	}
	
	public void aggiungiCampo(String categoria, String nomeCampo, String descrizione, boolean modificabile, boolean mandatory) {
		try {
		getCategoria(categoria).aggiungiCampo(new Campo(nomeCampo, descrizione, modificabile, mandatory));
		} catch(Exception e) {
			System.out.println("Elemento non trovato");
		}
	}
	
	public void rimuoviCampo(String categoria, String nomeCampo) {
		try {
		getCategoria(categoria).rimuoviCampo(getCategoria(categoria).trovaCampoPerNome(nomeCampo));
		} catch(Exception e) {
			System.out.println("Elemento non trovato");
		}
	}
	
	public void aggiungiSottocategoria(String radice, String categoria, String nome, String descrizioneLibera) {
		try {
			Categoria key = getCategoria(categoria);
			final Categoria subCat = new Categoria(nome, descrizioneLibera, key);
			
			if(key.isRoot()) {
				getCategoria(key).aggiungiSottoCategoria(subCat);
			} else {
				getCategoria(radice).findLeaf(key.trovaRoot(), categoria).aggiungiSottoCategoria(subCat);
			}
			
			listaCategorie.add(subCat);
			
		} catch(Exception e ) {
			System.out.println("Elemento non trovato");
		}
	}
	
	public void aggiungiNuovaCategoria(String nome, String descrizioneLibera) {
		aggiungiCategoria(new Categoria(nome, descrizioneLibera));
	}
	
	public void setCampiNativi(Categoria categoria, String descrizioneLibera, String statoDiConservazione) {
		getCategoria(categoria).modificaDescrizioneCampo(Categoria.NOME_STATO_DI_CONSERVAZIONE, statoDiConservazione);
		getCategoria(categoria).modificaDescrizioneCampo(Categoria.NOME_DESCRIZIONE_LIBERA, descrizioneLibera);
	}
	
	public Categoria getCategoria(String categoria) {
		for (Categoria cat : listaCategorie) {
			if(cat.getNomeCategoria().equals(categoria)) return cat;
		}
		return null;
	}
	
	public Categoria getCategoria(Categoria categoria) {
		if(listaCategorie.contains(categoria))
				return this.listaCategorie.get(this.listaCategorie.indexOf(categoria));
		return null;
	}
	
	public List<Categoria> getRootCategories() {
		List<Categoria> rootCat = new ArrayList<>();
		for (Categoria categoria : listaCategorie) {
			if(categoria.isRoot()) rootCat.add(categoria);
		}
		return rootCat;
	}
	
	public List<Categoria> getCategorie() {
		return this.listaCategorie;
	}
	
	public void setCategorie(List<Categoria> listaCategorie) {
		this.listaCategorie = listaCategorie;
	}
	
	public Articolo creaArticolo(String nome, String foglia, Utente utente) {
		try {
			Categoria catFoglia = getCategoria(foglia);
		Articolo art = new Articolo(nome, catFoglia, StatiOfferta.APERTA, utente);
		articoli.add(art);
		if(!getCategoria(foglia).hasSottoCategorie()) return art;
		else return null;
		} catch (Exception e) {
			e.printStackTrace();
		} return null;
	}
	
	public Articolo getArticolo(String nome, Utente creatore) {
		for(Articolo art : articoli) {
			if(art.getNomeArticolo().equals(nome) && art.getCreatore().getName().equals(creatore.getName())) return art;
		}
		return null;
	}
	
	public void ritiraOfferta(String daRitirare, Utente creatore){
		if(getArticolo(daRitirare, creatore).getStatoOfferta()!=StatiOfferta.RITIRATA) getArticolo(daRitirare, creatore).ritiraOfferta();
	}
	
	public List<Articolo> offerteAttive(Utente creatore){
		List<Articolo> offerte = new ArrayList<>();
		for(Articolo art : articoli) {
			if(art.getCreatore().equals(creatore)) offerte.add(art);
		}
		return offerte;
	}

}
