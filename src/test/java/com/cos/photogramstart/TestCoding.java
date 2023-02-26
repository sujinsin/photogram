package com.cos.photogramstart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCoding {

	@Test
	void stringdd() {
		String today = "2022.05.19";
		String comp = "2021.06.19";
		String[] terms = {"A 6", "B 12", "C 3"};
		String[] privacies = {"2021.05.02 A", "2021.07.01 B", "2022.02.19 C","2022.02.20 C"};
		//today = today.replaceAll("[.]", "");
		//System.out.println(today);
		
		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
		Calendar cld = Calendar.getInstance();


		ArrayList<HashMap<String,String>> arrList = new ArrayList<>(); 
		for(int i = 0 ; i < terms.length ; i ++) {
			HashMap<String, String> hmList = new HashMap<String,String>();
			hmList.put(terms[i].split(" ")[0],terms[i].split(" ")[1]);
			arrList.add(hmList);
		}
		
		
		
		ArrayList<HashMap<String,String>> arrList2 = new ArrayList<>(); 
		for(int i = 0 ; i < privacies.length ; i ++) {
			HashMap<String, String> hmList = new HashMap<String,String>();
			hmList.put(privacies[i].split(" ")[1],privacies[i].split(" ")[0]);
			arrList2.add(hmList);
			
		}
		System.out.println(arrList.toString());
		System.out.println(arrList2.toString());
		//arrList.add(e)
		
		for(int i = 0; i < arrList2.size(); i++){
			for(Entry<String, String> elem : arrList2.get(i).entrySet() ) {
	
				 switch (elem.getKey()) {
					case "A":
						
						
						System.out.println(elem.getValue().substring(5,7));
					    int uu1 =  Integer.parseInt(elem.getValue().substring(0,4));
					    int uu2 = Integer.parseInt(elem.getValue().substring(5,7));
					    int uu3 =  Integer.parseInt(elem.getValue().substring(8));
					    
					   int insi2 =  Integer.parseInt(arrList.get(i).get("A"));
					   int ss = uu2 + insi2;
					   if(ss > 12) {
						  ss =  ss - 12;
						  uu1 = uu1 + 1;
						  
					   }
					   
					   int to1 =  Integer.parseInt(today.substring(0,4));
					    int to2 = Integer.parseInt(today.substring(5,7));
					    int to3 =  Integer.parseInt(today.substring(8));
					    
					    System.out.println(uu1 + "." + uu2 + "." + uu3);
					    System.out.println(to1 + "." + to2 + "." + to3);
					    List<Integer> list = new ArrayList<>();
					    if(uu1 < to1) {
					    	list.add(i+1);
					    }else if(uu1==to1 && uu2 < to2) {
					    	list.add(i+1);
					    }
						System.out.println(list);
					    
						System.out.println(arrList.get(i).get("A"));
						String[] imsivalue1 = today.split(".");
						String[] imsivalue2 = elem.getValue().split(".");
//						for(int j= 0 ; j < imsivalue2.length ; j++) {
//							imsivalue1[j]
//						}
						break;
					case "B":
						break;
					case "C":
						break;
					}
			}
		}

		
	} 

}

