class Solution {
    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer,Integer> h = new HashMap<>();
        
        for(int i = 0; i < nums.length; i++) {
            int current = nums[i];
            int complement = target - current;
            
            if(h.containsKey(complement)) {
                return new int[]{ h.get(complement), i };
            }
            
            h.put(current, i);
        }
        
        return new int[0]; // if no solution (problem guarantees one)
    }
}
