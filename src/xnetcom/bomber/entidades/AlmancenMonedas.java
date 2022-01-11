package xnetcom.bomber.entidades;

import java.util.ArrayList;
import java.util.Iterator;

//import org.andengine.entity.layer.tiled.tmx.TMXLayer;
//import org.andengine.entity.layer.tiled.tmx.TMXTile;
import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;

import xnetcom.bomber.BomberEstado;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.GameManager;
import xnetcom.bomber.andengine.Pruebas;
import xnetcom.bomber.entidades.EnemigoBase.Tipo;
import xnetcom.bomber.entidades.Moneda.TipoMoneda;
import xnetcom.bomber.sql.DatosMapa;
import xnetcom.bomber.util.Aleatorio;
import android.util.Log;

/*
 * habra un maximo de dos monedas de cada tipo
 */

public class AlmancenMonedas {
	private BomberGame context;

	public BomberGame getContext() {
		return context;
	}

	private ArrayList<Moneda> almacenSprites;
	private ArrayList<TipoMoneda> almacen = new ArrayList<Moneda.TipoMoneda>();
	
	private ArrayList<TipoMoneda> monedasAire = new ArrayList<Moneda.TipoMoneda>();

	public ArrayList<Moneda> getAlmacen() {
		return almacenSprites;
	}

	private Sprite puerta;

	public Sprite getPuerta() {
		return puerta;
	}

	// private Iterator<Moneda> itr;
	private int numeroParedes = 0;
	//private int paredesRestantes = 0;
	// private int mCorazones=0;
	// private int mDetonadores=0;
	// private int mBombas=0;
	// //private int mVelocidad=0;
	// private int mFantasma=0;
	// private int mFuerza=0;
	// private int mExplosion=0;
	// private int boosterTotales=0;
	// private int boosterSacados=0;
	private boolean enable = true;
	private int mapa;
	private DatosMapa datosMapa;
	private GameManager gm; 

	public AlmancenMonedas(BomberGame context) {
		this.context = context;
		gm= context.getGameManager();
		almacenSprites = new ArrayList<Moneda>();
		creaMonedas();
	}

	public void setEnable() {
		enable = true;
	}

	public void setDisable() {
		enable = false;
	}

	public boolean isEnable() {
		return enable;
	}

	private void creaMonedas() {
		
		for (int i = 0; i < 7; i++) {
			TipoMoneda tipo = TipoMoneda.MBOMBA;
			if (i==0) tipo = TipoMoneda.MBOMBA;
			else if (i==1) tipo = TipoMoneda.MCORAZON;
			else if (i==2) tipo = TipoMoneda.MDETONADOR;
			else if (i==3) tipo = TipoMoneda.MEXPLOSION;
			else if (i==4) tipo = TipoMoneda.MFANTASMA;
			else if (i==5) tipo = TipoMoneda.MFUERZA;
			else if (i==6) tipo = TipoMoneda.MVELOCIDAD;
			
			for (int j = 0; j < 2; j++) {
				Moneda moneda = new Moneda(this, tipo);
				almacenSprites.add(moneda);
			}
		}
		
		
		
		if (puerta == null) {
			puerta = new Sprite(0, 0, context.getResouceManager().getPuerta_TR(),context.getVertexBufferObjectManager());
			puerta.setZIndex(BomberGame.ZINDEX_PAREDES + 1);
			puerta.setScale(0.5f);
			puerta.setScaleCenter(0, 0);
			puerta.setVisible(false);
			context.getEscenaJuego().attachChild(puerta);
		}
	}

	
	
	
	public int dameNumeroRestantedeMoneda(TipoMoneda tipo){
		int numero=0;
		for (TipoMoneda  moneda : almacen) {
			if (moneda.equals(tipo)){
				numero++;
			}			
		}
		return numero;		
	}
	
	
	/***
	 * Se llama a esta funcion cuando se tiene que reinicias la pamntalla y hay
	 * monedas visibles sin coger esta funcion las hace desaparecer y las
	 * reinicia
	 */

	public void cargaMapa(DatosMapa mapa) {
		monedasAire = new ArrayList<Moneda.TipoMoneda>();
		datosMapa = mapa;
		almacen.clear();
		this.mapa = datosMapa.getNumeroMapa();

		for (int i = 0; i < datosMapa.getM_bomba(); i++) {
			almacen.add(TipoMoneda.MBOMBA);
		}

		for (int i = 0; i < datosMapa.getM_corazon(); i++) {
			almacen.add(TipoMoneda.MCORAZON);
		}

		for (int i = 0; i < datosMapa.getM_correr(); i++) {
			almacen.add(TipoMoneda.MVELOCIDAD);
		}

		for (int i = 0; i < datosMapa.getM_detonador(); i++) {
			almacen.add(TipoMoneda.MDETONADOR);
		}
		
		for (int i = 0; i < datosMapa.getM_fantasma(); i++) {
			almacen.add(TipoMoneda.MFANTASMA);
		}

		for (int i = 0; i < datosMapa.getM_fuerza(); i++) {
			almacen.add(TipoMoneda.MFUERZA);
		}
		
		for (int i = 0; i < datosMapa.getM_potenciador(); i++) {
			almacen.add(TipoMoneda.MEXPLOSION);
		}
		for (int i = 0; i < datosMapa.getM_sorpresa(); i++) {
			almacen.add(TipoMoneda.MSORPRESA);
		}
		

		//numeroParedes = context.getGameManager().getNumparedes();
		//paredesRestantes = numeroParedes;
	}

	

	
	
	
	
	public TipoMoneda getTipoMonedaenXY(int columna, int fila){
		for (Iterator<Moneda> iterator = almacenSprites.iterator(); iterator.hasNext();) {
			Moneda moneda =  iterator.next();			
			if(moneda.isVisible()){
				if (moneda.getColumna()==columna && moneda.getFila()== fila){
					return moneda.getTipoMoneda();
				}
			}
		}
		return TipoMoneda.MNULA;
	}
	
		
	public int getBoosterRestantes() {
		synchronized (datosMapa) {
			int suma = datosMapa.getM_bomba();
			suma += datosMapa.getM_corazon();
			suma += datosMapa.getM_correr();
			suma += datosMapa.getM_detonador();
			suma += datosMapa.getM_fantasma();
			suma += datosMapa.getM_fuerza();
			suma += datosMapa.getM_potenciador();
			suma += datosMapa.getM_sorpresa();
			if (suma<0)return 0;
			return suma;
		}
	}


	
	public void reseteaMonedas() {
		// cargaMapa(mapa);
		puerta.setVisible(false);
		Iterator<Moneda> itr = almacenSprites.iterator();
		while (itr.hasNext()) {
			Moneda moneda = (Moneda) itr.next();
			moneda.dettach();
		}
	}

	/**
	 * se le llama cuando se explote una pared y el puede poner una moneda
	 * 
	 * @param X
	 * @param Y
	 * @return Moneda si se tiene que poner una moneda en esa XY
	 */

	public void recogidoMoneda(int columna, int fila) {		
		for (Moneda moneda : almacenSprites) {
			if (moneda.getFila() == fila && moneda.getColumna() == columna) {
				moneda.recogerMoneda();
				//if (!context.getGameManager().isModoEntrenamiento())datosMapa.actualiza();
				synchronized (monedasAire) {
					for(TipoMoneda moned: monedasAire){
						if (moned.equals(moneda.getTipoMoneda())){
							monedasAire.remove(moned);
							return;
						}						
					}
				}

			}
		}
	}

	
	
	public Object mutex = new Object();
	
	
	public void CuadradoExplotado(final int  columna, final int fila) {
		if (!GameManager.enableMonedas)	return;
		
		new Thread() {
			public void run() {
				synchronized (mutex) {
					
					if (context.getGameManager().isModoEntrenamiento()){						
						ponerMonedaEntrenamiento(columna, fila);
						return;
					}				
					
					//paredesRestantes--;
					context.getGameManager().getCapaParedes().setNumparedes(context.getGameManager().getCapaParedes().getNumparedes()-1);	
					boolean ponerPuerta = Aleatorio.TiraDadoposibilidades(context.getGameManager().getCapaParedes().getNumparedes(), 1);

					if((!puerta.isVisible()) && context.getGameManager().getCapaParedes().getNumparedes()<=getBoosterRestantes()){
						ponerPuerta=true;
					}
					
					//ponerPuerta=true; 
					if (ponerPuerta && !puerta.isVisible()) {
						// puerta = new Sprite(0, 0,
						// context.getResouceManager().getPuerta_TR());
						context.getGameManager().getMatriz().setValor(BomberGame.PUERTA, fila, columna);
						ponerPuerta(columna, fila);
						return ;
					} else {
						boolean ponerMoneda = Aleatorio.TiraDadoposibilidades(context.getGameManager().getCapaParedes().getNumparedes(), getBoosterRestantes());	
						//ponerMoneda=true;
						if (Pruebas.siempreMoneda)ponerMoneda=true;
						if (ponerMoneda) {		
							Log.i("moneda","/////////////////////////////////////////////");
							
							Log.i("moneda","datosMapa.getM_bomba()"+ datosMapa.getM_bomba());
							Log.i("moneda","datosMapa.getM_corazon()"+ datosMapa.getM_corazon());
							Log.i("moneda","datosMapa.getM_correr()"+ datosMapa.getM_correr());
							Log.i("moneda","datosMapa.getM_detonador()"+ datosMapa.getM_detonador());
							Log.i("moneda","datosMapa.getM_fantasma()"+ datosMapa.getM_fantasma());
							Log.i("moneda","datosMapa.getM_fuerza()"+ datosMapa.getM_fuerza());
							Log.i("moneda","datosMapa.getM_potenciador()"+ datosMapa.getM_potenciador());
							Log.i("moneda","datosMapa.getM_sorpresa()"+ datosMapa.getM_sorpresa());
							
							Log.d("moneda", "context.getGameManager().getCapaParedes().getNumparedes() "+context.getGameManager().getCapaParedes().getNumparedes());
							Log.d("moneda", "getBoosterRestantes() "+getBoosterRestantes());
							Log.d("moneda", "ponerMoneda() "+ponerMoneda);
							 ponerMoneda(fila, columna);
							 return;
						} else {
							//Log.i("puerta", "no pongo monda");
							//context.getGameManager().getMatriz().pintaMatriz();
							return;
						}
					}
				}
			};
		}.start();
		
	
		
		
	}
	
	
	
	
	
	
	
	
	


	int malaSuerte=0;
	
	
	public void ponerMonedaEntrenamiento(int  columna,  int fila){
		
		int numeroPremiado = Aleatorio.DameAleatorio(1, 150);
		Log.d("premio", ""+numeroPremiado);
		if (!(numeroPremiado<=30 && numeroPremiado>=20)&& malaSuerte<15){
			malaSuerte++;
			return;// para que salga 14 de cada 15 veces
		}
		
		malaSuerte=0;
		
		TipoMoneda tipoMoneda =dameMonedasEntrenamiento();
		synchronized (monedasAire) {
			monedasAire.add(tipoMoneda);
		}
		
		Moneda moneda = getMoneda(tipoMoneda);
		moneda.ponerMoneda(columna, fila);
		context.getGameManager().getMatriz().setValor(BomberGame.MONEDA, fila, columna);
		//context.getHudDisplay().actualizarIconos();
	}
	
	
	
	public TipoMoneda dameMonedasEntrenamiento(){		
		TipoMoneda moneda = dameMonedasAleatoriasEntrenamiento();
		BomberEstado estado = context.getGameManager().getEstado();
		//moneda=TipoMoneda.MSORPRESA;
		if (moneda!=TipoMoneda.MSORPRESA){
			return moneda;
		}
		//context.tostar("suerteee");
		
		int bombasActuales =  estado.getNumeroBombas();
		int explosionActual =estado.getTamExplosion();
		boolean detonadorActual = estado.isControlRemoto();
		int nivel =estado.getNivel();
		
		int maxBombas =estado.numeroMaximoDeBombasNivel();
		int maxExplosion = estado.numeroMaximoExplosionNivel();
		boolean posibleDetonador =estado.isDetonadorDisponibleNivel();
		
		int monedaBombaAire=0;
		int monedaExplosionAire=0;
		int monedaDetonadorAire=0;
		
		
		for(TipoMoneda monedaAire :monedasAire){
			switch (monedaAire) {
			case MBOMBA:
				monedaBombaAire++;
				break;
			case MEXPLOSION:
				monedaExplosionAire++;
				break;
			case MDETONADOR:
				monedaDetonadorAire++;
				break;
			default:
				break;
			}
		}
		
		
		
		ArrayList<TipoMoneda> miniAlmacen = new ArrayList<Moneda.TipoMoneda>();
		
		
		if (bombasActuales<(maxBombas+monedaBombaAire)){
			miniAlmacen.add(TipoMoneda.MBOMBA);
			miniAlmacen.add(TipoMoneda.MBOMBA);
			miniAlmacen.add(TipoMoneda.MBOMBA);
			miniAlmacen.add(TipoMoneda.MBOMBA);
			miniAlmacen.add(TipoMoneda.MBOMBA);
			miniAlmacen.add(TipoMoneda.MBOMBA);
		}else if (explosionActual<(maxExplosion+monedaExplosionAire)){
			miniAlmacen.add(TipoMoneda.MEXPLOSION);
			miniAlmacen.add(TipoMoneda.MEXPLOSION);
			miniAlmacen.add(TipoMoneda.MEXPLOSION);
			miniAlmacen.add(TipoMoneda.MEXPLOSION);
		}else if(!detonadorActual && posibleDetonador && monedaDetonadorAire!=0){
			miniAlmacen.add(TipoMoneda.MDETONADOR);
			miniAlmacen.add(TipoMoneda.MDETONADOR);
		}			
		
		if (miniAlmacen.size()!=0){
			int aleatorio =Aleatorio.DameAleatorio(0, miniAlmacen.size()-1);
			moneda = miniAlmacen.get(aleatorio);
			return moneda;
		}
		
			
		
		if (Aleatorio.DameAleatorio(1, 10)<5){
			return TipoMoneda.MCORAZON;
		}else{
			return TipoMoneda.MFUERZA;
		}
	
	}	
	
	
	
	public TipoMoneda dameMonedasAleatoriasEntrenamiento(){

		int aleatorio = Aleatorio.DameAleatorio(1, 75);
		
		if (GameManager.isBetween(aleatorio, 1, 10)) {			
			return TipoMoneda.MCORAZON;
		} else if (GameManager.isBetween(aleatorio, 11, 20)) {
			return TipoMoneda.MFANTASMA;
		}else if (GameManager.isBetween(aleatorio, 21, 30)){
			return TipoMoneda.MFUERZA;
		}else if (GameManager.isBetween(aleatorio, 31, 40)){
			return TipoMoneda.MVELOCIDAD;
		}else if (GameManager.isBetween(aleatorio, 41, 80)){
			return TipoMoneda.MSORPRESA;
		}else{
			return TipoMoneda.MCORAZON;
		}	
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Moneda ponerMoneda(int fila, int columna){
		Moneda msalida=null;
		try {
			context.getGameManager().getMatriz().setValor(BomberGame.MONEDA, fila, columna);
			TipoMoneda moneda = eligeMoneda();
			
			 msalida = getMoneda(moneda);
			synchronized (monedasAire) {
				monedasAire.add(msalida.getTipoMoneda());
			}	
			msalida.ponerMoneda(columna, fila);
			//Log.i("puerta", "moneda puesta");
			//context.getGameManager().getMatriz().pintaMatriz();
			if (!context.getGameManager().isModoEntrenamiento())datosMapa.actualiza();
			context.getHudDisplay().actualizarIconos();
		}catch (Exception e){}	
		return msalida;
	}

	private int puertaX = -1;
	private int puertaY = -1;

	public void ponerPuerta(int columna, int fila) {
		System.out.println("ponemor puerta x" + puertaX + " Y" + puertaY);
		puertaX = columna;
		puertaY = fila;
		TMXLayer tmxLayer = context.getGameManager().getTmxSuelo();
		TMXTile tile = tmxLayer.getTMXTile(columna, fila);
//		puerta.setPosition(tile.getTileX(), tile.getTileY()-6); /**GLES1***/
		puerta.setPosition(tile.getTileColumn()*tile.getTileWidth(), (tile.getTileRow()*tile.getTileHeight())-6); /**GLES2***/
		puerta.setVisible(true);
	}

	public void quitarPuerta() {
		puerta.setVisible(false);
		puertaX = -1;
		puertaY = -1;
	}

	
	
	
	public void explotado(int columna, int fila, final int numeroBomba){	
		System.out.println("explotado fila "+fila+"columna"+columna);
		
		
		int numEnemigos=0;
		if (gm.getNivel()<15){
			numEnemigos=1;
		}else if (gm.getNivel()<=20){
			numEnemigos=2;
		}else if (gm.getNivel()>20){
			numEnemigos=3;
		}
		
		for (int i = 0; i < numEnemigos; i++) {		
			
			final EnemigoMoneda enemigo1 = (EnemigoMoneda) context.getGameManager().getMiAlmacen().createEnemigo(Tipo.EN_MONEDA.toString(), columna, fila);
			enemigo1.animar();
			enemigo1.setVisible(true);
			enemigo1.coloca(enemigo1.getColumna(), enemigo1.getFila());
			context.getGameManager().getEstado().incrementaEnemigo();

			new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						Thread.sleep(Aleatorio.DameAleatorio(50, 1200));
						enemigo1.iniciaInteligenciaIA(numeroBomba);
					} catch (Exception e) {
					}
					super.run();
				}
			}.start();
		}
	}
	
	
	public void explotadoPuerta(int numeroBomba) {
		System.out.println("puede petar explotadoPuerta");
		explotado(puertaX,puertaY,numeroBomba);		
	}

	public void explotadoMoneda(int columna, int fila,int numeroBomba) {
		Moneda monedaExplotada=null;
		for (Moneda moneda : almacenSprites) {
			if (moneda.getFila() == fila && moneda.getColumna() == columna) {				
				if(moneda.monedaExplotada()){
					System.out.println("encontrado moneda en fila"+fila+"columna"+columna);
					context.getGameManager().getMatriz().setValor(BomberGame.NADA, fila, columna);
					explotado(moneda.getColumna(),moneda.getFila(),numeroBomba);
					monedaExplotada=moneda;
				}
			}
		}
		if (monedaExplotada!=null){
			
			synchronized (monedasAire) {
				for(TipoMoneda moned: monedasAire){
					if (moned.equals(monedaExplotada.getTipoMoneda())){
						monedasAire.remove(moned);
						return;
					}						
				}
			}
		}
	}

	private TipoMoneda eligeMoneda() {
		if (context.getGameManager().isModoEntrenamiento()){
			cargaMapa(context.getGameManager().getMapaActual());// ???? raro
		}
		int boosters = almacen.size();
		int numeroMoneda = Aleatorio.DameAleatorio(0, boosters - 1);
		TipoMoneda tipo = almacen.get(numeroMoneda);
		almacen.remove(numeroMoneda);
		//if (!context.getGameManager().isModoEntrenamiento())datosMapa.actualiza();
		//context.tostar("ACTUALIZA");
		Log.i("moneda", "eligeMoneda()" +tipo);
		return tipo;

	}

	
	
	
	public boolean monedasEnExceso(Moneda.TipoMoneda tipo){
		BomberEstado estado = context.getGameManager().getEstado();
		
		switch (tipo) {
		case MBOMBA:
			if (estado.getNumeroBombas()>=estado.numeroMaximoDeBombasNivel()){
				return true;
			}
			break;
		case MEXPLOSION:
			if (estado.getTamExplosion()>=estado.numeroMaximoExplosionNivel()){
				return true;
			}
			break;		
		case MDETONADOR:
			if (gm.getEstado().isControlRemoto()){
				return true;
			}
			break;	
		}
		
		return false;		
	}



	public Moneda getMoneda(Moneda.TipoMoneda tipo) {
		
		if (tipo.equals(Moneda.TipoMoneda.MSORPRESA)){
			
			
			
			
			
			Log.i("moneda", "sorpresaaa");
			//context.tostar("sorpresaaa!");
			int explosionRestante=dameNumeroRestantedeMoneda(TipoMoneda.MEXPLOSION);
			int bombasRestante=dameNumeroRestantedeMoneda(TipoMoneda.MBOMBA);
			int detonador=dameNumeroRestantedeMoneda(TipoMoneda.MDETONADOR);			
			boolean p1=gm.getEstado().isDetonadorDisponibleNivel();
			boolean p2= gm.getEstado().isControlRemoto();
			int p3 =gm.getEstado().numeroMaximoDeBombasNivel();
			int tam=gm.getEstado().getTamExplosion();
			
			int numbo=gm.getEstado().getNumeroBombas();
			int p4 =gm.getEstado().numeroMaximoExplosionNivel();
			
			
			
			// monedas en el aire
			
			int monedaAireBomba=0;
			int monedaAireDetonador=0;
			int monedaAireExplosion=0;
			
			for (TipoMoneda tipoMoneda : monedasAire) {
				switch (tipoMoneda) {
				case MBOMBA:
					monedaAireBomba++;
					break;
				case MDETONADOR:
					monedaAireDetonador++;
					break;
				case MEXPLOSION:
					monedaAireExplosion++;
					break;
				default:
					break;
				}
			}
			
			
			if ( (p1 && detonador==0 && !p2)&& monedaAireDetonador==0){
				tipo=TipoMoneda.MDETONADOR;
			}else if ((numbo+bombasRestante +monedaAireBomba)<p3){
				tipo=TipoMoneda.MBOMBA;
			}else if ((tam+explosionRestante+monedaAireExplosion)<p4){
				tipo=TipoMoneda.MEXPLOSION;
			}else{
				tipo=TipoMoneda.MCORAZON;
			}
			
		}		
		
		
		if (tipo.equals(Moneda.TipoMoneda.MCORAZON)){
			
			if (context.getGameManager().getEstado().getVidas()<=6){
				// nada se queda con la vida
				
			}else if (context.getGameManager().getEstado().getVidas()>=7 && context.getGameManager().getEstado().getVidas()<=10){
				int suerte =Aleatorio.DameAleatorio(1, 10);
				if (suerte>=5){
					//nada se queda la vida
				}else{
					tipo=TipoMoneda.MVELOCIDAD;
				}
			}else if (context.getGameManager().getEstado().getVidas()>=11 && context.getGameManager().getEstado().getVidas()<=14){
				int suerte =Aleatorio.DameAleatorio(1, 10);
				if (suerte>=7){
					//nada se queda la vida
				}else{
					tipo=TipoMoneda.MFUERZA;
				}
			}else{
				int suerte =Aleatorio.DameAleatorio(1, 10);
				if (suerte>=8){
					//nada se queda la vida
				}else{
					tipo=TipoMoneda.MFANTASMA;
				}
			}
		}
		
		
		
		
		
		
		// comprobacion final
		
		boolean exceso =monedasEnExceso(tipo);		
		if (exceso){
			tipo=TipoMoneda.MFUERZA;
		}
		
		
		Iterator<Moneda> itr = almacenSprites.iterator();
		while (itr.hasNext()) {
			Moneda moneda = itr.next();
			if (!moneda.getSpriteMoneda().isVisible() && moneda.getTipoMoneda() == tipo) {
				context.getHudDisplay().actualizarIconos();
				Log.i("moneda", "enconrado mnena en array  "+moneda.getTipoMoneda().toString());
				return moneda;
			}
		}
		
		System.out.println("erroorrrr no encontrado mondaa ya creada creo una");		
		Moneda moneda = new Moneda(this, tipo);
		almacenSprites.add(moneda);
		
		Log.i("moneda", "tenido que crear una moneda "+moneda.getTipoMoneda().toString());
		return moneda;
	}

}
