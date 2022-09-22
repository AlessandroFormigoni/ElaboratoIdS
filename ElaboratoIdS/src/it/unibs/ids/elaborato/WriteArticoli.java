package it.unibs.ids.elaborato;

import java.io.FileOutputStream;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class WriteArticoli {
	
	static String filename = "./Data/Articoli.xml";
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
	
	public static void writeArticoli(List<Articolo> articoli) {
		try {
			xmlw.writeStartDocument();
			xmlw.writeStartElement("articoli");
			for(Articolo art : articoli) {
				try {
				
				xmlw.writeStartElement("articolo");
				xmlw.writeAttribute("categoria", art.getCategoriaArticolo().getNomeCategoria());
				for(Campo cam : art.getCategoriaArticolo().getListaCampi()) {
					xmlw.writeAttribute("campo", cam.getNome());
					xmlw.writeAttribute("descrCampo", cam.getDescrizione());
				}
				xmlw.writeAttribute("statoOfferta", art.getStatoOfferta().name());
				xmlw.writeAttribute("creatore", art.getCreatore().getName());
				
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			xmlw.writeEndElement();
			xmlw.flush();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public static void write(List<Articolo> articoli) {
		initializeWriter();
		writeArticoli(articoli);
	}

}
