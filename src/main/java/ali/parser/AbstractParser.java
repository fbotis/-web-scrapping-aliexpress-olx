package ali.parser;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;

/**
 * Created by florinbotis on 13/03/2016.
 */
public class AbstractParser {


    protected String startPage;
    protected WebClient client;

    public AbstractParser(WebClient client, String startPage) {
        this.client = client;
        this.startPage = startPage;
    }

    public HtmlPage page() throws IOException {
        return client.getPage(startPage);
    }


}
