package tech.yoba.rpg;

import Game.Actions.AttackAction;
import Game.Actions.MoveAction;
import Game.Attacks.MeleeAttack;
import Game.Core.Creature;
import Game.Core.Location;
import Game.Core.Position;
import Game.Core.Stats;
import Game.Items.Item;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
		parameter.size = 12;
		font = generator.generateFont(parameter); // font size 12 pixels
		generator.dispose();
		font.setColor(Color.BLACK);
		batch = new ShapeRenderer();

		var actorStats = new Stats();
		actorStats.setStat("endurance", 6);
		location = new Location(30, 30);
		actor = new Creature("Actor", '@', new Position(2, 2),location);
		Item item = new Item("Kek", 1, 1);
		location.getLocation().get(100).addItem(item);
		actor.addAttack(new MeleeAttack(2));
		actor.addStats(actorStats);
		actor.addAttack(new MeleeAttack(1));
		enemy = new Creature("Enemy", 'E', new Position(10, 10),location);
		enemy.addToInventory(item);
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
				case '\"':
					isFilled = false;
					SpriteBatch fontBatch = new SpriteBatch();
					fontBatch.begin();
					font.draw(fontBatch, "\"", l.getX() * size + 8, l.getY() * size + 8);
					fontBatch.end();
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

	public void keyboardHandler()
	{
		if (Gdx.input.isKeyPressed(Input.Keys.A))
		{
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP))    actor.performAction(new AttackAction(actor, actor.getLocation().getPosition(actor.getPosition().getX(), actor.getPosition().getY() + 1).getMember(), 1, actor.getAttacks()[0]));
			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))  actor.performAction(new AttackAction(actor, actor.getLocation().getPosition(actor.getPosition().getX(), actor.getPosition().getY() - 1).getMember(), 1, actor.getAttacks()[0]));
			if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))  actor.performAction(new AttackAction(actor, actor.getLocation().getPosition(actor.getPosition().getX() - 1, actor.getPosition().getY()).getMember(), 1, actor.getAttacks()[0]));
			if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) actor.performAction(new AttackAction(actor, actor.getLocation().getPosition(actor.getPosition().getX() + 1, actor.getPosition().getY()).getMember(), 1, actor.getAttacks()[0]));
			return;
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.UP))    { actor.performAction(new MoveAction(actor, new int[] {0, 1} )); 	}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN))  { actor.performAction(new MoveAction(actor, new int[] {0, -1} )); }
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT))  { actor.performAction(new MoveAction(actor, new int[] {-1, 0} )); }
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) { actor.performAction(new MoveAction(actor, new int[] {1, 0} )); 	}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) { actor.endTurn(); enemy.endTurn(); }
	}

	public void drawUI()
	{
		SpriteBatch fontBatch = new SpriteBatch();
		fontBatch.begin();
		for (int i = 0; i < Stats.allStats.length; i++)
		{
			font.draw(fontBatch, Stats.allStats[i] + ": " + actor.getStat(Stats.allStats[i]),
					location.getSize()[0] * size + 10,
					Gdx.graphics.getHeight() - 10 - i * 15);
		}
		fontBatch.end();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		drawMap(batch, location);
		drawUI();
		keyboardHandler();
	}

	@Override
	public void dispose () {
		batch.dispose();
	}
}
