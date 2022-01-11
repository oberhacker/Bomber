package xnetcom.bomber.util;

public class Sincro {
	/**
	 * @uml.property  name="sinc"
	 */
	private boolean sinc = false;
	/**
	 * @uml.property  name="encolado"
	 */
	private boolean encolado=false;
	/**
	 * @uml.property  name="mensaje"
	 */
	private boolean Mensaje=false;
	/**
	 * @uml.property  name="dados"
	 */
	private boolean dados=true;
	/**
	 * @uml.property  name="partida"
	 */
	private boolean partida=false;
	/**
	 * @uml.property  name="finRonda"
	 */
	private boolean finRonda=false;
	/**
	 * @uml.property  name="numMensajes"
	 */
	private int numMensajes=0;
	
	public synchronized void EsperaIniciado() {
		try {
			while (!sinc) {
				this.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void setIniciado() {
		sinc=true;
		this.notify();
	}
	
	public synchronized void Encolado() {
		encolado=true;
		this.notify();
	}
	
	public synchronized void EsperaEncolado() {
		try {
			while (!encolado) {
				this.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized void EsperaMensaje() {
		try {
			while (numMensajes==0) {
				this.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numMensajes--;
	}
	public synchronized void LlegadoMensaje() {
		numMensajes++;
		this.notify();
	}
	
	
	public synchronized void EsperaPartida() {
		try {
			while (!partida) {
				this.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public synchronized void IniciadoPartida() {
		partida=true;
		this.notify();
	}
	
	
	public synchronized void Espera_Fin_Ronda() {
		try {
			while (!finRonda) {
				this.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finRonda=false;
	}
	
	public synchronized void Llegado_Fin_Ronda() {
		finRonda=true;
		this.notify();
	}
	public synchronized void EsperaDados() {
		try {
			while (!dados) {
				this.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dados=false;
	}
	public synchronized void LlegadoDados() {
		dados=true;
		this.notify();
	}
	
	public  synchronized void BoqueoMutex(){
		
	}
	

}