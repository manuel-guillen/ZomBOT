package data.generator;

import data.model.Gobblegum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class GobblegumDataGenerator {

    private static final String SOURCE_URL = "https://callofduty.fandom.com/wiki/GobbleGum";
    private static final String CSS_SELECTOR = "h2:has(span#Types) ~ ul > li:has(span[style] > b)";

    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.connect(SOURCE_URL).get();
        Elements elements = doc.select(CSS_SELECTOR);

        for (Element e : elements) {
            Element span = e.child(0);

            Gobblegum.Color color;
            switch(span.attr("style").replace("color:","")) {
                case "blue":      color = Gobblegum.Color.Blue;     break;
                case "green":     color = Gobblegum.Color.Green;    break;
                case "purple":    color = Gobblegum.Color.Purple;   break;
                default:          color = Gobblegum.Color.Orange;   break;
            }

            String name = span.child(0).text();

            System.out.println(name + "\t\t" + color);
        }
    }

}
