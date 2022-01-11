package xnetcom.bomber.entidades;

import java.util.ArrayList;
import java.util.Iterator;

import xnetcom.bomber.BomberGame;

public class AlmacenBombas {
	
	private BomberGame context;
	private ArrayList<Bomba> almacen;
	private Iterator<Bomba> itr; 

	private int maximoBombas;
	

	//private boolean controlRemoto=false;
	private Contador contador;	 
	//private int tamanoExplosion=1;
	private int secuencia=0;
	
	//public int getTamanoExplosion() {
	//	return tamanoExplosion;
	//}

	//public void setTamanoExplosion(int tamanoExplosion) {
	//	this.tamanoExplosion = tamanoExplosion;
	//}


	public Contador getContador() {
		return contador;
	}



	
	public AlmacenBombas(BomberGame context, int numeroBombas){		
		contador =new Contador();
		this.context=context;
		almacen= new ArrayList<Bomba>();
		maximoBombas=numeroBombas;
		
		Bomba b0 = new Bomba(context);
		
		b0.cargaTexturas();
		b0.creaBatch();
		b0.setAlmacen(this);
		
		Bomba bx;
		almacen.add(b0);
		b0.setNumeroBomba(0);
		for (int i = 0; i < numeroBombas; i++) {
			bx= new Bomba(context);
			bx.clonarTexturas(b0);
			bx.creaBatch();
			almacen.add(bx);
			bx.setAlmacen(this);
			bx.setNumeroBomba(i+1);
			//System.out.println("CREANDO BOMBA");
		}
		itr = almacen.iterator();


	}
	
	public boolean isControlRemoto() {
		return context.getGameManager().getEstado().isControlRemoto();
	}
	
	//public void setControlRemoto(boolean controlRemoto) {
		//this.controlRemoto = controlRemoto;
	//}
	
	
	public void setMaxBombas(int numero){
		maximoBombas=numero;
	}

	public ArrayList<Bomba> getAlmacen() {
		return almacen;
	}

	public void setAlmacen(ArrayList<Bomba> almacen) {
		this.almacen = almacen;
	}
	
	public int getMaximoBombas() {
		return maximoBombas;
	}

	public void setMaximoBombas(int maximoBombas) {
		this.maximoBombas = maximoBombas;
	}
	
	
	public boolean plantaBomba() {
		synchronized (this) {
			
		
			if (context.getBomberman().isMuerto())	return false;
			if (contador.getBombasPuestas() >= maximoBombas)return false;
			if (itr.hasNext()) {
				Bomba bm = itr.next();
				if (bm.isDetonada()) {
					bm.plantarBomba(context.getGameManager().getEstado().getTamExplosion(), secuencia);	
					context.vibrar(50);
					secuencia++;
					return true;
				}

			} else {
				itr = almacen.iterator();
				if (itr.hasNext()) {
					Bomba bm = itr.next();
					if (bm.isDetonada()) {
						bm.plantarBomba(context.getGameManager().getEstado().getTamExplosion(), secuencia);		
						context.vibrar(50);
						secuencia++;
						return true;
					}

				}

			}
			
			return false;
			
		}
	}

	public void ControlRemotoDetonar() {
		synchronized (this) {
			if (!context.getGameManager().getEstado().isControlRemoto())
				return;
			if (context.getBomberman().isMuerto())
				return;
			System.out.println("ControlRemotoDetonar");
			Iterator<Bomba> itr2 = almacen.iterator();
			Bomba tmp;
			Bomba bm = null;
			int primera = 1000;
			while (itr2.hasNext()) {
				tmp = itr2.next();
				if (!tmp.isDetonada()) {
					if (tmp.getPos() < primera) {
						primera = tmp.getPos();
						bm = tmp;
					}
				}
			}

			try {
				bm.detonar(true);
			} catch (Exception e) {
				System.out.println("errorr al bombardear" +e.getCause());
			}
		}
	}

	public synchronized Bomba getBombaXY(int columna, int fila){
		Iterator<Bomba> itr2= almacen.iterator();
		Bomba tmp;
		while(itr2.hasNext()){
			tmp=itr2.next();
			if (!tmp.isDetonada()&&tmp.getColumna()==columna && tmp.getFila()==fila) return tmp;
		}
		return null;
	}
	
	// para evitar que se pongan dos bombas en el mismo cuadro
	public synchronized boolean hayBomba (int columna, int fila){
		Iterator<Bomba> itr2= almacen.iterator();
		Bomba tmp;
		while(itr2.hasNext()){
			tmp=itr2.next();
			if (!tmp.isDetonada()&&tmp.getColumna()==columna && tmp.getFila()==fila) return true;
		}
		return false;
	}
	

	
	
	public class Contador{
		Integer bombasPuestas=0;
		
		public int getBombasPuestas() {
			synchronized(bombasPuestas) {
				return bombasPuestas;
			}
		}
		public void BombaPuesta() {
			synchronized(bombasPuestas) {
				bombasPuestas++;
			}
			 
		}
		public  void BombaExplotada() {
			synchronized(bombasPuestas) {
				bombasPuestas--;
			}
		}
		
	}
}
