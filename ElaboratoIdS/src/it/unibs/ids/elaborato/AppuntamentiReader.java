package it.unibs.ids.elaborato;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import it.unibs.fp.mylib.InputDati;

public class AppuntamentiReader {
	static String filename = "./Data/Appuntamenti.xml";
	static String absolutePath = new File(filename).getAbsolutePath();
	static String baseDirectory = "/ElaboratoIdS/Data";
	static XMLStreamReader xmlr = null;
	private static ArrayList<ConfAppointment> appuntamenti = new ArrayList<>();	
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
	
	public static void extractAppuntamenti() {
		try {
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlr.getLocalName()) {
					case "appuntamenti":
						break;
					case "appuntamento":
						String piazza = xmlr.getAttributeValue(0);
						List<String> luoghi = new ArrayList<>();
						int num = xmlr.getAttributeCount();
						int i = 1;
						int j = 0;
						while(xmlr.getAttributeLocalName(i).equals("luogo" + (j)) && i<num) {
							luoghi.add(xmlr.getAttributeValue(i));
							i++;
							j++;
						}
						j = 0;
						List<String> giorni = new ArrayList<>();
						while(xmlr.getAttributeLocalName(i).equals("giorno" + (j)) && i<num) {
							giorni.add(xmlr.getAttributeValue(i));
							i++;
							j++;
						}
						j = 0;
						List<Float[]> intervalliOrari = new ArrayList<>();
						while(xmlr.getAttributeLocalName(i).equals("iniziointervallo" + j) && i<num) {
							float inizio = Float.parseFloat(xmlr.getAttributeValue(i));
							float fine = Float.parseFloat(xmlr.getAttributeValue(i+1));
							Float[] intervallo = {inizio, fine};
							intervalliOrari.add(intervallo);
							i += 2;
							j++;

						}
						int scadenza = Integer.parseInt(xmlr.getAttributeValue(num-1));
						ConfAppointment app = new ConfAppointment(piazza);
								app.setGiorni(giorni);
								app.setLuoghi(luoghi);
								app.setIntervalliOrari(intervalliOrari);
								app.setScadenza(scadenza);
						appuntamenti.add(app);
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
	
	public static void extractAppuntamenti(XMLStreamReader xmlr) {
		try {
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlr.getLocalName()) {
					case "appuntamenti":
						break;
					case "appuntamento":
						String piazza = xmlr.getAttributeValue(0);
						List<String> luoghi = new ArrayList<>();
						int num = xmlr.getAttributeCount();
						int i = 1;
						int j = 0;
						while(xmlr.getAttributeLocalName(i).equals("luogo" + (j)) && i<num) {
							luoghi.add(xmlr.getAttributeValue(i));
							i++;
							j++;
						}
						j = 0;
						List<String> giorni = new ArrayList<>();
						while(xmlr.getAttributeLocalName(i).equals("giorno" + (j)) && i<num) {
							giorni.add(xmlr.getAttributeValue(i));
							i++;
							j++;
						}
						j = 0;
						List<Float[]> intervalliOrari = new ArrayList<>();
						while(xmlr.getAttributeLocalName(i).equals("iniziointervallo" + j) && i<num) {
							float inizio = Float.parseFloat(xmlr.getAttributeValue(i));
							float fine = Float.parseFloat(xmlr.getAttributeValue(i+1));
							Float[] intervallo = {inizio, fine};
							intervalliOrari.add(intervallo);
							i += 2;
							j++;

						}
						int scadenza = Integer.parseInt(xmlr.getAttributeValue(num-1));
						ConfAppointment app = new ConfAppointment(piazza);
								app.setGiorni(giorni);
								app.setLuoghi(luoghi);
								app.setIntervalliOrari(intervalliOrari);
								app.setScadenza(scadenza);
						appuntamenti.add(app);
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
	
	public static ConfAppointment readAppointment(XMLStreamReader xmlsr, int index) {
		String piazza = xmlsr.getAttributeValue(index);
		List<String> luoghi = new ArrayList<>();
		int num = xmlsr.getAttributeCount();
		int i = index + 1;
		int j = 0;
		while(xmlsr.getAttributeLocalName(i).equals("luogo" + (j)) && i<num) {
			luoghi.add(xmlsr.getAttributeValue(i));
			i++;
			j++;
		}
		j = 0;
		List<String> giorni = new ArrayList<>();
		while(xmlsr.getAttributeLocalName(i).equals("giorno" + (j)) && i<num) {
			giorni.add(xmlsr.getAttributeValue(i));
			i++;
			j++;
		}
		j = 0;
		List<Float[]> intervalliOrari = new ArrayList<>();
		while(xmlsr.getAttributeLocalName(i).equals("iniziointervallo" + j) && i<num) {
			float inizio = Float.parseFloat(xmlsr.getAttributeValue(i));
			float fine = Float.parseFloat(xmlsr.getAttributeValue(i+1));
			Float[] intervallo = {inizio, fine};
			intervalliOrari.add(intervallo);
			i += 2;
			j++;

		}
		int scadenza = Integer.parseInt(xmlsr.getAttributeValue(i));
		ConfAppointment app = new ConfAppointment(piazza);
				app.setGiorni(giorni);
				app.setLuoghi(luoghi);
				app.setIntervalliOrari(intervalliOrari);
				app.setScadenza(scadenza);
		return app;
	}
	
	public static List<ConfAppointment> readAppuntamenti() {
		initializeReader();
		extractAppuntamenti();
		return appuntamenti;
	}
	
	public static List<ConfAppointment> readAppuntamentiFromFileName(String fileName, XMLStreamReader xmlrd) {
		extractAppuntamenti(xmlrd);
		return appuntamenti;
	}
}
