package xnetcom.bomber.util;

import java.util.HashSet;
import java.util.Random;

public class Aleatorio {	
	
	private static Random generator;
	private static HashSet<Integer> numeros;
	
	static {
		generator= new Random();
		numeros = new HashSet<Integer>();
	}
	
	public static synchronized int DameAleatorio(int aStart, int aEnd) {
		if (aStart==aEnd)return aStart;
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * generator.nextDouble());
		int aleatorio = (int) (fraction + aStart);
		
		return aleatorio;
	}
	
	public static synchronized int DameAleatorioUnico(int aStart, int aEnd) throws BomberException {
		int cuenta=0;
		while (true){
			cuenta++;
			long range = (long) aEnd - (long) aStart + 1;
			long fraction = (long) (range * generator.nextDouble());
			int aleatorio = (int) (fraction + aStart);
			if (!(aleatorio<aStart)){
				if (numeros.add(aleatorio)){
					return aleatorio;
				}					
			}	
			if (cuenta<1000)throw new BomberException("DameAleatorioUnico");
		}		
	}
	
	public static boolean TiraDadoPorcentaje(float porcentaje) {		
		
		long range = (long) 100 - (long) 0 + 1;
		long fraction = (long) (range * generator.nextDouble());
		int aleatorio = (int) (fraction + 0);
		if (aleatorio<=porcentaje) return true;
		else return false;
	}
	
	public static boolean TiraDadoposibilidades(int opciones, int oportunidades) {	
		float porcentaje = (float)oportunidades/(float)opciones;
		System.out.println("porcentaje"+porcentaje);
		porcentaje=porcentaje*100;
		System.out.println("porcentaje"+porcentaje);		
		long range = (long) 100 - (long) 0 + 1;
		long fraction = (long) (range * generator.nextDouble());
		int aleatorio = (int) (fraction + 0);
		System.out.println("opciones="+opciones+"  oportunidades="+oportunidades+" porcentaje="+porcentaje+" aleatorio="+aleatorio+" porcentaje="+porcentaje+" aleatorio<=porcentaje"+(aleatorio<=porcentaje));
		if (aleatorio<=porcentaje) return true;
		else return false;
	}
	
}
