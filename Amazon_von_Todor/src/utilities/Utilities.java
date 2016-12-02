package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class Utilities {
	
	public static List<String> buildURL(String produkt, Integer seiten) throws InterruptedException, IOException{

		List<String> urlList = new ArrayList<String>();
		
		
		for(int i = 1; i <= seiten; i++){
			String string = produkt + i;
			urlList.add(string);
		}
		return urlList;
	}
	
	public static void getQuelltext(String url) throws IOException, InterruptedException {
		
		File fileQuelltext = new File("src/texts/Quelltext.txt");
		
		URL website = new URL("" + url + "");
		InputStreamReader isr = new InputStreamReader(website.openStream());
		BufferedReader br = new BufferedReader(isr);
		
		FileWriter out = new FileWriter(fileQuelltext, true);
		BufferedWriter bw = new BufferedWriter(out);
		
		String inputLine;
		while ((inputLine = br.readLine()) != null) {
			if (!inputLine.isEmpty()) {
				bw.append(inputLine)
				.append("\n");
			}
		}
		br.close();
		bw.close();
	}
	
	public static void wordOccurencies() throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader("src/texts/reviewsList.txt"));
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		String line;
		while ((line = br.readLine()) != null) {
			Integer i = 1;
			if(!map.containsKey(line)){
				map.put(line, i);
			}
			else if(map.containsKey(line)){
//				int val = map.get(line);
//				val = val + 1;
				map.put(line, map.get(line) + 1);
				
			}
			
			i++;
		}
		System.out.println(map.size());
		for (String key : map.keySet()) {
	        System.out.println(key + " " + map.get(key) + "E");
	        
	    }
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	public static void countWords (String string2) throws IOException{
//		
//		int i = 0;
//		String line = ""; 
//		String line2 = ""; 
//		
//		BufferedReader br = new BufferedReader(new FileReader("src/texts/reviewsList.txt"));
//		    while((line = br.readLine()) != null ) {
//		    	i++;
//		    }
//		    System.out.println("Insgesamt " + i + " W��rter in der Liste");
//		
//		}
	
	
}
