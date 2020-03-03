package blog.project.bullsandcows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Retry {
	
	private static int time = 0;
	private static boolean flag = true;
	private static ArrayList<Integer> must = new ArrayList<Integer>();
	private static ArrayList<Integer> cant = new ArrayList<Integer>();
	private static HashMap<Integer, ArrayList<Integer>> asking = new HashMap<Integer, ArrayList<Integer>>();
	private static HashMap<Integer, ArrayList<Integer>> answer = new HashMap<Integer, ArrayList<Integer>>();

	public static void main (String[] args) {
		baseball(4,9);
	}
	
	private static int number (int scope) {
		double x = Math.random();
		int result = (int)Math.round(x*scope);
		return result;
	}
	
	private static void baseball(int size, int scope) {
		if (flag) {
			System.out.println("Let's Start Bulls and Cows!");
			System.out.println("Is it your Number?");
			start(size, scope);
			flag = false;
		} else {
			System.out.println("Try again, is it your Number?");
		}

		ArrayList<String> list = printing(size);
		if (asking.containsKey(time)) {
			System.out.println(asking.get(time).toString());
			System.out.println("Choose one!");
			

			for (int i=0;i<list.size();i++) {
					System.out.print(i + " : " + list.get(i));
				if (i % size == 0) {
					System.out.println("");
				} else {
					if (i+1 != list.size()) {
						System.out.print(" / ");
					}
				}
			}
			System.out.println("");
			
			
		} else {
			System.out.println("Something is wrong. Try again.");
			System.out.println("Game Over");
			return;
		}
		
		Scanner scan = new Scanner(System.in);
		String judge = scan.nextLine().toUpperCase().trim();
		String[] rule = list.get(Integer.parseInt(judge)).split("");
		ArrayList<Integer> copy = new ArrayList<Integer>();
		
		if(judge.equalsIgnoreCase("0")) {
			System.out.println("You lose, I have your number " + asking.get(time) + " isn't it?");
			System.out.println("Game Over");
			scan.close();
		} else {
			copy.add(Integer.parseInt(rule[0]));
			copy.add(Integer.parseInt(rule[2]));
			answer.put(time, copy);
			next(size, scope);
		}
	}
	
	private static ArrayList<String> printing(int size) {
		ArrayList<String> list = new ArrayList<String>();

		list.add("HOMERUN");
		for (int i=0;i<size;i++) {
			for (int j=0;j<=size;j++) {
				if (i + j <= size) {
					list.add(i + "S" + j + "B");
				}
			}
		}
		list.remove(list.size()-1);
		
		return list;
	}

	private static void start(int size, int scope) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		while (list.size() < size) {
			int x = number(scope);
			if (!list.contains(x)) {
				list.add(x);
			}
			if (list.size() == size) {
				asking.put(time, list);
			}
		}
	}
	
	private static void next(int size, int scope) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		int strike;
		int ball;
		
		strike = answer.get(time).get(0);
		ball = answer.get(time).get(1);
		
		if (strike + ball == 0) {
			cant = asking.get(time);
		} else if (strike + ball == size) {
			must = asking.get(time);
		}
		
		do {
			list = create(size, scope);
		} while (!stack(size, scope, list));
		
		time++;
		asking.put(time, list);
		baseball(size, scope);
	}
					
	private static ArrayList<Integer> create(int size, int scope){
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		while (list.size() < size) {
			int x = number(scope);
			if (!list.contains(x) && !cant.contains(x)) {
				if (must.size() == 0) {
					list.add(x);
				} else {
					if (must.contains(x)) {
						list.add(x);
					}
				}
			}
		}
		
		return list;
	}
	
	private static boolean stack(int size, int scope, ArrayList<Integer> list) {
		ArrayList<Integer> user;
		int stackunderflow = time;
		int strike;
		int ball;
		
		do {
			if (stackunderflow == -1) {
				return true;
			}
			user = asking.get(stackunderflow);
			strike = answer.get(stackunderflow).get(0);
			ball = answer.get(stackunderflow).get(1);
			stackunderflow--;
		} while(judgement(size, scope, strike, ball, user, list));

		return false;

	}
	
	private static boolean judgement (int size, int scope, int strike, int ball, ArrayList<Integer> user, ArrayList<Integer> list) {
		int bCount = 0;
		int sCount = 0;
		
		for (int i=0;i<size;i++) {
			if (user.contains(list.get(i))) {
				bCount++;
				if (user.get(i) == list.get(i)) {
					sCount++;
					bCount--;
				}
			}	
		}
		
		if (strike == sCount && ball == bCount) {
			return true;
		} else {
			return false;
		}
	}

}
