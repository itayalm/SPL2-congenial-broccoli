package bgu.spl.mics;

import bgu.spl.mics.application.passiveObjects.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InventoryTest {
    Inventory inv;
    @BeforeEach
    public void setUp(){
        inv = new Inventory();
    }
    @Test
    public void testGetItem(){
        inv.load(new String[]{"yoyo","yoyo2"});
        assertTrue(inv.getItem("yoyo"));
        assertFalse(inv.getItem("yoyo"));
        assertTrue(inv.getItem("yoyo2"));
        assertFalse(inv.getItem("yoyo2"));
    }

    @Test
    public void testLoad(){
        inv.load(new String[]{"yoyo","yoyo2"});
        assertTrue(inv.getItem("yoyo"));
        assertFalse(inv.getItem("yoyo"));
        assertTrue(inv.getItem("yoyo2"));
        assertFalse(inv.getItem("yoyo2"));
    }
}
