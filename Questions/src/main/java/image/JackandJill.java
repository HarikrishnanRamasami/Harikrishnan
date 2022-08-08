package image;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class JackandJill {
	public static void main(String[] args) throws Exception {
		String str = "jack @amp; jill went up the @amp hill... ";
		ByteArrayInputStream bis = new ByteArrayInputStream(str.getBytes());

		BufferedReader br = new BufferedReader(new InputStreamReader(bis));

		int n = 0;

		boolean mark = false;
		while ((n = br.read()) != -1) {
			char c = (char) n;
			switch (c) {
			case '@': {
				br.mark(32);
				mark = true;
				break;
			}
			case ';': {
				System.out.print("&");
				mark = false;
			}
			case ' ': {
				if (mark) {
					br.reset();
				} else {
					System.out.print(" ");
				}
				break;
			}
			default: {
				if (!mark) {
					System.out.print(c);
				}
			}
			}
		}

	}
}

