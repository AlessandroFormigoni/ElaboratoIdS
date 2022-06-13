package it.unibs.ids.elaborato;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class WriteCategorie {
	static String filename = "./Data/Categories.xml";
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
			categoria = orderList(categoria);
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
			writeFields(categoria);
			xmlw.writeEndElement();
			xmlw.flush();
		} catch(Exception e) {e.printStackTrace();}
	}
	
	public static void writeFields(List<Categoria> categorie) {
		try {
			xmlw.writeStartElement("campi");
			for(Categoria cat: categorie) {
				for(Campo cam: cat.getListaCampi()) {
					xmlw.writeStartElement("campo");
					xmlw.writeAttribute("nome", cam.getNome());
					xmlw.writeAttribute("descrizione", cam.getDescrizione());
					if(cam.isModificabile()) xmlw.writeAttribute("modificabile", "true");
						else xmlw.writeAttribute("modificabile", "false");
					if(cam.isMandatory()) xmlw.writeAttribute("mandatory", "true");
						else xmlw.writeAttribute("mandatory", "false");
					xmlw.writeAttribute("proprietario", cat.getNomeCategoria());
					
					xmlw.writeEndElement();
				}
			}
			xmlw.writeEndElement();
		} catch (XMLStreamException e) {
			e.printStackTrace();
		}
	}
	
	public static void write(List<Categoria> categorie) {
		initializeWriter();
		writeCategories(categorie);
	}
	
	private static List<Categoria> orderList(List<Categoria> categorie) {
		List<Categoria> newList = new ArrayList<>();
		categorie.stream().filter(cat -> cat.isRoot()).forEach(cat -> newList.add(cat));
		categorie.stream().filter(cat -> !cat.isRoot()).forEach(cat -> newList.add(cat));
		return newList;
	}

}
