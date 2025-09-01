class Solution {
    public static List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<Integer>();
        int n = nums.length;
        if(n==0) return res;
        int max = n/3;
        int e1=Integer.MIN_VALUE,e2=Integer.MIN_VALUE;
        int c1=0,c2=0;

        for(int i=0;i<n;i++){
            if(c1==0 && nums[i]!=e2){
                e1=nums[i];
                c1++;
            }
            else if (c2==0 && nums[i]!=e1){
                e2=nums[i];
                c2++;
            }
            else if(nums[i]==e1){
                c1++;
            }
            else if(nums[i]==e2){
                c2++;
            }
            else{
                c1--;
                c2--;
            }
        }
        c1=0;c2=0;
        for(int i=0;i<n;i++){
            if(nums[i]==e1) c1++;
            else if(nums[i]==e2)    c2++;
        }

        if(c1>max) res.add(e1);
        if(c2>max) res.add(e2);
        return res;
    }
}