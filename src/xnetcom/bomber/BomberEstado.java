package xnetcom.bomber;

import xnetcom.bomber.preferences.Preferencias;
import xnetcom.bomber.sql.DatosMapa;
import xnetcom.bomber.util.ParserXML;

public class BomberEstado {


	private int nivel;
	
	private  ParserXML parser;

	
	public void setNivel(int nivel){
		this.nivel=nivel;
	}
	
	public int getNivel(){
		return nivel;
	}
	/*
	public boolean isDetonadorDisponibleEnNivel(int nivel){
		if (nivel>=DETONADOR)return true;
		else return false;
	}
	
	*/
	private Boolean controlRemoto;// detonador remoto
	
	public boolean isControlRemoto() {
		return controlRemoto;
	}

	private Integer tamExplosion;		// tamaño de la esplosion
	private Integer numBombas;
	private Integer vidas;
	private boolean MVELOCIDAD=false;	//velocidad 
	private boolean MFANTASMA=false;	// atraviesa bombas y paredes
	private boolean MFUERZA=false;		// inmune al fuego
	public static int TIEMPOBOOSTER=10000;
	public int MAXIMOBOMBAS=9;
	public int MAXIMOEXPLOSION=4;
	public Integer enemigosRestantes=0;
	public Object ctNivel;
	public int monedasEcontradas=0;
	private BomberGame context;
	private boolean boosterActivado= false;
	
	
	public boolean isBoosterActivado() {
		return boosterActivado;
	}
	public void setBoosterActivado(boolean boosterActivado) {
		this.boosterActivado = boosterActivado;
	}
	public void monedaEncontrada(){
		
	}
	public void reseteaEnemigosRestantes(){
		synchronized (enemigosRestantes) {
			enemigosRestantes=0;
		}
	}
	
	public int getEnemigosRestantes() {
		synchronized (enemigosRestantes) {
			return enemigosRestantes;
		}		
	}

	public void setEnemigosRestantes(int enemigosRestantes) {
		synchronized (this.enemigosRestantes) {
			this.enemigosRestantes = enemigosRestantes;
		}
		
	}

	public void incrementaEnemigo(){
		synchronized (this.enemigosRestantes) {
			this.enemigosRestantes++;
		}
	}
	
	
	public boolean mataEnemigo(){
		synchronized (this.enemigosRestantes) {
			this.enemigosRestantes--;
			if (enemigosRestantes==0){
				return true;
			}else{
				return false;
			}
		}
	}
	
	
	public void cargaPreferencias(){
		this.numBombas =Preferencias.leerPreferenciasInt("numeroBombas");
		String controlRemoto =Preferencias.leerPreferencias("controlRemoto");
		
		tamExplosion =Preferencias.leerPreferenciasInt("tamExplosion");
		vidas =Preferencias.leerPreferenciasInt("vidas");
		
		if(vidas==-1)vidas=BomberGame.INICIO_VIDAS;
		if(tamExplosion==-1)tamExplosion=BomberGame.INICIO_TAM_EXPLOSION;
		if(controlRemoto==null)controlRemoto=BomberGame.INICIO_CONTROL_REMOTO;
		setControlRemoto(controlRemoto);
		if(numBombas==-1)numBombas=BomberGame.INICIO_NUM_BOMBAS;
	}
	
	public void guardarPreferencias(){
		Preferencias.guardarPrefenrencias("numeroBombas", numBombas);
		Preferencias.guardarPrefenrencias("controlRemoto", controlRemoto.toString());
		Preferencias.guardarPrefenrencias("tamExplosion", tamExplosion);
		Preferencias.guardarPrefenrencias("vidas", vidas);		
	}
	
	DatosMapa datosmapaMaximos; 
	
	public void inicializaMapaMaximos(){
		parser= context.getGameManager().getParser();
		if (datosmapaMaximos==null || datosmapaMaximos.getNumeroMapa()!=nivel){
			datosmapaMaximos= parser.getMaximoMonedasEnNivel(nivel);
		}
	}
	public int numeroMaximoDeBombasNivel(){
		inicializaMapaMaximos();
		return datosmapaMaximos.getM_bomba()+1;
	}
	
	public int numeroMaximoExplosionNivel(){
		inicializaMapaMaximos();
		return datosmapaMaximos.getM_potenciador()+1;
		
	}
	
	public boolean isDetonadorDisponibleNivel(){
		inicializaMapaMaximos();
		if(datosmapaMaximos.getM_detonador()!=0){
			return true;
		}else{
			return false;
		}
	}
	
	public BomberEstado(BomberGame context){	
		this.context=context;		
		cargaPreferencias();		
	}
	
	public boolean isMVELOCIDAD() {
		return MVELOCIDAD;
	}
	public void setMVELOCIDAD(boolean mVELOCIDAD) {
		MVELOCIDAD = mVELOCIDAD;
	}
	public boolean isMFANTASMA() {
		return MFANTASMA;
	}
	public void setMFANTASMA(boolean mFANTASMA) {
		MFANTASMA = mFANTASMA;
	}
	public boolean isMFUERZA() {
		return MFUERZA;
	}
	public void setMFUERZA(boolean mFUERZA) {
		MFUERZA = mFUERZA;
	}
	
	public void setControlRemoto(boolean ctrl){
		controlRemoto=ctrl;
		guardarPreferencias();
	}
	
	/**
	 * solo usado por el conmstructor
	 * @param ctrl
	 */
	public void setControlRemoto(String ctrl){
		if (ctrl.equals("true")){
			controlRemoto=true;
		}else{
			controlRemoto=false;
		}		
	}
	
	public void addVida(){
		vidas++;
		guardarPreferencias();
	}
	
	public Integer getVidas(){
		return vidas;
	}
	/**
	 * si ya se han gastado todas las vidas 
	 * @return
	 */
	public boolean restaVida(){
		setMFANTASMA(false);
		setMFUERZA(false);
		setMVELOCIDAD(false);			
		boolean muerto=false;
		if(vidas!=0){
			vidas--;
			guardarPreferencias();
		}else{
			muerto=true;
		}		
		return muerto;
	}
	

	
	public Integer getNumeroBombas() {
		return numBombas;
	}
	
	
	public void setNumeroBombas(int numBombas){
		this.numBombas=numBombas;
	}
	
	public void setTamanoExplosion(int tamExplosion){
		this.tamExplosion=tamExplosion;
	}
	
	
	public void incrementaNumeroBombas(){
		if (numBombas<MAXIMOBOMBAS){
			numBombas++;
			guardarPreferencias();
		}		
	}
	
	public void incrementaTamanoExplosion(){
		if(tamExplosion<MAXIMOEXPLOSION){
			tamExplosion++;
			guardarPreferencias();
		}
	}
	
	public Integer getTamExplosion(){
		return tamExplosion;
	}
	
}
