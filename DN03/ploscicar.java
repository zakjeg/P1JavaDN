import java.util.*;
import java.math.*;
public class ploscicar{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int h = sc.nextInt();int w=sc.nextInt();int k=sc.nextInt();
		long sum=0;

		int visinaStolpca=0;
		int sirinaStolpca=0;
		long steviloPloscic=0;
		while((w)>0){
			
			int n=najvecjiK(h,w,k);
			int naN=(int)Math.pow(2,n);
			sirinaStolpca=naN;

			while(visinaStolpca<h){

				steviloPloscic+=((h-visinaStolpca)/naN)*(sirinaStolpca/naN);
				visinaStolpca+=((h-visinaStolpca)/naN)*naN;

				naN=(int)Math.pow(2,--n);

			}
			
			sum+=steviloPloscic*(w/sirinaStolpca);
			w-=(w/sirinaStolpca)*sirinaStolpca;
			visinaStolpca=0;
			steviloPloscic=0;

		}

		System.out.println(sum);

	}

	public static int najvecjiK(int h, int w, int k){

		int size = (int)Math.pow(2,k);
		while(size>h || size>w){
		k--;
		size=(int)Math.pow(2,k);
		}


		return k;
	}
	

	public static int stPloscic(int a){
		return -1;
	} 
}
