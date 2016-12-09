package utilities;

import gui.Gui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;

public class Url {

	public static String review = "";

	public static List<String> reviewTexte = new ArrayList<String>();
	
	public static List<String> words = new ArrayList<String>();
	public static String html2text(String html) {
	    return Jsoup.parse(html).text();
	}

	// Kommentar 1
	public static List<String> reviewTextToFile() throws IOException, InterruptedException {

		BufferedReader br = new BufferedReader(new FileReader("src/texts/Quelltext.txt"));

		// Review Texte ziehen
		String string;
		StringBuilder sb = new StringBuilder();
		while ((string = br.readLine()) != null) {
			if (string.contains("</span></a></span></div><div class=\"a-row review-data\"><span class=\"a-size-base review-text\">")) {

				String temp1 = string.replaceAll("</div><div class=\"a-section a-spacing-extra-large a-spacing-top-medium a-text-center comment-load-error a-hidden\">.*?<span class=\"a-size-base review-text\">", "");
				String temp2 = temp1.replaceAll("<\\/span><\\/div><div class=\"a-row a-spacing-top-small review-comments\">.*?<ul class=\"a-viewoptions-list a-viewoptions-section a-span12\">", "");
				reviewTexte.add(temp2);
				sb.append(html2text(temp2));
				sb.append("\n").append("***").append("\n");
				new Gui();
				Gui.content = sb.toString();
			}
			if (!string.contains("Most helpful positive review")) {
				// Do nothing
			}
			if (!string.contains("Most helpful critical review")) {
				// Do nothing
			}
		}
		br.close();
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

	public static List<String> reviewToToken() throws IOException {

		File fileIn = new File("src/texts/reviews.txt");
		String encoding = "UTF-8";

		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(fileIn), encoding));
		StringBuffer sb = new StringBuffer();

		File fileList = new File("src/texts/reviewsList.txt");
		FileWriter out = new FileWriter(fileList);
		BufferedWriter bw = new BufferedWriter(out);

		String line;
		while ((line = br.readLine()) != null) {
			if (line.contains("</span></a></span></div><div class=\"a-row review-data\"><span class=\"a-size-base review-text\">")) {
				String temp1 = line
						.replaceAll(
								"</div><div class=\"a-section a-spacing-extra-large a-spacing-top-medium a-text-center comment-load-error a-hidden\">.*?<span class=\"a-size-base review-text\">",
								"");
				String temp2 = temp1
						.replaceAll(
								"<\\/span><\\/div><div class=\"a-row a-spacing-top-small review-comments\">.*?<ul class=\"a-viewoptions-list a-viewoptions-section a-span12\">",
								"");

				sb.append(temp2).append("\n");

			}
		}
		br.close();
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

		// Liste schreiben
		//
		for (String word : words) {
			// kleiner Filter
			if (word.length() > 2) {
				bw.write(word);
				bw.newLine();
			} else {
			}
		}
		bw.close();
		br.close();
		out.close();

		return null;
	}
}
