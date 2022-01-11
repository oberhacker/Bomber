package xnetcom.bomber.sql;

import android.util.Log;

public class DatosMapa {

	private int id;
	private int numeroMapa=-1;
	private int estrellas=-1;
	private int recordPuntos=0;
	private int boostersIniciales=0;
	

	// mondas  de este tipo que se han recogido
	private int m_bomba=-1;
	private int m_corazon=-1;
	private int m_correr=-1;
	private int m_detonador=-1;
	private int m_fantasma=-1;
	private int m_fuerza=-1;
	private int m_potenciador=-1;
	private int m_sorpresa=-1;
	
	private DatabaseHandler datos;
	
	
	public DatosMapa(DatabaseHandler dbh){
		datos=dbh;
	}
	
	public void actualiza(){
		
		if (datos!=null){			
			datos.actualizaMapa(this);
			/*datos.getContext().tostar("actualizo m_bomba"+m_bomba+
					" co "+m_corazon+
					" or "+m_correr+
					" de "+m_detonador+
					" fa "+m_fantasma+
					" fu "+m_fuerza+
					" po "+m_potenciador+
					" so "+m_sorpresa);*/
		}else{
			Log.i("info", "no se guardara en base de datos DatabaseHandler null");
		}
	}
	
	public int getBoostersIniciales() {
		return boostersIniciales;
	}
	
	public  int getBoosterTotales(){
		return (m_bomba+m_corazon+m_correr+m_detonador+m_fantasma+m_fuerza+m_potenciador+m_sorpresa);
	}

	public void setBoostersIniciales(int boostersIniciales) {
		this.boostersIniciales = boostersIniciales;
	}
	public int getNumeroMapa() {
		return numeroMapa;
	}
	public int getEstrellas() {
		return estrellas;
	}
	public int getRecordPuntos() {
		return recordPuntos;
	}
	public int getM_bomba() {
		return m_bomba;
	}
	public int getM_corazon() {
		return m_corazon;
	}
	public int getM_correr() {
		return m_correr;
	}
	public int getM_detonador() {
		return m_detonador;
	}
	public int getM_fantasma() {
		return m_fantasma;
	}
	public int getM_fuerza() {
		return m_fuerza;
	}
	public int getM_potenciador() {
		return m_potenciador;
	}
	
	
	
	public void setNumeroMapa(int numeroMapa) {
		this.numeroMapa = numeroMapa;
		//actualiza();
	}
	public void setEstrellas(int estrellas) {
		this.estrellas = estrellas;
		//actualiza();
	}
	public void setRecordPuntos(int recordPuntos) {
		this.recordPuntos = recordPuntos;
		//actualiza();
	}
	public void setM_bomba(int m_bomba) {
		this.m_bomba = m_bomba;
		//actualiza();
	}
	public void setM_corazon(int m_corazon) {
		this.m_corazon = m_corazon;
		//actualiza();
	}
	public void setM_correr(int m_correr) {
		this.m_correr = m_correr;
		//actualiza();
	}
	public void setM_detonador(int m_detonador) {
		this.m_detonador = m_detonador;
		//actualiza();
	}
	public void setM_fantasma(int m_fantasma) {
		this.m_fantasma = m_fantasma;
		//actualiza();
	}
	public void setM_fuerza(int m_fuerza) {
		this.m_fuerza = m_fuerza;
		//actualiza();
	}
	public void setM_potenciador(int m_potenciador) {
		this.m_potenciador = m_potenciador;
		//actualiza();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public int getM_sorpresa() {
		return m_sorpresa;
	}

	public void setM_sorpresa(int m_sorpresa) {
		this.m_sorpresa = m_sorpresa;
	}

	
	
}
