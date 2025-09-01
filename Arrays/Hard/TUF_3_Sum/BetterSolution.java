import java.util.*;
public class BetterSolution
{
    public static List<List<Integer>> threeSum(int[] nums) {
        Set<List<Integer>> set = new HashSet<>();
        int n = nums.length;
        for(int i=0;i<n-2;i++){
            Set<Integer> h = new HashSet<>();
            for(int j=i+1;j<n;j++){
                int e = -(nums[i]+nums[j]);
                if(h.contains(e)){
                    List<Integer> temp = Arrays.asList(nums[i],nums[j],e);
                    Collections.sort(temp);
                    set.add(temp);
                }
                h.add(nums[j]);
            }
        }
        List<List<Integer>> result = new ArrayList<>();
        for(List<Integer> x:set){
            result.add(x);
        }
        
        return result;
    }
	
}