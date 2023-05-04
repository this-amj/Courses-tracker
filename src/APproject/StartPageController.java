/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author imbes
 */
public class StartPageController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button signinBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
//    private void gotoSigninPage(ActionEvent event) throws IOException {
//        
//     Parent root= FXMLLoader.load(getClass().getResource("signinFXML.fxml"));
//     Scene MySignInPage = new Scene(root);
//     Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
//     stage.setScene(MySignInPage);
//     stage.show();
//
//    }

//    private void gotoSignUpPage(ActionEvent event) throws IOException {
//                
//     Parent root= FXMLLoader.load(getClass().getResource("SignUpFXML.fxml"));
//     Scene MySignInPage = new Scene(root);
//     Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
//     stage.setScene(MySignInPage);
//     stage.show();
//    }

    @FXML
    private void gotoSignin(ActionEvent event) throws IOException {
     Parent root= FXMLLoader.load(getClass().getResource("SignIn.fxml"));
     Scene MySignInPage = new Scene(root);
     Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
     stage.setScene(MySignInPage);
     stage.show();
    }

    @FXML
    private void gotoSignUp(ActionEvent event) throws IOException {
    Parent root= FXMLLoader.load(getClass().getResource("SignUp.fxml"));
     Scene MySignInPage = new Scene(root);
     Stage stage=(Stage)((Node)event.getSource()).getScene().getWindow();
     stage.setScene(MySignInPage);
     stage.show();
    }



    
}
