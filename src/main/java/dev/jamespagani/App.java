package dev.jamespagani;
import dev.jamespagani.config.DatabaseConfigManager;
import dev.jamespagani.services.ProductService;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws ClassNotFoundException {
        DatabaseConfigManager.getEntityManagerFactory();
        ProductService ps = new ProductService();
        ps.createProduct(10, "Test Product");
        GUI.launch(GUI.class, args);
        DatabaseConfigManager.closeEntityManagerFactory();
    }
}
