package myGame;
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

    public RideAction(MyGame g){ 
        game = g;
    }

    @Override
    public void performAction(float time, Event e)
    { 


        //change the bool at the end
        
    }
}