package it.unibs.ids.elaborato;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import it.unibs.fp.mylib.InputDati;

public class ArticoloReader {
	static String filename = "./Data/Articoli.xml";
	static String absolutePath = new File(filename).getAbsolutePath();
	static String baseDirectory = "/ElaboratoIdS/Data";
	static XMLStreamReader xmlr = null;
	private static ArrayList<Articolo> articoli = new ArrayList<>();	
	public static void chooseFilename() {
		File fileList = new File(baseDirectory);
		ArrayList<String> validFiles = new ArrayList<>();
		for(String fileName : fileList.list()) {
				System.out.println("- "+fileName);
				validFiles.add(fileName);
		}
		
		do {
		filename = InputDati.leggiStringaNonVuota("Inserisci il nome del file dal leggere");
		} while (!validFiles.contains(filename));
		filename = baseDirectory+"/"+filename;
		
	}
	
	public static void initializeReader() {
		try {
			xmlr = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(absolutePath));
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void extractArticoli(CategoryController cc, UserController uc) {
		try {
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlr.getLocalName()) {
					case "articoli":
						break;
					case "articolo":
						String nome = xmlr.getAttributeValue(0);
						Categoria cat = cc.getCategoria(xmlr.getAttributeValue(1));
						int num = xmlr.getAttributeCount();
						for(int i=1; i<(num-3); i += 2) {
							cat.modificaCampo(cat.trovaCampoPerNome(xmlr.getAttributeValue(i)), xmlr.getAttributeValue(i), xmlr.getAttributeValue(i+1), cat.trovaCampoPerNome(xmlr.getAttributeValue(i)).isModificabile());
						}
						StatiOfferta so = StatiOfferta.valueOf(xmlr.getAttributeValue(xmlr.getAttributeCount()-2).toUpperCase());
						Utente user = uc.getUser(xmlr.getAttributeValue(xmlr.getAttributeCount()-1));
						articoli.add(new Articolo(nome, cat, so, user));
					}
					break;
				 case XMLStreamConstants.END_ELEMENT:
					 break;
				 case XMLStreamConstants.COMMENT:
					 break; 
				 case XMLStreamConstants.CHARACTERS:
					 break;
				}
				xmlr.next();
			}
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static List<Articolo> readArticoli(CategoryController cc, UserController uc) {
		initializeReader();
		extractArticoli(cc, uc);
		return articoli;
	}
}
