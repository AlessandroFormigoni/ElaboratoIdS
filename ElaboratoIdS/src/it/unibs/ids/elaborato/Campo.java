package it.unibs.ids.elaborato;

/*
 * classe usata per descrivere un campo di una categoria
 * */

public class Campo {
	
	private String nome;
	private String descrizione;
	private boolean modificabile;
	private boolean mandatory;
	
	
	Campo(String nomeCampo, String descrizione, boolean modificabile, boolean mandatory){
		this.nome = nomeCampo;
		this.descrizione = descrizione;
		this.modificabile = modificabile;
		this.mandatory = mandatory;
		
	}
	
	public boolean isMandatory() {
		return mandatory;
	}
	
	public boolean isModificabile() {
		return modificabile;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescrizione() {
		return descrizione;
	}
	
	public void setNome(String nuovoNome) {
		if(isModificabile()) this.nome = nuovoNome;
	}
	
	public void setDescrizione(String nuovaDescrizione){
		 this.descrizione = nuovaDescrizione;
	}
	
	public void setMandatory(boolean mandatory) {
		if(isModificabile()) this.mandatory = mandatory;
	}
	
	public boolean equals(Campo daParagonare) {
		boolean stesso;
		if(this.mandatory==daParagonare.mandatory && this.nome.equals(daParagonare.nome) && this.descrizione.equals(daParagonare.descrizione)) stesso = true;
		else stesso = false;
		
		return stesso;
		
	}
	
	
}
