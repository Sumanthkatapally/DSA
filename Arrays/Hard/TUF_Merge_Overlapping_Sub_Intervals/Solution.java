public Class Solution{
    public static List<List<Integer>> mergeOverlap(List<List<Integer>> intervals) {
        intervals.sort(Comparator.comparingInt(l->l.get(0)));
        List<List<Integer>> result = new ArrayList<>();
        int f=intervals.get(0).get(0),l=intervals.get(0).get(1);
        for(int i=1;i<intervals.size();i++){
            Integer cf = intervals.get(i).get(0);
            Integer cl = intervals.get(i).get(1);

            if(l>=cf)   l=Math.max(l,cl);
            else{
                result.add(Arrays.asList(f,l));
                f=cf;
                l=cl;
            }
        }
        result.add(Arrays.asList(f,l));
        return result;
    }
}