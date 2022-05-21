package it.unibs.ids.elaborato;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;


import it.unibs.fp.mylib.InputDati;

public class UserRegistryReader {

	static String filename = "../ElaboratoIdS/Data/UserRegistry.xml";
	static String absolutePath = new File(filename).getAbsolutePath();
	static String baseDirectory = "/ElaboratoIdS/Data";
	static XMLStreamReader xmlr = null;
	private static ArrayList<Utente> utenti = new ArrayList<>();
	
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
	
	public static void extractUsers() {
		try {
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlr.getLocalName()) {
					case "utenti":
						break;
					case "configuratori":
						break;
					case "configuratore":
						utenti.add(new Configuratore(xmlr.getAttributeValue(0), xmlr.getAttributeValue(1)));
					case "fruitori":
						break;
					case "fruitore":
						break;
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
	
	public static ArrayList<Utente> getReadUserReg() {
		initializeReader();
		extractUsers();
		return utenti;
	}
}
