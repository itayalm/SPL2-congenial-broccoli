package bgu.spl.mics;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FutureTest {
    Future<String> fts;
    @BeforeEach
    public void setUp(){
        fts = new Future<String>();
    }

    @Test
    public void testGet(){
        fts.resolve("yoyo");
        assertEquals("yoyo",fts.get());

    }
    @Test
    public void testResolve(){
        fts.resolve("yoyo");
        assertEquals("yoyo",fts.get());
        assertNotEquals("yoya", fts.get());
        fts.resolve("yiyi");
        assertEquals("yiyi", fts.get());

    }
    @Test
    public void testIsDone(){
        assertFalse(fts.isDone());
        fts.resolve("yoyo");
        assertTrue(fts.isDone());
    }




}
