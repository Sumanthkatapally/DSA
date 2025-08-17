class Solution {
    public int longestConsecutive(int[] nums) {
        int n = nums.length;
        Set<Integer> hashSet = new HashSet<>(n*2);

        if (n==0) return 0;

        for(int i=0;i<n;i++) hashSet.add(nums[i]);

        int max = 0;
        for(Integer x:hashSet){
            int c = x;
            if(!hashSet.contains(c-1)){
                int cnt=1;
                while(hashSet.contains(c+1)){
                    cnt++;
                    c++;
                }
                max = Math.max(max,cnt);
            }
        }
        return max;
    }
}