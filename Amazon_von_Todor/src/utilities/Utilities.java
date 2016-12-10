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
import java.io.PrintWriter;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		
//		File  = new File("src/texts/Quelltext.txt");
		
//		String website = new URL("" + url + "");
		
		
		Elements el = Jsoup.connect(url)
				.header("Accept-Encoding", "gzip, deflate")
				.userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
				.timeout(600000)
				.maxBodySize(0)
				.followRedirects(true)
				.get()
				.select("div.a-section");
		
		List<String> list = new ArrayList<String>();
		
		for (Element element : el) {
			if(element.text().contains("out of") & element.text().contains("Thank you for your feedback") & !element.text().startsWith("Customer Reviews"))
			{
				String[] stars = element.text().split("[0-9].[0-9] out of [0-9] stars");
				String star = element.text().substring(0, 3);
				list.add("\n************* " + star + " ***************************");
				list.add("\n" + stars[1] + "\n");
			}
		}
		
		for (String string : list) {
			System.out.println(string);
		}
		
//		StringBuffer sb = new StringBuffer();
//		//FIXME: Hier muss der neue Code rein
//		String sourcecode = Jsoup.connect(url)
//				.header("Accept-Encoding", "gzip, deflate")
//				.userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
//				.timeout(600000)
//				.maxBodySize(0)
//				.followRedirects(true)
//				.get().html();
//		
//		System.out.println(sourcecode);
		
//		Url.reviewTextToFile(sourcecode);
		
		
		
//		PrintWriter out = new PrintWriter(fileQuelltext);
//		out.println(sourcecode);
//	
//		out.close();
//		
//		FileWriter out = new FileWriter(fileQuelltext, true);
//		BufferedWriter bw = new BufferedWriter(out);
//	
//		
//		BufferedReader br = new BufferedReader(isr);
//		String inputLine;
//		while ((inputLine = br.readLine()) != null) {
//			if (!inputLine.isEmpty()) {
//				bw.append(inputLine)
//				.append("\n");
//			}
//		}
//		br.close();
//		bw.close();
		
//		
//		
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
