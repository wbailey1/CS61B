public class OffByN implements CharacterComparator {

	private int offBy;

	public OffByN(int N) {
		offBy = N;
	}

	@Override
	public boolean equalChars(char x, char y) {
		if (x + offBy == y || x - offBy == y) {
			return true;
		}
		else {
			return false;
		}
	}
}