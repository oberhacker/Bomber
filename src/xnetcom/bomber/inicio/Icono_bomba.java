package xnetcom.bomber.inicio;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.andengine.Pruebas;
import xnetcom.bomber.sql.DatabaseHandler;
import xnetcom.bomber.sql.DatosMapa;


public class Icono_bomba extends AnimatedSprite{

	
	
	String nombre="Mapa";
	
	/*
	 * Estado 	0 en gris no disponible
	 * 			1 disponible cero estrellas
	 * 			2 disponible una estella
	 * 			3 disponble dos estrellas
	 * 			4 disponible tres esrellas
	 */
	
	
	private BomberGame context;
	private Font fuente;
	private Text numero;
	private int estado=0;
	private int fontSize=50;
	private int offsetX=0;
	private int offsetY=0;
	private int numMapa;
	
	public int getNumMapa() {
		return numMapa;
	}


	public void setNumMapa(int numMapa) {
		this.numMapa = numMapa;
	}

	private TiledTextureRegion mBombaTexturaTR;
	
	public Icono_bomba(BomberGame context,Font fuente,int numMapa,float pX, float pY,TiledTextureRegion pTiledTextureRegion) {	
		
		super(pX, pY, pTiledTextureRegion,context.getVertexBufferObjectManager());		
		this.context=context;
		this.fuente=fuente;		
		numero = new Text(this.getScaleX()+(getWidthScaled()/2), this.getScaleY()+(getHeightScaled()/2), fuente, ""+numMapa,context.getVertexBufferObjectManager());
		numero.setPosition(-5+numero.getX()-numero.getWidth()/2,numero.getY()-numero.getHeight()/2);
		//System.out.println("iconobomba "+numMapa+"width "+numero.getWidth());
		mBombaTexturaTR=pTiledTextureRegion;
		numero.setColor(255, 255, 255);
		this.attachChild(numero);
		this.numMapa=numMapa;
		nombre+=""+numMapa;
				
		
		
		actualizaEstrellas();
		
		//context.getEscenaMapas().attachChild(this);
		setScale(0.9f);
	}

	
	
	public void actualizaEstrellas(){
		DatabaseHandler baseDatos = context.getBasedatos();
		DatosMapa datosMapa = baseDatos.getMapa(numMapa);
		 //List<DatosMapa> lista = baseDatos.getAllMapas();
		/* 
		 for (DatosMapa datosMapa2 : lista) {
			System.out.println("numero mapa "+datosMapa2.getNumeroMapa() +" estrellas:"+datosMapa2.getEstrellas() +" m_corazon" +datosMapa2.getM_corazon());
		}*/
		 
		 
				//=Preferencias.leerPreferenciasInt(nombre);
		//int estrellas =datosMapa.getEstrellas();
		// para que el primer mapa este disponible
		int estrellas =datosMapa.getEstrellas();
		setEstado(estrellas);
	//System.out.println("ICONO "+nombre+" ESTRELLAS "+estrellas);
	}
	
	public void setEstado(int estado){
		this.estado=estado;
		switch (estado) {
		case -1:
			this.setCurrentTileIndex(4);// aki abria que poner un candadito
			numero.setVisible(false);
			break;
		case 0:
			this.setCurrentTileIndex(0);
			numero.setVisible(true);
			break;
		case 1:
			this.setCurrentTileIndex(1);
			numero.setVisible(true);
			break;
		case 2:
			this.setCurrentTileIndex(2);
			numero.setVisible(true);
			break;
		case 3:
			this.setCurrentTileIndex(3);
			numero.setVisible(true);
			break;
			
		}
		//Preferencias.guardarPrefenrencias(nombre, estado);
		
	}
	
	public int getEstado(){
		return this.estado;
	}	

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent,float pTouchAreaLocalX, float pTouchAreaLocalY) {
		
		if (pSceneTouchEvent.getAction() == 0 ){
			tocado();
			return false;
		}
		return true;
	}
	
	
	public void tocado(){
		try {
			//System.out.println("tocado estado ="+estado);
			if (Pruebas.iconoBomba){
				context.getInicio().cargarMapa(this.numMapa);
				return;
			}
			if (estado!=-1)	context.getInicio().cargarMapa(this.numMapa);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		/*
		if (asd==0|| asd>4)asd=1;
		setEstado(asd);
		asd++;*/
		//this.nextTile();
	}
}
