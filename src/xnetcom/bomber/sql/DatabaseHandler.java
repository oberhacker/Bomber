package xnetcom.bomber.sql;

import xnetcom.bomber.BomberGame;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Contacts table name
	private static final String TABLA_MAPAS = "DatosBombers";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "nivel";
	private static final String M_BOMBA = "m_bomba";
	private static final String M_CORAZON = "m_corazon";
	private static final String M_CORRER = "m_correr";
	private static final String M_DETONADOR = "m_detonador";
	private static final String M_FANTASMA = "m_fantasma";
	private static final String M_FUERZA = "m_fuerza";
	private static final String M_POTENCIADOR = "m_potenciador";
	private static final String M_SORPRESA = "m_sorpresa";
	
	private static final String RECORD = "record";
	private static final String ESTRELLAS = "estrellas";
	private static final String  BOOSTER_INICIALES ="BOOSTER_INICIALES";
	
	private BomberGame context;
	public BomberGame getContext() {
		return context;
	}

	public DatabaseHandler(Context context) {		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.context=(BomberGame) context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLA_MAPAS + "("
					+ KEY_ID + " INTEGER PRIMARY KEY, " 
					+ KEY_NAME + " INTEGER, "	
					+ RECORD+ " INTEGER, "
					+ ESTRELLAS +" INTEGER, "
					+ M_BOMBA + " INTEGER, "
					+ M_CORAZON + " INTEGER, "
					+ M_CORRER + " INTEGER, "
					+ M_DETONADOR + " INTEGER, "
					+ M_FANTASMA + " INTEGER, "
					+ M_FUERZA + " INTEGER, "
					+ M_POTENCIADOR + " INTEGER, "
					+ M_SORPRESA + " INTEGER, "
					+ BOOSTER_INICIALES + " INTEGER "
					+ ")";
		System.out.println("CREATE_CONTACTS_TABLE");
		System.out.println(CREATE_CONTACTS_TABLE);
		db.execSQL(CREATE_CONTACTS_TABLE);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLA_MAPAS);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	
	
	
	public void addMapa(DatosMapa datos) {
		Log.d("sql", "addMapa getNumeroMapa ="+datos.getNumeroMapa());
		
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, datos.getNumeroMapa()); 
		values.put(RECORD, datos.getRecordPuntos()); 
		values.put(ESTRELLAS, datos.getEstrellas());
		values.put(M_BOMBA, datos.getM_bomba()); 
		values.put(M_CORAZON, datos.getM_corazon()); 
		values.put(M_CORRER, datos.getM_correr()); 
		values.put(M_DETONADOR, datos.getM_detonador()); 
		values.put(M_FANTASMA, datos.getM_fantasma()); 
		values.put(M_FUERZA, datos.getM_fuerza()); 
		values.put(M_POTENCIADOR, datos.getM_potenciador()); 
		values.put(M_SORPRESA, datos.getM_sorpresa()); 
		values.put(BOOSTER_INICIALES, datos.getBoostersIniciales());
	
		
		db.insert(TABLA_MAPAS, null, values);
		db.close(); // Closing database connection		
		
		
	}
	
	public void desbloqueaMapa(int numMapa) {
		if (numMapa>=46)return;
		try {
			DatosMapa datos = getMapa(numMapa);
			if (datos.getEstrellas() == -1) {
				datos.setEstrellas(0);
				actualizaMapa(datos);
			}
		} catch (Exception e) {
		}
	}
	
	
	public void actualizaMapa(DatosMapa datos){
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NAME, datos.getNumeroMapa()); 
		values.put(RECORD, datos.getRecordPuntos()); 
		values.put(ESTRELLAS, datos.getEstrellas());
		values.put(M_BOMBA, datos.getM_bomba()); 
		values.put(M_CORAZON, datos.getM_corazon()); 
		values.put(M_CORRER, datos.getM_correr()); 
		values.put(M_DETONADOR, datos.getM_detonador()); 
		values.put(M_FANTASMA, datos.getM_fantasma()); 
		values.put(M_FUERZA, datos.getM_fuerza()); 
		values.put(M_POTENCIADOR, datos.getM_potenciador()); 
		values.put(M_SORPRESA, datos.getM_sorpresa()); 
		values.put(BOOSTER_INICIALES, datos.getBoostersIniciales());
		// updating row
		db.update(TABLA_MAPAS, values, KEY_ID + " = ?",	new String[] { String.valueOf(datos.getId()) });
		db.close(); // Closing database connection	
	}
	
	
	public DatosMapa getMapa(int numeroMapa) {
		Log.d("sql", "DatosMapa getMapa(int numeroMapa)"+numeroMapa);
	    SQLiteDatabase db = this.getReadableDatabase();
	 
	    String[] Recuperar =new String[] { KEY_ID,  KEY_NAME,RECORD,ESTRELLAS, M_BOMBA,M_CORAZON,M_CORRER,M_DETONADOR,M_FANTASMA, M_FUERZA,M_POTENCIADOR,M_SORPRESA,BOOSTER_INICIALES};
	    
	    Cursor cursor = db.query(TABLA_MAPAS,Recuperar , KEY_ID + "=?", new String[] { String.valueOf(numeroMapa+1) }, null, null, null, null);
	    System.out.println("cursor"+cursor.toString());
	    DatosMapa mapa= new DatosMapa(this);
	    if (cursor != null && cursor.moveToFirst()){	    	
	    	mapa.setId(cursor.getInt(0));
			mapa.setNumeroMapa(cursor.getInt(1));
			mapa.setRecordPuntos(cursor.getInt(2));
			mapa.setEstrellas(cursor.getInt(3));
			mapa.setM_bomba(cursor.getInt(4));
			mapa.setM_corazon(cursor.getInt(5));
			mapa.setM_correr(cursor.getInt(6));
			mapa.setM_detonador(cursor.getInt(7));
			mapa.setM_fantasma(cursor.getInt(8));
			mapa.setM_fuerza(cursor.getInt(9));
			mapa.setM_potenciador(cursor.getInt(10));
			mapa.setM_sorpresa(cursor.getInt(11));
			mapa.setBoostersIniciales(cursor.getInt(12));
			}else{
				//Debug.e("no deberia entrar aki deberian estar todos los mapas inicializados ");
			}	  
	    db.close(); // Closing database connection	
	    Log.d("sql", "DatosMapa retorno getNumeroMapa "+mapa.getNumeroMapa());
	    return mapa;
	}
}
