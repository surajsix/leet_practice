import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] nums1 = null;
        int[] nums2 = null;

        // Input for the first array
        while (nums1 == null) {
            try {
                System.out.print("Enter the size of the first array: ");
                int size1 = sc.nextInt();
                nums1 = new int[size1];
                System.out.println("Enter the elements of the first array:");
                for (int i = 0; i < size1; i++) {
                    nums1[i] = sc.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter integers only.");
                sc.next(); // Clear the invalid input
            }
        }

        // Input for the second array
        while (nums2 == null) {
            try {
                System.out.print("Enter the size of the second array: ");
                int size2 = sc.nextInt();
                nums2 = new int[size2];
                System.out.println("Enter the elements of the second array:");
                for (int i = 0; i < size2; i++) {
                    nums2[i] = sc.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter integers only.");
                sc.next(); // Clear the invalid input
            }
        }

        // Finding and printing the median
        Solution solution = new Solution();
        double median = solution.findMedianSortedArrays(nums1, nums2);
        System.out.println("The median is: " + median);
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int[] merged = new int[n + m];
        int k = 0;

        // Copy all elements from nums1 to merged array
        for (int i = 0; i < n; i++) {
            merged[k++] = nums1[i];
        }

        // Copy all elements from nums2 to merged array
        for (int j = 0; j < m; j++) {
            merged[k++] = nums2[j];
        }

        // Sort the merged array
        Arrays.sort(merged);

        // Find the median
        int total = n + m;
        if (total % 2 == 1) {
            // If odd, return the middle element
            return merged[total / 2];
        } else {
            // If even, return the average of the two middle elements
            int middle1 = merged[total / 2 - 1];
            int middle2 = merged[total / 2];
            return ((double) middle1 + (double) middle2) / 2.0;
        }
    }
}
