package xnetcom.bomber.entidades;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.modifier.ease.EaseBounceOut;
import org.andengine.util.modifier.ease.EaseExponentialOut;

import xnetcom.bomber.BomberGame;

public class EnemigoGotaNaranja extends EnemigoBase {

	public EnemigoGotaNaranja(BomberGame context, int columna, int fila) {
		super(context, columna, fila);
		tipoEnemigo = Tipo.EN_GOTA_NARANJA;
		TiempoPorCuadro = 0.70f;
		tiempoFotograma = 90;
		TiledTextureRegion principalTR = context.getResouceManager().getGotaNaranjaTR().deepCopy();
		principal = new AnimatedSprite(((BomberGame.ANCHO)/2)-principalTR.getWidth()/2, -26, principalTR,context.getVertexBufferObjectManager());
		principal.setScale(0.5f);
		attachChild(principal);

	}

	/*
public void inteligencia(){	
		
		EnemigoDirection dirSeguidor= seguidor();
		
		
		EnemigoDirection direccionActual= this.direccion;
		EnemigoDirection direccionSalida= this.direccion;
		boolean retardo=false;
		
		if(dirSeguidor!=EnemigoDirection.NONE && puedoSeguir(dirSeguidor)){
			setEnfadado(true);
			setDireccion(dirSeguidor);
			direccionSalida=dirSeguidor;
			
		}else if(dirSeguidor != EnemigoDirection.NONE && puedoSeguirSalto(dirSeguidor)){			
			jump();
			setEnfadado(true);
			setDireccion(dirSeguidor);
			direccionSalida = dirSeguidor;			
			moverDosCuadros(direccionSalida);
			return;			
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
		
	
	*/
	
	
	public void inteligencia() {

		if (context.getGameManager().getMatriz().getValor(fila-1, columna)==BomberGame.BOMBA){
			System.out.println("bommmm");
		}
		
		EnemigoDirection dirSeguidor = seguidor();
		EnemigoDirection dirSeguidorSalto= seguidorSalto();
		
		if (dirSeguidor==EnemigoDirection.NONE && dirSeguidorSalto!=EnemigoDirection.NONE){
			System.out.println("·");
		}
		
		EnemigoDirection direccionActual = this.direccion;
		EnemigoDirection direccionSalida = this.direccion;
		boolean retardo = false;

		if (dirSeguidor != EnemigoDirection.NONE && puedoSeguir(dirSeguidor)) {
			setEnfadado(true);
			setDireccion(dirSeguidor);
			direccionSalida = dirSeguidor;

		}else if(dirSeguidorSalto != EnemigoDirection.NONE && puedoSeguirSalto(dirSeguidorSalto)){			
			jump();
			setEnfadado(true);
			setDireccion(dirSeguidorSalto);
			direccionSalida = dirSeguidorSalto;			
			moverDosCuadros(direccionSalida);
			return;			
		}else {

			if (puedoSeguir(direccionActual)) {

				if (tomaDecision(1, 100) < 60) {
					direccionSalida = direccionActual;
				} else {
					direccionSalida = cambiaDireccion(direccionActual);
				}
				// direccionSalida =caminoMuyAndado(direccionSalida); // de
				// momento lo quito que me gusta que vaya mas a su bola
			} else {// si no puedo segui por donde iba
				if (enfadado)
					retardo = true;
				if (tomaDecision(1, 100) < 30) {
					direccionSalida = cambiaDireccion(direccionActual);
				} else {
					direccionSalida = cambioSentido(direccionActual);
				}
				if(direccionSalida.equals(direccionActual)){
					direccionSalida=cambioSentido(direccionActual);
				}
			}
			setDireccion(direccionSalida);
			setEnfadado(false);

		}
		
		
		if (isCambioSentido(direccionActual, direccionSalida)) {
			retardo(direccionSalida);
		} else {
			if (!retardo) {
				mover(direccionSalida);
			} else {
				retardo(direccionSalida);
			}

		}

	}
	
	
	
	
	
    private void jump() {
        
        final float jumpDuration = 0.5f;
        final int jumpHeight = 50;             
        final float startY = principal.getY();
        final float peakY = startY - jumpHeight;        
        
        SequenceEntityModifier jumpModifier = new SequenceEntityModifier(new MoveYModifier(jumpDuration, startY, peakY, EaseExponentialOut.getInstance()),
        																new MoveYModifier(jumpDuration, peakY, startY, EaseBounceOut.getInstance()));        
        principal.registerEntityModifier(jumpModifier);
        
}
	
	
	
	
	
	
	
	public boolean puedoSeguirSalto(EnemigoDirection dir){
		switch (dir) {
		case DOWN:
			return puedoAbajoSalto();			
		case UP:
			return puedoArribaSalto();			
		case LEFT:
			return puedoIzquierdaSalto();			
		case RIGHT:
			return puedoDerechaSalto();			
		}
		return false;
		
	}
	
	/**
	 * significa que en la inmediata casilla a su derecha tiene una bomba, pero en la siguente esta libre
	 * si esta enfadado puede saltar esa bomba he ir a por ti
	 * @return
	 */
	public boolean puedoDerechaSalto(){
		int salida=-1;
		int salida2=-1;
		try{ salida=context.getGameManager().getMatriz().getValor(fila, columna+1);}catch (Exception e) {e.printStackTrace();}
		if (salida==BomberGame.BOMBA){
			try{ salida2=context.getGameManager().getMatriz().getValor(fila, columna+2);}catch (Exception e) {e.printStackTrace();}
			if (salida2==BomberGame.NADA || salida2==BomberGame.PUERTA || salida==BomberGame.MONEDA ){
				return true;
			}else{
				return false;
			}			
		}else{
			return false;
		}
	}
	
	public boolean puedoIzquierdaSalto(){
		int salida=-1;
		int salida2=-1;
		try{ salida=context.getGameManager().getMatriz().getValor(fila, columna-1);}catch (Exception e) {e.printStackTrace();}
		if (salida==BomberGame.BOMBA){
			try{ salida2=context.getGameManager().getMatriz().getValor(fila, columna-2);}catch (Exception e) {e.printStackTrace();}
			if (salida2==BomberGame.NADA || salida2==BomberGame.PUERTA || salida==BomberGame.MONEDA ){
				return true;
			}else{
				return false;
			}			
		}else{
			return false;
		}
	}
	
	public boolean puedoArribaSalto(){
		int salida=-1;
		int salida2=-1;
		try{ salida=context.getGameManager().getMatriz().getValor(fila-1, columna);}catch (Exception e) {e.printStackTrace();}
		if (salida==BomberGame.BOMBA){
			try{ salida2=context.getGameManager().getMatriz().getValor(fila-2, columna);}catch (Exception e) {e.printStackTrace();}
			if (salida2==BomberGame.NADA || salida2==BomberGame.PUERTA || salida==BomberGame.MONEDA ){
				return true;
			}else{
				return false;
			}			
		}else{
			return false;
		}
	}
	
	public boolean puedoAbajoSalto(){
		int salida=-1;
		int salida2=-1;
		try{ salida=context.getGameManager().getMatriz().getValor(fila+1, columna);}catch (Exception e) {e.printStackTrace();}
		if (salida==BomberGame.BOMBA){
			try{ salida2=context.getGameManager().getMatriz().getValor(fila+2, columna);}catch (Exception e) {e.printStackTrace();}
			if (salida2==BomberGame.NADA || salida2==BomberGame.PUERTA || salida==BomberGame.MONEDA ){
				return true;
			}else{
				return false;
			}			
		}else{
			return false;
		}
	}
	
	
	public EnemigoDirection seguidor() {
		int X = context.getGameManager().getBomberman().getColumna();
		int Y = context.getGameManager().getBomberman().getFila();

		EnemigoDirection dir = EnemigoDirection.NONE;
		int diferencia=0;
		if ((X == columna - 1 || X == columna - 2 || X == columna - 3 || X == columna - 4) && fila == Y) {
			diferencia=columna-X;
			dir = EnemigoDirection.LEFT;
		}
		
		if ((X == columna + 1 || X == columna + 2 || X == columna + 3 || X == columna + 4) && fila == Y) {
			dir = EnemigoDirection.RIGHT;
			diferencia=X-columna;
		}
		
		if ((Y == fila - 1 || Y == fila - 2 || Y == fila - 3 || Y == fila - 4) && columna == X) {
			dir = EnemigoDirection.UP;
			diferencia=fila-Y;
		}

		if ((Y == fila + 1 || Y == fila + 2 || Y == fila + 3 || Y == fila + 4) && columna == X) {
			dir = EnemigoDirection.DOWN;
			diferencia=Y-fila;
		}
		
		
		switch (dir) {
		case LEFT:
			try{ if (diferencia >1 && context.getGameManager().getMatriz().getValor(fila, columna-1)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) { System.out.println("error ");}
			try{ if (diferencia >2 && context.getGameManager().getMatriz().getValor(fila, columna-2)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			try{ if (diferencia >3 && context.getGameManager().getMatriz().getValor(fila, columna-3)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			try{ if (diferencia >4 && context.getGameManager().getMatriz().getValor(fila, columna-4)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			break;
		case RIGHT:
			try{ if (diferencia >1 && context.getGameManager().getMatriz().getValor(fila, columna+1)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			try{ if (diferencia >2 && context.getGameManager().getMatriz().getValor(fila, columna+2)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			try{ if (diferencia >3 && context.getGameManager().getMatriz().getValor(fila, columna+3)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			try{ if (diferencia >4 && context.getGameManager().getMatriz().getValor(fila, columna+4)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			break;

		case UP:

			try{if (diferencia >1 && context.getGameManager().getMatriz().getValor(fila-1, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e){}		
			try{ if (diferencia >2 && context.getGameManager().getMatriz().getValor(fila-2, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {}		
			try{ if (diferencia >3 && context.getGameManager().getMatriz().getValor(fila-3, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {}
			try{ if (diferencia >4 && context.getGameManager().getMatriz().getValor(fila-4, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {}
			break;
			
		case DOWN:
			try{ if (diferencia >1 && context.getGameManager().getMatriz().getValor(fila+1, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			try{ if (diferencia >2 && context.getGameManager().getMatriz().getValor(fila+2, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			try{ if (diferencia >3 && context.getGameManager().getMatriz().getValor(fila+3, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			try{ if (diferencia >4 && context.getGameManager().getMatriz().getValor(fila+4, columna)!=BomberGame.NADA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			break;
			
		case NONE:			
			break;
		}

		return dir;
	}
	
	
	
	public EnemigoDirection seguidorSalto() {
		int X = context.getGameManager().getBomberman().getColumna();
		int Y = context.getGameManager().getBomberman().getFila();

		EnemigoDirection dir = EnemigoDirection.NONE;
		int diferencia=0;
		if ((X == columna - 1 || X == columna - 2 || X == columna - 3 || X == columna - 4) && fila == Y) {
			diferencia=columna-X;
			dir = EnemigoDirection.LEFT;
		}
		
		if ((X == columna + 1 || X == columna + 2 || X == columna + 3 || X == columna + 4) && fila == Y) {
			dir = EnemigoDirection.RIGHT;
			diferencia=X-columna;
		}
		
		if ((Y == fila - 1 || Y == fila - 2 || Y == fila - 3 || Y == fila - 4) && columna == X) {
			dir = EnemigoDirection.UP;
			diferencia=fila-Y;
		}

		if ((Y == fila + 1 || Y == fila + 2 || Y == fila + 3 || Y == fila + 4) && columna == X) {
			dir = EnemigoDirection.DOWN;
			diferencia=Y-fila;
		}
		
		
		switch (dir) {
		case LEFT:
			int izq=context.getGameManager().getMatriz().getValor(fila, columna-1);
			try{ if (diferencia >1 && izq!=BomberGame.NADA  && izq!=BomberGame.BOMBA && izq!=BomberGame.MONEDA && izq!=BomberGame.PUERTA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) { System.out.println("error ");}
			izq=context.getGameManager().getMatriz().getValor(fila, columna-2);
			try{ if (diferencia >2&& izq!=BomberGame.NADA  && izq!=BomberGame.BOMBA && izq!=BomberGame.MONEDA && izq!=BomberGame.PUERTA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			izq= context.getGameManager().getMatriz().getValor(fila, columna-3);
			try{ if (diferencia >3 && izq!=BomberGame.NADA  && izq!=BomberGame.BOMBA && izq!=BomberGame.MONEDA && izq!=BomberGame.PUERTA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			izq= context.getGameManager().getMatriz().getValor(fila, columna-4);
			try{ if (diferencia >4  && izq!=BomberGame.NADA  && izq!=BomberGame.BOMBA && izq!=BomberGame.MONEDA && izq!=BomberGame.PUERTA){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			break;
		case RIGHT:
			int der= context.getGameManager().getMatriz().getValor(fila, columna+1);
			try{ if (diferencia >1 && isNoSaltable(der)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			der=context.getGameManager().getMatriz().getValor(fila, columna+2);
			try{ if (diferencia >2 && isNoSaltable(der)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			der=context.getGameManager().getMatriz().getValor(fila, columna+3);
			try{ if (diferencia >3 && isNoSaltable(der)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			der=context.getGameManager().getMatriz().getValor(fila, columna+4);
			try{ if (diferencia >4 && isNoSaltable(der)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			break;

		case UP:

			int valor= context.getGameManager().getMatriz().getValor(fila-1, columna);
			try{if (diferencia >1 && isNoSaltable(valor)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e){}		
			valor = context.getGameManager().getMatriz().getValor(fila-2, columna);
			try{ if (diferencia >2 && isNoSaltable(valor)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {}		
			valor = context.getGameManager().getMatriz().getValor(fila-3, columna);
			try{ if (diferencia >3 && isNoSaltable(valor)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {}
			valor =context.getGameManager().getMatriz().getValor(fila-4, columna);
			try{ if (diferencia >4 && isNoSaltable(valor)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {}
			break;
			
		case DOWN:
			
			int down =context.getGameManager().getMatriz().getValor(fila+1, columna);
			try{ if (diferencia >1 && isNoSaltable(down)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			down =context.getGameManager().getMatriz().getValor(fila+2, columna);
			try{ if (diferencia >2 && isNoSaltable(down)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			down =context.getGameManager().getMatriz().getValor(fila+3, columna);
			try{ if (diferencia >3 && isNoSaltable(down)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			down = context.getGameManager().getMatriz().getValor(fila+4, columna);
			try{ if (diferencia >4 && isNoSaltable(down)){dir= EnemigoDirection.NONE; return dir; }}catch (Exception e) {System.out.println("error ");}
			break;
			
		case NONE:			
			break;
		}

		return dir;
	}
	
	
	public boolean isNoSaltable(int valor ){
		return (valor!=BomberGame.NADA  && valor!=BomberGame.BOMBA && valor!=BomberGame.MONEDA && valor!=BomberGame.PUERTA);
	}
	

	@Override
	public void mover(EnemigoDirection dir) {
		if (dir == EnemigoDirection.LEFT) {
			principal.setFlippedHorizontal(false);
		} else if (dir == EnemigoDirection.RIGHT) {
			principal.setFlippedHorizontal(true);
		}
		super.mover(dir);
	}
	
	
	
	
	public void moverDosCuadros(EnemigoDirection dir){		
			//System.out.println("columna "+columna +"fila "+fila+" mover a "+direccion);
		
		if (dir == EnemigoDirection.LEFT) {
			principal.setFlippedHorizontal(false);
		} else if (dir == EnemigoDirection.RIGHT) {
			principal.setFlippedHorizontal(true);
		}
		

			toX=getX();
			toY=getY();
			
			switch (dir) {
			case DOWN:
				// //System.out.println("DOWN");
				toY=this.getY() + BomberGame.ALTO;
				this.registerEntityModifier(new MoveYModifier(2*TiempoPorCuadro, this.getY(), this.getY() + 2*BomberGame.ALTO) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						// playMusica();
						animarAbajo();
					}

					protected void onModifierFinished(IEntity pItem) {
						llegado();
					}
				});

				break;

			case UP:
				// //System.out.println("UP");
				toY=this.getY() - BomberGame.ALTO;
				this.registerEntityModifier(new MoveYModifier(2*TiempoPorCuadro, this.getY(), this.getY() - 2*BomberGame.ALTO) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						// playMusica();
						animarArriba();
					}

					protected void onModifierFinished(IEntity pItem) {
						llegado();
					}
				});
				break;
			case LEFT:
				// //System.out.println("LEFT");
				toX=this.getX() - BomberGame.ANCHO;
				this.registerEntityModifier(new MoveXModifier(2*TiempoPorCuadro, this.getX(), this.getX() - 2*BomberGame.ANCHO) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						animarIzquierda();
						// playMusica();
					}

					protected void onModifierFinished(IEntity pItem) {
						llegado();
					}
				});
				break;
			case RIGHT:
				// //System.out.println("RIGHT");
				toX=this.getX() + BomberGame.ANCHO;
				this.registerEntityModifier(new MoveXModifier(2*TiempoPorCuadro, this.getX(), this.getX() + 2*BomberGame.ANCHO) {
					@Override
					protected void onModifierStarted(IEntity pItem) {
						// playMusica();
						animarDerecha();
					}

					protected void onModifierFinished(IEntity pItem) {
						llegado();
					}
				});

				break;
				
			case NONE:
				
			}
		}
	
	
	
	
	
	

	public void setEnfadado(boolean enfadado) {
		if (enfadado) {
			this.enfadado = true;
			TiempoPorCuadro = 0.40f;
		} else {
			this.enfadado = false;
			TiempoPorCuadro = 0.70f;
		}
	}

	@Override
	public void animarDerecha() {
		int tiempo = 120;
		if (enfadado) {
			principal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 7, 8, 9, 10, 11, 12, 13 }, 1000);
		} else {
			principal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 0, 1, 2, 3, 4, 5, 6 }, 1000);
		}

	}

	@Override
	public void animarIzquierda() {
		int tiempo = 120;
		if (enfadado) {
			principal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 7, 8, 9, 10, 11, 12, 13 }, 1000);
		} else {
			principal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 0, 1, 2, 3, 4, 5, 6 }, 1000);
		}

	}

	@Override
	public void animarArriba() {
		int tiempo = 120;
		principal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 18, 19, 20, 21, 20, 19 }, 1000);

	}

	@Override
	public void animarAbajo() {
		int tiempo = 120;
		if (enfadado) {
			principal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 22, 23, 24, 25, 26, 27, 28, 29 }, 1000);
		} else {
			principal.animate(new long[] { tiempo, tiempo, tiempo, tiempo }, new int[] { 14, 15, 16, 17 }, 1000);
		}

	}

	@Override
	public void animarMuerte() {
		int tiempo = 120;
		principal.animate(new long[] { tiempo, tiempo, tiempo, tiempo, tiempo, tiempo, tiempo }, new int[] { 30, 31, 32, 33, 34, 35, 36 }, 0, new ListenerMorir());

	}

}