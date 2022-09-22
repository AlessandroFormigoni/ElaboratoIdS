package it.unibs.ids.elaborato;

public class Articolo {
	private Categoria categoriaArticolo;
	private StatiOfferta statoOfferta;
	private Utente creatore;
	
	public Articolo(Categoria categoriaArticolo, StatiOfferta statoOfferta, Utente creatore) {
		this.categoriaArticolo = categoriaArticolo;
		this.statoOfferta = statoOfferta;
		this.creatore = creatore;
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
