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
 *  1. create ant model
 *  
 *  2. implement animationUpdate() to move linkage, make creature face in the direction it is moving and stay inside vivarium
 *  
 *  3. add potential function to control creature's moving direction
 *  
 */

import javax.media.opengl.*;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.*;

import com.jogamp.opengl.util.gl2.GLUT;

import java.util.*;

public class Ant extends Component implements Animate {

	// set variables for creating model and controlling movement
	private double error = 0.000000000000001;
	private double rotateSpeed1 = 1;
	private double rotateSpeed2 = 1;
	private double scale=1;
	public static final double HEAD_HEIGHT = 1.3;
	public static final double HEAD_RADIUS = 0.3;	
	public static final double BODY_HEIGHT = 0.57;
	public static final double BODY_RADIUS = 0.4;	
	public static final double DISTAL_JOINT_HEIGHT = 0.6;
	public static final double DISTAL_LEG_RADIUS = 0.03;
	public static final double MIDDLE_JOINT_HEIGHT = 0.1;
	public static final double MIDDLE_LEG_RADIUS = 0.03;
    public static final double BODY_JOINT_HEIGHT = 0.3;
    public static final double BODY_LEG_RADIUS = 0.03;
    public static final double ANTENNA_DISTAL_HEIGHT = 0.4;
    public static final double ANTENNA_DISTAL_RADIUS = 0.02;
	public static final double ANTENNA_MIDDLE_HEIGHT = 0.1;
	public static final double ANTENNA_MIDDLE_RADIUS = 0.02;
    public static final double ANTENNA_HEAD_HEIGHT = 0.5;
    public static final double ANTENNA_HEAD_RADIUS = 0.02;
    private Component antenna_distal1, antenna_distal2;
    private Random rand = new Random();
    private float vx = 0.0001f;
    private float vy = 0.0001f;
    private float vz = 0.0001f;
    private float dx = rand.nextFloat();
    private float dy = rand.nextFloat();
    private float dz = rand.nextFloat();
    private Spider spider;
    private ArrayList<Food> food = new ArrayList<Food>();
	
	public Ant(Point3D p, float scale) {
		super(new Point3D(p));
		this.scale = scale;
		
		// set intial positions for all distal joints
		Component distal1 = new Component(new Point3D(0, 0, scale*MIDDLE_JOINT_HEIGHT),
				new AntDisplayable(scale*DISTAL_LEG_RADIUS, scale*DISTAL_JOINT_HEIGHT));
		Component distal2 = new Component(new Point3D(0, 0, scale*MIDDLE_JOINT_HEIGHT),
				new AntDisplayable(scale*DISTAL_LEG_RADIUS, scale*DISTAL_JOINT_HEIGHT));
		Component distal3 = new Component(new Point3D(0, 0, scale*MIDDLE_JOINT_HEIGHT),
				new AntDisplayable(scale*DISTAL_LEG_RADIUS, scale*DISTAL_JOINT_HEIGHT));
		Component distal4 = new Component(new Point3D(0, 0, scale*MIDDLE_JOINT_HEIGHT),
				new AntDisplayable(scale*DISTAL_LEG_RADIUS, scale*DISTAL_JOINT_HEIGHT));
		Component distal5 = new Component(new Point3D(0, 0, scale*MIDDLE_JOINT_HEIGHT),
				new AntDisplayable(scale*DISTAL_LEG_RADIUS, scale*DISTAL_JOINT_HEIGHT));
		Component distal6 = new Component(new Point3D(0, 0, scale*MIDDLE_JOINT_HEIGHT),
				new AntDisplayable(scale*DISTAL_LEG_RADIUS, scale*DISTAL_JOINT_HEIGHT));
		this.antenna_distal1 = new Component(new Point3D(0, 0, scale*ANTENNA_MIDDLE_HEIGHT),
				new AntDisplayable(scale*ANTENNA_DISTAL_RADIUS, scale*ANTENNA_DISTAL_HEIGHT));
		this.antenna_distal2 = new Component(new Point3D(0, 0, scale*ANTENNA_MIDDLE_HEIGHT),
				new AntDisplayable(scale*ANTENNA_DISTAL_RADIUS, scale*ANTENNA_DISTAL_HEIGHT));
		
		// set initial positions for all middle joints
		Component middle1 = new Component(new Point3D(0, 0, scale*BODY_JOINT_HEIGHT),
				new AntDisplayable(scale*MIDDLE_LEG_RADIUS, scale*MIDDLE_JOINT_HEIGHT));
		Component middle2 = new Component(new Point3D(0, 0, scale*BODY_JOINT_HEIGHT),
				new AntDisplayable(scale*MIDDLE_LEG_RADIUS, scale*MIDDLE_JOINT_HEIGHT));
		Component middle3 = new Component(new Point3D(0, 0, scale*BODY_JOINT_HEIGHT),
				new AntDisplayable(scale*MIDDLE_LEG_RADIUS, scale*MIDDLE_JOINT_HEIGHT));
		Component middle4 = new Component(new Point3D(0, 0, scale*BODY_JOINT_HEIGHT),
				new AntDisplayable(scale*MIDDLE_LEG_RADIUS, scale*MIDDLE_JOINT_HEIGHT));
		Component middle5 = new Component(new Point3D(0, 0, scale*BODY_JOINT_HEIGHT),
				new AntDisplayable(scale*MIDDLE_LEG_RADIUS, scale*MIDDLE_JOINT_HEIGHT));
		Component middle6 = new Component(new Point3D(0, 0, scale*BODY_JOINT_HEIGHT),
				new AntDisplayable(scale*MIDDLE_LEG_RADIUS, scale*MIDDLE_JOINT_HEIGHT));
		Component antenna_middle1 = new Component(new Point3D(0, 0, scale*ANTENNA_HEAD_HEIGHT),
				new AntDisplayable(scale*ANTENNA_MIDDLE_RADIUS, scale*ANTENNA_MIDDLE_HEIGHT));
		Component antenna_middle2 = new Component(new Point3D(0, 0, scale*ANTENNA_HEAD_HEIGHT),
				new AntDisplayable(scale*ANTENNA_MIDDLE_RADIUS, scale*ANTENNA_MIDDLE_HEIGHT));

		// set initial positions for all body joints
		Component body1 = new Component(new Point3D(scale*0.3, 0, 0),
				new AntDisplayable(scale*BODY_LEG_RADIUS, scale*BODY_JOINT_HEIGHT));
		Component body2 = new Component(new Point3D(-scale*0.3, 0, 0),
				new AntDisplayable(scale*BODY_LEG_RADIUS, scale*BODY_JOINT_HEIGHT));
		Component body3 = new Component(new Point3D(scale*0.3, 0, scale*0.1),
				new AntDisplayable(scale*BODY_LEG_RADIUS, scale*BODY_JOINT_HEIGHT));
		Component body4 = new Component(new Point3D(-scale*0.3, 0, scale*0.1),
				new AntDisplayable(scale*BODY_LEG_RADIUS, scale*BODY_JOINT_HEIGHT));
		Component body5 = new Component(new Point3D(scale*0.3, 0, scale*0.2),
				new AntDisplayable(scale*BODY_LEG_RADIUS, scale*BODY_JOINT_HEIGHT));
		Component body6 = new Component(new Point3D(-scale*0.3, 0, scale*0.2),
				new AntDisplayable(scale*BODY_LEG_RADIUS, scale*BODY_JOINT_HEIGHT));
		Component antenna_head1 = new Component(new Point3D(scale*0.15, -scale*0.08, 0),
				new AntDisplayable(scale*ANTENNA_HEAD_RADIUS, scale*ANTENNA_HEAD_HEIGHT));
		Component antenna_head2 = new Component(new Point3D(-scale*0.15, -scale*0.08, 0),
				new AntDisplayable(scale*ANTENNA_HEAD_RADIUS, scale*ANTENNA_HEAD_HEIGHT));
		
		// set initial positions for head and body
		Component head = new Component(new Point3D(0, 0, 0), new HeadDisplayable(scale*(float)HEAD_RADIUS));
		Component body = new Component(new Point3D(0, 0, scale*BODY_HEIGHT), new BodyDisplayable(scale*(float)BODY_RADIUS));
		
		// change ant color to white
		head.setColor(new FloatColor(1f, 1f, 1f));
		body.setColor(new FloatColor(1f, 1f, 1f));
		distal1.setColor(new FloatColor(1f, 1f, 1f));
		distal2.setColor(new FloatColor(1f, 1f, 1f));
		distal3.setColor(new FloatColor(1f, 1f, 1f));
		distal4.setColor(new FloatColor(1f, 1f, 1f));
		distal5.setColor(new FloatColor(1f, 1f, 1f));
		distal6.setColor(new FloatColor(1f, 1f, 1f));
		this.antenna_distal1.setColor(new FloatColor(1f, 1f, 1f));
		this.antenna_distal2.setColor(new FloatColor(1f, 1f, 1f));
		middle1.setColor(new FloatColor(1f, 1f, 1f));
		middle2.setColor(new FloatColor(1f, 1f, 1f));
		middle3.setColor(new FloatColor(1f, 1f, 1f));
		middle4.setColor(new FloatColor(1f, 1f, 1f));
		middle5.setColor(new FloatColor(1f, 1f, 1f));
		middle6.setColor(new FloatColor(1f, 1f, 1f));
		antenna_middle1.setColor(new FloatColor(1f, 1f, 1f));
		antenna_middle2.setColor(new FloatColor(1f, 1f, 1f));
		body1.setColor(new FloatColor(1f, 1f, 1f));
		body2.setColor(new FloatColor(1f, 1f, 1f));
		body3.setColor(new FloatColor(1f, 1f, 1f));
		body4.setColor(new FloatColor(1f, 1f, 1f));
		body5.setColor(new FloatColor(1f, 1f, 1f));
		body6.setColor(new FloatColor(1f, 1f, 1f));
		antenna_head1.setColor(new FloatColor(1f, 1f, 1f));
		antenna_head2.setColor(new FloatColor(1f, 1f, 1f));
		
		// set hierarchy of each part of ant
		this.addChild(head);
		// add body and antenna as children of head
		head.addChild(body);
		head.addChildren(antenna_head1, antenna_head2);
		// add body joints as children of body
		body.addChildren(body1, body2, body3, body4, body5, body6);
				   
		// add middle joints as children of body joints
		body1.addChild(middle1);
		body2.addChild(middle2);
		body3.addChild(middle3);
		body4.addChild(middle4);
		body5.addChild(middle5);
		body6.addChild(middle6);
		// add antenna middle joints as children of antenna body joints
		antenna_head1.addChild(antenna_middle1);
		antenna_head2.addChild(antenna_middle2);
				    
		// add distal joints as children of middle joints
		middle1.addChild(distal1);
		middle2.addChild(distal2);
		middle3.addChild(distal3);
		middle4.addChild(distal4);
		middle5.addChild(distal5);
		middle6.addChild(distal6);
		// add antenna distal joints as children of antenna middle joints
		antenna_middle1.addChild(this.antenna_distal1);
	    antenna_middle2.addChild(this.antenna_distal2);

		// set initial pose of ant
		body1.rotate(Axis.Y, 130);
		body2.rotate(Axis.Y, -130);
		body3.rotate(Axis.Y, 105);
		body4.rotate(Axis.Y, -105);
		body5.rotate(Axis.Y, 85);
		body6.rotate(Axis.Y, -85);
				
		body1.rotate(Axis.Z, -50);
	    body2.rotate(Axis.Z, 50);
	    body3.rotate(Axis.Z, -10);
		body4.rotate(Axis.Z, 10);
		body5.rotate(Axis.Z, 10);
		body6.rotate(Axis.Z, -10);
				
		distal1.rotate(Axis.X, -70);
		distal2.rotate(Axis.X, -70);
		distal3.rotate(Axis.X, -70);
		distal4.rotate(Axis.X, -70);
		distal5.rotate(Axis.X, -70);
		distal6.rotate(Axis.X, -70);
				
		antenna_head1.rotate(Axis.X, 120);
		antenna_head2.rotate(Axis.X, 120);
		this.antenna_distal1.rotate(Axis.X, 20);
		this.antenna_distal2.rotate(Axis.X, 20);
		
		this.antenna_distal1.setXPositiveExtent(70);
		this.antenna_distal1.setXNegativeExtent(0);
		this.antenna_distal2.setXPositiveExtent(70);
		this.antenna_distal2.setXNegativeExtent(0);
	}
	
	// add potential function to control moving directions
	private void potentialFunction() {
		Point3D p = this.position();
		Point3D q = spider.position();
		Point3D ret = p.guassianPotential(q, -0.25f);
		dx += (float)ret.x();
		dy += (float)ret.y();
		dz += (float)ret.z();
		for (Food f: food) {
			Point3D qi = f.position();
			Point3D pf = p.guassianPotential(qi, 0.2f);
			dx += (float)pf.x();
			dy += (float)pf.y();
			dz += (float)pf.z();
		}
	}
	
	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		if (config_list.size() > 1) {
			this.setConfiguration(config_list.get(0));
		}
	}
	
	// set original orientation to its initial facing position
	// set target orientation as its next facing position
	private Quaternion orientation = new Quaternion();
	private Point3D original_orientation = new Point3D(0, 0, -1);
	private Point3D target_orientation = new Point3D(vx*dx, vy*dy, vz*dz);
	
	@Override
	public void animationUpdate(GL2 gl) {
		
		Point3D pos = this.position();
		// call potential function to change moving direction
		potentialFunction();
		
		// collision detection against wall
		if ( pos.x() - this.scale < -2 || pos.x() + this.scale > 2) {
			dx = -dx;
		}
		if ( pos.y() - this.scale < -2 || pos.y() + this.scale > 2) {
			dy = -dy;
		}
		if ( pos.z() - this.scale < -2 || pos.z() + this.scale > 2) {
			dz = -dz;
		}
		
		// set rotation matrix for facing in direction
		target_orientation = new Point3D(vx*dx, vy*dy, vz*dz).normalize();
		
		if ((Math.abs(original_orientation.x() - target_orientation.x()) >= error)
				|| (Math.abs(original_orientation.y() - target_orientation.y()) >= error)
				|| (Math.abs(original_orientation.z() - target_orientation.z()) >= error)) {
			
			Point3D rotation_matrix = original_orientation.crossProduct(target_orientation);
			double rotation_angle = Math.acos(original_orientation.dotProduct(target_orientation));
			if (Math.floor(original_orientation.dotProduct(target_orientation)) == 1) {
				rotation_angle = 0;
			}
			
			if (rotation_matrix.norm() == 0) {
				rotation_matrix = new Point3D(-original_orientation.y(), original_orientation.x(), 0);
				if (rotation_matrix.norm() == 0) {
					rotation_matrix = new Point3D(-original_orientation.z(), 0, original_orientation.x());
					if (rotation_matrix.norm() == 0) {
						rotation_matrix = new Point3D(0, original_orientation.z(), -original_orientation.y());
					}
				}
				rotation_angle = Math.PI; 
			}
					
			rotation_matrix = rotation_matrix.normalize();
			double s = Math.cos(rotation_angle/2);
			double ia = Math.sin(rotation_angle/2)*rotation_matrix.x();
			double jb = Math.sin(rotation_angle/2)*rotation_matrix.y();
			double kc = Math.sin(rotation_angle/2)*rotation_matrix.z();
			Quaternion q = new Quaternion((float)s, (float)ia, (float)jb, (float)kc);
			q.normalize();
			this.orientation = q.multiply(this.orientation);
			this.orientation.normalize();	
			this.preMatrix = this.orientation.to_matrix();
     	}
		
		original_orientation = target_orientation.normalize();
		
		// move ant's antenna
		if (this.antenna_distal1.checkRotationReachedExtent(Axis.X)) { 
			rotateSpeed1 = -rotateSpeed1;
		}
		if (this.antenna_distal2.checkRotationReachedExtent(Axis.X)) {
			rotateSpeed2 = -rotateSpeed2;
		}
		
		this.antenna_distal1.rotate(Axis.X, rotateSpeed1);
		this.antenna_distal2.rotate(Axis.X, rotateSpeed2);

		// update ant's position
		this.setPosition(new Point3D(pos.x()+vx*dx, pos.y()+vy*dy, pos.z()+vz*dz));
	}
	
	// get spider position
	public void shareSpider(Spider s) {
		this.spider = s;
	}
	
	// get food position
	public void shareFood(Food f) {
		this.food.add(f);
	}
}
