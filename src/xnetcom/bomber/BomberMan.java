package xnetcom.bomber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.MoveModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.util.modifier.IModifier;

import xnetcom.bomber.entidades.Moneda.TipoMoneda;
import xnetcom.bomber.util.Coordenadas;
import android.util.Log;


public class BomberMan {
	
	private static long[] ANIMATE_DURATION = new long[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};
	
	
	private Boolean muerto=true;

	private Rectangle cuadrado; // es el que se mueve , bomber le sigue
	private Rectangle cuadradoMascara;
	//private Rectangle cuadradoMascara2;
	private BomberGame context;
	private AnimatedSprite  bomber;
	private AnimatedSprite  bomberB;
	private AnimatedSprite  aura;
	
	
	private TiledTextureRegion mBombermanTextureRegionAniA;
	private TiledTextureRegion mBombermanTextureRegionAniB;
	private final Semaphore semaforo2 = new Semaphore(1, true);
	public boolean animacionTope=false;	
	
	private boolean boosterActivado=false;
	private boolean velocidad=false;
	private boolean invencible=false;	
	private boolean fantasma=false;
	
	public boolean isInvencible() {
		return invencible;
	}

	public void boosterInvencible(boolean invencible) {
		this.invencible = invencible;	
		boosterActivado=invencible;
		aura.setVisible(invencible);	
		aura.setIgnoreUpdate(!invencible);
		context.getGameManager().getEstado().setMFUERZA(invencible);
	}

	
	
	public void boosterVelocidad(boolean velocidad){
		this.velocidad=velocidad;		
		boosterActivado=velocidad;
		if(velocidad){
			setAnimateDuration(new long[]{20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20});
			setTiempoPorPixel((TiempoPorCuadro/BomberGame.ANCHO)*0.6f);
			
		}else{
			context.getGameManager().getEstado().setMVELOCIDAD(false);
			setAnimateDuration(new long[]{30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30});
			setTiempoPorPixel((TiempoPorCuadro/BomberGame.ANCHO)*1);
		}	
	}
	
	
	public void boosterFantasma(boolean fantasma){
		if (fantasma){
			bomber.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);		
			bomberB.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			this.fantasma=true;
			bomber.setAlpha(0.5f);
			bomberB.setAlpha(0.5f);
		}else {
			context.getGameManager().getEstado().setMFANTASMA(false);
			bomber.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);		
			bomberB.setBlendFunction(GL10.GL_ONE, GL10.GL_ONE_MINUS_SRC_ALPHA);
			this.fantasma=false;
			bomber.setAlpha(1);
			bomberB.setAlpha(1);
		}
	}
	
		
	public void finBooster(){		
		boosterActivado=false;
		boosterInvencible(false);
		boosterVelocidad(false);
		//boosterFantasma(false);
		context.getGameManager().getEstado().setMFANTASMA(false);		
	}
	
	
	
	
	
	
	private float TiempoPorCuadro=0.40f;
	private Float tiempoPorPixel=TiempoPorCuadro/BomberGame.ANCHO;
	
	public PlayerDirection recolocaDirection = PlayerDirection.NONE;
	public PlayerDirection playerDirection = PlayerDirection.NONE;

	public Float getTiempoPorPixel(){
		synchronized (tiempoPorPixel) {
			return tiempoPorPixel;
		}
	}
	
	public void setTiempoPorPixel(Float tiempo){
		synchronized (tiempoPorPixel) {
			 tiempoPorPixel=tiempo;
		}
	}
	
	private Sound pasos;
	
	public Sound getPasos() {
		return pasos;
	}

	private  Integer columna=0;
	private Integer fila=0;
	 
	public boolean moviendo = false;
	public boolean moviendo_arriba = false;
	public boolean moviendo_abajo = false;
	public boolean moviendo_derecha = false;
	public boolean moviendo_izquierda = false;
	
	public enum PlayerDirection{
		NONE,
		UP,
		DOWN,
		LEFT,
		RIGHT
	}
	

	public boolean isMuerto() {
		synchronized (this.muerto) {
			return this.muerto;
		}
		
	}
	public void setMuerto(){
		synchronized (this.muerto) {				
			muerto=true;
		}
	}
	
	public void setnoMuerto(){
		synchronized (this.muerto) {
			muerto=false;
		}
	}
	

	
	public long[] getAnimateDuration(){
		synchronized (ANIMATE_DURATION) {
			return ANIMATE_DURATION;
		}		
	}
	
	public void setAnimateDuration( long[] animate){
		synchronized (ANIMATE_DURATION) {
			ANIMATE_DURATION=animate;
		}
	}
	
	

	
	public void actualizaEstado(){
		
		
		// velocidad
		//System.out.println("velocidad="+velocidad);
		if (context.getGameManager().getEstado().isMVELOCIDAD()&& velocidad==false){
			boosterVelocidad(true);
		}else if (!context.getGameManager().getEstado().isMVELOCIDAD()&& velocidad==true){
			boosterVelocidad(false);
		}
		
		
		
		// invencible 
		if (context.getGameManager().getEstado().isMFUERZA()){
			if (!invencible){
				boosterInvencible(true);				
			}else{
				// nada ya es invencible
			}
		}else{
			if (invencible){
				boosterInvencible(false);				
			}else{
				// nada ya es invencible
			}
		}
		
	}
	
	public void setFila(int fila){	
		synchronized (this.fila) {
			this.fila=fila;
		}		
	}
	public void setColumna(int columna){	
		synchronized (this.columna) {
			this.columna=columna;
		}		
	}
	public int getFila(){
		synchronized (this.fila) {
			return this.fila;		
		}
	}
	public int getColumna(){
		synchronized (this.columna) {
			return this.columna;
		}
	}
	public BomberMan(BomberGame context){
		this.context=context;
		SoundFactory.setAssetBasePath("mfx/");	
		try {
			this.pasos = SoundFactory.createSoundFromAsset(	context.getEngine().getSoundManager(), context, "pasos7.wav");			
		} catch (final IOException e) {
			e.printStackTrace();
		}
		

		
		cuadrado= new Rectangle(0, 0, BomberGame.ANCHO, BomberGame.ALTO,context.getVertexBufferObjectManager()){
			/*@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				//System.out.println("manager");
				bomberB.setPosition(this);
				super.onManagedUpdate(pSecondsElapsed);
			}*/
		};
		cuadradoMascara=new Rectangle(9, 7, BomberGame.ANCHO-17, BomberGame.ALTO-15,context.getVertexBufferObjectManager());
		//cuadradoMascara2=new Rectangle(15, 15, context.getGameManager().getmTMXTiledMap().getTileWidth()-30, context.getGameManager().getmTMXTiledMap().getTileHeight()-30);
		cuadradoMascara.setColor(1, 0, 0.50f, 0.25f);
		//cuadradoMascara2.setColor(1, 0, 0.50f, 0.25f);
		
		cuadrado.attachChild(cuadradoMascara);
		//cuadrado.attachChild(cuadradoMascara2);
		context.getmBoundChaseCamera().setChaseEntity(cuadrado);
		
		//this.mBombermanTextureAni = new BitmapTextureAtlas(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		//this.mBombermanTextureRegionAni=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBombermanTextureAni, context, "tiledmaster90.png", 0, 0,12, 5); 
		//context.getEngine().getTextureManager().loadTexture(this.mBombermanTextureAni);
		
		mBombermanTextureRegionAniA= context.getResouceManager().getmBombermanTextureRegionAniA();
		mBombermanTextureRegionAniB= context.getResouceManager().getmBombermanTextureRegionAniB();
		
		//bomber =new AnimatedSprite(cuadrado.getX(),cuadrado.getY()-30 , mBombermanTextureRegionAni);
		
		bomber=new AnimatedSprite(cuadrado.getX()-28, cuadrado.getY()-55 , mBombermanTextureRegionAniB,context.getVertexBufferObjectManager()){			
			@Override
			public void setCurrentTileIndex(int pTileIndex) {
				bomberB.setCurrentTileIndex(pTileIndex);
				super.setCurrentTileIndex(pTileIndex);
			}
			@Override
			protected void onManagedDraw(GLState GLState, Camera pCamera) {
				// TODO Auto-generated method stub
				bomberB.setPosition(cuadrado.getX()-28,cuadrado.getY()-55);
				super.onManagedDraw(GLState, pCamera);				
				//bomberB.dibuja(pGL, pCamera);				
			}
			
		};
		
		bomberB=new AnimatedSprite(0, 0, mBombermanTextureRegionAniA,context.getVertexBufferObjectManager());
		
		cuadradoMascara.setVisible(true);
		
		float achatado =0.9f;
		float escala =0.5f;
		
		bomber.setScaleX(escala);
		bomber.setScaleY(escala*achatado);
		
		bomberB.setScaleX(escala);
		bomberB.setScaleY(escala*achatado);
		
		//bomber.setScale(0.6f);

		cuadrado.setZIndex(BomberGame.ZINDEX_BOMBERMAN_Abajo);		
		cuadrado.attachChild(bomber);
		//cuadrado.attachChild(bomberB);
		bomber.setZIndex(BomberGame.ZINDEX_BOMBERMAN_Abajo);
		
		cuadrado.setVisible(true);
		if (BomberGame.visibleSuperficiesDeControl){
			cuadrado.setAlpha(1);
			cuadradoMascara.setAlpha(1);
		}else{
			cuadrado.setAlpha(0);
			cuadradoMascara.setAlpha(0);
		}
		
		
		//cuadradoMascara.setAlpha(0);
		
		context.getEscenaJuego().attachChild(cuadrado);
		cuadrado.sortChildren();
		
		bomberB.setZIndex(BomberGame.ZINDEX_BOMBERMAN_Arriba);
		
		aura =  new AnimatedSprite(7, -20, context.getResouceManager().getAuraTR(),context.getVertexBufferObjectManager());		
		bomberB.attachChild(aura);
		aura.animate(90, true);
		aura.setScaleY(1.1f);
		aura.setVisible(false);
		aura.setIgnoreUpdate(true);
		context.getEscenaJuego().attachChild(bomberB);
		
		pasos.setLooping(true);
		//pasos.play();
		
		setnoMuerto();		
		cambioBaldosa();
		updateHandler();
		
		
	}
	
	public Rectangle getCuadradoMascara() {
		return cuadradoMascara;
	}
	public AnimatedSprite getBomber() {
		return bomber;
	}
	public Rectangle getCuadrado() {
		return cuadrado;
	}
	
	
	
	
	public void matar(ArrayList<Coordenadas>  coordenadas){
		if (invencible)return;
		for (Coordenadas coor : coordenadas) {
			if (coor.getColumna()==getColumna()&&coor.getFila()==getFila()){
				morir(true);
				break;
			}
		}
		
	}
	
	public void morir(boolean fuego) {
		if (invencible &&fuego)return;
		if (isMuerto())	return;		
		setMuerto();
		context.getResouceManager().getPasosB().pause();
		context.getGameManager().premataBomberman();

		int currentTile= bomber.getCurrentTileIndex();
		
		int [] array= new int[]{48,49,48,49};
		if (fuego){
			if (0<=currentTile && currentTile<=11){
				////System.out.println("0-11");
				array = new int[]{48,49,48,49};
			}
			if (12<=currentTile && currentTile<=23){
				////System.out.println("12- 23");
				array = new int[]{50,51,50, 51};
			}			
			if (24<=currentTile && currentTile<=35){
				////System.out.println("2 -35");
				array = new int[]{52,53,52, 53};
			}
			if (36<=currentTile && currentTile<=47){
				////System.out.println("36-47");
				array = new int[]{54,55,54, 55};
			}
		
		}else {
			if (0<=currentTile && currentTile<=11){
				////System.out.println("0-11");
				array = new int[]{11,56,11, 56};
			}
			if (12<=currentTile && currentTile<=23){
				////System.out.println("12- 23");
				array = new int[]{23,23,23, 23};
			}
			if (24<=currentTile && currentTile<=35){
				////System.out.println("2 -35");
				array = new int[]{24,57,24, 57};
			}
			if (36<=currentTile && currentTile<=47){
				////System.out.println("36-47");
				array = new int[]{47,58,47, 58};
			}
			
		}		
		
		
		//bomber.stopAnimation();
		cuadrado.clearEntityModifiers();
		long tiempo=500;
		bomber.animate(new long []{tiempo,tiempo,tiempo,tiempo},array,0,new IAnimationListener() {			
//			public void onAnimationEnd(AnimatedSprite arg0) {
//				context.getGameManager().muertoBomberman();	
//				
//			}

			@Override
			public void onAnimationStarted(AnimatedSprite pAnimatedSprite,
					int pInitialLoopCount) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationFrameChanged(AnimatedSprite pAnimatedSprite,
					int pOldFrameIndex, int pNewFrameIndex) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationLoopFinished(AnimatedSprite pAnimatedSprite,
					int pRemainingLoopCount, int pInitialLoopCount) {
				context.getGameManager().muertoBomberman();	
				
			}

			@Override
			public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
				// TODO Auto-generated method stub
				
			}
		});

	}
	public void reinicio(){
		//muerto= false;
		setFrame(0);
		setPosicionCuadros(0,0)	;
		//startActivity(new Intent(this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		//context.cargaotro();
	}
	
	
	/**
	 * indica en que posicion tiene que empezar bomberman
	 * @param fila
	 * @param columna
	 */
	public void setPosicionCuadros(int fila, int columna){
		this.columna=columna+2;
		this.fila=fila;
		context.getGameManager().getCurrentTileRectangle().setPosition(columna * BomberGame.ANCHO, fila * BomberGame.ALTO);
		getCuadrado().setPosition(((columna)+2)*BomberGame.ANCHO, ((fila)+2)*BomberGame.ALTO);

	}
	
	
	
	
	

	int diffX;
	int diffY;
//	private TMXTile tmxTile;	
	public void updateHandler() {
//		tmxTile= context.getGameManager().getTmxSuelo().getTMXTileAt(100,100);
		context.getEscenaJuego().registerUpdateHandler(new IUpdateHandler() {
			PlayerDirection playerDirectionLocal = PlayerDirection.NONE;
			public void reset() {}
			
			

			public void onUpdate(final float pSecondsElapsed) {
				
				// try{context.getTexto1().setText(getPosicionRelativa().toString());
				// }catch(Exception e){}

				if (isMuerto())
					return;
				actualizaPosicion();
				
				
				actualizaEstado();
				
					
				switch (context.getPlayerDirection()) {
				case DOWN:
					if(context.getPlayerDirection() != playerDirectionLocal){
						playerDirectionLocal=PlayerDirection.DOWN;
						sonarPasos();
						moverAbajo();
					}
					break;
				case UP:
					if(context.getPlayerDirection() != playerDirectionLocal){
						playerDirectionLocal=PlayerDirection.UP;
						sonarPasos();
						moverArriba();
					}
					break;
				case LEFT:
					if(context.getPlayerDirection() != playerDirectionLocal){
						playerDirectionLocal=PlayerDirection.LEFT;
						sonarPasos();
						moverIzquierda();
					}
					
					break;
				case RIGHT:
					if(context.getPlayerDirection() != playerDirectionLocal){
						playerDirectionLocal=PlayerDirection.RIGHT;
						sonarPasos();
						moverDerecha();
					}
					
					break;
				case NONE:
					if(context.getPlayerDirection() !=playerDirectionLocal){
						playerDirectionLocal=PlayerDirection.NONE;
						pararPasos();
						pararMovimiento();
					}					
					break;
				}
				
			}
		
		});
	}
		
	
	
	
	
	public synchronized void actualizaPosicion() {

		float cY = getCuadrado().getY() - (BomberGame.ALTO / 2);
		float cX = getCuadrado().getX() - (BomberGame.ANCHO / 2);

		int fila = (int) (cY / BomberGame.ALTO);
		if ((cY % BomberGame.ALTO) > 0) {
			fila++;
		}

		int columna = (int) ((cX) / BomberGame.ANCHO);
		if ((cX % BomberGame.ANCHO) > 0) {
			columna++;
		}

		if (getColumna() != columna || getFila() != fila) {
			setColumna(columna);
			setFila(fila);
			context.getGameManager().getCurrentTileRectangle().setPosition(columna * BomberGame.ANCHO, fila * BomberGame.ALTO);					
			cambioBaldosa();			
		}		
	}
	
	
	/*
	 * Funciones de movimiento no probadas
	 */
	
	
	

	
	
	public void moverAcuadro(PlayerPosicion posicion){

		float xto = context.getGameManager().getCurrentTileRectangle().getX();
		float yto = context.getGameManager().getCurrentTileRectangle().getY();
		cuadrado.clearEntityModifiers();
		switch (posicion) {
		
		case MUY_ABAJO:
					yto+=BomberGame.ALTO;
					AnimarAbajo();
			break;
		case MUY_ARRIBA:
					yto-=BomberGame.ALTO;
					AnimarArriba();
					
			break;
		case MUY_DERECHA:
					xto+=BomberGame.ANCHO;
					AnimarDerecha();
			break;
		case MUY_IZQUIERDA:
					xto-=BomberGame.ANCHO;
					AnimarIzquierda();
			break;

		default:
			break;
		}
		
		float x = cuadrado.getX();
		float y = cuadrado.getY();

		float mover = 0;
		if (xto == x) {
			mover = yto - y;
		} else {
			mover = xto - x;
		}
				
		// //System.out.println("centramos desde x="+x +" xto="+xto+" y="+y
		// +" yto="+yto +" mover"+mover +" en "+tiempoPorPixel * mover);
		if (mover != 0) {
			cuadrado.clearEntityModifiers();
			cuadrado.registerEntityModifier(new MoveModifier(Math.abs(tiempoPorPixel * mover), x, xto, y, yto, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					terminadoCentrar();
				}
			}));
		}
	}
	
	
	
	
	public boolean esquineaHorizontal(){
		////System.out.println("esquineaHorizontal()");
		PlayerPosicion pos =getEskinado();
		switch (pos) {
		case MUY_ARRIBA:
			moverAcuadro(pos);
			break;

		case MUY_ABAJO:
			moverAcuadro(pos);
			break;
		default:
			return false;			
		}		
		return true;
	}

	
	public boolean esquineaVertical(){
	//	//System.out.println("esquineaVertical()");
		PlayerPosicion pos =getEskinado();
		switch (pos) {
		case MUY_DERECHA:
			////System.out.println("muy derecha");
			//moverAcuadro(pos);
			break;

		case MUY_IZQUIERDA:
			////System.out.println("muy izquierda");
			//moverAcuadro(pos);
			break;
		default:
			return false;
			
		}		
		return true;
	}	

	
	
	
	
		
	public PlayerPosicion getEskinado(){
		////System.out.println("getEskinado()");
		PlayerPosicion posicionRelativa=PlayerPosicion.CENTRO;
		
		float difY =(context.getGameManager().getCurrentTileRectangle().getY())-cuadrado.getY();
		//float signoY =(context.getGameManager().getCurrentTileRectangle().getY())-cuadrado.getY();
		
		float difX =(context.getGameManager().getCurrentTileRectangle().getX())-cuadrado.getX();
		//float signoX =(context.getGameManager().getCurrentTileRectangle().getX())-cuadrado.getX();
		
		// habria que sustituir los 26 po un mayor que cero
						
			Log.d("camino", "getEskinado() difY "+difY+ "difX "+difX);
		if (difX==0 && difY==0){
			//centro
			posicionRelativa=PlayerPosicion.CENTRO;			
		}else if ( difX<=-17){
			//izquierda
			posicionRelativa=PlayerPosicion.MUY_DERECHA;
		}else if (difX>=17){
			//derecha	
			posicionRelativa=PlayerPosicion.MUY_IZQUIERDA;			
		}else if ( difY<=-17){
			//arriba					
			posicionRelativa=PlayerPosicion.MUY_ABAJO;
		}else if (difY>=17){
			//abajo				
			posicionRelativa=PlayerPosicion.MUY_ARRIBA;
		}
		return posicionRelativa;
	}
		
	
	

	
	public void moverArriba() {
		if (isMuerto())return;
		synchronized (semaforo2) {

			//System.out.println("mover moverArriba");
			pararMovimiento();
			playerDirection = PlayerDirection.UP;
			if (isLimiteArriba()) {				
				if (!esquineaVertical()) {
					AnimarArriba();
					pararEnCentroVertical();
				}else{
					moverAcuadro(getEskinado());
				}
			} else {
				PlayerPosicion posicion = getPosicionRelativa();
				if (posicion == PlayerPosicion.IZQUIERDA || posicion == PlayerPosicion.DERECHA) {
					//System.out.println("estory torcido a la " + posicion);
					recoloca(posicion);
					// cuando termine de recolocar saldra y continuara su
					// ejecucion normal, si se le interumpe
				} else {
					moverAltenativo(true);
				}
			}
		}
	}
	
	boolean sonarPasos=false;
	public void sonarPasos(){
		if (!sonarPasos){			
			//Log.d("moneda", "playPasos");
			sonarPasos=true;
			context.getResouceManager().getPasosB().play();
			//pasos.play();
			
		}
		
	}
	public void pararPasos(){
		if (sonarPasos){
			//Log.d("moneda", "pararPasos");
			//System.out.println("pararpasos");
			sonarPasos=false;
			context.getResouceManager().getPasosB().pause();
			//pasos.stop();
			
		}	
	}
	
	public void moverAbajo() {
		if (isMuerto())return;
		synchronized (semaforo2) {
			//System.out.println("mover moverAbajo");
			pararMovimiento();
			playerDirection = PlayerDirection.DOWN;
			if (isLimiteAbajo()) {
				if (!esquineaVertical()) {
					AnimarAbajo();
					pararEnCentroVertical();
				}else{
					moverAcuadro(getEskinado());
				}
			} else {
				PlayerPosicion posicion = getPosicionRelativa();
				if (posicion == PlayerPosicion.IZQUIERDA || posicion == PlayerPosicion.DERECHA) {
					//System.out.println("estory torcido a la " + posicion);
					recoloca(posicion);
					// cuando termine de recolocar saldra y continuara su
					// ejecucion normal, si se le interumpe
				} else {
					moverAltenativo(true);
				}
			}
		}
	}

	public void moverDerecha() {
		if (isMuerto())return;
		synchronized (semaforo2) {
			//System.out.println("mover moverDerecha");
			pararMovimiento();
			playerDirection = PlayerDirection.RIGHT;
			if (isLimiteDerecha()) {
				if (!esquineaHorizontal()) {
					AnimarDerecha();	
					pararEnCentroLateral();
				}
			} else {
				PlayerPosicion posicion = getPosicionRelativa();
				if (posicion == PlayerPosicion.ARRIBA || posicion == PlayerPosicion.ABAJO) {
					//System.out.println("estory torcido a la " + posicion);
					recoloca(posicion);
					// cuando termine de recolocar saldra y continuara su
					// ejecucion normal, si se le interumpe
				} else {
					moverAltenativo(true);
				}
			}
		}
	}

	public void moverIzquierda() {
		if (isMuerto())return;
		synchronized (semaforo2) {
			//System.out.println("mover moverIzquierda");
			pararMovimiento();
			playerDirection = PlayerDirection.LEFT;
			if (isLimiteIzquierda()) {
				if (!esquineaHorizontal()) {
					AnimarIzquierda();
					pararEnCentroLateral();
				}
			} else {
				PlayerPosicion posicion = getPosicionRelativa();
				if (posicion == PlayerPosicion.ARRIBA || posicion == PlayerPosicion.ABAJO) {
					//System.out.println("estory torcido a la " + posicion);
					recoloca(posicion);
					// cuando termine de recolocar saldra y continuara su
					// ejecucion normal, si se le interumpe
				} else {
					moverAltenativo(true);
				}
			}
		}
	}

		
	/**
	 * 
	 */
	public void pararMovimiento(){
		if(isMuerto())return;		
		//System.out.println("mover pararMovimiento");		
			cuadrado.clearEntityModifiers();
			playerDirection=PlayerDirection.NONE;
			recolocaDirection=PlayerDirection.NONE;
			stopAnimation();
			if (0<= bomber.getCurrentTileIndex() && bomber.getCurrentTileIndex()<=11) 		setFrame(0);
			else if (12<= bomber.getCurrentTileIndex() && bomber.getCurrentTileIndex()<=23) setFrame(12);
			else if (24<= bomber.getCurrentTileIndex() && bomber.getCurrentTileIndex()<=35) setFrame(24);
			else if (36<= bomber.getCurrentTileIndex() && bomber.getCurrentTileIndex()<=47) setFrame(36);
	}
		
	
	
	/**
	 * comprueba la posicion relativa
	 * @param posicion
	 */
	public void recoloca(PlayerPosicion posicion) {
		switch (posicion) {
		
		case ABAJO:
			AnimarArriba();
			recolocaDirection=PlayerDirection.UP;
			centrar();
			break;
			
		case ARRIBA:
			AnimarAbajo();
			recolocaDirection=PlayerDirection.DOWN;
			centrar();
			break;
			
		case DERECHA:
			AnimarIzquierda();
			recolocaDirection=PlayerDirection.LEFT;
			centrar();
			break;
			
		case IZQUIERDA:
			AnimarDerecha();
			recolocaDirection=PlayerDirection.RIGHT;
			centrar();
			break;
		case CENTRO://Nada
			break;
		}
		
		
	}
	/**
	 * mueve el sprite bomberman hacia el centro
	 */
	public void centrar() {
		final float xto = context.getGameManager().getCurrentTileRectangle().getX();
		final float yto = context.getGameManager().getCurrentTileRectangle().getY();

		float x = cuadrado.getX();
		float y = cuadrado.getY();

		float mover = 0;
		if (xto == x) {
			mover = yto - y;
		} else {
			mover = xto - x;
		}
		
		if(xto<x){
			AnimarIzquierda();
		}else if (xto>x){
			AnimarDerecha();
		}else if (yto<y){
			AnimarArriba();
		}else if (yto>y){
			AnimarAbajo();
		}
		
		
		cuadrado.clearEntityModifiers();
		//System.out.println("centramos desde x="+x +" xto="+xto+" y="+y +" yto="+yto +" mover"+mover +" en "+tiempoPorPixel * mover);
		if (mover!=0){
			cuadrado.registerEntityModifier(new MoveModifier(Math.abs(tiempoPorPixel * mover), x, xto, y, yto, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				}
				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
					terminadoCentrar();			
				}
			}));	
		}


	}
	
	
	public void terminadoCentrar(){
		//if (playerDirection!=recolocaDirection){
			stopAnimation();
		//}
			moverAltenativo(true);
	}
	
	
	public void moverAltenativo(boolean animar) {
		//System.out.println("columna "+columna +"fila "+fila+" mover a "+direccion);
		
		actualizaPosicion();
		
		float x = cuadrado.getX();
		float y = cuadrado.getY();
		float toX = 0;
		float toY = 0;	
		float tiempo=0;
		Rectangle currentRectangulo = context.getGameManager().getCurrentTileRectangle();
		switch (playerDirection) {
		case DOWN:
			// //System.out.println("DOWN");
			toY= currentRectangulo.getY()+ BomberGame.ALTO;
			tiempo=tiempoPorPixel*(Math.abs(toY-cuadrado.getY()));
			if (animar)AnimarAbajo();
			cuadrado.registerEntityModifier(new MoveYModifier(tiempo, cuadrado.getY(), toY) {
				@Override
				protected void onModifierStarted(IEntity pItem) {					
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});

			break;

		case UP:
			// //System.out.println("UP");
			if (animar)AnimarArriba();
			toY=currentRectangulo.getY() - BomberGame.ALTO;
			tiempo=tiempoPorPixel*(Math.abs(toY-cuadrado.getY()));
			cuadrado.registerEntityModifier(new MoveYModifier(tiempo, cuadrado.getY(),toY) {
				@Override
				protected void onModifierStarted(IEntity pItem) {				
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});
			break;
		case LEFT:
			// //System.out.println("LEFT");
			if (animar)AnimarIzquierda();
			toX=currentRectangulo.getX() - BomberGame.ANCHO;
			tiempo=tiempoPorPixel*(Math.abs(toX-cuadrado.getX()));
			cuadrado.registerEntityModifier(new MoveXModifier(tiempo, cuadrado.getX(), toX) {
				@Override
				protected void onModifierStarted(IEntity pItem) {					
					// playMusica();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});
			break;
		case RIGHT:
			// //System.out.println("RIGHT");
			if (animar)AnimarDerecha();
			toX=currentRectangulo.getX() + BomberGame.ANCHO;
			tiempo=tiempoPorPixel*(Math.abs(toX-cuadrado.getX()));
			cuadrado.registerEntityModifier(new MoveXModifier(tiempo, cuadrado.getX(), toX) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();					
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});

			break;
			
		case NONE:
			
		}
	}
	
	public void llegado(){		
		if (context.isBotonA()){
			if (context.getGameManager().getPolvorin().plantaBomba()){
				//context.vibrar(50);
			}			
		}
		//System.out.println("he llegado");
		moverAltenativo(false);
		//actualizaPosicion();
		//inteligencia();
	}
	
	
	
	
	public void AnimarIzquierda(){
		stopAnimation();
		bomber.animate(ANIMATE_DURATION,36,47,true);
	}
	public void AnimarDerecha(){
		bomber.stopAnimation();
		bomber.animate(ANIMATE_DURATION,24,35,true);
	}
	public void AnimarArriba(){
		bomber.stopAnimation();
		bomber.animate(ANIMATE_DURATION,12,23,true);
	}
	public void AnimarAbajo(){
		bomber.stopAnimation();
		bomber.animate(ANIMATE_DURATION,0,11,true);
	}	
	public void setAnimation(boolean animar){
		
	}
	
	
	
	/**
	 * para la animacion, e funcion de PlayerDirection pone el fotograma correspondiente
	 */
	public void stopAnimation(){
		bomber.stopAnimation();
	}
	public void setFrame(int frame)  {
		bomber.setCurrentTileIndex(frame);
	}
	
	//public float 
	
	
	public void AnimacionMuerteFuegoDerecha(){
		setMuerto();
	}
	public void AnimacionMuerteFuegoIzquierda(){
		setMuerto();
	}
	public void AnimacionMuerteFuegoAbajo(){
		setMuerto();
	}
	public void AnimacionMuerteFuegoArriba(){
		setMuerto();
	}
	
	
	/**
	 * estas funciones llaman a otra de gameManager  que le dice que objeto hay en la posicion a la que quieren ir
	 * si ay una piedra O SI HAY UNA pared si hay una pared y tiene el booster de atravesar paredes esta funcion le dira que puede seguir 
	 * @return
	 */
	public boolean isLimiteArriba(){
		int sitio=context.getGameManager().getMatriz().getValor(getFila()-1, getColumna());
		if (!fantasma){	
			if (sitio==BomberGame.PARED || sitio==BomberGame.PIEDRA ||sitio==BomberGame.BOMBA){				
				return true;
			}
		}else{
			if (sitio==BomberGame.PIEDRA){				
				return true;
			}
		}		
		return false;
	}
	public boolean isLimiteAbajo(){
		int sitio =context.getGameManager().getMatriz().getValor(getFila()+1,getColumna());
		if (!fantasma){			
			if (sitio==BomberGame.PARED || sitio==BomberGame.PIEDRA ||sitio==BomberGame.BOMBA){			
				return true;
			}
		}else{
			if (sitio==BomberGame.PIEDRA){				
				return true;
			}
		}		
		return false;
	}
	public boolean isLimiteDerecha(){
		int sitio =context.getGameManager().getMatriz().getValor(getFila(),getColumna()+1);
		if (!fantasma){			
			if (sitio==BomberGame.PARED || sitio==BomberGame.PIEDRA ||sitio==BomberGame.BOMBA){				
				return true;
			}
		}else{
			if (sitio==BomberGame.PIEDRA){				
				return true;
			}
		}	
		return false;
	}
	public boolean isLimiteIzquierda(){
		int sitio =context.getGameManager().getMatriz().getValor(getFila(),getColumna()-1);
	
		if (!fantasma){			
			if (sitio==BomberGame.PARED || sitio==BomberGame.PIEDRA ||sitio==BomberGame.BOMBA){				
				return true;
			}
		}else{
			if (sitio==BomberGame.PIEDRA){
				//Log.d("puerta", "isLimiteIzquierda true"+sitio);
				return true;
			}
		}
		//Log.d("puerta", "isLimiteIzquierda false"+sitio);
		return false;

	}
		
	/**
	 * cada vez que se cambie de baldosa se pone la nueva posición en la tabla de posiciones en su array propio
	 */



	
	public void comprobarSuelo() {

		if (context.getGameManager().getMatriz().getValor(fila, columna) == BomberGame.MONEDA && !boosterActivado) {
			//TipoMoneda tipo = context.getGameManager().getMonedero().getTipoMonedaenXY(columna, fila);
			context.getGameManager().getMonedero().recogidoMoneda(columna, fila);
		}else if (context.getGameManager().getMatriz().getValor(fila, columna) == BomberGame.MONEDA && boosterActivado){
			TipoMoneda tipo = context.getGameManager().getMonedero().getTipoMonedaenXY(columna, fila);
			switch (tipo) {
			case MBOMBA:
			case MDETONADOR:
			case MCORAZON:
			case MEXPLOSION:
				context.getGameManager().getMonedero().recogidoMoneda(columna, fila);
				break;
			default:// nada
				break;
			}
		}
		
		if (context.getGameManager().getMatriz().getValor(fila, columna)==BomberGame.PUERTA && context.getGameManager().getEstado().getEnemigosRestantes()==0){
			context.getGameManager().nivelSuperado();
		}
		Log.d("puerta", "hmiBaldosa"+context.getGameManager().getMatriz().getValor(fila, columna));
		
	}
	
	/**
	 * se la llama cuando se cambia de valdosa
	 */
	
	
	public void compruebaFantasma(){
		if (!context.getGameManager().getEstado().isMFANTASMA() && context.getGameManager().getMatriz().getValor(fila, columna)==BomberGame.NADA){
			if (fantasma){
				boosterFantasma(false);
			}			
		}
	}
	
	
	public void cambioBaldosa(){
		comprobarSuelo();
		compruebaFantasma();

		
		//System.out.println("cambio valdosa");
		/*if (context.isBotonA()){
			context.getGameManager().getPolvorin().PlantaBomba();		
		}*/
		switch (playerDirection) {
		case DOWN:
			if (isLimiteAbajo()){
				//System.out.println("paro movimiento dow");
				pararEnCentro();
				//pararMovimiento();
			}
			break;

		case UP:
			if (isLimiteArriba()){
				//System.out.println("paro movimiento up ");
				pararEnCentro();
			}
			break;
		case LEFT:
			if (isLimiteIzquierda()){
				//System.out.println("paro movimiento iz");
				pararEnCentro();
			}
			break;
			
		case RIGHT:
			if (isLimiteDerecha()){
				//System.out.println("paro movimiento der");
				pararEnCentro();
			}
			break;
		case NONE:
			
			break;
		}
	}
	
	public void pararEnCentro(){
		//System.out.println("parar cetro");
		cuadrado.clearEntityModifiers();
		context.getTexto1().setText("parar");
		final float xto = context.getGameManager().getCurrentTileRectangle().getX();
		final float yto = context.getGameManager().getCurrentTileRectangle().getY();

		float x = cuadrado.getX();
		float y = cuadrado.getY();

		float mover = 0;
		if (xto == x) {
			mover = yto - y;
		} else {
			mover = xto - x;
		}
		
		cuadrado.clearEntityModifiers();
		cuadrado.clearEntityModifiers();
		//System.out.println("centramos desde x="+x +" xto="+xto+" y="+y +" yto="+yto +" mover"+mover +" en "+tiempoPorPixel * mover);
		if (mover != 0) {

			cuadrado.registerEntityModifier(new MoveModifier(Math.abs(tiempoPorPixel * mover), x, xto, y, yto, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub

				}
			}));
		}
	}
	
	

	
    
    
  
    
    
   
	/**
	 * acerca al centro cuando hay un tope sin centrar lateralmente
	 */
	
	
	public void pararEnCentroVertical(){
		//System.out.println("parar cetro");
		context.getTexto1().setText("parar");
		final float xto = context.getGameManager().getCurrentTileRectangle().getX();
		final float yto = context.getGameManager().getCurrentTileRectangle().getY();

		float x = cuadrado.getX();
		float y = cuadrado.getY();

		float mover = 0;
		
			mover = yto - y;
		
		
		cuadrado.clearEntityModifiers();
		//System.out.println("centramos desde x="+x +" xto="+xto+" y="+y +" yto="+yto +" mover"+mover +" en "+tiempoPorPixel * mover);
		if (mover != 0) {

			cuadrado.registerEntityModifier(new MoveModifier(Math.abs(tiempoPorPixel * mover), x, x, y, yto, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub

				}
			}));
		}
	}
	
	/**
	 * acerca al centro cuando hay un tope sin centrar lateralmente
	 */
	
	public void pararEnCentroLateral(){
		//System.out.println("parar cetro");
		context.getTexto1().setText("parar");
		final float xto = context.getGameManager().getCurrentTileRectangle().getX();
		final float yto = context.getGameManager().getCurrentTileRectangle().getY();

		float x = cuadrado.getX();
		float y = cuadrado.getY();

		float mover = 0;
		
			mover = xto - x;
		
		
		cuadrado.clearEntityModifiers();
		//System.out.println("centramos desde x="+x +" xto="+xto+" y="+y +" yto="+yto +" mover"+mover +" en "+tiempoPorPixel * mover);
		if (mover != 0) {

			cuadrado.registerEntityModifier(new MoveModifier(Math.abs(tiempoPorPixel * mover), x, xto, y, y, new IEntityModifierListener() {
				@Override
				public void onModifierStarted(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub
				}

				@Override
				public void onModifierFinished(IModifier<IEntity> arg0, IEntity arg1) {
					// TODO Auto-generated method stub

				}
			}));
		}
	}
	
	
	public enum PlayerPosicion{
		ARRIBA,
		ABAJO,		
		DERECHA,
		IZQUIERDA,
		CENTRO,
		MUY_ARRIBA,
		MUY_ABAJO,		
		MUY_DERECHA,
		MUY_IZQUIERDA,
	}
	private PlayerPosicion posicionRelativa;
	
	/**
	 * devuelve un enumerado la posición relativa tanto horizontal como vertical
	 */
	
	public synchronized PlayerPosicion getPosicionRelativa(){
		
		float difY =(context.getGameManager().getCurrentTileRectangle().getY())-cuadrado.getY();
		//float signoY =(context.getGameManager().getCurrentTileRectangle().getY())-cuadrado.getY();
		
		float difX =(context.getGameManager().getCurrentTileRectangle().getX())-cuadrado.getX();
		//float signoX =(context.getGameManager().getCurrentTileRectangle().getX())-cuadrado.getX();
		
		// habria que sustituir los 26 po un mayor que cero
		
	
		Log.d("camino", "getPosicionRelativa() difY "+difY+ "difX "+difX);
		
		if (difX==0 && difY==0){
			//centro
			posicionRelativa=PlayerPosicion.CENTRO;			
		}else if ( difX<=-1){
			//izquierda
			posicionRelativa=PlayerPosicion.DERECHA;
		}else if (difX>=1){
			//derecha	
			posicionRelativa=PlayerPosicion.IZQUIERDA;			
		}else if ( difY<=-1){
			//arriba					
			posicionRelativa=PlayerPosicion.ABAJO;
		}else if (difY>=1){
			//abajo				
			posicionRelativa=PlayerPosicion.ARRIBA;
		}
		
		return posicionRelativa;
	}
	public synchronized void  setPosicionRelativa(PlayerPosicion posicionRelativa){
		this.posicionRelativa=posicionRelativa;
	}
	
	/**
	 * devuelve la posicion en fila columna 
	 * @return
	 */
	
	public int [] getPosicion(){
		int salida[] ={getColumna(),getFila()};
		return salida;
	}
}
