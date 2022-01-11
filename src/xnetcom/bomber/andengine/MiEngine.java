package xnetcom.bomber.andengine;




import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.util.GLState;

import android.annotation.SuppressLint;
import android.util.Log;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 22:28:34 - 27.03.2010
 */

@SuppressLint("WrongCall")
public class MiEngine extends Engine {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final Camera mSecondCamera;

	// ===========================================================
	// Constructors
	// ===========================================================

	public MiEngine(final EngineOptions pEngineOptions, final Camera pSecondCamera) {
		super(pEngineOptions);
		this.mSecondCamera = pSecondCamera;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	@Deprecated
	@Override
	public Camera getCamera() {
		return super.mCamera;
	}

	public Camera getFirstCamera() {
		return super.mCamera;
	}

	public Camera getSecondCamera() {
		return this.mSecondCamera;
	}

	int selCamera=1;
	public void selCamera(int camera){
		Log.d("mierda", "selcamera"+camera);
		selCamera=camera;
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	@Override
	protected void onDrawScene(GLState pGLState, Camera pCamera) {
		final Camera firstCamera = this.getFirstCamera();
		final Camera secondCamera = this.getSecondCamera();		
		if (selCamera == 1) {
			if (this.mScene != null) {
				this.mScene.onDraw(pGLState, firstCamera);
			}
			firstCamera.onDrawHUD(pGLState);
		} else {
			if (this.mScene != null) {
				this.mScene.onDraw(pGLState, secondCamera);
			}
			secondCamera.onDrawHUD(pGLState);
		}

	}

	
	@Override
	protected Camera getCameraFromSurfaceTouchEvent(final TouchEvent pTouchEvent) {
		if(selCamera==1) {
			return this.getFirstCamera();
		} else {
			return this.getSecondCamera();
		}
	}
	
	// ===========================================================
	// Methods
	// ===========================================================

	
	
	
	
	
	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
