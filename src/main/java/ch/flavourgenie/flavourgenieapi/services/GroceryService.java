package ch.flavourgenie.flavourgenieapi.services;

import ch.flavourgenie.flavourgenieapi.models.GroceryItem;
import ch.flavourgenie.flavourgenieapi.repositories.ItemRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import java.util.List;

@Service
public class GroceryService {

    private final ItemRepository itemRepository;

    public GroceryService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Cacheable(value = "groceryCache")
    public long count() {
        return itemRepository.count();
    }

    @Cacheable(value = "groceryCache")
    public List<GroceryItem> findAll() {
        return itemRepository.findAll();
    }

    @Cacheable(value = "groceryCache", key = "#category")
    public List<GroceryItem> findAll(String category) {
        return itemRepository.findAll(category);
    }

    @Cacheable(value = "groceryCache", key = "#name")
    public GroceryItem findItemByName(String name) {
        return itemRepository.findItemByName(name);
    }

    @CacheEvict(value = "groceryCache", allEntries = true)
    public void save(GroceryItem item) {
        itemRepository.save(item);
    }

    @CacheEvict(value = "groceryCache", key = "#id")
    public void delete(String id) {
        itemRepository.deleteById(id);
    }

    @CacheEvict(value = "groceryCache", allEntries = true)
    public void deleteAll() {
        itemRepository.deleteAll();
    }
}
