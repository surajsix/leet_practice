import java.util.*;

class Solution {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() == 0 || words == null || words.length == 0) {
            return result;
        }

        int wordCount = words.length;
        int wordLength = words[0].length();
        int substringLength = wordCount * wordLength;
        if (s.length() < substringLength) {
            return result;
        }

        // Create a frequency map for words
        Map<String, Integer> wordMap = new HashMap<>();
        for (String word : words) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }

        // Iterate over every possible starting index
        for (int i = 0; i < wordLength; i++) {
            Map<String, Integer> currentMap = new HashMap<>();
            int count = 0;
            for (int j = i; j <= s.length() - wordLength; j += wordLength) {
                String word = s.substring(j, j + wordLength);
                if (wordMap.containsKey(word)) {
                    currentMap.put(word, currentMap.getOrDefault(word, 0) + 1);
                    count++;

                    // If there are more words than needed, adjust window
                    while (currentMap.get(word) > wordMap.get(word)) {
                        String leftWord = s.substring(j - (count - 1) * wordLength, j - (count - 2) * wordLength);
                        currentMap.put(leftWord, currentMap.get(leftWord) - 1);
                        count--;
                    }

                    // Check if all words are matched
                    if (count == wordCount) {
                        result.add(j - (wordCount - 1) * wordLength);
                    }
                } else {
                    // Reset the window if the word is not part of the target words
                    currentMap.clear();
                    count = 0;
                }
            }
        }

        return result;
    }
}
