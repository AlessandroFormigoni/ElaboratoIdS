package it.unibs.ids.elaborato;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class WriteOfferte {
	static String filename = "./Data/Offerte.xml";
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
	
	public static void writeOfferte(List<Offerta> offerte, HashMap<Offerta, String> offerteUpdate) {
		try {
			xmlw.writeStartElement("offerte");
			for(Offerta off : offerte) {
				try {
				
				xmlw.writeStartElement("offerta");
				xmlw.writeAttribute("id", Integer.toString(off.getId()));
				xmlw.writeAttribute("scadenzaOfferta", Long.toString(off.getScadenza().getTimeInMillis()));
				if(!off.coppiaArticoli[0].getStatoOfferta().equals(StatiOfferta.ACCOPPIATA))
					WriteAppuntamenti.writeAppuntamento(off.getAppuntamento(), xmlw);
				xmlw.writeAttribute("ultimoUtente", offerteUpdate.get(off));
				xmlw.writeAttribute("articolo0", off.coppiaArticoli[0].getNomeArticolo());
				xmlw.writeAttribute("articolo1", off.coppiaArticoli[1].getNomeArticolo());
				
				} catch(Exception e) {
					e.printStackTrace();
				}
				xmlw.writeEndElement();
			}
			xmlw.writeEndElement();
			xmlw.flush();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public static void write(List<Offerta> offerte, HashMap<Offerta, String> offerteUpdate) {
		initializeWriter();
		writeOfferte(offerte, offerteUpdate);
	}
}
