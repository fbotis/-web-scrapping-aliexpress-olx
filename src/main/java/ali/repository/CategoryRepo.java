package ali.repository;

import ali.model.Category;
import org.springframework.data.repository.CrudRepository;
import rx.Subscriber;

/**
 * Created by florinbotis on 13/03/2016.
 */

public interface CategoryRepo extends CrudRepository<Category, Long> {
}
