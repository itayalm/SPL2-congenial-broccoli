package bgu.spl.mics.application;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MissionInfo;
import bgu.spl.mics.application.passiveObjects.Squad;
import bgu.spl.mics.application.publishers.TimeService;
import bgu.spl.mics.application.subscribers.Intelligence;
import bgu.spl.mics.application.subscribers.M;
import bgu.spl.mics.application.subscribers.Moneypenny;
import bgu.spl.mics.application.subscribers.Q;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/** This is the Main class of the application. You should parse the input file,
 * create the different instances of the objects, and run the system.
 * In the end, you should output serialized objects.
 */
public class MI6Runner {
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try (Reader reader = new FileReader("/home/rusty/OneDrive/BGU Synced Library/BGU Semester 3/SPL/homework2/SPL2-congenial-broccoli/src/main/java/bgu/spl/mics/application/input201[3].json")) {

            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            System.out.println(jsonObject);

            //setting up of the inventory
            List<String> gadgets= new LinkedList<String>();
            // loop array
            JSONArray msg = (JSONArray) jsonObject.get("inventory");
            Iterator<String> iterator = msg.iterator();
            while (iterator.hasNext()) {
                gadgets.add(iterator.next());
            }
            Inventory inv = Inventory.getInstance();
            String[] gadgetArray = new String[gadgets.size()];
            inv.load(gadgets.toArray(gadgetArray));

            //setting up of the squad.
            List<Agent> agents = new LinkedList<Agent>();
            JSONArray msg2 = (JSONArray) jsonObject.get("squad");
            Iterator<JSONObject> iterator2 = msg2.iterator();
            while (iterator2.hasNext()) {
                JSONObject obj = iterator2.next();
                Agent ag = new Agent();
                ag.setName((String) obj.get("name"));
                ag.setSerialNumber((String) obj.get("serialNumber"));
                agents.add(ag);
            }

            Squad sq = Squad.getInstance();
            Agent[] agentsArray = new Agent[agents.size()];
            sq.load(agents.toArray(agentsArray));

            //setting up of M;
            JSONObject services = (JSONObject) jsonObject.get("services");
            long num_of_Ms = (Long) services.get("M");
            Thread[] ms = new Thread[(int)num_of_Ms];
            for(int i=0; i<num_of_Ms; i++){
                M m = new M("M- "+i);
                ms[i] = new Thread(m);
                ms[i].start();
            }
            //setting up MoneyPennies
            long num_of_MoneyPenny = (Long) services.get("Moneypenny");
            Thread[] mnpns = new Thread[(int) num_of_MoneyPenny];
            for(int i=0; i<num_of_MoneyPenny; i++){
                Moneypenny mnpn = new Moneypenny("MNPN- "+i);
                mnpns[i] = new Thread(mnpn);
                mnpns[i].start();
            }


            //setting up of intellgence;

            JSONArray intelArray = (JSONArray) services.get("intelligence");
            Thread[] intels = new Thread[intelArray.size()];
            for(int i=0; i<intelArray.size(); i++){
                JSONObject object = (JSONObject) intelArray.get(i);
                JSONArray missions = (JSONArray) object.get("missions");
//                System.out.println(missions.toJSONString());
                Intelligence intel = new Intelligence("Intel- "+i);
                MissionInfo[] missionInfos = new MissionInfo[missions.size()];
                for(int j=0; j<missions.size(); j++){
                    missionInfos[j] = new MissionInfo();
                    JSONObject mission_json = (JSONObject) missions.get(j);
//                    System.out.println(mission_json.get("name"));
                    missionInfos[j].setMissionName((String) mission_json.get("name"));
                    JSONArray serial_agent_numbers_json =(JSONArray) mission_json.get("serialAgentsNumbers");
                    List<String> serial_agent_numbers = new LinkedList<String>();
                    for(int k=0; k<serial_agent_numbers_json.size(); k++){
                        serial_agent_numbers.add((String) serial_agent_numbers_json.get(k));
                    }
                    missionInfos[j].setSerialAgentsNumbers(serial_agent_numbers);
                    missionInfos[j].setGadget((String) mission_json.get("gadget"));
                    missionInfos[j].setTimeIssued(((Long) mission_json.get("timeIssued")).intValue());
                    missionInfos[j].setTimeExpired(((Long) mission_json.get("timeExpired")).intValue());
                    missionInfos[j].setDuration(((Long) mission_json.get("duration")).intValue());
                    intel.addMissionInfo(missionInfos[j]);
                }
                intels[i] = new Thread(intel);
                intels[i].start();
            }
            TimeService t = new TimeService("Time-Service- 1", ((Long)services.get("time")).intValue());
            //setting up of Q;
            Q q = new Q("Q- 1");
            Thread qt = new Thread(q);
            qt.start();

//            Thread inteleThread = new Thread(intel);



            System.out.println("baba bubu");

        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
