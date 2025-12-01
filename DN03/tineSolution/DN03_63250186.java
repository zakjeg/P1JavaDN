import java.util.*;
import java.math.*;

public class DN03_63250186 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
	    int h = sc.nextInt();
		int w = sc.nextInt();
		int k = sc.nextInt();
        sc.close();
        long count = 0;
        int lh = h;
        int lw = 0;
        long cpc; //count per count 
        //System.out.println(" = " + );

        if (h == 0 || w == 0) System.out.println(0);
       
       else {
           
           int kc = maxk(h,w,k); //kc = k count
           int wperk = (1 << kc);
                           
            cpc = countPerMaxk(lh,w,kc);

            count = cpc * (w / wperk);
            lw = (w % wperk);
           // konc 1 pohoda,
           
           while(lw>0){
                kc = maxk(h,lw,k);
                wperk = (1 << kc);
                cpc = countPerMaxk(lh,lw,kc);
                count += cpc;
                lw = (w % wperk);
            }
            System.out.println(count);
}
}

    public static int maxk(int h, int w, int k){
        int size = (1 << k);
        while(size > h || size > w){
            k--;    
            size = (1 << k);
            }
        return k;

        }

    public static long countPerMaxk(int lh, int w, int kc){
        int size = (1 << kc);
        long cpc = 0;
        int wperk = size;
        while(lh>0){
            size = (1 << kc);
            cpc += ((lh/size)*(wperk/size));
            lh = (lh%size);
            kc--;
            }

            return cpc; 
        }



}
