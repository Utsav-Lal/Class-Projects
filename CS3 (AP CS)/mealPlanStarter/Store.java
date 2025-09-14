import java.util.*;  

/**
 * Class that stores the name of a store 
 * and a set of items that the store sells.
 */
public class Store {
    private String name;
    private HashSet<String> items;

    // Constructors
    
    /**
     * Constructs a Store with the specified name and no items for sale.
     * 
     * @param  name  The name of the store
     */
    public Store(String name) {
        this.name = name;
        items = new HashSet<>();
    }
    
    /**
     * Constructs a Store with the specified name and list of items for sale.
     * 
     * @param  name  The name of the store
     * @param  items  The items that the store sells
     */
    public Store(String name, Collection<String> items) {
        this.name = name;
        this.items = new HashSet<>(items);
    }

    // Getters
    
    public String getName() {
        return name; 
    }

    public HashSet<String> getItems() {
        return items;
    }
    
    // Other methods

    /**
     * Checks if an item is in store.
     * 
     * @param  item  name of item to search for
     * 
     * @return  true if item is sold at this store, false otherwise
     */
    public boolean hasItem(String item) {
        return items.contains(item); 
    }
    
    /**
     * Adds an item to the store's inventory.
     *
     * @param  item  name of item to add
     */
    public void addItem(String item) {
        items.add(item);
    }
    
    /**
     * Adds a collection of items to the store's inventory.
     *
     * @param  items  names of the items to add
     */
    public void addItems(Collection<String> items) {  
        this.items.addAll(items);
    }

    /**
     * Removes an item from the store's inventory.
     * 
     * @param  item  name of item to remove
     */
    public void removeItem(String item) {
        items.remove(item);
    }

    /**
     * Removes a collection of items from the store's inventory.
     * 
     * @param  items  names of the items to remove
     */
    public void removeItems(Collection<String> items) {  
        for (String s : items) {
            if (this.items.contains(s)) {
                removeItem(s);
            }
        }
    }
    
    /**
     * Converts the Store information to a String.
     * 
     * Uses the following format:
     * Store: Quick Mart
     * Products: bread, cheese, expired milk, soda
     * 
     * @return  store information as a String (see description for format)
     */
    @Override
    public String toString() {
        return "Store: "+getName()+"\nproducts: "+String.join(", ", items); 
    }
}
