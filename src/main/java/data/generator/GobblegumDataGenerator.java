package data.generator;

import data.model.Gobblegum;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GobblegumDataGenerator {

    private static final String SOURCE_URL = "https://callofduty.fandom.com/wiki/GobbleGum";
    private static final String MAIN_ELEMENT_SELECTOR = "h2:has(span#Types) ~ ul > li:has(span[style] > b)";
    private static final String THUMBNAIL_SELECTOR = "div:has(img[class=thumbimage]):has(div.lightbox-caption).wikia-gallery-item";

    private static final Pattern DESCR_PATTERN = Pattern.compile("[- ]*([\\w\\s]*\\((?<activation>[^\\(\\)]+)\\))?(?<description>.+)");

    private static final String FULL_SCALE_SENTINEL = "/latest";

    public static final Map<String, Gobblegum> GOBBLEGUM_MAP = downloadGobblegumData();

    private static Map<String,Gobblegum> downloadGobblegumData() {
        Document doc;
        try {
            doc = Jsoup.connect(SOURCE_URL).get();
        } catch (IOException e) {
            throw new RuntimeException("Could not connect to web source.");
        }
        Elements elements = doc.select(MAIN_ELEMENT_SELECTOR);
        Elements thumbnails = doc.select(THUMBNAIL_SELECTOR);

        Map<String,Gobblegum> map = new HashMap<String,Gobblegum>(70);

        for (Element e : elements) {
            Element span = e.child(0);
            String name = span.text();

            Gobblegum.Color color;
            switch(span.attr("style").replace("color:","")) {
                case "blue":      color = Gobblegum.Color.Blue;     break;
                case "green":     color = Gobblegum.Color.Green;    break;
                case "purple":    color = Gobblegum.Color.Purple;   break;
                default:          color = Gobblegum.Color.Orange;
            }

            span.remove();
            Matcher m = DESCR_PATTERN.matcher(e.text());
            m.matches();

            String activation = m.group("activation"),
                    description = m.group("description");

            if (activation == null) {
                int i = description.trim().indexOf('.');
                activation = description.substring(0, i);
                description = description.substring(i+1);
            }

            activation = activation.trim();
            description = description.trim();

            description = shortenDescription(description);

            Gobblegum.Type type;
            switch (e.parent().previousElementSiblings().select("h4").first().child(0).text()) {
                case "Default":             type = Gobblegum.Type.Default;          break;
                case "Normal":              type = Gobblegum.Type.Normal;           break;
                case "Whimsical":           type = Gobblegum.Type.Whimsical;        break;
                case "Mega":                type = Gobblegum.Type.Mega;             break;
                case "Rare Mega":           type = Gobblegum.Type.RareMega;         break;
                case "Ultra-Rare Mega":     type = Gobblegum.Type.UltraRareMega;    break;
                default:                    throw new RuntimeException("Incorrect type read.");
            }

            // Sanitize name (escape characters and no invalid filename characters)
            String sName = name.replace("'","\\'").replace("!","");
            Elements selection = thumbnails.select("div:has(div.lightbox-caption:contains(" + sName + ")), div:has(img[title~=" + sName + "])");

            String imageURL = selection.first().selectFirst("img").attr("src");
            int trimLength = imageURL.lastIndexOf(FULL_SCALE_SENTINEL) + FULL_SCALE_SENTINEL.length();
            imageURL = imageURL.substring(0, trimLength);

            map.put(name.replaceAll("[^A-Za-z0-9 ]","").toLowerCase(), new Gobblegum(name, color, type, activation, description, imageURL));
        }

        return map;
    }

    private static String shortenDescription(String description) {
        int length = description.length();

        if (length < 150)
            return description;

        String[] split = description.split(" ?[\\(\\.\\)] ?");
        Stream<String> sentences = Arrays.stream(split);

        if (length < 176)
            sentences = sentences.limit(split.length - 1);      // Remove last sentence
        else if (length < 214)
            sentences = sentences.limit(2);                     // Keep only first two sentences
        else
            sentences = sentences.limit(1);                     // Keep only first sentence

        return sentences.collect(Collectors.joining(". "));
    }

}
