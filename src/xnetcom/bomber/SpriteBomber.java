package xnetcom.bomber;

import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class SpriteBomber extends AnimatedSprite{

	AnimatedSprite mitadAbajo;
	public SpriteBomber(float pX, float pY, TiledTextureRegion pTiledTextureRegion,VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pTiledTextureRegion,vertexBufferObjectManager);
		mitadAbajo = new AnimatedSprite(pX, pY, pTiledTextureRegion,vertexBufferObjectManager);		
		/*mitadAbajo.
		TiledTextureRegion  mRegion=pTiledTextureRegion.deepCopy();
		mRegion.setWidth(30);
		mRegion.setHeight(15);
		mRegion.setTexturePosition(0, 15);*/
	
	}

}
