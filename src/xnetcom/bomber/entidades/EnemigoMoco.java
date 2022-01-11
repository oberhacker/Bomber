package xnetcom.bomber.entidades;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;

import xnetcom.bomber.BomberGame;

public class EnemigoMoco extends EnemigoBase{

	public EnemigoMoco(BomberGame context, int columna, int fila) {
		super(context, columna, fila);
		tipoEnemigo = Tipo.EN_MOCO;
		TiledTextureRegion principalTR = context.getResouceManager().getMocoTR().deepCopy();
		principal = new AnimatedSprite((getWidth()/2)-principalTR.getWidth()/2, -50, principalTR,context.getVertexBufferObjectManager());		
		transparencia= new AnimatedSprite((getWidth()/2)-principalTR.getWidth()/2, -50, principalTR,context.getVertexBufferObjectManager());	
		tiempoFotograma=120;
		transparencia.setZIndex(BomberGame.ZINDEX_SUELO+2);
		principal.setZIndex(BomberGame.ZINDEX_ENEMIGOS_TRANSPARENTES);
		
		principal.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		transparencia.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		principal.setScale(0.5f);
		principal.setAlpha(0.7f);
		transparencia.setAlpha(0.4f);
		transparencia.setScale(0.5f);
		Scene scene = context.getEscenaJuego();
		scene.attachChild(transparencia);
		scene.attachChild(principal);
	}

	@Override
	protected void onManagedDraw(GLState pGLState, Camera pCamera) {
		try{
			transparencia.setPosition(this.getX()-23,this.getY()-18);
			principal.setPosition(this.getX()-23,this.getY()-18);
		}catch(Exception e){}
		super.onManagedDraw(pGLState, pCamera);
	}

	
	
	public void actualizaPosicion() {

		// System.out.println("actualizaPosicion columna "+columna+" fila "+fila);

		float cY = getY() - (BomberGame.ALTO / 2);
		float cX = getX() - (BomberGame.ANCHO / 2);

		int fila = (int) (cY / BomberGame.ALTO);
		if ((cY % BomberGame.ALTO) > 0) {
			fila++;
		}

		int columna = (int) ((cX) / BomberGame.ANCHO);
		if ((cX % BomberGame.ANCHO) > 0) {
			columna++;
		}
		this.columna = columna;
		this.fila = fila;
		if (currentTileRectangle!=null)currentTileRectangle.setPosition(columna * BomberGame.ANCHO, fila * BomberGame.ALTO);

		int valor = getContext().getGameManager().getMatriz().getValor(fila, columna);

		if (valor == BomberGame.PARED ) {
			TiempoPorCuadro=0.50f;
		}
		if (valor == BomberGame.NADA) {
			TiempoPorCuadro=0.90f;
		}

	}
	
	public void mover(EnemigoDirection dir) {
		if(dir==EnemigoDirection.LEFT){
			principal.setFlippedHorizontal(true);			
		}else if(dir==EnemigoDirection.RIGHT){
			principal.setFlippedHorizontal(false);			
		}
		super.mover(dir);
	}

	
	public void midetachSelf(){
		detachSelf();
		transparencia.detachSelf();
		principal.detachSelf();
	}
	
	
	@Override
	public void setVisible(boolean pVisible) {
		// TODO Auto-generated method stub
		try{transparencia.setVisible(pVisible);}catch(Exception e){}
		try{principal.setVisible(pVisible);}catch(Exception e){}
		super.setVisible(pVisible);
	}
	
	
	
	public boolean puedoDerecha(){
		int salida=-1;	
		try{ salida=context.getGameManager().getMatriz().getValor(fila, columna+1);}catch (Exception e) {e.printStackTrace();}
		if (salida!=BomberGame.PIEDRA) return true;
		else return false;		 
	}
	
	public boolean puedoIzquierda(){
		int salida=-1;	
		try{ salida=context.getGameManager().getMatriz().getValor(fila, columna-1);}catch (Exception e) {e.printStackTrace();}
		if (salida!=BomberGame.PIEDRA) return true;
		else return false;
	}
	
	public boolean puedoArriba(){
		int salida=-1;	
		try{ salida=context.getGameManager().getMatriz().getValor(fila-1, columna);}catch (Exception e) {e.printStackTrace();}
		if (salida!=BomberGame.PIEDRA) return true;
		else return false;
	}
	
	public boolean puedoAbajo(){
		int salida=-1;	
		try{ salida=context.getGameManager().getMatriz().getValor(fila+1, columna);}catch (Exception e) {e.printStackTrace();}
		if (salida!=BomberGame.PIEDRA) return true;
		else return false;
	}

	
	@Override
	public void animarDerecha() {
		int tiempo=120;
		if (direccionAnimacion!=EnemigoDirection.RIGHT){	
			direccionAnimacion=EnemigoDirection.RIGHT;
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo},new int[]{0,1,2, 3,4,5,6}, 1000);
		}		
	}

	@Override
	public void animarIzquierda() {
		int tiempo=120;
		if (direccionAnimacion!=EnemigoDirection.LEFT){	
			direccionAnimacion=EnemigoDirection.LEFT;
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo},new int[]{0,1,2, 3,4,5,6}, 1000);
		}
		
	}

	@Override
	public void animarArriba() {
		int tiempo=120;
		if (direccionAnimacion!=EnemigoDirection.UP){	
			direccionAnimacion=EnemigoDirection.UP;
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo},new int[]{7, 8, 9, 10, 9, 8}, 1000);
		}
		
	}

	@Override
	public void animarAbajo() {
		int tiempo=120;
		if (direccionAnimacion!=EnemigoDirection.DOWN){	
			direccionAnimacion=EnemigoDirection.DOWN;
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo},new int[]{12, 13, 14, 15, 14, 13}, 1000);
		}
		
	}

	@Override
	public void animarMuerte() {
		int tiempo=120;
		clearEntityModifiers();
		clearEntityModifiers();
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo},new int[]{16,17,18, 19, 20, 21,22}, 0,new ListenerMorir());
		
	}

}
