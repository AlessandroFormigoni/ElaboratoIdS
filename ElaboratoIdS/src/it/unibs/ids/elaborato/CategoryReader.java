package it.unibs.ids.elaborato;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import it.unibs.fp.mylib.InputDati;

public class CategoryReader {
	static String filename = "./Data/Categories.xml";
	static String absolutePath = new File(filename).getAbsolutePath();
	static String baseDirectory = "/ElaboratoIdS/Data";
	static XMLStreamReader xmlr = null;
	private static ArrayList<Categoria> categorie = new ArrayList<>();
	private static Map<String, Set<String>> mappaTemp = new HashMap<>();
	
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
	
	public static void extractCategories() {
		try {
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlr.getLocalName()) {
					case "categoriaRoot":
						Categoria cat = new Categoria(xmlr.getAttributeValue(0), xmlr.getAttributeValue(1));
						categorie.add(cat);
					case "map":
						break;
					case "pair":
						String key = xmlr.getAttributeValue(0);
						Set<String> values = new TreeSet<>();
						if(xmlr.getAttributeCount()>1) {
							for(int i=1; i<xmlr.getAttributeCount(); i++) {
								values.add(xmlr.getAttributeValue(i));
							}
						}
						mappaTemp.put(key, values);
						break;
					case "categoria":
						String attributeValue = xmlr.getAttributeValue(2);
						Categoria genitore = getCategoria(attributeValue);
						Categoria sbcat = new Categoria(xmlr.getAttributeValue(0), xmlr.getAttributeValue(1), genitore);
						categorie.add(sbcat);
						break;
					case "campo":
						Campo cam = new Campo(xmlr.getAttributeValue(0), xmlr.getAttributeValue(1), xmlr.getAttributeValue(2).equals("true"), xmlr.getAttributeValue(3).equals("true"));
						for(Categoria c: categorie) {
							if(c.getNomeCategoria().equals(xmlr.getAttributeValue(4)) && !c.campi.contains(cam)) {
								c.aggiungiCampo(cam);
							}
						}
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
	
	public static void extractCategories(XMLStreamReader xmlr) {
		try {
			while(xmlr.hasNext()) {
				switch(xmlr.getEventType()) {
				case XMLStreamConstants.START_DOCUMENT:
					break;
				case XMLStreamConstants.START_ELEMENT:
					switch(xmlr.getLocalName()) {
					case "categoriaRoot":
						Categoria cat = new Categoria(xmlr.getAttributeValue(0), xmlr.getAttributeValue(1));
						categorie.add(cat);
					case "map":
						break;
					case "pair":
						String key = xmlr.getAttributeValue(0);
						Set<String> values = new TreeSet<>();
						if(xmlr.getAttributeCount()>1) {
							for(int i=1; i<xmlr.getAttributeCount(); i++) {
								values.add(xmlr.getAttributeValue(i));
							}
						}
						mappaTemp.put(key, values);
						break;
					case "categoria":
						String attributeValue = xmlr.getAttributeValue(2);
						Categoria genitore = getCategoria(attributeValue);
						Categoria sbcat = new Categoria(xmlr.getAttributeValue(0), xmlr.getAttributeValue(1), genitore);
						categorie.add(sbcat);
						break;
					case "campo":
						Campo cam = new Campo(xmlr.getAttributeValue(0), xmlr.getAttributeValue(1), xmlr.getAttributeValue(2).equals("true"), xmlr.getAttributeValue(3).equals("true"));
						for(Categoria c: categorie) {
							if(c.getNomeCategoria().equals(xmlr.getAttributeValue(4)) && !c.campi.contains(cam)) {
								c.aggiungiCampo(cam);
							}
						}
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
	
	public static List<Categoria> readCategories() {
		initializeReader();
		extractCategories();
		if(categorie.size()>0) {
			List<Categoria> tree = new ArrayList<>(reconstructedTree());
			categorie.addAll(tree);
			trimLeaves(tree);
		}
		return categorie;
	}
	
	public static List<Categoria> readCategoriesFromFileName(String fileName, XMLStreamReader xmlrd) {
		initializeReader(fileName);
		extractCategories(xmlrd);
		if(categorie.size()>0) {
			List<Categoria> tree = new ArrayList<>(reconstructedTree());
			categorie.addAll(tree);
			trimLeaves(tree);
		}
		return categorie;
	}
	
	public static Map<Categoria, Set<Categoria>> mapBuilder() throws XMLStreamException {
		Map<Categoria, Set<Categoria>> mappa = new HashMap<>();
		for(String s : mappaTemp.keySet()) {
			Categoria cat = getCategoria(s);
			Set<Categoria> catVal = new TreeSet<>();
			for(String v : mappaTemp.get(s)) {
				catVal.add(getCategoria(v));
			}
			mappa.put(cat, catVal);
		}
		return mappa;
	}
	
	public static Set<Categoria> reconstructedTree() {
		try {
		return categorie.get(0).treeReconstructor(mapBuilder());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static Categoria getCategoria(String s) {
		return categorie.stream().filter(c -> c.getNomeCategoria().equals(s)).findFirst().get();
	}

	private static void trimLeaves(List<Categoria> tree) {
		for(Categoria c : tree) {
			if(!getCategoria(c.getNomeCategoria()).equals(null)) {
				categorie.remove(categorie.indexOf(getCategoria(c.getNomeCategoria())));
			}
		}
	}

}
