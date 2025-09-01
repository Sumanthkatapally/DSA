public class Solution{ 
 public static int longestSubarrayWithSumZero(int[] nums) {
		target = 0;// Target always zero
        HashMap<Integer,Integer> h = new HashMap<>();
        int maxLength = 0;
        int n = nums.length;
        int sum = 0;
        for(int i=0;i<n;i++){
            sum+=nums[i];
            if(sum==target){
                maxLength = i-0;
            }
            else if(h.containsKey(sum-target)){
                maxLength=Math.max(maxLength,i-h.get(sum-target));
            }
            if(!h.containsKey(sum)) h.put(sum,i);
        }
        return maxLength;
    }
}