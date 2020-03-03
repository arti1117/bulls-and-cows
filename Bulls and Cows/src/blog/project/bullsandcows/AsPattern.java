package blog.project.bullsandcows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AsPattern {
	
	private static boolean flag = true;
	private static Integer times = 0;
	private static Map<Integer, List<Integer>> map =
			new HashMap<Integer, List<Integer>>();
	private static List<ArrayList<Integer>> gross =
			new ArrayList<ArrayList<Integer>>();
	
	private static int number(int scope) {
		double x = Math.random();
		int result = (int)Math.ceil(x*scope);
		return result;
	}
	
	public static void main(String[] args) {
		asking(4,9);
	}
	
	public static void asking(int size, int scope) {
		if (flag) {
			System.out.println("Let's Start Bulls and Cows!");
			System.out.println("Is it your Number?");
			start(size, scope);
			flag = false;
		} else {
			System.out.println("Try again, is it your Number?");
		}
		
		if (map.containsKey(times)) {
			System.out.println(map.get(times).toString());
		} else {
			System.out.println("Something is wrong. Try again.");
			System.out.println("Game Over");
			return;
		}
		
		Scanner scan = new Scanner(System.in);
		String judge = scan.nextLine().toUpperCase().trim();
		String[] sql = judge.split("");
		
		if(judge.equalsIgnoreCase("TRUE")) {
			System.out.println("You lose, I have your number " + map.get(times).toString() + " isn't it?");
			System.out.println("Game Over");
			scan.close();
		} else {
			search(size, scope, judging(sql));
		}
	}
	
	private static void start(int size, int scope) {
		int x = number(scope);
		List<Integer> list = new ArrayList<Integer>();
		while (list.size() < size) {
			if (!list.contains(x)) {
				list.add(x);
			}
			if (list.size() == size) {
				map.put(times, list);
			}
			x = number(scope);
		}
	
	}
	
	public static int[] judging(String[] sql) {
		int[] answer = {Integer.parseInt(sql[0]), Integer.parseInt(sql[3])};
		return answer;
	}
	
	private static List<ArrayList<Integer>> create(int size, int scope) {
		List<ArrayList<Integer>> load = new ArrayList<ArrayList<Integer>>();
		int x;
		long limit = factorial(size, scope);
		ArrayList<Integer> list = null;
		while(load.size() < limit) {
			list = new ArrayList<Integer>();
			while (list.size() < size) {
				x = number(scope);
				if (!list.contains(x)) {
					list.add(x);
				}
				if (list.size() == size && !load.contains(list)) {
					load.add(list);
					break;
				} else if (load.contains(list)) {
					break;
				}
			}
		}
		
		return load;
	}
	
	private static long factorial (int size, int scope) {
		long x = 1;
		int j = scope;
		for (int i=0;i<size;i++	) {
			x = x*j;
			j--;
		}
		return x;
	}
	
	private static void search(int size, int scope, int[] answer) {
		
		int x = answer[0];
		int y = answer[1];
		List<Integer> element = new ArrayList<Integer>();
		for (int i=0; i<size; i++) {
			element.add(map.get(times).get(i));
		}
		
		for (int i=0; i<gross.size();i++) {
			if (x == 0 && y == 0) {
				for (int j=0;j<size;j++	) {
					if (gross.get(i).contains(element.get(j))) {
						gross.remove(i);
					}
				}
			} else if ( x==0 && y!=0) {
				for (int j=0; j<y; j++) {
					for (int k=0; k<size;k++) {
						if (!gross.get(i).contains(element.get(k))) {
							gross.remove(i);
						}
					}
				}
			} else if ( x!=0 && y==0) {
				for (int j=0; j<x; j++) {
					for (int k=0; k<size; k++) {
						if (gross.get(i).get(k) != element.get(k)) {
							gross.remove(i);
						}
					}
				}
			} else {
				
			}
		}
		times++;
		asking(size,scope);
	}
}
