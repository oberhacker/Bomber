package xnetcom.bomber;

import java.io.IOException;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.camera.SmoothCamera;
import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.modifier.ColorBackgroundModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSCounter;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.controller.MultiTouchController;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.IGameInterface.OnCreateResourcesCallback;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;
import org.andengine.ui.IGameInterface.OnPopulateSceneCallback;
import org.andengine.ui.activity.BaseGameActivity;

import xnetcom.bomber.BomberMan.PlayerDirection;
import xnetcom.bomber.andengine.MiEngine;
import xnetcom.bomber.andengine.TransparentBitmapTextureAtlasSource;
import xnetcom.bomber.entidades.HudDisplay;
import xnetcom.bomber.entidades.Moneda.TipoMoneda;
import xnetcom.bomber.inicio.Inicio;
import xnetcom.bomber.inicio.MenuMapas;
import xnetcom.bomber.menus.Menu;
import xnetcom.bomber.preferences.Preferencias;
import xnetcom.bomber.sql.DatabaseHandler;
import xnetcom.bomber.sql.DatosMapa;
import xnetcom.bomber.util.ConstantesResolucion;
import xnetcom.bomber.util.ParserXML;
import xnetcom.bomber5.R;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class BomberGame extends BaseGameActivity  {

	private static final long[] ANIMATE_DURATION = new long[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30 };
	
	public String RES_16_9_M="16_9_M";
	
	public String RES_WVGA="WVGA_15_9";
	public String RES_QVGA="QVGA";
	/*
	 private static final int CAMERA_WIDTH_3 = 1920;
	 private static final int CAMERA_HEIGHT_3 = 1080;

	private static final int CAMERA_WIDTH = 1280;
	private static final int CAMERA_HEIGHT = 720;

	private static final int CAMERA_WIDTH_1 = 1280;
	private static final int CAMERA_HEIGHT_1 = 720;

	private static final int CAMERA_WIDTH_2 = 800;
	private static final int CAMERA_HEIGHT_2 = 480;
	
*/
	
	public static final int INICIO_VIDAS=5;
	public static final int INICIO_TAM_EXPLOSION=1;
	public static final String INICIO_CONTROL_REMOTO="false";
	public static final int INICIO_NUM_BOMBAS=1;
	public static final int MAXIMO_BOMBAS=10;
	/*
	 * Bomberman tiene in zindex de 120 y cuando esta por detras del techomuro
	 * su zindez es 80 los enemigos haran igual pero con uno menos los enemigos
	 * transparentes 95
	 */
	
	

	
public static boolean visibleSuperficiesDeControl= false;

	
	/* alto y ancho de los cuadrados */
	//public static int ANCHO = (int) (90*0.5f);
	//public static int ALTO = (int) (78*0.5f);

	
	
	public static int ANCHO = (int) (45);
	public static int ALTO = (int) (39);
	
	
	
	public static ConstantesResolucion RESOLUCION;
	
	public static BomberGame game; 
	/*
	public static final int ZINDEX_SUELO = 30;
	public static final int ZINDEX_PIEDRAS_SOMBRAS_MUROARRIBA = 40;
	public static final int ZINDEX_PAREDES = 70;
	public static final int ZINDEX_MONEDAS = 80;
	public static final int ZINDEX_PAREDES_TECHO = 90;
	public static final int ZINDEX_ENEMIGOS_TRANSPARENTES = 95;
	public static final int ZINDEX_PAREDES_TRANSPARENTES = 100;
	public static final int ZINDEX_PAREDES_TRANSPARENTES_TECHO = 100;
	public static final int ZINDEX_BOMBAS = 110;
	public static final int ZINDEX_BOMBERMAN_Arriba = 120;// 120 - 80
	public static final int ZINDEX_BOMBERMAN_Abajo  = 120;// 120 - 80
	public static final int ZINDEX_ENEMIGOS = 119; // 119- 79
	public static final int ZINDEX_FUEGO = 150;
	public static final int ZINDEX_PIEDRAS_TECHO = 160;
	public static final int ZINDEX_MUROSLATERAL_ABAJO = 170;
	public static final int ZINDEX_PARRAS_SOMBRAS = 190;
	public static final int ZINDEX_PARRAS = 200;
	public static final int ZINDEX_MUROFRAGMENTOS = 210;
	
	
	*/
	
	
	DatabaseHandler basedatos;
	
	public DatabaseHandler getBasedatos() {
		if (basedatos==null)basedatos= new DatabaseHandler(this);
		return basedatos;
	}

	public static final int ZINDEX_SUELO = 30;
	public static final int ZINDEX_PIEDRAS_SOMBRAS_MUROARRIBA = 40;
	
	public static final int ZINDEX_PAREDES = 1200;
	public static final int ZINDEX_MONEDAS = 1201;
	public static final int ZINDEX_PAREDES_TECHO = 1300;
	public static final int ZINDEX_ENEMIGOS_TRANSPARENTES = 1350;

	public static final int ZINDEX_BOMBAS = 1205;
	public static final int ZINDEX_BOMBERMAN_Arriba = 1500;// 120 - 80
	public static final int ZINDEX_BOMBERMAN_Abajo  = 1210;// 120 - 80
	public static final int ZINDEX_ENEMIGOS = 1251; // 119- 79
	public static final int ZINDEX_FUEGO = 1290;
	public static final int ZINDEX_PIEDRAS_TECHO = 1700;
	//public static final int ZINDEX_MUROSLATERAL_ABAJO = 170;
	public static final int ZINDEX_PARRAS_SOMBRAS = 2000;
	public static final int ZINDEX_PARRAS = 2500;
	public static final int ZINDEX_MUROFRAGMENTOS = 2600;
	public static final int POR_ENCIMA_DE_TODO = 3000;
	
	
	private MenuMapas menuMapas;
	
	

	
	public MenuMapas getMenuMapas() {
		return menuMapas;
	}

	public void setMenuMapas(MenuMapas menuMapas) {
		this.menuMapas = menuMapas;
	}
	
		
	public  void abrirURL(){
		try {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=xnetcom.bomber5"));
			startActivity(browserIntent);
		} catch (ActivityNotFoundException e) {
		    Toast.makeText(this, "No application can handle this request."
		        + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
		    e.printStackTrace();
		}
	}
	
	
	public void mostrarDialogo(){
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 new AlertDialog.Builder(BomberGame.this)
			        .setIcon(android.R.drawable.ic_dialog_alert)
			        .setTitle(R.string.rate)
			        .setMessage(R.string.rate_dialog)
			        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
			            @Override
			            public void onClick(DialogInterface dialog, int which) {
			            	Preferencias.guardarPrefenrencias("Votado", 1);
			            	abrirURL();
			            }

			        })
			        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
						
						public void onClick(DialogInterface dialog, int which) {							

							Preferencias.guardarPrefenrencias("veces", 0);
						}
					})
			        .show();
			}
		});
	     
	}
	
	
	
	
/*
	public static final float ZoomFactor = 1f;
	public static final float CrucetaSize = 2.25f;
	public static final int CrucetaX = 98;
	public static final int CrucetaY = 505;

	public static final float btn1Size = 0.7f;
	public static final int btn1X = 814;
	public static final int btn1Y = 500;

	public static final float btn2Size = 0.7f;
	public static final int btn2X = 1042;
	public static final int btn2Y = 500;
*/
	private Sprite btn_1, btn_2;
	
	
	private ResouceManager resourManager;
	private BitmapTextureAtlas btn_1_BTA;
	private TextureRegion btn_1_TR;
	private BitmapTextureAtlas btn_2_BTA;
	private TextureRegion btn_2_TR;

	private float TiempoPorCuadro = 0.40f;
	private static final int FONT_SIZE = 25;
	public static final int NADA = 0;
	public static final int PARED = 1;
	public static final int PIEDRA = 2;
	public static final int BOMBA = 3;	
	public static int MONEDA = -4;
	public static int PUERTA = -5;
	
	private Menu menu;
	public Menu getMenu() {
		return menu;
	}

	private Vibrator vibrator;	
	public boolean vibrar=true;
	private boolean inteligenciaPorEnemigo=true;
	private GameManager gameManager;
	private Text texto1;
	private BitmapTextureAtlas mFontTexture;
	private Font mFont;
	
	private BitmapTextureAtlas mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	private DigitalOnScreenControl mDigitalOnScreenControl;


	private TiledTextureRegion mPlayerTextureRegion;

	
	
	private HudDisplay hudDisplay;
	
	public HudDisplay getHudDisplay() {
		return hudDisplay;
	}

	private Sprite paralaxAI;
	private Sprite paralaxSombraAI;

	private Sprite paralaxAD;
	private Sprite paralaxSombraAD;

	private Sprite paralaxBD;
	private Sprite paralaxSombraBD;

	private Sprite paralaxBI;
	private Sprite paralaxSombraBI;

	
	private boolean botonA = false;
	private boolean botonB = false;

	public String fps;
	AnimatedSprite spr;

	private BitmapTextureAtlas mAutoParallaxBackgroundTexture;
	private TextureRegion mParallaxLayer;
	private BitmapTextureAtlas mAutoParallaxBackgroundTextureSombra;
	private TextureRegion mParallaxLayerSombra;

	private TextureRegion mParallaxLayerAD;

	private TextureRegion mParallaxLayerSombraAD;
	private TextureRegion mParallaxLayerBD;
	private TextureRegion mParallaxLayerSombraBD;
	private TextureRegion mParallaxLayerAI;
	private TextureRegion mParallaxLayerSombraAI;

	

	private SmoothCamera mBoundChaseCamera;

	// private BoundCamera mBoundChaseCamera;
	public ResouceManager getResouceManager(){
		return resourManager;
	}
	public Sprite getBtn_1() {
		return btn_1;
	}

	public Sprite getBtn_2() {
		return btn_2;
	}

	private HUD hud;

	public HUD getHud() {
		return hud;
	}	
	
	public boolean isInteligenciaPorEnemigo() {
		return inteligenciaPorEnemigo;
	}

	public void setInteligenciaPorEnemigo(boolean inteligenciaPorEnemigo) {
		this.inteligenciaPorEnemigo = inteligenciaPorEnemigo;
	}
	
	public boolean isVibrar() {
		return vibrar;
	}
	public void vibrar(long milisegundos){
		if (vibrar){
			vibrator.vibrate(milisegundos);
		}
	}

	public void setVibrar(boolean vibrar) {
		this.vibrar = vibrar;
	}

	public Text getTexto1() {
		return texto1;
	}

	public void setTexto1(Text texto1) {
		this.texto1 = texto1;
	}

	public Font getmFont() {
		return mFont;
	}



	public DigitalOnScreenControl getmDigitalOnScreenControl() {
		return mDigitalOnScreenControl;
	}


	public SmoothCamera getmBoundChaseCamera() {
		return mBoundChaseCamera;
	}



	public GameManager getGameManager() {
		return gameManager;
	}

	public Vibrator getVibrator() {
		return vibrator;
	}




	private Scene escenaMenu;
	private Scene escenaJuego;
	private Scene escenaInicio;
	private Scene escenaLoading;

	public Scene getEscenaLoading() {
		return escenaLoading;
	}

	public void setEscenaInicio(Scene escenaInicio) {
		this.escenaInicio = escenaInicio;
	}
	public Scene getEscenaInicio() {
		return escenaInicio;
	}
	public Scene getEscenaMenu() {
		return escenaMenu;
	}

	public Scene getEscenaJuego() {
		return escenaJuego;
	}

	public BomberMan getBomberman() {
		return gameManager.getBomberman();
	}
	private Camera camera;
	Camera getCamera (){
		return camera;
	}
	MiEngine en;
	@Override
	public EngineOptions onCreateEngineOptions() {
		BomberGame.game=this;
		ConstantesResolucion.inicia(DetectorRatio());
		this.mBoundChaseCamera = new SmoothCamera(0, 0, ConstantesResolucion.getCAMERA_WIDTH_MASTER(), ConstantesResolucion.getCAMERA_HEIGHT_MASTER(), 1000, 1000, 1.0f);
		camera = new Camera(ConstantesResolucion.getOffsetX_camara(), 0, ConstantesResolucion.getCAMERA_WIDTH_MASTER(), ConstantesResolucion.getCAMERA_HEIGHT_MASTER());
		//Engine engine = new Engine(new EngineOptions(true, ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), this.mBoundChaseCamera).setNeedsSound(true).setNeedsMusic(true));
		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(ConstantesResolucion.getCAMERA_WIDTH_MASTER(), ConstantesResolucion.getCAMERA_HEIGHT_MASTER()), this.mBoundChaseCamera){

		};
//		engineOptions.getTouchOptions().setRunOnUpdateThread(true);//no en gles2
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);
		en =new MiEngine(engineOptions, this.camera);
		
		try {
			en.setTouchController(new MultiTouchController());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return engineOptions;
		//return engine;
	}

	@Override
	public Engine onCreateEngine(final EngineOptions pEngineOptions) {
		return en;
	}
	
	public String DetectorRatio(){	
		/*
		final Display defaultDisplay = getWindow().getWindowManager().getDefaultDisplay();
	     int CAMERA_WIDTH = defaultDisplay.getWidth();
	     int CAMERA_HEIGHT = defaultDisplay.getHeight();
	     
	     float ratio =CAMERA_WIDTH/CAMERA_HEIGHT;
	     */
	     
	    // return RES_QVGA;
	     
	     DisplayMetrics metrics = new DisplayMetrics();
	     getWindowManager().getDefaultDisplay().getMetrics(metrics);
	     
	     int CAMERA_WIDTH = metrics.widthPixels;
	     int CAMERA_HEIGHT = metrics.heightPixels;
	     float ratio =(float)CAMERA_WIDTH/CAMERA_HEIGHT;
	     
	     
		if (ratio>=1.7f && ratio<=1.8f ){// 16/9			
			return RES_16_9_M;
		}else if (ratio>=1.6f && ratio<1.7f){// 15/9
			return RES_WVGA;
		}else if (ratio>=1.3f && ratio<1.6f){//4/3
			return RES_QVGA;
		}else{
			return RES_16_9_M;
		}
		
	}
	
	
	
	
	
	
	
	private Inicio inicio;
	private Carga carga;
	
	public Inicio getInicio(){
		return inicio;
	}
	
	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		//onLoadResourcesInicio();
		//onLoadResourcesGame();	
		
		
		MiEngine en= (MiEngine) getEngine();
		en.selCamera(2);
		
		onLoadResourcesCarga();		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}
	
	public void onLoadResourcesCarga() {		
		carga= new Carga(this);
		Preferencias.inicia(this);
		inicializaPrimeraVez();
		carga.onLoadResources();
	}
	
	public void onLoadResourcesInicio() {
		resourManager = new ResouceManager(this);
		resourManager.cargarTexturas();
		resourManager.cargaMusica();
		resourManager.setSonido(true);
		inicio= new Inicio(this);		
	}
	

	
	

	
	
	
	public void onLoadResourcesGame() {
		
		//System.out.println("cargartexturassssssssssss2");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		this.mAutoParallaxBackgroundTexture = new BitmapTextureAtlas(this.getTextureManager(),512, 256, TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(mAutoParallaxBackgroundTexture, new TransparentBitmapTextureAtlasSource(512,256), 0, 0);
		this.mParallaxLayer = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "paralaxAI.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mAutoParallaxBackgroundTexture);

		this.mAutoParallaxBackgroundTextureSombra = new BitmapTextureAtlas(this.getTextureManager(),512, 256, TextureOptions.DEFAULT);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(mAutoParallaxBackgroundTextureSombra, new TransparentBitmapTextureAtlasSource(512,256), 0, 0);
		this.mParallaxLayerSombra = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTextureSombra, this, "sombra2.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mAutoParallaxBackgroundTextureSombra);

		this.mParallaxLayerAD = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "paralaxAI.png", 0, 0);
		this.mParallaxLayerSombraAD = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTextureSombra, this, "sombra2.png", 0, 0);

		this.mParallaxLayerBD = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "paralaxAI.png", 0, 0);
		this.mParallaxLayerSombraBD = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTextureSombra, this, "sombra2.png", 0, 0);

		this.mParallaxLayerAI = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTexture, this, "paralaxAI.png", 0, 0);
		this.mParallaxLayerSombraAI = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mAutoParallaxBackgroundTextureSombra, this, "sombra2.png", 0, 0);

		btn_1_BTA = new BitmapTextureAtlas(this.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(btn_1_BTA, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
		btn_1_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(btn_1_BTA, this, "btn_1.png", 0, 0);

		btn_2_BTA = new BitmapTextureAtlas(this.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(btn_2_BTA, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
		btn_2_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(btn_2_BTA, this, "btn_2.png", 0, 0);

		getEngine().getTextureManager().loadTexture(btn_1_BTA);
		getEngine().getTextureManager().loadTexture(btn_2_BTA);

		// Control texture
		this.mOnScreenControlTexture = new BitmapTextureAtlas(this.getTextureManager(),256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(mOnScreenControlTexture, new TransparentBitmapTextureAtlasSource(256,128), 0, 0);
		this.mOnScreenControlBaseTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mOnScreenControlTexture, this, "onscreen_control_knob.png", 128, 0);

		


		this.mEngine.getTextureManager().loadTexture(this.mOnScreenControlTexture);

		this.mFontTexture = new BitmapTextureAtlas(this.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		FontFactory.setAssetBasePath("font/");
//		this.mFont = FontFactory.createFromAsset(this.mFontTexture, this, "DD.ttf", FONT_SIZE, true, android.graphics.Color.BLACK);//gles1
		this.mFont = FontFactory.createFromAsset(this.getFontManager(), this.getTextureManager(), 256, 256, TextureOptions.BILINEAR, this.getAssets(), "DD.ttf", FONT_SIZE, true, android.graphics.Color.BLACK);//gles2
		this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
		this.mEngine.getFontManager().loadFont(this.mFont);

	}

	
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		final Scene scene = carga.onLoadScene();

		pOnCreateSceneCallback.onCreateSceneFinished(scene);
		
	}
	//gles1
//	public Scene onLoadScene() {
//		//return inicio.onLoadScene();
//		return carga.onLoadScene();
//	}
	
	public void inicializaPrimeraVez(){
		if ( Preferencias.leerPreferencias("primeravez")==null){			
			Preferencias.guardarPrefenrencias("primeravez", "NO");
			Preferencias.guardarPrefenrencias("musica","true");
			Preferencias.guardarPrefenrencias("sonido","true");
			//Preferencias.guardarPrefenrencias("vidas", BomberGame.INICIO_VIDAS);
			//Preferencias.guardarPrefenrencias("controlRemoto","true");
			//Preferencias.guardarPrefenrencias("numBombas", 7);
			//Preferencias.guardarPrefenrencias("tamExplosion", 4);
			ParserXML parser = new ParserXML(this);

			List<DatosMapa> mapas =parser.getDatosMapas();
			DatabaseHandler db = getBasedatos();
			for (DatosMapa datosMapa : mapas) {
				
				System.out.println("datosMapa");
				System.out.println("numeromapa " +datosMapa.getNumeroMapa());
				System.out.println("getM_bomba " +datosMapa.getM_bomba());
				System.out.println("getM_fantasma()" +datosMapa.getM_fantasma());
	
				
				db.addMapa(datosMapa);
			}	

		}
	}


	
	public void playMusica() {
		try {
			
			if (getResouceManager().isMusica()){
				if (!getResouceManager().isPlayinMusica()) {				
					getResouceManager().playMusicaRandom();					
				}
			}
		} catch (Exception e) {
			System.out.println("eeee");
		}
 	}


	
	
	
	public Scene onLoadSceneGame() {
		
		int centro=ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2;
		
			escenaLoading= new Scene();
			Sprite loading = new Sprite(
					centro-(getResouceManager().getLoadingTR().getWidth()/2),
					alineacionCentradoVertical(getResouceManager().getLoadingTR().getHeight()),
					getResouceManager().getLoadingTR(),this.getVertexBufferObjectManager());
			escenaLoading.attachChild(loading);
					
		
		
		mEngine.enableVibrator(this);
		setInteligenciaPorEnemigo(false);
		 basedatos= new DatabaseHandler(this);
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final FPSCounter fpsCounter = new FPSCounter();
		this.mEngine.registerUpdateHandler(fpsCounter);
		escenaJuego = new Scene();
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		escenaJuego.registerUpdateHandler(new TimerHandler(1f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				fps = "" + fpsCounter.getFPS();
				fpsCounter.reset(); // <- HERE IT IS
				pTimerHandler.reset();
				//playMusica();
				try{							
					Log.d("datos", "gameManager.getEstado().getEnemigosRestantes() "+gameManager.getEstado().getEnemigosRestantes());
					Log.d("datos","gameManager.getEstado().getNivel() " +gameManager.getEstado().getNivel() );
					Log.d("datos","gameManager.getEstado().getNumeroBombas()" + gameManager.getEstado().getNumeroBombas());
					Log.d("datos","gameManager.getEstado().getTamExplosion()" + gameManager.getEstado().getTamExplosion());
					Log.d("datos","gameManager.getEstado().getVidas() " + gameManager.getEstado().getVidas());
					Log.d("datos","gameManager.getMapaActual().getBoostersIniciales()" + gameManager.getMapaActual().getBoostersIniciales());
					Log.d("datos","gameManager.getMapaActual().getBoosterTotales()" + gameManager.getMapaActual().getBoosterTotales());
					Log.d("datos","gameManager.getMapaActual().getEstrellas()" + gameManager.getMapaActual().getEstrellas());
					Log.d("datos","gameManager.getMapaActual().getM_bomba() " + gameManager.getMapaActual().getM_bomba());
					Log.d("datos","gameManager.getMapaActual().getM_corazon()" + gameManager.getMapaActual().getM_corazon());
					Log.d("datos","gameManager.getMapaActual().getM_correr()" + gameManager.getMapaActual().getM_correr());
					Log.d("datos","gameManager.getMapaActual().getM_detonador()" + gameManager.getMapaActual().getM_detonador());
					Log.d("datos","gameManager.getMapaActual().getM_fantasma() " + gameManager.getMapaActual().getM_fantasma());
					Log.d("datos","gameManager.getMapaActual().getM_fuerza() " + gameManager.getMapaActual().getM_fuerza());
					Log.d("datos","gameManager.getMapaActual().getM_potenciador() " + gameManager.getMapaActual().getM_potenciador());
					Log.d("datos","gameManager.getMapaActual().getM_sorpresa()" + gameManager.getMapaActual().getM_sorpresa());
					Log.d("datos","gameManager.getMapaActual().getNumeroMapa()" + gameManager.getMapaActual().getNumeroMapa());
					Log.d("datos","gameManager.getMonedero().getBoosterRestantes()" + gameManager.getMonedero().getBoosterRestantes());
					Log.d("datos","gameManager.getMonedero().dameNumeroRestantedeMoneda(TipoMoneda.MBOMBA)" + gameManager.getMonedero().dameNumeroRestantedeMoneda(TipoMoneda.MBOMBA));
					Log.d("datos","gameManager.getMonedero().dameNumeroRestantedeMoneda(TipoMoneda.MEXPLOSION)" + gameManager.getMonedero().dameNumeroRestantedeMoneda(TipoMoneda.MEXPLOSION));
					Log.d("datos","gameManager.getMonedero().dameNumeroRestantedeMoneda(TipoMoneda.MSORPRESA)" + gameManager.getMonedero().dameNumeroRestantedeMoneda(TipoMoneda.MSORPRESA));
					Log.d("datos","gameManager.getMonedero().getBoosterRestantes()" + gameManager.getMonedero().getBoosterRestantes());
					Log.d("datos","\n\n\n ");/*
					Log.d("datos"," "+ );
					
					
					
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );
					Log.d("datos"," "+ );*/

				}catch(Exception e){}
			}
		}));
		hud = new HUD();		
		hudDisplay = new HudDisplay(this,0, 0, resourManager.getHudTR());		
		hud.attachChild(hudDisplay);			
		
		gameManager = new GameManager(this);
		gameManager.setDisplay(hudDisplay);
		gameManager.precargaMapas();
		gameManager.cargarBomberMan();
		
		Sprite botonPause = new Sprite(0, 0, resourManager.getPauseTR(),this.getVertexBufferObjectManager()){
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (getEngine().getScene().equals(escenaJuego)){
					setVisible(true);
				}else{
					setVisible(false);
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!isVisible())return false;
				if(gameManager.getMenuSalida()!=null && gameManager.getMenuSalida().isVisible())return false;
				//System.out.println("botonPause pSceneTouchEvent" +pSceneTouchEvent.getAction());
				if (pSceneTouchEvent.getAction()==TouchEvent.ACTION_UP){
					deJuegoAmenu();
					//gameManager.getVentanaNivelCompletado().resultado(true, gameManager.getMapaActual().getBoostersIniciales()-gameManager.getMapaActual().getBoosterTotales(), gameManager.getMapaActual().getBoostersIniciales());
				}		
				return true;
			}
		};
		
		
		
		
		
		
		botonPause.setPosition((ConstantesResolucion.getCAMERA_WIDTH_MASTER())-(botonPause.getHeight())-20,1);
		botonPause.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		botonPause.setAlpha(0.5f);		
		hud.attachChild(botonPause);
		hud.registerTouchArea(botonPause);
	
		texto1 = new Text(0, 0, mFont, "                                                                            ",this.getVertexBufferObjectManager());
		
	
	
		btn_1 = new Sprite(0, 0, btn_1_TR,this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (menu.isPause())return false;
				if(!habilitaBotones)return false;
				//if (gameManager.isPause())return false;
				if (BomberGame.this.getEngine().getScene() == escenaMenu) {
					//System.out.println("X"+getX()+ "Y"+getY());
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
					return true;
				}

				if (pSceneTouchEvent.getAction() == pSceneTouchEvent.ACTION_DOWN) {
					apretarBoton(1, pSceneTouchEvent);				
				}	
				if (pSceneTouchEvent.getAction() == pSceneTouchEvent.ACTION_UP) {
					botonA=false;			
				}
				if (pSceneTouchEvent.getAction() == pSceneTouchEvent.ACTION_OUTSIDE) {
					botonA=false;			
				}
				return true;
			}
		};

		btn_2 = new Sprite(0, 0, btn_2_TR,this.getVertexBufferObjectManager()) {
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (menu.isPause())	return false;
				if(!habilitaBotones)return false;
				//if (gameManager.isPause())return false;
				if (BomberGame.this.getEngine().getScene() == escenaMenu) {
					//System.out.println("X"+getX()+ "Y"+getY());
					this.setPosition(pSceneTouchEvent.getX() - this.getWidth() / 2, pSceneTouchEvent.getY() - this.getHeight() / 2);
					return true;
				}
				
				if (pSceneTouchEvent.getAction() == 0) {
					//System.out.println("apretar");
					apretarBoton(2, pSceneTouchEvent);
					return false;// poner true para fuego automatico
				}
				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP) {
					//System.out.println("levantar");
					botonB=false;			
				}
				return true;
			}
		};
		
		
		float tn_1X = Preferencias.leerPreferenciasFloatResolucion("btn_1X");
		float tn_1Y = Preferencias.leerPreferenciasFloatResolucion("btn_1Y");
		float tn_1Size = Preferencias.leerPreferenciasFloatResolucion("btn_1Size");

		float tn_2X = Preferencias.leerPreferenciasFloatResolucion("btn_2X");
		float tn_2Y = Preferencias.leerPreferenciasFloatResolucion("btn_2Y");
		float tn_2Size = Preferencias.leerPreferenciasFloatResolucion("btn_2Size");

			
		System.out.println("btn_2_TR.getWidth()"+btn_2_TR.getWidth());

		int margen=0;
		
		if (tn_1X == -1)
			tn_1X = ConstantesResolucion.getCAMERA_WIDTH_MASTER()-2*255+85;
		if (tn_1Y == -1)
			tn_1Y = ConstantesResolucion.getBtn_Ay_MASTER();
		if (tn_1Size == -1)
			tn_1Size = ConstantesResolucion.getBtn_size_MASTER();

		if (tn_2X == -1)
			tn_2X = ConstantesResolucion.getCAMERA_WIDTH_MASTER()-255+30;
		if (tn_2Y == -1)
			tn_2Y = ConstantesResolucion.getBtn_By_MASTER();
		if (tn_2Size == -1)
			tn_2Size = ConstantesResolucion.getBtn_size_MASTER();
		
		btn_1.setPosition(tn_1X, tn_1Y);
		btn_2.setPosition(tn_2X, tn_2Y);
		
		

		
		//btn_1.setScale(ConstantesResolucion.getBtn_size_MASTER());
		//btn_2.setScale(ConstantesResolucion.getBtn_size_MASTER());		

		btn_1.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		btn_1.setAlpha(0.5f);
		btn_1.setScale(tn_1Size);
		hud.attachChild(btn_1);
		hud.registerTouchArea(btn_1);

		btn_2.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		btn_2.setAlpha(0.5f);
		btn_2.setScale(tn_1Size);
		hud.attachChild(btn_2);
		hud.registerTouchArea(btn_2);
		//hud.attachChild(texto1);
		


		
		

		
		
		controlTactil();

		this.mDigitalOnScreenControl.getControlBase().setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		this.mDigitalOnScreenControl.getControlBase().setAlpha(0.5f);
		// this.mDigitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
		float crucesaSize = Preferencias.leerPreferenciasFloatResolucion("crucesaSize");
		if (crucesaSize==-1){
			crucesaSize= ConstantesResolucion.getCruzeta_size_MASTER();
		}

		this.mDigitalOnScreenControl.getControlBase().setScale(crucesaSize);
		this.mDigitalOnScreenControl.getControlKnob().setScale(crucesaSize);
		this.mDigitalOnScreenControl.getControlKnob().setAlpha(0.5f);
		this.mDigitalOnScreenControl.refreshControlKnobPosition();

		hud.setChildScene(this.mDigitalOnScreenControl);

		this.mBoundChaseCamera.setBounds(0, gameManager.getTmxSuelo().getWidthScaled(), 0, gameManager.getTmxSuelo().getHeightScaled());
		this.mBoundChaseCamera.setBoundsEnabled(true);

		float zommFactor = Preferencias.leerPreferenciasFloatResolucion("zoom");
		if (zommFactor == -1)	zommFactor = ConstantesResolucion.getDefaultZoom_MASTER();
		this.mBoundChaseCamera.setZoomFactor(zommFactor);	

		updateHandler();
		escenaJuego.sortChildren();
		// poner color blanco de fondo
//		escenaJuego.setBackground(new ColorBackgroundModifier(255, 255, 255));//gles1
		escenaJuego.setBackground(new Background(255, 255, 255));//gles2
		escenaJuego.setBackgroundEnabled(true);
		
		
		paralaxSombraAI = new Sprite(-35, -10, this.mParallaxLayerSombra,this.getVertexBufferObjectManager());	
		//paralaxSombraAI.setPosition(70, 100);

		paralaxSombraAI.setZIndex(BomberGame.ZINDEX_PARRAS);
		escenaJuego.attachChild(paralaxSombraAI);

		paralaxAI = new Sprite(-45, -32, this.mParallaxLayer,this.getVertexBufferObjectManager());
		//paralaxAI.setScale(0.5f);
		//paralaxAI.setScaleCenter(0, 0);
		//paralaxAI.setPosition(100, 50);
		paralaxAI.setZIndex(BomberGame.ZINDEX_PARRAS + 1);
		escenaJuego.attachChild(paralaxAI);

		paralaxSombraAD = new Sprite((25*45)-250, 5, this.mParallaxLayerSombraAD,this.getVertexBufferObjectManager());
		paralaxSombraAD.setScale(1.5f);
		//paralaxSombraAD.setPosition((gameManager.getTmxSuelo().getWidth() - paralaxSombraAD.getWidthScaled() / 2) - 150, 80);
		paralaxSombraAD.setFlippedHorizontal(true);
		paralaxSombraAD.setZIndex(BomberGame.ZINDEX_PARRAS);
		escenaJuego.attachChild(paralaxSombraAD);

		paralaxAD = new Sprite((25*45)-270, 10, this.mParallaxLayerAD,this.getVertexBufferObjectManager());
		paralaxAD.setScale(1.5f);
		paralaxAD.setFlippedHorizontal(true);
		//paralaxAD.setPosition(gameManager.getTmxSuelo().getWidth() - paralaxAD.getWidthScaled() / 2, 50);
		paralaxAD.setZIndex(BomberGame.ZINDEX_PARRAS + 1);
		escenaJuego.attachChild(paralaxAD);

		paralaxSombraBD = new Sprite(700-60, 250+117+50, this.mParallaxLayerSombraBD,this.getVertexBufferObjectManager());
		//paralaxSombraBD.setScale(2.5f);
		//paralaxSombraBD.setPosition((gameManager.getTmxSuelo().getWidth() - paralaxSombraBD.getWidthScaled() / 2) - 100, gameManager.getTmxSuelo().getHeight() - 240);
		paralaxSombraBD.setFlippedHorizontal(true);
		paralaxSombraBD.setFlippedVertical(true);
		paralaxSombraBD.setZIndex(BomberGame.ZINDEX_PARRAS);
		escenaJuego.attachChild(paralaxSombraBD);
		
		

		paralaxBD = new Sprite(700-60, 250-9, this.mParallaxLayerBD,this.getVertexBufferObjectManager());
		//paralaxBD.setScale(2.5f);		
		paralaxBD.setFlippedHorizontal(true);
		paralaxBD.setFlippedVertical(true);
		paralaxBD.setZIndex(BomberGame.ZINDEX_PARRAS + 1);
		//paralaxBD.setPosition((gameManager.getTmxSuelo().getWidth() - paralaxBD.getWidthScaled() / 2) - 80, gameManager.getTmxSuelo().getHeight() - 350);
		escenaJuego.attachChild(paralaxBD);

		paralaxSombraBI = new Sprite(-225, 250+120, this.mParallaxLayerSombraAI,this.getVertexBufferObjectManager());
		//paralaxSombraBI.setScale(2f);
		paralaxSombraBI.setZIndex(BomberGame.ZINDEX_PARRAS);
		//paralaxSombraBI.setPosition(70, gameManager.getTmxSuelo().getHeight() - 200);
		paralaxSombraBI.setFlippedVertical(true);
		escenaJuego.attachChild(paralaxSombraBI);
		
		
		escenaJuego.attachChild(texto1);
		
		paralaxBI = new Sprite(-245, 240, this.mParallaxLayerAI,this.getVertexBufferObjectManager());
		//paralaxBI.setScale(2f);
		paralaxBI.setFlippedVertical(true);
		paralaxBI.setZIndex(BomberGame.ZINDEX_PARRAS + 1);
		//paralaxBI.setPosition(70, gameManager.getTmxSuelo().getHeight() - 300);
		escenaJuego.attachChild(paralaxBI);
		
		

		// hacemos invisibles
		//paralaxAI.setVisible(false);
		//paralaxSombraAI.setVisible(false);
		
		//paralaxBI.setVisible(false);
		//paralaxSombraBI.setVisible(false);
		
		//paralaxBD.setVisible(false);
		//paralaxSombraBD.setVisible(false);
		
		//paralaxAD.setVisible(false);
		//paralaxSombraAD.setVisible(false);
		
		/*
		System.out.println("LLLLLLLLLLLLLLLLLLLLLLLLLLLLL");
		System.out.println("paralaxSombraAI "+paralaxSombraAI.getX() +"  "+paralaxSombraAI.getY());
		System.out.println("paralaxAI "+paralaxAI.getX() +"  "+paralaxAI.getY());
		System.out.println("paralaxSombraAD "+paralaxSombraAD.getX() +"  "+paralaxSombraAD.getY());
		System.out.println("paralaxAD "+paralaxAD.getX() +"  "+paralaxAD.getY());
		System.out.println("paralaxSombraBD "+paralaxSombraBD.getX() +"  "+paralaxSombraBD.getY());
		System.out.println("paralaxBD "+paralaxBD.getX() +"  "+paralaxBD.getY());
		System.out.println("paralaxSombraBI "+paralaxSombraBI.getX() +"  "+paralaxSombraBI.getY());
		System.out.println("paralaxBI "+paralaxBI.getX() +"  "+paralaxBI.getY());
		*/
		/*
		 * aki qse pone para vet el current tile index
		 */

		//gameManager.getCurrentTileRectangle().setVisible(true);
		gameManager.getCurrentTileRectangle().setZIndex(100);
		escenaJuego.sortChildren();
		menu = new Menu(this);
		escenaMenu = menu.getScene();		
		getResouceManager().inicializaMusica();
		this.mBoundChaseCamera.setHUD(hud);
		texto1.setVisible(false);
		
		
		
		int veces =Preferencias.leerPreferenciasInt("veces");
		if (veces!=-1){
			veces++;
			Preferencias.guardarPrefenrencias("veces", veces);
		}
		
		if (Preferencias.leerPreferenciasInt("Votado")==-1 && Preferencias.leerPreferenciasInt("UltimoNivelPasado")>=4){
			if (veces==-1){
				mostrarDialogo();
			}else if (veces==2){
				mostrarDialogo();
			}
				
		}
		
		
		
		
		return escenaJuego;
	}
	
	
	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		setVibrar(true);		
		//inicio.onLoadComplete();
		carga.onLoadComplete();
		
		//getEngine().setScene(escenaJuego);		
		// cargamos superficies de control			
			
	}

	
	
	
	
	public void updateHandler() {

		getEngine().registerUpdateHandler(new IUpdateHandler() {
			public void reset() {
			}
			
			int camara=0;
			public void onUpdate(final float pSecondsElapsed) {


				float minX=getmBoundChaseCamera().getXMin();
				float maxX=getmBoundChaseCamera().getXMax();
				
				float maxY=getmBoundChaseCamera().getYMax();
				float minY=getmBoundChaseCamera().getYMin();
				
				
				
				
				maxX=1250-maxX;
				maxY=1125-maxY;
				
				
				//System.out.println(" Ymaxxx "+getmBoundChaseCamera().getMaxY());
				//System.out.println(" Yminn "+getmBoundChaseCamera().getMinY());
				
				//System.out.println(" Xmaxxx "+getmBoundChaseCamera().getMaxX());
				//System.out.println(" Xminn "+getmBoundChaseCamera().getMinX());
				
				
				float desplazamientoY=getmBoundChaseCamera().getYMin();
				
				if (getEngine().getScene()==escenaJuego || getEngine().getScene()==escenaMenu){
					if (camara!=1){
						MiEngine en= (MiEngine) getEngine();
						en.selCamera(1);
						camara=1;
						//System.out.println("cambio Camara a 1");
					}
				}else{
					if (camara!=2){
						MiEngine en= (MiEngine) getEngine();
						en.selCamera(2);
						camara=2;
						//System.out.println("cambio Camara a 2");
					}

				}
				
				
				
				try {				
					
					
					paralaxAI.setPosition(paralaxAI.getX()-minX/4, paralaxAI.getY()-desplazamientoY/4);
					paralaxAD.setPosition(paralaxAD.getX()+maxX/4, paralaxAD.getY()-desplazamientoY/4);
					paralaxBI.setPosition(paralaxBI.getX()-minX/4, paralaxBI.getY()+maxY/4);
					paralaxBD.setPosition(paralaxBD.getX()+maxX/4, paralaxBI.getY()+maxY/4);					

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});

	}

	private PlayerDirection playerDirection = PlayerDirection.NONE;
	
	public PlayerDirection getPlayerDirection(){
		synchronized (this.playerDirection) {
			return playerDirection;
		} 			
	}
	
	public void setPlayerDirection(PlayerDirection playerDirection){
		synchronized (this.playerDirection) {
			this.playerDirection=playerDirection;
		} 
	}
	
	
	public void controlTactil() {

		float crucesaX = Preferencias.leerPreferenciasFloatResolucion("crucetaX");
		float crucesaY = Preferencias.leerPreferenciasFloatResolucion("crucesaY");
		if (crucesaX == -1)	crucesaX = ConstantesResolucion.getCruzetaX_MASTER();
		if (crucesaY == -1)	crucesaY = ConstantesResolucion.getCruzetaY_MASTER();

		this.mDigitalOnScreenControl = new DigitalOnScreenControl(crucesaX, crucesaY, this.mBoundChaseCamera, this.mOnScreenControlBaseTextureRegion, this.mOnScreenControlKnobTextureRegion, 0.1f,this.getVertexBufferObjectManager(), new IOnScreenControlListener() {

			public void onControlChange(final BaseOnScreenControl pBaseOnScreenControl, final float pValueX, final float pValueY) {

				if (pValueY == 1 && getPlayerDirection() != PlayerDirection.DOWN) {					
					setPlayerDirection(PlayerDirection.DOWN);
					// Down
					//bomber.moverAbajo();
					//vibrator.vibrate(50);
					vibrar(50);
				} else if (pValueY == -1 && getPlayerDirection() != PlayerDirection.UP) {
					setPlayerDirection(PlayerDirection.UP);
					// Up
					//bomber.moverArriba();
					//vibrator.vibrate(50);
					vibrar(50);
				} else if (pValueX == -1 && getPlayerDirection() != PlayerDirection.LEFT) {
					setPlayerDirection(PlayerDirection.LEFT);
					// Left
					//bomber.moverIzquierda();
					//vibrator.vibrate(50);
					vibrar(50);

				} else if (pValueX == 1 && getPlayerDirection() != PlayerDirection.RIGHT) {
					setPlayerDirection(PlayerDirection.RIGHT);
					// Right
					//bomber.moverDerecha();
					//vibrator.vibrate(50);
					vibrar(50);
				} else if (pValueX == 0 && pValueY == 0 && getPlayerDirection() != PlayerDirection.NONE) {
					setPlayerDirection(PlayerDirection.NONE);
					// parado
					//bomber.pararMovimiento();
					//vibrator.vibrate(50);
					vibrar(50);
				}

			}
		}) {

			@Override
			protected void onUpdateControlKnob(final float pRelativeX, final float pRelativeY) {				
				float sensibilidad=0.2f;
				// cuanto pRelativeX > 0.2 mayor sea el numero menos sensible sera
				if (pRelativeX == 0 && pRelativeY == 0) {
					super.onUpdateControlKnob(0, 0);
				}
				//System.out.println("pRelativeX"+pRelativeX);

				if (Math.abs(pRelativeX) > Math.abs(pRelativeY)) {
					
					if (pRelativeX > sensibilidad) {
						super.onUpdateControlKnob(0.5f, 0);
					} else if (pRelativeX < -sensibilidad) {
						super.onUpdateControlKnob(-0.5f, 0);
					} else {
						super.onUpdateControlKnob(0, 0);
					}
				} else {
					if (pRelativeY > sensibilidad) {
						super.onUpdateControlKnob(0, 0.5f);
					} else if (pRelativeY < -sensibilidad) {
						super.onUpdateControlKnob(0, -0.5f);
					} else {
						super.onUpdateControlKnob(0, 0);
					}
				}
			}

			protected boolean onHandleControlBaseTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
			
				if (BomberGame.this.getEngine().getScene() == escenaMenu) {					
					if (menu.isPause())	return false;
					//System.out.println("X"+getX()+ "Y"+getY());
					getControlBase().setPosition(pSceneTouchEvent.getX() - getControlBase().getHeight() / 2, pSceneTouchEvent.getY() - getControlBase().getWidth() / 2);
					refreshControlKnobPosition();

				} else {
					if (menu.isPause())	return false;
					if(!habilitaBotones)return false;
					if (gameManager.isPause())return false;
					super.onHandleControlBaseTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);

				}
				return true;
			}

		};		
	}

	public void tostar(final String texto) {
		final BaseGameActivity menu = this;

		this.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(menu, texto, Toast.LENGTH_SHORT).show();
			}
		});

	}

	
	
	
	
	public void VerMenuMapas(){
		gameManager.clearMapa();
		escenaJuego.onUpdate(0.02f);
		gameManager.pararCuentaAtras();
		getEngine().setScene(inicio.getEscenaMapas());
		getResouceManager().stopMusica();
	}
	
	

	
	
	/*
	public void siguienteMapa(){
		gameManager.clearMapa();
		escenaJuego.onUpdate(0.02f);	
		gameManager.cargaNivel(gameManager);
	}
	*/
	
	
	boolean juego=true;
	boolean habilitaBotones=true;
	public void habilitaBotones(boolean enable){
		habilitaBotones=enable;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {	
		
		try {// eviar que se cuelque si presionas una tecla antes de que cargue
			if (!habilitaBotones)return false;
			if (gameManager.getMenuSalida().isVisible())return false;
			if (menu.isPause())	return false;
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:

				if (getEngine().getScene() == inicio.getEscenaMapas()) {
					inicio.inicioNormal();
					getEngine().setScene(escenaInicio);
					
				} else if (getEngine().getScene() == escenaJuego) {
					getGameManager().pause();
					getGameManager().getMenuSalida().setVisible(true);			
				
				} else  if (getEngine().getScene() == escenaMenu) {		
					
					if (getGameManager().isModoEntrenamiento()){
						getGameManager().setModoEntrenamiento(false);
						gameManager.clearMapa();
						escenaJuego.onUpdate(0.02f);
						getEngine().setScene(inicio.getEscenaMapas());
						getResouceManager().stopMusica();						
						inicio.cargaMenuCampOentrenamiento();
						getEngine().setScene(escenaInicio);
						getGameManager().setSalirInteligencia(true);
						menu.botonesVisibles(false);
					}else{
						menu.botonesVisibles(false);
						VerMenuMapas();
					}
					
				}else{
					finish();
				}
				break;

			case KeyEvent.KEYCODE_MENU:

				if (getEngine().getScene() == escenaMenu) {
					deMenuAjuego();
				} else if (getEngine().getScene() == escenaJuego) {
					deJuegoAmenu();
				}

				break;

			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	public void deJuegoAmenu(){
		getEngine().setScene(escenaMenu);
		gameManager.pause();
		menu.botonesVisibles(true);
	}
	public void deMenuAjuego(){
		menu.botonesVisibles(false);
		menu.guardarPosiciones();
		getEngine().setScene(escenaJuego);
		gameManager.play();
	}
	
	
	
	@Override
	public void onResumeGame() {
		// TODO Auto-generated method stub
		super.onResumeGame();
	}
	
	public void apretarBoton(int boton, TouchEvent pSceneTouchEvent) {
		
		switch (boton) {
		case 2:
			
			botonA = false;
			botonB = true;
			vibrar(50);
			gameManager.getPolvorin().ControlRemotoDetonar();			
			break;

		case 1:
			
			botonA = true;
			botonB = false;
			
			if (!gameManager.getPolvorin().plantaBomba()){
				vibrar(50);
			}
			
			break;
		}
	}
	
	public void soltarBoton(){
		
	}
	
	
	
	public void selectorScenas(String escena){
		
	}
	
	
	
	
	@Override
	protected void onPause() {		
		//tostar("pause");
		System.exit(0);
		//if(getResouceManager()!=null && getResouceManager().getMusica1()!=null && getResouceManager().isSonido()){
		//	//getResouceManager().getMusica1().pause();				
		//}
		super.onPause();
			
	}

	@Override
	protected synchronized void onResume() {
		//tostar("onResume");
		//System.gc();
		//if(getResouceManager()!=null && getResouceManager().getMusica1()!=null && getResouceManager().isSonido()){
			//getResouceManager().getMusica1().play();		
		//}
		super.onResume();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void setEscenaInicio() {
		getEngine().setScene(escenaInicio);
	}

	public boolean isBotonA() {
		return botonA;
	}

	public float alineacionCentradoHorizontal(float ancho) {
		return (getEngine().getSurfaceWidth() / 2) - (ancho / 2);
	}

	public float alineacionCentradoVertical(float alto){
		return (getEngine().getSurfaceHeight()/2)-(alto/2);
	}
	
	public float alineacionInteriorDerecha(float anchoPadre, float anchoHijo){
		return (anchoPadre-anchoHijo);
	}
	
	public float alineacionCentradoHorizontalRelativo(float anchoPadre, float ancho){
		return (anchoPadre/2)-(ancho/2);
	}
	
}
