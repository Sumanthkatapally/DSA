import java.util.*;
public class Solution
{
	public static void main(String[] args) {
		System.out.println("To get paticular element from Pascal Triangle using ncr(n!/r!*(n-r)!) combination formuale");
		int r = 5, c = 3;
		System.out.println(getPascalValue(r-1,c-1));
		System.out.println("To get paticular row's values");
		List<Integer> res = getPascalRow(r);
		for(int x:res) System.out.print(x+" ");
		System.out.println();
		System.out.println("To Print entire row");
		List<List<Integer>> result = generate(r);
		for(List<Integer> x: result){
		    System.out.println(x);
		}
	}
	
	public static int getPascalValue(int r,int c){
	    int res = 1;
	    for(int i=0;i<c;i++){
	        res = res*(r-i);
	        res = res/(i+1);
	    }
	    return res;
	}
	
	public static List<Integer> getPascalRow(int r){
	    List<Integer> rowResult = new ArrayList<>();
	    rowResult.add(1);
	    int ans = 1;
	    for(int i=1;i<r;i++){
	        ans = ans * (r-i);
	        ans/=i;
	        rowResult.add(ans);
	    }
	    return rowResult;
	}
	
	public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        if(numRows==0){
            return result;
        }
        for(int i=1;i<=numRows;i++){
            List<Integer> curRow = getPascalRow(i);
            result.add(curRow);
        }
        return result;
    }
}
