package upandgo.shared.utils;

/**
 * 
 * @author danabra
 * @since 20-06-17
 * 
 * A Class for calculating similarity between two strings (each string can contains more than one word)
 * 
 */

public class FuzzySearch {
	
	public static int similarity(String s1, String s2) {
		String[] words1 = s1.split(" ");
		String[] words2 = s2.split(" ");
		int similarity=0;
		int max_similarity_per_word = 0;
		for(int i = 0 ; i < words1.length; ++i){
			for(int j = 0; j < words2.length; ++j){
				int tmp_sim = similarity_aux(words1[i], words2[j]);
				if(max_similarity_per_word < tmp_sim)
					max_similarity_per_word = tmp_sim;
			}
			similarity+= (1/(double)words1.length)*max_similarity_per_word;
			max_similarity_per_word=0;
		}
		return similarity;
		
	}
	private static int similarity_aux(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) {
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		return longerLength == 0 ? 100
				: (int) (100 * (longerLength - computeLevenshteinDistance(longer, shorter)) / (double) longerLength);
	}
	
// this is a java implementation for calculating Levenshtein Distance between two strings. 
//code is taken from https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
	 
	private static int minimum(int a, int b, int c) {                            
	      return Math.min(Math.min(a, b), c);                                      
	  }                                                                            
	                                                                          
	  private static int computeLevenshteinDistance(CharSequence lhs, CharSequence rhs) {      
	        int[][] distance = new int[lhs.length() + 1][rhs.length() + 1];        
	                                                                                 
	        for (int i = 0; i <= lhs.length(); ++i)                                 
	            distance[i][0] = i;                                                  
	        for (int j = 1; j <= rhs.length(); ++j)                                 
	            distance[0][j] = j;                                                  
	                                                                                 
	        for (int i = 1; i <= lhs.length(); ++i)                                 
	            for (int j = 1; j <= rhs.length(); ++j)                             
	                distance[i][j] = minimum(                                        
	                        distance[i - 1][j] + 1,                                  
	                        distance[i][j - 1] + 1,                                  
	                        distance[i - 1][j - 1] + ((lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1));
	                                                                                 
	        return distance[lhs.length()][rhs.length()];                           
	    }                                                                            
	}
