/*
 * 
 * Name: Xinyuan Zhang
 * Class: CS480
 * 
 * Assignment 3
 * Due: 2019/11/12
 * Problem Number: /
 * 
 * Description:  
 *  File given for selecting and controlling objects
 *  
 */

/**
 * An interface ask object to response to select control
 * 
 * @author Zezhou Sun <micou@bu.edu>
 * @since Fall 2019
 */

public interface Selection {
	
	void toggleSelection(int componentNum);
	
	void changeSelected(Configuration configuration);
}
