import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.print.Doc;
import java.io.*;
import java.net.URL;
import java.util.List;

public class Parser implements Runnable {
    private Links links;
    private String urlCategoryPage;
    private String nextItem;

    public Parser(Links links, String urlCategoryPage) throws IOException {
        this.links = links;
        this.urlCategoryPage = urlCategoryPage;
    }

    @Override
    public void run() {
        try {
            parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse() throws IOException {
        File file = new File(filePath(urlCategoryPage));
        FileOutputStream fos;
        if (file.createNewFile()) { //true if the named file does not exist and was successfully created; false if the named file already exists
            fos = new FileOutputStream(file);
        } else {
            fos = new FileOutputStream(file, true);
        }


        Document page = Jsoup.parse(new URL(links.getUrlAvito() + urlCategoryPage), 15000);
        Element item = page.selectFirst("a[class=photo-wrapper js-item-link js-photo-wrapper large-picture]");
        String hrefItem;
        try {
            hrefItem = item.attr("href");
        } catch (Exception e) {
            item = page.selectFirst("a[class=styles-link-36uWZ]");
            try {
                hrefItem = item.attr("href");
            } catch (Exception e1) {
                item = page.selectFirst("a[class=js-item-slider item-slider]");
                hrefItem = item.attr("href");
            }
        }
        while (true) {
            try {
                parseItem(hrefItem, fos);
                hrefItem = nextItem;
            } catch (Exception e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void parseItem(String hrefItem, FileOutputStream fos) throws IOException {
        System.out.println("Парсинг " + hrefItem);
        Document itemPage = Jsoup.parse(new URL(links.getUrlAvito() + hrefItem), 15000);
        String title = itemPage.selectFirst("span[class=title-info-title-text]").text();
        String price;
        try {
            price = itemPage.selectFirst("span[class=js-item-price]").text();
        } catch (Exception e) {
            price = "Цена не указана";
        }
        String name = itemPage.selectFirst("div[class=seller-info-name js-seller-info-name]").select("a").text();
        String description;
        try {
            description = itemPage.selectFirst("div[class=item-description-text]").select("p").text();
        } catch (Exception e) {
            description = "Без описания";
        }

        String str = hrefItem + System.lineSeparator()
                + title + System.lineSeparator()
                + price + System.lineSeparator()
                + name + System.lineSeparator()
                + description + System.lineSeparator() + System.lineSeparator();
        fos.write(str.getBytes());
        nextItem = itemPage.selectFirst("a[class=js-item-view-next-button]").attr("href");
    }

    private String filePath(String urlCategoryPage) {
        return "src" + File.separator + "main" + File.separator + "cities" + File.separator + links.getCity() + File.separator + (urlCategoryPage.replaceAll("/" + links.getCity() + "/", "")).replaceAll("\\?cd=1", "") + ".txt";
    }

}
