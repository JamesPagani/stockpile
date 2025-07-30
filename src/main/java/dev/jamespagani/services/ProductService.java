package dev.jamespagani.services;

import java.util.List;
import java.util.UUID;

import dev.jamespagani.entities.Product;
import dev.jamespagani.persistence.ProductDAO;
import jakarta.transaction.Transactional;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService() {
        productDAO = new ProductDAO();
    }

    @Transactional
    public void createProduct(long stock, String name) {
        Product product = new Product();
        product.setStock(stock);
        product.setName(name);
        try {
            productDAO.save(product);
            System.out.println("Product Created");
        } catch (Exception e) {
            System.err.println("Could not create the product: " + e.getMessage());
        }
    }

    public List<Product> listAllProducts() {
        List<Product> products = productDAO.findAll();
        for (Product product : products) {
            System.out.println(product.toString());
        }
        return products;
    }

    public Product findProductById(UUID id) {
        return productDAO.findById(id);
    }
}
