package ali.parser;

import ali.parser.AbstractParser;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import rx.Observable;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by florinbotis on 13/03/2016.
 */
public class CategoryParser extends AbstractParser {

    public CategoryParser(WebClient client, String startPage) {
        super(client, startPage);
    }

    public Observable<String> getProductUrls() {
        return Observable.create(s -> {
            try {
                HtmlPage catpage = client.getPage(startPage);
                catpage = catpage.getElementById("linkFreeShip").click();
                catpage = catpage.getElementById("number_of_orders_1").click();
                catpage.getDocumentElement().getElementsByAttribute("div", "class", "item").forEach(
                        p -> {
                            s.onNext(p.getElementsByTagName("a").get(0).getAttribute("href"));
                        }
                );
                s.onCompleted();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
