package it.unibs.ids.elaborato;

import java.util.ArrayList;
import java.util.*;

public class CategoryController {
private List<Categoria> listaCategorie;
	
	public CategoryController() {
		this.listaCategorie = new ArrayList<>();
	}
	
	public void aggiungiCategoria(Categoria categoria) {
		listaCategorie.add(categoria);
		
	}
	
	public void aggiungiNuovaCategoria(String nome, String descrizioneLibera) {
		listaCategorie.add(new Categoria(nome, descrizioneLibera));
	}
	
	public Categoria getCategoria(String categoria) {
		for (Categoria cat : listaCategorie) {
			if(cat.getNomeCategoria().equals(categoria)) return cat;
		}
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

}
