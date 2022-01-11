package xnetcom.bomber.util;


public class ConstantesResolucion {
	
	public static String RES_16_9_M="16_9_M";
	
	public static String RES_WVGA="WVGA_15_9";
	public static String RES_QVGA="QVGA";

		
	private static String resolucion;

	private static String fondoInicio_MASTER;
	private static String fondoMenuMapas_MASTER;
	
	private static int TamFuenteCarga_MASTER;
	
	
	private static int CAMERA_WIDTH_MASTER;
	private static int CAMERA_HEIGHT_MASTER;

	// zom por defecto del juego
	private static float defaultZoom_MASTER;
	private static float ZoomMin_MASTER;
	private static float ZoomMax_MASTER;

	// cruceta
	private static int cruzetaX_MASTER;
	private static int cruzetaY_MASTER;
	private static float cruzeta_size_MASTER;

	// cruceta +
	private static int btnCruzeta_mas_X_MASTER;
	private static int btnCruzeta_mas_Y_MASTER;

	// cruceta -
	private static int btnCruzeta_menos_X_MASTER;
	private static int btnCruzeta_menos_Y_MASTER;

	// boton A
	private static int btn_Ax_MASTER;
	private static int btn_Ay_MASTER;
	// boton B
	private static int btn_Bx_MASTER;
	private static int btn_By_MASTER;

	// tamaño botones
	private static float btn_size_MASTER;

	// boton restaurar
	private static int btn_RestoreX_MASTER;
	private static int btn_RestoreY_MASTER;

	// boton del zoom +
	private static int btnZoom_mas_X_MASTER;
	private static int btnZoom_mas_Y_MASTER;
	// boton del zoom -
	private static int btnZoom_menos_X_MASTER;
	private static int btnZoom_menos_Y_MASTER;

	// boton de botones +
	private static int botones_mas_X_MASTER;
	private static int botones_mas_Y_MASTER;

	// boton de botones -
	private static int botones_menos_X_MASTER;
	private static int botones_menos_Y_MASTER;

	// tamaño de los botones de + - del menu
	private static float btnMenu_Size_MASTER;

	// constantes del menu de niveles
	private static int offsetX_BombasIcono_MASTER;
	private static int offsetY_BombasIcono_MASTER;

	private static float size_BombasIcono_MASTER;

	private static int anchoColumna_BombasIcono_MASTER;
	private static int altoFila_BombasIcono_MASTER;

	// menu inicio
	private static int sprtBombaPlayX_MASTER;
	private static int sprtBombaPlayY_MASTER;
	private static float sprtBombaPlaySize_MASTER;

	private static int sprtOpcionesPlayX_MASTER;
	private static int sprtOpcionesPlayY_MASTER;
	private static float sprtOpcionesPlaySize_MASTER;
	
	private static int sprtFlechaDerechaX_MASTER;
	private static int sprtFlechaDerechaY_MASTER;
	
	private static int sprtFlechaIzquierdaX_MASTER;
	private static int sprtFlechaIzquierdaY_MASTER;
	
	private static int sprtMenuX_MASTER;
	private static int sprtMenuY_MASTER;
	private static float sprtMenuSize_MASTER;
	
	
	private static int txtHUD_X_MASTER;
	private static int txtHUD_Y_MASTER;
	private static int txtHUD_Size_MASTER;
	
	
	private   static int offsetX_camara;
	
	
	//////////////////////////////////////////////
	/////////////////////////////////////////////
	
	/*
	
	private  final static int CAMERA_WIDTH_16_9_H = 1920;
	private  final static int CAMERA_HEIGHT_16_9_H  = 1080;	
	
	
	private  final static int CAMERA_WIDTH_WVGA = 800;
	private  final static int CAMERA_HEIGHT_WVGA  = 480;
	
	private  final static int CAMERA_WIDTH_16_9_L = 852;
	private  final static int CAMERA_HEIGHT_16_9_L  = 480;
	
	private  final static int CAMERA_WIDTH_4_3_L = 480;
	private  final static int CAMERA_HEIGHT_4_3_L = 320;
	
	private  final static int CAMERA_WIDTH_4_3_M = 640;
	private  final static int CAMERA_HEIGHT_4_3M = 480;
	
	private  final static int CAMERA_WIDTH_4_3_H = 1024;
	private  final static int CAMERA_HEIGHT_4_3_H = 768;
	*/
	
	
	///////////////////////////////////////////
	///////////////////////////////////////////
	
	public static int getOffsetX_camara() {
		return offsetX_camara;
	}


	private static String fondoInicio_16_9_M ="fondo_inicio_1280.jpg";
	private static String fondoMenuMapas_16_9_M="menu_mapas_1280.jpg";
	
	private static int TamFuenteCarga_16_9_M=60;
	
	//   1280 * 720
	public  final static int CAMERA_WIDTH_16_9_M = 1280;
	public  final static int CAMERA_HEIGHT_16_9_M  = 720;
	
	// zom por defecto del juego	
	private  final static float defaultZoom_16_9_M=1.6f;
	private  final static float ZoomMin_16_9_M=0.64f;
	private  final static float ZoomMax_16_9_M=1.2f;
	
	
	// cruceta
	private  final static int cruzetaX_16_9_M=98;
	private  final static int cruzetaY_16_9_M=505;
	private  final static float cruzeta_size_16_9_M=2.25f;
	
	
	// cruceta -
	private  final static int btnCruzeta_menos_X_16_9_M=40+40;
	private  final static int btnCruzeta_menos_Y_16_9_M=300;	
	
	
	// cruceta +
	private  final static int btnCruzeta_mas_X_16_9_M=150+20;
	private  final static int btnCruzeta_mas_Y_16_9_M=300;
	

	
	// boton A
	private  final static int btn_Ax_16_9_M=814;
	private  final static int btn_Ay_16_9_M=500;
	//boton B
	private  final static int btn_Bx_16_9_M=1042;
	private  final static int btn_By_16_9_M=500;
	
	// tamaño botones 
	private  final static float btn_size_16_9_M=0.7f;
	
	//boton restaurar
	private  final static int btn_RestoreX_16_9_M=536;
	private  final static int btn_RestoreY_16_9_M=610;
	
	// boton del zoom -
	private  final static int btnZoom_menos_X_16_9_M=360;
	private  final static int btnZoom_menos_Y_16_9_M=360;
	// boton del zoom +
	private  final static int btnZoom_mas_X_16_9_M=450;
	private  final static int btnZoom_mas_Y_16_9_M=360;
  
	
	
	// boton de botones -
	private  final static int botones_menos_X_16_9_M=970;
	private  final static int botones_menos_Y_16_9_M=415;
	
	// boton de botones +
	private  final static int botones_mas_X_16_9_M=1060;
	private  final static int botones_mas_Y_16_9_M=415;



	// tamaño de los botones de + - del menu
	private  final static float btnMenu_Size_16_9_M=1f;
	
	private  final static int offsetX_camara_16_9_M=0;
	
	
	// constantes del menu de niveles
	private  final static int offsetX_BombasIcono_16_9_M=170;
	private  final static int offsetY_BombasIcono_16_9_M=20;
	
	private  final static float size_BombasIcono_16_9_M=1f;
	
	private  final static int anchoColumna_BombasIcono_16_9_M=200;
	private  final static int altoFila_BombasIcono_16_9_M=179;
	
	// menu inicio
	private  final static int sprtBombaPlayX_16_9_M=500;
	private  final static int sprtBombaPlayY_16_9_M=300;
	private  final static float sprtBombaPlaySize_16_9_M=1f;
	
	private  final static int sprtOpcionesPlayX_16_9_M=50;
	private  final static int sprtOpcionesPlayY_16_9_M=600;
	private  final static float sprtOpcionesPlaySize_16_9_M=1f;
	
	
	private final static int sprtFlechaIzquierdaX_16_9_M=400;
	private final static int sprtFlechaIzquierdaY_16_9_M=600;
	
	
	private final static int sprtMenuX_16_9_M=576;
	private final static int sprtMenuY_16_9_M=600;
	private final static float sprtMenuSize_16_9_M=1;	
	
	private final static int sprtFlechaDerechaX_16_9_M=752;
	private final static int sprtFlechaDerechaY_16_9_M=600;
	

// del texto dde hud
	private static int txtHUD_X_16_9_M=20;
	private static int txtHUD_Y_16_9_M=20;
	private static int txtHUD_Size_16_9_M=25;
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	//private  final static  String fondoInicio_WVGA="fondo_inicio_800_p.jpg";
	//private static String fondoMenuMapas_WVGA="menu_mapas_1280.jpg";

	private static String fondoInicio_WVGA ="fondo_inicio_1280.jpg";
	private static String fondoMenuMapas_WVGA="menu_mapas_1280.jpg";
	
	private static int TamFuenteCarga_WVGA=60;
	
	//   1280 * 720
	public  final static int CAMERA_WIDTH_WVGA = 1200;
	public  final static int CAMERA_HEIGHT_WVGA  = 720;
	
	// zom por defecto del juego	
	private  final static float defaultZoom_WVGA=1.6f;
	private  final static float ZoomMin_WVGA=0.64f;
	private  final static float ZoomMax_WVGA=1.2f;
	
	
	// cruceta
	private  final static int cruzetaX_WVGA=98;
	private  final static int cruzetaY_WVGA=505;
	private  final static float cruzeta_size_WVGA=2.25f;
	
	
	// cruceta -
	private  final static int btnCruzeta_menos_X_WVGA=40+40;
	private  final static int btnCruzeta_menos_Y_WVGA=300;	
	
	
	
	// cruceta +
	private  final static int btnCruzeta_mas_X_WVGA=150+20;
	private  final static int btnCruzeta_mas_Y_WVGA=300;
	
	


	// boton A
	private  final static int btn_Ax_WVGA=734;
	private  final static int btn_Ay_WVGA=500;
	
	//boton B
	private  final static int btn_Bx_WVGA=962;
	private  final static int btn_By_WVGA=500;
	
	// tamaño botones 
	private  final static float btn_size_WVGA=0.7f;
	
	//boton restaurar
	private  final static int btn_RestoreX_WVGA=456;
	private  final static int btn_RestoreY_WVGA=610;
	 
	// boton del zoom -	
	private  final static int btnZoom_menos_X_WVGA=360;
	private  final static int btnZoom_menos_Y_WVGA=360;
	// boton del zoom +
	private  final static int btnZoom_mas_X_WVGA=450;
	private  final static int btnZoom_mas_Y_WVGA=360;

	
	// boton de botones -
	private  final static int botones_menos_X_WVGA=970;
	private  final static int botones_menos_Y_WVGA=415;
	
	// boton de botones +
	private  final static int botones_mas_X_WVGA=1060;
	private  final static int botones_mas_Y_WVGA=415;



	// tamaño de los botones de + - del menu
	private  final static float btnMenu_Size_WVGA=1f;
	
	
	private  final static int offsetX_camara_WVGA=40;
	
	
	// constantes del menu de niveles
	private  final static int offsetX_BombasIcono_WVGA=170;
	private  final static int offsetY_BombasIcono_WVGA=20;
	
	private  final static float size_BombasIcono_WVGA=1f;
	
	private  final static int anchoColumna_BombasIcono_WVGA=200;
	private  final static int altoFila_BombasIcono_WVGA=179;
	
	// menu inicio
	private  final static int sprtBombaPlayX_WVGA=500;
	private  final static int sprtBombaPlayY_WVGA=300;
	private  final static float sprtBombaPlaySize_WVGA=1f;
	
	private  final static int sprtOpcionesPlayX_WVGA=50;
	private  final static int sprtOpcionesPlayY_WVGA=600;
	private  final static float sprtOpcionesPlaySize_WVGA=1f;
	
	
	private final static int sprtFlechaIzquierdaX_WVGA=400;
	private final static int sprtFlechaIzquierdaY_WVGA=600;
	
	
	private final static int sprtMenuX_WVGA=576;
	private final static int sprtMenuY_WVGA=600;
	private final static float sprtMenuSize_WVGA=1;	
	
	private final static int sprtFlechaDerechaX_WVGA=752;
	private final static int sprtFlechaDerechaY_WVGA=600;
	

// del texto dde hud
	private static int txtHUD_X_WVGA=20;
	private static int txtHUD_Y_WVGA=20;
	private static int txtHUD_Size_WVGA=25;

	
	
	
	
	
	


	
	//private  final static  String fondoInicio_QVGA="fondo_inicio_800_p.jpg";
	//private static String fondoMenuMapas_QVGA="menu_mapas_1280.jpg";

	private static String fondoInicio_QVGA ="fondo_inicio_1280.jpg";
	private static String fondoMenuMapas_QVGA="menu_mapas_1280.jpg";
	
	private static int TamFuenteCarga_QVGA=60;
	
	//   1280 * 720
	public  final static int CAMERA_WIDTH_QVGA = 960;
	public  final static int CAMERA_HEIGHT_QVGA  = 720;
	
	// zom por defecto del juego	
	private  final static float defaultZoom_QVGA=1.6f;
	private  final static float ZoomMin_QVGA=0.64f;
	private  final static float ZoomMax_QVGA=1.2f;
	
	
	// cruceta
	private  final static int cruzetaX_QVGA=98;
	private  final static int cruzetaY_QVGA=505;
	private  final static float cruzeta_size_QVGA=2.25f;
	
	
	// cruceta -
	private  final static int btnCruzeta_menos_X_QVGA=40+40;
	private  final static int btnCruzeta_menos_Y_QVGA=300;	
	
	
	
	// cruceta +
	private  final static int btnCruzeta_mas_X_QVGA=150+20;
	private  final static int btnCruzeta_mas_Y_QVGA=300;
	
	


	// boton A
	private  final static int btn_Ax_QVGA=734;
	private  final static int btn_Ay_QVGA=500;
	
	//boton B
	private  final static int btn_Bx_QVGA=962;
	private  final static int btn_By_QVGA=500;
	
	// tamaño botones 
	private  final static float btn_size_QVGA=0.7f;
	
	//boton restaurar
	private  final static int btn_RestoreX_QVGA=456;
	private  final static int btn_RestoreY_QVGA=610;
	 
	// boton del zoom -	
	private  final static int btnZoom_menos_X_QVGA=360;
	private  final static int btnZoom_menos_Y_QVGA=360;
	// boton del zoom +
	private  final static int btnZoom_mas_X_QVGA=450;
	private  final static int btnZoom_mas_Y_QVGA=360;

	
	// boton de botones -
	private  final static int botones_menos_X_QVGA=970;
	private  final static int botones_menos_Y_QVGA=415;
	
	// boton de botones +
	private  final static int botones_mas_X_QVGA=1060;
	private  final static int botones_mas_Y_QVGA=415;



	// tamaño de los botones de + - del menu
	private  final static float btnMenu_Size_QVGA=1f;
	
	
	private  final static int offsetX_camara_QVGA=160;
	
	
	// constantes del menu de niveles
	private  final static int offsetX_BombasIcono_QVGA=170;
	private  final static int offsetY_BombasIcono_QVGA=20;
	
	private  final static float size_BombasIcono_QVGA=1f;
	
	private  final static int anchoColumna_BombasIcono_QVGA=200;
	private  final static int altoFila_BombasIcono_QVGA=179;
	
	// menu inicio
	private  final static int sprtBombaPlayX_QVGA=500;
	private  final static int sprtBombaPlayY_QVGA=300;
	private  final static float sprtBombaPlaySize_QVGA=1f;
	
	private  final static int sprtOpcionesPlayX_QVGA=180;
	private  final static int sprtOpcionesPlayY_QVGA=600;
	private  final static float sprtOpcionesPlaySize_QVGA=1f;
	
	
	private final static int sprtFlechaIzquierdaX_QVGA=400;
	private final static int sprtFlechaIzquierdaY_QVGA=600;
	
	
	private final static int sprtMenuX_QVGA=576;
	private final static int sprtMenuY_QVGA=600;
	private final static float sprtMenuSize_QVGA=1;	
	
	private final static int sprtFlechaDerechaX_QVGA=752;
	private final static int sprtFlechaDerechaY_QVGA=600;
	

// del texto dde hud
	private static int txtHUD_X_QVGA=20;
	private static int txtHUD_Y_QVGA=20;
	private static int txtHUD_Size_QVGA=25;

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void  inicia(String resolucion){
		ConstantesResolucion.resolucion=resolucion;
		getPreferencias(resolucion);
	}
	
	
	public static void getPreferencias(String resolucion){	
		if (resolucion.equals("16_9_M")){
			atributos_16_9_M();
		}
		if (resolucion.equals("WVGA_15_9")){
			atributosWVGA();
		}
		if (resolucion.equals("QVGA")){
			atributosQVGA();
		}
		
	}
	
	
	
	
	

	
	
	
	public static void atributosQVGA(){
		offsetX_camara=offsetX_camara_QVGA;

		TamFuenteCarga_MASTER=TamFuenteCarga_QVGA;
			fondoInicio_MASTER=fondoInicio_QVGA;
			fondoMenuMapas_MASTER=fondoMenuMapas_QVGA;
		  CAMERA_WIDTH_MASTER=CAMERA_WIDTH_QVGA;
		  CAMERA_HEIGHT_MASTER=CAMERA_HEIGHT_QVGA;

		// zom por defecto del juego
		  defaultZoom_MASTER = defaultZoom_QVGA;
		  ZoomMin_MASTER = ZoomMin_QVGA;
		  ZoomMax_MASTER = ZoomMax_QVGA;

		// cruceta
		  cruzetaX_MASTER = cruzetaX_QVGA;
		  cruzetaY_MASTER = cruzetaY_QVGA;
		  cruzeta_size_MASTER = cruzeta_size_QVGA;

		// cruceta +
		  btnCruzeta_mas_X_MASTER = btnCruzeta_mas_X_QVGA;
		  btnCruzeta_mas_Y_MASTER = btnCruzeta_mas_Y_QVGA;

		// cruceta -
		  btnCruzeta_menos_X_MASTER = btnCruzeta_menos_X_QVGA;
		  btnCruzeta_menos_Y_MASTER = btnCruzeta_menos_Y_QVGA;	

		// boton A
		  btn_Ax_MASTER = btn_Ax_QVGA;
		  btn_Ay_MASTER = btn_Ay_QVGA;
		// boton B
		  btn_Bx_MASTER =  btn_Bx_QVGA;
		  btn_By_MASTER =btn_By_QVGA;

		// tamaño botones
		  btn_size_MASTER = btn_size_QVGA;

		// boton restaurar
		  btn_RestoreX_MASTER = btn_RestoreX_QVGA;
		  btn_RestoreY_MASTER =  btn_RestoreY_QVGA;

		// boton del zoom +
		  btnZoom_mas_X_MASTER = btnZoom_mas_X_QVGA;
		  btnZoom_mas_Y_MASTER = btnZoom_mas_Y_QVGA;
		// boton del zoom -
		  btnZoom_menos_X_MASTER = btnZoom_menos_X_QVGA;
		  btnZoom_menos_Y_MASTER = btnZoom_menos_Y_QVGA;

		// boton de botones +
		  botones_mas_X_MASTER = botones_mas_X_QVGA;
		  botones_mas_Y_MASTER =  botones_mas_Y_QVGA;

		// boton de botones -
		  botones_menos_X_MASTER = botones_menos_X_QVGA;
		  botones_menos_Y_MASTER = botones_menos_Y_QVGA;

		// tamaño de los botones de + - del menu
		  btnMenu_Size_MASTER = btnMenu_Size_QVGA;

		// constantes del menu de niveles
		  offsetX_BombasIcono_MASTER = offsetX_BombasIcono_QVGA;
		  offsetY_BombasIcono_MASTER = offsetY_BombasIcono_QVGA;

		  size_BombasIcono_MASTER = size_BombasIcono_QVGA;

		  anchoColumna_BombasIcono_MASTER = anchoColumna_BombasIcono_QVGA;
		  altoFila_BombasIcono_MASTER = altoFila_BombasIcono_QVGA;

		// menu inicio
		  sprtBombaPlayX_MASTER = sprtBombaPlayX_QVGA;
		  sprtBombaPlayY_MASTER = sprtBombaPlayY_QVGA;
		  sprtBombaPlaySize_MASTER = sprtBombaPlaySize_QVGA;

		  sprtOpcionesPlayX_MASTER = sprtOpcionesPlayX_QVGA;
		  sprtOpcionesPlayY_MASTER = sprtOpcionesPlayY_QVGA;
		  sprtOpcionesPlaySize_MASTER = sprtOpcionesPlaySize_QVGA;
		  
			
		 sprtFlechaIzquierdaX_MASTER=sprtFlechaIzquierdaX_QVGA;
		 sprtFlechaIzquierdaY_MASTER=sprtFlechaIzquierdaY_QVGA;


		 sprtMenuX_MASTER=sprtMenuX_QVGA;
		 sprtMenuY_MASTER=sprtMenuY_QVGA;
		 sprtMenuSize_MASTER=sprtMenuSize_QVGA;

		 sprtFlechaDerechaX_MASTER=sprtFlechaDerechaX_QVGA;
		 sprtFlechaDerechaY_MASTER=sprtFlechaDerechaY_QVGA;

		 txtHUD_X_MASTER = txtHUD_X_QVGA;
		 txtHUD_Y_MASTER = txtHUD_Y_QVGA;
		 txtHUD_Size_MASTER = txtHUD_Size_QVGA;
			 
	}
	
	
	
	
	
	
	
	
	public static void atributos_16_9_M(){
		offsetX_camara=offsetX_camara_16_9_M;

		TamFuenteCarga_MASTER=TamFuenteCarga_16_9_M;
			fondoInicio_MASTER=fondoInicio_16_9_M;
			fondoMenuMapas_MASTER=fondoMenuMapas_16_9_M;
		  CAMERA_WIDTH_MASTER=CAMERA_WIDTH_16_9_M;
		  CAMERA_HEIGHT_MASTER=CAMERA_HEIGHT_16_9_M;

		// zom por defecto del juego
		  defaultZoom_MASTER = defaultZoom_16_9_M;
		  ZoomMin_MASTER = ZoomMin_16_9_M;
		  ZoomMax_MASTER = ZoomMax_16_9_M;

		// cruceta
		  cruzetaX_MASTER = cruzetaX_16_9_M;
		  cruzetaY_MASTER = cruzetaY_16_9_M;
		  cruzeta_size_MASTER = cruzeta_size_16_9_M;

		// cruceta +
		  btnCruzeta_mas_X_MASTER = btnCruzeta_mas_X_16_9_M;
		  btnCruzeta_mas_Y_MASTER = btnCruzeta_mas_Y_16_9_M;

		// cruceta -
		  btnCruzeta_menos_X_MASTER = btnCruzeta_menos_X_16_9_M;
		  btnCruzeta_menos_Y_MASTER = btnCruzeta_menos_Y_16_9_M;	

		// boton A
		  btn_Ax_MASTER = btn_Ax_16_9_M;
		  btn_Ay_MASTER = btn_Ay_16_9_M;
		// boton B
		  btn_Bx_MASTER =  btn_Bx_16_9_M;
		  btn_By_MASTER =btn_By_16_9_M;

		// tamaño botones
		  btn_size_MASTER = btn_size_16_9_M;

		// boton restaurar
		  btn_RestoreX_MASTER = btn_RestoreX_16_9_M;
		  btn_RestoreY_MASTER =  btn_RestoreY_16_9_M;

		// boton del zoom +
		  btnZoom_mas_X_MASTER = btnZoom_mas_X_16_9_M;
		  btnZoom_mas_Y_MASTER = btnZoom_mas_Y_16_9_M;
		// boton del zoom -
		  btnZoom_menos_X_MASTER = btnZoom_menos_X_16_9_M;
		  btnZoom_menos_Y_MASTER = btnZoom_menos_Y_16_9_M;

		// boton de botones +
		  botones_mas_X_MASTER = botones_mas_X_16_9_M;
		  botones_mas_Y_MASTER =  botones_mas_Y_16_9_M;

		// boton de botones -
		  botones_menos_X_MASTER = botones_menos_X_16_9_M;
		  botones_menos_Y_MASTER = botones_menos_Y_16_9_M;

		// tamaño de los botones de + - del menu
		  btnMenu_Size_MASTER = btnMenu_Size_16_9_M;

		// constantes del menu de niveles
		  offsetX_BombasIcono_MASTER = offsetX_BombasIcono_16_9_M;
		  offsetY_BombasIcono_MASTER = offsetY_BombasIcono_16_9_M;

		  size_BombasIcono_MASTER = size_BombasIcono_16_9_M;

		  anchoColumna_BombasIcono_MASTER = anchoColumna_BombasIcono_16_9_M;
		  altoFila_BombasIcono_MASTER = altoFila_BombasIcono_16_9_M;

		// menu inicio
		  sprtBombaPlayX_MASTER = sprtBombaPlayX_16_9_M;
		  sprtBombaPlayY_MASTER = sprtBombaPlayY_16_9_M;
		  sprtBombaPlaySize_MASTER = sprtBombaPlaySize_16_9_M;

		  sprtOpcionesPlayX_MASTER = sprtOpcionesPlayX_16_9_M;
		  sprtOpcionesPlayY_MASTER = sprtOpcionesPlayY_16_9_M;
		  sprtOpcionesPlaySize_MASTER = sprtOpcionesPlaySize_16_9_M;
		  
			
		 sprtFlechaIzquierdaX_MASTER=sprtFlechaIzquierdaX_16_9_M;
		 sprtFlechaIzquierdaY_MASTER=sprtFlechaIzquierdaY_16_9_M;


		 sprtMenuX_MASTER=sprtMenuX_16_9_M;
		 sprtMenuY_MASTER=sprtMenuY_16_9_M;
		 sprtMenuSize_MASTER=sprtMenuSize_16_9_M;

		 sprtFlechaDerechaX_MASTER=sprtFlechaDerechaX_16_9_M;
		 sprtFlechaDerechaY_MASTER=sprtFlechaDerechaY_16_9_M;

		 txtHUD_X_MASTER = txtHUD_X_16_9_M;
		 txtHUD_Y_MASTER = txtHUD_Y_16_9_M;
		 txtHUD_Size_MASTER = txtHUD_Size_16_9_M;
			 
			 
			
		  
		  
	}

	
	
	
	public static void atributosWVGA(){
		   
		offsetX_camara= offsetX_camara_WVGA;
		
	  CAMERA_WIDTH_MASTER=CAMERA_WIDTH_WVGA;
	  CAMERA_HEIGHT_MASTER=CAMERA_HEIGHT_WVGA;
	  
	  TamFuenteCarga_MASTER=TamFuenteCarga_WVGA;
	  fondoInicio_MASTER=fondoInicio_WVGA;
	  fondoMenuMapas_MASTER=fondoMenuMapas_WVGA;

	// zom por defecto del juego
	  defaultZoom_MASTER = defaultZoom_WVGA;
	  ZoomMin_MASTER = ZoomMin_WVGA;
	  ZoomMax_MASTER = ZoomMax_WVGA;

	// cruceta
	  cruzetaX_MASTER = cruzetaX_WVGA;
	  cruzetaY_MASTER = cruzetaY_WVGA;
	  cruzeta_size_MASTER = cruzeta_size_WVGA;

	// cruceta +
	  btnCruzeta_mas_X_MASTER = btnCruzeta_mas_X_WVGA;
	  btnCruzeta_mas_Y_MASTER = btnCruzeta_mas_Y_WVGA;

	// cruceta -
	  btnCruzeta_menos_X_MASTER = btnCruzeta_menos_X_WVGA;
	  btnCruzeta_menos_Y_MASTER = btnCruzeta_menos_Y_WVGA;	

	// boton A
	  btn_Ax_MASTER = btn_Ax_WVGA;
	  btn_Ay_MASTER = btn_Ay_WVGA;
	// boton B
	  btn_Bx_MASTER =  btn_Bx_WVGA;
	  btn_By_MASTER =btn_By_WVGA;

	// tamaño botones
	  btn_size_MASTER = btn_size_WVGA;

	// boton restaurar
	  btn_RestoreX_MASTER = btn_RestoreX_WVGA;
	  btn_RestoreY_MASTER =  btn_RestoreY_WVGA;

	// boton del zoom +
	  btnZoom_mas_X_MASTER = btnZoom_mas_X_WVGA;
	  btnZoom_mas_Y_MASTER = btnZoom_mas_Y_WVGA;
	// boton del zoom -
	  btnZoom_menos_X_MASTER = btnZoom_menos_X_WVGA;
	  btnZoom_menos_Y_MASTER = btnZoom_menos_Y_WVGA;

	// boton de botones +
	  botones_mas_X_MASTER = botones_mas_X_WVGA;
	  botones_mas_Y_MASTER =  botones_mas_Y_WVGA;

	// boton de botones -
	  botones_menos_X_MASTER = botones_menos_X_WVGA;
	  botones_menos_Y_MASTER = botones_menos_Y_WVGA;

	// tamaño de los botones de + - del menu
	  btnMenu_Size_MASTER = btnMenu_Size_WVGA;

	// constantes del menu de niveles
	  offsetX_BombasIcono_MASTER = offsetX_BombasIcono_WVGA;
	  offsetY_BombasIcono_MASTER = offsetY_BombasIcono_WVGA;

	  size_BombasIcono_MASTER = size_BombasIcono_WVGA;

	  anchoColumna_BombasIcono_MASTER = anchoColumna_BombasIcono_WVGA;
	  altoFila_BombasIcono_MASTER = altoFila_BombasIcono_WVGA;

	// menu inicio
	  sprtBombaPlayX_MASTER = sprtBombaPlayX_WVGA;
	  sprtBombaPlayY_MASTER = sprtBombaPlayY_WVGA;
	  sprtBombaPlaySize_MASTER = sprtBombaPlaySize_WVGA;

	  sprtOpcionesPlayX_MASTER = sprtOpcionesPlayX_WVGA;
	  sprtOpcionesPlayY_MASTER = sprtOpcionesPlayY_WVGA;
	  sprtOpcionesPlaySize_MASTER = sprtOpcionesPlaySize_WVGA;
	  
	   
		
	 sprtFlechaIzquierdaX_MASTER=sprtFlechaIzquierdaX_WVGA;
	 sprtFlechaIzquierdaY_MASTER=sprtFlechaIzquierdaY_WVGA;
	
	
	 sprtMenuX_MASTER=sprtMenuX_WVGA;
	 sprtMenuY_MASTER=sprtMenuY_WVGA;
	 sprtMenuSize_MASTER=sprtMenuSize_WVGA;
	
	 sprtFlechaDerechaX_MASTER=sprtFlechaDerechaX_WVGA;
	 sprtFlechaDerechaY_MASTER=sprtFlechaDerechaY_WVGA;
	 
	 txtHUD_X_MASTER = txtHUD_X_WVGA;
	 txtHUD_Y_MASTER = txtHUD_Y_WVGA;
	 txtHUD_Size_MASTER = txtHUD_Size_WVGA;
	  
	}

	public static int getCAMERA_WIDTH_MASTER() {
		return CAMERA_WIDTH_MASTER;
	}


	public static int getCAMERA_HEIGHT_MASTER() {
		return CAMERA_HEIGHT_MASTER;
	}


	public static float getDefaultZoom_MASTER() {
		return defaultZoom_MASTER;
	}


	public static float getZoomMin_MASTER() {
		return ZoomMin_MASTER;
	}


	public static float getZoomMax_MASTER() {
		return ZoomMax_MASTER;
	}


	public static int getCruzetaX_MASTER() {
		return cruzetaX_MASTER;
	}


	public static int getCruzetaY_MASTER() {
		return cruzetaY_MASTER;
	}


	public static float getCruzeta_size_MASTER() {
		return cruzeta_size_MASTER;
	}


	public static int getBtnCruzeta_mas_X_MASTER() {
		return btnCruzeta_mas_X_MASTER;
	}


	public static int getBtnCruzeta_mas_Y_MASTER() {
		return btnCruzeta_mas_Y_MASTER;
	}


	public static int getBtnCruzeta_menos_X_MASTER() {
		return btnCruzeta_menos_X_MASTER;
	}


	public static int getBtnCruzeta_menos_Y_MASTER() {
		return btnCruzeta_menos_Y_MASTER;
	}


	public static int getBtn_Ax_MASTER() {
		return btn_Ax_MASTER;
	}


	public static int getBtn_Ay_MASTER() {
		return btn_Ay_MASTER;
	}


	public static int getBtn_Bx_MASTER() {
		return btn_Bx_MASTER;
	}


	public static int getBtn_By_MASTER() {
		return btn_By_MASTER;
	}


	public static float getBtn_size_MASTER() {
		return btn_size_MASTER;
	}


	public static int getBtn_RestoreX_MASTER() {
		return btn_RestoreX_MASTER;
	}


	public static int getBtn_RestoreY_MASTER() {
		return btn_RestoreY_MASTER;
	}


	public static int getBtnZoom_mas_X_MASTER() {
		return btnZoom_mas_X_MASTER;
	}


	public static int getBtnZoom_mas_Y_MASTER() {
		return btnZoom_mas_Y_MASTER;
	}


	public static int getBtnZoom_menos_X_MASTER() {
		return btnZoom_menos_X_MASTER;
	}


	public static int getBtnZoom_menos_Y_MASTER() {
		return btnZoom_menos_Y_MASTER;
	}


	public static int getBotones_mas_X_MASTER() {
		return botones_mas_X_MASTER;
	}


	public static int getBotones_mas_Y_MASTER() {
		return botones_mas_Y_MASTER;
	}


	public static int getBotones_menos_X_MASTER() {
		return botones_menos_X_MASTER;
	}


	public static int getBotones_menos_Y_MASTER() {
		return botones_menos_Y_MASTER;
	}


	public static float getBtnMenu_Size_MASTER() {
		return btnMenu_Size_MASTER;
	}


	public static int getOffsetX_BombasIcono_MASTER() {
		return offsetX_BombasIcono_MASTER;
	}


	public static int getOffsetY_BombasIcono_MASTER() {
		return offsetY_BombasIcono_MASTER;
	}


	public static float getSize_BombasIcono_MASTER() {
		return size_BombasIcono_MASTER;
	}


	public static int getAnchoColumna_BombasIcono_MASTER() {
		return anchoColumna_BombasIcono_MASTER;
	}


	public static int getAltoFila_BombasIcono_MASTER() {
		return altoFila_BombasIcono_MASTER;
	}


	public static int getSprtBombaPlayX_MASTER() {
		return sprtBombaPlayX_MASTER;
	}


	public static int getSprtBombaPlayY_MASTER() {
		return sprtBombaPlayY_MASTER;
	}


	public static float getSprtBombaPlaySize_MASTER() {
		return sprtBombaPlaySize_MASTER;
	}


	public static int getSprtOpcionesPlayX_MASTER() {
		return sprtOpcionesPlayX_MASTER;
	}


	public static int getSprtOpcionesPlayY_MASTER() {
		return sprtOpcionesPlayY_MASTER;
	}


	public static float getSprtOpcionesPlaySize_MASTER() {
		return sprtOpcionesPlaySize_MASTER;
	}
	public static String getResolucion(){
		return resolucion;
	}
	public static String getFondoInicio(){
		return fondoInicio_MASTER;
	}
	public static String getFondoMenuMapas(){
		return fondoMenuMapas_MASTER;
	}
	public static int getTamFuenteCarga(){
		return TamFuenteCarga_MASTER;
	}


	public static int getSprtFlechaDerechaX_MASTER() {
		return sprtFlechaDerechaX_MASTER;
	}


	public static int getSprtFlechaDerechaY_MASTER() {
		return sprtFlechaDerechaY_MASTER;
	}


	public static int getSprtFlechaIzquierdaX_MASTER() {
		return sprtFlechaIzquierdaX_MASTER;
	}


	public static int getSprtFlechaIzquierdaY_MASTER() {
		return sprtFlechaIzquierdaY_MASTER;
	}


	public static int getSprtMenuX_MASTER() {
		return sprtMenuX_MASTER;
	}


	public static int getSprtMenuY_MASTER() {
		return sprtMenuY_MASTER;
	}


	public static float getSprtMenuSize_MASTER() {
		return sprtMenuSize_MASTER;
	}
	
	
	public static int getTxtHUD_X_MASTER() {
		return txtHUD_X_MASTER;
	}


	public static int getTxtHUD_Y_MASTER() {
		return txtHUD_Y_MASTER;
	}


	public static int getTxtHUD_Size_MASTER() {
		return txtHUD_Size_MASTER;
	}

	
	
	
}
