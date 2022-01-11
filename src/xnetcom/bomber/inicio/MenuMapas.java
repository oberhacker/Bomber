package xnetcom.bomber.inicio;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.background.SpriteBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.ConstantesResolucion;

public class MenuMapas implements IOnSceneTouchListener{
	 
	private BomberGame context;
	/*
	private int offsetX=170;
	private int offsetY=50;	
*/

	private int paginaActual=0;
	private Rectangle cuadrado;
	public boolean moviendo = false;	
	private Scene escenaMapas;
	public Scene getEscenaMapas() {
		return escenaMapas;
	}

	private Icono_bomba spr_Bombas[];
	
	//private Sprite sprtAdelante;
	//private Sprite sprtAtras;
	//private Sprite sprtMenu;

	public MenuMapas(final BomberGame context) {
		this.context = context;
		escenaMapas = new Scene();
		escenaMapas.setBackground(new SpriteBackground(new Sprite(0, 0, context.getResouceManager().getFondo_mapas_TR(),context.getVertexBufferObjectManager())));
		cuadrado= new Rectangle(0, 0, 10, 10,context.getVertexBufferObjectManager());
		cuadrado.setAlpha(0);
		
		int offsetX = ConstantesResolucion.getOffsetX_BombasIcono_MASTER();
		int offsetY = ConstantesResolucion.getOffsetY_BombasIcono_MASTER();

		int anchoColumna = ConstantesResolucion.getAnchoColumna_BombasIcono_MASTER();
		int altoFila = ConstantesResolucion.getAltoFila_BombasIcono_MASTER();

		spr_Bombas = new Icono_bomba[200];
		int h = 0;

		int anchoPagina = ConstantesResolucion.getCAMERA_WIDTH_MASTER();
		int pagina = 0;

		// for de filas
		for (int j = 0; j < 3; j++) {
			// for de columnass
			for (int i = 0; i < 5; i++) {

				//System.out.println("spr_Bombas["+h+"  offsetX+i*anchoColumna"+( offsetX+i*anchoColumna)+"  offsetY+j*altoFila"+(offsetY+j*altoFila));
				spr_Bombas[h]= new Icono_bomba(context, context.getResouceManager().getmFont(), h+1,pagina*anchoPagina+ offsetX+i*anchoColumna, offsetY+j*altoFila, context.getResouceManager().getIcono_bombas_TR().deepCopy());
				spr_Bombas[h].setScale(ConstantesResolucion.getSize_BombasIcono_MASTER());
				h++;
			}
		}
	
		pagina=1;
		
		// for de filas 
		for (int j = 0; j < 3; j++) {
			// for de columnass
			for (int i = 0; i < 5; i++) {
				//System.out.println("spr_Bombas["+h+"  offsetX+i*anchoColumna"+( offsetX+i*anchoColumna)+"  offsetY+j*altoFila"+(offsetY+j*altoFila));
				spr_Bombas[h]= new Icono_bomba(context, context.getResouceManager().getmFont(), h+1,pagina*anchoPagina+ offsetX+i*anchoColumna, offsetY+j*altoFila, context.getResouceManager().getIcono_bombas_TR().deepCopy());
				spr_Bombas[h].setScale(ConstantesResolucion.getSize_BombasIcono_MASTER());
				h++;
			}
		}
		
		pagina=2;
		// for de filas 
		for (int j = 0; j < 3; j++) {
			// for de columnass
			for (int i = 0; i < 5; i++) {
				//System.out.println("spr_Bombas["+h+"  offsetX+i*anchoColumna"+( offsetX+i*anchoColumna)+"  offsetY+j*altoFila"+(offsetY+j*altoFila));
				spr_Bombas[h]= new Icono_bomba(context, context.getResouceManager().getmFont(), h+1,pagina*anchoPagina+ offsetX+i*anchoColumna, offsetY+j*altoFila, context.getResouceManager().getIcono_bombas_TR().deepCopy());
				spr_Bombas[h].setScale(ConstantesResolucion.getSize_BombasIcono_MASTER());
				h++;
			}
		}
		
		
		
		
		System.out.println("salgo");
		for (Icono_bomba bomba : spr_Bombas) {
			if (bomba!=null){
				//escenaMapas.attachChild(bomba);		
				cuadrado.attachChild(bomba);		
				escenaMapas.registerTouchArea(bomba);
			}
		}
		escenaMapas.attachChild(cuadrado);
		
		Sprite flechaDerecha = new Sprite(ConstantesResolucion.getSprtFlechaDerechaX_MASTER(), ConstantesResolucion.getSprtFlechaDerechaY_MASTER(),  context.getResouceManager().getIconoflecha_TR().deepCopy(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				System.out.println("flecha d");
				if (pSceneTouchEvent.getAction()==1){
					System.out.println("flecha dd");
					if(!moviendo&& paginaActual!=2)cuadrado.registerEntityModifier(new MoveXModifier(0.5f, cuadrado.getX(), cuadrado.getX()-ConstantesResolucion.getCAMERA_WIDTH_MASTER()){
						@Override
						protected void onModifierStarted(IEntity pItem) {
							paginaActual++;
							moviendo=true;
						}
						@Override
						protected void onModifierFinished(IEntity pItem) {
							moviendo=false;
						}
					});
				}
				
				return true;
			}
		};
		Sprite flechaIzquierda = new Sprite(ConstantesResolucion.getSprtFlechaIzquierdaX_MASTER(), ConstantesResolucion.getSprtFlechaIzquierdaY_MASTER(),  context.getResouceManager().getIconoflecha_TR().deepCopy(),context.getVertexBufferObjectManager()){
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				System.out.println("flecha i");
				if (pSceneTouchEvent.getAction()==1){

					if(!moviendo && paginaActual!=0)cuadrado.registerEntityModifier(new MoveXModifier(0.5f, cuadrado.getX(), cuadrado.getX()+ConstantesResolucion.getCAMERA_WIDTH_MASTER()){
						@Override
						protected void onModifierStarted(IEntity pItem) {
							moviendo=true;
							paginaActual--;
							
						}
						@Override
						protected void onModifierFinished(IEntity pItem) {
							moviendo=false;
						}
					});
				}
				
				return true;
			}
		};
		Sprite iconoMenu = new Sprite(ConstantesResolucion.getSprtMenuX_MASTER(),ConstantesResolucion.getSprtMenuY_MASTER(), context.getResouceManager().getIconoVolver_TR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction()==1){
					if(!moviendo){
						context.getInicio().inicioNormal();
						MenuMapas.this.context.setEscenaInicio();
					}
				}
				
				return true;
			}
		};
		flechaDerecha.setScale(ConstantesResolucion.getSprtMenuSize_MASTER());
		flechaIzquierda.setScale(ConstantesResolucion.getSprtMenuSize_MASTER());
		iconoMenu.setScale(ConstantesResolucion.getSprtMenuSize_MASTER());
		flechaDerecha.setFlippedHorizontal(true);
		escenaMapas.attachChild(flechaDerecha);
		escenaMapas.attachChild(flechaIzquierda);	
		escenaMapas.attachChild(iconoMenu);	
		escenaMapas.registerTouchArea(flechaDerecha);
		escenaMapas.registerTouchArea(flechaIzquierda);
		escenaMapas.registerTouchArea(iconoMenu);
		escenaMapas.setOnSceneTouchListener(this);
		
		

		if (spr_Bombas[0].getEstado()==-1){
			spr_Bombas[0].setEstado(0);
		}		
	}

	
	private float xDown=0;
	private float xCuadrado=0;
	private boolean movido=false;
	@Override
	public boolean onSceneTouchEvent(Scene arg0, TouchEvent arg1) {
		
		switch (arg1.getAction()) {
		case TouchEvent.ACTION_DOWN:
			xDown=arg1.getX();
			xCuadrado=cuadrado.getX();
			movido=false;
			break;
		case TouchEvent.ACTION_UP:
			
			Icono_bomba bomba =encuentraArea( arg1);
			if (bomba!=null){
				bomba.tocado();
			}
			
			break;
			
		case TouchEvent.ACTION_MOVE:
			if (Math.abs(xDown-arg1.getX())>7|| movido){
				movido=true;
				//cuadrado.setPosition(xCuadrado+(arg1.getX()-xDown), cuadrado.getInitialY());
			}
			
			break;
			
		default:
			break;
		}
		
		return true;
	}
	
	public Icono_bomba encuentraArea(TouchEvent arg1){
		for (Icono_bomba bomba : spr_Bombas) {
			try {
				if (bomba.contains(arg1.getX(), arg1.getY())) return bomba;
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return null;
	}
	
	
	public void actualizaMapas(){
		
		for ( Icono_bomba icono : spr_Bombas) {
			if(icono!=null){
				icono.actualizaEstrellas();
			}
		}
	}	
	
	
	
}
