package xnetcom.bomber.inicio;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.ResouceManager;
import xnetcom.bomber.andengine.MiEngine;
import xnetcom.bomber.menus.Info;
import xnetcom.bomber.preferences.Preferencias;
import xnetcom.bomber.util.ConstantesResolucion;

public class Inicio {

	/*
	 * Resolucion 1 1280x720 16/9
	 * 
	 * 
	 */
	
	private static final int CAMERA_WIDTH_1 = 1280;
	private static final int CAMERA_HEIGHT_1 = 720;
	
	
	private static final int CAMERA_WIDTH_2 = 800;
	private static final int CAMERA_HEIGHT_2 = 480;
	

	public static final int RESOLUCION=1;
	
	private Preferencias preferencias;	
	public static final int FONT_SIZE_1 = 60;
	
	
	private Sprite sprtBombaPlay;
	private Sprite sprtOpciones;
	
	private Sprite misiones;
	private Sprite entrenamiento;
	
	private MenuMapas mapas;
	
	

	private Scene escenaMapas;
	private Scene escenaInicio;
	private Scene escenaOpciones;
		

	private Camera mCamera;
	
	public Scene getEscenaInicio(){
		return escenaInicio;
	}

	public Preferencias getPreferencias() {
		return preferencias;
	}

	
	public static Preferencias pref;
	
	
	
	private ResouceManager resouceManager;
	public ResouceManager getResouceManager() {
		return resouceManager;
	}


	private BomberGame context;
	public BomberGame getContext() {
		return context;
	}
	public  Inicio(BomberGame context) {
		this.context=context;
		resouceManager=context.getResouceManager();
	}

	Sprite fondo;
	public void setOscuro(boolean oscuro){
		if (oscuro){
			fondo.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			fondo.setAlpha(0.5f);
		}else{
			fondo.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);		
			fondo.setAlpha(1);
		}

	}
	
	
	
	public void cargaMenuCampOentrenamiento(){
		int UltimoNivelPasado = Preferencias.leerPreferenciasInt("UltimoNivelPasado");		
		if (UltimoNivelPasado==-1){
			entrenamiento.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			entrenamiento.setAlpha(0.3f);
		}else{
			entrenamiento.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);		
			entrenamiento.setAlpha(1);
		}
		setOscuro(true);
		sprtBombaPlay.setVisible(false);
		sprtOpciones.setVisible(false);
		entrenamiento.setVisible(true);
		misiones.setVisible(true);
	}
	
	public void inicioNormal(){
		setOscuro(false);
		sprtBombaPlay.setVisible(true);
		sprtOpciones.setVisible(true);
		entrenamiento.setVisible(false);
		misiones.setVisible(false);
	}
	
	
	
	
	public Scene onLoadScene() {
		escenaInicio = new Scene();			
		float offset = (float) (((ConstantesResolucion.CAMERA_WIDTH_16_9_M-ConstantesResolucion.getCAMERA_WIDTH_MASTER()))/2);
		
		
		/*t offset=0;
		if (ConstantesResolucion.getResolucion().equals("QVGA")){
			offset=130;
		}*/
		
		fondo=new Sprite(ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2, ConstantesResolucion.getCAMERA_HEIGHT_MASTER()/2, resouceManager.getFondo_inicio_TR(),context.getEngine().getVertexBufferObjectManager());
		

		misiones = new Sprite(offset+30, 350, resouceManager.getCampaignTR(),context.getEngine().getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!isVisible())return false;
				if (pSceneTouchEvent.getAction() == 1) {
					cargarMenuMapas();
					return false;
				} 
					return false;			
			}
		};
		misiones.setVisible(false);
		
		escenaInicio.attachChild(misiones);
		escenaInicio.registerTouchArea(misiones);
		
		
		entrenamiento = new Sprite(offset+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-resouceManager.getTrainingTR().getWidth()-20,350,resouceManager.getTrainingTR(),context.getEngine().getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!isVisible())return false;
				int UltimoNivelPasado = Preferencias.leerPreferenciasInt("UltimoNivelPasado");
				if (UltimoNivelPasado!=-1)context.getGameManager().cargaCampoEntrenamiento();
				return false;
			}
		};
		
		entrenamiento.setVisible(false);
		
		escenaInicio.attachChild(entrenamiento);
		escenaInicio.registerTouchArea(entrenamiento);
		
		
		escenaInicio.setBackground(new SpriteBackground(fondo));
				
		
		sprtBombaPlay = new Sprite(0, 0, resouceManager.getBomba_inicio_TR(),context.getEngine().getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(!isVisible())return false;
				if (pSceneTouchEvent.getAction() == 1) {
					cargaMenuCampOentrenamiento();
					return false;
				} 
					return false;
			}
		};
		
		
		// recalcula el tamano de  campaing y de trainin
		if (ConstantesResolucion.getResolucion().equals(ConstantesResolucion.RES_QVGA)){
			entrenamiento.setScale(0.7f);
			misiones.setScale(0.7f);
		}
		
		
		
		
		
		

		System.out.println("qwe ConstantesResolucion.getCAMERA_WIDTH_MASTER()"+ConstantesResolucion.getCAMERA_WIDTH_MASTER());
		System.out.println("ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2 -sprtBombaPlay.getWidthScaled()/2"+(ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2 -sprtBombaPlay.getWidthScaled()/2));
		
		System.out.println("sprtBombaPlay.getWidthScaled()"+sprtBombaPlay.getWidthScaled());
		
	//	ClippingEntity container = new ClippingEntity(0, 50, 200,500);
		//container.attachChild(sprtBombaPlay);
		
		sprtBombaPlay.setScale(ConstantesResolucion.getSprtBombaPlaySize_MASTER());

		
		sprtBombaPlay.setPosition(ConstantesResolucion.CAMERA_WIDTH_16_9_M /2 -sprtBombaPlay.getWidthScaled()/2, ConstantesResolucion.getSprtBombaPlayY_MASTER());
		
		escenaInicio.attachChild(sprtBombaPlay);
		escenaInicio.registerTouchArea(sprtBombaPlay);
		
	
		sprtBombaPlay.registerEntityModifier(new LoopEntityModifier(new SequenceEntityModifier(new ScaleModifier(0.7f, ConstantesResolucion.getSprtBombaPlaySize_MASTER(), ConstantesResolucion.getSprtBombaPlaySize_MASTER()*1.3f),new ScaleModifier(0.7f, ConstantesResolucion.getSprtBombaPlaySize_MASTER()*1.3f, ConstantesResolucion.getSprtBombaPlaySize_MASTER()))));
			
		sprtOpciones = new Sprite(ConstantesResolucion.getSprtOpcionesPlayX_MASTER(),ConstantesResolucion.getSprtOpcionesPlayY_MASTER(), resouceManager.getOpciones_TR(),context.getEngine().getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if(!isVisible())return false;
				if (pSceneTouchEvent.getAction()==0){
					cargarMenuOpciones();
					return false;
				}else return true;
				
			}
		};
		sprtOpciones.setScale(ConstantesResolucion.getSprtOpcionesPlaySize_MASTER());
		escenaInicio.attachChild(sprtOpciones);
		escenaInicio.registerTouchArea(sprtOpciones);

		 
		
		mapas= new MenuMapas(context);	
		context.setMenuMapas(mapas);
		escenaMapas= mapas.getEscenaMapas();
		
		
		
		Info menuInfo = new Info(context);
		
		escenaOpciones = menuInfo.getEcenaInfo();
		
		
		
		return escenaInicio;
	}
	public Scene getEscenaMapas() {
		return escenaMapas;
	}


	public boolean iniciado=false;

	
	public void cargarMapa(int numMapa){
		System.out.println("cargo mapa "+numMapa);
		
		if (context.getEscenaJuego()!=null){
			context.getGameManager().cargaNivel(numMapa);
			//context.getGameManager().cargaCampoEntrenamiento();
			context.getEngine().setScene(context.getEscenaJuego());
			MiEngine en= (MiEngine) context.getEngine();
			en.selCamera(1);
		}else{
			System.out.println("la escena es nula");
		}	
	}
	
	
	
	
	// antigua opciones despues info
	public void  cargarMenuOpciones(){
		context.getEngine().setScene(escenaOpciones);
	}
	public void cargarMenuMapas(){
		context.getEngine().setScene(escenaMapas);
	}
	public void cargarMenuInicio(){
		context.getEngine().setScene(escenaInicio);
	}
	

}
