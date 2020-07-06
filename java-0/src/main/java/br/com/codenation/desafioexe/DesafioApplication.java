package br.com.codenation.desafioexe;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DesafioApplication {

	private static final int MAX_NUMBER = 350;
	private static ArrayList<Integer> listFibonacci;

	public static List<Integer> fibonacci() {
		List<Integer> listFibonacci = getListFibonacciUntil(MAX_NUMBER);
		return listFibonacci;
	}

	private static List<Integer> getListFibonacciUntil(Integer maxNumber){
		listFibonacci = new ArrayList<Integer>();
		//Definition
 		if( 0 <= maxNumber) {
			listFibonacci.add(0);
			if( 1 <= maxNumber) {
				listFibonacci.add(1);
				if(1 != maxNumber) {
					do {
						Integer nextFibonacci =  listFibonacci.get(listFibonacci.size() - 1) + listFibonacci.get(listFibonacci.size() - 2);
						listFibonacci.add(nextFibonacci);
					} while (listFibonacci.get(listFibonacci.size() - 1) <= maxNumber);
				}
			}
		}
		return listFibonacci;
	}

	public static Boolean isFibonacci(Integer value) {
		Boolean isFibonacci = getListFibonacciUntil(value).contains(value);
		return isFibonacci;
	}


}