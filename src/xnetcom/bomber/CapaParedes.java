package xnetcom.bomber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.AnimatedSprite.IAnimationListener;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.sprite.batch.SpriteGroup;
import org.andengine.extension.tmx.TMXLayer;
import org.andengine.extension.tmx.TMXTile;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.list.SmartList;
import org.andengine.util.adt.pool.GenericPool;

import xnetcom.bomber.util.Aleatorio;
import xnetcom.bomber.util.Coordenadas;
import android.util.Log;

public class CapaParedes {
	
	// index paredes 89
	// index techparedes 91
	//
	private BomberGame context;
	private TMXTile tileSoloBase;	 
	private TMXTile tileSoloTecho;
	private TMXTile tileIzquierdaBase;
	private TMXTile tileIzquierdaTecho;
	private TMXTile tileDerechaBase;
	private TMXTile tileDerechaTecho;
	private TMXTile tileCentroBase;
	private TMXTile tileCentroTecho;
	private SpritePool spritePool;
	private HashMap<Coordenadas,TiledSprite> sombras;
	private SpriteGroup spriteGroup;
	
	//private TMXLayer tmxParedesOriginal; /// capa con las paredes originales
	private TMXLayer tmxParedesOut;	// capa que devolveremos con las para representar
	private TMXLayer tmxParedesTechoOut;	// capa que devolveremos con las para representar
	//private TMXLayer tmxParedesTransparencia;	// capa que devolveremos con las para representar	
	//private TMXLayer tmxParedesTransparenciaTecho;	// capa que devolveremos con las para representar	


	private Iterator<AnimatedSprite> itr;
	private BitmapTextureAtlas explosionBTA;
	
	private ArrayList<AnimatedSprite> almacenExplosiones;

	public TMXLayer getTmxParedesTechoOut() {
		return tmxParedesTechoOut;
	}
	public void setTmxParedesTechoOut(TMXLayer tmxParedesTechoOut) {
		this.tmxParedesTechoOut = tmxParedesTechoOut;
	}
	//public void setTmxParedesTransparencia(TMXLayer tmxParedesTransparencia) {
	//	this.tmxParedesTransparencia = tmxParedesTransparencia;
	//}
	//public void setTmxParedesTransparenciaTecho(TMXLayer tmxParedesTransparenciaTecho) {
	//	this.tmxParedesTransparenciaTecho = tmxParedesTransparenciaTecho;
	//}
	

	
	public CapaParedes(final BomberGame context){
		this.context=context;	
		
		this.explosionBTA = new BitmapTextureAtlas(context.getTextureManager(), 512, 512, TextureOptions.NEAREST_PREMULTIPLYALPHA);
		context.getEngine().getTextureManager().loadTexture(this.explosionBTA);
		
		almacenExplosiones = new ArrayList<AnimatedSprite>();
		for (int i = 0; i < 10; i++) {
			AnimatedSprite ans =new AnimatedSprite(0, 0, BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.explosionBTA, context, "pared_explo.png", 0, 0, 2, 2),context.getVertexBufferObjectManager());
			ans.setScale(0.5f);
			ans.setScaleCenter(0, 0);
			almacenExplosiones.add(ans);
		}	
		itr= almacenExplosiones.iterator();
				
		//ArrayList<TiledSprite> AlmacenSombras= new ArrayList<TiledSprite>();
		
		if (GameManager.enableSombras)spritePool= new SpritePool(context.getResouceManager().getSombrasTR(),context);
		if (GameManager.enableSombras)sombras = new HashMap<Coordenadas, TiledSprite>();
		if (GameManager.enableSombras)spriteGroup = new SpriteGroup(context.getResouceManager().getSombrasBTA(),220,context.getVertexBufferObjectManager()){
			
			@Override
			protected void onManagedUpdate(float pSecondsElapsed) {
				try {
					super.onManagedUpdate(pSecondsElapsed);
				} catch (Exception e) {
					System.out.println("errorrrrrrrrrrr en batch  sombras");
					//context.tostar("errorrrrrrrrrrr en batch sombras");
				}
				
			}
			
			protected boolean onUpdateSpriteBatch() {
				final SmartList<IEntity> children = this.mChildren;
				if(children == null) {
					return false;
				} else {
					final int childCount = children.size();
					for(int i = 0; i < childCount; i++) {
						try{
							super.drawWithoutChecks((Sprite)children.get(i));
							}catch(Exception e){
								System.out.println("error pillado por mi");
								e.printStackTrace();
							}
					}
					return true;
				}
			}
		};
		if (GameManager.enableSombras)spriteGroup.setPosition(0, 0);
		if (GameManager.enableSombras)spriteGroup.setZIndex(BomberGame.ZINDEX_SUELO+1);
		if (GameManager.enableSombras) spriteGroup.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		if (GameManager.enableSombras)spriteGroup.setAlpha(0.8f);
		if (GameManager.enableSombras)context.getEscenaJuego().attachChild(spriteGroup);
		
	}

	
	
	public TMXLayer getTmxParedesOut() {
		return tmxParedesOut;
	}


	public void setMuestras(TMXLayer muestras){
		tileSoloTecho=muestras.getTMXTile(0, 0+13);
		tileSoloBase = muestras.getTMXTile(0, 1+13);		
		tileIzquierdaTecho=muestras.getTMXTile(2, 0+13);
		tileIzquierdaBase =muestras.getTMXTile(2, 1+13);
		tileCentroTecho=muestras.getTMXTile(3, 0+13);
		tileCentroBase=muestras.getTMXTile(3, 1+13);
		tileDerechaTecho=muestras.getTMXTile(4, 0+13);
		tileDerechaBase=muestras.getTMXTile(4, 1+13);		
	}

	public void setTmxParedesOut(TMXLayer tmxParedesOut) {
		this.tmxParedesOut = tmxParedesOut;
	}

	public void restauraParedes() {
		numparedes=0;
		//System.out.println("restauraParedes");
		creaParedes();
	}
	public void restauraSombras(){
		if (!GameManager.enableSombras)return;
		for (Entry<Coordenadas, TiledSprite> entry : sombras.entrySet()){
			TiledSprite sprite = entry.getValue();			
			sprite.detachSelf();
			spritePool.recyclePoolItem(sprite);
		}
		sombras.clear();
	}
	
	int numparedes=0;
	
	public int getNumparedes() {
		return numparedes;
	}
	public void setNumparedes(int numparedes) {
		this.numparedes= numparedes;
	}
	
	public void creaParedesCampoEntrenamiento() {		
		int numeroParedesInicio=Aleatorio.DameAleatorio(20,40);
		
		for (int i = 0; i < numeroParedesInicio; i++) {
		
			boolean posicionValida=false;
			int filaElejida=5;
			int columnaElegida=5;
			while (!posicionValida){
				columnaElegida=Aleatorio.DameAleatorio(5,22);
				filaElejida=Aleatorio.DameAleatorio(5,12);	
				int valor =context.getGameManager().getMatriz().getValor(filaElejida, columnaElegida);
				if (valor==BomberGame.NADA){
					posicionValida=true;
				}
			}

			context.getGameManager().getMatriz().setValor(BomberGame.PARED, filaElejida, columnaElegida);			
			
			int porcentajePonerDerecha= Aleatorio.DameAleatorio(1,100);			
			if (porcentajePonerDerecha>30 && context.getGameManager().getMatriz().getValor(filaElejida, columnaElegida+1)==BomberGame.NADA){
				context.getGameManager().getMatriz().setValor(BomberGame.PARED, filaElejida, columnaElegida+1);
				i++;
			}
			
			int porcentajePonerIzquierda= Aleatorio.DameAleatorio(1,100);			
			if (porcentajePonerIzquierda>30 && context.getGameManager().getMatriz().getValor(filaElejida, columnaElegida-1)==BomberGame.NADA){
				context.getGameManager().getMatriz().setValor(BomberGame.PARED, filaElejida, columnaElegida-1);
				i++;
			}
			
			int porcentajePonerArriba= Aleatorio.DameAleatorio(1,100);			
			if (porcentajePonerArriba>30 && context.getGameManager().getMatriz().getValor(filaElejida-1, columnaElegida)==BomberGame.NADA){
				context.getGameManager().getMatriz().setValor(BomberGame.PARED, filaElejida-1, columnaElegida);
				i++;
			}
			
			int porcentajePonerAbajo= Aleatorio.DameAleatorio(1,100);			
			if (porcentajePonerAbajo>30 && context.getGameManager().getMatriz().getValor(filaElejida+1, columnaElegida)==BomberGame.NADA){
				context.getGameManager().getMatriz().setValor(BomberGame.PARED, filaElejida+1, columnaElegida);
				i++;
			}			
		}		
		
		
		creaParedes();
	}
	
	
	
	public boolean crearUnMuro(int columna, int fila){
		if (context.getGameManager().getMatriz().getValor(fila, columna)!=BomberGame.NADA){
			return false;
		}
		context.getGameManager().getMatriz().setValor(BomberGame.PARED, fila, columna);	
		numparedes++;
		int izq=context.getGameManager().getMatriz().getValor(fila, columna-1);                 
		int der=context.getGameManager().getMatriz().getValor(fila, columna+1);                    
		int izqIzq=context.getGameManager().getMatriz().getValor(fila, columna-2);                 
		int derDer=context.getGameManager().getMatriz().getValor(fila, columna+2);              
		int hayPared= BomberGame.PARED;
		
		if ( izq==hayPared && izqIzq!=hayPared ){// izq = soliario				
			tmxParedesOut.					getTMXTile(columna-1, fila).setTextureRegion(tileIzquierdaBase.getTextureRegion());
			tmxParedesTechoOut.				getTMXTile(columna-1, fila-1).setTextureRegion(tileIzquierdaTecho.getTextureRegion());
			//ponSombra(columna, fila);
			
		}else if(izq==hayPared && izqIzq==hayPared){// izq = murodere
			tmxParedesOut.					getTMXTile(columna-1, fila).setTextureRegion(tileCentroBase.getTextureRegion());
			tmxParedesTechoOut.				getTMXTile(columna-1, fila-1).setTextureRegion(tileCentroTecho.getTextureRegion());
			//ponSombra(columna, fila);
		}
		
		
		if ( der==hayPared && derDer!=hayPared ){// der = soliario				
			tmxParedesOut.					getTMXTile(columna+1, fila).setTextureRegion(tileDerechaBase.getTextureRegion());
			tmxParedesTechoOut.				getTMXTile(columna+1, fila-1).setTextureRegion(tileDerechaTecho.getTextureRegion());
			//ponSombra(columna, fila);
			
		}else if(der==hayPared && derDer==hayPared){// der = muroizq
			tmxParedesOut.					getTMXTile(columna+1, fila).setTextureRegion(tileCentroBase.getTextureRegion());
			tmxParedesTechoOut.				getTMXTile(columna+1, fila-1).setTextureRegion(tileCentroTecho.getTextureRegion());
			//ponSombra(columna, fila);
			
		}
		
		
		
		if (izq!=hayPared && der!=hayPared){
			//pintamos un cuadrado solitario
			tmxParedesOut.					getTMXTile(columna, fila).setTextureRegion(tileSoloBase.getTextureRegion());
			tmxParedesTechoOut.				getTMXTile(columna, fila-1).setTextureRegion(tileSoloTecho.getTextureRegion());
		}else if (der!=hayPared && izq==hayPared){		// nada
			tmxParedesOut.					getTMXTile(columna, fila).setTextureRegion(tileDerechaBase.getTextureRegion());
			tmxParedesTechoOut.				getTMXTile(columna, fila-1).setTextureRegion(tileDerechaTecho.getTextureRegion());
			
		}else if (izq!=hayPared & der==hayPared){		// nada
			tmxParedesOut.					getTMXTile(columna, fila).setTextureRegion(tileIzquierdaBase.getTextureRegion());
			tmxParedesTechoOut.				getTMXTile(columna, fila-1).setTextureRegion(tileIzquierdaTecho.getTextureRegion());
			
		}else {
			tmxParedesOut.					getTMXTile(columna, fila).setTextureRegion(tileCentroBase.getTextureRegion());
			tmxParedesTechoOut.				getTMXTile(columna, fila-1).setTextureRegion(tileCentroTecho.getTextureRegion());
		}
		
		ponSombra(columna, fila);
		return true;
	}
	
	
	
	public void ponSombra(int columna,int fila){
		TMXTile tile=tmxParedesOut.getTMXTile(columna, fila);
		TiledSprite sprite = spritePool.obtainPoolItem();
//		sprite.setPosition(tile.getTileX()-BomberGame.ANCHO, tile.getTileY()-BomberGame.ALTO);//gels1
		sprite.setPosition(tmxParedesOut.getTileX(tile.getTileColumn()) -BomberGame.ANCHO, tmxParedesOut.getTileY(tile.getTileRow())-BomberGame.ALTO);//gles2
		spriteGroup.attachChild(sprite);
		sprite.setCurrentTileIndex(0);
		sombras.put(new Coordenadas(tile.getTileColumn(), tile.getTileRow()), sprite);
	}
	
	
	
	
	public void creaParedes() {	
		numparedes=0;
		restauraSombras();
		//System.out.println("actualizaParedes()");		
		int col = context.getGameManager().getMatriz().getMatrizmuros()[0].length;
		int filas = context.getGameManager().getMatriz().getMatrizmuros().length;

		for (int i = 0; i < filas; i++) {
			for (int j = 0; j < col; j++) {
				try {
					if (context.getGameManager().getMatriz().getMatrizmuros()[i][j] == BomberGame.PARED) {
						numparedes++;
						// ///System.out.println("azulejo fila "+i+" coluimna "+j+" es pared");					
						// //System.out.println("pnumparedes "+num);

						boolean izq = hayParedIzquierda(j, i);
						boolean der = hayParedDerecha(j, i);						
						if (izq && der) {							// centro
							
							TMXTile tile=tmxParedesOut.getTMXTile(j, i);
							tile.setTextureRegion(tileCentroBase.getTextureRegion());
							tmxParedesTechoOut.getTMXTile(j, i - 1).setTextureRegion(tileCentroTecho.getTextureRegion());
							//tmxParedesTransparencia.getTMXTile(j, i).setTextureRegion(tileCentroBase.getTextureRegion());
							//tmxParedesTransparenciaTecho.getTMXTile(j, i - 1).setTextureRegion(tileCentroTecho.getTextureRegion());
//							Log.d("paredParaSombra", "tile="+tmxParedesTechoOut.getTileX(tile.getTileColumn())+" tile="+tile.getTileY());
							if (GameManager.enableSombras){
								TiledSprite sprite = spritePool.obtainPoolItem();
								//sprite.setPosition(tile.getTileX()-BomberGame.ANCHO, tile.getTileY()-BomberGame.ALTO);
								sprite.setPosition((j-1)*BomberGame.ANCHO, (i-1)*BomberGame.ALTO);
								Log.d("sombra", "spriteX="+sprite.getX()+" spriteY="+sprite.getY());
								spriteGroup.attachChild(sprite);
								sprite.setCurrentTileIndex(2);
								sombras.put(new Coordenadas(tile.getTileColumn(), tile.getTileRow()), sprite);
							}

							
							
						} else if (izq) {
							TMXTile tile=tmxParedesOut.getTMXTile(j, i);
							tile.setTextureRegion(tileDerechaBase.getTextureRegion());
							tmxParedesTechoOut.getTMXTile(j, i - 1).setTextureRegion(tileDerechaTecho.getTextureRegion());
							//tmxParedesTransparencia.getTMXTile(j, i).setTextureRegion(tileDerechaBase.getTextureRegion());
							//tmxParedesTransparenciaTecho.getTMXTile(j, i - 1).setTextureRegion(tileDerechaTecho.getTextureRegion());
//							Log.d("paredParaSombra", "tile="+tile.getTileX()+" tile="+tile.getTileY());
							if (GameManager.enableSombras){
							TiledSprite sprite = spritePool.obtainPoolItem();
							//sprite.setPosition(tile.getTileX()-BomberGame.ANCHO, tile.getTileY()-BomberGame.ALTO);
							
							sprite.setPosition((j-1)*BomberGame.ANCHO, (i-1)*BomberGame.ALTO);
							
							Log.d("sombra", "spriteX="+sprite.getX()+" spriteY="+sprite.getY());
							spriteGroup.attachChild(sprite);
							sprite.setCurrentTileIndex(3);
							sombras.put(new Coordenadas(tile.getTileColumn(), tile.getTileRow()), sprite);
							}
							
						} else if (der) {
							
							
							TMXTile tile=tmxParedesOut.getTMXTile(j, i);
							tile.setTextureRegion(tileIzquierdaBase.getTextureRegion());
							tmxParedesTechoOut.getTMXTile(j, i - 1).setTextureRegion(tileIzquierdaTecho.getTextureRegion());
//							Log.d("paredParaSombra", "tile="+tile.getTileX()+" tile="+tile.getTileY() +"  j="+j+" i="+i);
							//tmxParedesTransparencia.getTMXTile(j, i).setTextureRegion(tileIzquierdaBase.getTextureRegion());
							//tmxParedesTransparenciaTecho.getTMXTile(j, i - 1).setTextureRegion(tileIzquierdaTecho.getTextureRegion());
							if (GameManager.enableSombras){							
							TiledSprite sprite = spritePool.obtainPoolItem();							
						//	sprite.setPosition(tile.getTileX()-BomberGame.ANCHO, tile.getTileY()-BomberGame.ALTO);
							sprite.setPosition((j-1)*BomberGame.ANCHO, (i-1)*BomberGame.ALTO);
							Log.d("sombra", "spriteX="+sprite.getX()+" spriteY="+sprite.getY());
							spriteGroup.attachChild(sprite);
							sprite.setCurrentTileIndex(1);
							sombras.put(new Coordenadas(tile.getTileColumn(), tile.getTileRow()), sprite);
							
							}
						}
						if (!izq && !der) {
							//solo
							//System.out.println("el cuadro columna"+j+"fila"+i);
							TMXTile tile=tmxParedesOut.getTMXTile(j, i);
							tile.setTextureRegion(tileSoloBase.getTextureRegion());
							tmxParedesTechoOut.getTMXTile(j, i - 1).setTextureRegion(tileSoloTecho.getTextureRegion());
//							Log.d("paredParaSombra", "tile="+tile.getTileX()+" tile="+tile.getTileY());
							//tmxParedesTransparencia.getTMXTile(j, i).setTextureRegion(tileSoloBase.getTextureRegion());
							//tmxParedesTransparenciaTecho.getTMXTile(j, i - 1).setTextureRegion(tileSoloTecho.getTextureRegion());
							if (GameManager.enableSombras){
							TiledSprite sprite = spritePool.obtainPoolItem();
							//sprite.setPosition(tile.getTileX()-BomberGame.ANCHO, tile.getTileY()-BomberGame.ALTO);
							sprite.setPosition((j-1)*BomberGame.ANCHO, (i-1)*BomberGame.ALTO);
							Log.d("sombra", "spriteX="+sprite.getX()+" spriteY="+sprite.getY());
							spriteGroup.attachChild(sprite);
							sprite.setCurrentTileIndex(0);
							sombras.put(new Coordenadas(tile.getTileColumn(), tile.getTileRow()), sprite);
							}
						}

					}else{
						tmxParedesOut.getTMXTile(j, i).setTextureRegion(null);
						tmxParedesTechoOut.getTMXTile(j, i - 1).setTextureRegion(null);
						//tmxParedesTransparencia.getTMXTile(j, i).setTextureRegion(null);
						//tmxParedesTransparenciaTecho.getTMXTile(j, i - 1).setTextureRegion(null);						
					}

				} catch (Exception e) {
					// e.printStackTrace();
				}
			}

		}
	}
	

	public void actualizaParedes(ArrayList<Coordenadas> coordenadas){
		int [][] matriz =context.getGameManager().getMatriz().getMatrizmuros();
		for (Coordenadas coor : coordenadas) {
			int izq=matriz[coor.getFila()][coor.getColumna()-1];
			int der=matriz[coor.getFila()][coor.getColumna()+1];
			int izqIzq=matriz[coor.getFila()][coor.getColumna()-2];
			int derDer=matriz[coor.getFila()][coor.getColumna()+2];
			int hayPared= BomberGame.PARED;
			if (GameManager.enableSombras){
			TiledSprite sprite = sombras.get(coor);
			sprite.detachSelf();
			spritePool.recyclePoolItem(sprite);
			sombras.remove(coor);
			}
			if ( izq==hayPared && izqIzq!=hayPared ){// izq = soliario				
				tmxParedesOut.					getTMXTile(coor.getColumna()-1, coor.getFila()).setTextureRegion(tileSoloBase.getTextureRegion());
				tmxParedesTechoOut.				getTMXTile(coor.getColumna()-1, coor.getFila()-1).setTextureRegion(tileSoloTecho.getTextureRegion());
			//	tmxParedesTransparencia.		getTMXTile(coor.getColumna()-1, coor.getFila()).setTextureRegion(tileSoloBase.getTextureRegion());
				//tmxParedesTransparenciaTecho.	getTMXTile(coor.getColumna()-1, coor.getFila()-1).setTextureRegion(tileSoloTecho.getTextureRegion());
				if (GameManager.enableSombras)sombras.get(new Coordenadas(coor.getColumna()-1, coor.getFila())).setCurrentTileIndex(0);
				
			}else if(izq==hayPared && izqIzq==hayPared){// izq = murodere
				tmxParedesOut.					getTMXTile(coor.getColumna()-1, coor.getFila()).setTextureRegion(tileDerechaBase.getTextureRegion());
				tmxParedesTechoOut.				getTMXTile(coor.getColumna()-1, coor.getFila()-1).setTextureRegion(tileDerechaTecho.getTextureRegion());
				//tmxParedesTransparencia.		getTMXTile(coor.getColumna()-1, coor.getFila()).setTextureRegion(tileDerechaBase.getTextureRegion());
				//tmxParedesTransparenciaTecho.	getTMXTile(coor.getColumna()-1, coor.getFila()-1).setTextureRegion(tileDerechaTecho.getTextureRegion());
				if (GameManager.enableSombras)sombras.get(new Coordenadas(coor.getColumna()-1, coor.getFila())).setCurrentTileIndex(3);
			}else if (izq!=hayPared){		// nada
				
				
			}
			
			if ( der==hayPared && derDer!=hayPared ){// der = soliario				
				tmxParedesOut.					getTMXTile(coor.getColumna()+1, coor.getFila()).setTextureRegion(tileSoloBase.getTextureRegion());
				tmxParedesTechoOut.				getTMXTile(coor.getColumna()+1, coor.getFila()-1).setTextureRegion(tileSoloTecho.getTextureRegion());
				//tmxParedesTransparencia.		getTMXTile(coor.getColumna()+1, coor.getFila()).setTextureRegion(tileSoloBase.getTextureRegion());
				//tmxParedesTransparenciaTecho.	getTMXTile(coor.getColumna()+1, coor.getFila()).setTextureRegion(tileSoloTecho.getTextureRegion());
				if (GameManager.enableSombras)sombras.get(new Coordenadas(coor.getColumna()+1, coor.getFila())).setCurrentTileIndex(0);
				
			}else if(der==hayPared && derDer==hayPared){// der = muroizq
				tmxParedesOut.					getTMXTile(coor.getColumna()+1, coor.getFila()).setTextureRegion(tileIzquierdaBase.getTextureRegion());
				tmxParedesTechoOut.				getTMXTile(coor.getColumna()+1, coor.getFila()-1).setTextureRegion(tileIzquierdaTecho.getTextureRegion());
				//tmxParedesTransparencia.		getTMXTile(coor.getColumna()+1, coor.getFila()).setTextureRegion(tileIzquierdaBase.getTextureRegion());
				//tmxParedesTransparenciaTecho.	getTMXTile(coor.getColumna()+1, coor.getFila()-1).setTextureRegion(tileIzquierdaTecho.getTextureRegion());
				if (GameManager.enableSombras)sombras.get(new Coordenadas(coor.getColumna()+1, coor.getFila())).setCurrentTileIndex(1);
				
			}else if (der!=hayPared){		// nada
				
				
			}			
		}
	}
	
	

	
	public void explota(ArrayList<Coordenadas> coordenadas){
		
		for (Coordenadas coor : coordenadas) {
			try{				
				TMXTile tile =tmxParedesOut.getTMXTile(coor.getColumna(), coor.getFila());
				TMXTile tiletecho =tmxParedesTechoOut.getTMXTile(coor.getColumna(), coor.getFila()-1);		
				//TMXTile tileT =tmxParedesTransparencia.getTMXTile(coor.getColumna(), coor.getFila());
				//TMXTile tiletechoT =tmxParedesTransparenciaTecho.getTMXTile(coor.getColumna(), coor.getFila()-1);	
				
				
				
				tile.setTextureRegion(null);
				tiletecho.setTextureRegion(null);			
				//tileT.setTextureRegion(null);
				//tiletechoT.setTextureRegion(null);
				
				//System.out.println("hacemos que la mariz no tenga nada en X="+coor.getFila()+ "  Y="+coor.getColumna());
				context.getGameManager().getMatriz().setValor(BomberGame.NADA, coor.getFila(), coor.getColumna());
								
				if (!itr.hasNext()){
					itr= almacenExplosiones.iterator();
					////System.out.println("REWINNNN");
				}
				AnimatedSprite spr = itr.next();
				spr.setVisible(true);
				spr.setPosition(tmxParedesOut.getTileX(tile.getTileColumn())-40,tmxParedesOut.getTileY(tile.getTileRow())-60);
				if (!spr.hasParent())context.getEscenaJuego().attachChild(spr);
				ListenerExplotar listener = new ListenerExplotar();
				spr.setZIndex(BomberGame.ZINDEX_MUROFRAGMENTOS);
				listener.setSprite(spr);
				spr.animate(40,0,listener);		
				context.getGameManager().getMonedero().CuadradoExplotado(coor.getColumna(),coor.getFila());		
				numparedes--;
			}catch (Exception e) {e.printStackTrace();}
		}
		 actualizaParedes(coordenadas);
	}

	
	public boolean hayParedIzquierda(int columna, int fila) {
		int valor = context.getGameManager().getMatriz().getValor(fila, columna - 1);
		if (valor == BomberGame.PARED) {
			return true;
		}else{
			return false;
		}		
	}
	
	public boolean hayParedDerecha(int columna, int fila) {
		int valor = context.getGameManager().getMatriz().getValor(fila, columna + 1);
		if (valor == BomberGame.PARED) {
			return true;
		}else{
			return false;
		}		
	}
		
	public class ListenerExplotar implements IAnimationListener{

		private AnimatedSprite sprt;
		public void setSprite(AnimatedSprite sprt){
			this.sprt=sprt;
		}
//		@Override
//		public void onAnimationEnd(AnimatedSprite arg0) {
//			this.sprt.setVisible(false);
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
			this.sprt.setVisible(false);
			
		}
		@Override
		public void onAnimationFinished(AnimatedSprite pAnimatedSprite) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	
	static class SpritePool extends GenericPool<TiledSprite> {
        // ===========================================================
        // Constants
        // ===========================================================

        // ===========================================================
        // Fields
        // ===========================================================
        //private final VertexBufferObjectManager mVertexBufferObjectManager;
        private TiledTextureRegion mFaceTextureRegion;
        BomberGame context;

        // ===========================================================
        // Constructors
        // ===========================================================
        public SpritePool(final TiledTextureRegion pFaceTextureRegion,BomberGame context) {
            mFaceTextureRegion = pFaceTextureRegion;  
            this.context=context;
        }


        // Methods for/from SuperClass/Interfaces
        // ===========================================================
        @Override
        protected TiledSprite onAllocatePoolItem() {
            final TiledSprite lSprite = new TiledSprite(0, 0, mFaceTextureRegion.deepCopy(),context.getVertexBufferObjectManager());
            lSprite.setIgnoreUpdate(true);
            lSprite.setScaleCenter(0, 0);
            lSprite.setScale(0.5f);
            return lSprite;
        }

        @Override
        protected void onHandleRecycleItem(final TiledSprite pSprite) {
        }

        @Override
        protected void onHandleObtainItem(final TiledSprite pSprite) {
        }
    }
	
	
}


