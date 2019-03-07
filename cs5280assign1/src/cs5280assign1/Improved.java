package cs5280assign1;

public class Improved {
    private int[] ogList;
    
    public Improved(int[] list){
        ogList = list;
    }
    
    private void insertionSort(){
        int size = ogList.length;
        for(int i = 1; i< size; ++i){
            int j =i;
            while(ogList[j] < ogList[j-1]){
                //swap values
                int temp = ogList[j];
                ogList[j] = ogList[--j];
                ogList[j] = temp;
                if(j<=0) break;
            }
        }
    }
    
    public int improvement(){
        insertionSort();
        int count = 0;
        
        int firstPos=0;
        for(int i = 0; i < ogList.length; ++i){
            if(ogList[i] >= 0){
                firstPos = i;
                break;
            }
        }
        
        //get first two integers
        //limit i to negatives since no sum of 4 positive integers will equal 0
        for(int i = 0; i< firstPos; ++i){
            for(int j =i+1; j< ogList.length-2; ++j){
                //search remaining integers for a target sum
                int k = j+1;
                int m = ogList.length -1;
                //no need to limit m to only positives since it's unlikely to occur
                while(m > k){
                    if(ogList[i]+ogList[j]+ogList[m]+ogList[k] > 0){
                        --m;
                    }
                    else if(ogList[i]+ogList[j]+ogList[m]+ogList[k]<0){
                        ++k;
                    }
                    else{
                        ++count;
                        //no other int can pair with either of these since all are unique
                        ++k; --m;
                    }
                }
            }
        }
        return count;
    }
}