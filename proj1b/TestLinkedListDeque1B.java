import org.junit.Test;
import static org.junit.Assert.*;

public class TestLinkedListDeque1B {

	public int makeRandom(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}

	@Test
	public void randomTest() {
		Integer actual;
		Integer expected;
		int random;
		StudentLinkedListDeque<Integer> slld1 = new StudentLinkedListDeque<Integer>();
		LinkedListDeque<Integer> happy1 = new LinkedListDeque<Integer>();
		FailureSequence fs = new FailureSequence();
		DequeOperation dequeOp;
		for (int i = 0; i < 1000; i++) {
			random = makeRandom(0,4);
			if (random == 0) {
				slld1.addLast(i);
				happy1.addLast(i);
				dequeOp = new DequeOperation("addLast", i);
				fs.addOperation(dequeOp);
			}
			if (random == 1) {
				slld1.addFirst(i);
				happy1.addFirst(i);
				dequeOp = new DequeOperation("addFirst", i);
				fs.addOperation(dequeOp);
			}
			if (random == 2) {
				actual = slld1.removeFirst();
				expected = happy1.removeFirst();
				dequeOp = new DequeOperation("removeFirst");
				fs.addOperation(dequeOp);
				assertEquals(fs.toString(), expected, actual);
			}
			if (random == 3) {
				actual = slld1.removeLast();
				expected = happy1.removeLast();
				dequeOp = new DequeOperation("removeLast");
				fs.addOperation(dequeOp);
				assertEquals(fs.toString(), expected, actual);
			}
			if (random == 4) {
				actual = slld1.size();
				expected = slld1.size();
				dequeOp = new DequeOperation("size");
				fs.addOperation(dequeOp);
				assertEquals(fs.toString(), expected, actual);
				random = makeRandom(0, slld1.size() - 1);
				actual = slld1.get(random);
				expected = happy1.get(random);
				dequeOp = new DequeOperation("get", random);
				fs.addOperation(dequeOp);
				assertEquals(fs.toString(), expected, actual);
			}
		}
	}

	public static void main(String[] args) {
		jh61b.junit.TestRunner.runTests("all", TestLinkedListDeque1B.class);
	}
}