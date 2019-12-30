package bgu.spl.mics.application.passiveObjects;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Passive data-object representing a information about an agent in MI6.
 * You must not alter any of the given public methods of this class. 
 * <p>
 * You may add ONLY private fields and methods to this class.
 */
public class Squad {

	private static Squad squad_singleton = new Squad();


	private Map<String, Agent> agents;

	private Squad(){
		agents = new HashMap<>();
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Squad getInstance() {
		if(squad_singleton == null){
			squad_singleton = new Squad();
		}
		return squad_singleton;
	}

	/**
	 * Initializes the squad. This method adds all the agents to the squad.
	 * <p>
	 * @param agents 	Data structure containing all data necessary for initialization
	 * 						of the squad.
	 */
	public void load (Agent[] agents) {//CMNT
		for(Agent ag: agents){
			this.agents.put(ag.getSerialNumber(),ag);
		}
	}

	/**
	 * Releases agents.
	 */
	public void releaseAgents(List<String> serials){
		for(String ser: serials){
			if (agents.get(ser) != null)
				this.agents.get(ser).release();
		}
	}

	/**
	 * simulates executing a mission by calling sleep.
	 * @param time   time ticks to sleep
	 */
	public void sendAgents(List<String> serials, int time){
		try
		{
			Thread.sleep(time*100);
		}
		catch (InterruptedException e){}
		releaseAgents(serials);
	}

	/**
	 * acquires an agent, i.e. holdys the agent until the caller is done with it
	 * @param serials   the serial numbers of the agents
	 * @return ‘false’ if an agent of serialNumber ‘serial’ is missing, and ‘true’ otherwise
	 */
	public synchronized boolean getAgents(List<String> serials){
		Agent currAgent;
		for(String ser: serials){
			currAgent = this.agents.get(ser);
			if(currAgent == null){
//				System.out.println("wrong serial");
				return false;

			}
			currAgent.acquire();
//			System.out.println("acquire agents");
		}
		return true;
	}

    /**
     * gets the agents names
     * @param serials the serial numbers of the agents
     * @return a list of the names of the agents with the specified serials.
     */
    public List<String> getAgentsNames(List<String> serials){
        List<String> namesList = new LinkedList<String>();
        for(String ser: serials){
        	if (agents.get(ser) != null)
        		namesList.add(this.agents.get(ser).getName());
		}
        return namesList;
    }

}
