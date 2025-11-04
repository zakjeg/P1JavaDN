import java.math.*;
import java.util.*;
public class test{
	public static void main(String[] args){
		for(int i=0; i<48;i++){
			int a = (int)Math.floor(Math.sqrt(i));
			if(a%2==0)--a;
			a=(a+1)/2;
			System.out.println("i = " + i + " => " + a);
		}
	}
}

