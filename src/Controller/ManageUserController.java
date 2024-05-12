package Controller;

import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageUserController {

    private static Object getInstance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean addUser(String userid, String userName, String password, String role) {
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to insert a new user
            String query = "INSERT INTO user(user_id, user_name, user_password, role_as) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the parameters for the user to be inserted//
            preparedStatement.setString(1, userid);
            preparedStatement.setString(2, userName);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, role);

            // Execute the SQL query
            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();

            // Check if the user was added successfully and return a boolean value
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Return false in case of an error
            return false;
        }
    }

    public List<User> viewUserDetails() {
        List<User> users = new ArrayList<>();
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to select all users
            String query = "SELECT * FROM user";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");
                int Role_as = resultSet.getInt("role_as");

                // Create a user object and add it to the list
                User user = new User(userId, userName, userPassword, Role_as) {

                };
                users.add(user);
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }

    public boolean updateCurrentUser(int user_id, String user_name, String user_password, String role_as) {
        
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            // Prepare an SQL statement to update the user information
            String updateQuery = "UPDATE user SET user_name = ?, user_password = ?, role_as = ? WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            // Set the parameters for the update statement
            preparedStatement.setString(1, user_name);
            preparedStatement.setString(2, user_password);
            preparedStatement.setString(3, role_as);
            preparedStatement.setInt(4, user_id);

            int rowsAffected = preparedStatement.executeUpdate();

            preparedStatement.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove User from Database  
    public boolean removeUserFromDatabase(int userId){
        //Variables for connection
        Connection connection = null;
        PreparedStatement removeProductStatement = null;
        PreparedStatement removeSalesStatement = null;
        
        try {
            //Get Database Connction
            connection = DatabaseController.getInstance().getConnection();
            // Disable auto-commit to ensure transaction consistency
            connection.setAutoCommit(false);
            // Prepare a SQL statement to delete the product
            String removeProductSQL = "DELETE FROM user WHERE user_id = ?";
            removeProductStatement = connection.prepareStatement(removeProductSQL);
            removeProductStatement.setInt(1, userId);
            // Delete the product
            removeProductStatement.executeUpdate();
            // Commit the transaction
            connection.commit();
            return true; // Deletion was successful
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public java.util.List<User> searchUser(String keyword) {
        java.util.List<User> searchResults = new ArrayList<>();

        // Implement the search logic to query the database based on the keyword
        try {
            // Get a database connection
            Connection connection = DatabaseController.getInstance().getConnection();

            // SQL query to search for the keyword in relevant columns (e.g., name, price)
            String query = "SELECT user_id, user_name, user_password, role_as FROM user "
                    + "WHERE user_name LIKE ? OR user_password LIKE ?";

            // Set search keywords in the SQL query
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");
            // Execute the SQL query
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                // Retrieve product details
                int userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("user_name");
                String userPassword = resultSet.getString("user_password");
                int Role_AS = resultSet.getInt("role_as");

                // Create a user object and add it to the search results
                User user = new User(userId, userName, userPassword, Role_AS) {
                };
                searchResults.add(user);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return searchResults;
    }


    }

    
   

   