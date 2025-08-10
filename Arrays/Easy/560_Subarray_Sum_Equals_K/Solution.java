import java.util.*;
public class Solution
{
	public static void main(String[] args) {
		int nums[] = {1, 2, 3, -2, 1};
		int k =3;
		System.out.println(longestSubarray(nums,k));
		}

		public static int longestSubarray(int[] nums, int k) {
        int sum = 0,count =0;
        HashMap<Integer,Integer> h = new HashMap<>();
        h.put(0,1);
        for(int i=0;i<nums.length;i++){
            sum+=nums[i];
            int needed = sum-k;
            if(h.containsKey(needed)){
                count+=h.get(needed);
            }
            h.put(sum,h.getOrDefault(sum,0)+1);
        }
        return count;
		}
}
