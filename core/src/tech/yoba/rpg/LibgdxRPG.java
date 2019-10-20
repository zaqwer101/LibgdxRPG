package tech.yoba.rpg;

import Game.Core.Creature;
import Game.Core.Location;
import Game.Core.Position;
import Game.Core.Stats;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class LibgdxRPG extends ApplicationAdapter {
	ShapeRenderer batch;
	Texture img;
	int size = 20;
	Location location;
	BitmapFont font;
	Label label;
	Creature actor;
	Creature enemy;

	@Override
	public void create () {


		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("OpenSans-Regular.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 10;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		font.setColor(Color.BLACK);

		batch = new ShapeRenderer();
		location = new Location(30, 30);
		actor = new Creature("Actor", '@', new Position(2, 2),location);
		enemy = new Creature("Enemy", 'E', new Position(10, 10),location);
	}

	public void drawMap(ShapeRenderer batch, Location location)
	{
		for (var l : location.getLocation())
		{
			Color spriteColor = Color.BLACK;
			boolean isFilled = false;

			switch (l.getIcon())
			{
				case '@':
					spriteColor = Color.GREEN;
					isFilled = true;
					break;
				case '#':
					isFilled = true;
					break;
				case 'E':
					isFilled = true;
					spriteColor = Color.RED;
					break;
			}

			batch.setColor(spriteColor);
			if (isFilled)
			{
				batch.begin(ShapeRenderer.ShapeType.Filled);
			}
			else
			{
				batch.begin(ShapeRenderer.ShapeType.Line);
			}

			batch.rect(size * l.getX(), size * l.getY(), size, size);
			batch.end();
		}
	}

	public void drawUI()
	{
		SpriteBatch fontBatch = new SpriteBatch();
		fontBatch.begin();
//		font.draw(fontBatch, "Hello World", 620f, 10f);
		for (int i = 0; i < Stats.allStats.length; i++)
		{
			font.draw(fontBatch, Stats.allStats[i] + ": " + actor.getStat(Stats.allStats[i]), 620f, 590 - i * 15);
		}

		fontBatch.end();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawMap(batch, location);
		drawUI();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
