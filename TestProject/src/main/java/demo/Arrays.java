
package demo;
//https://fluvid.com/videos/detail/-KRkYhqqxxtBpjBaR#.YnypwYcmA6E.link
public class Arrays {
	public static void main(String[] args) {
	/*	int[] arr= {20,30,40,50,60};
		
		for(int i=0;i<arr.length;i++)
		{
			System.out.print(arr[i]+"\t");
			}
		System.out.println();
		
		for(int n:arr)
		{
			System.out.print(n+"\t");
		}
		System.out.println("\n\n");*/
		
		int [][] ar= {{10,20,30,40,50,60} ,
				{100,200,300,400,500,600} , 
				{1000,2000,3000,4000,5000,6000}
		
		};
		
		
		for(int i=0;i<ar.length;i++) {
			for(int j=0;j<ar[i].length;j++) {
				//System.out.print(ar[i][j]+"\t");
				
		}
		System.out.println();
		}
		
		for(int m[]:ar) {
			for(int n:m) {
			System.out.print(n+"\t");
			
		}
			System.out.println();
	
	}
	}
}




