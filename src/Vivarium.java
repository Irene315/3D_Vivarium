/*
 * 
 * Name: Xinyuan Zhang
 * Class: CS480
 * 
 * Assignment 3
 * Due: 2019/11/12
 * Problem Number: 2, 4, 5
 * 
 * Description:  
 *  1. add and render models of creature and food to vivarium
 *  
 *  2. share positions among objects
 *  
 *  3. delete object models from vivarium when there is collision
 *  
 */

import javax.media.opengl.*;
import com.jogamp.opengl.util.*;
import java.util.*;

public class Vivarium implements Displayable, Animate {
	private Tank tank;
	
	// initialize vivarium and food list to store objects
	public ArrayList<Component> vivarium = new ArrayList<Component>();
	public ArrayList<Food> food = new ArrayList<Food>();
	private ArrayList<Food> to_remove = new ArrayList<Food>();
	Ant ant = new Ant(new Point3D(0, 0, 0), 0.3f);
	Spider spider = new Spider(new Point3D(-1, 0, 0), 0.5f);

	public Vivarium() {
		tank = new Tank(4.0f, 4.0f, 4.0f);
		
		// add creature models to vivarium statically
		vivarium.add(ant);
		vivarium.add(spider);
		
		food.add(new Food(new Point3D(0, 1, 0), 0.08f));
	}
	
	// add food models to vivarium dynamically
	public void addFood() {
		Food newFood = new Food(new Point3D(Math.random()*4-2, 1.5, Math.random()*4-2), 0.08f);
		food.add(newFood);
	}
	
	public void initialize(GL2 gl) {
		tank.initialize(gl);
		for (Component object : vivarium) {
			object.initialize(gl);
		}
		// share creature positions
		if (vivarium.size() == 2) {
			ant.shareSpider(spider);
			spider.shareAnt(ant);
		}
	}

	public void update(GL2 gl) {
		
		// detect collision between creature and food
		// when collide, food gets eaten
		for (Component bug : vivarium) {
			for (Food object : food) {
				if (bug.position().distance(object.position()) < 0.5) {
					to_remove.add(object);
				}
			}	
		}	
		
		// detect collision between spider and ant
		// when collide, ant gets eaten
		if (vivarium.size() == 2) {
			if (vivarium.get(0).position().distance(vivarium.get(1).position()) < 0.3) {
			vivarium.remove(0);
			}
		}
		
		tank.update(gl);
		for (Component bug : vivarium) {
			bug.update(gl);
		}
		
		for (Food f: to_remove) {
			food.remove(f);
		}
		
		// share food position with creatures
		for (Food object : food) {
			object.initialize(gl);
			if (vivarium.size() == 2) {
				ant.shareFood(object);
			}
			spider.shareFood(object);
		}
		
		for (Food object : food) {
			object.update(gl);
		}	
	}

	public void draw(GL2 gl) {
		tank.draw(gl);
		for (Component object : vivarium) {
			object.draw(gl);
		}
		for (Food object : food) {
			object.draw(gl);
		}
	}

	@Override
	public void setModelStates(ArrayList<Configuration> config_list) {
		// assign configurations in config_list to all Components in here
	}

	@Override
	public void animationUpdate(GL2 gl) {
		for (Component example : vivarium) {
			if (example instanceof Animate) {
				((Animate) example).animationUpdate(gl);
			}
		}
		for (Food example : food) {
			if (example instanceof Animate) {
				((Animate) example).animationUpdate(gl);
			}
		}
	}
}
