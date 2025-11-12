import java.util.*;
public class DN04_63250113{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int[] arr = new int[sc.nextInt()];
		int lucky=sc.nextInt();

		for(int i=0; i<arr.length; i++)arr[i]=sc.nextInt();

		slowSolution(arr,lucky);
		
	}

	public static void slowSolution(int[] arr, int lucky){
		long sum=0;

		for(int i=0;i<arr.length;i++)
			for(int j=0; j<arr.length; j++)
				if((arr[i]+arr[j])==lucky)sum++;

		System.out.println(sum);
	
	}
}
