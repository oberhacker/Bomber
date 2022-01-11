package xnetcom.bomber.entidades;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;

public class EnemigoGota extends EnemigoBase{

	
	
	
	
	
	
	public EnemigoGota(BomberGame context, int columna, int fila) {
		super(context, columna, fila);
		tipoEnemigo = Tipo.EN_GOTA_AZUL;
		TiempoPorCuadro=0.70f;
		tiempoFotograma=90;
		TiledTextureRegion principalTR = context.getResouceManager().getGotaTR().deepCopy();
		principal = new AnimatedSprite(((BomberGame.ANCHO)/2)-principalTR.getWidth()/2, -26, principalTR,context.getVertexBufferObjectManager());		
		principal.setScale(0.5f);
		attachChild(principal);
		
	}

	
	
	
	
public void inteligencia(){	
		
		EnemigoDirection dirSeguidor= seguidor();
		
	
		EnemigoDirection direccionActual= this.direccion;
		EnemigoDirection direccionSalida= this.direccion;
		boolean retardo=false;
		
		if(dirSeguidor!=EnemigoDirection.NONE && puedoSeguir(dirSeguidor)){
			setEnfadado(true);
			setDireccion(dirSeguidor);
			direccionSalida=dirSeguidor;
			
		}else{					
			
			// no estoy siguendo pudo seguir o no 
			
			//  aki hay un fallo
			
			if (puedoSeguir(direccionActual)){
				direccionSalida=direccionActual;
				
				if(tomaDecision(1, 100)<85){
					direccionSalida=direccionActual;
				}else{
					direccionSalida=cambiaDireccion(direccionActual);				
				}		
				//direccionSalida =caminoMuyAndado(direccionSalida);  // de momento lo quito que me gusta que vaya mas a su bola
			}else{// si no puedo segui por donde iba		
				if(enfadado)retardo=true;				
				if (tomaDecision(1, 100)<30){
					direccionSalida=cambiaDireccion(direccionActual);					
				}else{
					direccionSalida=cambioSentido(direccionActual);
					
				}				
				
				if(direccionSalida.equals(direccionActual)){
					direccionSalida=cambioSentido(direccionActual);
				}
				
				
				// aki podria llegar con direccion NONE				
				if (direccionSalida.equals(EnemigoDirection.NONE)){
					direccionSalida =EligeDireccion();
				}					
			}		
			setDireccion(direccionSalida);			
			setEnfadado(false);
			
			
			
		}	
		
		if (isCambioSentido(direccionActual, direccionSalida)){
			retardo(direccionSalida);
		}else{
			if (!retardo){
				mover(direccionSalida);	
			}else{
				retardo(direccionSalida);
			}
			
		}
			
	
	}
		






public EnemigoDirection seguidor() {
	int X = context.getGameManager().getBomberman().getColumna();
	int Y = context.getGameManager().getBomberman().getFila();

	EnemigoDirection dir = EnemigoDirection.NONE;
	int diferencia=0;
	if ((X == columna - 1 || X == columna - 2 || X == columna - 3 ) && fila == Y) {
		diferencia=columna-X;
		dir = EnemigoDirection.LEFT;
	}
	
	if ((X == columna + 1 || X == columna + 2 || X == columna + 3) && fila == Y) {
		dir = EnemigoDirection.RIGHT;
		diferencia=X-columna;
	}
	
	if ((Y == fila - 1 || Y == fila - 2 || Y == fila - 3) && columna == X) {
		dir = EnemigoDirection.UP;
		diferencia=fila-Y;
	}

	if ((Y == fila + 1 || Y == fila + 2 || Y == fila + 3) && columna == X) {
		dir = EnemigoDirection.DOWN;
		diferencia=Y-fila;
	}
	
	
	switch (dir) {
	case LEFT:
		try{ if (diferencia >1 && context.getGameManager().getMatriz().getValor(fila, columna-1)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) { System.out.println("error ");}
		try{ if (diferencia >2 && context.getGameManager().getMatriz().getValor(fila, columna-2)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
		try{ if (diferencia >3 && context.getGameManager().getMatriz().getValor(fila, columna-3)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
		break;
	case RIGHT:
		try{ if (diferencia >1 && context.getGameManager().getMatriz().getValor(fila, columna+1)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
		try{ if (diferencia >2 && context.getGameManager().getMatriz().getValor(fila, columna+2)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
		try{ if (diferencia >3 && context.getGameManager().getMatriz().getValor(fila, columna+3)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
		break;

	case UP:

		try{if (diferencia >1 && context.getGameManager().getMatriz().getValor(fila-1, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e){}		
		try{ if (diferencia >2 && context.getGameManager().getMatriz().getValor(fila-2, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {}		
		try{ if (diferencia >3 && context.getGameManager().getMatriz().getValor(fila-3, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {}
		break;
		
	case DOWN:
		try{ if (diferencia >1 && context.getGameManager().getMatriz().getValor(fila+1, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
		try{ if (diferencia >2 && context.getGameManager().getMatriz().getValor(fila+2, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
		try{ if (diferencia >3 && context.getGameManager().getMatriz().getValor(fila+3, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
		break;
		
	case NONE:			
		break;
	}

	return dir;
}




















	
	/*
	public EnemigoDirection seguidor(){
		int X =context.getGameManager().getBomberman().getColumna();
		int Y =context.getGameManager().getBomberman().getFila();
		
		EnemigoDirection dir = EnemigoDirection.NONE;
		
		if ((X==columna-1||X==columna-2||X==columna-3)&&fila==Y){
			dir=EnemigoDirection.LEFT;
		}
		if ((X==columna+1||X==columna+2||X==columna+3)&&fila==Y){
			dir=EnemigoDirection.RIGHT;
		}
		if ((Y==fila-1||Y==fila-2||Y==fila-3)&&columna==X){
			dir=EnemigoDirection.UP;
		}
		
		if ((Y==fila+1||Y==fila+2||Y==fila+3)&&columna==X){
			dir=EnemigoDirection.DOWN;
		}
		return dir;
	}
	*/
	@Override
	public void mover(EnemigoDirection dir) {
		if(dir==EnemigoDirection.LEFT){
			principal.setFlippedHorizontal(false);
		}else if(dir==EnemigoDirection.RIGHT){
			principal.setFlippedHorizontal(true);
		}
		super.mover(dir);
	}
	
	public void setEnfadado(boolean enfadado){
		if(enfadado){
			this.enfadado=true;
			TiempoPorCuadro=0.40f;
		}else{
			this.enfadado=false;
			TiempoPorCuadro=0.70f;
		}
	}
	
	
	
	@Override
	public void animarDerecha() {
		int tiempo=120;
		if(enfadado){
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo},new int[]{7,8,9, 10,11,12,13}, 1000);
		}else{
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo},new int[]{0,1,2,3,4,5,6}, 1000);
		}		
		
	}

	@Override
	public void animarIzquierda() {
		int tiempo=120;
		if(enfadado){
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo},new int[]{7,8,9, 10,11,12,13}, 1000);
		}else{
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo},new int[]{0,1,2,3,4,5,6}, 1000);
		}	
		
	}

	@Override
	public void animarArriba() {
		int tiempo=120;
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo},new int[]{18, 19, 20, 21, 20, 19}, 1000);	
		
	}

	@Override
	public void animarAbajo() {
		int tiempo=120;
		if(enfadado){
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo, tiempo,tiempo},new int[]{22, 23, 24,25, 26, 27,28,29}, 1000);
		}else{
			principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo},new int[]{14, 15, 16, 17}, 1000);
		}
		
	}

	@Override
	public void animarMuerte() {
		int tiempo=120;
		principal.animate(new long[]{tiempo, tiempo, tiempo, tiempo, tiempo,tiempo,tiempo},new int[]{30,31,32, 33, 34, 35,36}, 0,new ListenerMorir());
		
		
	}
	
	
	
}