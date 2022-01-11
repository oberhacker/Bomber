package xnetcom.bomber.entidades;

public class EnemigoPosicion {

	String nombre;
	public String getNombre() {
		return nombre;
	}

	public int getColumna() {
		return columna;
	}

	public int getFila() {
		return fila;
	}

	int columna, fila;
	
	public EnemigoPosicion(String nombre, int columna,int fila){
		this.nombre=nombre;
		this.fila=fila;
		this.columna=columna;		
	}
}
