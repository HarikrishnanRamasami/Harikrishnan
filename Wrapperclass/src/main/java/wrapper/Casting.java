package wrapper;

public class Casting {

	public static void main(String[] args) {
		byte value=127;
		int n;
		n=value;
		
		value=(byte)n;
		System.out.println(value);
		n=600;
		
		value=(byte)n;
		System.out.println(value);
		
		char ch=72;
		n=ch;
		System.out.println(ch);
		System.out.println((char)n);

	}

}
