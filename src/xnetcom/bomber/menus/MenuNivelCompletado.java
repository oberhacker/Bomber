package xnetcom.bomber.menus;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.GameManager;
import xnetcom.bomber.ResouceManager;
import xnetcom.bomber.preferences.Preferencias;
import xnetcom.bomber.util.ConstantesResolucion;

public class MenuNivelCompletado {
	
	
	private BomberGame context;
	
	private Sprite sprFondo;

	
	private AnimatedSprite sprReintentar;
	private Sprite sprMenu;
	private AnimatedSprite sprSiguiente;
	
	private Text ctTiempo;
	private Text ctNivel;
	private Text ctMonedas;
	private AnimatedSprite sprtEstrella1;
	private AnimatedSprite sprtEstrella2;
	private AnimatedSprite sprtEstrella3;
	private Sprite sprtFailed;
	private Sprite sprtCleared;
	
	private GameManager gm;
	private ResouceManager rm;
	
	public MenuNivelCompletado(BomberGame context){
		
		
		
		int centro=ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2;
		
		this.context=context;	
		gm= context.getGameManager();		
		rm= context.getResouceManager();
		sprFondo = new Sprite(centro-(rm.getFichaTR().getWidth()/2), 100, rm.getFichaTR(),context.getVertexBufferObjectManager());
		sprFondo.setVisible(false);
		
		
		ctNivel= new Text(50, 70, rm.getmFontEras(),     "LEVEL:  00  ",context.getVertexBufferObjectManager());
		ctTiempo= new Text(50,115, rm.getmFontEras(),    "TIME:     0:00  ",context.getVertexBufferObjectManager());
		ctMonedas= new Text(50, 160, rm.getmFontEras(),  "COINS:  0/0   ",context.getVertexBufferObjectManager());
				
		sprFondo.attachChild(ctNivel);
		sprFondo.attachChild(ctTiempo);
		sprFondo.attachChild(ctMonedas);
		
				
		sprtEstrella1 = new AnimatedSprite(160, 30, rm.getEstrellasTR().deepCopy(),context.getVertexBufferObjectManager()); 
		sprtEstrella2 = new AnimatedSprite(260, 30, rm.getEstrellasTR().deepCopy(),context.getVertexBufferObjectManager());
		sprtEstrella3 = new AnimatedSprite(360, 30, rm.getEstrellasTR().deepCopy(),context.getVertexBufferObjectManager());
		sprFondo.attachChild(sprtEstrella3);
		sprFondo.attachChild(sprtEstrella2);
		sprFondo.attachChild(sprtEstrella1);
		
		
		
				
		sprtFailed = new Sprite(context.alineacionCentradoHorizontalRelativo(sprFondo.getWidth(), rm.getFailedTR().getWidth()), 15, rm.getFailedTR(),context.getVertexBufferObjectManager());
		sprtFailed.setVisible(false);
		sprFondo.attachChild(sprtFailed);
		
		
		sprtCleared= new Sprite(context.alineacionCentradoHorizontalRelativo(sprFondo.getWidth(), rm.getClearedTR().getWidth()), 15, rm.getClearedTR(),context.getVertexBufferObjectManager());		
		sprtCleared.setVisible(false);
		sprFondo.attachChild(sprtCleared);
		
		
		sprReintentar = new AnimatedSprite(0, 0, rm.getRetryTR(),context.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!sprFondo.isVisible()) return false;
				System.out.println("sprReintentar");
				if(pSceneTouchEvent.getAction()==TouchEvent.ACTION_UP){
					System.out.println("sprReintentarisActionUp");
					if (getCurrentTileIndex()==0)reintenar();
				}
				
				return false;				
			};
		};
		
		sprMenu = new Sprite(0, 0, rm.getTomenuTR(),context.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!sprFondo.isVisible()) return false;
				System.out.println("acctionn");
				if(pSceneTouchEvent.isActionUp()){
					System.out.println("acctionnUPPP");
					menu();
				}
				return false;				
			};
		};
		
		sprSiguiente = new AnimatedSprite(0, 0, rm.getSigTR(),context.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!sprFondo.isVisible()) return false;
				System.out.println("sprSiguiente");
				if(pSceneTouchEvent.isActionUp()){
					if (getCurrentTileIndex()==0)siguente();
				}
				return false;				
			};
		};
		
		sprReintentar.setScale(1.2f);
		sprMenu.setScale(1.2f);
		sprSiguiente.setScale(1.2f);
		
		
		// recolocacion de estrellas
		
		
		
		sprReintentar.setPosition(30, sprFondo.getHeightScaled()-sprReintentar.getHeightScaled()-10);
		sprMenu.setPosition(10+context.alineacionCentradoHorizontalRelativo(sprFondo.getWidthScaled() ,sprMenu.getWidthScaled()), sprFondo.getHeightScaled()-sprMenu.getHeightScaled()-10);		
		sprSiguiente.setPosition(-15+context.alineacionInteriorDerecha(sprFondo.getWidthScaled(), sprSiguiente.getWidthScaled()), sprFondo.getHeightScaled()-sprSiguiente.getHeightScaled()-10);
		
		
		
		System.out.println("sprReintentar.getHeight()"+sprReintentar.getHeight());
		System.out.println("sprReintentar.getHeightScaled()"+sprReintentar.getHeightScaled());
		
		
		sprFondo.attachChild(sprReintentar);
		sprFondo.attachChild(sprMenu);
		sprFondo.attachChild(sprSiguiente);
		hud= new HUD();
		hud.attachChild(sprFondo);
		
		
		hud.registerTouchArea(sprReintentar);
		hud.registerTouchArea(sprMenu);
		hud.registerTouchArea(sprSiguiente);

		
		sprFondo.setZIndex(BomberGame.POR_ENCIMA_DE_TODO+100);
		
		
		//context.getHud().attachChild(sprFondo);
		
	}
	
	
	public boolean isVisible() {
		return sprFondo.isVisible();
		
	}
	
	
	HUD hud;
	
	public int getEstrellas(int boostersEncontrados, int boostersTotales){
		float porcentageBoster=1; 
		int estrellas=0;
		if (boostersTotales!=0)porcentageBoster=((float) boostersEncontrados/ (float)boostersTotales);
		
		if(porcentageBoster==1){
			estrellas=3;			
		}else if (porcentageBoster>=0.5f){
			estrellas=2;
		}else{
			//unaestrella			
			estrellas=1;
		}
		return estrellas;
	}
	
	public void resultado(boolean pasado, int boostersEncontrados, int boostersTotales){	
		int nivel= context.getGameManager().getNivel();
		context.getResouceManager().getPasosB().pause();
		context.getResouceManager().stopMusica();
		context.habilitaBotones(false);
		context.getResouceManager().PasadoPantalla();
		
		context.getGameManager().getBomberman().pararMovimiento();		
		context.getHudDisplay().actualizarIconos();		
		
		context.getGameManager().pause();
		context.getGameManager().getBomberman().finBooster();
		
		
		// reproducir sonido de puntos
		if (pasado){
			sprtFailed.setVisible(false);
			sprtCleared.setVisible(true);
		}else{
			sprtCleared.setVisible(false);
			sprtFailed.setVisible(true);
		}

			
		if(!pasado){
			setEstrellas(0);
		}else {
			setEstrellas (getEstrellas(boostersEncontrados, boostersTotales));
			int UltimoNivelPasado = Preferencias.leerPreferenciasInt("UltimoNivelPasado");			
			if (UltimoNivelPasado<nivel){
				Preferencias.guardarPrefenrencias("UltimoNivelPasado", nivel);
			}			
		}	
		
		
		if (context.getGameManager().getEstado().getVidas()>0){
			 enableReintentar(true);
		}else{
			 enableReintentar(false);
		}
		
		if (context.getGameManager().getEstado().getNivel()==45){
			enableSiguiente(false);
		}else{
			enableSiguiente(pasado);
		}
		
		
		/*
		if (gm.getEstado().getVidas()==0 && !pasado){
			enableReintentar(false);
		}else{
			
		}
		*/
		// DatosMapa datos = gm.getParser().getDatosMapa(gm.getMapaActual().getNumeroMapa());
		// gm.getEstado().
		
		// ctNivel.setText("LEVEL - "+gm.getMapaActual().getNumeroMapa());
		
		int minutos= context.getGameManager().getDisplay().getSegundosPasados()/60;
		int segundos = context.getGameManager().getDisplay().getSegundosPasados() -(minutos*60);
		
		
		//context.tostar(String.valueOf(gm.getSegundosPasados()));
		
		String textosegundos="";
		if (segundos<10)textosegundos="0";
		textosegundos=textosegundos+String.valueOf(segundos);		
		
		ctTiempo.setText("TIME: "+minutos+":"+textosegundos);
		ctMonedas.setText("COINS:  "+boostersEncontrados+"/"+boostersTotales+"   ");
		
		String textonivel="";
		if (nivel<10)textonivel="0";
		textonivel=textonivel+String.valueOf(nivel);	
		ctNivel.setText("LEVEL:  "+textonivel+"  ");
		sprFondo.setVisible(true);	
		context.getmBoundChaseCamera().setHUD(hud);
		
	
	}	
		
	private void reintenar(){
		context.getGameManager().play();
		context.getmBoundChaseCamera().setHUD(context.getHud());
		sprFondo.setVisible(false);
		context.habilitaBotones(true);		
		reseteaEstrellas();
		context.getGameManager().miresetea();
	}
	
	private void siguente(){
		context.getGameManager().play();
		context.getmBoundChaseCamera().setHUD(context.getHud());
		sprFondo.setVisible(false);
		context.habilitaBotones(true);
		reseteaEstrellas();
		context.getGameManager().siguienteMapa();
		
	}
	
	

	
	private void menu(){	
		context.getGameManager().play();
		context.getmBoundChaseCamera().setHUD(context.getHud());
		/*
		new Thread(){
			@Override
			public void run() {
				setVisible(false);
			}
		}.start();*/
		setVisible(false);
		context.habilitaBotones(true);		
		context.VerMenuMapas();		
		reseteaEstrellas();
		
	}
	
	public void reseteaEstrellas(){
		sprtEstrella1.setCurrentTileIndex(0);
		sprtEstrella2.setCurrentTileIndex(0);
		sprtEstrella3.setCurrentTileIndex(0);
	}
	
	
	
	private  void setEstrellas(final int numEstrellas){
		sprtEstrella1.stopAnimation(0);
		sprtEstrella2.stopAnimation(0);
		sprtEstrella3.stopAnimation(0);
		System.out.println("llamado poner estrellas "+numEstrellas);
		Thread hilo = new Thread() {			
			@Override
			public void run() {			
				try {				
						Thread.sleep(600);	
						context.getResouceManager().playEstrellas();
						System.out.println("animado1");
						sprtEstrella1.animate(50, false);					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		Thread hilo2 = new Thread() {		
			@Override
			public void run() {			
				try {				
						Thread.sleep(1200);	
						System.out.println("animado1");
						context.getResouceManager().playEstrellas();
						sprtEstrella2.animate(50, false);					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
		};
		Thread hilo3 = new Thread() {			
			@Override
			public void run() {			
				try {				
						Thread.sleep(1800);	
						System.out.println("animado3");
						context.getResouceManager().playEstrellas();
						sprtEstrella3.animate(50, false);					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		if (numEstrellas>=1){
			hilo.start();
		}
		if (numEstrellas>=2){
			hilo2.start();
		}
		if (numEstrellas>=3){
			 hilo3.start();
		}
		
		
		
		
		
	}
	
	public void setVisible(boolean pVisible){
		System.out.println("invisibleeee"+sprFondo.isVisible());
		sprFondo.setVisible(pVisible);
		System.out.println("invisibleeee2"+sprFondo.isVisible());
	}
	
	private void enableReintentar(boolean enable){	
		if (enable){
			sprReintentar.setCurrentTileIndex(0);
		}else{
			sprReintentar.setCurrentTileIndex(1);
		}
			
	}
	private void enableSiguiente(boolean enable){	
		System.out.println("enableSiguiente"+enable);
		if (enable){
			sprSiguiente.setCurrentTileIndex(0);
		}else{
			sprSiguiente.setCurrentTileIndex(1);
		}
			
	}
	
	
}
