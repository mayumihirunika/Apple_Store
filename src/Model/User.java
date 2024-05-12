package Model;

import Controller.DatabaseController;
import View.CashierForm;
import View.ManagerForm;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
        
public class User {
    private int userId;
    private String userName;
    private String userPassword;
    private int role_As;
    
    public User(int userId, String userName, String userPassword, int role_As){
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.role_As = role_As;
    }
    public int getUserId(){
        return userId;
    }
    public String getUserName(){
        return userName;
    }
    public String getUserPassword(){
        return userPassword;
    }
    public int getRoleas(){
        return role_As;
    }
    
    //Authenticate user
    public static User LoginUser(String username, String password) {
        User loginUser = null;
        String UserType = null;
        try {
            Connection connection = DatabaseController.getInstance().getConnection();

            String query = "SELECT * FROM user WHERE user_name = ? AND user_password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String role = resultSet.getString("role_as");

                if (role.equals("1")) {
                    ManagerForm MF = new ManagerForm();
                    UserType = "manager";
                    MF.setVisible(true);
                } else if (role.equals("0")) {
                    CashierForm CF = new CashierForm();
                    UserType = "cashier";
                    CF.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(null, "USER NOT FOUND");
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return loginUser;
    }

    public int UserID() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
