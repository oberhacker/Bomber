package xnetcom.bomber.entidades;

import java.util.ArrayList;
import java.util.Iterator;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Coordenadas;

public class AlmacenDeEnemigos {
	private ArrayList<EnemigoBase> almacen;
	private BomberGame context;
	
	
	public AlmacenDeEnemigos(BomberGame context) {
		this.context = context;
		almacen = new ArrayList<EnemigoBase>();
	}
	
	

	

	
	Thread handler;
	//ArrayList<DatosEnmigo> enemigosAcrear= new ArrayList<AlmacenDeEnemigos.DatosEnmigo>();	
		
	/*
	public void crearElemento(){
		System.out.println("asasdasdasdsad");
		synchronized (enemigosAcrear) {
			Iterator<DatosEnmigo> iterador = enemigosAcrear.iterator();
			if(iterador.hasNext()){
				System.out.println("creadoo");
				DatosEnmigo elemento = iterador.next();
				createEnemigo(elemento.getTipo(),elemento.getColumna(),elemento.getFila());
				enemigosAcrear.remove(elemento);
			}				
		}
	}
	
	

	*/
	
	
	/*
	class DatosEnmigo {		
		String tipo;
		int fila;
		int columna;
		int mapa;
		
		public String getTipo() {
			return tipo;
		}

		public int getFila() {
			return fila;
		}

		public int getColumna() {
			return columna;
		}

		public int getMapa() {
			return mapa;
		}
		
		public DatosEnmigo(String tipo, int mapa, int columna, int fila){
			this.tipo=tipo;
			this.mapa=mapa;
			this.columna=columna;
			this.fila=fila;
		}
	}
*/
	
	public EnemigoBase createEnemigo(final String tipoEnemigo, final int columna, final int fila) {		
		
		
		System.out.println("createEnemigo");
		System.out.println("enemigos totales "+almacen.size());
		EnemigoBase enemigo = getInstanciaEnemigo(tipoEnemigo, columna, fila);		
		synchronized (almacen) {			
			almacen.add(enemigo);
			enemigo.attachEscena(context.getEscenaJuego());
			//try { context.getEscenaJuego().sortChildren();}catch(Exception e ){e.printStackTrace();}
			System.out.println("creamos tipoEnemigo " + tipoEnemigo + " columna " + columna + " fila " + fila);
		}
		try{			
			context.getEscenaJuego().sortChildren();
		}catch (Exception e){
			System.out.println("Error en context.getEscenaJuego().sortChildren();");
			e.printStackTrace();
		}
		return enemigo;
	}
	
	/*
	public void reciclaEnemigo(String tipoEnemigo, int numMap, int columna, int fila){
		
		for (Iterator<EnemigoBase> iterator = almacen.iterator(); iterator.hasNext();) {
			 EnemigoBase enemigo = iterator.next();
			 if (enemigo.getTipoEnemigo().toString().equals(tipoEnemigo) && enemigo.isMuerto()){
				 System.out.println("recicloooo");
				 
				 
			 }
			
		}
		
		
		
		
		
	}
	
	*/
	
	
	public EnemigoBase getInstanciaEnemigo(String tipoEnemigo, int columna,int  fila){
		EnemigoBase enemigo=null;
		if (tipoEnemigo.equals("EN_GLOBO")){
			enemigo = new EnemigoGlobo(context, columna, fila);
		}else if (tipoEnemigo.equals("EN_GOTA_AZUL")){
			enemigo = new EnemigoGota(context, columna, fila);	
		}else if (tipoEnemigo.equals("EN_FANTASMA")){
			enemigo = new EnemigoFantasma(context, columna, fila);
		}else if (tipoEnemigo.equals("EN_MOCO")){
			enemigo = new EnemigoMoco(context, columna, fila);
		}else if (tipoEnemigo.equals("EN_MONEDA")){
			enemigo = new EnemigoMoneda(context, columna, fila);
		}else if (tipoEnemigo.equals("EN_GOTA_NARANJA")){
			enemigo = new EnemigoGotaNaranja(context, columna, fila);
		}else if (tipoEnemigo.equals("EN_GLOBO_AZUL")){//
			enemigo = new EnemigoGloboAzul(context, columna, fila);
		}else if (tipoEnemigo.equals("EN_MOCO_ROJO")){
			enemigo = new EnemigoMocoRojo(context, columna, fila);
		}else if (tipoEnemigo.equals("EN_MONEDA_MARRON")){
			enemigo = new EnemigoMonedaMarron(context, columna, fila);
		}else if (tipoEnemigo.equals("EN_GOTA_ROJA")){
			enemigo = new EnemigoGotaRoja(context, columna, fila);
		}else{
			System.out.println("errrorrrrrr carcando wenemigossss");
			enemigo = new EnemigoGlobo(context, columna, fila);
		}		
		return enemigo;
	}
	
	public void pausarEnemigos(){	
		synchronized (almacen) {
			for (EnemigoBase enemigo : almacen) {
				enemigo.setIgnoreUpdate(true);
				
			}
		}

	}
	
	
	public void playEnemigos(){
		synchronized (almacen) {
			for (EnemigoBase enemigo : almacen) {
				enemigo.setIgnoreUpdate(false);
				
			}
		}

	}
	
	


	
	
	public void reciclarEnemigos() {
		System.out.println("killEmAll");		
		synchronized (almacen) {
			Iterator<EnemigoBase> itr = almacen.iterator();
			EnemigoBase enm;
			while (itr.hasNext()) {
				System.out.println("llamada");
				enm = itr.next();
				enm.eliminaBicho();
				//almacen.remove(enm);
			}
			almacen.clear();
		}


	}
	
	

	public void iniciaEnemigos(){
		for (EnemigoBase enemigo : almacen) {			
			enemigo.iniciaInteligenciaIA();
			}
	}
	
	
	public boolean creadoandler=false;
	public boolean iniciadoHandler=false;
	

	
	public void matar(ArrayList<Coordenadas> corr, int numeroBomba){		
		for (EnemigoBase enemigo : almacen) {
			enemigo.matar(corr,numeroBomba);
		}
		if (context.getGameManager().getEstado().getEnemigosRestantes()==0){
			context.getGameManager().todosEnemigosMuertos();
		}
	}
	
	
	public void PararTodosEnemigos() {
		Iterator<EnemigoBase> itr = almacen.iterator();
		EnemigoBase enm;
		while (itr.hasNext()) {
			enm = itr.next();
			enm.pararBicho();
		}

	}
	
}
