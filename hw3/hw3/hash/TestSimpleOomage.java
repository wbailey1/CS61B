package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;

public class TestSimpleOomage {

    @Test
    public void testHashCodeDeterministic() {
        SimpleOomage so = SimpleOomage.randomSimpleOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    @Test
    public void testHashCodePerfect() {
        /* TODO: Write a test that ensures the hashCode is perfect,
          meaning no two SimpleOomages should EVER have the same
          hashCode!
         */
        SimpleOomage oo1 = new SimpleOomage(5, 10, 20);
        SimpleOomage oo2 = new SimpleOomage(5, 20, 10);
        SimpleOomage oo3 = new SimpleOomage(10, 5, 20);
        SimpleOomage oo4 = new SimpleOomage(10, 20, 5);
        SimpleOomage oo5 = new SimpleOomage(20, 10, 5);
        SimpleOomage oo6 = new SimpleOomage(20, 5, 10);
        SimpleOomage ooA;
        SimpleOomage ooB;
        SimpleOomage[] list = {oo1, oo2, oo3, oo4, oo5, oo6};
        for (int i = 0; i < 5; i++) {
            ooA = list[i];
            for (int t = i + 1; t < 6; t++) {
                ooB = list[t];
                assertNotEquals(ooA.hashCode(), ooB.hashCode());
            }
        }

    }

    @Test
    public void testEquals() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        SimpleOomage ooB = new SimpleOomage(50, 50, 50);
        assertEquals(ooA, ooA2);
        assertNotEquals(ooA, ooB);
        assertNotEquals(ooA2, ooB);
        assertNotEquals(ooA, "ketchup");
    }

    /*@Test
    public void testHashCodeAndEqualsConsistency() {
        SimpleOomage ooA = new SimpleOomage(5, 10, 20);
        SimpleOomage ooA2 = new SimpleOomage(5, 10, 20);
        HashSet<SimpleOomage> hashSet = new HashSet<SimpleOomage>();
        hashSet.add(ooA);
        assertTrue(hashSet.contains(ooA2));
    }*/

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestSimpleOomage.class);
    }
}
