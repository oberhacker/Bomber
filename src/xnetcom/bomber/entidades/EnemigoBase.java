package xnetcom.bomber.entidades;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.shape.IShape;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;


import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.Coordenadas;

public abstract class EnemigoBase extends Rectangle{

	protected BomberGame context;
	//protected Rectangle cuadrado;
	protected Rectangle cuadradoMascara;
	protected AnimatedSprite principal;	
	protected AnimatedSprite transparencia;

	
	protected Rectangle currentTileRectangle;
	
	protected Tipo tipoEnemigo = Tipo.EN_GLOBO;
	protected Random generator;
	
	protected boolean enfadado=false;
	protected int columna=0;
	protected int fila=0;
	protected boolean primeraVez=true;
	protected float tiempoRetardo=0.4f;
	protected float TiempoPorCuadro=0.40f;
	protected float tiempoPorPixel=TiempoPorCuadro/BomberGame.ANCHO;	
	protected boolean fantasma=false;
	protected boolean fliped=false;
	protected boolean muerto=false;
	public boolean inicioInteligencia =false;

	protected EnemigoDirection direccion;
	protected long tiempoFotograma;
	protected EnemigoDirection direccionAnimacion=EnemigoDirection.NONE;
	
	
	
	protected enum EnemigoDirection {
		NONE("NONE"), UP("UP"), DOWN("DOWN"), LEFT("LEFT"), RIGHT("RIGHT");
		String valor;
		EnemigoDirection(String valor) {
			this.valor = valor;
		}
		public String getValor(){
			return valor;
		}
	}
	
	public enum Tipo {
		EN_GLOBO("EN_GLOBO"), 
		EN_FANTASMA("EN_FANTASMA"), 
		EN_MOCO("EN_MOCO"), 
		EN_MONEDA("EN_MONEDA"), 
		EN_GOTA_AZUL("EN_GOTA_AZUL"),
		EN_GOTA_NARANJA("EN_GOTA_NARANJA"),
		EN_GLOBO_AZUL("EN_GLOBO_AZUL"), 
		EN_GOTA_ROJA("EN_GOTA_ROJA"), 
		EN_MOCO_ROJO("EN_MOCO_ROJO"), 
		EN_MONEDA_MARRON("EN_MONEDA_MARRON");
		String value;

		Tipo(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		
	}	
	

	
	public EnemigoBase(BomberGame context, int columna, int fila) {
		super(0, 0, BomberGame.ANCHO, BomberGame.ALTO,context.getVertexBufferObjectManager());
		
		this.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//this.setAlpha(0f);
		
		setZIndex(BomberGame.ZINDEX_ENEMIGOS);
		this.columna=columna;
		this.fila=fila;
		generator= new Random();
		setVisible(true);
		this.context=context;

		cuadradoMascara=new Rectangle(9, 7, BomberGame.ANCHO-17, BomberGame.ALTO-15,context.getVertexBufferObjectManager()){
			public boolean collidesWith(IShape pOtherShape) {
				if (!EnemigoBase.this.isMuerto() && super.collidesWith(pOtherShape)) {
					return true;
				} else
					return false;

			}
		};
		
		
		cuadradoMascara.setColor(1, 0, 0.50f, 0.25f);
		attachChild(cuadradoMascara);
		cuadradoMascara.setVisible(true);
		
		if (BomberGame.visibleSuperficiesDeControl){
			setAlpha(1);
			cuadradoMascara.setAlpha(1);
			currentTileRectangle = new Rectangle(0, 0, BomberGame.ANCHO, BomberGame.ALTO,context.getVertexBufferObjectManager());		
			currentTileRectangle.setColor(1, 0, 0, 0.25f);
			currentTileRectangle.setZIndex(1200);
			context.getEscenaJuego().attachChild(currentTileRectangle);
		}else{
			setAlpha(0);
			cuadradoMascara.setAlpha(0);
		}
		registerUpdateHandler(new TimerHandler(0.2f, true, new ITimerCallback() {
			@Override
			public void onTimePassed(final TimerHandler pTimerHandler) {
				try{					
					actualizaPosicion();
				}catch(Exception e){}
			}
		}));
		// TODO Auto-generated constructor stub
	}

		
	public Rectangle getCuadradoMascara() {
		return cuadradoMascara;
	}

	public boolean collidesWith(IShape pOtherShape) {
		if (super.collidesWith(pOtherShape) && !EnemigoBase.this.isMuerto()) {
			return true;
		} else
			return false;
	}
	
	
	public int getColumna() {
		return columna;
	}
	public int getFila() {
		return fila;
	}
	
	protected void onManagedUpdate(float pSecondsElapsed) {		

		//actualizaPosicion();
		
		if(cuadradoMascara!=null && cuadradoMascara.collidesWith(EnemigoBase.this.context.getBomberman().getCuadradoMascara())){
			EnemigoBase.this.context.getGameManager().getBomberman().morir(false); // comentado para que no mate en pruebas
		}
		
		super.onManagedUpdate(pSecondsElapsed);
	}
	
	int numeroBomba=-5;
	boolean iniciado=false;
	public void iniciaInteligenciaIA(int numeroBomba) {	
		this.numeroBomba=numeroBomba;		
		iniciaInteligenciaIA();	
	}
	
	
	
	public void iniciaInteligenciaIA() {	
		coloca(this.columna,this.fila);
		iniciado=true;
		setVisible(true);		
		this.direccion = EligeDireccion();		
		inteligencia();		
	}
	
	

	public void coloca(int columna,int fila){
		float cY=(fila*BomberGame.ALTO);
		float cX=(columna*BomberGame.ANCHO);	
		setPosition(cX,cY);
		actualizaPosicion();
		if (currentTileRectangle!=null)currentTileRectangle.setPosition(this);
	}
	BomberGame getContext(){
		return context;
	}
	
	
	
	public boolean isMuerto() {
		return muerto;
	}

	public void setMuerto(boolean muerto) {
		this.muerto = muerto;
	}


	public void attachEscena(Scene scena){
		 scena.attachChild(this);
	}
		
	public void eliminaBicho(){		
		context.runOnUpdateThread(new Runnable() {			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				//detachSelf();	
				midetachSelf();
			}
		});
	}
		
	
	public void miRevive(int columna, int fila){
		this.columna=columna;
		this.fila=fila;
		
		float cY=(this.fila*BomberGame.ALTO);
		float cX=(this.columna*BomberGame.ANCHO);	
		setPosition(cX,cY);		
		actualizaPosicion();
		muerto=false;
		setVisible(false);
		setIgnoreUpdate(false);
	}
	
	
	
	public void midetachSelf(){
		detachSelf();
		
		//muerto=true;
		//setVisible(true);
		//setIgnoreUpdate(true);
	}
	
	
	
	
	
	public void inteligencia(){	
		
		
		
		if (puedoSeguir(direccion)){// siguooooo
			
			if(tomaDecision(1, 100)<70){
				setDireccion(direccion);
				mover(direccion);				
				return;
			}else{// cambio de sentido
				EnemigoDirection dir = cambiaDireccion(direccion);
				setDireccion(dir);
				mover(dir);
				return;

			}

		}else{// cambio	direccion	
			
			
			if(numeroSalidas()==0){// me paro
				tiempoMuerto();
				return;
			}else if (numeroSalidas()==1){//cambio	direccion  solo una salida
				EnemigoDirection cambio =EligeDireccion();
				boolean cam = isCambioSentido(direccion, cambio);
				if(cam){
					setDireccion(cambio);
					retardo(cambio);
					return;
				}else{
					setDireccion(cambio);
					mover(cambio);
					return;
				}				
			}else{// cambio	direccion tengo multiples salidas
				
				// doy la vuelta  o cambio de direccion??
				if(tomaDecision(1, 100)<70){// doy la vuelta
					EnemigoDirection cambio = cambioSentido(direccion);
					setDireccion(cambio);
					retardo(cambio);	
					return;
				}else{
					EnemigoDirection cambio = cambiaDireccion(direccion);
					setDireccion(cambio);
					mover(cambio);
					return;
					
				}				
			}
			
		}
	}

	
	EnemigoDirection tomaDecisionDireccion(){
		int numero =tomaDecision(1, 4);			
		switch (numero) {
		case 1:
			return EnemigoDirection.RIGHT;			
		case 2:
			return EnemigoDirection.LEFT;			
		case 3:			
			return EnemigoDirection.DOWN;				
		case 4:					
			return EnemigoDirection.UP;	
		default:
			System.err.println("erorreeeeee");
			return EnemigoDirection.NONE;				
		}
	}
	
	protected int tomaDecision(int aStart, int aEnd) {
		//System.out.println("to,maaaaaa");
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * generator.nextDouble());
		int aleatorio = (int) (fraction + aStart);
		return aleatorio;

	}
	
	
	
	
	///
	public boolean isCambioSentido(EnemigoDirection anterior, EnemigoDirection actual){
		boolean cambio=true;
		if(anterior==EnemigoDirection.RIGHT && actual==EnemigoDirection.LEFT);
		else if(anterior==EnemigoDirection.LEFT && actual==EnemigoDirection.RIGHT);
		else if(anterior==EnemigoDirection.UP && actual==EnemigoDirection.DOWN);
		else if(anterior==EnemigoDirection.DOWN && actual==EnemigoDirection.UP);
		else{
			cambio = false;
		}
		return cambio;
	}
	
	

	/**
	 * elige una direccion aleatoria valida si no tiene sera none
	 * @return
	 */
	public EnemigoDirection EligeDireccion(){

		
		EnemigoDirection direccion = EnemigoDirection.NONE;
		
		if(numeroSalidas()==0){
			return EnemigoDirection.NONE;
		}		
		
		while (direccion == EnemigoDirection.NONE) {
			//System.out.println("eligedireccionm");
			
			int dir = tomaDecision(1, 4);
			
			switch (dir) {
			case 1:
				if (puedoAbajo()) {
					direccion = EnemigoDirection.DOWN;
					//System.out.println("estoy en columna"+columna+" fila "+fila+ " y puedo ir para abajo");
				}
				break;
			case 2:
				if (puedoArriba()) {
					direccion = EnemigoDirection.UP;
					//System.out.println("estoy en columna"+columna+" fila "+fila+ " y puedo ir para arriba");
				} 
				break;
			case 3:
				if (puedoIzquierda()) {
					direccion = EnemigoDirection.LEFT;
					//System.out.println("estoy en columna"+columna+" fila "+fila+ " y puedo ir para izquierda");
				} 
				break;
			case 4:
				if (puedoDerecha()) {
					direccion = EnemigoDirection.RIGHT;
					//System.out.println("estoy en columna"+columna+" fila "+fila+ " y puedo ir para derecha");
				} 
				break;
			}
		}		
	
		return direccion;	
	}
	
	
	
	
	
	
	public EnemigoDirection cambioSentido(EnemigoDirection direccion){
		EnemigoDirection direccionSalida=EnemigoDirection.NONE;
		if(direccion==EnemigoDirection.RIGHT)direccionSalida=EnemigoDirection.LEFT;
		else if(direccion==EnemigoDirection.LEFT)direccionSalida=EnemigoDirection.RIGHT;
		else if(direccion==EnemigoDirection.UP)direccionSalida=EnemigoDirection.DOWN;
		else if(direccion==EnemigoDirection.DOWN)direccionSalida=EnemigoDirection.UP;
		return direccionSalida;
		
	}
	
	
	/**
	 * funcion devuelve una direccion alternativa que no es cambio de sentido en
	 * caso contrario devulve null
	 * solo dara seguir en la direccion si no tiene otro camino
	 * 
	 * @param dir
	 * @return
	 */
	public EnemigoDirection cambiaDireccion(EnemigoDirection dir) {

		EnemigoDirection dirSalida = EnemigoDirection.NONE;

		int valorDerecha = tomaDecision(1, 1000);
		int valorIzquierda = tomaDecision(1, 1000);
		int valorArriba = tomaDecision(1, 1000);
		int valorAbajo = tomaDecision(1, 1000);
		int valorMaximo = -1;

		// con esto evitamos el cambio de sentido
		switch (dir) {
		case DOWN:
			valorArriba = -10;
			valorAbajo=1;
			break;
		case UP:
			valorAbajo = -10;
			valorArriba=1;
			break;

		case LEFT:
			valorDerecha = -10;
			valorIzquierda=1;
			break;

		case RIGHT:
			valorIzquierda = -10;
			valorDerecha=1;
			break;

		default:
			break;
		}

		if (valorDerecha >= valorMaximo && puedoDerecha()) {
			valorMaximo = valorDerecha;
			dirSalida = EnemigoDirection.RIGHT;
		}
		if (valorIzquierda >= valorMaximo && puedoIzquierda()) {
			valorMaximo = valorIzquierda;
			dirSalida = EnemigoDirection.LEFT;
		}
		if (valorArriba >= valorMaximo && puedoArriba()) {
			valorMaximo = valorArriba;
			dirSalida = EnemigoDirection.UP;
		}
		if (valorAbajo >= valorMaximo && puedoAbajo()) {
			valorMaximo = valorAbajo;
			dirSalida = EnemigoDirection.DOWN;
		}

		return dirSalida;

	}

	
	
	
	
	
	public EnemigoDirection cambiaDireccion2(EnemigoDirection dir) {
		
		EnemigoDirection dirSalida = EnemigoDirection.NONE;
		
		int valorDerecha=1;
		int valorIzquierda=1;
		int valorArriba=1;
		int valorAbajo=1;
		
		int valorMaximo = -1;
		
		if(!puedoDerecha())valorDerecha=0;
		if(!puedoIzquierda())valorIzquierda=0;
		if(!puedoArriba())valorArriba=0;
		if(!puedoAbajo())valorAbajo=0;
		
		
		
		switch (dir) {
		case DOWN:			
			valorAbajo=0;
			break;
		case UP:			
			valorArriba=0;
			break;

		case LEFT:			
			valorIzquierda=0;
			break;

		case RIGHT:			
			valorDerecha=0;
			break;

		default:
			break;
		}
		
		
		
		
		
	
		
		
		
		
		return null;
	}
	
	
	
	
	
	
	
	
	public int numeroSalidas(){
		int salidas=0;
		if(puedoAbajo())salidas++;
		if(puedoArriba())salidas++;
		if(puedoDerecha())salidas++;
		if(puedoIzquierda())salidas++;
		return salidas;
	}
	
	
	public boolean puedoSeguir(EnemigoDirection dir){
		switch (dir) {
		case DOWN:
			return puedoAbajo();			
		case UP:
			return puedoArriba();			
		case LEFT:
			return puedoIzquierda();			
		case RIGHT:
			return puedoDerecha();			
		}
		return false;
		
	}
	
	
	
	
	

	
	
	
	float toX;
	float toY;
	
	public void mover(EnemigoDirection dir) {
		//System.out.println("columna "+columna +"fila "+fila+" mover a "+direccion);

		toX=getX();
		toY=getY();
		
		switch (dir) {
		case DOWN:
			// //System.out.println("DOWN");
			toY=this.getY() + BomberGame.ALTO;
			this.registerEntityModifier(new MoveYModifier(TiempoPorCuadro, this.getY(), this.getY() + BomberGame.ALTO) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					animarAbajo();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});

			break;

		case UP:
			// //System.out.println("UP");
			toY=this.getY() - BomberGame.ALTO;
			this.registerEntityModifier(new MoveYModifier(TiempoPorCuadro, this.getY(), this.getY() - BomberGame.ALTO) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					animarArriba();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});
			break;
		case LEFT:
			// //System.out.println("LEFT");
			toX=this.getX() - BomberGame.ANCHO;
			this.registerEntityModifier(new MoveXModifier(TiempoPorCuadro, this.getX(), this.getX() - BomberGame.ANCHO) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					animarIzquierda();
					// playMusica();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});
			break;
		case RIGHT:
			// //System.out.println("RIGHT");
			toX=this.getX() + BomberGame.ANCHO;
			this.registerEntityModifier(new MoveXModifier(TiempoPorCuadro, this.getX(), this.getX() + BomberGame.ANCHO) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					animarDerecha();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});

			break;
			
		case NONE:		
			// //System.out.println("RIGHT");
			toX=this.getX() + BomberGame.ANCHO;
			this.registerEntityModifier(new MoveXModifier(TiempoPorCuadro, this.getX(), this.getX()) {
				@Override
				protected void onModifierStarted(IEntity pItem) {
					// playMusica();
					//animarDerecha();
				}

				protected void onModifierFinished(IEntity pItem) {
					llegado();
				}
			});
			
		}
	}
	
	public void retardo(final EnemigoDirection dir){		
		this.registerEntityModifier(new DelayModifier(tiempoRetardo){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				mover(dir);
			}
		});	
	}
	
	
	
	public void tiempoMuerto(){		
		this.registerEntityModifier(new DelayModifier(tiempoRetardo){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				llegado();
			}
		});	
	}
	
	public void llegado(){		
		
		//System.out.println("llegado a la "+columna);
		//matrizPath[fila][columna]++;
		actualizaPosicion();
		inteligencia();
	}
	
	
	
	public void setDireccion(EnemigoDirection direccion){
		this.direccion=direccion;
	}
	

	
	public void actualizaPosicion(){		
		//System.out.println("actualizaPosicion columna "+columna+" fila "+fila);
		
		float cY = getY() - (BomberGame.ALTO / 2);
		float cX = getX() - (BomberGame.ANCHO / 2);

		int fila = (int) (cY / BomberGame.ALTO );
		if ((cY % BomberGame.ALTO ) > 0) {
			fila++;
		}

		int columna = (int) ((cX) / BomberGame.ANCHO);
		if ((cX % BomberGame.ANCHO) > 0) {
			columna++;
		}
		this.columna=columna;
		this.fila=fila;
		if (currentTileRectangle!=null)currentTileRectangle.setPosition(columna*BomberGame.ANCHO,fila*BomberGame.ALTO);	
	}
	
	
	

	public abstract void animarDerecha();	
	public abstract void animarIzquierda();
	public abstract void animarArriba();
	public abstract void animarAbajo();	
	

	

	
	public boolean puedoDerecha(){
		int salida=-1;
		try{ salida=context.getGameManager().getMatriz().getValor(fila, columna+1);}catch (Exception e) {e.printStackTrace();}
		if (salida==BomberGame.NADA|| salida==BomberGame.PUERTA|| salida==BomberGame.MONEDA ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean puedoIzquierda(){
		int salida=-1;
		try{ salida=context.getGameManager().getMatriz().getValor(fila, columna-1);}catch (Exception e) {e.printStackTrace();}
		if (salida==BomberGame.NADA || salida==BomberGame.PUERTA|| salida==BomberGame.MONEDA ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean puedoArriba(){
		int salida=-1;
		try{ salida=context.getGameManager().getMatriz().getValor(fila-1, columna);}catch (Exception e) {e.printStackTrace();}
		if (salida==BomberGame.NADA|| salida==BomberGame.PUERTA|| salida==BomberGame.MONEDA ){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean puedoAbajo(){
		int salida=-1;
		try{ salida=context.getGameManager().getMatriz().getValor(fila+1, columna);}catch (Exception e) {e.printStackTrace();}
		if (salida==BomberGame.NADA|| salida==BomberGame.PUERTA|| salida==BomberGame.MONEDA ){
			return true;
		}else{
			return false;
		}

	}
	


	public  void matarBicho(int numeroBomba){		
		if (muerto)return;
		if (!iniciado) return;
		if (this.numeroBomba==numeroBomba) return;// para el enemigo moneda que no le mate la bomba que e creeo
		try {
			muerto=true;			
			clearEntityModifiers();	
			principal.stopAnimation();
			animarMuerte();			
			context.getGameManager().enemigoMuerto();
			return;	
		} catch (Exception e) {
			// TODO: handle exception
		}
	
	}
	
	public void matar(ArrayList<Coordenadas> coor,int numeroBomba){
		for (Coordenadas coordenadas : coor) {
			if (coordenadas.getColumna()==columna && coordenadas.getFila()==fila){
				matarBicho(numeroBomba);
			}
		}
	}
	
	
	
	public void pararBicho(){			
		try {			
			clearEntityModifiers();			
			principal.stopAnimation();
		} catch (Exception e) {
			System.out.println("pararBicho");
		}
		
		
	}
	
	
	public abstract void animarMuerte();
	
	
	
	public void animar(EnemigoDirection dir) {
		switch (dir) {
		case DOWN:
			animarAbajo();
			 break;
		case UP:
			animarArriba();
			 break;
		case LEFT:
			 animarIzquierda();
			 break;
		case RIGHT:
			 animarDerecha();
			 break;
		}
	}
	
	
	public class ListenerMorir implements IAnimationListener{
		
//		public void onAnimationEnd(AnimatedSprite arg0) {
//			setVisible(false);			
//			eliminaBicho();		
//		}

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
			setVisible(false);			
			eliminaBicho();		
			
		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			// TODO Auto-generated method stub
			
		}
	}
	
	
	public Tipo getTipoEnemigo() {
		// TODO Auto-generated method stub
		return tipoEnemigo;
	}
}
