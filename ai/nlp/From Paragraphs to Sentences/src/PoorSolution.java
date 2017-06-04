import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class PoorSolution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
    	
//    	Scanner scan = new Scanner(System.in);
//    	String input = scan.nextLine();
    	
    	String input = "In the third category he included those Brothers (the majority) who saw nothing in Freemasonry but the external forms and ceremonies, and prized the strict performance of these forms without troubling about their purport or significance. Such were Willarski and even the Grand Master of the principal lodge. Finally, to the fourth category also a great many Brothers belonged, particularly those who had lately joined. These according to Pierre's observations were men who had no belief in anything, nor desire for anything, but joined the Freemasons merely to associate with the wealthy young Brothers who were influential through their connections or rank, and of whom there were very many in the lodge.Pierre began to feel dissatisfied with what he was doing. Freemasonry, at any rate as he saw it here, sometimes seemed to him based merely on externals. He did not think of doubting Freemasonry itself, but suspected that Russian Masonry had taken a wrong path and deviated from its original principles. And so toward the end of the year he went abroad to be initiated into the higher secrets of the order.What is to be done in these circumstances? To favor revolutions, overthrow everything, repel force by force?No! We are very far from that. Every violent reform deserves censure, for it quite fails to remedy evil while men remain what they are, and also because wisdom needs no violence. \"But what is there in running across it like that?\" said Ilagin's groom. \"Once she had missed it and turned it away, any mongrel could take it,\" Ilagin was saying at the same time, breathless from his gallop and his excitement.";
//    	String input = "Dr. W. Watson is amazing.";
        
    	String[] result = getSentences(input.toCharArray());
    	for (String string : result) {
			System.out.println(string + "\n");
		}
    }

	private static String[] getSentences(char[] input) {
		List<String> resultList = new ArrayList<>();
		
		int start = 0;
		int end = 0;
		for (int i = 0; i < input.length; i++) {
			char c = input[i];
			if (isPeriod(c)) {
				if (!isNextAlphaCharCaps(i, input)) {
					end=i;
					resultList.add(addSentence(start, end, input));
					start = end + 1;
				}
			}
			
		}
		return resultList.toArray(new String[0]);
	}

	private static String addSentence(int start, int end, char[] input) {
		String sentence = String.valueOf(Arrays.copyOfRange(input, start, end+1));
		return sentence.trim();
	}

	private static boolean isPeriod(char c) {
		return c == '.';
	}

	private static boolean isNextAlphaCharCaps(int index, char[] input) {
		int i = index;
		while (i < input.length && !isAlphaChar(input[i])) {
			i++;
		}
		return i < input.length ? isCaps(input[i]) : false;
	}

	private static boolean isCaps(char c) {
		return c >= 'A' && c <= 'Z';
	}

	private static boolean isAlphaChar(char c) {
		return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
	}
}