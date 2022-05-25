package it.unibs.ids.elaborato;
import java.util.*;

public class CategoriaStringheFormattate {
	
	/*
	 * riga che rappresenta nome e descrizione di una categoria
	 * */
	public static String categoriaConDescr(Categoria daFromattare) {
		StringBuffer sb = new StringBuffer();
		sb.append(daFromattare.getNomeCategoria()+": "+daFromattare.getDescrizioneCategoria());
		return sb.toString();	
		}
	
	/*
	 * Elenca i campi di una categoria
	 * */
	public static String campiFormattati(Categoria daFormattare) {
		StringBuffer sb = new StringBuffer();
		for(Campo c: daFormattare.campi) {
			sb.append(c.getNome()+": "+c.getDescrizione()+"\n");
		}
		
		return sb.toString();
	}
	
	
	
	/*
	 * una categoria con i propri campi
	 * */
	public static String conCampi(Categoria daFormattare) {
		StringBuffer sb = new StringBuffer();
		sb.append(campiFormattati(daFormattare));
		
		return sb.toString();
		
	}
	
	/*
	 * elenca le sottocategorie di una data categoria
	 * */
	public static String conSottoCategorie(Categoria daFormattare) {
		StringBuffer sb = new StringBuffer();
		if(daFormattare.hasSottoCategorie()) {
			for(Categoria c : daFormattare.sottoCategorie) {
				sb.append("\t"+categoriaConDescr(c)+"\n");
			}
		}
		
		
		return sb.toString();
	}
	
	/*
	 * elenca il percorso delle categorie in ordine decrescente fino a quella passata come parametro
	 * */
	public static String percorso(Categoria daFormattare) {
		StringBuffer sb = new StringBuffer();
		Categoria c = daFormattare;
		ArrayList<Categoria> al = new ArrayList<>();
		al.add(c);

		while(!c.isRoot()){
			c=c.getGenitore();
			al.add(c);
			
		}
		
		
		for(int i=al.size()-1;i>=0;i--) {
			sb.append("/"+al.get(i).getNomeCategoria());
		}
		
		return sb.toString();
	}
	
	/*
	 * elenca i campi di una categoria e di tutte le categorie più grandi di essa
	 * */
	public static String tuttiCampi(Categoria daFormattare) {
		StringBuffer sb = new StringBuffer();
		Categoria c = daFormattare;
		ArrayList<Categoria> al = new ArrayList<>();
		al.add(c);
		while(!c.isRoot()) {
			
			c=c.getGenitore();
			al.add(c);
			
			}
		
		
		
		for(int i=al.size()-1; i>=0; i--) {
			sb.append(campiFormattati(al.get(i)));
		}
		return sb.toString();
	}

}
