package it.unibs.ids.elaborato;

import java.util.*;

public class Articolo {
	private String nomeArticolo;
	private Categoria categoriaArticolo;
	private StatiOfferta statoOfferta;
	private Utente creatore;
	private List<Campo> campiArticolo;
	
	public Articolo(String nomeArticolo, Categoria categoriaArticolo, StatiOfferta statoOfferta, Utente creatore) {
		this.nomeArticolo = nomeArticolo;
		this.categoriaArticolo = categoriaArticolo;
		this.statoOfferta = statoOfferta;
		this.creatore = creatore;
		cloneCampi(categoriaArticolo);
	}

	private void cloneCampi(Categoria categoriaArticolo) {
		campiArticolo = new ArrayList<Campo>();
		for(Campo cam : categoriaArticolo.getTuttiCampi()) {
			try {
				this.campiArticolo.add(cam.clone());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
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
	
	
	public List<Campo> getCampiArticolo() {
		return campiArticolo;
	}

	public void setCampiArticolo(List<Campo> campiArticolo) {
		this.campiArticolo = campiArticolo;
	}

	public Campo getCampoFromNome(String nome) {
		for (Campo campo : campiArticolo) {
			if(campo.getNome().equals(nome))
				return campo;
		}
		return null;
	}
	
	public void changeStatoOfferta(StatiOfferta stato) {
		this.statoOfferta = stato;
	}
	
	
}
