package ali;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.logging.Level;

/**
 * Created by florinbotis on 13/03/2016.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");

        java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF);
        java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);


        try (final WebClient webClient = new WebClient(BrowserVersion.BEST_SUPPORTED)) {
            webClient.addRequestHeader("Accept-Language", "en-US,en;q=0.8,en-GB;q=0.6");
            final HtmlPage page = webClient.getPage("http://www.aliexpress.com/all-wholesale-products.html");
            page.getDocumentElement().getElementsByAttribute("ul", "class", "sub-item-cont util-clearfix").stream().
                    forEach(x -> {
                        x.getElementsByTagName("a").stream().forEach(a -> {
                            try {
                                System.out.println("Found " + a.getFirstElementChild());
                                HtmlPage catpage = new WebClient(BrowserVersion.BEST_SUPPORTED).getPage(a.getAttribute("href"));
                                System.out.println("Clicking orders " + a);
                                catpage = catpage.getElementById("number_of_orders_1").click();

                                catpage.getDocumentElement().getElementsByAttribute("div", "class", "item").forEach(
                                        p -> {
                                            System.out.println(p.getElementsByTagName("a"));
                                        }
                                );
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                    });
        }
    }
}
