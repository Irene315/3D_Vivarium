/*
 * 
 * Name: Xinyuan Zhang
 * Class: CS480
 * 
 * Assignment 3
 * Due: 2019/11/12
 * Problem Number: 1, 2, 3, 4, 5
 * 
 * Description:  
 *  1. create food model
 *  
 *  2. implement animationUpdate() to make food drop
 *  
 */

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.*;

import com.jogamp.opengl.util.gl2.GLUT;

import java.util.*;

public class Food extends Component implements Animate{

	private double rotateSpeed = 0.01;
	private float scale;
	
	public Food(Point3D p, float scale) {
		super(new Point3D(p));
		this.scale = scale;
		Component foodBody = new Component(new Point3D(0, 0, 0), new FoodDisplayable(scale));
		foodBody.setColor(new FloatColor(0.3f, 0.6f, 1f));
		this.addChild(foodBody);
		
//		this.setExtentSwitch(false);
	}

	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		if (config_list.size() > 1) {
			this.setConfiguration(config_list.get(0));
		}
	}
	
	
	@Override
	public void animationUpdate(GL2 gl) {
		
		Point3D pos = this.position();
		
		// make food drop to bottom of vivarium when added
		if (pos.y()-this.scale <= -2) {
			this.setPosition(new Point3D(pos.x(), pos.y(), pos.z()));
			return;
		}
				
		this.setPosition(new Point3D(pos.x(), pos.y()-rotateSpeed, pos.z()));
	}
	
}
