package ch.flavourgenie.flavourgenieapi.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document("GroceryItem")
public class GroceryItem {

    @Id
    private String id;

    private final String name;
    private final int quantity;
    private final String category;

    public GroceryItem(String name, int quantity, String category) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getCategory() {
        return category;
    }
}
