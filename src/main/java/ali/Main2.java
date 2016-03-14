package ali;

import ali.parser.CategoriesParser;
import ali.parser.CategoryParser;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import org.apache.commons.logging.LogFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

/**
 * Created by florinbotis on 13/03/2016.
 */
public class Main2 {
    static ExecutorService thpool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);
        WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED);
        CategoriesParser p1 = new CategoriesParser(webClient, "http://www.aliexpress.com/all-wholesale-products.html");
        p1.getCategories().subscribe(category ->
                thpool.execute(() -> {
                    if (category.getLevel() > 0) {
                        CategoryParser p = new CategoryParser(new WebClient(BrowserVersion.BEST_SUPPORTED), category.getUrl());
                        p.getProductUrls().subscribe(s -> System.out.println(s));
                    }
                }));
    }
}
