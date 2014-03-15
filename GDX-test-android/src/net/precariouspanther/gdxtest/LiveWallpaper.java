package net.precariouspanther.gdxtest;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;

public class LiveWallpaper extends AndroidLiveWallpaperService{

	@Override
	public AndroidApplicationConfiguration createConfig() {
		// TODO Auto-generated method stub
		return new AndroidApplicationConfiguration();
		//return null;
	}

	@Override
	public ApplicationListener createListener(boolean arg0) {
		// TODO Auto-generated method stub
		return new GDXTestPaper();
		//return null;
	}

	@Override
	public void offsetChange(ApplicationListener arg0, float arg1, float arg2,
			float arg3, float arg4, int arg5, int arg6) {
		Gdx.app.log("LiveWallpaper", "offset changed: " + arg1 + ", " + arg2);
		// TODO Auto-generated method stub
		
	}
	

}
