package Controller;

import Model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CashierController {

    //View
    public java.util.List<Product> viewProductDetails() {
        java.util.List<Product> products = new ArrayList<>();
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to select all products
            String query = "SELECT * FROM product";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("product_price");
                String category = resultSet.getString("product_category");
                int stockQuantity = resultSet.getInt("product_qty");

                // Create a Product object and add it to the list
                Product product = new Product(productId, productName, price, stockQuantity, category) {

                };
                products.add(product);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    // Search Product Data
    public List<Product> searchProduct(String keyword) {
        List<Product> searchResults = new ArrayList<>();

        // Implement the search logic to query the database based on the keyword
        try {
            // Get a database connection
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to search for the keyword in relevant columns (e.g., name, price, category)
            String query = "SELECT product_id, product_name, product_price, product_qty, product_category FROM product "
                    + "WHERE product_name LIKE ? OR product_price LIKE ? OR product_category LIKE ? ";

            // Set search keywords in the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            preparedStatement.setString(3, "%" + keyword + "%");
            
            // Execute the SQL query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                // Retrieve product details
                int productId = resultSet.getInt("product_id");
                String productName = resultSet.getString("product_name");
                double price = resultSet.getDouble("product_price");
                String category = resultSet.getString("product_category");
                int stockQuantity = resultSet.getInt("product_qty");

                // Create a Product object and add it to the search results
                Product product = new Product(productId, productName, price, stockQuantity, category) {
                };
                searchResults.add(product);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }

}
