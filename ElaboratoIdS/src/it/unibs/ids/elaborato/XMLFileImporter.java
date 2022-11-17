package it.unibs.ids.elaborato;

import java.io.File;

import java.io.FileInputStream;
import java.util.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;


public class XMLFileImporter {
	static String baseDirectory = "./Imports";
	static XMLStreamReader xmlr = null;
	
	public static void initializeReader(String absolutePath) {
		try {
			xmlr = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(absolutePath));
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void importFiles(CategoryController cc, UserBaseController uc, AppointmentBaseController ac,  HashMap<Offerta, String> offerteUpdate) {
		File fileList = new File(baseDirectory);
		if(fileList.list().length!=0) {
		for(String fileName : fileList.list()) {
			fileName = baseDirectory + "/" + fileName;
			initializeReader(fileName);
			findFileReaderAndWrite(xmlr, cc, uc, ac, offerteUpdate, fileName);
		}
		} else {
			System.out.println("Nessun file da leggere trovato nella cartella Imports");
		}
	}
	
	public static void findFileReaderAndWrite(XMLStreamReader xmlrd, CategoryController cc, UserBaseController uc, AppointmentBaseController ac,  HashMap<Offerta, String> offerteUpdate, String fileName) {
		try {
			while(xmlrd.hasNext()) {
				switch(xmlrd.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlrd.getLocalName()) {
					case "appuntamenti":
						List<ConfAppointment> appuntamenti = new ArrayList<>();
						appuntamenti.addAll(AppuntamentiReader.readAppuntamentiFromFileName(fileName, xmlrd));
						ac.getAppointmentList().addAll(appuntamenti);
						break;
					case  "articoli":
						List<Articolo> articoli = new ArrayList<>();
						articoli.addAll(ArticoloReader.readArticoliFromFileName(cc, uc, fileName, xmlrd));
						cc.setArticoli(articoli);
						break;
					case "categorie":
						List<Categoria> categorie = new ArrayList<>();
						categorie.addAll(CategoryReader.readCategoriesFromFileName(fileName, xmlrd));
						cc.getCategorie().addAll(categorie);
						break;
					case "offerte":
						List<Offerta> offerte = new ArrayList<>();
						offerte.addAll(OfferteReader.readOfferte(cc, ac, fileName, xmlrd));
						ac.getOfferteList().addAll(offerte);
						break;
					case "utenti":
						List<Utente> utenti = new ArrayList<>();
						utenti.addAll(UserRegistryReader.getReadUserRegFromFileName(fileName, xmlrd));
						uc.getListaUtenti().addAll(utenti);
						break;
					}
				 case XMLStreamConstants.END_ELEMENT:
					 break;
				 case XMLStreamConstants.COMMENT:
					 break; 
				 case XMLStreamConstants.CHARACTERS:
					 break;
				}
				xmlrd.next();
			}
		} catch (NoSuchElementException nsee) {System.out.println("Lettura file batch terminata");}
		catch (Exception e) {e.printStackTrace();}
	}
	
	public static void deleteFiles() {
		File fileList = new File(baseDirectory);
		int fileListLength = fileList.list().length;
		int counter = 0;
		if(fileListLength!=0) {
		for(String fileName : fileList.list()) {
			fileName = baseDirectory + "/" + fileName;
			File file = new File(fileName);
			file.delete();
			counter++;
		}
		} else {
			System.out.println("Nessun file da eliminare trovato nella cartella Imports");
		}
		if(fileListLength!=0 && counter == (fileListLength)) System.out.println("File eliminati con successo");
		else if(counter != (fileListLength)) System.out.println("Errore: Non sono stati eliminati tutti i file");
	}
	

}
