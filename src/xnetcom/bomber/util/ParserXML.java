package xnetcom.bomber.util;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber5.R;
import xnetcom.bomber.entidades.Moneda.TipoMoneda;
import xnetcom.bomber.sql.DatosMapa;
//import xnetcom.bomber5.R;
import android.util.Log;



public class ParserXML {




	private Document dom;
	private DocumentBuilder builder;
	private BomberGame context;
	private String nombreMapa;
	
	public ParserXML(BomberGame context){
		this.context=context;
	}
	
	
	public String getNombreMapa(int numMap){	
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {

			InputStream fraw = context.getResources().openRawResource(R.raw.maps);
			builder = factory.newDocumentBuilder();
			dom = builder.parse(fraw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Element root = dom.getDocumentElement();
		NodeList niveles = root.getElementsByTagName("nivel");		
		Element nivel =(Element)niveles.item(numMap);
		
		NodeList elementos =nivel.getChildNodes();

		for (int j = 0; j < elementos.getLength(); j++) {
			
			if(elementos.item(j).getNodeName().trim().equals("nombre")){
				nombreMapa=elementos.item(j).getTextContent();
				//System.out.println(nombreMapa);
			}		
		}		
		return nombreMapa;
	}
	
	
	/**
	 * retorna el numero de monedas de este tipo que tiene el mapa 
	 * @param numMap
	 * @param tipo
	 * @return
	 */
	public int getMonedaTipo(int numMap, TipoMoneda tipo){	
		
		
		int cantidadMoneda=0;
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			InputStream fraw = context.getResources().openRawResource(R.raw.maps);
			builder = factory.newDocumentBuilder();
			dom = builder.parse(fraw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Element root = dom.getDocumentElement();
		NodeList niveles = root.getElementsByTagName("nivel");
		
		
		Element nivel =(Element)niveles.item(numMap);
		
		NodeList elementos =nivel.getChildNodes();

		for (int j = 0; j < elementos.getLength(); j++) {
			if(elementos.item(j).getNodeName().trim().equals("moneda")){	
				
				Element moneda =(Element)elementos.item(j);				
				String tipoMoneda =moneda.getAttribute("tipo");	
				if (tipo.getValor().equals(tipoMoneda)){
					cantidadMoneda =Integer.parseInt(moneda.getTextContent());
					return cantidadMoneda;
					//System.out.println("tipoMoneda"+tipoMoneda+" cantidadMoneda"+cantidadMoneda);
				}

			}
		}
		
	
		return cantidadMoneda;
		
	}
	
	public List <DatosMapa> getDatosMapas(){

		List<DatosMapa> listaMapa = new ArrayList<DatosMapa>();
				
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			InputStream fraw = context.getResources().openRawResource(R.raw.maps);
			builder = factory.newDocumentBuilder();
			dom = builder.parse(fraw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Element root = dom.getDocumentElement();
		NodeList niveles = root.getElementsByTagName("nivel");
		Log.d("sql", "niveles.getLength()"+niveles.getLength());
		
		
		for (int i=0;i<niveles.getLength();i++){
			DatosMapa mapa= new DatosMapa(context.getBasedatos());
			Node nivel = niveles.item(i);
			System.out.println("nivel ="+i);
			mapa.setNumeroMapa(i);
			//if(i==0)mapa.setEstrellas(0);
			NodeList monedas =nivel.getChildNodes();
			
			for (int j = 0; j < monedas.getLength(); j++) {
				if(monedas.item(j).getNodeName().trim().equals("moneda")){	
					Element moneda =(Element)monedas.item(j);		
					String tipoMoneda =moneda.getAttribute("tipo");	
					int cantidadMoneda =Integer.parseInt(moneda.getTextContent());					
					if (tipoMoneda.equals("m_bomba")) mapa.setM_bomba(cantidadMoneda);
					else if (tipoMoneda.equals("m_corazon")) mapa.setM_corazon(cantidadMoneda);
					else if (tipoMoneda.equals("m_correr")) mapa.setM_correr(cantidadMoneda);
					else if (tipoMoneda.equals("m_detonador")) mapa.setM_detonador(cantidadMoneda);
					else if (tipoMoneda.equals("m_fantasma")) mapa.setM_fantasma(cantidadMoneda);
					else if (tipoMoneda.equals("m_fuerza")) mapa.setM_fuerza(cantidadMoneda);
					else if (tipoMoneda.equals("m_potenciador")) mapa.setM_potenciador(cantidadMoneda);	
					else if (tipoMoneda.equals("m_sorpresa")) mapa.setM_sorpresa(cantidadMoneda);
				}
			}
			mapa.setBoostersIniciales(mapa.getM_bomba()+mapa.getM_corazon()+mapa.getM_correr()+mapa.getM_detonador()+mapa.getM_fantasma()+mapa.getM_fuerza()+mapa.getM_potenciador()+mapa.getM_sorpresa());			
			listaMapa.add(mapa);						
		}
		return listaMapa;
		
	}
	
	public DatosMapa getDatosMapa(int numMapa){

		DatosMapa datos = new DatosMapa(null);
				
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			InputStream fraw = context.getResources().openRawResource(R.raw.maps);
			builder = factory.newDocumentBuilder();
			dom = builder.parse(fraw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Element root = dom.getDocumentElement();
		NodeList niveles = root.getElementsByTagName("nivel");		
			
			Node nivel = niveles.item(numMapa-1);			
			
			NodeList monedas =nivel.getChildNodes();
			
			for (int j = 0; j < monedas.getLength(); j++) {
				if(monedas.item(j).getNodeName().trim().equals("moneda")){	
					Element moneda =(Element)monedas.item(j);		
					String tipoMoneda =moneda.getAttribute("tipo");	
					int cantidadMoneda =Integer.parseInt(moneda.getTextContent());		
					
					if (tipoMoneda.equals("m_bomba")) datos.setM_bomba(cantidadMoneda);
					if (tipoMoneda.equals("m_corazon")) datos.setM_corazon(cantidadMoneda);
					if (tipoMoneda.equals("m_correr")) datos.setM_correr(cantidadMoneda);
					if (tipoMoneda.equals("m_detonador")) datos.setM_detonador(cantidadMoneda);
					if (tipoMoneda.equals("m_fantasma")) datos.setM_fantasma(cantidadMoneda);
					if (tipoMoneda.equals("m_fuerza")) datos.setM_fuerza(cantidadMoneda);
					if (tipoMoneda.equals("m_potenciador")) datos.setM_potenciador(cantidadMoneda);					
				}
			}
		
			return datos;	
	}
	
	public String gettiempoMapa(int numMapa){

		DatosMapa datos = new DatosMapa(null);
				
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			InputStream fraw = context.getResources().openRawResource(R.raw.maps);
			builder = factory.newDocumentBuilder();
			dom = builder.parse(fraw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		Element root = dom.getDocumentElement();
		NodeList niveles = root.getElementsByTagName("nivel");		
			
			Node nivel = niveles.item(numMapa-1);			
			
			NodeList monedas =nivel.getChildNodes();
			
			for (int j = 0; j < monedas.getLength(); j++) {
				if(monedas.item(j).getNodeName().trim().equals("tiempo")){	
					Element moneda =(Element)monedas.item(j);	
					return moneda.getTextContent();
				}
			}
		return "2:40";
			
	}
	
	
	
	
	public DatosMapa getMaximoMonedasEnNivel(int nivel) {
		DatosMapa datos = new DatosMapa(null);
		datos.setM_bomba(0);
		datos.setM_corazon(0);
		datos.setM_correr(0);
		datos.setM_detonador(0);
		datos.setM_fantasma(0);
		datos.setM_fuerza(0);
		datos.setM_potenciador(0);

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			InputStream fraw = context.getResources().openRawResource(R.raw.maps);
			builder = factory.newDocumentBuilder();
			dom = builder.parse(fraw);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		Element root = dom.getDocumentElement();
		NodeList niveles = root.getElementsByTagName("nivel");

		try {
			// Node nivel = niveles.item(numMapa-1);
			for (int i = 0; i < nivel + 1; i++) {
				Node nodenivel = niveles.item(i);
				NodeList monedas = nodenivel.getChildNodes();

				for (int j = 0; j < monedas.getLength(); j++) {
					if (monedas.item(j).getNodeName().trim().equals("moneda")) {
						Element moneda = (Element) monedas.item(j);
						String tipoMoneda = moneda.getAttribute("tipo");
						int cantidadMoneda = Integer.parseInt(moneda.getTextContent());

						if (tipoMoneda.equals("m_bomba"))
							datos.setM_bomba(datos.getM_bomba() + cantidadMoneda);
						if (tipoMoneda.equals("m_corazon"))
							datos.setM_corazon(datos.getM_corazon() + cantidadMoneda);
						if (tipoMoneda.equals("m_correr"))
							datos.setM_correr(datos.getM_correr() + cantidadMoneda);
						if (tipoMoneda.equals("m_detonador"))
							datos.setM_detonador(datos.getM_detonador() + cantidadMoneda);
						if (tipoMoneda.equals("m_fantasma"))
							datos.setM_fantasma(datos.getM_fantasma() + cantidadMoneda);
						if (tipoMoneda.equals("m_fuerza"))
							datos.setM_fuerza(datos.getM_fuerza() + cantidadMoneda);
						if (tipoMoneda.equals("m_potenciador"))
							datos.setM_potenciador(datos.getM_potenciador() + cantidadMoneda);
					}
				}

			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return datos;

	}
	
	
	
	
	
	
	
}
