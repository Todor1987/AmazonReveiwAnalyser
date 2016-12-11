package utilities;

import gui.Gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Url {

	public static String review = "";

	public static List<String> reviewTexte = new ArrayList<String>();
	
	public static List<String> words = new ArrayList<String>();
	
	public static StringBuilder sb = new StringBuilder();
	
	public static String html2text(String html) {
	    return Jsoup.parse(html).text();
	}

	// Kommentar 1
	public static List<String> reviewTextToFile(String s) throws IOException, InterruptedException {

		System.out.println(s);
		Document doc = Jsoup.parse(s);
		Elements elements = doc.select("div.a-section");
		for (Element element : elements) {
//			reviewTexte.add(element.text());
			sb.append(element.text());
		}
		review = sb.toString();

		File file = new File("src/texts/reviews.txt");
		FileWriter out = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(out);

		// // Review Texte toFile
		Iterator<String> iteratorReviewTexte = reviewTexte.iterator();
		while (iteratorReviewTexte.hasNext()) {
			String str = iteratorReviewTexte.next();
			bw.write(str);
			bw.newLine();
		}
		bw.close();
		return reviewTexte;
	}

	public static List<String> reviewToToken(List<String> list) throws IOException {

		StringBuilder sb = new StringBuilder();
		for (String string : list) {
			sb.append(string);
		}
		
		BreakIterator iterator = BreakIterator.getWordInstance();
		String text = sb.toString();
		iterator.setText(text);
		
		int last = 0;
		while (true) {
			int next = iterator.next();
			if (next == -1)
				break;
			String sub = text.substring(last, next).trim();
			if (sub.length() == 0)
				continue;
			words.add(sub);
			last = next;
		}
		Gui.words = words;
		Utilities.wordOccurencies(words);

		return null;
	}
}
