package ch.flavourgenie.flavourgenieapi.controllers;

import ch.flavourgenie.flavourgenieapi.models.GroceryItem;
import ch.flavourgenie.flavourgenieapi.services.GroceryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@Tag(name = "Grocery", description = "Grocery API")
public class GroceryController {

    private final GroceryService groceryService;

    public GroceryController(GroceryService groceryService) {
        this.groceryService = groceryService;
    }

    @GetMapping("/grocery")
    @Operation(summary = "Get all Groceries",  description = "Get all Groceries which were created by Users", operationId = "get-grocery")
    public List<GroceryItem> getGroceryItems() {
        return groceryService.findAll();
    }

    @PostMapping("/grocery")
    @Operation(summary = "Create a Grocery",  description = "Create a Grocery with defined request body", operationId = "post-grocery")
    public void addGroceryItem(@RequestBody GroceryItem item) {
        groceryService.save(item);
    }
}
