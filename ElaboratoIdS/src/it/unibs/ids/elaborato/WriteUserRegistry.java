package it.unibs.ids.elaborato;
import java.io.FileOutputStream;
import java.util.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class WriteUserRegistry {
	static String filename = "./Data/UserRegistry.xml";
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
	
	public static void writeUsers(List<Utente> utenti) {
		try {
			xmlw.writeStartElement("utenti");
			xmlw.writeStartElement("configuratori");
			utenti.stream().filter(u -> u.isAuthorized()).forEach(conf -> {
				try {
				xmlw.writeStartElement("configuratore");
				xmlw.writeAttribute("nome", conf.getName());
				xmlw.writeAttribute("password", conf.getPassword());
				if(!conf.isFirstAccess) xmlw.writeAttribute("firstAccess", "1");
				else xmlw.writeAttribute("firstAccess", "0");
				xmlw.writeEndElement();
				} catch(Exception e) {
					e.printStackTrace();
				}
			});
			xmlw.writeEndElement();
			xmlw.writeStartElement("fruitori");
			utenti.stream().filter(u -> !u.isAuthorized()).forEach(fruit -> {
				try {
					xmlw.writeStartElement("fruitore");
					xmlw.writeAttribute("nome", fruit.getName());
					xmlw.writeAttribute("password", fruit.getPassword());
					if(!fruit.isFirstAccess) xmlw.writeAttribute("firstAccess", "1");
					else xmlw.writeAttribute("firstAccess", "0");
					xmlw.writeEndElement();
				} catch(Exception e) {
					e.printStackTrace();
				}
			});
			xmlw.writeEndElement();
			xmlw.writeEndElement();
			xmlw.flush();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public static void write(List<Utente> utenti) {
		initializeWriter();
		writeUsers(utenti);
	}
}
