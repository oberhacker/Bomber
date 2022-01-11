package xnetcom.bomber.menus;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.ResouceManager;
import xnetcom.bomber.inicio.Inicio;
import xnetcom.bomber.preferences.Preferencias;
import xnetcom.bomber.util.ConstantesResolucion;

public class Menu {

		private float zoomMax=0;
	private float zoomMax_1=2f;
	private float zoomMax_2=0;
	
	
	private float zoomMin=0;
	private float zoomMin_1=1.23f;
	private float zoomMin_2=0;	
	
	private Sprite back;
	private BomberGame context;
	private Sprite cruceta_mas,cruceta_menos, restore;
	private Sprite btn_1_mas,btn_1_menos;
	private Sprite zoom_mas,zoom_menos;
	private Sprite music_mas, music_menos, sound_mas,sound_menos;
	
	private TiledSprite ticSpr;
	
	private ResouceManager rm;
	
	
	private Scene scene;
	
	private Text texto1;
	private Text texto2;
	private Text texto3;
	private Text texto4;
	private Text texto5;
	private Text texto6;
	
	private String stexto4=" Music level:";
	private String stexto5=" Sound level:";
	
	private int sound_level;
	private int music_level;
	
	private boolean pause=false;
	
	public boolean isPause() {
		return pause;
	}
	public Menu(final BomberGame context){
		scene = new Scene();
		resample();
		
		this.context=context;
		rm =context.getResouceManager();
		texto1 = new Text(0, 0, context.getmFont(), "Control size:",context.getVertexBufferObjectManager());
		texto2 = new Text(0, 0, context.getmFont(), "Buttons size:",context.getVertexBufferObjectManager());
		texto3 = new Text(0, 0, context.getmFont(), "Zoom:",context.getVertexBufferObjectManager());
		texto4 = new Text(0, 0, context.getmFont(), " Music level:00",context.getVertexBufferObjectManager());
		texto5 = new Text(0, 0, context.getmFont(), " Sound level:00",context.getVertexBufferObjectManager());
		texto6 = new Text(0, 0, context.getmFont(), " Vibration: ",context.getVertexBufferObjectManager());
		
		sound_level =Preferencias.leerPreferenciasInt("sound_level");
		music_level =Preferencias.leerPreferenciasInt("music_level");
		if (sound_level==-1){
			Preferencias.guardarPrefenrencias("sound_level", 5);
			sound_level=5;
			Preferencias.guardarPrefenrencias("music_level", 5);
			music_level=5;			
		}
		
		texto4.setText(stexto4+music_level);
		texto5.setText(stexto5+sound_level);		

		Float offset = (float) (((ConstantesResolucion.CAMERA_WIDTH_16_9_M-ConstantesResolucion.getCAMERA_WIDTH_MASTER()))/2);
		
		back=new Sprite(-offset,0,rm.getFondoMenuTR(),context.getVertexBufferObjectManager());
		
	
		
		scene.setBackground(new SpriteBackground(back));
		
		
		
		
		Sprite botonPlay = new Sprite(0, 0, context.getResouceManager().getPlayTR(),context.getVertexBufferObjectManager()){
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (context.getEngine().getScene().equals(context.getEscenaMenu())){
					setVisible(true);
				}else{
					setVisible(false);
				}
				
			}
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!isVisible())return false;
				System.out.println("botonPlay pSceneTouchEvent" +pSceneTouchEvent.getAction());
				if (pSceneTouchEvent.getAction()==TouchEvent.ACTION_UP){
					context.deMenuAjuego();
				}		
				return true;
			}
		};	
		
		botonPlay.setPosition(offset+(ConstantesResolucion.getCAMERA_WIDTH_MASTER())-(botonPlay.getHeight())-20,1);
		back.attachChild(botonPlay);
		context.getHud().registerTouchArea(botonPlay);
		
		
		
		Sprite botonmenu = new Sprite(0, 0, context.getResouceManager().getMenuTR(),context.getVertexBufferObjectManager()){
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				if (context.getEngine().getScene().equals(context.getEscenaMenu())){
					setVisible(true);
				}else{
					setVisible(false);
				}
				super.onManagedUpdate(pSecondsElapsed);
			}
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (!isVisible())return false;
				if (pSceneTouchEvent.getAction()==TouchEvent.ACTION_UP){
					context.VerMenuMapas();
					botonesVisibles(false);
				}		
				return true;
			}
		};	
		
		botonmenu.setPosition(offset+(ConstantesResolucion.getCAMERA_WIDTH_MASTER())-(botonmenu.getHeight())-(botonmenu.getHeight())-40,1);
		back.attachChild(botonmenu);
		context.getHud().registerTouchArea(botonmenu);
		
		
		
		
		restore = new Sprite(0, 610, rm.getRestoreTR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0){
					System.out.println("restoreeee");
					resetear();
					//context.getGameManager().nivelSuperado();
				}
				return false;
			}
		};
		
		restore.setPosition((ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2)-(restore.getWidth()/2), restore.getY());	
		
		
		
		ticSpr = new TiledSprite(0, 0, rm.getTicTR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {				
				if (pSceneTouchEvent.getAction()==TouchEvent.ACTION_UP ){
					if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
					if (getCurrentTileIndex()==1){
						setCurrentTileIndex(0);					
						context.setVibrar(true);	
						Preferencias.guardarPrefenrencias("Vibracion", "true");
					}else{
						setCurrentTileIndex(1);
						context.setVibrar(false);		
						Preferencias.guardarPrefenrencias("Vibracion", "false");
					}
					
					
				}			
				return false;
			}
		};
		
		
		
		
		String vibrar =Preferencias.leerPreferencias("Vibracion");
		
		if (vibrar!=null ){
			
			if (vibrar.equalsIgnoreCase("true")){
				ticSpr.setCurrentTileIndex(0);
			}else{
				ticSpr.setCurrentTileIndex(1);
				context.setVibrar(false);
			}
		}else{
			Preferencias.guardarPrefenrencias("Vibracion", "true");
			ticSpr.setCurrentTileIndex(0);
		}
		
		
		
		btn_1_mas = new Sprite(ConstantesResolucion.getBotones_mas_X_MASTER(), ConstantesResolucion.getBotones_mas_Y_MASTER(), rm.getMasTR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0 && Menu.this.context.getBtn_1().getScaleX()<1.2f){
					Menu.this.context.getBtn_1().setScale(Menu.this.context.getBtn_1().getScaleX()+0.1f);
					Menu.this.context.getBtn_2().setScale(Menu.this.context.getBtn_2().getScaleX()+0.1f);
				}

				return false;
				/*setPosition(pSceneTouchEvent.getX()-getHeight()/2, pSceneTouchEvent.getY()-getWidth()/2);
				System.out.println(getX()+" "+getY());
				return true;*/
			}
		};
		//btn_1_mas.setScale(ConstantesResolucion.getBtnMenu_Size_MASTER());

		
		cruceta_mas = new Sprite(ConstantesResolucion.getBtnCruzeta_mas_X_MASTER(), ConstantesResolucion.getBtnCruzeta_mas_Y_MASTER(), rm.getMasTR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				//cruceta.setScale(cruceta.getScaleX()+0.2f);
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0 && context.getmDigitalOnScreenControl().getControlBase().getScaleX()<3.3f){
					context.getmDigitalOnScreenControl().getControlBase().setScale(context.getmDigitalOnScreenControl().getControlBase().getScaleX()+0.2f);
					context.getmDigitalOnScreenControl().getControlKnob().setScale(context.getmDigitalOnScreenControl().getControlKnob().getScaleX()+0.2f);
					context.getmDigitalOnScreenControl().refreshControlKnobPosition();
				}				

				return false;
				
			}
		};
		
		//cruceta_mas.setScale(ConstantesResolucion.getBtnMenu_Size_MASTER());
		
		btn_1_menos = new Sprite(ConstantesResolucion.getBotones_menos_X_MASTER(), ConstantesResolucion.getBotones_menos_Y_MASTER(), rm.getMenosTR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0 && Menu.this.context.getBtn_1().getScaleX()>0.3f){
					Menu.this.context.getBtn_1().setScale(Menu.this.context.getBtn_1().getScaleX()-0.1f);
					Menu.this.context.getBtn_2().setScale(Menu.this.context.getBtn_2().getScaleX()-0.1f);
				}				
				return false;
			}
		};
		
		//btn_1_menos.setScale(ConstantesResolucion.getBtnMenu_Size_MASTER());
		
		zoom_mas = new Sprite(ConstantesResolucion.getBtnZoom_mas_X_MASTER(), ConstantesResolucion.getBtnZoom_mas_Y_MASTER(), rm.getMasTR(),context.getVertexBufferObjectManager()){
			
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0){
					System.out.println("Zomm "+(context.getmBoundChaseCamera().getZoomFactor()+0.2f));
					float zoom=context.getmBoundChaseCamera().getZoomFactor()+0.2f;		
					
					if(zoom>=zoomMax){
						zoom=zoomMax;
					}
					
					System.out.println("Zomm m "+zoom);
					context.getmBoundChaseCamera().setZoomFactor(zoom);	
					
					zoom_mas.clearEntityModifiers();
					zoom_menos.clearEntityModifiers();
					registerEntityModifier( new MoveYModifier(1f, getY(), getY()){
						protected void onModifierStarted(IEntity pItem) {			
							pause=true;
							//context.getGameManager().pause();
							context.getEngine().setScene(context.getEscenaJuego());
							System.out.println("start");};
						protected void onModifierFinished(IEntity pItem) {
							pause=false;
							//context.getGameManager().play();
							context.getEngine().setScene(context.getEscenaMenu());						
							System.out.println("end");};
					});
				}				
				return false;
			}
		};
		
		//zoom_mas.setScale(ConstantesResolucion.getBtnMenu_Size_MASTER());
		
		zoom_menos = new Sprite(ConstantesResolucion.getBtnZoom_menos_X_MASTER(), ConstantesResolucion.getBtnZoom_menos_Y_MASTER(), rm.getMenosTR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0){
					System.out.println("Zomm "+(context.getmBoundChaseCamera().getZoomFactor()-0.2f));
					float zoom=context.getmBoundChaseCamera().getZoomFactor()-0.2f;
					
					if(zoom<=zoomMin){
						zoom=zoomMin;
					}
					System.out.println("Zomm m "+zoom);
					context.getmBoundChaseCamera().setZoomFactor(zoom);
					zoom_mas.clearEntityModifiers();
					zoom_menos.clearEntityModifiers();
					registerEntityModifier( new MoveYModifier(1f, getY(), getY()){
						protected void onModifierStarted(IEntity pItem) {	
							pause=true;
							//context.getGameManager().pause();
							context.getEngine().setScene(context.getEscenaJuego());
							System.out.println("start");
							};
						protected void onModifierFinished(IEntity pItem) {
							pause=false;
							context.getEngine().setScene(context.getEscenaMenu());		
							//context.getGameManager().play();
							System.out.println("end");};
					});
				}
				
				return false;
			}
		};
		
		//zoom_menos.setScale(ConstantesResolucion.getBtnMenu_Size_MASTER());
		

		cruceta_menos = new Sprite(ConstantesResolucion.getBtnCruzeta_menos_X_MASTER(), ConstantesResolucion.getBtnCruzeta_menos_Y_MASTER(), rm.getMenosTR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0 && context.getmDigitalOnScreenControl().getControlBase().getScaleX()>1.0f ){
					//cruceta.setScale(cruceta.getScaleX()-0.2f);
					context.getmDigitalOnScreenControl().getControlBase().setScale(context.getmDigitalOnScreenControl().getControlBase().getScaleX()-0.2f);
					context.getmDigitalOnScreenControl().getControlKnob().setScale(context.getmDigitalOnScreenControl().getControlKnob().getScaleX()-0.2f);
					context.getmDigitalOnScreenControl().refreshControlKnobPosition();
				}
				return false;				
			}
			
		};
		
		
		//texto4.setText(stexto4+music_level);
		//texto5.setText(stexto5+sound_level);
		
		sound_menos=new Sprite(550,360 , rm.getMenosTR(),context.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0){
					if (sound_level>0){
						sound_level--;
						Preferencias.guardarPrefenrencias("sound_level", sound_level);
						texto5.setText(stexto5+sound_level);
						rm.setVolumenSonido();
					}
				}
				return false;
			}
		};
		
		
		sound_mas=new Sprite(640,360 , rm.getMasTR(),context.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0){
					if (sound_level<10){
						sound_level++;
						Preferencias.guardarPrefenrencias("sound_level", sound_level);
						texto5.setText(stexto5+sound_level);
						rm.setVolumenSonido();
					}
				}
				return false;
			}
		};
		music_mas=new Sprite(840,360 , rm.getMasTR(),context.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0){
					if (music_level<10){
						music_level++;
						Preferencias.guardarPrefenrencias("music_level", music_level);
						texto4.setText(stexto4+music_level);
						rm.setVolumenMusica();
					}
				}
				return false;
			}
		};
		music_menos=new Sprite(750,360 , rm.getMenosTR(),context.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pause)return false;
				if (context.getEngine().getScene()!=context.getEscenaMenu())return false;
				if(pSceneTouchEvent.getAction()==0){
					if (music_level>0){
						music_level--;
						Preferencias.guardarPrefenrencias("music_level", music_level);
						texto4.setText(stexto4+music_level);
						rm.setVolumenMusica();
					}
				}
				return false;
			}
		};
		
		
		
		
		int subida=8;
		int bajada=8;
		
		texto1.setPosition(offset+20, 170+bajada);  cruceta_mas.setPosition(offset+texto1.getWidth()+30, 170-subida); cruceta_menos.setPosition(offset+360, 170-subida);
		texto2.setPosition(offset+20, 255+bajada); btn_1_mas.setPosition(offset+texto1.getWidth()+30, 255-subida); btn_1_menos.setPosition(offset+360, 255-subida);
		texto3.setPosition(offset+20, 340+bajada); zoom_mas.setPosition(offset+texto3.getWidth()+30, 340-subida); zoom_menos.setPosition(zoom_mas.getX()+zoom_mas.getWidth()+10, 340-subida);
		
		texto4.setPosition(offset-5+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-2*music_mas.getWidthScaled()-20-texto4.getWidth()-40, 170+bajada); music_mas.setPosition(offset+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-2*music_mas.getWidthScaled()-20, 170-subida); music_menos.setPosition(offset+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-music_mas.getWidthScaled()-10, 170-subida);
		texto5.setPosition(offset-5+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-2*music_mas.getWidthScaled()-20-texto4.getWidth()-40, 255+bajada);  sound_mas.setPosition(offset+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-2*music_mas.getWidthScaled()-20, 255-subida); sound_menos.setPosition(offset+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-music_mas.getWidthScaled()-10, 255-subida);
		texto6.setPosition(offset+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-2*music_mas.getWidthScaled()-20-texto4.getWidth()-40, 340+bajada); ticSpr.setPosition(offset+ConstantesResolucion.getCAMERA_WIDTH_MASTER()-2*music_mas.getWidthScaled()-20, 340-subida);
		
		

		
		
		//cruceta_menos.setScale(ConstantesResolucion.getBtnMenu_Size_MASTER());
		
		texto1.setVisible(false);
		texto2.setVisible(false);
		texto3.setVisible(false);
		texto4.setVisible(false);
		texto5.setVisible(false);
		
		sound_menos.setVisible(false);
		music_menos.setVisible(false);
		sound_mas.setVisible(false);
		music_mas.setVisible(false);
		
		restore.setVisible(false);
		btn_1_mas.setVisible(false);
		zoom_mas.setVisible(false);
		cruceta_mas.setVisible(false);
		btn_1_menos.setVisible(false);
		zoom_menos.setVisible(false);
		cruceta_menos.setVisible(false);
		
		
		
		back.attachChild(ticSpr);
		
		back.attachChild(texto1);
		back.attachChild(texto2);
		back.attachChild(texto3);
		back.attachChild(texto4);
		back.attachChild(texto5);
		back.attachChild(texto6);
		
		back.attachChild(btn_1_mas);
		context.getHud().attachChild(zoom_mas);
		back.attachChild(cruceta_mas);
		back.attachChild(btn_1_menos);
		context.getHud().attachChild(zoom_menos);
		back.attachChild(cruceta_menos);
		back.attachChild(restore);
		
		back.attachChild(sound_menos);
		back.attachChild(music_menos);
		back.attachChild(sound_mas);
		back.attachChild(music_mas);
		
		context.getHud().registerTouchArea(ticSpr);
		
		context.getHud().registerTouchArea(sound_menos);
		context.getHud().registerTouchArea(music_menos);
		context.getHud().registerTouchArea(sound_mas);
		context.getHud().registerTouchArea(music_mas);
		
		context.getHud().registerTouchArea(restore);
		context.getHud().registerTouchArea(btn_1_mas);
		context.getHud().registerTouchArea(zoom_mas);
		context.getHud().registerTouchArea(cruceta_mas);
		context.getHud().registerTouchArea(btn_1_menos);
		context.getHud().registerTouchArea(zoom_menos);
		context.getHud().registerTouchArea(cruceta_menos);
	}
	
	
	
	
	public Scene getScene() {
		return scene;
	}

	
	public void cargarPosiciones(){
		try{		
			float zoom =Preferencias.leerPreferenciasFloatResolucion("zoom");
			float crucesaX =Preferencias.leerPreferenciasFloatResolucion("crucetaX");
			float crucesaY =Preferencias.leerPreferenciasFloatResolucion("crucesaY");
			float crucesaSize =Preferencias.leerPreferenciasFloatResolucion("crucesaSize");
			
			float btn_1X =Preferencias.leerPreferenciasFloatResolucion("btn_1X");
			float btn_1Y =Preferencias.leerPreferenciasFloatResolucion("btn_1Y");
			float btn_1Size =Preferencias.leerPreferenciasFloatResolucion("btn_1Size");
			
			float btn_2X =Preferencias.leerPreferenciasFloatResolucion("btn_2X");
			float btn_2Y =Preferencias.leerPreferenciasFloatResolucion("btn_2Y");
			float btn_2Size =Preferencias.leerPreferenciasFloatResolucion("btn_2Size");
			
			if (crucesaY!=-1){
				System.out.println("pos crucesa X distinto -1");
				//cruceta.setPosition(crucesaX, crucesaY);
			}else{
				System.out.println("pos crucesa X igual -1");
				//cruceta.setPosition(BomberGame.CrucetaX, BomberGame.CrucetaY);
			}
			if (crucesaSize!=-1){
				//cruceta.setScale(crucesaSize);
			}else{
				//cruceta.setScale(BomberGame.CrucetaSize);
			}
			
			if (btn_1Size!=-1){
				context.getBtn_1().setScale(btn_1Size);
			}else{
				context.getBtn_1().setScale(ConstantesResolucion.getBtn_size_MASTER());
			}
			if (btn_1Y!=-1){
				context.getBtn_1().setPosition(btn_1X, btn_1Y);
			}else{
				context.getBtn_1().setPosition(ConstantesResolucion.getBtn_Ax_MASTER(), ConstantesResolucion.getBtn_Ay_MASTER());
			}
			
			if (btn_2Size!=-1){
				context.getBtn_2().setScale(btn_2Size);
			}else{
				context.getBtn_2().setScale(ConstantesResolucion.getBtn_size_MASTER());
			}
			if (btn_2Y!=-1){
				context.getBtn_2().setPosition(btn_2X, btn_2Y);
			}else{
				context.getBtn_2().setPosition(ConstantesResolucion.getBtn_Bx_MASTER(), ConstantesResolucion.getBtn_By_MASTER());
			}
			
			if (zoom !=-1){
				context.getmBoundChaseCamera().setZoomFactor(ConstantesResolucion.getDefaultZoom_MASTER());
			}
			
		
		}catch (Exception e) {
			System.err.println(e.getMessage());
		}
	}
	
	public void guardarPosiciones(){
		System.out.println("guardarposiciones");
		System.out.println("cargartexturassssssssssss2");
		float crucesaX=context.getmDigitalOnScreenControl().getControlBase().getX();
		float crucesaY=context.getmDigitalOnScreenControl().getControlBase().getY();
		float crucesaSize=context.getmDigitalOnScreenControl().getControlBase().getScaleX();
		
		float btn_1X=context.getBtn_1().getX();
		float btn_1Y=context.getBtn_1().getY();
		float btn_1Size=context.getBtn_1().getScaleX();
		
		float btn_2X=context.getBtn_2().getX();
		float btn_2Y=context.getBtn_2().getY();
		float btn_2Size=context.getBtn_2().getScaleX();
		Preferencias.guardarPrefenrenciasResolucion("crucetaX", crucesaX);
		Preferencias.guardarPrefenrenciasResolucion("crucesaY", crucesaY);
		
		Preferencias.guardarPrefenrenciasResolucion("crucesaSize", crucesaSize);
		Preferencias.guardarPrefenrenciasResolucion("btn_1X", btn_1X);
		Preferencias.guardarPrefenrenciasResolucion("btn_1Y", btn_1Y);
		Preferencias.guardarPrefenrenciasResolucion("btn_1Size", btn_1Size);
		Preferencias.guardarPrefenrenciasResolucion("btn_2X", btn_2X);
		Preferencias.guardarPrefenrenciasResolucion("btn_2Y", btn_2Y);
		Preferencias.guardarPrefenrenciasResolucion("btn_2Size", btn_2Size);
		
		context.getmDigitalOnScreenControl().getControlBase().setPosition(crucesaX, crucesaY);
		context.getmDigitalOnScreenControl().getControlKnob().setScale(crucesaSize);
		context.getmDigitalOnScreenControl().getControlBase().setScale(crucesaSize);
		context.getmDigitalOnScreenControl().refreshControlKnobPosition();
		
		
		Preferencias.guardarPrefenrenciasResolucion("zoom", context.getmBoundChaseCamera().getZoomFactor());
		
		System.out.println("crucetaX"+ crucesaX);
		System.out.println("crucesaY"+ crucesaY);
		System.out.println("btn_1X"+ btn_1X);
		System.out.println("btn_1Y"+ btn_1Y);
		System.out.println("btn_1Size"+btn_1Size);
		System.out.println("btn_2X"+ btn_2X);
		System.out.println("btn_2Y"+ btn_2Y);
		System.out.println("crucesaSize"+ crucesaSize);
		System.out.println();
	}
	
	
	
	public void resample(){
		switch (Inicio.RESOLUCION) {
		case 1:
			zoomMax=zoomMax_1;
			zoomMin=zoomMin_1;
			break;

		case 2:
			zoomMax=zoomMax_2;
			zoomMin=zoomMin_2;
			break;
		}
	}
	
	public void botonesVisibles(boolean visible){	
				texto1.setVisible(visible);
				texto2.setVisible(visible);
				texto3.setVisible(visible);
				texto4.setVisible(visible);
				texto5.setVisible(visible);
				restore.setVisible(visible);
				btn_1_mas.setVisible(visible);
				
				sound_menos.setVisible(visible);
				sound_mas.setVisible(visible);
				music_menos.setVisible(visible);
				music_mas.setVisible(visible);
				
				
				zoom_mas.setVisible(visible);
				cruceta_mas.setVisible(visible);
				btn_1_menos.setVisible(visible);
				zoom_menos.setVisible(visible);
				cruceta_menos.setVisible(visible);		
	}
	
	public void resetear(){					
		
		context.getmDigitalOnScreenControl().getControlBase().setScale(ConstantesResolucion.getCruzeta_size_MASTER());
		context.getmDigitalOnScreenControl().getControlKnob().setScale(ConstantesResolucion.getCruzeta_size_MASTER());
		context.getmDigitalOnScreenControl().getControlBase().setPosition(ConstantesResolucion.getCruzetaX_MASTER(), ConstantesResolucion.getCruzetaY_MASTER());		
		context.getmDigitalOnScreenControl().refreshControlKnobPosition();
		
		context.getBtn_1().setPosition(ConstantesResolucion.getCAMERA_WIDTH_MASTER()-2*255+85,ConstantesResolucion.getBtn_Ay_MASTER());
		context.getBtn_1().setScale(ConstantesResolucion.getBtn_size_MASTER());
		context.getBtn_2().setPosition(ConstantesResolucion.getCAMERA_WIDTH_MASTER()-255+30, ConstantesResolucion.getBtn_By_MASTER());
		context.getBtn_2().setScale(ConstantesResolucion.getBtn_size_MASTER());
	}
}
