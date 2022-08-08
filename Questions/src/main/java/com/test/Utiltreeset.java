package com.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class Utiltreeset implements Comparable{
public static void main(String[] args) {
	TreeSet<String> abc=new TreeSet(new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			// TODO Auto-generated method stub
			return o2.compareTo(o1);
		}

	});
	abc.add("Hari");
	abc.add("CRISTIANO");
	abc.add("messi");
	
	System.out.println(abc);
}

@Override
public int compareTo(Object o) {
	// TODO Auto-generated method stub
	return 0;
}
}
