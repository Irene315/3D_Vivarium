/*
 * 
 * Name: Xinyuan Zhang
 * Class: CS480
 * 
 * Assignment 3
 * Due: 2019/11/12
 * Problem Number: 1
 * 
 * Description: 
 *  Initialize and draw leg object.
 *  
 */

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.gl2.GLUT;

public class AntDisplayable implements Displayable{

	private int callListHandle;
	private double scale, height;
	private GLUquadric qd;
	
	public AntDisplayable(final double scale, final double height) {
		this.scale = scale;
		this.height = height;
	}
	
	/*
	 * Method to be called for data retrieving
	 * 
	 * */
	@Override
	public void draw(GL2 gl) {
		gl.glCallList(this.callListHandle);
	}

	/*
	 * Initialize our example model and store it in display list
	 * 
	 * */
	@Override
	public void initialize(GL2 gl) {
		this.callListHandle = gl.glGenLists(1);
		gl.glNewList(this.callListHandle, GL2.GL_COMPILE);
		
		GLU glu = new GLU();
		this.qd = glu.gluNewQuadric();
		GLUT glut = new GLUT();

		gl.glPushMatrix();
		
		glut.glutSolidCylinder(scale, height, 36, 28);
	    
		gl.glPopMatrix();

		gl.glEndList();
	}

}