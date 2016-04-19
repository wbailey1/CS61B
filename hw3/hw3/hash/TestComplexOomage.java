package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.StdRandom;


public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    public boolean haveNiceHashCodeSpread(Set<ComplexOomage> oomages) {
        /* TODO: Write a utility function that ensures that the oomages have
         * hashCodes that would distribute them fairly evenly across
         * buckets To do this, mod each's hashCode by M = 10,
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int hashCode;
        int bucket;
        int[] count = new int[10];

        for (Oomage o : oomages) {
            hashCode = o.hashCode();
            bucket = (hashCode & 0x7FFFFFFF) % 10;
            count[bucket] = count[bucket] + 1;
        }
        int min = count[0];
        for (int i = 1; i < 10; i++) {
            if (count[i] < min) {
                min = count[i];
            }
        }
        int max = count[0];
        for (int i = 1; i < 10; i++) {
            if (count[i] > max) {
                max = count[i];
            }
        }
        if (max < 4000 && min > 200) {
            return true;
        }
        return false;
    }


    @Test
    public void testRandomItemsHashCodeSpread() {
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    @Test
    public void testWithDeadlyParams() {
        /* TODO: Create a Set that shows the flaw in the hashCode function.
         */
        HashSet<ComplexOomage> oomages = new HashSet<ComplexOomage>();
        int N = 10000;
        ComplexOomage o;
        ArrayList<Integer> list;
        for (int i = 0; i < N; i += 1) {
            list = new ArrayList<Integer>(9);
            for (int n = 0; n < 9; n++) {
                list.add(StdRandom.uniform(0,255));
            }
            o = new ComplexOomage(list);
            oomages.add(o);
        }

        assertTrue(haveNiceHashCodeSpread(oomages));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
