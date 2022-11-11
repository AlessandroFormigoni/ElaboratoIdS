package it.unibs.ids.elaborato;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import it.unibs.fp.mylib.InputDati;

public class OfferteReader {
	static String filename = "./Data/Offerte.xml";
	static String absolutePath = new File(filename).getAbsolutePath();
	static String baseDirectory = "/ElaboratoIdS/Data";
	static XMLStreamReader xmlr = null;
	private static ArrayList<Offerta> offerte = new ArrayList<>();	
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
	
	public static void initializeReader(String fileName) {
		try {
			xmlr = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream(fileName));
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void extractOfferte(CategoryController cc, AppointmentBaseController ac) {
		try {
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlr.getLocalName()) {
					case "offerte":
						break;
					case "offerta":
						int id = Integer.parseInt(xmlr.getAttributeValue(0));
						Calendar scadenza = Calendar.getInstance();
						scadenza.setTimeInMillis(Long.parseLong(xmlr.getAttributeValue(1)));
						int num = xmlr.getAttributeCount();
						String utente = xmlr.getAttributeValue(num-3);
						Articolo artA = cc.getArticolo(xmlr.getAttributeValue(num-2));
						Articolo artB = cc.getArticolo(xmlr.getAttributeValue(num-1));
						Offerta off = new Offerta(scadenza, artA, artB);
						if(!artA.getStatoOfferta().equals(StatiOfferta.ACCOPPIATA)) {
							ConfAppointment app = AppuntamentiReader.readAppointment(xmlr, 2);
							off.setAppointment(app);
						}
						off.setId(id);
						offerte.add(off);
						ac.setUpdate(off, utente);

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
	
	public static void extractOfferte(CategoryController cc, AppointmentBaseController ac, XMLStreamReader xmlr) {
		try {
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlr.getLocalName()) {
					case "offerte":
						break;
					case "offerta":
						int id = Integer.parseInt(xmlr.getAttributeValue(0));
						Calendar scadenza = Calendar.getInstance();
						scadenza.setTimeInMillis(Long.parseLong(xmlr.getAttributeValue(1)));
						int num = xmlr.getAttributeCount();
						String utente = xmlr.getAttributeValue(num-3);
						Articolo artA = cc.getArticolo(xmlr.getAttributeValue(num-2));
						Articolo artB = cc.getArticolo(xmlr.getAttributeValue(num-1));
						Offerta off = new Offerta(scadenza, artA, artB);
						if(!artA.getStatoOfferta().equals(StatiOfferta.ACCOPPIATA)) {
							ConfAppointment app = AppuntamentiReader.readAppointment(xmlr, 2);
							off.setAppointment(app);
						}
						off.setId(id);
						offerte.add(off);
						ac.setUpdate(off, utente);

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
	
	public static List<Offerta> readOfferte(CategoryController cc, AppointmentBaseController ac) {
		initializeReader();
		extractOfferte(cc, ac);
		return offerte;
	}
	
	public static List<Offerta> readOfferte(CategoryController cc, AppointmentBaseController ac, String fileName, XMLStreamReader xmlrd) {
		extractOfferte(cc, ac, xmlrd);
		return offerte;
	}
}
