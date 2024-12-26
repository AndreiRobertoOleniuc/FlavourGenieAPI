package ch.flavourgenie.flavourgenieapi.controllers;

import ch.flavourgenie.flavourgenieapi.models.GroceryItem;
import ch.flavourgenie.flavourgenieapi.services.GroceryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GroceryController {

    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @GetMapping("/grocery")
    public List<GroceryItem> getGroceryItems() {
        return groceryService.findAll();
    }

    @PostMapping("/grocery")
    public void addGroceryItem(@RequestBody GroceryItem item) {
        groceryService.save(item);
    }
}
