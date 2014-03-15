package net.precariouspanther.gdxtest;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "GDX-test";
		cfg.useGL20 = false;
		cfg.width = 640;
		cfg.height = 480;
		cfg.useGL20 = true;
		
		new LwjglApplication(new GDXTestPaper(), cfg);
	}
}
