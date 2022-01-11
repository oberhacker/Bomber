package xnetcom.bomber.entidades;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;

public class EnemigoMonedaMarron extends EnemigoBase{

	public EnemigoMonedaMarron(BomberGame context, int columna, int fila) {
		super(context, columna, fila);
		tipoEnemigo = Tipo.EN_MONEDA_MARRON;
		TiempoPorCuadro=0.20f;
		tiempoFotograma=70;
		TiledTextureRegion principalTR = context.getResouceManager().getMonedaMarronTR().deepCopy();
		principal = new AnimatedSprite(-17, -24, principalTR,context.getVertexBufferObjectManager());
		//principal.setZIndex(BomberGame.ZINDEX_ENEMIGOS);
		principal.setScale(0.45f);
		attachChild(principal);
		//cuadradoMascara.setVisible(false);
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
	
	

	
	
	
	
	public void animar(){
		if (!principal.isAnimationRunning()){
			int tiempo=120;
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo},new int[]{0,1,2, 3,4,5,6,7,8,3,9,10}, 1000);
		}		
	}
	
	@Override
	public void animarDerecha() {
		animar();
	}

	@Override
	public void animarIzquierda() {
		animar();		
	}

	@Override
	public void animarArriba() {
		animar();
		
	}

	@Override
	public void animarAbajo() {
		animar();		
	}
	@Override
	public void animarMuerte() {
		int tiempo=120;
		clearEntityModifiers();
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo},new int[]{11,12,13, 14, 15, 16,17}, 0,new ListenerMorir());
	}
	
	
	

}
