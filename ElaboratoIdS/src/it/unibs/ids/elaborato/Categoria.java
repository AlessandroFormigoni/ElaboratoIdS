package it.unibs.ids.elaborato;

import java.util.*;

/**
 * classe utilizzata per distinguere gli articoli
 * @author dani0
 * 
 *
 */


public class Categoria implements Comparable<Categoria> {
	
	 public static final String NOME_STATO_DI_CONSERVAZIONE = "Stato di Conservazione";
	 public static final String NOME_DESCRIZIONE_LIBERA = "Descrizione libera";
	 public static final String DESCRIZIONE_DEAFAULT = "da compilare";
	 public static final boolean MODIFICABILE = true;
	 public static final boolean MANDATORY = true;
	 
	
	private String nomeCategoria;
	private String descrizioneCategoria;
	private boolean root;
	private Campo statoDiConservazione;
	private Campo descrizioneLibera;
	private Categoria genitore;
	TreeSet<Categoria> sottoCategorie;
	HashSet<Campo> campi;
	
	/*
	 * costruttore per categoria root
	 * */
	Categoria(String nomeCategoria, String descrizioneCategoria){
		this.nomeCategoria = nomeCategoria;
		this.descrizioneCategoria=descrizioneCategoria;
		this.root=true;
		sottoCategorie = new TreeSet<>();
		campi = new HashSet<>();
		statoDiConservazione = new Campo(NOME_STATO_DI_CONSERVAZIONE, DESCRIZIONE_DEAFAULT, !MODIFICABILE, MANDATORY);
		descrizioneLibera = new Campo(NOME_DESCRIZIONE_LIBERA, DESCRIZIONE_DEAFAULT ,!MODIFICABILE, MANDATORY);
		campi.add(descrizioneLibera);
		campi.add(statoDiConservazione);
		
	}
	
	/*
	 * costruttore per categoria non root.
	 * va specificato il genitore a differnza della categoria root.
	 * */
	Categoria(String nomeCategoria, String descrizioneCategoria, Categoria genitore){
		this.nomeCategoria = nomeCategoria;
		this.descrizioneCategoria=descrizioneCategoria;
		this.root=false;
		this.genitore=genitore;
		sottoCategorie = new TreeSet<>();
		campi = new HashSet<>();
	}
	
	public boolean isRoot() {
		return root;
	}
	
	public void setNomeCategoria(String nomeCategoria) {
		this.nomeCategoria = nomeCategoria;
	}
	
	public String getNomeCategoria() {
		return nomeCategoria;
	}
	
	public String getDescrizioneCategoria() {
		return descrizioneCategoria;
	}

	public void setDescrizioneCategoria(String descrizioneCategoria) {
		this.descrizioneCategoria = descrizioneCategoria;
	}
	
	public Categoria getGenitore() {
		if(!this.isRoot())return genitore;
		else throw new NoSuchElementException("Una Categoria root non ha genitore");
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
	
	//case sensitive
	public Campo trovaCampoPerNome(String nomeCampoDaTrovare) {
		for(Campo c : campi) {
			if(c.getNome().equals(nomeCampoDaTrovare)) return c;
		}
		return null;
	}
	
	public void modificaCampo(Campo daModificare, String nuovoNomeCampo, String nuovaDescrizione, boolean nuovoMandatory) {
		
		if(trovaCampo(daModificare)!=null) {
			trovaCampo(daModificare).setNome(nuovoNomeCampo);
			trovaCampo(daModificare).setDescrizione(nuovaDescrizione);
			trovaCampo(daModificare).setMandatory(nuovoMandatory);
		}
	}
	
	
	public void modificaNomeCampo(Campo daModificare, String nuovoNomeCampo) {
			
			if(trovaCampo(daModificare)!=null) {
				trovaCampo(daModificare).setNome(nuovoNomeCampo);
			}
		}
	
	public void modificaDescrizioneCampo(Campo daModificare, String nuovaDescrizione) {
		if(trovaCampo(daModificare)!=null) {
			trovaCampo(daModificare).setNome(nuovaDescrizione);
		}
	}
	
	public void modificaDescrizioneCampo(String nomeCampoDaModificare, String nuovaDescrizione) {
		if(trovaCampoPerNome(nomeCampoDaModificare)!=null) {
			trovaCampoPerNome(nomeCampoDaModificare).setDescrizione(nuovaDescrizione);
		}
	}
	
	public void modificaCampo(Campo daModificare, boolean nuovoMandatory) {
			
			if(trovaCampo(daModificare)!=null) {
				trovaCampo(daModificare).setMandatory(nuovoMandatory);
			}
		}
	
	public void rimuoviCampo(Campo daRimuovere) {
		if(trovaCampo(daRimuovere)!=null && daRimuovere.isModificabile()) campi.remove(daRimuovere);
	}
	
	/*public boolean equals(Categoria daParagonare){
		boolean stesso;
		if(this.nomeCategoria.equals(daParagonare.nomeCategoria)&&this.descrizioneLibera.equals(daParagonare.descrizioneLibera)) stesso=true;
		else stesso=false;
		
		return stesso;
	}*/
	
	public int compareTo(Categoria daComparare) {
		
		if(this.nomeCategoria.compareTo(daComparare.nomeCategoria)>=0) return 1;
		else if(this.nomeCategoria.compareTo(daComparare.nomeCategoria)<=0) return -1;
		else if(this.nomeCategoria.compareTo(daComparare.nomeCategoria)==0) return 0;
		else return 999;
	}
	
	public boolean aggiungiSottoCategoria(Categoria daAggiungere) {
		if(!daAggiungere.root && sottoCategorie.add(daAggiungere)) return true;
		else return false;
		
	}
	
	public boolean hasSottoCategorie(){
		if(!this.sottoCategorie.isEmpty()) return true;
		else return false;
	}

	//solo per test
	public String toString(){
		return this.getNomeCategoria();
	}
	
	public Categoria trovaRoot() {
		Categoria c = this.getGenitore();
		while(!c.isRoot()) {
			c=c.getGenitore();
		}
		return c;
	}
	
	public Categoria trovaSottoCategoria(String nome) {
		for(Categoria c : this.sottoCategorie) {
			if(c.getNomeCategoria().equals(nome)) return c;
		}
		return (new Categoria("", ""));
	}
	
	public Categoria findLeaf(Categoria root, String nome) {
		Categoria currentLeaf = root;
			if(!currentLeaf.sottoCategorie.contains(trovaSottoCategoria(nome))) {
				for(Categoria leaf : currentLeaf.sottoCategorie) {
					currentLeaf = findLeaf(leaf, nome);
					if(currentLeaf.getNomeCategoria().equals(nome)) return currentLeaf;
				}
			}
			return root;
	}

}
