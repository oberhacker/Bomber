package xnetcom.bomber.entidades;

import org.andengine.entity.sprite.Sprite;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import xnetcom.bomber.BomberEstado;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.GameManager;
import xnetcom.bomber.andengine.TransparentBitmapTextureAtlasSource;

public class Moneda {

	
	
	public enum TipoMoneda{
		MCORAZON("m_corazon"),//1			te da una vida
		MDETONADOR("m_detonador"),//2			tienes detonador
		MFUERZA("m_fuerza"),//3				aguantas las explosiones y a los enemigos
		MFANTASMA("m_fantasma"),//4			atraviesas las paredes
		MBOMBA("m_bomba"),//5				te da una bomba mas 
		MVELOCIDAD("m_correr"),//6			te mueves deprisa	
		MEXPLOSION("m_potenciador"),//7			explosion mas potente
		MSORPRESA("m_sorpresa"),
		MNULA("m_nula");
		private TipoMoneda(String valor) {
			this.valor = valor;
		}
		public String getValor(){
			return valor;
		}
		private String valor;
	}
	public enum Estado{
		NOPLANTADA,
		PLANTADA,

	}

	private BomberGame context;
	private AlmancenMonedas almacen;
	private Sprite sprtMoneda;
	private TipoMoneda tipo;
	private BitmapTextureAtlas btaMoneda;
	private TextureRegion trMoneda;
	private TMXLayer tmxLayer ;
	private int fila;
	private int columna;

	public int getFila() {
		return fila;
	}

	public int getColumna() {
		return columna;
	}

	public Moneda(AlmancenMonedas almacen, TipoMoneda tipo){
		this.context =almacen.getContext();
		this.almacen=almacen;		
		this.tipo=tipo;
		tmxLayer=context.getGameManager().getTmxSuelo();
		this.btaMoneda = new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(btaMoneda, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		switch (tipo) {
			case MBOMBA:
				this.trMoneda =BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btaMoneda, context, "m_bomba.png", 0, 0);
				break;
			case MDETONADOR:
				this.trMoneda =BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btaMoneda, context, "m_detonador.png", 0, 0);
				break;
			case MFUERZA:
				this.trMoneda =BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btaMoneda, context, "m_fuerza.png", 0, 0);
				break;
			case MFANTASMA:
				this.trMoneda =BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btaMoneda, context, "m_fantasma.png", 0, 0);
				break;
			case MCORAZON:
				this.trMoneda =BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btaMoneda, context, "m_corazon.png", 0, 0);
				break;
			case MEXPLOSION:
				this.trMoneda =BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btaMoneda, context, "m_potenciador.png", 0, 0);
				break;
			case MVELOCIDAD:
				this.trMoneda =BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.btaMoneda, context, "m_correr.png", 0, 0);
				break;
		}
		context.getEngine().getTextureManager().loadTexture(this.btaMoneda);
		
		sprtMoneda = new Sprite(0, 0, trMoneda,context.getVertexBufferObjectManager());
		sprtMoneda.setScale(0.5f);
		sprtMoneda.setScaleCenter(0, 0);
		sprtMoneda.setVisible(false);
		getSpriteMoneda().setZIndex(BomberGame.ZINDEX_MONEDAS);
		context.getEscenaJuego().attachChild(sprtMoneda);
		context.getEscenaJuego().sortChildren();
	}
	
	public void ponerMoneda(int columna, int fila){
		explotable=false;
		GameManager gm = context.getGameManager();
		switch (tipo) {
		case MBOMBA:
			gm.plantadaM_bomba();
			break;
		case MDETONADOR:
			gm.plantadaM_detonador();
			break;
		case MFUERZA:
			gm.plantadaM_fuerza();
			break;
		case MFANTASMA:
			gm.plantadaM_fantasma();
			break;
		case MCORAZON:
			gm.plantadaM_corazon();
			break;
		case MEXPLOSION:
			gm.plantadaM_potencia();
			break;
		case MVELOCIDAD:
			gm.plantadaM_correr();
			break;
	}
		
		
		this.fila=fila;
		this.columna=columna;
		TMXTile tile =tmxLayer.getTMXTile(columna, fila);
		
		getSpriteMoneda().setPosition(tmxLayer.getTileX(tile.getTileColumn()),tmxLayer.getTileY(tile.getTileRow()));		
		getSpriteMoneda().setVisible(true);
		new Thread(){
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				explotable=true;
			};
		}.start();
		
	}
	
	
	private boolean explotable=false;
	
	
	
	public boolean monedasEnExceso(){
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
		}
		
		return false;		
	}
	
	
	
	/**
	 * esta funcion se le llamara cuando bomberman recoja la moneda
	 */
	public void recogerMoneda(){
		if(!sprtMoneda.isVisible())return;
		
			
		
		GameManager gm = context.getGameManager();
		switch (tipo) {
		case MBOMBA:
			//context.getResouceManager().playBooster();
			if (!monedasEnExceso())	gm.adquiridaM_bomba();
			break;
		case MDETONADOR:
			//context.getResouceManager().playBooster();
			gm.adquiridaM_detonador();
			break;
		case MFUERZA:
			if (gm.getEstado().isBoosterActivado()) return;		
			context.getResouceManager().playBooster();
			gm.adquiridaM_fuerza();
			break;
		case MFANTASMA:
			if (gm.getEstado().isBoosterActivado()) return;
			context.getResouceManager().playBooster();
			gm.adquiridaM_fantasma();
			break;
		case MCORAZON:
			//context.getResouceManager().playBooster();
			gm.adquiridaM_corazon();
			break;
		case MEXPLOSION:
			//context.getResouceManager().playBooster();
			if (!monedasEnExceso())	gm.adquiridaM_potencia();
			break;
		case MVELOCIDAD:
			if (gm.getEstado().isBoosterActivado()) return;
			context.getResouceManager().playBooster();
			gm.adquiridaM_correr();
			break;
	}	
		context.getResouceManager().sonidoMonedaPlay();		
		// aki es codigo que avisa al game manager que se ha cogid esta moneda		
		context.getGameManager().getMatriz().setValor(BomberGame.NADA, fila, columna);
		dettach();
	}
	
	
	
	public void dettach(){	
		sprtMoneda.setVisible(false);
	}
	
	/**
	 * aki se pondra el codigo que se ejecute cuando se explote esta moneda
	 */
	public boolean monedaExplotada(){
		if (!explotable){
			return false;
		}else{
			dettach();
			return true;
		}
				
	}
	
	
	public boolean isVisible(){
		return sprtMoneda.isVisible();
	}
	public Sprite getSpriteMoneda(){
		return sprtMoneda;
	}
	public TipoMoneda getTipoMoneda(){
		return tipo;
	}
}
