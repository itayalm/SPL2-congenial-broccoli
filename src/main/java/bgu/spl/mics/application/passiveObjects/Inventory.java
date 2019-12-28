package bgu.spl.mics.application.passiveObjects;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  That's where Q holds his gadget (e.g. an explosive pen was used in GoldenEye, a geiger counter in Dr. No, etc).
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Inventory {
	private static Inventory inventory_singleton = new Inventory();


	private List<String> gadgets;

	private Inventory(){
		gadgets = new LinkedList<String>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Inventory getInstance() {
		if(inventory_singleton == null){
			inventory_singleton = new Inventory();
		}
		return inventory_singleton;
	}

	/**
	 * Initializes the inventory. This method adds all the items given to the gadget
	 * inventory.
	 * <p>
	 * @param inventory 	Data structure containing all data necessary for initialization
	 * 						of the inventory.
	 */
	public void load (String[] inventory) {//CMNT not thread safe probz.
		for(String s: inventory){
			gadgets.add(s);
		}
	}

	/**
	 * acquires a gadget and returns 'true' if it exists.
	 * <p>
	 * @param gadget 		Name of the gadget to check if available
	 * @return 	‘false’ if the gadget is missing, and ‘true’ otherwise
	 */
	public boolean getItem(String gadget){

			for (String s : gadgets) {
				if (s == gadget) {
					gadgets.remove(s);
					return true;
				}
			}
			return false;

	}

	/**
	 *
	 * <p>
	 * Prints to a file name @filename a serialized object List<String> which is a
	 * list of all the of the gadgeds.
	 * This method is called by the main method in order to generate the output.
	 */
	public void printToFile(String filename){
		//TODO: Implement this
	}
}
