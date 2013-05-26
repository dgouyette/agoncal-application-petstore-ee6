package org.agoncal.application.petstore.service;

import fj.data.Option;
import org.agoncal.application.petstore.domain.Category;
import org.agoncal.application.petstore.domain.Item;
import org.agoncal.application.petstore.domain.Product;
import org.agoncal.application.petstore.exception.ValidationException;
import org.agoncal.application.petstore.util.Loggable;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.List;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */

@Stateless
@Loggable
public class CatalogService implements Serializable {

    // ======================================
    // =             Attributes             =
    // ======================================

    @Inject
    private EntityManager em;

    // ======================================
    // =              Public Methods        =
    // ======================================

    public Option<Category> findCategory(Long categoryId) {
        if (categoryId == null)
            throw new ValidationException("Invalid category id");

        return Option.fromNull(em.find(Category.class, categoryId));
    }

    public List<Category> findAllCategories() {
        TypedQuery<Category> typedQuery = em.createNamedQuery(Category.FIND_ALL, Category.class);
        return typedQuery.getResultList();
    }

    public Category createCategory(Category category) {
        if (category == null)
            throw new ValidationException("Category object is null");

        em.persist(category);
        return category;
    }

    public Category updateCategory(Category category) {
        if (category == null)
            throw new ValidationException("Category object is null");

        return em.merge(category);
    }

    public void removeCategory(Category category) {
        if (category == null)
            throw new ValidationException("Category object is null");

        em.remove(em.merge(category));
    }

    public void removeCategory(Long categoryId) {
        if (categoryId == null)
            throw new ValidationException("Invalid category id");
        Option<Category> category = findCategory(categoryId);
        if (category.isSome()) {
            removeCategory(category.some());
        }
    }

    public List<Product> findProducts(String categoryName) {
        if (categoryName == null)
            throw new ValidationException("Invalid category name");

        TypedQuery<Product> typedQuery = em.createNamedQuery(Product.FIND_BY_CATEGORY_NAME, Product.class);
        typedQuery.setParameter("pname", categoryName);
        return typedQuery.getResultList();
    }

    public Option<Product> findProduct(Long productId) {
        if (productId == null)
            throw new ValidationException("Invalid product id");
        return Option.fromNull(em.find(Product.class, productId));
    }

    public List<Product> findAllProducts() {
        TypedQuery<Product> typedQuery = em.createNamedQuery(Product.FIND_ALL, Product.class);
        return typedQuery.getResultList();
    }

    public Product createProduct(Product product) {
        if (product == null)
            throw new ValidationException("Product object is null");

        if (product.getCategory() != null && product.getCategory().getId() == null)
            em.persist(product.getCategory());

        em.persist(product);
        return product;
    }

    public Product updateProduct(Product product) {
        if (product == null)
            throw new ValidationException("Product object is null");

        return em.merge(product);
    }

    public void removeProduct(Product product) {
        if (product == null)
            throw new ValidationException("Product object is null");

        em.remove(em.merge(product));
    }

    public void removeProduct(Long productId) {
        if (productId == null)
            throw new ValidationException("Invalid product id");

        Option<Product> productOption = findProduct(productId);
        if (productOption.isSome()) {
            removeProduct(productOption.some());
        } else {
            //TODO log warn
        }
    }

    public List<Item> findItems(Long productId) {
        if (productId == null)
            throw new ValidationException("Invalid product id");

        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.FIND_BY_PRODUCT_ID, Item.class);
        typedQuery.setParameter("productId", productId);
        return typedQuery.getResultList();
    }

    public Option<Item> findItem(final Long itemId) {
        if (itemId == null)
            throw new ValidationException("Invalid item id");
        return Option.fromNull(em.find(Item.class, itemId));
    }

    public List<Item> searchItems(String keyword) {
        if (keyword == null)
            keyword = "";

        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.SEARCH, Item.class);
        typedQuery.setParameter("keyword", "%" + keyword.toUpperCase() + "%");
        return typedQuery.getResultList();
    }

    public List<Item> findAllItems() {
        TypedQuery<Item> typedQuery = em.createNamedQuery(Item.FIND_ALL, Item.class);
        return typedQuery.getResultList();
    }

    public Item createItem(Item item) {
        if (item == null)
            throw new ValidationException("Item object is null");

        if (item.getProduct() != null && item.getProduct().getId() == null) {
            em.persist(item.getProduct());
            if (item.getProduct().getCategory() != null && item.getProduct().getCategory().getId() == null)
                em.persist(item.getProduct().getCategory());
        }

        em.persist(item);
        return item;
    }

    public Item updateItem(Item item) {
        if (item == null)
            throw new ValidationException("Item object is null");

        return em.merge(item);
    }

    public void removeItem(Item item) {
        if (item == null)
            throw new ValidationException("Item object is null");

        em.remove(em.merge(item));
    }

    public void removeItem(Long itemId) {
        if (itemId == null)
            throw new ValidationException("itemId is null");

        Option<Item> itemOption = findItem(itemId);
        if (itemOption.isSome()) {
            removeItem(itemOption.some());
        } else {
            //TODO log warn
        }
    }
}
