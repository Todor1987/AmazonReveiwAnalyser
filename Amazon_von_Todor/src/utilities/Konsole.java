package utilities;

import java.io.IOException;
import java.util.List;

public class Konsole {
	
	
	private static boolean online = true;
	
	// http://www.amazon.com/AH1650SR-Feet-16-Gauge-Speaker-Wire/product-reviews/B0029HO66G/ref=cm_cr_pr_btm_link_2?ie=UTF8&showViewpoints=1&sortBy=helpful&reviewerType=all_reviews&formatType=all_formats&filterByStar=all_stars&pageNumber=1
	
	public static void control(String link, int seiten) throws InterruptedException, IOException{

		String temp = link.substring(0, link.length()-1);
		
		List<String> urlList = utilities.Utilities.buildURL(temp, seiten );
		
	// "B008RHGC7S"	
	
	if(online == true){
		for (String string : urlList) {
			System.out.println("Quelltext ziehen.....");
			utilities.Utilities.getQuelltext(string);
//			Thread.sleep(500);
		}
	}
//	Url.reviewTextToFile();
//	Url.reviewToToken();
      
//	System.out.println("##############");
//	System.out.println("Dursuchte url's:");
//	for (String string : urlList) {
//		System.out.println(string);
//	}
	}
}
