package upandgo.shared.utils;

import org.apache.commons.lang3.StringUtils;

public class FuzzySearch {
	private FuzzySearch(){
		
	}
	
	
	public static int similarity(String s1, String s2) {
		String[] words1 = s1.split(" ");
		String[] words2 = s2.split(" ");
		int similarity=0;
		int max_similarity_per_word = 0;
		for(int i = 0 ; i < words1.length; i++){
			for(int j = 0; j < words2.length; j++){
				int tmp_sim = similarity_aux(words1[i], words2[j]);
				if(max_similarity_per_word < tmp_sim){
					max_similarity_per_word = tmp_sim;
				}
			}
			similarity+= (1/(double)words1.length)*max_similarity_per_word;
			max_similarity_per_word=0;
		}
		return similarity;
		
	}
	private static int similarity_aux(String s1, String s2) {
	    String longer = s1, shorter = s2;
	    if (s1.length() < s2.length()) { // longer should always have greater length
	      longer = s2; shorter = s1;
	    }
	    int longerLength = longer.length();
	    if (longerLength == 0) { 
	    	return 100;
	    }
	    return (int) (((longerLength - StringUtils.getLevenshteinDistance(longer, shorter)) / (double) longerLength)*100);

	  }

}
