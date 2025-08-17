import java.util.*;

public class Solution {
    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};
        int[] res = maxSubArray(nums); // {maxsum, start, end}
        int maxsum = res[0], start = res[1], end = res[2];

        System.out.println("Max sum: " + maxsum);
        System.out.print("Subarray: ");
        for (int i = start; i <= end; i++) {
            System.out.print(nums[i] + (i < end ? " " : ""));
        }
        System.out.println();
    }

    // returns {maxsum, start, end}
    public static int[] maxSubArray(int[] nums) {
        int maxsum = Integer.MIN_VALUE;
        int cursum = 0;
        int start = 0, end = 0, tempStart = 0;

        for (int i = 0; i < nums.length; i++) {
            cursum += nums[i];

            if (cursum > maxsum) {
                maxsum = cursum;
                start = tempStart;
                end = i;
            }
            if (cursum < 0) {
                cursum = 0;
                tempStart = i + 1;
            }
        }
        return new int[]{maxsum, start, end};
    }
}

//If you truly want to return the subarray values, you can build and return Arrays.copyOfRange(nums, start, end + 1).