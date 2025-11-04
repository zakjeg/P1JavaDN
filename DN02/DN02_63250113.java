import java.util.Scanner;
import java.math.*;

public class DN02_63250113{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int b = sc.nextInt();
		int d = sc.nextInt();
		int n = sc.nextInt();
		int[] arr = new int[n];
	
		for(int i=0; i<n; i++)arr[i]=sc.nextInt();

		switch(b){
			case 1:
				System.out.println(ravnovrstnica(d,arr));
				break;
			case 2: 
				System.out.println(kvadratnica(d,arr));
				break;
			case 3: 
				System.out.println(piramidnica(d,arr));
				break;
			case 4: 
				System.out.println(spiralnica(d,arr));
				break;
		}
	}

	public static int ravnovrstnica(int d, int[] arr){
		
		int sum=0;

		for(int i=0; i<arr.length-1; i++){
			sum+=Math.abs(arr[i+1]-arr[i]);
		}

		return sum;
	}

        public static int kvadratnica(int d, int[] arr){

		int sum=0; 

		for(int i=0; i<arr.length-1; i++){
			int low=arr[i]; int high=arr[i+1];
			if(low>high){int temp = low; low = high; high = temp;}

			sum+=Math.abs(((high/d)-(low/d)))+Math.abs(((high%d)-(low%d)));
		}

		return sum;
        }

        public static int piramidnica(int d, int[] arr){
	
		int sum=0;

		//Kodrinatni sistem kjer je ničla x osi v sredini piramide (jo "prepeže na pol"). Premik v Y pa je razlika med stoplci.
		int y1=-1;int x1=-1;
		int y2=-1;int x2=-1;

		for(int i=0; i<arr.length-1; i++){
			y1=(int)Math.floor(Math.sqrt(arr[i]));
			y2=(int)Math.floor(Math.sqrt(arr[i+1]));
			x1=arr[i]-((y1+1)*(y1+1)-1-y1);
			x2=arr[i+1]-((y2+1)*(y2+1)-1-y2);
			sum+=(Math.abs(x2-x1)+Math.abs(y2-y1));
		}
		return sum;
        }

	public static int spiralnica(int d, int[] arr){

		//a-"kolobar" prve številke, b-druge
		int a = 0;
		int b = 0;
		int sum=0;

		//kordinatni sistem z srediscem v 0 (sredini).
		int y1=-1;int x1=-1;
		int y2=-1;int x2=-1;

		for(int i=0; i<arr.length-1; i++){
			a = (int)Math.floor(Math.sqrt(arr[i]));
			b = (int)Math.floor(Math.sqrt(arr[i+1]));
			if(a%2==0)a--;
			if(b%2==0)b--;
			a=(a+1)/2;
		       	b=(b+1)/2;

			y1=+a;x1=-a;
			y2=+b;x2=-b;

			int premik1= -((int) Math.pow(((a==0)?0:((2*a)-1)),2) - arr[i]);
			int premik2= -((int) Math.pow(((b==0)?0:((2*b)-1)),2) - arr[i+1]);

			//PREMIK "NAPREJ" PO IZBRANEM KOLOBARJU REDA A/B
			int[] arr1=premik(a, premik1);
			int[] arr2=premik(b, premik2);

			//VNOS PREMIKOV KI SO PODANI V ARR-J
			x1+=arr1[0];y1+=arr1[1];
			x2+=arr2[0];y2+=arr2[1];

			sum+=(Math.abs(y2-y1)+Math.abs(x2-x1));
		}

		return sum;
	}

	public static int[] premik(int a, int premik){
		
		int x=0;
		int y=0;
		int[] arr = new int[2];

		switch((int) Math.floor(((premik-1)/(2*a)))){
			case 0: x+=premik;
				break;
			case 1: x+=(2*a);
				y-=(premik-(2*a));
				break;
			case 2: y-=(2*a);
				x+=((6*a)-premik);
				break;
			case 3: y-=((8*a)-premik);
		}
		arr[0]=x;arr[1]=y;
		return arr;
	}

}

