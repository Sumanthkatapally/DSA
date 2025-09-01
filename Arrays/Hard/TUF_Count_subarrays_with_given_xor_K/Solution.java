public class Solution{ 
    public static int countSubarraysWithXorK(int[] nums, int target) {
        HashMap<Integer,Integer> h = new HashMap<>();
        h.put(0,1);
        int n = nums.length;
        int prefixZor = 0,count=0;
        for(int i=0;i<n;i++){
            prefixZor^=nums[i];
            if(h.containsKey(prefixZor^target)){
                count+=h.get(prefixZor^target);
            }
            h.put(prefixZor,h.getOrDefault(prefixZor,0)+1);
        }
        return count;
    }
}