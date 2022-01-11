package xnetcom.bomber.entidades;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;

public class EnemigoGlobo extends EnemigoBase{
	
	
	

	public EnemigoGlobo(BomberGame context, int columna, int fila) {
		super(context, columna, fila);
		tipoEnemigo = Tipo.EN_GLOBO;
		TiempoPorCuadro=0.70f;
		tiempoFotograma=120;
		TiledTextureRegion principalTR = context.getResouceManager().getGloboTR().deepCopy();
		principal = new AnimatedSprite(-15, -50, principalTR,context.getVertexBufferObjectManager());
		//principal.setZIndex(BomberGame.ZINDEX_ENEMIGOS);
		principal.setScale(0.3f);
		attachChild(principal);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public void mover(EnemigoDirection dir ) {
		if(dir==EnemigoDirection.LEFT){
			principal.setFlippedHorizontal(true);
		}else if(dir==EnemigoDirection.RIGHT){
			principal.setFlippedHorizontal(false);
		}
		super.mover(dir);
	}
	
	
	@Override
	public void animarDerecha() {
		if (direccionAnimacion!=EnemigoDirection.RIGHT){	
			direccionAnimacion=EnemigoDirection.RIGHT;
			principal.animate(new long[]{tiempoFotograma, tiempoFotograma, tiempoFotograma, 
					tiempoFotograma, tiempoFotograma,tiempoFotograma,tiempoFotograma,tiempoFotograma,
					tiempoFotograma,tiempoFotograma},new int[]{0, 1, 2, 3, 4, 5, 4, 3, 2, 1}, 1000);
		}		
	}

	@Override
	public void animarIzquierda() {
		if (direccionAnimacion!=EnemigoDirection.LEFT){	
			direccionAnimacion=EnemigoDirection.LEFT;
			principal.animate(new long[]{tiempoFotograma, tiempoFotograma, tiempoFotograma, 
					tiempoFotograma, tiempoFotograma,tiempoFotograma,tiempoFotograma,tiempoFotograma,
					tiempoFotograma,tiempoFotograma},new int[]{0, 1, 2, 3, 4, 5, 4, 3, 2, 1}, 1000);
		}		
		
	}

	@Override
	public void animarArriba() {
		if (direccionAnimacion!=EnemigoDirection.UP){	
			direccionAnimacion=EnemigoDirection.UP;
			principal.animate(new long[]{tiempoFotograma, tiempoFotograma, tiempoFotograma, 
					tiempoFotograma, tiempoFotograma,tiempoFotograma,tiempoFotograma,tiempoFotograma,
					tiempoFotograma,tiempoFotograma},new int[]{6, 7, 8, 9, 10, 11, 10, 9, 8, 7}, 1000);
		}
		
		
	}

	@Override
	public void animarAbajo() {
		if (direccionAnimacion!=EnemigoDirection.DOWN){	
			direccionAnimacion=EnemigoDirection.DOWN;
			principal.animate(new long[]{tiempoFotograma, tiempoFotograma, tiempoFotograma, 
					tiempoFotograma, tiempoFotograma,tiempoFotograma,tiempoFotograma,tiempoFotograma,
					tiempoFotograma,tiempoFotograma},new int[]{0, 1, 2, 3, 4, 5, 4, 3, 2, 1}, 1000);
		}
		
		
	}
	@Override
	public void animarMuerte() {
		int tiempo=120;
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo},new int[]{13,14,15, 16, 17, 18,19}, 0, new ListenerMorir());	
		
	}
	


}
