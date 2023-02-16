package myGame;

import tage.*;
import tage.input.InputManager;
import tage.shapes.*;

import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import org.joml.*;

import myGame.FwdAction;
import myGame.TurnAction;

public class MyGame extends VariableFrameRateGame
{
	private boolean isMounted;
	private InputManager im;
	private static Engine engine;

	private boolean paused=false;
	private int counter=0;
	private double lastFrameTime, currFrameTime, elapsTime;

	private GameObject dol, cub, x, y, z;
	private ObjShape dolS, cubS, linxS, linyS, linzS;
	private TextureImage doltx, prize;
	private Light light1;

	public MyGame() { super(); }

	public static void main(String[] args)
	{	MyGame game = new MyGame();
		engine = new Engine(game);
		game.initializeSystem();
		game.game_loop();
	}

	@Override
	public void loadShapes()
	{	dolS = new ImportedModel("dolphinHighPoly.obj");
		cubS = new Cube();
		linxS = new Line(new Vector3f(0f,0f,0f), new Vector3f(3f,0f,0f));
		linyS = new Line(new Vector3f(0f,0f,0f), new Vector3f(0f,3f,0f));
		linzS = new Line(new Vector3f(0f,0f,0f), new Vector3f(0f,0f,-3f));
	}

	@Override
	public void loadTextures()
	{	doltx = new TextureImage("Dolphin_HighPolyUV.png");
		prize = new TextureImage("prize.png");
	}

	@Override
	public void buildObjects()
	{	Matrix4f initialTranslation, initialScale;

		// create world axes
		x = new GameObject(GameObject.root(), linxS);
		y = new GameObject(GameObject.root(), linyS);
		z = new GameObject(GameObject.root(), linzS);

		//set world axes' colors
		x.getRenderStates().setColor(new Vector3f(1f, 0f, 0f));
		y.getRenderStates().setColor(new Vector3f(0f, 1f, 0f));
		z.getRenderStates().setColor(new Vector3f(0f, 0f, 1f));

		// build dolphin in the center of the window
		dol = new GameObject(GameObject.root(), dolS, doltx);
		initialTranslation = (new Matrix4f()).translation(0,0,0);
		initialScale = (new Matrix4f()).scaling(3.0f);
		dol.setLocalTranslation(initialTranslation);
		dol.setLocalScale(initialScale);

		// build cube at the right of the window
		cub = new GameObject(GameObject.root(), cubS, prize);
		initialTranslation = (new Matrix4f()).translation(3,0,0);
		initialScale = (new Matrix4f()).scaling(0.5f);
		cub.setLocalTranslation(initialTranslation);
		cub.setLocalScale(initialScale);
	}

	@Override
	public void initializeLights()
	{	Light.setGlobalAmbient(0.5f, 0.5f, 0.5f);
		light1 = new Light();
		light1.setLocation(new Vector3f(5.0f, 4.0f, 2.0f));
		(engine.getSceneGraph()).addLight(light1);
	}

	@Override
	public void initializeGame()
	{	lastFrameTime = System.currentTimeMillis();
		currFrameTime = System.currentTimeMillis();
		elapsTime = 0.0;
		(engine.getRenderSystem()).setWindowDimensions(1900,1000);

		// ------------- positioning the camera -------------
		(engine.getRenderSystem().getViewport("MAIN").getCamera()).setLocation(new Vector3f(0,0,5));

		isMounted = true;

		//INPUT SECTION
		im = engine.getInputManager();
		FwdAction fwdAction = new FwdAction(this);
		TurnAction turnAction = new TurnAction(this);
		PitchAction pitchAction = new PitchAction(this);

		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.W, fwdAction,
			InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.S, fwdAction,
		InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.A, turnAction,
		InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);


		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.D, turnAction,
		InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		im.associateActionWithAllGamepads(net.java.games.input.Component.Identifier.Axis.X, turnAction,
			InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
		
		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.UP, pitchAction,
		InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);

		im.associateActionWithAllKeyboards(net.java.games.input.Component.Identifier.Key.DOWN, pitchAction,
		InputManager.INPUT_ACTION_TYPE.REPEAT_WHILE_DOWN);
	}

	@Override
	public void update()
	{	// rotate dolphin if not paused
		lastFrameTime = currFrameTime;
		currFrameTime = System.currentTimeMillis();
		if (!paused) elapsTime += (currFrameTime - lastFrameTime) / 1000.0;
		//dol.setLocalRotation((new Matrix4f()).rotation((float)elapsTime, 0, 1, 0)); THIS BITCH??????

		// build and set HUD
		int elapsTimeSec = Math.round((float)elapsTime);
		String elapsTimeStr = Integer.toString(elapsTimeSec);
		String counterStr = Integer.toString(counter);
		String dispStr1 = "Time = " + elapsTimeStr;
		String dispStr2 = "Keyboard hits = " + counterStr;
		Vector3f hud1Color = new Vector3f(1,0,0);
		Vector3f hud2Color = new Vector3f(0,0,1);
		(engine.getHUDmanager()).setHUD1(dispStr1, hud1Color, 15, 15);
		(engine.getHUDmanager()).setHUD2(dispStr2, hud2Color, 500, 15);

		//update input manager
		im.update((float) elapsTime);// can prob take out
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{ 	Vector3f loc, fwd, up, right, newLocation;
		Camera cam;
		switch (e.getKeyCode())
			{ case KeyEvent.VK_1:
				paused = !paused;
				break;
			case KeyEvent.VK_2: // move dolphin forward
				fwd = dol.getWorldForwardVector();
				loc = dol.getWorldLocation();
				newLocation = loc.add(fwd.mul(.02f));
				dol.setLocalLocation(newLocation);
				break;
			case KeyEvent.VK_3: // move dolphin backward
				fwd = dol.getWorldForwardVector();
				loc = dol.getWorldLocation();
				newLocation = loc.add(fwd.mul(-.02f));
				dol.setLocalLocation(newLocation);
				break;
			case KeyEvent.VK_4: // view from dolphin
				// to "ride" the dolphin, move this code to update()
				cam = (engine.getRenderSystem()
				.getViewport("MAIN").getCamera());
				loc = dol.getWorldLocation();
				fwd = dol.getWorldForwardVector();
				up = dol.getWorldUpVector();
				right = dol.getWorldRightVector();
				cam.setU(right);
				cam.setV(up);
				cam.setN(fwd);
				cam.setLocation(loc.add(up.mul(1.3f)).add(fwd.mul(-2.5f)));
				break;
			}
		super.keyPressed(e);
	}

	public GameObject getAvatar() { return dol; }
}