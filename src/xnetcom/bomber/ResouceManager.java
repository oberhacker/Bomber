package xnetcom.bomber;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
//import org.andengine.opengl.texture.atlas.buildable.builder.ITextureBuilder.TextureAtlasSourcePackingException;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.debug.Debug;

import xnetcom.bomber.andengine.TransparentBitmapTextureAtlasSource;
import xnetcom.bomber.inicio.Inicio;
import xnetcom.bomber.preferences.Preferencias;
import xnetcom.bomber.util.Aleatorio;
import xnetcom.bomber.util.ConstantesResolucion;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.util.Log;


public class ResouceManager {
	private BomberGame context;

	
	//private BuildableBitmapTextureAtlas enemigosybomberBBTA;
	private TiledTextureRegion globoTR;
	private TiledTextureRegion iconosHUDTR;
	
	private TiledTextureRegion ticTR;
	
	public TiledTextureRegion getTicTR() {
		return ticTR;
	}
	public TiledTextureRegion getIconosHUDTR() {
		return iconosHUDTR;
	}

	
	private TextureRegion pauseTR;
	private TextureRegion playTR;
	private TextureRegion loadingTR;
	
	
	public TextureRegion getLoadingTR() {
		return loadingTR;
	}
	public TextureRegion getPlayTR() {
		return playTR;
	}

	private TextureRegion menuTR;
	
	public TextureRegion getMenuTR() {
		return menuTR;
	}
	public TextureRegion getPauseTR() {
		return pauseTR;
	}

	//private Sound pasos;
	private Sound pasosB;
	private Sound campanaFinal;

	public Sound getCampanaFinal() {
		return campanaFinal;
	}
	public Sound getPasosB() {
		return pasosB;
	}

	private Sound bomset;
	private boolean sonido=true;
	
	
	private Sound estrellas;
	private Sound explosion;
	private Sound monedasSound;
	
	private Music musica1;
	private Music booster;
	private Music musica2;
	private Music musica3;
	private Music musica4;
	private Music musica5;
	private Music musica6;
	private Music musica7;
	private Music musica8;
	
	private Sound click;
	
	public Music getBooster() {
		return booster;
	}

	public void playEstrellas(){
		this.estrellas.play();
	}
	private BitmapTextureAtlas sombrasBTA;
	public BitmapTextureAtlas getSombrasBTA() {
		return sombrasBTA;
	}

	private TiledTextureRegion sombrasTR;
	public TiledTextureRegion getSombrasTR() {
		return sombrasTR;
	}

	private TiledTextureRegion auraTR;
	
	public TiledTextureRegion getAuraTR() {
		return auraTR;
	}


	private TiledTextureRegion fantasmaTR;
	private TiledTextureRegion mocoTR;
	private TiledTextureRegion mocoRojoTR;
	public TiledTextureRegion getMocoRojoTR() {
		return mocoRojoTR;
	}

	private TiledTextureRegion monedaMarronTR;
	public TiledTextureRegion getMonedaMarronTR() {
		return monedaMarronTR;
	}


	private TiledTextureRegion monedaTR;
	private TiledTextureRegion gotaTR;
	private TiledTextureRegion gotaNaranjaTR;
	private TiledTextureRegion gotaRojaTR;
	public TiledTextureRegion getGotaRojaTR() {
		return gotaRojaTR;
	}


	private TiledTextureRegion mBombermanTextureRegionAniA;
	private TiledTextureRegion mBombermanTextureRegionAniB;
	private TiledTextureRegion bombaTR;
	private TiledTextureRegion mFuegoCentroTR;
	private TiledTextureRegion mFuegoCentroAbajoTR;
	private TiledTextureRegion mFuegoCentroArribaTR;
	private TiledTextureRegion mFuegoCentroDerechaTR;
	private TiledTextureRegion mFuegoCentroIzquierdaTR;
	private TiledTextureRegion mFuegoFinAbajoTR;
	private TiledTextureRegion mFuegoFinArribaTR;
	private TiledTextureRegion mFuegoFinDerechaTR;
	private TiledTextureRegion mFuegoFinIzquierdaTR;
	private TiledTextureRegion mFuegoHorizontalTR;
	private TiledTextureRegion mFuegoVerticalTR;
	
	
	
	
	private TextureRegion info_1TR;
	public TextureRegion getInfo_1TR() {
		return info_1TR;
	}
	public TextureRegion getInfo_2TR() {
		return info_2TR;
	}
	public TextureRegion getInfo_3TR() {
		return info_3TR;
	}
	public TextureRegion getInfo_4TR() {
		return info_4TR;
	}



	private TextureRegion info_2TR;
	private TextureRegion info_3TR;
	private TextureRegion info_4TR;
	
	
	
	
	private TextureRegion campaignTR;
	public TextureRegion getCampaignTR() {
		return campaignTR;
	}


	private TextureRegion trainingTR;
	
	public TextureRegion getTrainingTR() {
		return trainingTR;
	}


	private TextureRegion restoreTR;
	private TextureRegion btn_1_TR;
	private TextureRegion btn_2_TR;
	private TextureRegion crucetaTR;
	private TextureRegion masTR;
	private TextureRegion menosTR;
	
	private TextureRegion btnAceptarTR;
	private TextureRegion btn_CancelarTR;
	private TextureRegion btnFondoTR;
	
	public TextureRegion getBtnAceptarTR() {
		return btnAceptarTR;
	}
	public TextureRegion getBtn_CancelarTR() {
		return btn_CancelarTR;
	}
	public TextureRegion getBtnFondoTR() {
		return btnFondoTR;
	}

	
	
	private TiledTextureRegion sigTR;
	private TextureRegion tomenuTR;
	private TiledTextureRegion retryTR;
	private TextureRegion fichaTR;
	private TextureRegion failedTR;
	private TextureRegion clearedTR;
	private TiledTextureRegion estrellasTR;
	
	public TiledTextureRegion getEstrellasTR() {
		return estrellasTR;
	}
	public TextureRegion getFailedTR() {
		return failedTR;
	}
	public TextureRegion getClearedTR() {
		return clearedTR;
	}
	
	public TextureRegion getFichaTR() {
		return fichaTR;
	}
	public TiledTextureRegion getSigTR() {
		return sigTR;
	}
	public TextureRegion getTomenuTR() {
		return tomenuTR;
	}
	public TiledTextureRegion getRetryTR() {
		return retryTR;
	}

	// inicio
	
	
	
	
	TextureRegion hudTR;

	
	public TextureRegion getHudTR() {
		return hudTR;
	}

//	private BitmapTextureAtlas mFontTexturBombae;
	private BuildableBitmapTextureAtlas inicio_BTA;
	private TextureRegion fondo_mapas_TR;	
	private TextureRegion bomba_inicio_TR;
	private TextureRegion fondo_inicio_TR;
	private TextureRegion opciones_TR;
	private TiledTextureRegion icono_bombas_TR;	
	
	
	private float volumenSonidos;

	public float getVolumenSonidos() {
		return volumenSonidos;
	}

	private Font mFont;
	private Font mFontDigital;
	private Font mFontEras;
	private Font mFontBomber;
	
	public Font getmFontBomber() {
		return mFontBomber;
	}


	private TextureRegion iconoVolver_TR;
	private TextureRegion iconoflecha_TR;
	private TextureRegion puerta_TR;
	private TextureRegion fondoMenuTR;
	private Sound mecha;


	private TiledTextureRegion globoAzulTR;	

	public TiledTextureRegion getGloboAzulTR() {
		return globoAzulTR;
	}
	public Sound getMecha() {
		return mecha;
	}
	public TextureRegion getFondoMenuTR() {
		return fondoMenuTR;
	}
	public Font getmFontDigital() {
		return mFontDigital;
	}
	public Font getmFont() {
		return mFont;
	}
	public Font getmFontEras(){
		return mFontEras;
	}
	
	public TextureRegion getFondo_inicio_TR() {
		return fondo_inicio_TR;
	}
	public TextureRegion getFondo_mapas_TR() {
		return fondo_mapas_TR;
	}
	public TextureRegion getBomba_inicio_TR() {
		return bomba_inicio_TR;
	}
	public TiledTextureRegion getIcono_bombas_TR() {
		return icono_bombas_TR;
	}
	
	
	public TiledTextureRegion getBombaTR() {
		return bombaTR;
	}



	public TiledTextureRegion getGloboTR() {
		return globoTR;
	}

	public TiledTextureRegion getFantasmaTR() {
		return fantasmaTR;
	}

	public TiledTextureRegion getMocoTR() {
		return mocoTR;
	}

	public TiledTextureRegion getMonedaTR() {
		return monedaTR;
	}

	public TiledTextureRegion getGotaTR() {
		return gotaTR;
	}
	public TiledTextureRegion getGotaNaranjaTR() {
		return gotaNaranjaTR;
	}

	public TiledTextureRegion getmBombermanTextureRegionAniA() {
		return mBombermanTextureRegionAniA;
	}
	public TiledTextureRegion getmBombermanTextureRegionAniB() {
		return mBombermanTextureRegionAniB;
	}	
	
	public ResouceManager(BomberGame context) {
		this.context=context;	
		setSonido(true);
	}
	

	
	public void cargarTexturas(){
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");	


		BitmapTextureAtlas info_1BTA = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.NEAREST);
		info_1TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(info_1BTA, context, "info_1.jpg", 0, 0);
		
		BitmapTextureAtlas info_2BTA = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.NEAREST);
		info_2TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(info_2BTA, context, "info_2.jpg", 0, 0);
		
		BitmapTextureAtlas info_3BTA = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.NEAREST);
		info_3TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(info_3BTA, context, "info_3.jpg", 0, 0);
		
		BitmapTextureAtlas info_4BTA = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.NEAREST);
		info_4TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(info_4BTA, context, "info_4.jpg", 0, 0);
		
	
		
		
		
		SoundFactory.setAssetBasePath("mfx/");	
		try {
			this.pasosB = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "pasos7.wav");			
		} catch (final IOException e) {
			e.printStackTrace();
		}
		//this.pasosB.setVolume(1);
		pasosB.setLooping(true);
		
		
		
		
		BitmapTextureAtlas fondoBTA = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.NEAREST);
		fondoMenuTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(fondoBTA, context, "menu3.jpg", 0, 0);
		
		BitmapTextureAtlas ticBTA = new BitmapTextureAtlas(context.getTextureManager(),128, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(ticBTA, new TransparentBitmapTextureAtlasSource(128,64), 0, 0);
		
		BitmapTextureAtlas trainingBTA = new BitmapTextureAtlas(context.getTextureManager(),512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(trainingBTA, new TransparentBitmapTextureAtlasSource(512, 256), 0, 0);
		
		BitmapTextureAtlas campaignBTA = new BitmapTextureAtlas(context.getTextureManager(),512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(campaignBTA, new TransparentBitmapTextureAtlasSource(512, 128), 0, 0);
		
		
		
		
		BitmapTextureAtlas auraBTA = new BitmapTextureAtlas(context.getTextureManager(), 512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(auraBTA, new TransparentBitmapTextureAtlasSource(512,128), 0, 0);
		
		
		BitmapTextureAtlas loading = new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(loading, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
		
		BitmapTextureAtlas iconosHUD = new BitmapTextureAtlas(context.getTextureManager(),256, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(iconosHUD, new TransparentBitmapTextureAtlasSource(256,32), 0, 0);
		
		
		BitmapTextureAtlas retry = new BitmapTextureAtlas(context.getTextureManager(),256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(retry, new TransparentBitmapTextureAtlasSource(256,128), 0, 0);


		BitmapTextureAtlas menu = new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(menu, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		
		BitmapTextureAtlas sig = new BitmapTextureAtlas(context.getTextureManager(),256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(sig, new TransparentBitmapTextureAtlasSource(256,128), 0, 0);
		
		BitmapTextureAtlas ficha = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(ficha, new TransparentBitmapTextureAtlasSource(1024,512), 0, 0);
		
		
		
		BitmapTextureAtlas failed = new BitmapTextureAtlas(context.getTextureManager(),512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(failed, new TransparentBitmapTextureAtlasSource(512,64), 0, 0);
				
		
		BitmapTextureAtlas cleared = new BitmapTextureAtlas(context.getTextureManager(),512, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(cleared, new TransparentBitmapTextureAtlasSource(512,64), 0, 0);
		
		BitmapTextureAtlas estrellas = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(estrellas, new TransparentBitmapTextureAtlasSource(1024,512), 0, 0);
	
		
		BitmapTextureAtlas globo_Azul_ani = new BitmapTextureAtlas(context.getTextureManager(),1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(globo_Azul_ani, new TransparentBitmapTextureAtlasSource(1024,256), 0, 0);
		
		
		BitmapTextureAtlas globo_naranja_ani = new BitmapTextureAtlas(context.getTextureManager(),1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(globo_naranja_ani, new TransparentBitmapTextureAtlasSource(1024,256), 0, 0);
		
		BitmapTextureAtlas hudBitmapTextureAtlas = new BitmapTextureAtlas(context.getTextureManager(),2048, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		BitmapTextureAtlasTextureRegionFactory.createFromSource(hudBitmapTextureAtlas, new TransparentBitmapTextureAtlasSource(2048,128), 0, 0);
		
		BitmapTextureAtlas fantasma_tile90 = new BitmapTextureAtlas(context.getTextureManager(),512, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(fantasma_tile90, new TransparentBitmapTextureAtlasSource(512,512), 0, 0);
		
		BitmapTextureAtlas moco_tiled90 = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(moco_tiled90, new TransparentBitmapTextureAtlasSource(1024,512), 0, 0);
		
		BitmapTextureAtlas moco_tiled90R = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(moco_tiled90R, new TransparentBitmapTextureAtlasSource(1024,512), 0, 0);
		
		BitmapTextureAtlas moneda_tiledm = new BitmapTextureAtlas(context.getTextureManager(),512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(moneda_tiledm, new TransparentBitmapTextureAtlasSource(512,256), 0, 0);
		
		BitmapTextureAtlas moneda_tiled = new BitmapTextureAtlas(context.getTextureManager(),512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(moneda_tiled, new TransparentBitmapTextureAtlasSource(512,256), 0, 0);
		
		BitmapTextureAtlas cebolla_final90 = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(cebolla_final90, new TransparentBitmapTextureAtlasSource(1024,512), 0, 0);
		
		
		BitmapTextureAtlas cebolla_final90N = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(cebolla_final90N, new TransparentBitmapTextureAtlasSource(1024,512), 0, 0);
		
		BitmapTextureAtlas cebolla_final90R = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(cebolla_final90R, new TransparentBitmapTextureAtlasSource(1024,512), 0, 0);
		
		BitmapTextureAtlas tiledmaster90A = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(tiledmaster90A, new TransparentBitmapTextureAtlasSource(2048, 1024), 0, 0);
		
		BitmapTextureAtlas tiledmaster90B = new BitmapTextureAtlas(context.getTextureManager(),2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(tiledmaster90B, new TransparentBitmapTextureAtlasSource(2048, 1024), 0, 0);
		
		BitmapTextureAtlas bomba_ani90 = new BitmapTextureAtlas(context.getTextureManager(),512, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(bomba_ani90, new TransparentBitmapTextureAtlasSource(512,256), 0, 0);
		
		BitmapTextureAtlas restore_btn = new BitmapTextureAtlas(context.getTextureManager(),256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(restore_btn, new TransparentBitmapTextureAtlasSource(256,128), 0, 0);
		
		BitmapTextureAtlas btn_1 = new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(btn_1, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
		
		BitmapTextureAtlas btn_2 = new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(btn_2, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
		
		BitmapTextureAtlas control = new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(control, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		
		BitmapTextureAtlas mas = new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(mas, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		
		BitmapTextureAtlas menos = new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		BitmapTextureAtlasTextureRegionFactory.createFromSource(menos, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		
		BitmapTextureAtlas puerta = new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		BitmapTextureAtlasTextureRegionFactory.createFromSource(puerta, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		
		
		BitmapTextureAtlas btnAceptar = new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		BitmapTextureAtlasTextureRegionFactory.createFromSource(btnAceptar, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		
		BitmapTextureAtlas btnCancelar = new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		BitmapTextureAtlasTextureRegionFactory.createFromSource(btnCancelar, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		
		BitmapTextureAtlas btnFondo = new BitmapTextureAtlas(context.getTextureManager(),1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		BitmapTextureAtlasTextureRegionFactory.createFromSource(btnFondo, new TransparentBitmapTextureAtlasSource(1024,512), 0, 0);
		
		
		
		this.puerta_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(puerta, context, "puerta.png",0,0);
		this.loadingTR= BitmapTextureAtlasTextureRegionFactory.createFromAsset(loading, context, "loadingx2.jpg",0,0);
		
		this.globoAzulTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(globo_Azul_ani, context, "globo_azul_ani.png", 0,0,13, 2);
		this.globoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(globo_naranja_ani, context, "globo_naranja_ani.png", 0,0,13, 2);
		this.fantasmaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(fantasma_tile90, context, "fantasma_tile90.png",0,0,  5, 4);
		
		this.mocoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(moco_tiled90, context, "moco_tiled90.png",0,0,6, 4);
		this.mocoRojoTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(moco_tiled90R, context, "moco_tiled90_rojo.png",0,0,6, 4);
		
		this.monedaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(moneda_tiled, context, "moneda_tiled.png",0,0,6, 3);
		this.monedaMarronTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(moneda_tiledm, context, "moneda_tiled_marron.png",0,0,6, 3);
		this.gotaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cebolla_final90, context, "cebolla_final90.png",0,0,10, 4);
		
		this.gotaNaranjaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cebolla_final90N, context, "cebolla_final90_naranja.png",0,0,10, 4);
		this.gotaRojaTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(cebolla_final90R, context, "cebolla_final90_roja.png",0,0,10, 4);
		this.mBombermanTextureRegionAniA=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(tiledmaster90A, context, "tiledmaster(125x104)ArribaB.png",0,0, 12, 5); 
		this.mBombermanTextureRegionAniB=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(tiledmaster90B, context, "tiledmaster(125x104)abajoS6.png",0,0, 12, 5); 
		
		this.bombaTR=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(bomba_ani90, context, "bomba_ani90.png",0,0,4, 2);
		
		this.auraTR=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(auraBTA, context, "AURA128.png",0,0,3, 1);
		
	
		this.restoreTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(restore_btn, context, "restore_btn.png",0,0);
		
		this.hudTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(hudBitmapTextureAtlas, context, "Hud_Marcador.png",0,0);
		
		this.iconosHUDTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(iconosHUD, context, "iconosHUD.png",0,0,6,1);
		
		this.btn_1_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(btn_1, context, "btn_1.png",0,0);
		this.btn_2_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(btn_2, context, "btn_2.png",0,0);
		this.crucetaTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(control, context, "control.png",0,0);
		this.masTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mas, context, "mas07.png",0,0);
		this.menosTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menos, context, "menos07.png",0,0);		
		
		this.campaignTR  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(campaignBTA, context, "campaign.png",0,0);	
		this.trainingTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(trainingBTA, context, "training.png",0,0);	
		
		this.sigTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sig, context, "next.png",0,0,2,1);		
		this.tomenuTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu, context, "tomenu.png",0,0);		
		this.retryTR = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(retry, context, "retry.png",0,0,2,1);		
		
		
		this.fichaTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(ficha, context, "tarjeta.png",0,0);	
		this.failedTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(failed, context, "failed.png",0,0);	
		this.clearedTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(cleared, context, "cleared.png",0,0);	
		this.estrellasTR =BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(estrellas, context, "estrellas.png",0,0,3, 2);	
		
		this.ticTR =BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(ticBTA, context, "tic.png",0,0,2, 1);	
		
		
		this.btnAceptarTR= BitmapTextureAtlasTextureRegionFactory.createFromAsset(btnAceptar, context, "btn_aceptar.png",0,0);	
		this.btn_CancelarTR= BitmapTextureAtlasTextureRegionFactory.createFromAsset(btnCancelar, context, "btn_cancelar.png",0,0);	
		this.btnFondoTR= BitmapTextureAtlasTextureRegionFactory.createFromAsset(btnFondo, context, "salirMenu.png",0,0);	
		
		context.getEngine().getTextureManager().loadTexture(globo_Azul_ani);
		context.getEngine().getTextureManager().loadTexture(btnAceptar);
		context.getEngine().getTextureManager().loadTexture(btnCancelar);
		context.getEngine().getTextureManager().loadTexture(btnFondo);
		
		context.getEngine().getTextureManager().loadTexture(info_1BTA);
		context.getEngine().getTextureManager().loadTexture(info_2BTA);
		context.getEngine().getTextureManager().loadTexture(info_3BTA);
		context.getEngine().getTextureManager().loadTexture(info_4BTA);
		
		
		context.getEngine().getTextureManager().loadTexture(campaignBTA);
		context.getEngine().getTextureManager().loadTexture(trainingBTA);
		
		
		context.getEngine().getTextureManager().loadTexture(moneda_tiledm);
		context.getEngine().getTextureManager().loadTexture(moco_tiled90R);
		context.getEngine().getTextureManager().loadTexture(ticBTA);
		context.getEngine().getTextureManager().loadTexture(auraBTA);
		context.getEngine().getTextureManager().loadTexture(iconosHUD);
		context.getEngine().getTextureManager().loadTexture(fondoBTA);
		context.getEngine().getTextureManager().loadTexture(loading);
		context.getEngine().getTextureManager().loadTexture(estrellas);
		context.getEngine().getTextureManager().loadTexture(sig);
		context.getEngine().getTextureManager().loadTexture(menu);
		context.getEngine().getTextureManager().loadTexture(retry);
		context.getEngine().getTextureManager().loadTexture(ficha);
		context.getEngine().getTextureManager().loadTexture(failed);
		context.getEngine().getTextureManager().loadTexture(cleared);
		
		context.getEngine().getTextureManager().loadTexture(hudBitmapTextureAtlas);
		context.getEngine().getTextureManager().loadTexture(puerta);
		context.getEngine().getTextureManager().loadTexture(globo_naranja_ani);
		context.getEngine().getTextureManager().loadTexture(fantasma_tile90);
		context.getEngine().getTextureManager().loadTexture(moco_tiled90);
		context.getEngine().getTextureManager().loadTexture(moneda_tiled);
		context.getEngine().getTextureManager().loadTexture(cebolla_final90);
		context.getEngine().getTextureManager().loadTexture(cebolla_final90N);
		context.getEngine().getTextureManager().loadTexture(cebolla_final90R);
		context.getEngine().getTextureManager().loadTexture(bomba_ani90);
		context.getEngine().getTextureManager().loadTexture(restore_btn);
		context.getEngine().getTextureManager().loadTexture(btn_1);
		context.getEngine().getTextureManager().loadTexture(btn_2);
		context.getEngine().getTextureManager().loadTexture(control);
		context.getEngine().getTextureManager().loadTexture(mas);
		context.getEngine().getTextureManager().loadTexture(menos);
		context.getEngine().getTextureManager().loadTexture(tiledmaster90A);
		context.getEngine().getTextureManager().loadTexture(tiledmaster90B);
				
		this.inicio_BTA = new BuildableBitmapTextureAtlas(context.getTextureManager(),2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);			
		this.fondo_inicio_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.inicio_BTA, context, ConstantesResolucion.getFondoInicio());
		this.fondo_mapas_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(this.inicio_BTA, context, ConstantesResolucion.getFondoMenuMapas());			
		 try 
		    {
			 inicio_BTA.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0,0,0));
			 context.getEngine().getTextureManager().loadTexture(inicio_BTA);
		    } catch (final Exception e) {
		        Debug.e(e);
		    }

		 BitmapTextureAtlas pause_BTA= new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 BitmapTextureAtlasTextureRegionFactory.createFromSource(pause_BTA, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		 this.pauseTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(pause_BTA, context, "pause.png",0,0);	
		 context.getEngine().getTextureManager().loadTexture(pause_BTA);
		 
		 
		 
		 
			
		sombrasBTA = new BitmapTextureAtlas(context.getTextureManager(),1024, 256, TextureOptions.NEAREST_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(sombrasBTA, new TransparentBitmapTextureAtlasSource(1024, 256), 0, 0);
		this.sombrasTR=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(sombrasBTA, context, "sombras3.png",0,0,4, 1);
		context.getEngine().getTextureManager().loadTexture(sombrasBTA);		
		 
		
		 BitmapTextureAtlas play_BTA= new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 BitmapTextureAtlasTextureRegionFactory.createFromSource(play_BTA, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		 this.playTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(play_BTA, context, "play.png",0,0);	
		 context.getEngine().getTextureManager().loadTexture(play_BTA);
		 
		 
		 BitmapTextureAtlas menu_BTA= new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 BitmapTextureAtlasTextureRegionFactory.createFromSource(menu_BTA, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		 this.menuTR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menu_BTA, context, "menu.png",0,0);	
		 context.getEngine().getTextureManager().loadTexture(menu_BTA);
		 
		 
		 BitmapTextureAtlas iconoVolver_BTA= new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 BitmapTextureAtlas iconoflecha_BTA= new BitmapTextureAtlas(context.getTextureManager(),128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 BitmapTextureAtlasTextureRegionFactory.createFromSource(iconoVolver_BTA, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		 BitmapTextureAtlasTextureRegionFactory.createFromSource(iconoflecha_BTA, new TransparentBitmapTextureAtlasSource(128,128), 0, 0);
		 
		 this.iconoVolver_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(iconoVolver_BTA, context, "btn_menu.png",0,0);	
		 this.iconoflecha_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(iconoflecha_BTA, context, "btn_flecha.png",0,0);	
		 context.getEngine().getTextureManager().loadTexture(iconoVolver_BTA);
		 context.getEngine().getTextureManager().loadTexture(iconoflecha_BTA);
		 
		 BitmapTextureAtlas icono_BTA= new BitmapTextureAtlas(context.getTextureManager(),1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 BitmapTextureAtlasTextureRegionFactory.createFromSource(icono_BTA, new TransparentBitmapTextureAtlasSource(1024,256), 0, 0);
		 
		 this.icono_bombas_TR=BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(icono_BTA, context, "bombas_mapas.png",0,0,5, 1); 
		 context.getEngine().getTextureManager().loadTexture(icono_BTA);
		 
		 BitmapTextureAtlas texto_BTA= new BitmapTextureAtlas(context.getTextureManager(),512, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		 BitmapTextureAtlasTextureRegionFactory.createFromSource(texto_BTA, new TransparentBitmapTextureAtlasSource(512,128), 0, 0);
		 
		this.opciones_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(texto_BTA, context, "texto_options_menu.png",0,0);		
		context.getEngine().getTextureManager().loadTexture(texto_BTA);	
		
		
		BitmapTextureAtlas bomba_inicio_BTA;
		bomba_inicio_BTA = new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		BitmapTextureAtlasTextureRegionFactory.createFromSource(bomba_inicio_BTA, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
		this.bomba_inicio_TR = BitmapTextureAtlasTextureRegionFactory.createFromAsset(bomba_inicio_BTA, context, "bomba_play_inicio.png", 0, 0);
		context.getEngine().getTextureManager().loadTexture(bomba_inicio_BTA);
	
			FontFactory.setAssetBasePath("font/");		
//			this.mFontTexturBombae= new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//			BitmapTextureAtlas mFontTexturEras= new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//			
//			BitmapTextureAtlas mFontTextureBomber= new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//			
//			BitmapTextureAtlasTextureRegionFactory.createFromSource(mFontTexturBombae, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
//			BitmapTextureAtlasTextureRegionFactory.createFromSource(mFontTexturEras, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
//			
//			BitmapTextureAtlas fontDigital= new BitmapTextureAtlas(context.getTextureManager(),256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
//			BitmapTextureAtlasTextureRegionFactory.createFromSource(fontDigital, new TransparentBitmapTextureAtlasSource(256,256), 0, 0);
			
			this.mFont = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(), "acegaffigan.ttf", Inicio.FONT_SIZE_1, true, android.graphics.Color.WHITE);
			mFont.load();
			this.mFontDigital = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(),"DigitaldreamFat.ttf", ConstantesResolucion.getTxtHUD_Size_MASTER(), true, android.graphics.Color.BLACK);
			mFontDigital.load();
			this.mFontEras = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(), "ERASDEMI.TTF", 50, true, android.graphics.Color.BLACK);
			mFontEras.load();
			this.mFontBomber = FontFactory.createFromAsset(context.getFontManager(), context.getTextureManager(), 256, 256, TextureOptions.BILINEAR, context.getAssets(), "bomberman.ttf", 30, true, android.graphics.Color.BLACK);
			mFontBomber.load();
//			context.getEngine().getTextureManager().loadTexture(mFontTextureBomber);
//			context.getEngine().getTextureManager().loadTexture(mFontTexturEras);
//			context.getEngine().getTextureManager().loadTexture(this.mFontTexturBombae);
//			context.getEngine().getTextureManager().loadTexture(fontDigital);
//			context.getEngine().getFontManager().loadFont(this.mFont);	
//			context.getEngine().getFontManager().loadFont(this.mFontDigital);	
//			context.getEngine().getFontManager().loadFont(this.mFontEras);	
//			context.getEngine().getFontManager().loadFont(this.mFontBomber);
		
			MusicFactory.setAssetBasePath("mfx/");
			SoundFactory.setAssetBasePath("mfx/");
			try {
				this.monedasSound = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "ITEM_GET.wav");
				this.bomset = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "SE_08.wav");
				this.campanaFinal = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "arpa.mp3");
				this.explosion = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "BOM_11_M_bajos.wav");
				this.estrellas = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "estrellas.mp3");
			} catch (final IOException e) {
				e.printStackTrace();
			}
			this.estrellas.setVolume(1);
			bomset.setVolume(1);
			
			//musica1.setVolume(0.1f);
			explosion.setVolume(2f);
		
		
			
			

			
	}
	
	
	
	public void cargaMusica(){
		MusicFactory.setAssetBasePath("mfx/");
		try {		
			this.musica1 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "410140_Bomberman_2___Music.mp3");
			this.musica2 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "ARYX.mp3");
			this.musica3 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "BLeH.mp3");
			this.musica4 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "BRD.mp3");
			this.musica5 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "DESPERATE .mp3");
			this.musica6 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "FFF.mp3");
			this.musica7 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "iNFERNO.mp3");
			this.musica8 = MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "tPORt.mp3");
			this.booster= MusicFactory.createMusicFromAsset(context.getEngine().getMusicManager(), context, "booster.mp3");
			
			this.mecha = SoundFactory.createSoundFromAsset(context.getEngine().getSoundManager(), context, "mecha.mp3");
			mecha.setLooping(true);
			
		} catch (final IOException e) {
			e.printStackTrace();
		}
		//musica1.setVolume(0.1f);
		jukebox();
		cargarPreferencias();
	}
	
	
	
	public void cargarPreferencias(){
		setVolumenSonido();
		setVolumenMusica();
		
	}
	
	
	public void jukebox() {

		musica1.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(1);
			}
		});
		musica2.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(2);
			}
		});

		musica3.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(3);
			}
		});
		musica4.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(4);
			}
		});
		musica5.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(5);
			}
		});
		musica6.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(6);
			}
		});
		musica7.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(7);
			}
		});
		musica8.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoCancion(8);
			}
		});
		booster.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				terminadoBooster();
			}
		});


	}
	public void terminadoBooster(){
		System.out.println("Terrminado boossterrrr");		
		context.getGameManager().getBomberman().finBooster();	
		context.getGameManager().getEstado().setBoosterActivado(false);
		new Thread(){
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if (!playinMusica)return;
				switch (musicaSonando) {
				case 1:
					musica1.resume();
					break;

				case 2:
					musica2.resume();
					break;
				case 3:
					musica4.resume();
					break;
				case 5:
					musica5.resume();
					break;
				case 6:
					musica6.resume();
					break;
				case 7:
					musica7.resume();
					break;
				default:
					musica8.resume();
					break;		
				}
				
			};
			
		}.start();
	}
	
	public void terminadoCancion(final int cancion){
		
		new Thread(){
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Log.i("mierda", "terminado cancion");
				int aleatorio =cancion;
				while(aleatorio==cancion){
					aleatorio =Aleatorio.DameAleatorio(1, 8);			
				}
				playMusica(aleatorio);
				
			};
			
		}.start();
		
		
		
		
	}
	
	
	public void playMusicaRandom(){
		Log.i("mierda", "playMusicaRandom");
		int aleatorio =Aleatorio.DameAleatorio(1, 9);
		if (aleatorio!=1)aleatorio =Aleatorio.DameAleatorio(1, 9);
		if (aleatorio!=1)aleatorio =Aleatorio.DameAleatorio(1, 9);
		stopMusica();
		playMusica(aleatorio);
	}
	
	public boolean playinMusica=false;
	
	public void inicializaMusica(){
		musica1.setVolume(0);
		musica2.setVolume(0);
		musica3.setVolume(0);
		musica4.setVolume(0);
		musica5.setVolume(0);
		musica6.setVolume(0);
		musica7.setVolume(0);
		musica8.setVolume(0);
		booster.setVolume(0);
		
		musica1.play();	
		musica2.play();	
		musica3.play();	
		musica4.play();	
		musica5.play();	
		musica6.play();	
		musica7.play();	
		musica8.play();	
		booster.play();
		
		musica1.pause();
		musica2.pause();
		musica3.pause();
		musica4.pause();
		musica5.pause();
		musica6.pause();
		musica7.pause();
		musica8.pause();
		booster.pause();
		
		musica1.setVolume(0.1f);
		musica2.setVolume(0.1f);
		musica3.setVolume(0.1f);
		musica4.setVolume(0.1f);
		musica5.setVolume(0.1f);
		musica6.setVolume(0.1f);
		musica7.setVolume(0.1f);
		musica8.setVolume(0.1f);
		booster.setVolume(0.1f);
	}
	
	
	int musicaSonando=0;
	public void playMusica( int cancion){
		setVolumenMusica();
		playinMusica=true;
		Log.i("mierda", "playMusica playinMusica="+playinMusica);
		

				// TODO Auto-generated method stub
				switch (cancion) {
				case 1:		
					musicaSonando=1;
					musica1.seekTo(0);
					musica1.play();		
					break;
				case 2:		
					musicaSonando=2;
					musica2.seekTo(0);
					musica2.play();		
					break;
				case 3:		
					musicaSonando=3;
					musica3.seekTo(0);
					musica3.play();		
					break;
				case 4:		
					musicaSonando=4;
					musica4.seekTo(0);
					musica4.play();			
					break;
				case 5:		
					musicaSonando=5;
					musica5.seekTo(0);
					musica5.play();				
					break;
				case 6:		
					musicaSonando=6;
					musica6.seekTo(0);
					musica6.play();		
					break;
				case 7:		
					musicaSonando=7;
					musica7.seekTo(0);
					musica7.play();		
					break;			
				default:  	
					musicaSonando=8;
					musica8.seekTo(0);
					musica8.play();					
					break;
				}
		

	}
	
	
	int bombas=0;
	public int sonarMecha(){
		float vol= Preferencias.leerPreferenciasInt("sound_level");
		if (vol==-1){
			vol=5;
		}
		mecha.setVolume((vol/10)*2);
		if (bombas==0)mecha.play();
		bombas++;
		System.out.println("bombas"+bombas);
		return bombas;
	}
	
	public int pararMecha(){
		
		bombas--;
		System.out.println("bombas"+bombas);
		if (bombas==0){
			mecha.pause();
		}
		return bombas;
	}

	
	public void setVolumenSonido(){
		Log.d("pasos","setVolumenSonido()" );
		float volumen= Preferencias.leerPreferenciasInt("sound_level");
		if (volumen==-1)volumen=5;
		
		this.campanaFinal.setVolume((volumen/10)*2);
		this.pasosB.setVolume((volumen/10)*4);			
		this.monedasSound.setVolume(volumen/3);
		this.bomset.setVolume(volumen);
		mecha.setVolume((volumen/10)*2);
		explosion.setVolume((volumen/10)*6);
		this.estrellas.setVolume(volumen);
		
	}
		
	public void setVolumenMusica(){
		float volumen= Preferencias.leerPreferenciasInt("music_level");
		if (volumen==-1)volumen=5;
		
		volumen=volumen/10;
		System.out.println("volumen "+volumen);
		musica1.setVolume(volumen*0.3f);
		musica2.setVolume(volumen*0.3f);
		musica3.setVolume(volumen*0.3f);
		musica4.setVolume(volumen*0.3f);
		musica5.setVolume(volumen*0.3f);
		musica6.setVolume(volumen*0.3f);
		musica7.setVolume(volumen*0.3f);
		musica8.setVolume(volumen*0.3f);
		booster.setVolume(volumen*0.3f);
		
		System.out.println("Volumen musica al "+musica7.getVolume());
	}
	
	public void playBooster(){
		try{musica1.pause();}catch(Exception e){};
		try{musica2.pause();}catch(Exception e){};
		try{musica3.pause();}catch(Exception e){};
		try{musica4.pause();}catch(Exception e){};
		try{musica5.pause();}catch(Exception e){};
		try{musica6.pause();}catch(Exception e){};
		try{musica7.pause();}catch(Exception e){};
		try{musica8.pause();}catch(Exception e){};	
		booster.seekTo(0);
		booster.play();
	}
	
	
	public void pararBoosterMuertoBomberman(){
		if (!booster.isPlaying())return;
		try{booster.pause();}catch(Exception e){};
		terminadoBooster();
	}
	
	public void PasadoPantalla(){		
		try{booster.pause();}catch(Exception e){};	
		stopMusica();
	}
	
	public boolean isMusica() {
		if (Preferencias.leerPreferencias("musica").equals("true")){
			musicaEnable=true;
		}else{
			musicaEnable=false;
		}
		return musicaEnable;
	}
	public void setMusica(Boolean musica) {
		Preferencias.guardarPrefenrencias("musica",musica.toString());
		if(!musica){
			playinMusica=false;
			stopMusica();
		}else{
			playinMusica=true;
		}
		this.musicaEnable = musica;
	}
	
	public void stopMusica(){
		Log.i("mierda", "stopMusica "+playinMusica);
		playinMusica=false;
		try{musica1.pause();}catch(Exception e){};
		try{musica2.pause();}catch(Exception e){};
		try{musica3.pause();}catch(Exception e){};
		try{musica4.pause();}catch(Exception e){};
		try{musica5.pause();}catch(Exception e){};
		try{musica6.pause();}catch(Exception e){};
		try{musica7.pause();}catch(Exception e){};
		try{musica8.pause();}catch(Exception e){};		
		try{booster.pause();}catch(Exception e){};
		playinMusica=false;
		
	}
	public boolean isPlayinMusica() {
		Log.i("mierda", "playinMusica="+playinMusica);
		
		return playinMusica;
	}
	
	
	
	private boolean musicaEnable=true;
	
	public boolean isSonido() {
		return sonido;
	}

	public void setSonido(boolean sonido){
		this.sonido=sonido;
	}

	public void sonidoPlantarBombaPlay(){
		if(sonido){
			try{
			bomset.play();
			}catch(Exception e){}
		}
	}
	
	public void sonidoExplosionPlay(){
		if(sonido){
			try{
				explosion.play();
			}catch(Exception e){}
		}
	}
	
	
	public void sonidoMonedaPlay(){
		if(sonido){
			try{
				monedasSound.play();
			}catch(Exception e){}
		}
	}
	
	
	public Music getMusica1() {
		return musica1;
	}

	public Sound getExplosion() {
		return explosion;
	}
	public Sound getMonedasSound() {
		return monedasSound;
	}
	
	public TextureRegion getOpciones_TR() {
		return opciones_TR;
	}

	public TextureRegion getRestoreTR() {
		return restoreTR;
	}

	public TextureRegion getBtn_1_TR() {
		return btn_1_TR;
	}

	public TextureRegion getBtn_2_TR() {
		return btn_2_TR;
	}

	public TextureRegion getCrucetaTR() {
		return crucetaTR;
	}

	public TextureRegion getMasTR() {
		return masTR;
	}

	public TextureRegion getMenosTR() {
		return menosTR;
	}

	public TiledTextureRegion getmFuegoCentroTR() {
		return mFuegoCentroTR;
	}

	public TiledTextureRegion getmFuegoCentroAbajoTR() {
		return mFuegoCentroAbajoTR;
	}

	public TiledTextureRegion getmFuegoCentroArribaTR() {
		return mFuegoCentroArribaTR;
	}

	public TiledTextureRegion getmFuegoCentroDerechaTR() {
		return mFuegoCentroDerechaTR;
	}

	public TiledTextureRegion getmFuegoCentroIzquierdaTR() {
		return mFuegoCentroIzquierdaTR;
	}

	public TiledTextureRegion getmFuegoFinAbajoTR() {
		return mFuegoFinAbajoTR;
	}

	public TiledTextureRegion getmFuegoFinArribaTR() {
		return mFuegoFinArribaTR;
	}

	public TiledTextureRegion getmFuegoFinDerechaTR() {
		return mFuegoFinDerechaTR;
	}

	public TiledTextureRegion getmFuegoFinIzquierdaTR() {
		return mFuegoFinIzquierdaTR;
	}

	public TiledTextureRegion getmFuegoHorizontalTR() {
		return mFuegoHorizontalTR;
	}

	public TiledTextureRegion getmFuegoVerticalTR() {
		return mFuegoVerticalTR;
	}

	public TextureRegion getIconoVolver_TR() {
		return iconoVolver_TR;
	}

	public TextureRegion getIconoflecha_TR() {
		return iconoflecha_TR;
	}

	public TextureRegion getPuerta_TR() {
		return puerta_TR;
	}
	
}
