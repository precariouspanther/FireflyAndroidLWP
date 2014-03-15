package net.precariouspanther.gdxtest;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Swarm {
	private static final float SPEED_LIMIT = 60;
	public Vector2 position;
	public Vector2 velocity;
	public Vector2 acceleration;
	
	private GDXTestPaper stage;
	
	
	Swarm(GDXTestPaper stage) {
		this.stage = stage;
		

		Random rnd = new Random();

		// Init Position
		position = new Vector2(rnd.nextFloat() * stage.width, rnd.nextFloat()
				* stage.height);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0f, 0f);

	}
	
	void move(float delta) {
		acceleration.set(0f, 0f);

		acceleration.x += (float) (Math.random() - 0.5) * 8;
		acceleration.y += (float) (Math.random() - 0.5) * 8;

		// Screen edge bouncing
		if (position.x + velocity.x > stage.width - 2
				|| position.x + velocity.x < 0) {
			// velocity.x = -velocity.x;
			velocity.x = 0;
		}
		// Screen edge bouncing
		if (position.y + velocity.y > stage.height - 2
				|| position.y + velocity.y < 0) {
			// velocity.y = -velocity.y;
			velocity.y = 0;
		}

		velocity.add(acceleration);


		// }

		// Speed limiting
		if (velocity.x > SPEED_LIMIT)
			velocity.x = SPEED_LIMIT;
		if (velocity.x < -SPEED_LIMIT)
			velocity.x = -SPEED_LIMIT;
		if (velocity.y > SPEED_LIMIT)
			velocity.y = SPEED_LIMIT;
		if (velocity.y < -SPEED_LIMIT)
			velocity.y = -SPEED_LIMIT;

		// position.add(velocity);

		position.x += velocity.x * delta;
		position.y += velocity.y * delta;

		// Reposition on stage if we get lost
		if (position.x > stage.width || position.x < 0) {
			position.x = stage.width / 2;
		}
		if (position.y > stage.height || position.y < 0) {
			position.y = stage.height / 2;
		}

	}
}
