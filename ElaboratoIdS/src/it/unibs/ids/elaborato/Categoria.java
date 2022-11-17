package it.unibs.ids.elaborato;

import java.util.*;

/**
 * classe utilizzata per distinguere gli articoli
 * @author dani0
 * 
 *
 */


public class Categoria implements Comparable<Categoria>, Cloneable {
	
	 public static final String NOME_STATO_DI_CONSERVAZIONE = "Stato di Conservazione";
	 public static final String NOME_DESCRIZIONE_LIBERA = "Descrizione libera";
	 public static final String DESCRIZIONE_DEAFAULT = "da compilare";
	 public static final boolean MODIFICABILE = true;
	 public static final boolean MANDATORY = true;
	 
	
	private String nomeCategoria;
	private String descrizioneCategoria;
	private boolean root;
	private Categoria genitore;
	TreeSet<Categoria> sottoCategorie;
	HashSet<Campo> campi;
	Map<Categoria, Set<Categoria>> mappa = new HashMap<>();
	
	/*
	 * costruttore per categoria root
	 * */
	Categoria(String nomeCategoria, String descrizioneCategoria){
		this.nomeCategoria = nomeCategoria;
		this.descrizioneCategoria=descrizioneCategoria;
		this.root=true;
		sottoCategorie = new TreeSet<>();
		campi = new HashSet<>();
		campi.add(new Campo(NOME_STATO_DI_CONSERVAZIONE, DESCRIZIONE_DEAFAULT, !MODIFICABILE, MANDATORY));
		campi.add(new Campo(NOME_DESCRIZIONE_LIBERA, DESCRIZIONE_DEAFAULT ,!MODIFICABILE, MANDATORY));
		creaMappa();
		
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
		this.genitore.sottoCategorie.add(this);
		campi = new HashSet<>();
		sottoCategorie = new TreeSet<>();
		creaMappa();
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
		if(!campo.getNome().equals(Categoria.NOME_DESCRIZIONE_LIBERA)&&!campo.getNome().equals(Categoria.NOME_STATO_DI_CONSERVAZIONE))campi.add(campo);
		}
	
	public Campo trovaCampo(Campo daTrovare) {
		for(Campo c : campi) {
			if(c.equals(daTrovare))  return daTrovare;
		}
		
		return null;
	}
	
	//case sensitive
	public Campo trovaCampoPerNome(String nomeCampoDaTrovare) {
		for(Campo c : getTuttiCampi()) {
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
	
	public int compareTo(Categoria daComparare) {
		
		if(this.nomeCategoria.compareTo(daComparare.nomeCategoria)>=0) return 1;
		else if(this.nomeCategoria.compareTo(daComparare.nomeCategoria)<=0) return -1;
		else if(this.nomeCategoria.compareTo(daComparare.nomeCategoria)==0) return 0;
		else return 999;
	}
	
	public boolean aggiungiSottoCategoria(Categoria daAggiungere) {
		if(!daAggiungere.root && sottoCategorie.add(daAggiungere)) {  
			this.creaMappa();
			return true;
		}
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
	
	public synchronized Categoria findLeaf(Categoria root, String nome) {
		Categoria currentLeaf = root;
			if(!currentLeaf.sottoCategorie.contains(trovaSottoCategoria(nome))) {
				for(Categoria leaf : currentLeaf.sottoCategorie) {
					currentLeaf = findLeaf(leaf, nome);
					if(currentLeaf.getNomeCategoria().equals(nome)) return currentLeaf;
				}
			}
			return root;
	}
	
	public void putMappa(Map<Categoria, Set<Categoria>> mappa) {
		this.mappa.putAll(mappa);
	}
	
	public void creaMappa(){
		
		if(!this.isRoot()) {
			mappa.put(this, this.sottoCategorie);
			this.getGenitore().putMappa(mappa);
			this.getGenitore().creaMappa();
		} 
		else {
			mappa.put(this, this.sottoCategorie);
		}
		
		
	}
	
	public Map<Categoria, Set<Categoria>> getMappa(){
		return mappa;
	}
	
	public static synchronized Categoria treeGet(Set<Categoria> tree, Categoria obj) {
		for(Categoria t : tree) {
			if(t.equals(obj)) return t;
		}
		return null;
	}
	
	public static synchronized Categoria treeGet(Set<Categoria> tree, String obj) {
		for(Categoria t : tree) {
			if(t.getNomeCategoria().equals(obj)) return t;
		}
		return null;
	}
	
	public TreeSet<Categoria> treeReconstructor(Map<Categoria, Set<Categoria>> mappa) {
		TreeSet<Categoria> tree = new TreeSet<>();
		mappa.keySet().stream().filter(r -> r.isRoot()).forEach(v -> {
			//Categoria root = new Categoria("", "");
			try {
				tree.add((Categoria)v.clone());
				
				Map<Categoria, Set<Categoria>> copia = new HashMap<>();
				copia.putAll(mappa);
				
				List<Categoria> visited = new ArrayList<>();
				for(Categoria key : mappa.keySet()) {
					if(visited.contains(key)) continue;
					Categoria root = findLeaf(tree.first(), key.getDescrizioneCategoria());
					if(root!=null && root.hasSottoCategorie()) {
							TreeSet<Categoria> lista = root.sottoCategorie;
							if(lista.containsAll(visited)) continue;
							synchronized(lista) {
							lista.addAll(copia.get(key));
							}
							
								
					}
					
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
		});
		
		return tree;
	}
	
	public Object clone() throws CloneNotSupportedException {
		Categoria cat = null;
		cat = (Categoria) super.clone();
		return cat;
    }
	
	public Set<Campo> getSetCampi(){
		return this.campi;
	}
	public List<Campo> getListaCampi(){
		return new ArrayList<Campo>(this.campi);
	}
	
	public List<Campo> getTuttiCampi() {
		ArrayList<Campo> campi = new ArrayList<>();
		campi.addAll(getListaCampi());
		
		Categoria g = this.genitore;
		while(!g.isRoot()) {
			campi.addAll(genitore.getListaCampi());
			g = g.genitore;
		}
		campi.addAll(g.getListaCampi());
		return campi;
	}
	
}
