package xnetcom.bomber;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;

import xnetcom.bomber.andengine.TransparentBitmapTextureAtlasSource;
import xnetcom.bomber.util.ConstantesResolucion;
import android.os.AsyncTask;

public class Carga {


//	private BitmapTextureAtlas mFontTexture;
	private BitmapTextureAtlas mBarraTexture;
	private TextureRegion textureR;
	private Font mFont;
	private Text text;
	private Sprite barra;
	private Scene ScenaCarga;
	
	private BomberGame context;
	
	public Carga(){
		
	}
	
	public Carga(BomberGame context){
		this.context=context;
		
	}	

	public void onLoadResources() {
//		this.mFontTexture= new BitmapTextureAtlas(context.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mBarraTexture= new BitmapTextureAtlas(context.getTextureManager(),2, 64, TextureOptions.DEFAULT);
		
//		BitmapTextureAtlasTextureRegionFactory.createFromSource(mFontTexture, new TransparentBitmapTextureAtlasSource(256,128), 0, 0);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(mBarraTexture, new TransparentBitmapTextureAtlasSource(2,64), 0, 0);
		
		FontFactory.setAssetBasePath("font/");
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		this.textureR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.mBarraTexture, context, "barra.png", 0, 0);
		this.mFont = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA, context.getAssets(), "DD.ttf", ConstantesResolucion.getTamFuenteCarga(), true, android.graphics.Color.WHITE);
		mFont.load();
		//		context.getEngine().getTextureManager().loadTexture(this.mFontTexture);
		context.getEngine().getTextureManager().loadTexture(this.mBarraTexture);
//		context.getEngine().getFontManager().loadFont(this.mFont);		
		
	}

	
	public Scene onLoadScene() {
		ScenaCarga = new Scene();		
		
		barra = new Sprite(ConstantesResolucion.getOffsetX_camara(), 0, textureR,context.getVertexBufferObjectManager());
		barra.setPosition(0, ConstantesResolucion.getCAMERA_HEIGHT_MASTER()-barra.getHeight());
		
		text = new Text(ConstantesResolucion.getOffsetX_camara() +30, barra.getY()-(mFont.getLineHeight()+5), mFont, "Loading...                 ",context.getVertexBufferObjectManager());	
		ScenaCarga.attachChild(barra);
		ScenaCarga.attachChild(text);
		
		return ScenaCarga;
	}
	/*

	public void onLoadComplete() {		
		
		//System.out.println("onload complete");
		Thread hilo = new Thread(){
			@Override
			public void run() {
				int por=0;
				while(true){					
					setPorcentaje(por);					
					//text.setText("width "+width +" "+por);
					por+=10;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		};				
		
		height =mBoundChaseCamera.getHeight();
		width =mBoundChaseCamera.getWidth();
		
		text.setPosition(20, height-barra.getHeight()-text.getHeight()-30);
		barra.setPosition(0, height-barra.getHeight());
		//hilo.start();					
	}
*/
	
	public void onLoadComplete() {
		context.runOnUiThread(new Runnable() {			
			@Override
			public void run() {
				 new AsyncTask<Void, Void, Void>() {
                     
                     private Exception mException = null;

                     @Override
                     public void onPreExecute() {
                    	 setPorcentaje(10); 
                    	 try {
							Thread.sleep(200);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    	 /*
                    	 Thread hilo = new Thread(){
                 			
                    		 int i=0;
                 			public void run() {                 				
                 				while(i<20){					
                 					//incrementaProcentaje(3);
                 					i++;
                 					try {
                 						Thread.sleep(500);
                 					} catch (InterruptedException e) {
                 						// TODO Auto-generated catch block
                 						e.printStackTrace();
                 					}
                 				}

                 			}
                 		};		
                 		hilo.start();*/
                 		
                     }

                     @Override
                     public Void doInBackground(final Void... params) {
                             //System.out.println("In");
                             //publishProgress(2);                                                         
                             context.onLoadResourcesInicio();
                             setPorcentaje(30); 
                             context.getInicio().onLoadScene();
                             setPorcentaje(60);                        
         					context.onLoadResourcesGame();
         					setPorcentaje(80);
         					context.onLoadSceneGame();
         					setPorcentaje(100);
         					try {
         						Thread.sleep(500);
         					} catch (InterruptedException e) {
         						// TODO Auto-generated catch block
         						e.printStackTrace();
         					}
                             return null;
                     }

                     @Override
                     public void onPostExecute(final Void result) {
                    	 context.setEscenaInicio(context.getInicio().getEscenaInicio());
                    	 context.getEngine().setScene(context.getInicio().getEscenaInicio());
                     }
             }.execute((Void[]) null);
				
			}
		});
		//mapas = new MenuMapas(this);
		//opciones = new MenuOpciones();
		
		
	}
	
	int width=ConstantesResolucion.getCAMERA_WIDTH_MASTER();
	int parcial=1;
	public void setPorcentaje(float porciento){		
		barra.setWidth((porciento/100)*width);
	}
	
	
}
