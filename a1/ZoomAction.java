package a1;
import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;

public class ZoomAction extends AbstractInputAction
{ 
    private MyGame game;
    private float zoomValue;
    private Camera cam;

    public ZoomAction(MyGame g){ 
        game = g;
        cam = game.getEngine().getRenderSystem().getViewport("BR").getCamera();
    }

    @Override
    public void performAction(float time, Event e)
    { 
        float keyValue = e.getValue();
        String inputName = e.getComponent().getName(); // W, S, Y Axis
        if (keyValue > -.2 && keyValue < .2) return; 
        //parse which command it was 
        switch (inputName){
            case "Y":
                zoomValue =0.998f;
                break;
            case "H":
                zoomValue =1.002f;
                break;
        }
        //modify vectors
        Vector3f camN = cam.getN();
        cam.setN(camN.mul(zoomValue));
    }

}