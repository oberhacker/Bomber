package xnetcom.bomber.menus;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.align.HorizontalAlign;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.ConstantesResolucion;
import xnetcom.bomber5.R;

public class MenuSalida {
	
	
	private Sprite aceptarSPRT;
	private Sprite cancelarSPRT;
	private Sprite fondoSPRT;
	private BomberGame context;
	private Text ctTitulo;
	private Text ctTexto;
	
	private Font mFontTitulo;
	private Font mFontTexto;
	
	public MenuSalida(final BomberGame context){
		this.context=context;
		
		
		FontFactory.setAssetBasePath("font/");
		
//		BitmapTextureAtlas mFontTituloTextureBomber= new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//		BitmapTextureAtlas mFontTextoTextureBomber= new BitmapTextureAtlas(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		this.mFontTitulo = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(), "acegaffigan.ttf", 70, true, android.graphics.Color.BLACK);
		mFontTitulo.load();
		this.mFontTexto = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(), "acegaffigan.ttf", 40, true, android.graphics.Color.BLACK);
		mFontTexto.load();
		String titulo=context.getString(R.string.TXT1);
		String texto=context.getString(R.string.TXT2);
		
//		context.getEngine().getFontManager().loadFont(this.mFontTitulo);
//		context.getEngine().getFontManager().loadFont(this.mFontTexto);
//		context.getEngine().getTextureManager().loadTexture(mFontTituloTextureBomber);
//		context.getEngine().getTextureManager().loadTexture(mFontTextoTextureBomber);
		final VertexBufferObjectManager vertexBufferObjectManager = context.getVertexBufferObjectManager();
		ctTitulo = new Text(0, 0, mFontTitulo, titulo,new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
		ctTexto = new Text(0, 0, mFontTexto, texto,new TextOptions(HorizontalAlign.LEFT), vertexBufferObjectManager);
		
		fondoSPRT = new Sprite((ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2)-(context.getResouceManager().getBtnFondoTR().getWidth()/2), 100, context.getResouceManager().getBtnFondoTR(),context.getEngine().getVertexBufferObjectManager());		
		
		aceptarSPRT= new Sprite(0, 0, context.getResouceManager().getBtnAceptarTR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {				
				if (!fondoSPRT.isVisible()) return false;		
				context.getGameManager().play();
				fondoSPRT.setVisible(false);
				aceptar();				
				return false;
			}
		};
		cancelarSPRT = new Sprite(0, 0, context.getResouceManager().getBtn_CancelarTR(),context.getEngine().getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {				
				if (!fondoSPRT.isVisible()) return false;				
				fondoSPRT.setVisible(false);
				context.getGameManager().play();
				return false;	
				
			}
		};
		
		aceptarSPRT.setPosition(aceptarSPRT.getWidth(), fondoSPRT.getHeight()-aceptarSPRT.getHeight()*1.5f);
		cancelarSPRT.setPosition(fondoSPRT.getWidth()-cancelarSPRT.getWidth()*2, fondoSPRT.getHeight()-aceptarSPRT.getHeight()*1.5f);	
		
		ctTitulo.setPosition(context.alineacionCentradoHorizontalRelativo(fondoSPRT.getWidth(),ctTitulo.getWidthScaled()), 40);		
		ctTexto.setPosition(context.alineacionCentradoHorizontalRelativo(fondoSPRT.getWidth(),ctTexto.getWidth()), 150);
		
		fondoSPRT.attachChild(aceptarSPRT);
		fondoSPRT.attachChild(cancelarSPRT);
		
	
		fondoSPRT.attachChild(ctTitulo);
		fondoSPRT.attachChild(ctTexto);
		
		fondoSPRT.setVisible(false);
		context.getHud().attachChild(fondoSPRT);	
		context.getHud().registerTouchArea(aceptarSPRT);
		context.getHud().registerTouchArea(cancelarSPRT);
		
		fondoSPRT.setZIndex(BomberGame.POR_ENCIMA_DE_TODO+101);
	}
	
	public void setVisible(boolean mostrar){
		//context.habilitaBotones(!mostrar);
		fondoSPRT.setVisible(mostrar);			
	}
	
	public boolean isVisible(){
		return fondoSPRT.isVisible();
	}
	
	public void aceptar(){
		
		if (context.getGameManager().isModoEntrenamiento()){
			context.getGameManager().setModoEntrenamiento(false);
			context.getGameManager().clearMapa();
			context.getEscenaJuego().onUpdate(0.02f);
			context.getEngine().setScene(context.getInicio().getEscenaMapas());
			context.getResouceManager().stopMusica();						
			context.getInicio().cargaMenuCampOentrenamiento();
			context.getEngine().setScene(context.getEscenaInicio());
			context.getGameManager().setSalirInteligencia(true);
			context.getMenu().botonesVisibles(false);
		}else{
			context.getMenu().botonesVisibles(false);
			context.getResouceManager().stopMusica();	
			context.VerMenuMapas();
		}	
	}
	

}
