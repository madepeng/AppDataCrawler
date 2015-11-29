package com.crawler.test;

public class TestCatch {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//try catch在for循环里面
		int i = 0;
			for (i = 2; i >=-3; i--) {
				try {
					int n = 10/i;
					System.out.println(n);
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("exception");
				}
			}
	}

}
