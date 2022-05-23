package it.unibs.ids.elaborato;

/*
 * classe usata per descrivere un campo di una categoria
 * */

public class Campo {
	
	String nome;
	String descrizione;
	boolean modificabile;
	boolean mandatory;
	
	
	Campo(String nome, String descrizione, boolean modificabile, boolean mandatory){
		this.nome = nome;
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
	
	public void modificaNome(String nome) {
		if(isModificabile()) this.nome = nome;
	}
	
	public void modificaDescrizione(String descrizione){
		 this.descrizione = descrizione;
	}
	
	public void modificaMandatory(boolean mandatory) {
		if(isModificabile()) this.mandatory = mandatory;
	}
	
	public boolean equals(Campo daParagonare) {
		boolean stesso;
		if(this.mandatory==daParagonare.mandatory && this.nome.equals(daParagonare.nome) && this.descrizione.equals(daParagonare.descrizione)) stesso = true;
		else stesso = false;
		
		return stesso;
		
	}
	
	
}
