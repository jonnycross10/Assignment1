package myGame;
import tage.GameObject;
import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;
import myGame.MyGame;

public class TurnAction extends AbstractInputAction
{
    private MyGame game;
    private GameObject av;
    private Matrix4f oldRotation, newRotation, rotAroundAvatarUp;
    private Vector4f oldUp;

    public TurnAction(MyGame game){
        this.game = game;
    }

    @Override
    public void performAction(float time, Event e)
    {   
        float keyValue = e.getValue();
        String inputName = e.getComponent().getName(); //A, D, and X Axis
        if (keyValue > -.2 && keyValue < .2) return; // deadzone (works for A and D)
        float yawValue = keyValue;
        switch(inputName){
            case "A":
                yawValue = -.5f;
                break;
            case "D":
                yawValue = .5f;
                break;
            default:
                break;
        }
        av = game.getAvatar();
        av.yaw(yawValue);
    } 
}