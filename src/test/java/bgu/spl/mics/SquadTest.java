package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Agent;
import bgu.spl.mics.application.passiveObjects.Squad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SquadTest {
    Agent[] agents;
    Squad sq;
    @BeforeEach
    public void setUp(){
        agents = new Agent[3];

        agents[0] = new Agent();
        agents[0].setSerialNumber("007");
        agents[0].setName("James Bond");
        agents[1] = new Agent();
        agents[1].setSerialNumber("002");
        agents[1].setName("Bill Fairbanks");
        agents[2] = new Agent();
        agents[2].setSerialNumber("0012");
        agents[2].setName("Sam Johnston");
        sq = new Squad();

    }

    @Test
    public void testGetAgentsNames(){
        sq.load(agents);
        List<String> input = new ArrayList<String>();
        input.add("007");
        List<String> names = sq.getAgentsNames(input);
        assertEquals("James Bond", names.get(0));
        input.remove("007");
        input.add("002");
        names = sq.getAgentsNames(input);
        assertNotEquals("James Bond", names.get(0));

        input.add("007");
        names = sq.getAgentsNames();
        assertEquals("Bill Fairbanks", names.get(0));
        assertEquals("James Bond", names.get(1));
    }

    @Test
    public void testLoad(){
     sq.load(agents);
     assertTrue(sq.getAgents());
     agents[1].setSerialNumber("");
     assertFalse(sq.getAgents());
    }
}
