import java.util.*;

class Solution {
    public List<Integer> leaders(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int n = nums.length;
        if (n == 0) return list;

        int rightMax = nums[n - 1];
        list.add(rightMax); // rightmost is always a leader

        for (int i = n - 2; i >= 0; i--) {
            if (nums[i] > rightMax) {
                rightMax = nums[i];
                list.add(nums[i]);
            }
        }

        Collections.reverse(list); // restore left-to-right order
        return list;
    }
}
