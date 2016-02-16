import org.junit.Test;
import static org.junit.Assert.*;

public class TestArrayDeque1B {

	public int makeRandom(int min, int max) {
		return min + (int)(Math.random() * ((max - min) + 1));
	}

	@Test
	public void randomTest() {
		Integer actual;
		Integer expected;
		int random;
		StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<Integer>();
		ArrayDeque<Integer> happy1 = new ArrayDeque<Integer>();
		FailureSequence fs = new FailureSequence();
		DequeOperation dequeOp;
		for (int i = 0; i < 1000; i++) {
			random = makeRandom(0,4);
			if (random == 0) {
				sad1.addLast(i);
				happy1.addLast(i);
				dequeOp = new DequeOperation("addLast", i);
				fs.addOperation(dequeOp);
			}
			if (random == 1) {
				sad1.addFirst(i);
				happy1.addFirst(i);
				dequeOp = new DequeOperation("addFirst", i);
				fs.addOperation(dequeOp);
			}
			if (random == 2) {
				actual = sad1.removeFirst();
				expected = happy1.removeFirst();
				dequeOp = new DequeOperation("removeFirst");
				fs.addOperation(dequeOp);
				assertEquals(fs.toString(), expected, actual);
			}
			if (random == 3) {
				actual = sad1.removeLast();
				expected = happy1.removeLast();
				dequeOp = new DequeOperation("removeLast");
				fs.addOperation(dequeOp);
				assertEquals(fs.toString(), expected, actual);
			}
			if (random == 4) {
				actual = sad1.size();
				expected = sad1.size();
				dequeOp = new DequeOperation("size");
				fs.addOperation(dequeOp);
				assertEquals(fs.toString(), expected, actual);
				random = makeRandom(0, sad1.size() - 1);
				actual = sad1.get(random);
				expected = happy1.get(random);
				dequeOp = new DequeOperation("get", random);
				fs.addOperation(dequeOp);
				assertEquals(fs.toString(), expected, actual);
			}
		}
	}

	public static void main(String[] args) {
		jh61b.junit.TestRunner.runTests("all", TestArrayDeque1B.class);
	}
}