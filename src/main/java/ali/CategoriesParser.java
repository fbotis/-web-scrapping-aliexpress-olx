package ali;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import rx.Observable;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by florinbotis on 13/03/2016.
 */
public class CategoriesParser {

    protected final String startPage;
    protected WebClient client;

    public CategoriesParser(WebClient client, String startPage) {
        this.client = client;
        this.startPage = startPage;
        //http://www.aliexpress.com/all-wholesale-products.html
    }

    Observable<String> parse() {
        return Observable.create(s -> {
            try {
                final HtmlPage page = client.getPage(startPage);
                page.getDocumentElement().getElementsByAttribute("ul", "class", "sub-item-cont util-clearfix").parallelStream().forEach(ul ->
                        ul.getElementsByTagName("a").forEach(url -> s.onNext(url.getAttribute("href")))
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
