class Solution {
    public boolean isMatch(String text, String pattern) {
        int m = text.length();
        int n = pattern.length();
        
        // Create a DP table
        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        
        // Handle patterns like "a*", "a*b*" which can match empty text
        for (int j = 1; j <= n; j++) {
            if (pattern.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // Fill the DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (pattern.charAt(j - 1) == text.charAt(i - 1) || pattern.charAt(j - 1) == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                } else if (pattern.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2] || (dp[i - 1][j] && (text.charAt(i - 1) == pattern.charAt(j - 2) || pattern.charAt(j - 2) == '.'));
                }
            }
        }
        
        return dp[m][n];
    }
}
