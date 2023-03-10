package a1;
import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;

public class PanAction extends AbstractInputAction
{ 
    private MyGame game;
    private float zoomValue;
    private Camera cam;

    public PanAction(MyGame g){ 
        game = g;
        cam = game.getEngine().getRenderSystem().getViewport("BR").getCamera();
    }

    @Override
    public void performAction(float time, Event e)
    { 
        float keyValue = e.getValue();
        String inputName = e.getComponent().getName(); // W, S, Y Axis
        if (keyValue > -.2 && keyValue < .2) return; 

        //process I and K first 
        switch (inputName){
            case "I":
                cam.pitch(-0.5f);
                break;
            case "K":
                cam.pitch(0.5f);
                break;
            case "J":
                cam.yaw(-0.5f);
                break;
            case "L":
                cam.yaw(0.5f);
                break;
        }
    }


    
}