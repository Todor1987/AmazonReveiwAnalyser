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

import javax.net.ssl.HttpsURLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import gui.Gui;
import sun.net.www.protocol.http.HttpURLConnection;

public class Utilities {

	public static List<String> list = new ArrayList<String>();

	public static List<String> buildURL(String url, Integer seiten) throws InterruptedException, IOException {

		getQuelltext(url, seiten);

		// SEBDE LISTEN IN DER GUI
		Gui.reviewList = list;
		Url.reviewToToken(list);

		return null;
	}

	public static void getQuelltext(String url, int seiten) throws IOException, InterruptedException {

		// Document doc1 = Jsoup.parse(new
		// File("/home/guisy/Dokumente/amazonSource.html"), "UTF-8");

		// Elements elements1 = doc1.getElementsByAttributeValue("class",
		// "a-section review");
		// Elements elements1 = doc1.getElementsByTag("a");
		String nextUrl = url;
		for (int i = 0; i < seiten; i++) {
			
//			URL u = new URL(nextUrl);
//			HttpsURLConnection urlConn = (HttpsURLConnection) u.openConnection();
//			String line = null;
//			StringBuilder tmp = new StringBuilder();
//			BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
//			while ((line = in.readLine()) != null) {
//			  tmp.append(line);
//			}
//			
//			Document doc1 = Jsoup.parse(tmp.toString());
//			System.out.println(doc1);
			Document doc1 = Jsoup
					.connect(nextUrl)
					.header("Accept-Encoding", "gzip, deflate")
					.userAgent("Mozilla/5.0")
//					.maxBodySize(0)
//					.timeout(60000)
					.get();
				
			System.out.println(doc1);
			
			Elements bewertungenElements = doc1.select("span.a-size-base review-text");

			for (Element element : bewertungenElements) {
				String star = "";
				String review = "";
				String key = "";

				// STERNEBEWERTUNG *****************************
				Elements stars = element.getElementsByAttributeValue("class", "a-icon-alt");
				for (Element element2 : stars) {
					if (element2.text().contains("out of"))
						star = element2.text();
				}
				// AUTHORNAME um Texte unterscheiden zu können *****************************
				Elements author = element.getElementsByAttributeValue("class", "a-size-base a-link-normal author");
				key = author.text();

				// EIGENTLICHE TEXT *****************************
				Elements reviews = element.getElementsByAttributeValue("class", "a-size-base review-text");
				list.add("\n\n " + key + " ***************** " + star + " *******************\n");
				list.add(reviews.text() + "\n");

			}
			// NÄCHSTER LINK ************************************************** 
			Elements nextLink = doc1.select("a");
			for (Element element2 : nextLink) {
				if (element2.text().contains("Next"))
					nextUrl = element2.absUrl("href");
			}
		}
	}

	public static void wordOccurencies(List<String> list) throws IOException {

		Map<String, Integer> map = new HashMap<String, Integer>();

		for (String string : list) {
			Integer i = 1;
			if (!map.containsKey(string)) {
				map.put(string, i);
			} else if (map.containsKey(string)) {
				map.put(string, map.get(string) + 1);
			}
			i++;
		}
		// System.out.println(map.size());
		// for (String key : map.keySet()) {
		// System.out.println(key + " " + map.get(key));
		// }
	}

	// public static void countWords (String string2) throws IOException{
	//
	// int i = 0;
	// String line = "";
	// String line2 = "";
	//
	// BufferedReader br = new BufferedReader(new
	// FileReader("src/texts/reviewsList.txt"));
	// while((line = br.readLine()) != null ) {
	// i++;
	// }
	// System.out.println("Insgesamt " + i + " W��rter in der Liste");
	//
	// }

}
