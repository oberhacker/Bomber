package xnetcom.bomber.entidades;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;

import xnetcom.bomber.BomberGame;
import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("WrongCall")
public class EnemigoFantasma extends EnemigoBase{

	
	
	private AnimatedSprite transparencia;
	
	
	public EnemigoFantasma(BomberGame context, int columna, int fila) {
		super(context, columna, fila);
		tipoEnemigo = Tipo.EN_FANTASMA;
		TiledTextureRegion principalTR = context.getResouceManager().getFantasmaTR().deepCopy();
		principal = new AnimatedSprite(-20, -30, principalTR,context.getVertexBufferObjectManager());
		transparencia= new AnimatedSprite(-20, -30, principalTR,context.getVertexBufferObjectManager());
		
		tiempoFotograma=120;
		transparencia.setZIndex(BomberGame.ZINDEX_SUELO+2);
		principal.setZIndex(BomberGame.ZINDEX_ENEMIGOS_TRANSPARENTES);
		
		principal.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		transparencia.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		principal.setAlpha(0.4f);
		transparencia.setAlpha(0.4f);
		principal.setScale(0.5f);
		transparencia.setScale(0.5f);
		
		Scene scene = context.getEscenaJuego();
		scene.attachChild(transparencia);
		scene.attachChild(principal);
		
		
		
		
		
	}

	@Override
	protected void onManagedDraw(GLState pGLState, Camera pCamera) {
		try{
			transparencia.setPosition(this.getX()-25,this.getY()-30);
			principal.setPosition(this.getX()-25,this.getY()-30);
		}catch(Exception e){
			Log.e("Errorr", "onManagedDraw",e);
		}	
		super.onManagedDraw(pGLState, pCamera);
	}
	
	
	
	
	public void mover(EnemigoDirection dir) {
		if(dir==EnemigoDirection.LEFT){
			principal.setFlippedHorizontal(false);			
		}else if(dir==EnemigoDirection.RIGHT){
			principal.setFlippedHorizontal(true);			
		}
		super.mover(dir);
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

		if (valor == BomberGame.PARED || valor == BomberGame.BOMBA) {
			TiempoPorCuadro=0.90f;
		}
		if (valor == BomberGame.NADA) {
			TiempoPorCuadro=0.50f;
		}

	}
	
		
	
	
	
	public void midetachSelf(){
		detachSelf();
		transparencia.detachSelf();
		principal.detachSelf();
		//muerto=true;
		//setVisible(true);
		//setIgnoreUpdate(true);
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
		long tiempo=tiempoFotograma;
		if (direccionAnimacion!=EnemigoDirection.RIGHT){	
			direccionAnimacion=EnemigoDirection.RIGHT;					
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo},new int[]{5,6,7, 6,}, 1000);
		}
		
	}

	@Override
	public void animarIzquierda() {
		long tiempo=tiempoFotograma;
		if (direccionAnimacion!=EnemigoDirection.LEFT){	
			direccionAnimacion=EnemigoDirection.LEFT;
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo},new int[]{5,6,7, 6,}, 1000);
		}
		
	}

	@Override
	public void animarArriba() {
		long tiempo=tiempoFotograma;
		if (direccionAnimacion!=EnemigoDirection.UP){	
			direccionAnimacion=EnemigoDirection.UP;
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo,tiempo},new int[]{10, 11, 12, 11, 10, 13, 14,13}, 1000);
		}
		
	}

	@Override
	public void animarAbajo() {
		long tiempo=tiempoFotograma;
		if (direccionAnimacion!=EnemigoDirection.DOWN){	
			direccionAnimacion=EnemigoDirection.DOWN;
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo,tiempo},new int[]{0, 1, 2, 1, 0, 3, 4,3}, 1000);
		}
		
	}

	@Override
	public void animarMuerte() {
		int tiempo=120;	
		clearEntityModifiers();
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo},new int[]{15,15,15, 16, 17, 18,19}, 0, new ListenerMorir());
		
	}

}
