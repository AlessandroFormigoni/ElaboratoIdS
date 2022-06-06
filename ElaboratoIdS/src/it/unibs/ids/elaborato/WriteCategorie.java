package it.unibs.ids.elaborato;

import java.io.FileOutputStream;

import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;

public class WriteCategorie {
	static String filename = "../Data/Categories.xml";
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
	
	public static void writeCategories(List<Categoria> categoria) {
		try {
			xmlw.writeStartElement("categorie");
			for(Categoria cat : categoria) {
				try {
				
				if(cat.isRoot()) {
					xmlw.writeStartElement("categoriaRoot");
					xmlw.writeAttribute("nome", cat.getNomeCategoria());
					xmlw.writeAttribute("descrizione", cat.getDescrizioneCategoria());
					xmlw.writeStartElement("map");
					for(Categoria sc : cat.getMappa().keySet()) {
						xmlw.writeStartElement("pair");
						xmlw.writeAttribute("key", sc.getNomeCategoria());
						int index = 0;
						for(Categoria c : cat.getMappa().get(sc)) {
							xmlw.writeAttribute("value"+index, c.getNomeCategoria());
							index++;
						}
						xmlw.writeEndElement();
					}
					xmlw.writeEndElement();
					xmlw.writeEndElement();
				} else {
					xmlw.writeStartElement("categoria");
					xmlw.writeAttribute("nome", cat.getNomeCategoria());
					xmlw.writeAttribute("descrizione", cat.getDescrizioneCategoria());
					xmlw.writeAttribute("genitore", cat.getGenitore().getNomeCategoria());
					xmlw.writeEndElement();
				}
				
				
				} catch(Exception e) {
					e.printStackTrace();
				}
				
			}
			xmlw.writeEndElement();
			xmlw.flush();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public static void write(List<Categoria> categorie) {
		initializeWriter();
		writeCategories(categorie);
	}

}
