import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Links {
    private String homepage;
    private String city;
    private String urlAvito;
    private List<String> urlCategories;

    public Links() throws IOException {
        Properties properties = GetProperties.getProperties();
        this.city = properties.getProperty("city");
        this.urlAvito = properties.getProperty("avito");
        this.homepage = urlAvito + "/" + city;
        this.urlCategories = setCategories();
    }

    private List<String> setCategories() throws IOException {
        List<String> categories = new ArrayList<String>();
        Document page = Jsoup.parse(new URL(homepage), 15000);
        Elements elementsOfCategories = page.select("a[class=link-link-39EVK link-design-default-2sPEv link-novisited-1w4JY category-with-counters-link-1zX0y]");
        for (Element element :
                elementsOfCategories) {
            categories.add(element.attr("href"));
        }
        return categories;
    }

    public String getUrlAvito() {
        return urlAvito;
    }

    public List<String> getUrlCategories() {
        return urlCategories;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getCity() {
        return city;
    }
}
