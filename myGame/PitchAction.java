package myGame;
import tage.GameObject;

import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;

import tage.*;
import org.joml.*;


public class PitchAction extends AbstractInputAction
{
    private MyGame game;
    private GameObject av;

    private boolean isMounted;

    public PitchAction(MyGame game){
        this.game = game;
    }

    @Override
    public void performAction(float time, Event e)
    {   
        Engine engine = game.getEngine();
        isMounted = game.getMounted();
        float keyValue = e.getValue();
        String inputName = e.getComponent().getName(); //A, D, and X Axis
        System.out.println(inputName);
        if (keyValue > -.2 && keyValue < .2) return; // deadzone (works for A and D)
        float pitchValue = keyValue;
        if(isMounted){
            switch(inputName){
                case "Up":
                    pitchValue = .5f;
                    break;
                case "Down":
                    pitchValue = -.5f;
                    break;
                default:
                    break;
            }
            av = game.getAvatar();
            av.pitch(pitchValue);
        }
        else {
            switch(inputName){
                case "Up":
                    pitchValue = -.5f;
                    break;
                case "Down":
                    pitchValue = .5f;
                    break;
                default:
                    break;
            }
            Camera cam;
		    cam = (engine.getRenderSystem().getViewport("MAIN").getCamera());
            //rotate the V and N axes around U
            cam.pitch(pitchValue);
        }
    } 
}