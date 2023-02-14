package myGame;
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
        System.out.println(keyValue);
        if (keyValue > -.2 && keyValue < .2) return; // deadzone
        av = game.getAvatar();
        oldRotation = new Matrix4f(av.getWorldRotation()); //get current rotation
        oldUp = new Vector4f(0f,1f,0f,1f).mul(oldRotation); //multiply y axis by current rotation
        //create a new matrix with rotation to current orientation applied
        //rotAroundAvatarUp = new Matrix4f().rotation(keyValue, new Vector3f(0f, oldUp.y(), 0f)); 
        //newRotation = oldRotation.mul(rotAroundAvatarUp);
        av.setLocalRotation(new Matrix4f().rotation(keyValue,0,1,0));
    } 
}