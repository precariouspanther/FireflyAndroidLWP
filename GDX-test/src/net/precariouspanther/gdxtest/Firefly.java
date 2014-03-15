package net.precariouspanther.gdxtest;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Firefly {
	public enum FireflyState {
		GLOWING, FADING, ALIVE, HIDING
	}

	private float speedLimit = 60;
	private float gravityForce = 6.2f;

	private float alpha = 1f; // How bright are we

	private Sprite sprite;

	public Vector2 position;
	public Vector2 velocity;
	public Vector2 acceleration;

	public FireflyState state = FireflyState.ALIVE;

	public boolean visible = true;

	private GDXTestPaper stage;

	Firefly(GDXTestPaper stage, TextureRegion region) {
		this.stage = stage;
		// Create sprite
		sprite = new Sprite(region);
		sprite.setSize(32f, 32f);

		// Origin central to sprite
		sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
		// sprite.setPosition(100,10);

		Random rnd = new Random();

		// Init Position
		position = new Vector2(rnd.nextFloat() * stage.width, rnd.nextFloat()
				* stage.height);

		// Each firefly has randomly generated traits:
		speedLimit = rnd.nextFloat() * 30 + 10;
		// How attracted from a swarm
		gravityForce = rnd.nextFloat() * 10f;

		// position.set(rnd.nextFloat() * stage.width, rnd.nextFloat() *
		// stage.height);
		// position.set(30,50);

		// velocity = new Vector2(rnd.nextFloat() - 0.5f * 10,
		// rnd.nextFloat() - 0.5f * 10);
		velocity = new Vector2(0, 0);
		acceleration = new Vector2(0f, 0f);

	}

	void create() {

	}

	void move(float delta) {

		switch (state) {
		case FADING:
			if (this.alpha > 0f) {
				this.alpha -= 0.1f;
			} else {
				alpha = 0f;
				state = FireflyState.HIDING;

			}
			break;
		case GLOWING:
			if (this.alpha < 1f) {
				this.alpha += 0.01f;
			} else {
				alpha = 1f;
				state = FireflyState.ALIVE;
			}

			break;
		case ALIVE:
			alpha = 1f;
			// Flyin' around minding our own business. Should we start fading?
			if (Math.random() > 0.999) {
				state = FireflyState.FADING;
			}
			break;
		case HIDING:
			alpha = 0f;
			// We're flying around invisible at the moment. Should we light back
			// up?
			if (Math.random() > 0.999) {
				state = FireflyState.GLOWING;
			}

			break;
		default:
			break;

		}

		// Should we turn on/off our light?

		acceleration.set(0f, 0f);

		acceleration.x += (float) (Math.random() - 0.5);
		acceleration.y += (float) (Math.random() - 0.5);

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

		// Check touch gravity
		if (stage.touchPosition.x != -1) {
			// Active touch. Apply gravity, ignore swarms.
			float force = 10000 /this.position.dst2(stage.touchPosition);
			//this.velocity.x = force;
			//this.velocity.y = 0;
			//this.velocity.setAngle(angle)
			
			this.velocity.x += force
					* (stage.touchPosition.x - this.position.x);
			this.velocity.y += force
					* (stage.touchPosition.y - this.position.y);

		} else {

			// Gravity to swarms

			for (Swarm swarm : stage.swarms) {
				float dist = this.fastDistance(swarm.position);
				if (dist > 1 && dist < 200) {
					float force = (gravityForce / dist) * delta;
					this.velocity.x += force
							* (swarm.position.x - this.position.x);
					this.velocity.y += force
							* (swarm.position.y - this.position.y);
					// System.out.println(this.acceleration.x);

				}

			}
		}

		// }

		// If we start moving off the screen, strong force to push us back.
		if (position.x > stage.width || position.x < 0) {
			velocity.x = stage.width / 2 - position.x;
		}
		if (position.y > stage.height || position.y < 0) {
			velocity.y = stage.height / 2 - position.y;
			// position.y = stage.height / 2;
		}
		
		// Speed limiting
		if (velocity.x > speedLimit)
			velocity.x = speedLimit;
		if (velocity.x < -speedLimit)
			velocity.x = -speedLimit;
		if (velocity.y > speedLimit)
			velocity.y = speedLimit;
		if (velocity.y < -speedLimit)
			velocity.y = -speedLimit;

		// position.add(velocity);

		position.x += velocity.x * delta;
		position.y += velocity.y * delta;

	}

	void render(SpriteBatch batch) {
		alpha = Math.min(1f, alpha);
		alpha = Math.max(0f, alpha);

		if (state != FireflyState.HIDING) {
			sprite.setColor(0.5f, 1f, 0.5f, alpha);
			sprite.setPosition(position.x, position.y);
			sprite.setRotation(velocity.angle() - 90); // Texture rotated by 90
														// degrees
			sprite.draw(batch);
		}
	}

	void dispose() {

	}

	float fastDistance(Vector2 target) {
		float abX = Math.abs(position.x - target.x);
		float abY = Math.abs(position.y - target.y);
		float dist = (float) ((1 + 1 / (4 - 2 * GDXTestPaper.SQRT2)) / 2 * Math
				.min((1 / GDXTestPaper.SQRT2) * (abX + abY), Math.max(abX, abY)));
		return dist;
	}

}
