package xnetcom.bomber.menus;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;

import android.util.Log;
import xnetcom.bomber.BomberGame;
import xnetcom.bomber.ResouceManager;
import xnetcom.bomber.util.ConstantesResolucion;
import xnetcom.bomber5.R;

public class Info {
	
	private BomberGame context;
	
	private Text info1_timer;
	private Text info1_det;
	private Text info1_vidas;
	private Text info1_bombas;
	private Text info1_tam;
	private Text info1_boost;
	private Text info2_camp;
	private Text info3_campa;
	private Text info4_corazon;
	private Text info4_expl;
	private Text info4_inm;
	private Text info4_bom;
	private Text info4_det;
	private Text info4_vel;
	private Text info4_fan;
	
	private Text info5_cred1;
	private Text info5_cred2;
	
	private Sprite info_1;
	private Sprite info_2;
	private Sprite info_3;
	private Sprite info_4;
	private Sprite info_5;
	
	
	private Sprite flechaDerecha;
	private Sprite flechaizquierda;
	private Sprite salir;
	
	private Scene ecenaInfo;

	public Scene getEcenaInfo() {
		return ecenaInfo;
	}

	private ResouceManager rm;
	int pagina=1;
	//float offset;
	
	float centro=0;
	
	public Info(final BomberGame context) {
		this.context=context;
		rm =context.getResouceManager();
		ecenaInfo = new Scene();
		
		
		
		//offset = (float) (((ConstantesResolucion.CAMERA_WIDTH_16_9_M-ConstantesResolucion.getCAMERA_WIDTH_MASTER()))/2);
		info_1 =new Sprite(0, 0, rm.getInfo_1TR(),context.getVertexBufferObjectManager());
		centro=ConstantesResolucion.getCAMERA_WIDTH_MASTER()/2;
		
		info1_timer = new Text(295, 90, context.getResouceManager().getmFontBomber(), context.getString(R.string.info1_timer),context.getVertexBufferObjectManager());
		info1_det = new Text(295, 180, context.getResouceManager().getmFontBomber(), context.getString(R.string.info1_det),context.getVertexBufferObjectManager());
		info1_vidas = new Text(295, 280, context.getResouceManager().getmFontBomber(), context.getString(R.string.info1_vidas),context.getVertexBufferObjectManager());
		info1_bombas = new Text(295, 380, context.getResouceManager().getmFontBomber(), context.getString(R.string.info1_bombas),context.getVertexBufferObjectManager());
		info1_tam = new Text(295, 475, context.getResouceManager().getmFontBomber(), context.getString(R.string.info1_tam),context.getVertexBufferObjectManager());
		info1_boost = new Text(295, 560, context.getResouceManager().getmFontBomber(), context.getString(R.string.info1_boost),context.getVertexBufferObjectManager());
		
		info_1.attachChild(info1_bombas);
		info_1.attachChild(info1_timer);
		info_1.attachChild(info1_det);
		info_1.attachChild(info1_vidas);
		info_1.attachChild(info1_tam);
		info_1.attachChild(info1_boost);
		ecenaInfo.attachChild(info_1);
		
		info2_camp = new Text(0, 0, context.getResouceManager().getmFontBomber(), context.getString(R.string.info2_camp1) +"\n "+context.getString(R.string.info2_camp2) +"\n "+context.getString(R.string.info2_camp3)+"\n "+context.getString(R.string.info2_camp4),context.getVertexBufferObjectManager());
		info_2= new Sprite(0, 0, rm.getInfo_2TR(),context.getVertexBufferObjectManager());
		info_2.attachChild(info2_camp);
		info_2.setVisible(false);
		info2_camp.setPosition(centro-(info2_camp.getWidth()/2), 320);
		ecenaInfo.attachChild(info_2);
		
		
		
		info3_campa = new Text(0, 0, context.getResouceManager().getmFontBomber(), context.getString(R.string.info3_campa1) +"\n "+context.getString(R.string.info3_campa2) +"\n "+context.getString(R.string.info3_campa3)+"\n "+context.getString(R.string.info3_campa4),context.getVertexBufferObjectManager());		
		info_3= new Sprite(0, 0, rm.getInfo_3TR(),context.getVertexBufferObjectManager());
		info_3.attachChild(info3_campa);
		info_3.setVisible(false);
		info3_campa.setPosition(centro-(info3_campa.getWidth()/2), 320);
		ecenaInfo.attachChild(info_3);
		
		
		int inicio=40;
		int separacion=82;
		
		info4_corazon = new Text(300, inicio, context.getResouceManager().getmFontBomber(), context.getString(R.string.info4_corazon),context.getVertexBufferObjectManager());
		info4_expl = new Text(300, inicio+separacion*1, context.getResouceManager().getmFontBomber(), context.getString(R.string.info4_expl),context.getVertexBufferObjectManager());
		info4_inm = new Text(300, inicio+separacion*2, context.getResouceManager().getmFontBomber(), context.getString(R.string.info4_inm),context.getVertexBufferObjectManager());
		info4_bom = new Text(300, inicio+separacion*3, context.getResouceManager().getmFontBomber(), context.getString(R.string.info4_bom),context.getVertexBufferObjectManager());
		info4_det = new Text(300, inicio+separacion*4, context.getResouceManager().getmFontBomber(), context.getString(R.string.info4_det),context.getVertexBufferObjectManager());
		info4_vel = new Text(300, inicio+separacion*5, context.getResouceManager().getmFontBomber(), context.getString(R.string.info4_vel),context.getVertexBufferObjectManager());
		info4_fan = new Text(300, inicio+separacion*6, context.getResouceManager().getmFontBomber(), context.getString(R.string.info4_fan),context.getVertexBufferObjectManager());

		info_4= new Sprite(0, 0, rm.getInfo_4TR(),context.getVertexBufferObjectManager());
		info_4.attachChild(info4_corazon);
		info_4.attachChild(info4_expl);
		info_4.attachChild(info4_inm);
		info_4.attachChild(info4_bom);
		info_4.attachChild(info4_det);
		info_4.attachChild(info4_vel);
		info_4.attachChild(info4_fan);
		info_4.setVisible(false);
		ecenaInfo.attachChild(info_4);
		
		
		
				
		info_5 = new Sprite(0, 0, rm.getFondo_mapas_TR(),context.getVertexBufferObjectManager());
		
		
		info5_cred1 = new Text(0, 300, context.getResouceManager().getmFontBomber(), context.getString(R.string.info5_cred1),context.getVertexBufferObjectManager());
		info5_cred2 = new Text(0, 360, context.getResouceManager().getmFontBomber(), context.getString(R.string.info5_cred2),context.getVertexBufferObjectManager());
		info5_cred1.setPosition(centro-(info5_cred1.getWidth()/2), 300);
		info5_cred2.setPosition(centro-(info5_cred2.getWidth()/2), 360);
		
		
		info_5.attachChild(info5_cred1);
		info_5.attachChild(info5_cred2);
		
		info_5.setVisible(false);
		ecenaInfo.attachChild(info_5);
		
		flechaDerecha = new Sprite(ConstantesResolucion.getCAMERA_WIDTH_MASTER()-context.getResouceManager().getIconoflecha_TR().getWidth()-50, ConstantesResolucion.getCAMERA_HEIGHT_MASTER()-context.getResouceManager().getIconoflecha_TR().getHeight()-5,  context.getResouceManager().getIconoflecha_TR().deepCopy(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				if (pSceneTouchEvent.isActionDown()&&pagina<5){
					pagina++;
					verPagina(pagina);
					
				}
				return false;
			}
		};
	
		
		flechaizquierda= new Sprite(30, ConstantesResolucion.getCAMERA_HEIGHT_MASTER()-context.getResouceManager().getIconoflecha_TR().getHeight()-5,  context.getResouceManager().getIconoflecha_TR().deepCopy(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				if (pSceneTouchEvent.isActionDown()&& pagina>1){
					pagina--;
					verPagina(pagina);
					
				}
				return false;
			}
		};		
		salir = new Sprite(context.alineacionCentradoHorizontal(context.getResouceManager().getIconoVolver_TR().getWidth()), ConstantesResolucion.getCAMERA_HEIGHT_MASTER()-context.getResouceManager().getIconoVolver_TR().getHeight()-5,  context.getResouceManager().getIconoVolver_TR(),context.getVertexBufferObjectManager()){
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
				
				if (pSceneTouchEvent.isActionDown()){
					context.getInicio().cargarMenuInicio();						
				}
				return false;
			}
		};
		salir.setPosition(centro-(salir.getWidth()/2), salir.getY());		
		
		
		flechaDerecha.setFlippedHorizontal(true);
		ecenaInfo.attachChild(flechaDerecha);
		ecenaInfo.attachChild(flechaizquierda);
		ecenaInfo.attachChild(salir);
		ecenaInfo.registerTouchArea(flechaDerecha);
		ecenaInfo.registerTouchArea(flechaizquierda);
		ecenaInfo.registerTouchArea(salir);
	}

	
	
	public void verInfo(){
		context.getEngine().setScene(ecenaInfo);
	}
	
	public void verPagina(int pagina){
		switch (pagina) {
		case 1:	
			info_1.setVisible(true);
			info_2.setVisible(false);
			info_3.setVisible(false);
			info_4.setVisible(false);
			info_5.setVisible(false);
			break;
		case 2:	
			info_1.setVisible(false);
			info_2.setVisible(true);
			info_3.setVisible(false);
			info_4.setVisible(false);
			info_5.setVisible(false);
			break;
		case 3:	
			info_1.setVisible(false);
			info_2.setVisible(false);
			info_3.setVisible(true);
			info_4.setVisible(false);
			info_5.setVisible(false);			
			break;

		case 4:
			info_1.setVisible(false);
			info_2.setVisible(false);
			info_3.setVisible(false);
			info_4.setVisible(true);
			info_5.setVisible(false);
			break;
		default:
			info_1.setVisible(false);
			info_2.setVisible(false);
			info_3.setVisible(false);
			info_4.setVisible(false);
			info_5.setVisible(true);			
			break;
			
			
			
		}
	}
	
	
	
	/*
	
	
	public void cargarAni
	*/
	
	
	
	
	
	
	
}
