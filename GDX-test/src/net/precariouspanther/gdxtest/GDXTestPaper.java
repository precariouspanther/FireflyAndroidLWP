package net.precariouspanther.gdxtest;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GDXTestPaper implements ApplicationListener {
	public static float SQRT2;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Texture texture;

	// private ParticleEffect effect;
	public int width, height;

	private int flyCount = 500;
	private int swarmCount = 5;

	private boolean updateScreen = false;

	public Vector2 touchPosition;
	public Vector3 touchPosition3;
	// private MeshHelper meshHelper;

	public ArrayList<Firefly> flies;

	public ArrayList<Swarm> swarms;

	@Override
	public void create() {
		touchPosition = new Vector2();
		touchPosition3 = new Vector3();
		
		SQRT2 = (float) Math.sqrt(2);
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();

		camera = new OrthographicCamera(1, height / width);
		batch = new SpriteBatch();

		texture = new Texture(Gdx.files.internal("data/firefly.png"));
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		TextureRegion region = new TextureRegion(texture, 0, 0, 64, 64);
		batch.enableBlending();

		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
		batch.setProjectionMatrix(camera.combined);


		flies = new ArrayList<Firefly>();

		for (int i = 0; i < flyCount; i++) {
			Firefly f = new Firefly(this, region);
			flies.add(f);
		}
		swarms = new ArrayList<Swarm>();

		for (int i = 0; i <= swarmCount; i++) {
			Swarm s = new Swarm(this);
			swarms.add(s);
		}

	}

	@Override
	public void dispose() {
		batch.dispose();
		texture.dispose();
		// meshHelper.dispose();
	}

	@Override
	public void render() {
		// Gdx.gl.glClearColor(0, 0, 0, 1);
		// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if (Gdx.graphics.isGL20Available()) {
			Gdx.graphics.getGL20().glClearColor(0.1f, 0.1f, 0.1f, 0.1f);
			Gdx.graphics.getGL20().glClear(GL10.GL_COLOR_BUFFER_BIT);
		} else {
			Gdx.gl.glClearColor(0, 0, 0, 0.1f);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		}
		
		
		batch.setProjectionMatrix(camera.combined);

		// Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// Gdx.gl.glEnable(GL10.GL_DEPTH_TEST);
		Gdx.gl.glEnable(GL10.GL_BLEND);
		Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		
		Gdx.gl.glViewport(0, 0, width, height);
		if (updateScreen) {
			updateScreen = false;
			camera.setToOrtho(false, width, height);
			batch.setProjectionMatrix(camera.combined);
			// effect.setPosition(width/2, height/2);
		}
		camera.update();
		// effect.update(Gdx.graphics.getDeltaTime()/1.6f);
		
		//Process touch
		if(Gdx.input.isTouched()){
			touchPosition3.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(touchPosition3);
			
			touchPosition.set(touchPosition3.x,touchPosition3.y);
			//camera.unproject(touchPosition);
		} else {
			touchPosition.set(-1,-1);
		}
		
		batch.begin();
		// effect.draw(batch);
		
		// sprite.draw(batch);
		// batch.draw(texture, 10, 10);
		// batch.draw(texture, 50, 50);
		// batch.draw(texture, 100, 100);
		float delta = Gdx.graphics.getDeltaTime();

		for (Firefly fly : flies) {
			fly.move(delta);
			fly.render(batch);
		}

		for (Swarm swarm : swarms) {
			swarm.move(delta);
		}

		batch.end();

	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
		updateScreen = true;
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
