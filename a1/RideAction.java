package a1;
import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;

public class RideAction extends AbstractInputAction
{ 
    private MyGame game;
    private GameObject av;
    private Vector3f oldPosition, newPosition;
    private Vector4f fwdDirection;
    private boolean isMounted;

    public RideAction(MyGame g){ 
        game = g;
    }

    @Override
    public void performAction(float time, Event e)
    { 
        System.out.println("button pushed");
        isMounted = game.getMounted();
        if(isMounted){
            game.dismountCam();
        }
        game.setMounted(!isMounted);
    }
}