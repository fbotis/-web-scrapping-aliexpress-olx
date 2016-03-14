package ali.model;

import com.google.common.base.MoreObjects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by florinbotis on 13/03/2016.
 */
@Entity
public class Category {
    @Id
    @GeneratedValue
    Long id;


    Long parentId;

    String name;

    String url;

    int level;

    public Category(String name, String url) {
        this.name = name;
        this.url = url;
        level=0;
    }

    public Category(Long parentId, String name, String url) {
        this.parentId = parentId;
        this.name = name;
        this.url = url;
        level=1;

    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public int getLevel() {
        return level;
    }

}
