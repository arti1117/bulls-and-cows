package blog.project.bullsandcows;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BullsAndCows {
	
	private static class Information {
		private static int howmany;
		private static List<Integer> position;
		private static List<Integer> duplication;
		private static int percentages = 100;
	}
	
	private static Integer times = 0;								// Times of Asking
	private static boolean flag = true;								// Needs for start
	private static Map<Integer, List<Integer>> map =
			new HashMap<Integer, List<Integer>>();
	private static Map<Integer, List<Integer>> numbers = 
			new HashMap<Integer, List<Integer>>();
	private static Map<Integer, List<Double>> percentage =
			new HashMap<Integer, List<Double>>();
	private static Map<Integer, Information> info =
			new HashMap<Integer, Information>();
	private static Map<Integer, String> log = new HashMap<Integer, String>();
	
	public static void main(String[] args) {
		// Objective : Growth of production.
		
		// Gaming Rule
		// 1. Input 4 decimal number without duplication
		// 2. Receive Bot's answer randomly 4 decimal number without duplication
		// 3. Then decision what number accord your's or not
		// 		Judge rule
		//		1) HomeRun : Accord What you inputed
		//		2) Strike : Accurate position and number
		//		3) Ball : Have some number
		//		4) Out : There has no according number
		//		e.g. If user input 1234, then Bot says 1245
		//		as judge rule your response is 2S1B (Two Strikes One Ball)
		//	4. Restart from 1 to final HomeRun, before 9 times
		
		// point is control of input data and logic

		asking(4, 9);

	}

	// Ask number and wait for judging
	private static void asking(int size, int scope) {
		if (flag) {
			System.out.println("Let's Start Bulls and Cows!");
			System.out.println("Is it your Number?");
			fisrtNumber(size, scope);
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
		
		if(judge.equalsIgnoreCase("TRUE")) {
			System.out.println("You lose, I have your number " + map.get(times).toString() + " isn't it?");
			System.out.println("Game Over");
			scan.close();
		} else {
			judging(size, scope, judge);
		}
	}
	
	// Only for first creating number series for new
	// After change to DB with p
	private static void fisrtNumber(int size, int scope) {
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
		
		for (int i=0; i<size; i++) {
			List<Double> p = new ArrayList<Double>();
			List<Integer> n = new ArrayList<Integer>();
			for (int j=0; j<=scope; j++) {
				p.add(1.0/(scope+1));
				n.add(1);
			}
			// each position information is number
			// e.g. if percentage.get(0).get(4) is first position's number 4 percentage is that one.
			numbers.put(i,n);
			percentage.put(i,p);
		}
	}
	
	// Choosing number for asking series and number of map
	// n is depends for using 
	// e.g. if n is 3  asking for array of number series, 
	// if n is 10 is asking number of asking number.
	private static int number(int scope) {
		double x = Math.random();
		int result = (int)Math.ceil(x*scope);
		return result;
	}
	
	private static void judging(int size, int scope, String judge) {
		
		log.put(times, judge);	
		
		if (judge.isEmpty()) {
			System.out.println("Please answer something.");
			log.remove(times);
			asking(size, scope);
		}
		
		switch (judge) {
		case "1S" :
			secondNumber(size, scope, 1, 0);
			break;
		case "2S" :
			secondNumber(size, scope, 2, 0);
			break;
		case "3S" :
			secondNumber(size, scope, 3, 0);
			break;
		case "1B" :
			secondNumber(size, scope, 0, 1);
			break;
		case "2B" :
			secondNumber(size, scope, 0, 2);
			break;
		case "3B" :
			secondNumber(size, scope, 0, 3);
			break;
		case "4B" :
			secondNumber(size, scope, 0, 4);
			break;
		case "1S1B" :
			secondNumber(size, scope, 1, 1);
			break;
		case "1S2B" :
			secondNumber(size, scope, 1, 2);
			break;
		case "1S3B" :
			secondNumber(size, scope, 1, 3);
			break;
		case "2S1B" :
			secondNumber(size, scope, 2, 1);
			break;
		case "2S2B" :
			secondNumber(size, scope, 2, 2);
			break;
		case "OUT" :
			secondNumber(size, scope, 0, 0);
			break;
		default :
			log.remove(times);
			System.out.println("----------------------------");
			System.out.println("Your answer should be below");
			System.out.println("1S, 2S, 3S, 1B, 2B, 3B, 4B");
			System.out.println("1S1B, 1S2B, 1S3B");
			System.out.println("2S1B, 2S2B");
			System.out.println("----------------------------");
			asking(size, scope);
			break;
		}	
	}
	
	private static void secondNumber(int size, int scope, int strike, int ball) {
		
		int r = number(size);
		int s = number(scope);
		List<Integer> list = new ArrayList<Integer>();
		
		if (strike == 0 && ball == 0) {

			for (int i=0;i<size;i++) {
				List<Double> p = new ArrayList<Double>();
				List<Integer> n = new ArrayList<Integer>();
				for (int j=0;j<=scope;j++) {
					if (map.get(times).get(i).equals(j)) {
						p.add(0.0);
						n.add(0);
					} else {
						p.add(map.get(times).get(i) + (1.0/6));
						n.add(1);
					}
				}
			}
			
		} else if (strike == 0 && ball == 1) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() < ball && !list.contains(s) && map.get(times).contains(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 0 && ball == 2) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= ball && !list.contains(s) && map.get(times).contains(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 0 && ball == 3) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= ball && !list.contains(s) && map.get(times).contains(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 0 && ball == 4) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && map.get(times).contains(s) && !map.get(times).get(list.size()).equals(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 1 && ball == 0) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= strike && !list.contains(s) && map.get(times).contains(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 1 && ball == 1) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= strike + ball && !list.contains(s) && map.get(times).contains(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 1 && ball == 2) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= strike + ball && !list.contains(s) && map.get(times).contains(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 1 && ball == 3) {

			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= strike + ball && !list.contains(s) && map.get(times).contains(s) && !map.get(times).get(list.size()).equals(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}

		} else if (strike == 2 && ball == 0) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= strike && !list.contains(s) && map.get(times).contains(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 2 && ball == 1) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= strike + ball && !list.contains(s) && map.get(times).contains(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 2 && ball == 2) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= strike + ball && !list.contains(s) && map.get(times).contains(s)) {
						if (map.get(times).get(list.size()).equals(s) && list.size() < strike) {
							list.add(s);
						} else {
							list.add(s);
						}
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		} else if (strike == 3 && ball == 0) {
			
			if (times == 0) {
				while (list.size() < size) {
					if (!list.contains(s) && !map.get(times).contains(s)) {
						list.add(s);
					} else if (list.size() <= strike && !list.contains(s) && map.get(times).contains(s) && map.get(times).get(list.size()).equals(s)) {
						list.add(s);
					}
					if (list.size() == size) {
						break;
					}
					s = number(scope);
				}
			} else {
				
			}
			
		}
		
		// making next map
		times++;
		/*
		List<Integer> m = new ArrayList<Integer>();
		for (int i=0; i<size; i++) {
			
			int num = 0;
			int count = 0;
			
			for (int j=0; j<=scope; j++) {
				if (numbers.get(i).get(j).equals(1)) { count++; }
			}
			
			for (int k=0; k<=scope; k++) {
				if (count <= size) {
					for (int l=k+1; l<=scope; l++) {
						if (percentage.get(i).get(k) < percentage.get(i).get(l)) {
							num = l;
						} else if (percentage.get(i).get(k) < percentage.get(i).get(l)) {
							num = k;
						} else {
							if (r <= (size/2)) {
								num = l;
							} else {
								num = k;
							}
						}
					}
				} else {
					
				}
			}
			m.add(num);
		}
		map.put(times,m);
		*/
		map.put(times,list);
		asking(size, scope);
	}

}
