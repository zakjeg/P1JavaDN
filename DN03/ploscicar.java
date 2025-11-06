import java.util.*;
import java.math.*;
public class ploscicar{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int h = sc.nextInt();int w=sc.nextInt();int k=sc.nextInt();
		int ploscina = h*w;
		int ploscice = 0;

		while(Math.pow(2,k)>h || Math.pow(2,h)>w){
			k--;
		}

		while(w>0){

			while(Math.pow(2,k)>h || Math.pow(2,h)>w){
        	                k--;
               		}

			int visina=0; int sirina=0;
			while(visina<=h){
				visina+=((h/Math.pow(2,k))*Math.pow(2,k));
				
				k--
			}
			for(int i=k; i>-1;i--){
				int ploscica=Math.pow(2,i);
				
			}
		}

		System.out.println(h + " " + w + " " + k);
	}
}
