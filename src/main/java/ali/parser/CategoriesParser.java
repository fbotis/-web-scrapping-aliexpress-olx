package ali.parser;

import ali.model.Category;
import ali.parser.AbstractParser;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import rx.Observable;
import rx.Subscriber;

import java.io.IOException;

/**
 * Created by florinbotis on 13/03/2016.
 */
public class CategoriesParser extends AbstractParser {


    public CategoriesParser(WebClient client, String startPage) {
        super(client, startPage);
    }

    public Observable<Category> getCategories() {
        return Observable.create(s -> {
            try {
                page().getDocumentElement().getElementsByAttribute("div", "class", "item util-clearfix")
                        .parallelStream()
                        .forEach(bigGroup ->
                                consumeParentGroups(bigGroup, s));
            } catch (IOException e) {
                s.onError(e);
            }

            s.onCompleted();
        });
    }

    private void consumeParentGroups(HtmlElement bigGroup, Subscriber<? super Category> s) {
        try {
            HtmlElement bigGroupUrlEl = bigGroup.getElementsByAttribute("h3", "class", "big-title anchor1 anchor-agricuture")
                    .get(0).getElementsByTagName("a").get(0);
            String name = bigGroupUrlEl.getTextContent();
            String url = bigGroupUrlEl.getAttribute("href");

            Category parentCategory = new Category(name, url);
            s.onNext(parentCategory);

            final Category finalParentCategory = parentCategory;
            getSubCategories(bigGroup, finalParentCategory, s);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void getSubCategories(HtmlElement bigGroup, Category finalParentCategory, Subscriber<? super Category> s) {
        bigGroup.getElementsByTagName("li").forEach(li -> {
            li.getElementsByTagName("a").forEach(subcat -> {
                        String subCatName = subcat.getTextContent();
                        String subCatUrl = subcat.getAttribute("href");
                        Category subCat = new Category(finalParentCategory.getId(), subCatName, subCatUrl);
                        s.onNext(subCat);
                    }

            );
        });
    }
}
