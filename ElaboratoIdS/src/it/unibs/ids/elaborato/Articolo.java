package it.unibs.ids.elaborato;

public class Articolo {
	private Categoria categoriaArticolo;
	private StatiOfferta statoOfferta;
	
	public Articolo(Categoria categoriaArticolo, StatiOfferta statoOfferta) {
		this.categoriaArticolo = categoriaArticolo;
		this.statoOfferta = statoOfferta;
	}
	
	
	public Categoria getCategoriaArticolo() {
		return categoriaArticolo;
	}

	public StatiOfferta getStatoOfferta() {
		return statoOfferta;
	}
	
	
}
