public class Palindrome {

	public static Deque<Character> wordToDeque(String word) {
		ArrayDeque<Character> d1 = new ArrayDeque<Character>();
		for(int i = 0, n = word.length() ; i < n ; i++) { 
    		char c = word.charAt(i);
    		d1.addLast(c);
		}
		return d1;
	}
	private static boolean helper(Deque<Character> d) {
		if (d.size() == 0 || d.size() == 1) {
			return true;
		}
		if (d.removeLast() == d.removeFirst()) {
			return helper(d);
		}
		else {
			return false;
		}
	}

	public static boolean isPalindrome(String word) {
		Deque<Character> d1 = wordToDeque(word);
		return helper(d1);
	}

	private static boolean cchelper(Deque<Character> d, CharacterComparator cc) {
		if (d.size() == 0 || d.size() == 1) {
			return true;
		}
		if (cc.equalChars(d.removeFirst(),d.removeLast())) {
			return cchelper(d, cc);
		}
		else {
			return false;
		}
	}

	public static boolean isPalindrome(String word, CharacterComparator cc) {
		Deque<Character> d1 = wordToDeque(word);
		return cchelper(d1, cc);
	}
}