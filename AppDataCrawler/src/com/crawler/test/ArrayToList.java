package com.crawler.test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			Integer[] num = {1,4,5,67,7,8,6};
			//System.out.println(num.length);
			//System.out.println(num);
			List<Integer> list = new ArrayList<Integer>(Arrays.asList(num));
			for (Integer integer : list) {
				//System.out.println(integer);
			}
			Integer[] nums = {-10,10,101,102,104,106,112,110,115,119,111,107,118,108,100,114,117,109,105,113,116,122};
			List<Integer> list1 = new ArrayList<Integer>(Arrays.asList(nums));
			//System.out.println(list1.);
	}

}
