package it.unibs.ids.elaborato;

import java.util.*;

/**
 * classe utilizzata per distinguere gli articoli
 * @author dani0
 * 
 *
 */


public class Categoria {
	
	 public static final String NOME_STATO_DI_CONSERVAZIONE = "Stato di Conservazione";
	 public static final String NOME_DESCRIZIONE_LIBERA = "Stato di Conservazione";
	 public static final String DESCRIZIONE_DEAFAULT = "da compilare";
	 public static final boolean MODIFICABILE = true;
	 public static final boolean MANDATORY = true;
	 
	
	String nomeCategoria;
	boolean root;
	Campo statoDiConservazione;
	Campo descrizioneLibera;
	TreeSet<Categoria> sottoCategorie;
	Set<Campo> campi;
	
	Categoria(String nomeCategoria, boolean root){
		
		sottoCategorie = new TreeSet<>();
		campi = new HashSet<>();
		if(root) {
			statoDiConservazione = new Campo(NOME_STATO_DI_CONSERVAZIONE, DESCRIZIONE_DEAFAULT, !MODIFICABILE, MANDATORY);
			descrizioneLibera = new Campo(NOME_DESCRIZIONE_LIBERA, DESCRIZIONE_DEAFAULT ,!MODIFICABILE, MANDATORY);
			campi.add(descrizioneLibera);
			campi.add(statoDiConservazione);
		}
		
	}
	
	public boolean isRoot() {
		return root;
	}
	
	public void aggiungiCampo(Campo campo){
		 campi.add(campo);
		}
	
	public Campo trovaCampo(Campo daTrovare) {
		for(Campo c : campi) {
			if(c.equals(daTrovare))  return daTrovare;
		}
		
		return null;
	}
	
	public void modificaCampo(Campo daModificare, String nuovoNomeCampo, boolean nuovoMandatory) {
		
		if(trovaCampo(daModificare)!=null) {
			trovaCampo(daModificare).modificaNome(nuovoNomeCampo);
			trovaCampo(daModificare).modificaMandatory(nuovoMandatory);
		}
	}
	
	public void modificaCampo(Campo daModificare, String nuovoNomeCampo) {
			
			if(trovaCampo(daModificare)!=null) {
				trovaCampo(daModificare).modificaNome(nuovoNomeCampo);
			}
		}
	
	public void modificaCampo(Campo daModificare, boolean nuovoMandatory) {
			
			if(trovaCampo(daModificare)!=null) {
				trovaCampo(daModificare).modificaMandatory(nuovoMandatory);
			}
		}
	
	public void rimuoviCampo(Campo daRimuovere) {
		if(trovaCampo(daRimuovere)!=null && daRimuovere.isModificabile()) campi.remove(daRimuovere);
	}

}
