package com.example.messengerclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

public class DBUtils {

    public static void changeScene(ActionEvent event, String fxmlFile, String tittle, String username, String password) {
        Parent root = null;

        if (username != null && password != null) {
            try {
                FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
                root = loader.load();
                LogInController logInController = loader.getController();
                logInController.setUserInformation(username, password);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                root = FXMLLoader.load(DBUtils.class.getResource(fxmlFile));
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(tittle);
        stage.setScene(new Scene(root, 1000, 700));
        stage.show();
    }

    public static void signUpUser(ActionEvent event, String username, String email, String password) {
        Connection connection = null;
        PreparedStatement psInsert = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11496013", "sql11496013", "QHVnl7VClt");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(1, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Username is taken!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else {
                psInsert = connection.prepareStatement("INSERT INTO users (username, email, password) VALUES (?, ?, ?)");
                psInsert.setString(1, username);
                psInsert.setString(2, email);
                psInsert.setString(3, password);
                psInsert.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null) {
                try {
                    psCheckUserExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void logInUser(ActionEvent event, String username, String password) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11496013", "sql11496013", "QHVnl7VClt");
            preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        changeScene(event, "hello-view.fxml", "Welcome to Messenger!", username, password);
                    } else {
                        System.out.println("Passwords did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Username or Password incorrect");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void changeUsername(ActionEvent event, String username) {
        Connection connection = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psCheckUserExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11496013", "sql11496013", "QHVnl7VClt");
            psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
            psCheckUserExists.setString(2, username);
            resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Username is taken!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You cannot use this username.");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE users SET username = ? WHERE user_id = ?");
                psUpdate.executeUpdate();
            }


            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                if (resultSet != null) {
                    try {
                        resultSet.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (psCheckUserExists != null) {
                    try {
                        psCheckUserExists.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (psUpdate != null) {
                    try {
                        psUpdate.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    public static void changePassword(ActionEvent event, String password) {
        Connection connection = null;
        PreparedStatement psUpdate = null;
        PreparedStatement psCheckPasswordExists = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection("jdbc:mysql://sql11.freemysqlhosting.net:3306/sql11496013", "sql11496013", "QHVnl7VClt");
            psCheckPasswordExists = connection.prepareStatement("SELECT password FROM users WHERE username = ?");
            psCheckPasswordExists.setString(3, password);
            resultSet = psCheckPasswordExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("Password is same as previous!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You're password has to be different than the previous!");
                alert.show();
            } else {
                psUpdate = connection.prepareStatement("UPDATE users SET password = ? WHERE user_id = ?");
                psUpdate.executeUpdate();
            }


        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psCheckPasswordExists != null) {
                try {
                    psCheckPasswordExists.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (psUpdate != null) {
                try {
                    psUpdate.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
