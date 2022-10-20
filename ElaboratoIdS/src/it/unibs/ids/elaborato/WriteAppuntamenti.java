package it.unibs.ids.elaborato;

import java.io.FileOutputStream;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class WriteAppuntamenti {
	
	static String filename = "./Data/Appuntamenti.xml";
	static String version = "1.0";
	static String encoding = "UTF-8";
	static XMLStreamWriter xmlw = null;
	static XMLOutputFactory xmlof = null;
	
	
	public static void initializeWriter() {
		try {
			xmlof = XMLOutputFactory.newInstance();
			xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(filename), encoding);
			xmlw.writeStartDocument();
		} catch (Exception e) {e.printStackTrace();}
	}
	
	public static void writeAppuntamenti(List<ConfAppointment> appuntamenti) {
		try {
			xmlw.writeStartElement("appuntamenti");
			for(ConfAppointment app : appuntamenti) {
				try {
				
				xmlw.writeStartElement("appuntamento");
				xmlw.writeAttribute("piazza", app.getPiazza());
				int i = 0;
				for(String luoghi : app.getLuoghi()) {
					xmlw.writeAttribute("luogo"+i, luoghi);
					i++;
				}
				i = 0;
				for(String giorni : app.getGiorni()) {
					xmlw.writeAttribute("giorno"+i, giorni);
					i++;
				}
				i = 0;
				for(Float[] intervalli : app.getIntervalliOrari()) {
					xmlw.writeAttribute("iniziointervallo"+i, Float.toString(intervalli[0]));
					xmlw.writeAttribute("fineintervallo"+i, Float.toString(intervalli[1]));
					i++;
				}
				xmlw.writeAttribute("scadenza", Integer.toString(app.getScadenza()));
				
				} catch(Exception e) {
					e.printStackTrace();
				}
				xmlw.writeEndElement();
			}
			xmlw.writeEndElement();
			xmlw.flush();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public static void writeAppuntamento(ConfAppointment app, XMLStreamWriter xmlsw) throws XMLStreamException, NullPointerException {
		xmlsw.writeAttribute("piazza", app.getPiazza());
		int i = 0;
		for(String luoghi : app.getLuoghi()) {
			xmlsw.writeAttribute("luogo"+i, luoghi);
			i++;
		}
		i = 0;
		for(String giorni : app.getGiorni()) {
			xmlsw.writeAttribute("giorno"+i, giorni);
			i++;
		}
		i = 0;
		for(Float[] intervalli : app.getIntervalliOrari()) {
			xmlsw.writeAttribute("iniziointervallo"+i, Float.toString(intervalli[0]));
			xmlsw.writeAttribute("fineintervallo"+i, Float.toString(intervalli[1]));
			i++;
		}
		xmlsw.writeAttribute("scadenza", Integer.toString(app.getScadenza()));
	}
	
	public static void write(List<ConfAppointment> appuntamenti) {
		initializeWriter();
		writeAppuntamenti(appuntamenti);
	}


}
