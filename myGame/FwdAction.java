package myGame;
import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;
import myGame.MyGame;

public class FwdAction extends AbstractInputAction
{ 
    private MyGame game;
    private GameObject av;
    private Vector3f oldPosition, newPosition;
    private Vector4f fwdDirection;

    public FwdAction(MyGame g){ 
        game = g;
    }

    @Override
    public void performAction(float time, Event e)
    { 
        String inputName = e.getComponent().getName(); // W and S
        System.out.println(inputName);
        float keyValue = inputName == "W" ? .01f : -.01f;
        av = game.getAvatar();
        oldPosition = av.getWorldLocation();
        fwdDirection = new Vector4f(0f,0f,1f,1f);
        fwdDirection.mul(av.getWorldRotation());
        fwdDirection.mul(keyValue);
        newPosition = oldPosition.add(fwdDirection.x(),
        fwdDirection.y(), fwdDirection.z());
        av.setLocalLocation(newPosition);
    }
}