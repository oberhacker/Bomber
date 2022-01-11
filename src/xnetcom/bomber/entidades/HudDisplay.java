package xnetcom.bomber.entidades;

import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.texture.region.TextureRegion;

import xnetcom.bomber.BomberEstado;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.ConstantesResolucion;

public class HudDisplay extends Sprite{

	private BomberGame context;
	private int minutes=0;
	private int seconds=0;
	private int lifes=0;
	private Text ct_tiempo;
	private Text ct_vidas;
	private Text ct_bombas;
	private Text ct_explosion;
	private Text ct_monedas;
	private TiledSprite spr_detonador;
	private TiledSprite spr_vidas;
	private TiledSprite spr_bombas;
	private TiledSprite spr_monedas;
	private TiledSprite spr_explosion;
	private int segundosPasados=0;
	
	public HudDisplay(BomberGame context,float pX, float pY, TextureRegion pTextureRegion) {
		super(0, 0, pTextureRegion,context.getVertexBufferObjectManager());
		this.context=context;
		//setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		//setAlpha(0.5f);
		
		
		float offsetX=(ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2)-(getWidth()/2);
		spr_detonador = new TiledSprite(0, 0, context.getResouceManager().getIconosHUDTR().deepCopy(),context.getVertexBufferObjectManager());
		spr_vidas = new TiledSprite(0,0, context.getResouceManager().getIconosHUDTR().deepCopy(),context.getVertexBufferObjectManager());
		spr_bombas = new TiledSprite(0, 0, context.getResouceManager().getIconosHUDTR().deepCopy(),context.getVertexBufferObjectManager());
		spr_explosion = new TiledSprite(0, 0, context.getResouceManager().getIconosHUDTR().deepCopy(),context.getVertexBufferObjectManager());
		spr_monedas = new TiledSprite(0, 0, context.getResouceManager().getIconosHUDTR().deepCopy(),context.getVertexBufferObjectManager());
		
		spr_vidas.setCurrentTileIndex(1);
		spr_bombas.setCurrentTileIndex(2);
		spr_explosion.setCurrentTileIndex(3);
		spr_monedas.setCurrentTileIndex(4);
		
		ct_tiempo = new Text(0, 0, context.getResouceManager().getmFontDigital(), getStringTiempo()+"    ",context.getVertexBufferObjectManager());
		ct_vidas = new Text(0, 0, context.getResouceManager().getmFontDigital(), "99",context.getVertexBufferObjectManager());
		ct_bombas = new Text(0, 0, context.getResouceManager().getmFontDigital(), "10",context.getVertexBufferObjectManager());
		ct_explosion = new Text(0, 0, context.getResouceManager().getmFontDigital(), "4",context.getVertexBufferObjectManager());
		ct_monedas = new Text(0, 0, context.getResouceManager().getmFontDigital(), "10",context.getVertexBufferObjectManager());
		
		//float x =(this.getWidth()/2)-(ct_tiempo.getWidth()/2);
		float y =(this.getHeight()/2)-(ct_tiempo.getHeight()/2);
		
		Text blanco = new Text(0, 0, context.getResouceManager().getmFontDigital(),"    ",context.getVertexBufferObjectManager());
		ct_tiempo.setPosition(offsetX+25, y);
		ct_vidas.setPosition(offsetX+375, y);
		ct_bombas.setPosition(offsetX+475, y);
		ct_explosion.setPosition(offsetX+575, y);
		ct_monedas.setPosition(offsetX+665, y);
		
		spr_detonador.setPosition(offsetX+275, 9);
		spr_vidas.setPosition(offsetX+325, 7);
		spr_bombas.setPosition(offsetX+425, 7);
		spr_explosion.setPosition(offsetX+525, 9);
		spr_monedas.setPosition(offsetX+615, 9);
			
		
		attachChild(blanco);
		attachChild(ct_tiempo);
		attachChild(ct_vidas);
		attachChild(ct_bombas);
		attachChild(ct_explosion);
		attachChild(ct_monedas);
		attachChild(spr_detonador);
		attachChild(spr_vidas);
		attachChild(spr_bombas);
		attachChild(spr_explosion);
		attachChild(spr_monedas);
		

		
		setPosition(offsetX, pY);
	}
	
	
	
	public void setVisibleMonedas(boolean visible){
		ct_monedas.setVisible(visible);
		//spr_monedas.setVisible(visible);
		
	}
	
	public void setTextoMonedas(String texto){
		ct_monedas.setText(texto);
	}
	
	public void actualizarIconos(){
		BomberEstado estado = context.getGameManager().getEstado();
		ct_bombas.setText(estado.getNumeroBombas().toString());
		ct_explosion.setText(estado.getTamExplosion().toString());
		ct_vidas.setText(estado.getVidas().toString());
		ct_monedas.setText(String.valueOf( context.getGameManager().getMonedero().getBoosterRestantes()));
		if (estado.isControlRemoto()){
			spr_detonador.setCurrentTileIndex(0);
		}else{
			spr_detonador.setCurrentTileIndex(5);
		}
	}
	
		
	boolean miVisible=true;
	public void setmiVisible(boolean visible){
		miVisible=visible;
	}
	
	
	
	@Override
	protected void onManagedUpdate(float pSecondsElapsed) {
		try{
			if((context.getEngine().getScene() == context.getEscenaJuego())) {
				if (miVisible){
					setVisible(true);				
				}else {
					setVisible(false);
				}
			}else{
				setVisible(false);
			}
		}catch(Exception e){}
	}
	
	public void setTime(int minutes, int seconds){
		
		this.minutes=minutes;
		this.seconds=seconds;
		ct_tiempo.setText( getStringTiempo()/*+" Lifes "+ getLifes()*/);
	}
		
	
	public void setLifes(int lifes){
		this.lifes =lifes;
	}
	
	public String getLifes(){
		String lif="";
		if (lifes<10){
			lif+="0"+lifes;
		}else{
			lif=""+lifes;
		}
		return lif;
	}
	
	
	public String getStringTiempo(){
		String minutos="";
		String segundos="";
		if (this.minutes<10) minutos="0";
		minutos+=""+this.minutes;
		if (this.seconds<10) segundos="0";
		segundos+=""+this.seconds;			
		return "Time: "+minutos +":"+segundos;		
	}



	public int getSegundosPasados() {
		return segundosPasados;
	}



	public void setSegundosPasados(int segundosPasados) {
		this.segundosPasados = segundosPasados;
	}
	
	
	
	
}



	
