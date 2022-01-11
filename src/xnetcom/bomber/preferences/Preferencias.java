package xnetcom.bomber.preferences;

import xnetcom.bomber.BomberGame;
import xnetcom.bomber.util.ConstantesResolucion;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias{
	
	private static BomberGame context;	

	public static void inicia(BomberGame ctx){
		context=ctx;
	}
	
	public static void  guardarPrefenrencias(String clave, String valor){		
		SharedPreferences settings = context.getSharedPreferences("1234", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(clave, valor);	
		editor.commit();
	}
	
	public static void  guardarPrefenrenciasResolucion(String clave, float valor){		
		SharedPreferences settings = context.getSharedPreferences("1234", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putFloat(clave+ConstantesResolucion.getResolucion(), valor);		
		editor.commit();
	}
	
	public static void  guardarPrefenrencias(String clave, int valor){		
		SharedPreferences settings = context.getSharedPreferences("1234", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(clave, valor);		
		editor.commit();
	}
	
	
	public static String leerPreferencias(String clave){
		SharedPreferences settings = context.getSharedPreferences("1234", Context.MODE_PRIVATE);
		String salida =settings.getString(clave, "");
		if (salida.length()==0)return null;
		else return salida;
		 
	}
	public static float leerPreferenciasFloatResolucion(String clave){
		SharedPreferences settings = context.getSharedPreferences("1234", Context.MODE_PRIVATE);
		return settings.getFloat(clave+ConstantesResolucion.getResolucion(), -1);
	}

	
	
	
	public static int leerPreferenciasInt(String clave){
		SharedPreferences settings = context.getSharedPreferences("1234", Context.MODE_PRIVATE);
		return settings.getInt(clave, -1);
	}
	
	
	
	public static int leerPreferenciasIntResolucion(String clave){
		SharedPreferences settings = context.getSharedPreferences("1234", Context.MODE_PRIVATE);
		return settings.getInt(clave+ConstantesResolucion.getResolucion(), -1);
	}
	
	

	
}
