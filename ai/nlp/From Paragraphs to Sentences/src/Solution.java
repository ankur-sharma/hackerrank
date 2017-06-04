import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

	public static void main(String[] args) {

    	Scanner scan = new Scanner(System.in);
    	String input = scan.nextLine();
		
		String[] result = getSentences(input);
    	print(result);
	}

	private static void print(String[] result) {
		if (result == null) return;
		for (String string : result) {
			System.out.println(string);
		}
	}

	private static String[] getSentences(String input) {
		String[] words = input.split(" ");
		
		List<String> wordList = preProcessWords(words);
		List<String> sentence = new ArrayList<>();
		List<String> sentences = new ArrayList<>();
		int pendingQuote = 0;
		for (String word : wordList) {
			pendingQuote += getQuotes(word);
			sentence.add(word);
			if (endsWithPunctuation(word) && pendingQuote % 2 == 0) {
				sentences.add(getSentence(sentence));
				sentence = new ArrayList<>();
			}
		}
		sentences.add(getSentence(sentence));
		return sentences.toArray(new String[0]);
	}

	private static List<String> preProcessWords(String[] words) {
		List<String> wordList = new ArrayList<>();
		for (String word : words) {
			String[] smallerWords = word.split("(?<=[\\.?!])");
			for (String smallWord : smallerWords) {
				wordList.add(smallWord);
			}
		}
		return wordList;
	}

	private static int getQuotes(String word) {
		int quotes = 0;
		for (char c : word.toCharArray()) {
			if (c == '"')
				quotes++;
		}
		return quotes;
	}

	private static String getSentence(List<String> sentence) {
		if (sentence == null || sentence.size() == 0)
			return new String();
		StringBuilder sentenceBuilder = new StringBuilder();
		for (String string : sentence) {
			sentenceBuilder.append(string);
			sentenceBuilder.append(" ");
		}
		return sentenceBuilder.substring(0, sentenceBuilder.length()-1);
	}

	private static boolean endsWithPunctuation(String word) {
		return word.matches(".*[\\.?!]$") && 
				(word.length() == 1 || word.length() > 2);
	}

}
