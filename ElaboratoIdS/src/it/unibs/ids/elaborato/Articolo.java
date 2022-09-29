package it.unibs.ids.elaborato;

public class Articolo {
	private String nomeArticolo;
	private Categoria categoriaArticolo;
	private StatiOfferta statoOfferta;
	private Utente creatore;
	
	public Articolo(String nomeArticolo, Categoria categoriaArticolo, StatiOfferta statoOfferta, Utente creatore) {
		this.nomeArticolo = nomeArticolo;
		this.categoriaArticolo = categoriaArticolo;
		this.statoOfferta = statoOfferta;
		this.creatore = creatore;
	}
	
	public String getNomeArticolo() {
		return nomeArticolo;
	}

	public Categoria getCategoriaArticolo() {
		return categoriaArticolo;
	}

	public StatiOfferta getStatoOfferta() {
		return statoOfferta;
	}
	
	public Utente getCreatore() {
		return creatore;
	}

	public void ritiraOfferta() {
		this.statoOfferta = StatiOfferta.RITIRATA;
	}
	
	
}
