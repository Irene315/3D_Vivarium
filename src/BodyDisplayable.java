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
 *  Initialize and draw body object.
 *  
 */

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.gl2.GLUT;

public class BodyDisplayable implements Displayable{

	private int callListHandle;
	private float scale;
	private GLUquadric qd;
	
	public BodyDisplayable(final float scale) {
		this.scale = scale;
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
		
		gl.glScalef(0.9f, 0.5f, 1);
		glut.glutSolidSphere(scale, 36, 18);
	    
		gl.glPopMatrix();

		gl.glEndList();
	}

}
