import java.util.*;
public class DN04_63250113{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int[] arr = new int[sc.nextInt()];
		int lucky=sc.nextInt();

		for(int i=0; i<arr.length; i++)arr[i]=sc.nextInt();

		slowSolution(arr,lucky);
		
		//sejk(arr,lucky);

		
	}

	public static void slowSolution(int[] arr, int lucky){
		long sum=0;

		for(int i=0;i<arr.length;i++)
			for(int j=0; j<arr.length; j++)
				if((arr[i]+arr[j])==lucky)sum++;

		System.out.println(sum);
	
	}

	public static void sejk(int[] arr, int lucky){
	
		HashMap<Integer, Long> map = new HashMap<Integer, Long>();
		long sum=0; 
		long curr=0;
		for(int i=0;i<arr.length;i++){
			curr=0;
			if(map.containsKey(arr[i])){
				sum+=map.get(arr[i]);
			}
			else{
			for(int j=0; j<arr.length; j++)
				if((arr[i]+arr[j])==lucky)curr++;

				map.put(arr[i], curr);
			}

			sum+=curr;
		}

		System.out.println(sum);
	}
}
