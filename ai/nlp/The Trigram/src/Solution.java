import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
	
	private static class Trigram {
		private String trigram;
		private long frequency;
		private long firstOccurance;
		
		public Trigram(String trigram, long firstOccurance) {
			this.trigram = trigram;
			this.firstOccurance = firstOccurance;
			frequency = 0;
		}

		public long getFrequency() {
			return frequency;
		}

		public void incFrequency() {
			frequency++;
		}
		
		@Override
		public String toString() {
			return trigram + ":" + frequency + ":" + firstOccurance;
		}
	}
	
	private static PriorityQueue<Trigram> pq = new PriorityQueue<>(3, new Comparator<Trigram>() {

		@Override
		public int compare(Trigram o1, Trigram o2) {
			if (o1.getFrequency() > o2.getFrequency()) {
				return -1;
			} else if (o1.getFrequency() == o2.getFrequency()) {
				if (o1.firstOccurance < o2.firstOccurance) {
					return -1;
				}
			}
			return 1;
		}
	});
	
	private static Map<String, Trigram> trigramMap = new HashMap<>();
	
	private static long firstOccuranceCounter = 0;

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    	
    	Scanner tokenizer = new Scanner(System.in);
    	
    	Deque<String> queue = new ArrayDeque<>(3);
    	while (tokenizer.hasNext()) {
    		String token = tokenizer.next();
    		token = token.trim();
    		boolean endOfSentence = false;
    		if (token.endsWith(".")) {
    			token = token.substring(0, token.length()-1);
    			endOfSentence = true;
    		}
    		queue.offer(token);
    		
    		processQueue(queue, endOfSentence);
    	}
    	System.out.println(pq.peek().trigram);
    }

	private static void processQueue(Deque<String> queue, boolean endOfSentence) {
		if (queue.size() < 3) {
			return;
		}
		
		Trigram trigram = getTrigram(queue);
		
		if (pq.contains(trigram)) {
			pq.remove(trigram);
		}
		pq.offer(trigram);
		
		if (queue.size() == 3) {
			queue.remove();
		}
		
		if (endOfSentence) {
			queue.clear();
		}
		
	}

	private static Trigram getTrigram(Deque<String> queue) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iter = queue.iterator();
		while (iter.hasNext()) {
			String string = (String) iter.next();
			sb.append(string);
			sb.append(" ");
		}
		String trigramString = sb.substring(0, sb.length()-1).trim().toLowerCase();
		Trigram trigram = trigramMap.get(trigramString);
		if (trigram == null) {
			trigram = new Trigram(trigramString, firstOccuranceCounter++);
			trigramMap.put(trigramString, trigram);
		}
		trigram.incFrequency();
		return trigram;
	}
}