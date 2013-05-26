package org.agoncal.application.petstore.service;

import fj.data.Option;
import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.Item;
import org.agoncal.application.petstore.domain.Product;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 */
//@Ignore
@RunWith(Arquillian.class)
public class CatalogServiceIT extends AbstractServiceIT {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private CatalogService catalogService;

    // ======================================
    // =              Unit tests            =
    // ======================================

    @Test
    public void whenCategoryNotFoundShouldThrowNPE() {
        Option<Category> categoryOption = catalogService.findCategory(999L);
        if (categoryOption.isSome()) {
            String description = categoryOption.some().getDescription();
        }

    }

    @Test
    public void shouldCRUDaCategory() {

        // Finds all the objects
        int initialNumber = catalogService.findAllCategories().size();

        // Creates an object
        Category category = new Category("Fish", "Any of numerous cold-blooded aquatic vertebrates characteristically having fins, gills, and a streamlined body");

        // Persists the object
        category = catalogService.createCategory(category);
        Long id = category.getId();

        // Finds all the objects and checks there's an extra one
        assertEquals("Should have an extra object", initialNumber + 1, catalogService.findAllCategories().size());

        // Finds the object by primary key
        Option<Category> categoryOption = catalogService.findCategory(id);

        assertThat(categoryOption.isSome()).isTrue();
        assertEquals("Fish", categoryOption.some().getName());

        // Updates the object
        category.setName("Big Fish");
        catalogService.updateCategory(category);

        // Finds the object by primary key
        Option<Category> categoryOption1 = catalogService.findCategory(id);
        assertThat(categoryOption1.isSome()).isTrue();
        assertEquals("Big Fish", category.getName());

        // Deletes the object
        catalogService.removeCategory(category);

        // Checks the object has been deleted

        assertThat(catalogService.findCategory(id).isNone()).isTrue();

        // Finds all the objects and checks there's one less
        assertEquals("Should have an extra object", initialNumber, catalogService.findAllCategories().size());
    }

    @Test
    public void shouldCRUDaProduct() {

        // Finds all the objects
        int initialNumber = catalogService.findAllProducts().size();

        // Creates an object
        Category category = new Category("Fish", "Any of numerous cold-blooded aquatic vertebrates characteristically having fins, gills, and a streamlined body");
        Product product = new Product("Angelfish", "Saltwater fish from Australia", category);

        // Persists the object
        product = catalogService.createProduct(product);
        Long id = product.getId();

        // Finds all the objects and checks there's an extra one
        assertEquals("Should have an extra object", initialNumber + 1, catalogService.findAllProducts().size());

        // Finds the object by primary key
        Option<Product> productOption = catalogService.findProduct(id);
        assertThat(productOption.isSome()).isTrue();
        assertEquals("Angelfish", productOption.some().getName());

        // Updates the object
        product.setName("Big Angelfish");
        catalogService.updateProduct(product);

        // Finds the object by primary key
        Option<Product> productOption1 = catalogService.findProduct(id);
        assertThat(productOption1.isSome()).isTrue();
        assertEquals("Big Angelfish", productOption1.some().getName());

        // Deletes the object
        catalogService.removeProduct(product);

        // Checks the object has been deleted
        Option<Product> productOption2 = catalogService.findProduct(id);
        assertThat(productOption2.isNone()).isTrue();

        // Finds all the objects and checks there's one less
        assertEquals("Should have an extra object", initialNumber, catalogService.findAllProducts().size());
    }

    @Test
    public void shouldCRUDanItem() {

        // Finds all the objects
        int initialNumber = catalogService.findAllItems().size();

        // Creates an object
        Category category = new Category("Fish", "Any of numerous cold-blooded aquatic vertebrates characteristically having fins, gills, and a streamlined body");
        Product product = new Product("Angelfish", "Saltwater fish from Australia", category);
        Item item = new Item("Large", 10.00f, "fish1.jpg", product, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum velit ante, malesuada porta condimentum eget, tristique id magna. Donec ac justo velit. Suspendisse potenti. Donec vulputate vulputate molestie. Quisque vitae arcu massa, dictum sodales leo. Sed feugiat elit vitae ante auctor ultrices. Duis auctor consectetur arcu id faucibus. Curabitur gravida.");

        // Persists the object
        item = catalogService.createItem(item);
        Long id = item.getId();

        // Finds all the objects and checks there's an extra one
        assertEquals("Should have an extra object", initialNumber + 1, catalogService.findAllItems().size());

        // Finds the object by primary key
        Option<Item> itemOption = catalogService.findItem(id);
        assertThat(itemOption.isSome()).isTrue();
        assertEquals("Large", itemOption.some().getName());

        // Updates the object
        item.setName("Large fish");
        catalogService.updateItem(item);

        // Finds the object by primary key
        Option<Item> itemOption1 = catalogService.findItem(id);
        assertThat(itemOption1.isSome()).isTrue();
        assertEquals("Large fish", itemOption1.some().getName());

        // Deletes the object
        catalogService.removeItem(item);

        // Checks the object has been deleted
        Option<Item> itemOption2 = catalogService.findItem(id);
        assertThat(itemOption2.isNone()).isTrue();

        // Finds all the objects and checks there's one less
        assertEquals("Should have an extra object", initialNumber, catalogService.findAllItems().size());
    }
}
