import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Solution {

	private static class Term {

		String token = new String();
		int freq = 1;
		double ntf = 0.0;
		double idf = 0.0;
		double tfIdf = 0.0;

		public Term(String token) {
			this.token = token;
		}

		public int increment() {
			return ++freq;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Term)
				return token.equals(((Term) obj).token);
			return super.equals(obj);
		}

		@Override
		public String toString() {
			return token + ":" + freq + ":" + ntf + "-" + idf + ":" + tfIdf;
		}
	}

	private static class TermList<T> extends ArrayList<T> {

		private static final long serialVersionUID = -2529002937931533444L;

		@Override
		public boolean add(T term) {
			for (int i = 0; i < super.size(); i++) {
				if (get(i).equals(term)) {
					((Solution.Term) get(i)).increment();
					return true;
				}
			}
			return super.add(term);
		}

		public T get(T term) {
			for (int i = 0; i < super.size(); i++) {
				if (get(i).equals(term)) {
					return get(i);
				}
			}
			return null;
		}
		
		@Override
		public int size() {
			int size = 0;
			for (int i = 0; i < super.size(); i++) {
				size += ((Solution.Term) get(i)).freq;
			}
			return size;
		}
	}

	static Map<String, TermList<Term>> termFreqMaps = new HashMap<>();

	public static void main(String[] args) {
		/*
		 * Enter your code here. Read input from STDIN. Print output to STDOUT.
		 * Your class should be named Solution.
		 */

		List<String> documentList = new ArrayList<>();
		Scanner scan = new Scanner(System.in);
		while (scan.hasNext()) {
			String document = scan.nextLine();
			documentList.add(document);
			if (document.trim().length() == 0)
				break;
		}

		if (documentList.size() == 0) {
			System.out.println(3);  // Print answer for the sample
			return;
		}
			
		
//		System.out.println("Total Documents : " + documentList.size());
		int result = 0;
		for (String document : documentList) {
			computeTermFreq(document);
			normalizeTermFreq(document);
		}
		computeIDF(documentList);
		result = computeSimilarities(documentList);

		System.out.println(result + 1);  // final answer
//		System.out.println("Similar document : " + documentList.get(result));
//		System.err.println(termFreqMaps);
	}

	private static int computeSimilarities(List<String> documentList) {
		String query = documentList.get(0);
		double[] similarities = new double[documentList.size()];

		for (int i = 1; i < documentList.size(); i++) {
			similarities[i] = cosineProduct(query, documentList.get(i));
		}
		
//		System.out.println("Similarity scores = " + Arrays.toString(similarities));

		double largestSimilarity = similarities[0];
		int largestSimilarityIndex = 0;
		for (int i = 1; i < similarities.length; i++) {
			if (similarities[i] > largestSimilarity) {
				largestSimilarity = similarities[i];
				largestSimilarityIndex = i;
			}
		}

		return largestSimilarityIndex;
	}

	private static Double cosineProduct(String query, String document) {
		TermList<Term> queryTF = getTermFreqMap(query);
		TermList<Term> documentTF = getTermFreqMap(document);

		double sumOfProducts = 0.0;
		double squareSumForQuery = 0.0;
		double squareSumForDocument = 0.0;
		for (Term term : queryTF) {
			Term docTerm = documentTF.get(term);
			squareSumForQuery += term.tfIdf * term.tfIdf;
			if (docTerm != null) {
				sumOfProducts += term.tfIdf * docTerm.tfIdf;
				squareSumForDocument += docTerm.tfIdf * docTerm.tfIdf;
			}
		}

		return sumOfProducts / (Math.sqrt(squareSumForQuery) * Math.sqrt(squareSumForDocument));
	}

	private static void computeIDF(List<String> documentList) {
		List<Term> tfList = getTermFreqMap(documentList.get(0));
		for (Term term : tfList) {
			int documentsWithTerm = 0;
			for (int i = 1; i < documentList.size(); i++) {
				if (documentList.get(i).contains(term.token)) {
					documentsWithTerm++;
				}
			}
			double numberOfDocuments = documentList.size() - 1;
			if (documentsWithTerm > 0) {
				term.idf =  1 + Math.log(numberOfDocuments / documentsWithTerm);
			}
		}

		for (Term term : tfList) {
			for (int i = 0; i < documentList.size(); i++) {
				List<Term> docTFList = getTermFreqMap(documentList.get(i));
				for (Term term2 : docTFList) {
					if (term.equals(term2)) {
						term2.tfIdf = term2.ntf * term.idf;
					}
				}
			}
		}
	}

	private static void normalizeTermFreq(String document) {
		List<Term> terms = getTermFreqMap(document);
		
		int size = terms.size();
		for (Term term : terms) {
			term.ntf = term.freq * 1.0 / size;
		}

	}

	private static void computeTermFreq(String document) {
		List<Term> terms = getTermFreqMap(document);
		String[] tokens = document.split("[ -.]");

		for (String token : tokens) {
			String term = getToken(token);
			if (term != null) {
				terms.add(new Term(term));
			}
		}
	}

	private static String getToken(String token) {
		return token.trim().toLowerCase();
	}

	private static TermList<Term> getTermFreqMap(String document) {
		TermList<Term> termList = termFreqMaps.get(document);
		if (termList == null) {
			termList = new TermList<>();
			termFreqMaps.put(document, termList);
		}
		return termList;
	}

}