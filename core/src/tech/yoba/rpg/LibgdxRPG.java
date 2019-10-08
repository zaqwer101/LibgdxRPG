package tech.yoba.rpg;

import Game.Core.Creature;
import Game.Core.Location;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class LibgdxRPG extends ApplicationAdapter {
	ShapeRenderer batch;
	Texture img;
	int size = 20;
	Location location;
	@Override
	public void create () {
		batch = new ShapeRenderer();
		img = new Texture("badlogic.jpg");
		Creature creature;
		location = new Location(40, 30);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setColor(Color.RED);
		batch.begin(ShapeRenderer.ShapeType.Line);
		for (var l : location.getLocation())
		{
			batch.rect(size * l.getX(), size * l.getY(), size, size);
		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
