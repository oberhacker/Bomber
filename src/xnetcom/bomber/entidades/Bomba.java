package xnetcom.bomber.entidades;

import java.util.ArrayList;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.andengine.TransparentBitmapTextureAtlasSource;
import xnetcom.bomber.util.Coordenadas;

public class Bomba {
	
	
	private BomberGame context;
	private SpriteGroup batch;
	
	
	private static final int TIEMPO = 100;
	private static final long[] ANIMATE_DURATION =new long[]{50, 50, 50, 50, 50};
	private int posX=0;
	private int posY=0;
	
	//private Sound mecha;
	private int tamExplosion;
	AlmacenBombas almacen;
	private BitmapTextureAtlas bombaBTA;
	private TiledTextureRegion bombaTR;
	private int secuencia; 


	private BuildableBitmapTextureAtlas fuegoBBTA;
	private TiledTextureRegion mFuegoCentroTR;
	private TiledTextureRegion mFuegoCentroDerechaTR;	
	private TiledTextureRegion mFuegoCentroIzquierdaTR;	
	private TiledTextureRegion mFuegoCentroArribaTR;	
	private TiledTextureRegion mFuegoCentroAbajoTR;	
	private TiledTextureRegion mFuegoHorizontalTR;
	private TiledTextureRegion mFuegoVerticalTR;
	
	private TiledTextureRegion mFuegoFinDerechaTR;
	private TiledTextureRegion mFuegoFinIzquierdaTR;
	private TiledTextureRegion mFuegoFinArribaTR;	
	private TiledTextureRegion mFuegoFinAbajoTR;

	private AnimatedSprite[] spriteArray;
	private ArrayList<Coordenadas> coordenadas;
	private ArrayList<Coordenadas> coordenadasParedes;
	private ArrayList<Coordenadas> coordenadasBombas;
	private Boolean detonada=true;
	
	
	
	private AnimatedSprite sprCentro;
	private AnimatedSprite sprCentroDerecha;
	private AnimatedSprite sprCentroIzquierda;
	private AnimatedSprite sprCentroArriba;
	private AnimatedSprite sprCentroAbajo;
	private AnimatedSprite sprArriba_1;
	private AnimatedSprite sprArriba_2;
	//private AnimatedSprite sprArriba_3;
	private AnimatedSprite sprAbajo_1;
	private AnimatedSprite sprAbajo_2;
	//private AnimatedSprite sprAbajo_3;
	private AnimatedSprite sprDerecha_1;
	private AnimatedSprite sprDerecha_2;
	//private AnimatedSprite sprDerecha_3;
	private AnimatedSprite sprIzquierda_1;
	private AnimatedSprite sprIzquierda_2;
	//private AnimatedSprite sprIzquierda_3;
	private AnimatedSprite sprFinArriba;
	private AnimatedSprite sprFinAbajo;
	private AnimatedSprite sprFinDerecha;
	private AnimatedSprite sprFinIzquierda;	
	

	
	private AnimatedSprite sprBomba;
	
	public AnimatedSprite getSprBomba() {
		return sprBomba;
	}



	private static int NoVisible=0; 
	private static int Visible=1; 
	private static int Final=2;
	
	
	
	public enum Posicion {
		Centro(0),
		CentroArriba(1),Arriba_1(2),Arriba_2(3),Arriba_3(4),
		CentroAbajo(5),Abajo_1(6),Abajo_2(7),Abajo_3(8),
		CentroDerecha(9),Derecha_1(10),Derecha_2(11),Derecha_3(12), 
		CentroIzquierda(13),Izquierda_1(14),Izquierda_2(15),Izquierda_3(16),
		FinArriba(17),FinAbajo(18),FinDerecha(19),FinIzquierda(20);
		
		final int value;
		private Posicion(int value) {
			this.value = value;
		}
		public int getValue(){
			return this.value;
		}
	}
	
		
	public Bomba(BomberGame context){
		this.context=context;			
	}
		
	public void cargaTexturas() {
		this.bombaBTA = new BitmapTextureAtlas(context.getTextureManager(),512, 256, TextureOptions.REPEATING_BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(bombaBTA, new TransparentBitmapTextureAtlasSource(512,256), 0, 0);
		this.fuegoBBTA = new BuildableBitmapTextureAtlas(context.getTextureManager(),2048, 256, TextureOptions.REPEATING_NEAREST_PREMULTIPLYALPHA);		
		this.mFuegoCentroTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_centro_r_ani_90.png", 5, 1);
		this.mFuegoCentroAbajoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_centro_abajo_r_ani_90.png", 5, 1);
		this.mFuegoCentroArribaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_centro_arriba_r_ani_90.png", 5, 1);
		this.mFuegoCentroDerechaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_centro_derecha_r_ani_90.png",5, 1);
		this.mFuegoCentroIzquierdaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_centro_izquierda_r_ani_90.png",5, 1);
		this.mFuegoFinAbajoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_fin_abajo_r_ani_90.png", 5, 1);
		this.mFuegoFinArribaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_fin_arriba_r_ani_90.png", 5, 1);
		this.mFuegoFinDerechaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_fin_derecha_r_ani_90.png", 5, 1);
		this.mFuegoFinIzquierdaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_fin_izquierda_r_ani_90.png",5, 1);
		this.mFuegoHorizontalTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_horizontal_r_ani_90.png", 5, 1);
		this.mFuegoVerticalTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.fuegoBBTA, context, "fuego_vertical_r_ani_90.png", 5, 1);
		this.bombaTR=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.bombaBTA, context, "bomba_ani90.png",0,0,4, 2);
		
			
		try {
			fuegoBBTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,2));// posible error gles2
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		context.getEngine().getTextureManager().loadTexture(fuegoBBTA);
		context.getEngine().getTextureManager().loadTexture(bombaBTA);			
	}
	
	
	public void clonarTexturas(Bomba clone){
		this.fuegoBBTA=clone.getFuegoBBTA();
		this.bombaBTA=clone.getBombaBTA();
		this.mFuegoCentroAbajoTR=clone.getmFuegoCentroAbajoTR().deepCopy();
		this.mFuegoCentroArribaTR=clone.getmFuegoCentroArribaTR().deepCopy();
		this.mFuegoCentroDerechaTR=clone.getmFuegoCentroDerechaTR().deepCopy();
		this.mFuegoCentroIzquierdaTR=clone.getmFuegoCentroIzquierdaTR().deepCopy();
		this.mFuegoCentroTR=clone.getmFuegoCentroTR().deepCopy();
		this.mFuegoFinAbajoTR=clone.getmFuegoFinAbajoTR().deepCopy();
		this.mFuegoFinArribaTR=clone.getmFuegoFinArribaTR().deepCopy();
		this.mFuegoFinDerechaTR=clone.getmFuegoFinDerechaTR().deepCopy();
		this.mFuegoFinIzquierdaTR=clone.getmFuegoFinIzquierdaTR().deepCopy();
		this.mFuegoHorizontalTR=clone.getmFuegoHorizontalTR().deepCopy();
		this.mFuegoVerticalTR=clone.getmFuegoVerticalTR().deepCopy();	
		this.bombaTR=clone.getBombaTR().deepCopy();
	}
	
	
	public void creaBatch(){		
		// Srpite maestro
		
		sprCentro = new AnimatedSprite(0, 0, mFuegoCentroTR,context.getVertexBufferObjectManager()){
			public void setAlpha(float pAlpha) {
				sprAbajo_1.setAlpha(pAlpha);
				sprAbajo_2.setAlpha(pAlpha);
				//sprAbajo_3.setAlpha(pAlpha);
				sprArriba_1.setAlpha(pAlpha);
				sprArriba_2.setAlpha(pAlpha);
				//sprArriba_3.setAlpha(pAlpha);
				sprCentroAbajo.setAlpha(pAlpha);
				sprCentroArriba.setAlpha(pAlpha);
				sprCentroDerecha.setAlpha(pAlpha);
				sprCentroIzquierda.setAlpha(pAlpha);
				sprDerecha_1.setAlpha(pAlpha);
				sprDerecha_2.setAlpha(pAlpha);
				//sprDerecha_3.setAlpha(pAlpha);
				sprFinAbajo.setAlpha(pAlpha);
				sprFinArriba.setAlpha(pAlpha);
				sprFinDerecha.setAlpha(pAlpha);
				sprFinIzquierda.setAlpha(pAlpha);
				sprIzquierda_1.setAlpha(pAlpha);
				sprIzquierda_2.setAlpha(pAlpha);
				//sprIzquierda_3.setAlpha(pAlpha);
				super.setAlpha(pAlpha);
			}

			@Override
			public void setCurrentTileIndex(int pTileIndex) {
				sprAbajo_1.setCurrentTileIndex(pTileIndex);
				sprAbajo_2.setCurrentTileIndex(pTileIndex);
				//sprAbajo_3.setCurrentTileIndex(pTileIndex);
				sprArriba_1.setCurrentTileIndex(pTileIndex);
				sprArriba_2.setCurrentTileIndex(pTileIndex);
				//sprArriba_3.setCurrentTileIndex(pTileIndex);
				sprCentroAbajo.setCurrentTileIndex(pTileIndex);
				sprCentroArriba.setCurrentTileIndex(pTileIndex);
				sprCentroDerecha.setCurrentTileIndex(pTileIndex);
				sprCentroIzquierda.setCurrentTileIndex(pTileIndex);
				sprDerecha_1.setCurrentTileIndex(pTileIndex);
				sprDerecha_2.setCurrentTileIndex(pTileIndex);
				//sprDerecha_3.setCurrentTileIndex(pTileIndex);
				sprFinAbajo.setCurrentTileIndex(pTileIndex);
				sprFinArriba.setCurrentTileIndex(pTileIndex);
				sprFinDerecha.setCurrentTileIndex(pTileIndex);
				sprFinIzquierda.setCurrentTileIndex(pTileIndex);
				sprIzquierda_1.setCurrentTileIndex(pTileIndex);
				sprIzquierda_2.setCurrentTileIndex(pTileIndex);
				//sprIzquierda_3.setCurrentTileIndex(pTileIndex);
				super.setCurrentTileIndex(pTileIndex);
			}			
					
		};
		
		sprCentroDerecha = new AnimatedSprite(sprCentro.getX()+sprCentro.getWidth(), sprCentro.getY(), mFuegoCentroDerechaTR,context.getVertexBufferObjectManager());
		sprCentroIzquierda = new AnimatedSprite(sprCentro.getX()-sprCentro.getWidth(), sprCentro.getY(), mFuegoCentroIzquierdaTR,context.getVertexBufferObjectManager());
		sprCentroArriba = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-sprCentro.getHeight(), mFuegoCentroArribaTR,context.getVertexBufferObjectManager());
		sprCentroAbajo = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+sprCentro.getHeight(), mFuegoCentroAbajoTR,context.getVertexBufferObjectManager());
		sprArriba_1 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-(sprCentro.getHeight()*2), mFuegoVerticalTR,context.getVertexBufferObjectManager());
		sprArriba_2 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-(sprCentro.getHeight()*3), mFuegoVerticalTR,context.getVertexBufferObjectManager());
		//sprArriba_3 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-(sprCentro.getHeight()*4), mFuegoVerticalTR);
		sprAbajo_1 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+(sprCentro.getHeight()*2), mFuegoVerticalTR,context.getVertexBufferObjectManager());
		sprAbajo_2 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+(sprCentro.getHeight()*3), mFuegoVerticalTR,context.getVertexBufferObjectManager());
		//sprAbajo_3 = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+(sprCentro.getHeight()*4), mFuegoVerticalTR);
		
		//sprDerecha_1 = new AnimatedSprite(sprCentro.getX()+(sprCentro.getBaseWidth()*2), sprCentro.getY(), mFuegoHorizontalTR);//gles1
		//sprDerecha_2 = new AnimatedSprite(sprCentro.getX()+(sprCentro.getBaseWidth()*3), sprCentro.getY(), mFuegoHorizontalTR);//gles1		
		sprDerecha_1 = new AnimatedSprite(sprCentro.getX()+(sprCentro.getWidth()*2), sprCentro.getY(), mFuegoHorizontalTR,context.getVertexBufferObjectManager());//gles2
		sprDerecha_2 = new AnimatedSprite(sprCentro.getX()+(sprCentro.getWidth()*3), sprCentro.getY(), mFuegoHorizontalTR,context.getVertexBufferObjectManager());//gles2
		
		//sprDerecha_3 = new AnimatedSprite(sprCentro.getX()+(sprCentro.getBaseWidth()*4), sprCentro.getY(), mFuegoHorizontalTR);
		
		
		
//		sprIzquierda_1 = new AnimatedSprite(sprCentro.getX()-(sprCentro.getBaseWidth()*2), sprCentro.getY(), mFuegoHorizontalTR);//gles1
//		sprIzquierda_2 = new AnimatedSprite(sprCentro.getX()-(sprCentro.getBaseWidth()*3), sprCentro.getY(), mFuegoHorizontalTR);//gles1		
		sprIzquierda_1 = new AnimatedSprite(sprCentro.getX()-(sprCentro.getWidth()*2), sprCentro.getY(), mFuegoHorizontalTR,context.getVertexBufferObjectManager());//gles2
		sprIzquierda_2 = new AnimatedSprite(sprCentro.getX()-(sprCentro.getWidth()*3), sprCentro.getY(), mFuegoHorizontalTR,context.getVertexBufferObjectManager());//gles2
		
		//sprIzquierda_3 = new AnimatedSprite(sprCentro.getX()-(sprCentro.getBaseWidth()*4), sprCentro.getY(), mFuegoHorizontalTR);
		
		sprFinArriba = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()-(sprCentro.getHeight()*4), mFuegoFinArribaTR,context.getVertexBufferObjectManager());
		sprFinAbajo = new AnimatedSprite(sprCentro.getX(), sprCentro.getY()+(sprCentro.getHeight()*4), mFuegoFinAbajoTR,context.getVertexBufferObjectManager());
		
//		sprFinDerecha = new AnimatedSprite(sprCentro.getX()+(sprCentro.getBaseWidth()*4), sprCentro.getY(), mFuegoFinDerechaTR);//gles1
//		sprFinIzquierda = new AnimatedSprite(sprCentro.getX()-(sprCentro.getBaseWidth()*4), sprCentro.getY(), mFuegoFinIzquierdaTR);//gles1
		
		sprFinDerecha = new AnimatedSprite(sprCentro.getX()+(sprCentro.getWidth()*4), sprCentro.getY(), mFuegoFinDerechaTR,context.getVertexBufferObjectManager());//gles2
		sprFinIzquierda = new AnimatedSprite(sprCentro.getX()-(sprCentro.getWidth()*4), sprCentro.getY(), mFuegoFinIzquierdaTR,context.getVertexBufferObjectManager());//gles2
		
		sprBomba= new AnimatedSprite(0, 0, bombaTR,context.getVertexBufferObjectManager());
		sprBomba.setZIndex(BomberGame.ZINDEX_BOMBAS);
		
		////System.out.println("es nulo fuegoBBTA"+(fuegoBBTA==null));
		 batch = new SpriteGroup(fuegoBBTA, 21,context.getVertexBufferObjectManager());
		 
		 batch.attachChild(sprAbajo_1);
		 batch.attachChild(sprAbajo_2);
		// batch.attachChild(sprAbajo_3);
		 
		 batch.attachChild(sprArriba_1);
		 batch.attachChild(sprArriba_2);
		 //batch.attachChild(sprArriba_3);
		 
		 batch.attachChild(sprDerecha_1);
		 batch.attachChild(sprDerecha_2);
		 //batch.attachChild(sprDerecha_3);
		 
		 batch.attachChild(sprIzquierda_1);
		 batch.attachChild(sprIzquierda_2);
		 //batch.attachChild(sprIzquierda_3);
		 
		 batch.attachChild(sprCentro);
		 
		 batch.attachChild(sprCentroAbajo);
		 batch.attachChild(sprCentroArriba);
		 batch.attachChild(sprCentroDerecha);
		 batch.attachChild(sprCentroIzquierda);
		 
		 batch.attachChild(sprFinAbajo);
		 batch.attachChild(sprFinArriba);
		 batch.attachChild(sprFinDerecha);
		 batch.attachChild(sprFinIzquierda);		
		
		batch.setVisible(false);
		sprBomba.setVisible(false);
		batch.setZIndex(BomberGame.ZINDEX_FUEGO);
		batch.setIgnoreUpdate(true);
		
		batch.setScaleCenter(0, 0);
		batch.setScale(0.5f);
		
		sprBomba.setScaleCenter(0, 0);
		sprBomba.setScale(0.5f);
		
		context.getEscenaJuego().attachChild(sprBomba);	
		context.getEscenaJuego().attachChild(batch);
		context.getEscenaJuego().sortChildren();	

		//batch.setPosition(sprCentro.getWidth()*4, sprCentro.getHeight()*4);	
		
	}
	
	
	public SpriteGroup getBatch() {
		return batch;
	}

	public void plantarBomba(int tamExplosion, int secuencia){
		////System.out.println("plantobomba");
		reiniciaBatch();
		//context.getGameManager().getMatriz().pintaMatriz();
		 int posX=context.getBomberman().getColumna();
		 int posY=context.getBomberman().getFila();

		if (almacen.hayBomba(posX, posY)) return;    // kitado ahora para las pruebas ponerlo despues
		if (context.getGameManager().getMatriz().getValor(posY, posX)!=BomberGame.NADA)return;
		this.posX=posX;
		this.posY=posY;
		context.getGameManager().getMatriz().setValor(BomberGame.BOMBA, this.posY, this.posX);	
		almacen.getContador().BombaPuesta();
		this.context.getResouceManager().sonidoPlantarBombaPlay();
		this.secuencia=secuencia;
		this.tamExplosion=tamExplosion;
		setDetonada(false);// se comprueba en la llamada que no este explotada
		batch.setPosition(context.getGameManager().getCurrentTileRectangle().getX(),context.getGameManager().getCurrentTileRectangle().getY());
		sprBomba.setPosition(context.getGameManager().getCurrentTileRectangle().getX()+2,context.getGameManager().getCurrentTileRectangle().getY()-10);
		if (!sprBomba.hasParent())context.getEngine().getScene().attachChild(sprBomba);
		sprBomba.setVisible(true);
		if (!context.getGameManager().getPolvorin().isControlRemoto()){
			sprBomba.animate(TIEMPO, 2,new ListenerDetonador());				
			sonarMecha();
		}else{
			sprBomba.animate(TIEMPO, 1000);
			//mecha.setLooping(true);
			sonarMecha();
			
		}
	}
	
	
	public void sonarMecha(){
		
		context.getResouceManager().sonarMecha();
		
		/*
		if (context.getResouceManager().isSonido()){
			try{
				float vol= Preferencias.leerPreferenciasInt("sound_level");
				if (vol==-1){
					vol=5;
				}
				mecha.setVolume((vol/10)*2);
				mecha.play();
			}catch(Exception e){e.printStackTrace();}
		}*/
	}
	public void preDesarmar(){
		if (isDetonada()) return;
		sprBomba.clearUpdateHandlers();
		sprBomba.clearEntityModifiers();
		sprBomba.stopAnimation();
	}
	
	public void  desarmarBomba(){
		if (isDetonada()) return;
		almacen.getContador().BombaExplotada();	
		context.getGameManager().getMatriz().setValor(BomberGame.NADA, this.posY, this.posX);	
		sprBomba.clearEntityModifiers();
		sprBomba.setVisible(false);
		setDetonada(true);
		pararSonidoMecha();
		
	}
	
	public synchronized void detonar(final boolean sound){		
		
		////System.out.println("detonarrr"+isDetonada());
		if (isDetonada()) return;
		setDetonada(true);
		pararSonidoMecha();
		//if (sound)context.getExplosion().play();
		context.getResouceManager().sonidoExplosionPlay();
		sprBomba.setVisible(false);
		context.getGameManager().getMatriz().setValor(BomberGame.NADA, this.posY, this.posX);		
		context.vibrar(300);
		creaFragmentoExplosiones();		// ya crea la cadena de bombas
		batch.setVisible(true);
		cadenaDetonacion();
		almacen.getContador().BombaExplotada();	
		
		
		try{context.getGameManager().getBomberman().matar(coordenadas);}catch(Exception e){System.out.println("error detonar matar");}
		try{context.getGameManager().getCapaParedes().explota(coordenadasParedes);}catch(Exception e){System.out.println("error detonar explota");}
		try{context.getGameManager().getMiAlmacen().matar(coordenadas, numeroBomba);}catch(Exception e){System.out.println("error detonar mataren");}
		new Thread(){
			public void run() {
				try {
					Thread.sleep(500);
					try{context.getGameManager().getMiAlmacen().matar(coordenadas,numeroBomba);}catch(Exception e){System.out.println("error detonar mataren");}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			};
		}.start();
		
		sprCentro.animate(ANIMATE_DURATION, false, new ListenerExplotar());
		
	}

	public void detonarContiempo(float secs){
		
		getBomba().registerEntityModifier(new DelayModifier(secs){
			@Override
			protected void onModifierFinished(IEntity pItem) {
				// TODO Auto-generated method stub
				detonar(true);
			}
		});		
		
	}
	
	public void cadenaDetonacion(){		
		for (Coordenadas coordenadasBomba : coordenadasBombas) {
			////System.out.println("coordenadasBomba.getColumna()"+coordenadasBomba.getColumna()+" coordenadasBomba.getFila()"+ coordenadasBomba.getFila());
			try{
				almacen.getBombaXY(coordenadasBomba.getColumna(), coordenadasBomba.getFila()).detonarContiempo(0.1f);			
			}catch (Exception e){
				context.getGameManager().getMatriz().pintaMatriz();
				e.printStackTrace();
			}
		}
	}
	
	public void reiniciaBatch() {
		
		sprCentro.setVisible(false);			
		sprAbajo_1.setVisible(false);
		sprAbajo_2.setVisible(false);	
	//	sprAbajo_3.setVisible(false);	
		sprArriba_1.setVisible(false);	
		sprArriba_2.setVisible(false);	
		//sprArriba_3.setVisible(false);	
		sprCentroAbajo.setVisible(false);	
		sprCentroArriba.setVisible(false);	
		sprCentroDerecha.setVisible(false);	
		sprCentroIzquierda.setVisible(false);	
		sprDerecha_1.setVisible(false);	
		sprDerecha_2.setVisible(false);	
		//sprDerecha_3.setVisible(false);	
		sprFinAbajo.setVisible(false);	
		sprFinArriba.setVisible(false);	
		sprFinDerecha.setVisible(false);	
		sprFinIzquierda.setVisible(false);	
		sprIzquierda_1.setVisible(false);	
		sprIzquierda_2.setVisible(false);	
		//sprIzquierda_3.setVisible(false);	
		
		
	}
	
	
public void matarMonedas(){
	
	// descomentar luego para matar las monedas
/*
		
		Iterator<Moneda> monedasitr=context.getGameManager().getMonedero().getAlmacen().iterator();
		
		while (monedasitr.hasNext()){
			Moneda mo =monedasitr.next();
			if (mo.getEstado()==Estado.PLANTADA){
				if (sprAbajo_1.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprAbajo_2.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				//else if (sprAbajo_3.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprArriba_1.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprArriba_2.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				//else if (sprArriba_3.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprCentroAbajo.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprCentroArriba.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprCentroDerecha.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprCentroIzquierda.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprDerecha_2.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprFinAbajo.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprFinArriba.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprFinDerecha.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprFinIzquierda.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprIzquierda_1.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
				else if (sprIzquierda_2.collidesWith(mo.getCuadradoMascara()))mo.monedaExplotada();
			}			
		}

		*/
	}
	
	
	
	public void creaFragmentoExplosiones(){
		int [][] matriz = context.getGameManager().getMatriz().getMatrizmuros();
		coordenadas= new ArrayList<Coordenadas>();
		coordenadasBombas= new ArrayList<Coordenadas>();
		coordenadasParedes= new ArrayList<Coordenadas>();
		
		batch.setIgnoreUpdate(false);
		
		//reiniciaBatch();
		sprCentro.setVisible(true);	
		coordenadas.add(new Coordenadas(posX, posY));
		cruzArriba(matriz);
		cruzAbajo(matriz);
		cruzDerecha(matriz);
		cruzIzquierda(matriz);			
	}
	
	
	
	public void cruzArriba(int [][]matriz){		
		
		//nivel 1
		if (matriz[posY-1][posX]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX, posY-1,numeroBomba);
		}else if (matriz[posY-1][posX]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
				
		if(matriz[posY-1][posX]==BomberGame.BOMBA||matriz[posY-1][posX]==BomberGame.PARED){			
			sprFinArriba.setPosition(sprCentroArriba);
			sprFinArriba.setVisible(true);
			coordenadas.add(new Coordenadas(posX, posY-1));
			if(matriz[posY-1][posX]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX, posY-1));
			if(matriz[posY-1][posX]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX, posY-1));
			return;
		}else if (matriz[posY-1][posX]==BomberGame.PIEDRA){
			// la primera vez nada
			return;
		}else if (matriz[posY-1][posX]<=BomberGame.NADA && this.tamExplosion==1){			
			sprFinArriba.setPosition(sprCentroArriba);
			sprFinArriba.setVisible(true);
			coordenadas.add(new Coordenadas(posX, posY-1));
			return;
		}else if (matriz[posY-1][posX]<=BomberGame.NADA && this.tamExplosion>1){
			sprCentroArriba.setVisible(true);
			coordenadas.add(new Coordenadas(posX, posY-1));
		}
		
		//nivel 2		
		
		if (matriz[posY-2][posX]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX, posY-2,numeroBomba);
		}else if (matriz[posY-2][posX]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if(matriz[posY-2][posX]==BomberGame.BOMBA||matriz[posY-2][posX]==BomberGame.PARED){
			sprFinArriba.setVisible(true);
			sprFinArriba.setPosition(sprArriba_1);
			coordenadas.add(new Coordenadas(posX, posY-2));
			if(matriz[posY-2][posX]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX, posY-2));
			if(matriz[posY-2][posX]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX, posY-2));
			return;
		}else if (matriz[posY-2][posX]==BomberGame.PIEDRA){
			sprCentroArriba.setVisible(false);
			sprFinArriba.setVisible(true);;
			sprFinArriba.setPosition(sprCentroArriba);
			return;
		}else if (matriz[posY-2][posX]<=BomberGame.NADA && this.tamExplosion==2){
			sprFinArriba.setVisible(true);
			sprFinArriba.setPosition(sprArriba_1);
			coordenadas.add(new Coordenadas(posX, posY-2));
			return;
		}else if (matriz[posY-2][posX]<=BomberGame.NADA && this.tamExplosion>2){
			sprArriba_1.setVisible(true);
			coordenadas.add(new Coordenadas(posX, posY-2));
		}
		
		// nivel 3
		
		if (matriz[posY-3][posX]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX, posY-3,numeroBomba);
		}else if (matriz[posY-3][posX]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if (matriz[posY - 3][posX] == BomberGame.BOMBA||matriz[posY - 3][posX] == BomberGame.PARED) {
			sprFinArriba.setVisible(true);
			sprFinArriba.setPosition(sprArriba_2);
			coordenadas.add(new Coordenadas(posX, posY - 3));
			if(matriz[posY-3][posX]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX, posY-3));
			if(matriz[posY-3][posX]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX, posY-3));
			return;
		} else if (matriz[posY - 3][posX] == BomberGame.PIEDRA) {
			sprArriba_1.setVisible(false);
			sprFinArriba.setVisible(true);
			sprFinArriba.setPosition(sprArriba_1);
			return;
		} else if (matriz[posY - 3][posX] <= BomberGame.NADA && this.tamExplosion == 3) {
			sprFinArriba.setVisible(true);
			sprFinArriba.setPosition(sprArriba_2);
			coordenadas.add(new Coordenadas(posX, posY - 3));
			return;
		} else if (matriz[posY - 3][posX] <= BomberGame.NADA && this.tamExplosion > 3) {
			sprArriba_2.setVisible(true);
			coordenadas.add(new Coordenadas(posX, posY - 3));
		}
		
		// nivel 4
		
		if (matriz[posY-4][posX]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX, posY-4,numeroBomba);
		}else if (matriz[posY-4][posX]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if (matriz[posY - 4][posX] == BomberGame.BOMBA||matriz[posY - 4][posX] == BomberGame.PARED) {
			sprFinArriba.setVisible(true);
//			sprFinArriba.setPosition(sprFinArriba.getInitialX(),sprFinArriba.getInitialY());//gles1
			sprFinArriba.setPosition(0,0);//gles2
			
			coordenadas.add(new Coordenadas(posX, posY - 4));
			if(matriz[posY-4][posX]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX, posY-4));
			if(matriz[posY-4][posX]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX, posY-4));
			return;
		} else if (matriz[posY - 4][posX] == BomberGame.PIEDRA) {
			sprArriba_2.setVisible(false);
			sprFinArriba.setVisible(true);
			sprFinArriba.setPosition(sprArriba_2);
			return;
		} else if (matriz[posY - 4][posX] <= BomberGame.NADA && this.tamExplosion == 4) {
			sprFinArriba.setVisible(true);
//			sprFinArriba.setPosition(sprFinArriba.getInitialX(),sprFinArriba.getInitialY());//gles1
			sprFinArriba.setPosition(0,0);//gles2
			coordenadas.add(new Coordenadas(posX, posY - 4));
			return;
		} 
		/*else if (matriz[posY - 4][posX] == BomberGame.NADA && this.tamExplosion > 4) {
			cruz[Posicion.Arriba_3.getValue()] = Visible;
			coordenadas.add(new Coordenadas(posX, posY - 4));
		}*/
		

		// no va a haber nivel 5
	}
	
	
	public void cruzAbajo(int [][]matriz){		
		
		//nivel 1
		if (matriz[posY+1][posX]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX, posY+1,numeroBomba);
		}else if (matriz[posY+1][posX]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if(matriz[posY+1][posX]==BomberGame.BOMBA ||matriz[posY+1][posX]==BomberGame.PARED){
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprCentroAbajo);
			coordenadas.add(new Coordenadas(posX, posY+1));
			if(matriz[posY+1][posX]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX, posY+1));
			if(matriz[posY+1][posX]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX, posY+1));
			return;
		}else if (matriz[posY+1][posX]==BomberGame.PIEDRA){
			// la primera vez nada
			return;
		}else if (matriz[posY+1][posX]<=BomberGame.NADA && this.tamExplosion==1){
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprCentroAbajo);
			coordenadas.add(new Coordenadas(posX, posY+1));
			return;
		}else if (matriz[posY+1][posX]<=BomberGame.NADA && this.tamExplosion>1){
			sprCentroAbajo.setVisible(true);
			coordenadas.add(new Coordenadas(posX, posY+1));
		}
		
		//nivel 2		
		if (matriz[posY+2][posX]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX, posY+2,numeroBomba);
		}else if (matriz[posY+2][posX]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if(matriz[posY+2][posX]==BomberGame.BOMBA||matriz[posY+2][posX]==BomberGame.PARED){
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprAbajo_1);
			coordenadas.add(new Coordenadas(posX, posY+2));
			if(matriz[posY+2][posX]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX, posY+2));
			if(matriz[posY+2][posX]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX, posY+2));
			return;
		}else if (matriz[posY+2][posX]==BomberGame.PIEDRA){
			sprCentroAbajo.setVisible(false);
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprCentroAbajo);
			return;
		}else if (matriz[posY+2][posX]<=BomberGame.NADA && this.tamExplosion==2){
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprAbajo_1);
			coordenadas.add(new Coordenadas(posX, posY+2));
			return;
		}else if (matriz[posY+2][posX]<=BomberGame.NADA && this.tamExplosion>2){
			sprAbajo_1.setVisible(true);
			coordenadas.add(new Coordenadas(posX, posY+2));
		}
		
		// nivel 3
		
		if (matriz[posY+3][posX]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX, posY+3,numeroBomba);
		}else if (matriz[posY+3][posX]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if (matriz[posY + 3][posX] == BomberGame.BOMBA||matriz[posY + 3][posX] == BomberGame.PARED) {			
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprAbajo_2);
			coordenadas.add(new Coordenadas(posX, posY + 3));
			if(matriz[posY+3][posX]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX, posY+3));
			if(matriz[posY+3][posX]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX, posY+3));
			return;
		} else if (matriz[posY + 3][posX] == BomberGame.PIEDRA) {
			sprAbajo_1.setVisible(false);
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprAbajo_1);
			return;
		} else if (matriz[posY + 3][posX] <= BomberGame.NADA && this.tamExplosion == 3) {			
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprAbajo_2);
			coordenadas.add(new Coordenadas(posX, posY + 3));
			return;
		} else if (matriz[posY + 3][posX] <= BomberGame.NADA && this.tamExplosion > 3) {			
			sprAbajo_2.setVisible(true);
			coordenadas.add(new Coordenadas(posX, posY + 3));
		}
		
		// nivel 4
		if (matriz[posY+4][posX]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX, posY+4,numeroBomba);
		}else if (matriz[posY+4][posX]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if (matriz[posY + 4][posX] == BomberGame.BOMBA||matriz[posY + 4][posX] == BomberGame.PARED) {			
			sprFinAbajo.setVisible(true);
//			sprFinAbajo.setPosition(sprFinAbajo.getInitialX(),sprFinAbajo.getInitialY());//gles1
			sprFinAbajo.setPosition(0,0);//gles2
			coordenadas.add(new Coordenadas(posX, posY + 4));
			if(matriz[posY+4][posX]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX, posY+4));
			if(matriz[posY+4][posX]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX, posY+4));
			return;
		} else if (matriz[posY + 4][posX] == BomberGame.PIEDRA) {
			sprAbajo_2.setVisible(false);
			sprFinAbajo.setVisible(true);
			sprFinAbajo.setPosition(sprAbajo_2);
			return;
		} else if (matriz[posY + 4][posX] <= BomberGame.NADA && this.tamExplosion == 4) {			
			sprFinAbajo.setVisible(true);
//			sprFinAbajo.setPosition(sprFinAbajo.getInitialX(),sprFinAbajo.getInitialY());//gles1
			sprFinAbajo.setPosition(0,0);//gles2
			coordenadas.add(new Coordenadas(posX, posY + 4));
			return;
		} 
		
	}
	
	public void cruzIzquierda(int [][]matriz){		
		
		//nivel 1
		
		if (matriz[posY][posX-1]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX-1, posY,numeroBomba);
		}else if (matriz[posY][posX-1]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if(matriz[posY][posX-1]==BomberGame.BOMBA||matriz[posY][posX-1]==BomberGame.PARED){
			sprFinIzquierda.setVisible(true);
			sprFinIzquierda.setPosition(sprCentroIzquierda);
			coordenadas.add(new Coordenadas(posX-1, posY));
			if(matriz[posY][posX-1]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX-1, posY));
			if(matriz[posY][posX-1]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX-1, posY));
			return;
		}else if (matriz[posY][posX-1]==BomberGame.PIEDRA){
			// la primera vez nada
			return;
		}else if (matriz[posY][posX-1]<=BomberGame.NADA && this.tamExplosion==1){
			sprFinIzquierda.setVisible(true);
			sprFinIzquierda.setPosition(sprCentroIzquierda);
			coordenadas.add(new Coordenadas(posX-1, posY));
			return;
		}else if (matriz[posY][posX-1]<=BomberGame.NADA && this.tamExplosion>1){			
			sprCentroIzquierda.setVisible(true);
			coordenadas.add(new Coordenadas(posX-1, posY));
		}
		
		//nivel 2		
		if (matriz[posY][posX-2]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX-2, posY,numeroBomba);
		}else if (matriz[posY][posX-2]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		if(matriz[posY][posX-2]==BomberGame.BOMBA||matriz[posY][posX-2]==BomberGame.PARED){		
			////System.out.println("22");			
			sprIzquierda_1.setVisible(false);
			sprFinIzquierda.setVisible(true);			
			sprFinIzquierda.setPosition(sprIzquierda_1);
			coordenadas.add(new Coordenadas(posX-2, posY));					
			if(matriz[posY][posX-2]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX-2, posY));
			if(matriz[posY][posX-2]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX-2, posY));
			return;
		}else if (matriz[posY][posX-2]==BomberGame.PIEDRA){
			////System.out.println("33");
			sprCentroIzquierda.setVisible(false);
			sprFinIzquierda.setVisible(true);
			sprFinIzquierda.setPosition(sprCentroIzquierda);
			return;
		}else if (matriz[posY][posX-2]<=BomberGame.NADA && this.tamExplosion==2){
			////System.out.println("44");
			sprFinIzquierda.setVisible(true);
			sprFinIzquierda.setPosition(sprIzquierda_1);
			coordenadas.add(new Coordenadas(posX-2, posY));
			return;
		}else if (matriz[posY][posX-2]<=BomberGame.NADA && this.tamExplosion>2){
			////System.out.println("55");
			sprIzquierda_1.setVisible(true);
			coordenadas.add(new Coordenadas(posX-2, posY));
		}
		
		// nivel 3
		if (matriz[posY][posX-3]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX-3, posY,numeroBomba);
		}else if (matriz[posY][posX-3]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if (matriz[posY][posX - 3] == BomberGame.BOMBA||matriz[posY][posX - 3] == BomberGame.PARED) {
			sprFinIzquierda.setVisible(true);
			sprFinIzquierda.setPosition(sprIzquierda_2);
			coordenadas.add(new Coordenadas(posX - 3, posY));
			if(matriz[posY][posX-3]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX-3, posY));
			if(matriz[posY][posX-3]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX-3, posY));
			return;
		} else if (matriz[posY][posX - 3] == BomberGame.PIEDRA) {
			sprIzquierda_1.setVisible(false);
			sprFinIzquierda.setVisible(true);
			sprFinIzquierda.setPosition(sprIzquierda_1);
			return;
		} else if (matriz[posY ][posX- 3] <= BomberGame.NADA && this.tamExplosion == 3) {
			sprFinIzquierda.setVisible(true);
			sprFinIzquierda.setPosition(sprIzquierda_2);
			coordenadas.add(new Coordenadas(posX - 3, posY));
			return;
		} else if (matriz[posY ][posX- 3] <= BomberGame.NADA && this.tamExplosion > 3) {
			sprIzquierda_2.setVisible(true);
			coordenadas.add(new Coordenadas(posX- 3, posY ));
		}
		
		// nivel 4
		if (matriz[posY][posX-4]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX-4, posY,numeroBomba);
		}else if (matriz[posY][posX-4]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		if (matriz[posY][posX - 4] == BomberGame.BOMBA||matriz[posY][posX - 4] == BomberGame.PARED) {
			sprFinIzquierda.setVisible(true);
//			sprFinIzquierda.setPosition(sprFinIzquierda.getInitialX(),sprFinIzquierda.getInitialY());///gles1
			sprFinIzquierda.setPosition(0,0);//gles2
			coordenadas.add(new Coordenadas(posX - 4, posY));
			if(matriz[posY][posX-4]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX-4, posY));
			if(matriz[posY][posX-4]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX-4, posY));
			return;
		} else if (matriz[posY ][posX- 4] == BomberGame.PIEDRA) {
			sprIzquierda_2.setVisible(false);
			sprFinIzquierda.setVisible(true);
			sprFinIzquierda.setPosition(sprIzquierda_2);
			return;
		} else if (matriz[posY ][posX- 4] <= BomberGame.NADA && this.tamExplosion == 4) {
			sprFinIzquierda.setVisible(true);
//			sprFinIzquierda.setPosition(sprFinIzquierda.getInitialX(),sprFinIzquierda.getInitialY());//gles1
			sprFinIzquierda.setPosition(0,0);//gles2
			coordenadas.add(new Coordenadas(posX- 4, posY ));
			return;
		} 
		
	}
	
	
	
	public void cruzDerecha(int [][]matriz){		
		
		//nivel 1
		if (matriz[posY][posX+1]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX+1, posY,numeroBomba);
		}else if (matriz[posY][posX+1]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if(matriz[posY][posX+1]==BomberGame.BOMBA||matriz[posY][posX+1]==BomberGame.PARED){
			sprFinDerecha.setVisible(true);
			sprFinDerecha.setPosition(sprCentroDerecha);
			coordenadas.add(new Coordenadas(posX+1, posY));
			if(matriz[posY][posX+1]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX+1, posY));
			if(matriz[posY][posX+1]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX+1, posY));
			return;
		}else if (matriz[posY][posX+1]==BomberGame.PIEDRA){
			// la primera vez nada
			return;
		}else if (matriz[posY][posX+1]<=BomberGame.NADA && this.tamExplosion==1){
			sprFinDerecha.setVisible(true);
			sprFinDerecha.setPosition(sprCentroDerecha);
			coordenadas.add(new Coordenadas(posX+1, posY));
			return;
		}else if (matriz[posY][posX+1]<=BomberGame.NADA && this.tamExplosion>1){
			sprCentroDerecha.setVisible(true);
			coordenadas.add(new Coordenadas(posX+1, posY));
		}
		
		//nivel 2		
		if (matriz[posY][posX+2]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX+2, posY,numeroBomba);
		}else if (matriz[posY][posX+2]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		
		if(matriz[posY][posX+2]==BomberGame.BOMBA||matriz[posY][posX+2]==BomberGame.PARED){
			sprFinDerecha.setVisible(true);
			sprFinDerecha.setPosition(sprDerecha_1);
			coordenadas.add(new Coordenadas(posX+2, posY));
			if(matriz[posY][posX+2]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX+2, posY));
			if(matriz[posY][posX+2]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX+2, posY));
			return;
		}else if (matriz[posY][posX+2]==BomberGame.PIEDRA){
			sprCentroDerecha.setVisible(false);
			sprFinDerecha.setVisible(true);
			sprFinDerecha.setPosition(sprCentroDerecha);
			return;
		}else if (matriz[posY][posX+2]<=BomberGame.NADA && this.tamExplosion==2){
			sprFinDerecha.setVisible(true);
			sprFinDerecha.setPosition(sprDerecha_1);
			coordenadas.add(new Coordenadas(posX+2, posY));
			return;
		}else if (matriz[posY][posX+2]<=BomberGame.NADA && this.tamExplosion>2){
			sprDerecha_1.setVisible(true);
			coordenadas.add(new Coordenadas(posX+2, posY));
		}
		
		// nivel 3
		if (matriz[posY][posX+3]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX+3, posY,numeroBomba);
		}else if (matriz[posY][posX+3]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		if (matriz[posY][posX + 3] == BomberGame.BOMBA||matriz[posY][posX + 3] == BomberGame.PARED) {
			sprFinDerecha.setVisible(true);
			sprFinDerecha.setPosition(sprDerecha_2);
			coordenadas.add(new Coordenadas(posX + 3, posY));
			if(matriz[posY][posX+3]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX+3, posY));
			if(matriz[posY][posX+3]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX+3, posY));
			return;
		} else if (matriz[posY][posX + 3] == BomberGame.PIEDRA) {
			sprDerecha_1.setVisible(false);
			sprFinDerecha.setPosition(sprDerecha_1);
			sprFinDerecha.setVisible(true);			
			return;
		} else if (matriz[posY ][posX+ 3] <= BomberGame.NADA && this.tamExplosion == 3) {
			sprFinDerecha.setVisible(true);
			sprFinDerecha.setPosition(sprDerecha_2);
			coordenadas.add(new Coordenadas(posX + 3, posY));
			return;
		} else if (matriz[posY ][posX+ 3] <= BomberGame.NADA && this.tamExplosion > 3) {
			sprDerecha_2.setVisible(true);
			coordenadas.add(new Coordenadas(posX+ 3, posY ));
		}
		
		// nivel 4
		if (matriz[posY][posX+4]==BomberGame.MONEDA){
			context.getGameManager().getMonedero().explotadoMoneda(posX+4, posY,numeroBomba);
		}else if (matriz[posY][posX+4]==BomberGame.PUERTA){
			context.getGameManager().getMonedero().explotadoPuerta(numeroBomba);
		}
		if (matriz[posY][posX + 4] == BomberGame.BOMBA||matriz[posY][posX + 4] == BomberGame.PARED) {
			sprFinDerecha.setVisible(true);
//			sprFinDerecha.setPosition(sprFinDerecha.getInitialX(),sprFinDerecha.getInitialY());//gles1
			sprFinDerecha.setPosition(0,0);//gles2
			coordenadas.add(new Coordenadas(posX + 4, posY));
			if(matriz[posY][posX+4]==BomberGame.BOMBA)coordenadasBombas.add(new Coordenadas(posX+4, posY));
			if(matriz[posY][posX+4]==BomberGame.PARED)coordenadasParedes.add(new Coordenadas(posX+4, posY));
			return;
		} else if (matriz[posY ][posX+ 4] == BomberGame.PIEDRA) {
			sprDerecha_2.setVisible(false);
			sprFinDerecha.setVisible(true);
			sprFinDerecha.setPosition(sprDerecha_2);
			return;
		}else if (matriz[posY ][posX+ 4] <= BomberGame.NADA) {
			sprFinDerecha.setVisible(true);
//			sprFinDerecha.setPosition(sprFinDerecha.getInitialX(),sprFinDerecha.getInitialY());//gles1
			sprFinDerecha.setPosition(0,0);//gles2
			coordenadas.add(new Coordenadas(posX+ 4, posY ));
			return;
		} 		
	}
	
	

	
	
	public void iniciaTemporizador(){
		
	}
		
	


	
	public int getPos(){
		return secuencia;
	}
	
	
	

	
	
	public void setAlmacen(AlmacenBombas almacen) {
		this.almacen=almacen;
	}
	
	public void setPosition(float pX, float pY) {
		// TODO Auto-generated method stub
		batch.setPosition(pX, pY);
	}
	
	public float getX() {
		// TODO Auto-generated method stub
		return batch.getX();
	}
	
	public float getY() {
		// TODO Auto-generated method stub
		return batch.getY();
	}
	
	public int getColumna() {
		// TODO Auto-generated method stub
		return posX;
	}
	public int getFila() {
		// TODO Auto-generated method stub
		return posY;
	}
	
	


	
	public void explota(int columna, int fila){
		
	}
	
	public void pararSonidoMecha(){
		context.getResouceManager().pararMecha();
		//try{mecha.stop();}catch(Exception e){}
	}
	
	public void setDetonada(boolean detonada) {
		synchronized (this.detonada) {
			this.detonada = detonada;
		}		
	}

	public Boolean isDetonada() {
		synchronized (this.detonada) {
			return this.detonada;
		}		
	}
		public boolean enPosicion(int posX, int posY){
		if (this.posX==posX && this.posY==posY)return true;
		else return false;				
	}
	public TiledTextureRegion getmFuegoCentroTR() {
		return mFuegoCentroTR;
	}

	public TiledTextureRegion getBombaTR() {
		return bombaTR;
	}
	public TiledTextureRegion getmFuegoCentroDerechaTR() {
		return mFuegoCentroDerechaTR;
	}


	public TiledTextureRegion getmFuegoCentroIzquierdaTR() {
		return mFuegoCentroIzquierdaTR;
	}


	public TiledTextureRegion getmFuegoCentroArribaTR() {
		return mFuegoCentroArribaTR;
	}


	public TiledTextureRegion getmFuegoCentroAbajoTR() {
		return mFuegoCentroAbajoTR;
	}


	public TiledTextureRegion getmFuegoHorizontalTR() {
		return mFuegoHorizontalTR;
	}


	public TiledTextureRegion getmFuegoVerticalTR() {
		return mFuegoVerticalTR;
	}


	public TiledTextureRegion getmFuegoFinDerechaTR() {
		return mFuegoFinDerechaTR;
	}


	public TiledTextureRegion getmFuegoFinIzquierdaTR() {
		return mFuegoFinIzquierdaTR;
	}


	public TiledTextureRegion getmFuegoFinArribaTR() {
		return mFuegoFinArribaTR;
	}
	public BuildableBitmapTextureAtlas getFuegoBBTA() {
		return fuegoBBTA;
	}

	public void setFuegoBBTA(BuildableBitmapTextureAtlas fuegoBBTA) {
		this.fuegoBBTA = fuegoBBTA;
	}
	public BitmapTextureAtlas getBombaBTA() {
		return bombaBTA;
	}




	public void setBombaBTA(BitmapTextureAtlas bombaBTA) {
		this.bombaBTA = bombaBTA;
	}

	public TiledTextureRegion getmFuegoFinAbajoTR() {
		return mFuegoFinAbajoTR;
	}


	public AnimatedSprite[] getSpriteArray() {
		return spriteArray;
	}
	
	
	public AnimatedSprite getBomba() {
		return sprBomba;
	}
	
	
	/**
	 * para el detonador con temporizador
	 * @author Ober
	 *
	 */
	public class ListenerDetonador implements IAnimationListener{

//		@Override//gles1
//		public void onAnimationEnd(AnimatedSprite arg0) {			
//			detonar(true);		
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
			detonar(true);//gles2
			
		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	/**
	 * es el que muestra aparecer la tertura de fuego
	 * cuando termina llama a desvanecer
	 * @author Ober
	 *
	 */
	public class ListenerExplotar implements IAnimationListener{

//		@Override
//		public void onAnimationEnd(AnimatedSprite arg0) {
//			sprCentro.animate(ANIMATE_DURATION,new int[]{4, 3, 2, 1, 0}, 0,new ListenerDesvanecer());
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
			sprCentro.animate(ANIMATE_DURATION,new int[]{4, 3, 2, 1, 0}, 0,new ListenerDesvanecer());
			
		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	
	public class ListenerDesvanecer implements IAnimationListener{

//		@Override
//		public void onAnimationEnd(AnimatedSprite arg0) {
//			batch.setVisible(false);
//			batch.setIgnoreUpdate(true);
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
			batch.setVisible(false);
			batch.setIgnoreUpdate(true);
			
		}

		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			// TODO Auto-generated method stub
			
		}
		
	}


	int numeroBomba;
	public int getNumeroBomba() {
		return numeroBomba;
	}

	public void setNumeroBomba(int i) {
		// TODO Auto-generated method stub
		numeroBomba=i;
	}
}
