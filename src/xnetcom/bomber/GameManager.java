package xnetcom.bomber;

import java.util.ArrayList;
import java.util.Date;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXLoader;
import org.andengine.extension.tmx.TMXLoader.ITMXTilePropertiesListener;
import org.andengine.extension.tmx.TMXProperties;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.extension.tmx.TMXTileProperty;
import org.andengine.extension.tmx.TMXTiledMap;
import org.andengine.extension.tmx.util.exception.TMXLoadException;
import org.andengine.opengl.texture.TextureOptions;

import xnetcom.bomber.andengine.MiEngine;
import xnetcom.bomber.entidades.AlmacenBombas;
import xnetcom.bomber.entidades.AlmacenDeEnemigos;
import xnetcom.bomber.entidades.AlmancenMonedas;
import xnetcom.bomber.entidades.Bomba;
import xnetcom.bomber.entidades.EnemigoBase;
import xnetcom.bomber.entidades.EnemigoBase.Tipo;
import xnetcom.bomber.entidades.EnemigoPosicion;
import xnetcom.bomber.entidades.HudDisplay;
import xnetcom.bomber.menus.MenuNivelCompletado;
import xnetcom.bomber.menus.MenuSalida;
import xnetcom.bomber.preferences.Preferencias;
import xnetcom.bomber.sql.DatabaseHandler;
import xnetcom.bomber.sql.DatosMapa;
import xnetcom.bomber.util.Aleatorio;
import xnetcom.bomber.util.ParserXML;
import android.util.Log;

public class GameManager {
	
	
	
	/*
	 * Boosters
	 */

	
	public static boolean enableEnemigos= false;
	public static boolean enableMonedas = true;	
	public static boolean enableSombras=true;
	public static boolean visibleCurrenCuadrado=false;
	 
	//private ArrayList<EnemigoBase> enemigosMapa; // enemigos del mapa actual
	


	
	
	private boolean boosterActivo=false;
	
	public boolean isBoosterActivo() {
		return boosterActivo;
	}

	public void setBoosterActivo(boolean boosterActivo) {
		this.boosterActivo = boosterActivo;
	}

	private ParserXML parser;
	private int nivel=0;
	private String snivel;
	//private int vidas;
	//private int numeroBombas;
	//private int tamBombas;	
	//private boolean controlRemoto=false;
	
	public int getNivel() {
		return nivel;
	}
	
	public String getSnivel() {
		return snivel;
	}

	int numParedes =0;
	private BomberGame context;
	//private MiAlmacenDeEnemigos almacenEnemigos;
	private AlmacenBombas polvorin;
	private AlmancenMonedas monedero;
	private TMXLoader tmxLoader;
	private Scene scene;
	private DatosMapa mapaActual;
	private Matriz matriz;
	private Matriz matrizReferencia;
	
	private TMXTiledMap mTMXTiledMap;
	private TMXTiledMap mTMXTiledMapActual;
	
	private TMXLayer tmxParedesEditor ;
	private HudDisplay display;
	private MenuSalida menuSalida;
	private BomberEstado estado;

	private MenuNivelCompletado ventanaNivelCompletado;
	ArrayList<EnemigoPosicion> enemigos= new ArrayList<EnemigoPosicion>();
	public MenuNivelCompletado getVentanaNivelCompletado() {
		return ventanaNivelCompletado;
	}

	public void setDisplay(HudDisplay display) {
		this.display = display;
	}

	public HudDisplay getDisplay() {
		return display;
	}

	private TMXLayer tmxSuelo; 
	//private TMXLayer tmxParedesGame;
	private BomberMan bomberman;
	private Rectangle currentTileRectangle;
	
	private CapaParedes capaParedes;
	AlmacenDeEnemigos miAlmacen;
	
	public AlmacenDeEnemigos getMiAlmacen() {
		return miAlmacen;
	}

	public void setMiAlmacen(AlmacenDeEnemigos miAlmacen) {
		this.miAlmacen = miAlmacen;
	}

	public GameManager (BomberGame context){
		this.context=context;		
		matriz= new Matriz();
		matrizReferencia= new Matriz();		
		estado= new BomberEstado(context);
		ventanaNivelCompletado= new MenuNivelCompletado(context);
		 miAlmacen= new AlmacenDeEnemigos(context);
		 menuSalida = new MenuSalida(context);		
	}
	
	public MenuSalida getMenuSalida() {
		return menuSalida;
	}

	public TMXLayer getTmxParedesEditor() {
		return tmxParedesEditor;
	}
	public AlmancenMonedas getMonedero() {
		return monedero;
	}


	public BomberEstado getEstado(){
		return estado;
	}
	
	
	
	public int getNumparedes(){
		return numParedes;
	}
	public void incrementanumParedes(){
		numParedes++;
	}
			
	
	public void precargaMapas(){
		scene = context.getEscenaJuego();
		InicializaCargadorTMX();

		this.mTMXTiledMap = cargaTMXmapa("mapa0");

		this.mTMXTiledMapActual=this.mTMXTiledMap;			
		
		
		tmxSuelo = this.mTMXTiledMap.getTMXLayers().get(0);// suelo		
		TMXLayer tmxPiedras = this.mTMXTiledMap.getTMXLayers().get(1);// piedras y sombras Y murozarriba	
		tmxParedesEditor = this.mTMXTiledMap.getTMXLayers().get(2);// paredes que cojo del mapa pero no represento
		TMXLayer tmxParedesGame = this.mTMXTiledMap.getTMXLayers().get(3);// capa de paredes que dibujare a partir de tmxParedesTMP, esta si se representa
		TMXLayer tmxParedesTechoGame = this.mTMXTiledMap.getTMXLayers().get(4);
		//TMXLayer tmxParedesTransp = this.mTMXTiledMap.getTMXLayers().get(5);// peredes que se copiaran  tmxParedesEditordesde para se
		//TMXLayer tmxParedesTranspTecho = this.mTMXTiledMap.getTMXLayers().get(6);
		TMXLayer tmxTechoPiedras = this.mTMXTiledMap.getTMXLayers().get(7);// techopiedras muros laterales y abajo	

		capaParedes = new CapaParedes(context);
		capaParedes.setMuestras(tmxParedesEditor);
		//capaParedes.setTmxParedesOriginal(tmxParedesEditor);
		
		capaParedes.setTmxParedesTechoOut(tmxParedesTechoGame);
		//capaParedes.setTmxParedesTransparencia(tmxParedesTransp);
		//capaParedes.setTmxParedesTransparenciaTecho(tmxParedesTranspTecho);
		capaParedes.setTmxParedesOut(tmxParedesGame);
		
		
		
		tmxSuelo.setZIndex(BomberGame.ZINDEX_SUELO);
		tmxPiedras.setZIndex(BomberGame.ZINDEX_PIEDRAS_SOMBRAS_MUROARRIBA);
		tmxParedesGame.setZIndex(BomberGame.ZINDEX_PAREDES);
		tmxParedesTechoGame.setZIndex(BomberGame.ZINDEX_PAREDES_TECHO);
		//tmxParedesTransp.setZIndex(BomberGame.ZINDEX_PAREDES_TRANSPARENTES);
		//tmxParedesTranspTecho.setZIndex(BomberGame.ZINDEX_PAREDES_TRANSPARENTES_TECHO);
		tmxTechoPiedras.setZIndex(BomberGame.ZINDEX_PIEDRAS_TECHO);
		//tmxParedesTransp.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//tmxParedesTransp.setAlpha(0.5f);
		
		//tmxParedesTranspTecho.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//tmxParedesTranspTecho.setAlpha(0.5f);			

		tmxSuelo.setScaleCenter(0, 0);
		//tmxSuelo.setScale(BomberGame.FACTOR);
		
		tmxPiedras.setScaleCenter(0, 0);
		//tmxPiedras.setScale(BomberGame.FACTOR);		
		
		tmxParedesGame.setScaleCenter(0, 0);
		//tmxParedesGame.setScale(BomberGame.FACTOR);
		
		tmxParedesTechoGame.setScaleCenter(0, 0);
		//tmxParedesTechoGame.setScale(BomberGame.FACTOR);
		
		tmxTechoPiedras.setScaleCenter(0, 0);
		
		//tmxTechoPiedras.setScale(BomberGame.FACTOR);
		
		scene.attachChild(mTMXTiledMap);
//		scene.attachChild(tmxPiedras);
//		scene.attachChild(tmxParedesGame);
//		scene.attachChild(tmxParedesTechoGame);
		//scene.attachChild(tmxParedesTransp);
		//scene.attachChild(tmxParedesTranspTecho);
//		scene.attachChild(tmxTechoPiedras);
		
		if (currentTileRectangle == null) {
			currentTileRectangle = new Rectangle(0, 0, this.mTMXTiledMap.getTileWidth(), this.mTMXTiledMap.getTileHeight(),context.getVertexBufferObjectManager());
			currentTileRectangle.setColor(1, 0, 0, 0.25f);
			scene.attachChild(currentTileRectangle);
		}
		currentTileRectangle.setVisible(false);
		
	}
	
	
	
	
	

	public void cargaNivel(int nivel){	
		
		setModoEntrenamiento(false);
		sonarArpa=true;
		if (primeraVez )context.getHudDisplay().setmiVisible(false);
		System.out.println("mapa");
		//DatosMapa mapa = getParser().getMaximoMonedasEnNivel(nivel);
		//System.out.println("mapa.getM_bomba()"+mapa.getM_bomba());
		
				
		this.nivel=nivel;
		estado.setNivel(nivel);
		
		if(hiloCreaMuros!=null){
			hiloCreaMuros.terminaEjecucion();			
			hiloCreaEnemigos.terminaEjecucion();
		}

		
		
		// kiar boosters que se hayan podido quedar del modo entrenamiento
		
		
		
		
		
		context.getHudDisplay().setVisibleMonedas(true);
		String snivel="";
		
		snivel =getParser().getNombreMapa(nivel);
		
		estado.setEnemigosRestantes(0);
		System.out.println("carga nivel "+snivel+" "+nivel);
				
	
		
		
		
		setMapaActual(context.getBasedatos().getMapa(nivel));
		//context.tostar("snivel "+snivel + " nivel"+nivel+" boosters"+mapaActual.getBoostersIniciales() +" ");
		
		System.out.println("mapaActual.getNumeroMapa()"+mapaActual.getNumeroMapa());
		this.mTMXTiledMapActual=cargaTMXmapa(snivel);			
		
		capaParedes.creaParedes();

		cargaPolvorin();
		cargarBomberMan();
		
		cargarMonedero(mapaActual);	
		context.getHudDisplay().actualizarIconos();

		
		context.getEscenaJuego().sortChildren();
		retrasaPlay();	
		miAlmacen.iniciaEnemigos();		
		play();
		String tiempo =parser.gettiempoMapa(nivel);
		String [] tiempos =tiempo.split(":");
		iniciaCuentaAtras(Integer.parseInt(tiempos[0]),Integer.parseInt(tiempos[1]));	
		context.getResouceManager().playMusicaRandom();
		
	}
	
	
	boolean primeraVez=true;
	int tiempo=2000;
	
	public void retrasaPlay(){		
		if (!primeraVez)tiempo=0;		
		new Thread(){
			public void run() {
				try {
					sleep(tiempo);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				context.getHudDisplay().setmiVisible(true);
			};
		}.start();		
		primeraVez=false;
	}
	
	
	public void recargaEnemigos(){
		estado.setEnemigosRestantes(0);
		for (EnemigoPosicion enemigo : enemigos) {			
			miAlmacen.createEnemigo(enemigo.getNombre(),  enemigo.getColumna(), enemigo.getFila());
			estado.incrementaEnemigo();			
		}
	}
	
	
	
	
	
	
	public void miresetea(){
		clearMapa();
		miAlmacen.reciclarEnemigos();
		new Thread(){
			public void run() {
				setEscenaNeutra();
				//System.gc();
				try {
					sleep(200);
					cargaNivel(nivel);
					sleep(200);
					context.getEngine().setScene(context.getEscenaJuego());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("cargamos nivel"+(nivel+1));
				
			};
		}.start();
	}
	
	
	
	public void siguienteMapa(){
		clearMapa();
		miAlmacen.reciclarEnemigos();
		new Thread(){
			public void run() {
				setEscenaNeutra();
				try {
					//System.gc();
					sleep(200);
					cargaNivel(nivel+1);
					sleep(200);
					context.getEngine().setScene(context.getEscenaJuego());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("cargamos nivel"+(nivel+1));
				
			};
		}.start();
		

	}
	
	
	public void setEscenaNeutra(){			
		//context.getEscenaJuego().onUpdate(0.02f);		
		context.getEngine().setScene(context.getEscenaLoading());
		context.getResouceManager().stopMusica();
}




	public void clearMapa(){
		// limpuia los enemigos y las bombas
		//almacenEnemigos.PararTodosEnemigos();
		//almacenEnemigos.reciclarTodosEnemigos();
		context.getResouceManager().terminadoBooster();
		miAlmacen.reciclarEnemigos();
		monedero.reseteaMonedas();
		pause();
		ArrayList<Bomba> aL = getPolvorin().getAlmacen();
		for (Bomba bomba : aL) {
			bomba.preDesarmar();
			try{bomba.desarmarBomba();}catch(Exception a){a.printStackTrace();}
		}		
	}
	
	
	
	public void InicializaCargadorTMX() {
		
		tmxLoader = new TMXLoader(context, context.getEngine().getTextureManager(), TextureOptions.BILINEAR_PREMULTIPLYALPHA,context.getVertexBufferObjectManager(), new ITMXTilePropertiesListener() {
			int num=0;
			
			public void onTMXTileWithPropertiesCreated(final TMXTiledMap pTMXTiledMap, final TMXLayer pTMXLayer, final TMXTile pTMXTile, final TMXProperties<TMXTileProperty> pTMXTileProperties) {

				 //System.out.println("Valdosin"+num);
				 num++;
				if (pTMXTileProperties.containsTMXProperty("piedra", "true")) {
					matriz.setValor(BomberGame.PIEDRA, pTMXTile.getTileRow(), pTMXTile.getTileColumn());
					matrizReferencia.setValor(BomberGame.PIEDRA, pTMXTile.getTileRow(), pTMXTile.getTileColumn());
				} else if (pTMXTileProperties.containsTMXProperty("pared", "true")) {
					matriz.setValor(BomberGame.PARED, pTMXTile.getTileRow(), pTMXTile.getTileColumn());
					matrizReferencia.setValor(BomberGame.PARED, pTMXTile.getTileRow(), pTMXTile.getTileColumn());
					if (pTMXTile.getTileRow() != 14) {// por que hay paredes de muestra en la 14 que no se ven
						incrementanumParedes();
					}
					// //System.out.println(numParedes+"incrementa pared "+pTMXTile.getTileRow()+" "+pTMXTile.getTileColumn());
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "globo")){
					System.out.println("enemigo globo");
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_GLOBO.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_GLOBO.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "fantasma")){
					System.out.println("enemigo glofantasma");
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_FANTASMA.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_FANTASMA.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "moco")){
					System.out.println("enemigo moco");
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_MOCO.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_MOCO.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "gota")){
					System.out.println("enemigo gota");
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_GOTA_AZUL.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_GOTA_AZUL.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "moneda")){
					System.out.println("enemigo moneda");
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_MONEDA.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_MONEDA.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "gotanaranja")){
					System.out.println("enemigo gotanaraja");
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_GOTA_NARANJA.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_GOTA_NARANJA.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "GLOBO_AZUL")){
					
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_GLOBO_AZUL.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_GLOBO_AZUL.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "MOCO_ROJO")){
				
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_MOCO_ROJO.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_MOCO_ROJO.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "MONEDA_MARRON")){
					
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_MONEDA_MARRON.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_MONEDA_MARRON.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}else if (pTMXTileProperties.containsTMXProperty("enemigo", "GOTA_ROJA")){
				
					miAlmacen.createEnemigo(EnemigoBase.Tipo.EN_GOTA_ROJA.getValue(),  pTMXTile.getTileColumn(), pTMXTile.getTileRow());
					enemigos.add(new EnemigoPosicion(EnemigoBase.Tipo.EN_GOTA_ROJA.getValue(), pTMXTile.getTileColumn(), pTMXTile.getTileRow()));
					estado.incrementaEnemigo();
				}
				
				
			}
		});
	}
	


	
	private boolean sonarArpa=false;
	public void todosEnemigosMuertos(){
		if (sonarArpa){
			context.getResouceManager().getCampanaFinal().play();
			sonarArpa=false;
		}
		
	}
	
	public TMXTiledMap cargaTMXmapa(String snivel){	
		
		enemigos= new ArrayList<EnemigoPosicion>();
		TMXTiledMap map=null;
		numParedes=0;
		getMatriz().reiniciaMatriz();
		getMatrizReferencia().reiniciaMatriz();

		try {
			//System.out.println("me pongo a cargarrr "+"tmx/"+snivel+".tmx");
			map = tmxLoader.loadFromAsset("tmx/"+snivel+".tmx");
			
			
			//System.out.println("cargado mapa ok");
			//Toast.makeText(this, "Cactus count in this TMXTiledMap: " + this.mCactusCount, Toast.LENGTH_LONG).show();
		} catch (final TMXLoadException tmxle) {
			//System.out.println("error de donde seeee");
			//tmxle.printStackTrace();
			//Debug.e(tmxle);
			System.out.println("ERORRR de XML");
			
		}catch (Exception e) {
			System.out.println("ERORRR de wwwwwXML");
		}
		//getMatriz().pintaMatriz();
		
		return map;
	}	

	
	public void cargarMonedero(DatosMapa mapa){		
		if(monedero==null){
			monedero = new AlmancenMonedas(context);		
		}
		monedero.reseteaMonedas();
		monedero.cargaMapa(mapa);
		
	}
		
	
	public ParserXML getParser() {
		iniParserXML();
		return parser;
	}

/*
	public void cargarAlmacenEnemigos(){	
		
		//if (almacenEnemigos==null) almacenEnemigos= new AlmacenEnemigos(context);	
		if(!enableEnemigos) return ;
		for (EnemigoPosicion enemigo : enemigos) {	
			System.out.println("creo enemigo "+enemigo.getNombre());
			EnemigoBase ene= context.getGameManager().getAlmacenEnemigos().createEnemigo(enemigo.getNombre(),0, enemigo.getColumna(), enemigo.getFila());			
		}	
	}	

	*/
	public void cargarBomberMan(){		
		if (bomberman==null)bomberman = new BomberMan(context);
		//System.out.println("deberia ponerse en 00");
		bomberman.getCuadrado().clearEntityModifiers();
		bomberman.setPosicionCuadros(0, 0);
	}
	
	public void iniParserXML(){
		if (parser==null)parser= new ParserXML(context);
	}

	public void cargaPolvorin(){
		
		if (polvorin==null ){
			polvorin = new AlmacenBombas(context, estado.getNumeroBombas());
		}else{
			polvorin.setMaxBombas(estado.getNumeroBombas());
		}
		//polvorin.setTamanoExplosion(estado.getTamExplosion());		
		//polvorin.setControlRemoto();
	}
	
	
		
	public void premataBomberman(){			
		System.out.println("premataBomberman");	
		setSalirInteligencia(true);
		context.getGameManager().getMiAlmacen().PararTodosEnemigos();// descomentar	
		context.getResouceManager().pararBoosterMuertoBomberman();
		ArrayList<Bomba> aL = getPolvorin().getAlmacen();
		for (Bomba bomba : aL) {
			bomba.preDesarmar();
		}
	}
	
	public void enemigoMuerto(){
		if (estado.mataEnemigo()){
			// aki reproducui algun sonido
		}
	}
	
	
	public void nivelSuperado(){
		Log.i("puerta", "nivel superado");
		// aki se pondrian los puntos y las estrellas
			
		DatabaseHandler baseDatos = context.getBasedatos();
		baseDatos.desbloqueaMapa(getMapaActual().getNumeroMapa()+1);
		getMapaActual().setEstrellas(ventanaNivelCompletado.getEstrellas(mapaActual.getBoostersIniciales()-mapaActual.getBoosterTotales(), mapaActual.getBoostersIniciales()));
		context.getBasedatos().actualizaMapa(getMapaActual());			
		context.getMenuMapas().actualizaMapas();		
		ventanaNivelCompletado.resultado(true, mapaActual.getBoostersIniciales()-mapaActual.getBoosterTotales(), mapaActual.getBoostersIniciales());
		
		
	}
	
	public void nivelNOSuperado(){
		ventanaNivelCompletado.resultado(false, mapaActual.getBoostersIniciales()-mapaActual.getBoosterTotales(), mapaActual.getBoostersIniciales());
	}
	
	
	
	public void muertoBomberman(){	
		
		//nivelSuperado();// suolo para probar
		//System.out.println("muertoBomberman2");
		Thread t = new Thread(){
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				ArrayList<Bomba> aL = getPolvorin().getAlmacen();
				for (Bomba bomba : aL) {
					try{bomba.desarmarBomba();}catch(Exception a){a.printStackTrace();}
				}
				
				//monedero.reseteaMonedas();
				getMiAlmacen().reciclarEnemigos();
				recargaEnemigos();
				
				
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				

				bomberman.reinicio();
				
				matriz.setMatriz(matrizReferencia.getMatrizmuros());						
				capaParedes.restauraParedes();				
				monedero.reseteaMonedas();
				bomberman.setnoMuerto();
				if (!isModoEntrenamiento() && estado.restaVida()){
					perdidoTodasLasvidas();
					return;
				}
				
				if (!isModoEntrenamiento()){
					String tiempo =getParser().gettiempoMapa(nivel);
					String [] tiempos =tiempo.split(":");
					iniciaCuentaAtras(Integer.parseInt(tiempos[0]),Integer.parseInt(tiempos[1]));	
					context.getHudDisplay().actualizarIconos();					
				}
				
				
				if (isModoEntrenamiento()){
					reiniciaEntrenamiento();
				}
				miAlmacen.iniciaEnemigos();// descomentar para iniciar				
			}
		};
		t.start();	
				
	}
	
	public void perdidoTodasLasvidas(){
		nivelNOSuperado();
		estado.setControlRemoto(BomberGame.INICIO_CONTROL_REMOTO);
		estado.setNumeroBombas(BomberGame.INICIO_NUM_BOMBAS);
		estado.setTamanoExplosion(BomberGame.INICIO_TAM_EXPLOSION);	
		estado.guardarPreferencias();
		
		
	}
	
	
	public CapaParedes getCapaParedes() {
		return capaParedes;
	}

	public AlmacenBombas getPolvorin() {
		return polvorin;
	}
	public BomberMan getBomberman() {
		return bomberman;
	}
	public TMXLayer getTmxSuelo() {
		return tmxSuelo;
	}
/*
	public TMXTiledMap getmTMXTiledMap() {
		return mTMXTiledMap;
	}*/
	public Matriz getMatriz(){
		return matriz;
	}
	public Matriz getMatrizReferencia(){
		return matrizReferencia;
	}
	public Rectangle getCurrentTileRectangle() {
		return currentTileRectangle;
	}


	public TMXTiledMap getmTMXTiledMap() {
		// TODO Auto-generated method stub
		return mTMXTiledMapActual;
	}
	
	
	
	public void MonedaExplotada(int columna, int fila ){
		
		//almacenEnemigos.ponerEnemigoPrecargado(Enemigo.EN_MONEDA, columna, fila);
		//almacenEnemigos.ponerEnemigoPrecargado(Enemigo.EN_MONEDA, columna, fila);
	}
	
	
	public class Matriz{
		
		public int matrizmuros[][]={			{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
												{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}	};
		
		
		public synchronized int[][] getMatrizmuros() {			
			return this.matrizmuros;
		}
		
		
		public synchronized void setMatriz(int [][] matriz){

			//this.matrizmuros=matriz.clone();
			
			for (int i = 0; i < matriz.length; i++) {
				for (int j = 0; j < matriz.length; j++) {
					this.matrizmuros[i][j]=matriz[i][j];
				}		
			}
			
		}
		
		public void reiniciaMatriz(){
			for (int i = 0; i < matrizmuros.length; i++) {
				for (int j = 0; j < matrizmuros.length; j++) {
					matrizmuros[i][j]=BomberGame.NADA;
				}				
			}
			
		}
		public int getNumFilas(){
			return this.matrizmuros.length;
		}
		public int getNumColumnas(){
			return this.matrizmuros[0].length;
		}
		public void pintaMatriz(){
			
			for (int i = 0; i < 13; i++) {
				String cadena="";
				for (int j = 0; j < matrizmuros.length; j++) {
					cadena+="["+matrizmuros[i][j]+"]";
				}	
				Log.d("puerta",cadena);
			}
			
		}
		public  void setValor(int valor, int fila, int columna){
			synchronized (matrizmuros) {
				this.matrizmuros[fila][columna]=valor;
			}
			
		}
		public  int getValor( int fila, int columna){			
			synchronized (matrizmuros) {
				int valor=BomberGame.PIEDRA;
				try{
					if (fila==-1 || columna==-1){
						valor=BomberGame.PIEDRA;
					}else{
						valor =this.matrizmuros[fila][columna];
					}
					
				}catch (Exception e) {
					////System.out.println("matrizmuros[fila][columna]"+fila+" "+columna);
					System.out.println("error  intentamos miarar fila"+fila+ " columna"+columna);
					e.printStackTrace();
					//System.out.println("mierrroorrr");
				}				
				return valor;
			}

		}
	
	}
	
	boolean play=true;
	
	public boolean isPlay() {
		return play;
	}

	boolean pause=true;
	
	public boolean isPause() {
		return pause;
	}

	public void pause() {
		pause=true;
		play=false;
		pararCuentaAtras();
		miAlmacen.pausarEnemigos();
		ArrayList<Bomba> bombas = polvorin.getAlmacen();
		for (Bomba bomba : bombas) {
			bomba.getBomba().setIgnoreUpdate(true);
			//context.getResouceManager().getMecha().pause();
			bomba.getBatch().setIgnoreUpdate(true);
		}

	}

	public void play() {
		pause=false;
		play=true;
		resumeCuentaAtras();
		ArrayList<Bomba> bombas = polvorin.getAlmacen();
		for (Bomba bomba : bombas) {
			bomba.getBomba().setIgnoreUpdate(false);
			//context.getResouceManager().getMecha().resume();
			
			bomba.getBatch().setIgnoreUpdate(false);
		}
		miAlmacen.playEnemigos();

	}
	
	
	public void playEntrenamento() {
		pause=false;
		play=true;
		
		ArrayList<Bomba> bombas = polvorin.getAlmacen();
		for (Bomba bomba : bombas) {
			bomba.getBomba().setIgnoreUpdate(false);
			//context.getResouceManager().getMecha().resume();
			
			bomba.getBatch().setIgnoreUpdate(false);
		}
		miAlmacen.playEnemigos();

	}
	

	private int minutos;
	private int segundos;
	public boolean cuenta = true;
	private Thread hilo;

	public int getMinutos() {
		return minutos;
	}

	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	public int getSegundos() {
		return segundos;
	}

	public void setSegundos(int segundos) {
		this.segundos = segundos;
	}

	public void finCuentaAtras() {
		try{
			bomberman.finBooster();
			bomberman.morir(false);			
		}catch(Exception e){
			
		}
	}

	public void pararCuentaAtras() {
		cuenta = false;
	}

	public void resumeCuentaAtras() {
		cuenta = true;
	}
		



	public DatosMapa getMapaActual() {
		return mapaActual;
	}

	public void setMapaActual(DatosMapa mapaActual) {
		this.mapaActual = mapaActual;
	}
	
	
	public void adquiridaM_bomba(){			
		estado.incrementaNumeroBombas();
		if (polvorin.getMaximoBombas()<BomberGame.MAXIMO_BOMBAS){
			polvorin.setMaxBombas(polvorin.getMaximoBombas()+1);
		}
		display.actualizarIconos();
	}
	
	public void adquiridaM_corazon(){
		estado.addVida();
		display.actualizarIconos();
	}
	
	public void adquiridaM_correr(){
		estado.setMVELOCIDAD(true);
	}
	
	public void adquiridaM_detonador(){
		estado.setControlRemoto(true);		
		display.actualizarIconos();
	}
	
	public void adquiridaM_fantasma(){		
		estado.setMFANTASMA(true);
		getBomberman().boosterFantasma(true);
	}
	
	public void adquiridaM_fuerza(){
			estado.setMFUERZA(true);
	}
	
	public void adquiridaM_potencia(){
		estado.incrementaTamanoExplosion();
		display.actualizarIconos();
	}
	
	

	
	public void plantadaM_bomba(){	
		mapaActual.setM_bomba(mapaActual.getM_bomba()-1);
	}
	public void plantadaM_corazon(){
		mapaActual.setM_corazon(mapaActual.getM_corazon()-1);
	}
	public void plantadaM_correr(){
		mapaActual.setM_correr(mapaActual.getM_correr()-1);
	}
	public void plantadaM_detonador(){
		mapaActual.setM_detonador(mapaActual.getM_detonador()-1);
	}
	public void plantadaM_fantasma(){
		mapaActual.setM_fantasma(mapaActual.getM_fantasma()-1);
	}
	public void plantadaM_fuerza(){
		mapaActual.setM_fuerza(mapaActual.getM_fuerza()-1);
	}
	public void plantadaM_potencia(){
		mapaActual.setM_potenciador(mapaActual.getM_potenciador()-1);
	}
	
	
	private int segundosPasados=0;
	
	public int getSegundosPasados() {
		return segundosPasados;
	}

	public void iniciaCuentaAtras(int minutes, int seconds) {
		//setVidas(1000);
		this.minutos=minutes;
		this.segundos=seconds;
		segundosPasados=0;
		cuenta=true;
		display.setTime(minutos, segundos);
		display.setLifes(estado.getVidas());
		if (hilo == null) {
			hilo = new Thread() {
				@Override
				public void run() {
					while (true) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (cuenta){

							if (segundos == 0 && minutos != 0) {
								segundos = 59;
								minutos--;								
							} else if (segundos == 0 && minutos == 0) {
								finCuentaAtras();
							} else {
								segundos--;
								segundosPasados++;								
							}	
							display.setSegundosPasados(segundosPasados);
							display.setTime(minutos, segundos);
							display.setLifes(estado.getVidas());
						}

					}
					
				}
			};
			hilo.start();
		}

	}

	




	
	boolean salirInteligencia=true;
	boolean modoEntrenamiento=false;
	
	public boolean isModoEntrenamiento() {
		return modoEntrenamiento;
	}

	public void setModoEntrenamiento(boolean modoEntrenamiento) {
		this.modoEntrenamiento = modoEntrenamiento;
	}

	public boolean isSalirInteligencia() {
		return salirInteligencia;
	}

	public void setSalirInteligencia(boolean salirInteligencia) {
		this.salirInteligencia = salirInteligencia;
		if (salirInteligencia &&hiloCreaMuros!=null){
			hiloCreaMuros.terminaEjecucion();			
			hiloCreaEnemigos.terminaEjecucion();
		}
	}

	
	
	public void reiniciaEntrenamiento(){
		capaParedes.creaParedesCampoEntrenamiento();
		creaEnemigosInicialesEntrenamiento();
		inteligenciaEntrenamiento();
		mapaActual.setM_bomba(0);
		context.getResouceManager().getPasosB().pause();
	}
	
	
	
	
	
	

	
	
	private int enemigosIniciales=0;	
	private int periodoCreacionEnemigos=0;
	private int maximoEnemigos=0;
	private int maximoMuros=0;
	
	private int porcentajeAparicionEnemigoGlobo=0;
	private int porcentajeAparicionEnemigoGota=0;
	private int porcentajeAparicionEnemigoGotaNaranja=0;
	private int porcentajeAparicionEnemigoMoco=0;
	private int porcentajeAparicionEnemigoMoneda=0;
	private int porcentajeAparicionEnemigoFantasma=0;
	
	
	
	int UltimoNivelPasado;
	
	
	ArrayList<Tipo> ememigosEntrenamiento;
	public void creaDatosEntrenamiento() {
		ememigosEntrenamiento= new ArrayList<EnemigoBase.Tipo>();
		display.setTime(0, 0);
		UltimoNivelPasado = Preferencias.leerPreferenciasInt("UltimoNivelPasado");	
		nivel=UltimoNivelPasado;
		estado.setNivel(nivel);
		// poner los maximos		
		
		DatosMapa datosMapa = getParser().getMaximoMonedasEnNivel(UltimoNivelPasado);		

		//MaximoBoosterMDETONADOR=datosMapa.getM_detonador();
		//MaximoBoosterMBOMBA=datosMapa.getM_bomba();
		//MaximonBoosterMEXPLOSION=datosMapa.getM_potenciador();		
		
		maximoMuros = 40;
		//maxiomoMonedas = 8;
		
		if (isBetween(UltimoNivelPasado, 1, 5)) {
			
			enemigosIniciales=1;		
			maximoEnemigos = 4;
			periodoCreacionEnemigos = 80;			
				

			porcentajeAparicionEnemigoGlobo = 1;
			porcentajeAparicionEnemigoGota = 0;
			porcentajeAparicionEnemigoGotaNaranja = 0;
			porcentajeAparicionEnemigoMoco = 0;
			porcentajeAparicionEnemigoMoneda = 0;
			porcentajeAparicionEnemigoFantasma = 0;
	

		}else if (isBetween(UltimoNivelPasado, 6, 10)) {
			
			enemigosIniciales=2;		
			maximoEnemigos = 10;	
			
			periodoCreacionEnemigos = 80;						

			porcentajeAparicionEnemigoGlobo = 10;
			porcentajeAparicionEnemigoGota = 0;
			porcentajeAparicionEnemigoGotaNaranja = 0;
			porcentajeAparicionEnemigoMoco = 1;
			porcentajeAparicionEnemigoMoneda = 0;
			porcentajeAparicionEnemigoFantasma = 3;			


		}else if (isBetween(UltimoNivelPasado, 11, 15)) {
			
			enemigosIniciales=4;		
			maximoEnemigos = 13;	
			
			periodoCreacionEnemigos = 60;						

			porcentajeAparicionEnemigoGlobo = 8;
			porcentajeAparicionEnemigoGota = 0;
			porcentajeAparicionEnemigoGotaNaranja = 0;
			porcentajeAparicionEnemigoMoco = 3;
			porcentajeAparicionEnemigoMoneda = 2;
			porcentajeAparicionEnemigoFantasma = 3;			
			
		}else if (isBetween(UltimoNivelPasado, 16, 20)) {
			enemigosIniciales=4;		
			maximoEnemigos = 14;	
			periodoCreacionEnemigos = 50;							

			porcentajeAparicionEnemigoGlobo = 8;
			porcentajeAparicionEnemigoGota = 2;
			porcentajeAparicionEnemigoGotaNaranja = 0;
			porcentajeAparicionEnemigoMoco = 3;
			porcentajeAparicionEnemigoMoneda = 2;
			porcentajeAparicionEnemigoFantasma = 3;			
			
		}else if (isBetween(UltimoNivelPasado, 21, 30)) {
			
			enemigosIniciales=5;		
			maximoEnemigos = 16;	
			
			periodoCreacionEnemigos = 50;							

			porcentajeAparicionEnemigoGlobo = 4;
			porcentajeAparicionEnemigoGota = 2;
			porcentajeAparicionEnemigoGotaNaranja = 0;
			porcentajeAparicionEnemigoMoco = 3;
			porcentajeAparicionEnemigoMoneda = 2;
			porcentajeAparicionEnemigoFantasma = 3;		
			
		}else if (isBetween(UltimoNivelPasado, 31,40)){			
			
			enemigosIniciales=6;		
			maximoEnemigos = 27;	
			
			periodoCreacionEnemigos = 40;							

			porcentajeAparicionEnemigoGlobo = 5;
			porcentajeAparicionEnemigoGota = 4;
			porcentajeAparicionEnemigoGotaNaranja = 0;
			porcentajeAparicionEnemigoMoco = 5;
			porcentajeAparicionEnemigoMoneda = 5;
			porcentajeAparicionEnemigoFantasma = 5;		
			
			
			
		}else if (isBetween(UltimoNivelPasado, 41,100)){
			
			enemigosIniciales=6;		
			maximoEnemigos = 18;	
			
			periodoCreacionEnemigos = 40;							

			porcentajeAparicionEnemigoGlobo = 5;
			porcentajeAparicionEnemigoGota = 4;
			porcentajeAparicionEnemigoGotaNaranja = 3;
			porcentajeAparicionEnemigoMoco = 5;
			porcentajeAparicionEnemigoMoneda = 5;
			porcentajeAparicionEnemigoFantasma = 5;		
		}else{
			
			enemigosIniciales=0;		
			maximoEnemigos = 0;
			periodoCreacionEnemigos = 10;		

			porcentajeAparicionEnemigoGlobo = 0;
			porcentajeAparicionEnemigoGota = 0;
			porcentajeAparicionEnemigoGotaNaranja = 0;
			porcentajeAparicionEnemigoMoco = 0;
			porcentajeAparicionEnemigoMoneda = 0;
			porcentajeAparicionEnemigoFantasma = 0;
		}

		
		for (int i=0; i<porcentajeAparicionEnemigoGlobo; i++){
			ememigosEntrenamiento.add(Tipo.EN_GLOBO);
		}		
		for (int i=0; i<porcentajeAparicionEnemigoGota; i++){
			ememigosEntrenamiento.add(Tipo.EN_GOTA_AZUL);
		}
		for (int i=0; i<porcentajeAparicionEnemigoGotaNaranja; i++){
			ememigosEntrenamiento.add(Tipo.EN_GOTA_NARANJA);
		}		
		for (int i=0; i<porcentajeAparicionEnemigoMoco; i++){
			ememigosEntrenamiento.add(Tipo.EN_MOCO);
		}
		for (int i=0; i<porcentajeAparicionEnemigoMoneda; i++){
			ememigosEntrenamiento.add(Tipo.EN_MONEDA);
		}
		for (int i=0; i<porcentajeAparicionEnemigoFantasma; i++){
			ememigosEntrenamiento.add(Tipo.EN_FANTASMA);
		}		
	}
	
	public EnemigoBase creaEnemigoEntrenamiento(int columna, int fila){
		int numero = Aleatorio.DameAleatorio(0, ememigosEntrenamiento.size()-1);
		System.out.println("numero"+numero);
		System.out.println("ememigosEntrenamiento.size()"+ememigosEntrenamiento.size());
		Tipo tipo = ememigosEntrenamiento.get(numero);		
		EnemigoBase enemigo = miAlmacen.createEnemigo(tipo.toString(), columna, fila);
		estado.incrementaEnemigo();
		//enemigo.iniciaInteligenciaIA();
		return enemigo;
	}
	
	public int getPeriodoMuros(){
		int numparedes =getCapaParedes().getNumparedes();
		int periodo=1;
		if (numparedes<=10){
			periodo=1;
		}else if (numparedes>10 && numparedes<=20){
			periodo=3;
		}else if (numparedes>20 && numparedes<=30){
			periodo=4;
		}else if (numparedes>30 && numparedes<=40){
			periodo=5;
		}else if (numparedes>40){
			periodo=6;
		}
		return periodo;
	}
	
	public int getPeriodoEnemigos(){
		int numEnemigos =estado.getEnemigosRestantes();
		int periodo=1;
		if (numEnemigos<=2){
			return (int) (periodoCreacionEnemigos*0.1f);
		}else if (numEnemigos>2 && numEnemigos<=5){
			return (int) (periodoCreacionEnemigos*0.2f);
		}else if (numEnemigos>5 && numEnemigos<=7){
			return (int) (periodoCreacionEnemigos*0.3f);
		}else if (numEnemigos>7 && numEnemigos<=10){
			return (int) (periodoCreacionEnemigos*0.3f);
		}else if (numEnemigos>10){
			return (int) (periodoCreacionEnemigos*0.3f);
		}
		return periodo;
	}
	
	
	
	public void creaEnemigosInicialesEntrenamiento() {
		for (int i = 0; i < enemigosIniciales; i++) {
			Date date = new Date(System.currentTimeMillis());
			Log.d("enemigo", "creadoInicial"+ date.getMinutes()+":"+date.getSeconds());
			boolean creado=false;
			while(!creado){
				int columna = Aleatorio.DameAleatorio(6, 22);
				int fila = Aleatorio.DameAleatorio(6, 12);
				if (context.getGameManager().getMatriz().getValor(fila, columna) == BomberGame.NADA) {
					creaEnemigoEntrenamiento(columna, fila);
					creado=true;
				}
			}		
		}
	}
	
	
	public void cargaCampoEntrenamiento(){	
		context.getHudDisplay().setTextoMonedas(" ");
		if (primeraVez )context.getHudDisplay().setmiVisible(false);
		context.getHudDisplay().setVisibleMonedas(false);
		//context.getHudDisplay().setVisibleMonedas(false);
		Log.d("entrenamiento", "cargaCampoEntrenamiento");
		creaDatosEntrenamiento();
		modoEntrenamiento=true;
		sonarArpa=false;
		// aki habria que cargar el nivel adecuado de enemigos y boosters		
		
		estado.setEnemigosRestantes(0);			
		setMapaActual(context.getBasedatos().getMapa(0));
			
		
		Log.d("entrenamiento", " getBoosterTotales()"+mapaActual.getBoosterTotales());
		System.out.println("Boosers "+mapaActual.getBoostersIniciales()+" numero  "+mapaActual.getNumeroMapa() +" getId "+mapaActual.getId());
		this.mTMXTiledMapActual=cargaTMXmapa("1mapa0");
		
		capaParedes.creaParedesCampoEntrenamiento();		
		
		
		//capaParedes.crearUnMuro(4, 4);	
		
		cargaPolvorin();
		cargarBomberMan();
		
		cargarMonedero(mapaActual);	
		context.getHudDisplay().actualizarIconos();
		
		creaEnemigosInicialesEntrenamiento();

		//miAlmacen.createEnemigo("EN_MOCO", 1, 2, 8);
		//miAlmacen.createEnemigo("EN_MONEDA", 1, 2, 9);
		//miAlmacen.createEnemigo("EN_FANTASMA", 1, 12, 4);
		//miAlmacen.createEnemigo("EN_FANTASMA", 1, 8, 9);

		miAlmacen.iniciaEnemigos();
		
		playEntrenamento();
		//iniciaCuentaAtras(2, 30);	
		
		retrasaPlay();
		
		context.getEngine().setScene(context.getEscenaJuego());
		MiEngine en= (MiEngine) context.getEngine();
		en.selCamera(1);
		inteligenciaEntrenamiento();
		context.getEscenaJuego().sortChildren();
		context.getResouceManager().playMusicaRandom();
		
	}
	
	
	
	public static boolean isBetween(int x, int lower, int upper) {
		  return lower <= x && x <= upper;
		}
	
	
	HiloTrabajador hiloCreaMuros;
	HiloTrabajador hiloCreaEnemigos;

	
	public void inteligenciaEntrenamiento(){	
		Log.d("entrenamiento", "inteligenciaEntrenamiento");
		salirInteligencia=false;
		context.getHudDisplay().actualizarIconos();
		
		hiloCreaMuros = new HiloTrabajador(){
			@Override
			public void inteligencia() {				
				try{				
					int columna=Aleatorio.DameAleatorio(2,22);
					int fila=Aleatorio.DameAleatorio(2,12);			
					if (terminar)return;
					if ( capaParedes.getNumparedes()<maximoMuros &&  lejosDeBomberMan(columna, fila) && play && context.getGameManager().getCapaParedes().crearUnMuro(columna, fila)){	
						Log.d("entrenamiento", "ID="+id+" CRAMUROS numeroMuros"  +capaParedes.getNumparedes() +" Periodo"+getPeriodoMuros()+"maximpo "+maximoMuros);
						if (getPeriodoMuros()!=0){
							Thread.sleep(getPeriodoMuros()*1000);
						}else{
							Thread.sleep(1000);
						}
					}
				}catch(Exception e){}				
			}
		};
		hiloCreaMuros.start();	
		
		
		hiloCreaEnemigos = new HiloTrabajador(){
			int id;
			@Override
			public void run() {
				id=Aleatorio.DameAleatorio(1, 1000);
				try {
					Thread.sleep(getPeriodoEnemigos()*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.run();
			}
			@Override
			public void inteligencia() {
				try{						
					int columna=Aleatorio.DameAleatorio(2,22);
					int fila=Aleatorio.DameAleatorio(2,12);
					
					if (estado.getEnemigosRestantes()<maximoEnemigos && lejosDeBomberMan(columna, fila) &&  context.getGameManager().getMatriz().getValor(fila, columna)==BomberGame.NADA){		
						if (play && !terminar){
							Date date = new Date(System.currentTimeMillis());
							Log.d("enemigo", id+" creadoAdicional "+ date.getMinutes()+":"+date.getSeconds());
							
							//Log.d("hora", date.getMinutes()+":"+date.getSeconds());
							EnemigoBase enemigo = creaEnemigoEntrenamiento(columna,fila);
							enemigo.iniciaInteligenciaIA();
							Thread.sleep(getPeriodoEnemigos()*1000);
						}						
					}
					if (estado.getEnemigosRestantes()>=maximoEnemigos){
						Thread.sleep(1000);
					}
				
				}catch(Exception e){}
			}
		};
		hiloCreaEnemigos.start();

				
	}
	
		
		
	
	
	
	
	public boolean lejosDeBomberMan(int columna, int fila){
		
		int filaBomber =getBomberman().getFila();
		int columnaBomber =getBomberman().getColumna();
		int rango=3;		
		if ((columna>=columnaBomber-rango) && (columna<=columnaBomber+rango) && (fila>=filaBomber-rango) && (fila<=filaBomber+rango)){			
			return false;
		}else{
			return true;
		}
	}
	
	
	public  class HiloTrabajador extends Thread{
		
		
		protected boolean terminar=false;
		protected int id = Aleatorio.DameAleatorio(1, 1000);
		public void terminaEjecucion(){
			terminar=true;
		}
		@Override
		public void run() {
			while (!terminar) {				
				inteligencia();				
			}
		}
		
		public  void inteligencia(){
			
		}
	}
	

}
