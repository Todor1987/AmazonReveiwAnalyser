package utilities;

import java.io.File;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Neu {

	File file = new File("/home/guisy/workspace/Spielplatz/AmazTest/sourcecode.txt");
	
	String source = Jsoup.connect("https://www.amazon.com/gp/product/B009VMC7UO/ref=s9_acsd_ri_bw_c_x_4_w?pf_rd_m=ATVPDKIKX0DER&pf_rd_s=merchandised-search-8&pf_rd_r=VPW4A6708GRBJ5TCZ2XT&pf_rd_r=VPW4A6708GRBJ5TCZ2XT&pf_rd_t=101&pf_rd_p=b8c0a303-a08e-4b0b-bd49-040811fd7080&pf_rd_p=b8c0a303-a08e-4b0b-bd49-040811fd7080&pf_rd_i=283155#customerReviews").userAgent("Mozilla/5.0 Chrome/26.0.1410.64 Safari/537.31")
			  .timeout(2*10000)
			  .followRedirects(true).get().html();
	
	
//	System.out.println(source);
	
	
	Document doc = Jsoup.parse(source);
//	
	Elements reviews = doc.select("div.a-section");
	
	int i = 0;
	for (Element e : reviews) {
		String s = e.text();
		if(s.contains("out of 5 stars") & i >= 3)
			System.out.println(s + "\n" + "**************************************************************" + "\n");
			
	i++;	
	}
}
