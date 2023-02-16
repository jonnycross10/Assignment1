package myGame;
import tage.GameObject;

import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;


public class PitchAction extends AbstractInputAction
{
    private MyGame game;
    private GameObject av;

    public PitchAction(MyGame game){
        this.game = game;
    }

    @Override
    public void performAction(float time, Event e)
    {   
        float keyValue = e.getValue();
        String inputName = e.getComponent().getName(); //A, D, and X Axis
        System.out.println(inputName);
        if (keyValue > -.2 && keyValue < .2) return; // deadzone (works for A and D)
        float pitchValue = keyValue;
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
}