package myGame;

import tage.*;
import tage.input.action.AbstractInputAction;
import net.java.games.input.Event;
import org.joml.*;

public class TurnAction extends AbstractInputAction
{
    private MyGame game;
    private GameObject av;
    private boolean isMounted;

    public TurnAction(MyGame game){
        this.game = game;
    }

    @Override
    public void performAction(float time, Event e)
    {   
        Engine engine = game.getEngine();
        isMounted = game.getMounted();
        float keyValue = e.getValue();
        String inputName = e.getComponent().getName(); //A, D, and X Axis
        if (keyValue > -.2 && keyValue < .2) return; // deadzone (works for A and D)
        float yawValue = keyValue *.3f;
        if(isMounted) {
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
        else {
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
            Camera cam;
		    cam = (engine.getRenderSystem().getViewport("MAIN").getCamera());
            //rotate the U and N axes around V
            Vector3f nVector = cam.getN();
            Vector3f uVector = cam.getU();
            Vector3f vVector = cam.getV();

            Vector3f newU = uVector.rotateAxis(-.01f *yawValue, vVector.x, vVector.y ,vVector.z);
            Vector3f newN = nVector.rotateAxis(-.01f *yawValue, vVector.x, vVector.y ,vVector.z);

            cam.setU(newU);
            cam.setN(newN);
        }
    } 
}