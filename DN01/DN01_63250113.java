import java.util.*;

public class DN01_63250113{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int a = sc.nextInt(); int b = sc.nextInt();
		if(a>b){int c = b;b=a;a=c;} 
		int sum=0;
		for(int i=1; i<a; i++)sum+=(a-i)*(b-i);
		System.out.println(sum);
	}
}
