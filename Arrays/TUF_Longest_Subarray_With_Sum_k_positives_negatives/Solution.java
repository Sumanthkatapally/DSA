import java.util.*;
public class Solution
{
	public static void main(String[] args) {
		int nums[] = {2, 3, 2, -2, 0, 1, 1, 1, 1, 1, 2};
		int k =7;
		System.out.println(longestSubarray(nums,k));
		}

		public static int longestSubarray(int[] nums, int k) {
			int sum=0;
			int max =0;
            HashMap<Integer,Integer> h = new HashMap<>();
            for(int i=0;i<nums.length;i++){
                sum+=nums[i];
                if(h.containsKey(sum-k)){
                    max = Math.max(max,i-h.get(sum-k));
                }
                if (sum==k){
                    max = Math.max(max,i+1);
                }
                h.putIfAbsent(sum,i);
            }
            return max;
		}
}
