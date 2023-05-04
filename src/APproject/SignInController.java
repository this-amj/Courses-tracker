/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author imbes
 */
public class SignInController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Label SigninEmaillb;
    @FXML
    private Label SigninPasswordlb;
    @FXML
    private Label forgotPasswordlb;
    @FXML
    private Label backToSignUp;
    @FXML
    private Button signinBtn2;
    @FXML
    private TextField SigninEmailTF;
    @FXML
    private TextField SigninPasswordTF;
    @FXML
    private AnchorPane SignInalert;
    @FXML
    private Label SignInmsg;
    private Student currentSt;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SignInalert.setTranslateX(-500);
    }


    @FXML
    private void SigninPageBtn(ActionEvent event) throws IOException {

        try {
            Session session1 = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session1.beginTransaction();
            Query query = session1.createQuery("from Student");
            List<Student> stList= query.list();
            System.out.println("connection suceeded");
            t.commit();
            session1.close();

            if (SigninEmailTF.getText().isEmpty() && SigninPasswordTF.getText().isEmpty()) {
                Audio.sound1();
                trans("Please enter your information!");
                return;
            }
            if (SigninEmailTF.getText().isEmpty() && !SigninPasswordTF.getText().isEmpty()) {
                Audio.sound1();
                trans("Please enter your email!");
                return;
            }
            if (SigninPasswordTF.getText().isEmpty() && !SigninEmailTF.getText().isEmpty() ) {
                Audio.sound1();
                trans("Please enter your password!");
                return;
            }
            
            if (!(SigninEmailTF.getText().contains("@"))) {
                SigninEmailTF.clear();
                Audio.sound1();
                trans("Wrong email, please enter a valid email!");
                return;
            }
            
            if(stList.isEmpty()){
                Audio.sound1();
                trans("There's no account with this email, please register!");
                return;
            }
            
            for(Student i : stList){
                currentSt=i;
                if ((i != null) && i.getEmail().equals(SigninEmailTF.getText())) {
                    if (i.getPassword().equals(SigninPasswordTF.getText())) {
                        //continue to home page 
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml")); //to get the fxml file to be displayed
                        Parent root = loader.load(); //put it as a root 
                        HomePageController home = loader.getController(); //to take the methods in this calss
                        home.currentUser(currentSt);

                        Scene scene = new Scene(root);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    }
                    else{
                        Audio.sound1();
                        trans("Incorrect password! please enter your password");
                        return;
                    }
                }
            }
            if(!currentSt.getEmail().equals(SigninEmailTF.getText())){
                trans("There's no account with this email, please register!");
                return;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    

    @FXML
    private void backToSignUp(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUp.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        
    }
    
    private void trans(String m){
        SignInmsg.setText(m);
        TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(SignInalert);
            slide.setToX(0);
            slide.play();
    }

    @FXML
    private void SignInOkBtn(ActionEvent event) {
        SignInalert.setTranslateX(-500);
    }

    @FXML
    private void forgetPassword(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("forgetPassword.fxml"));
        Scene MySignInPage = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(MySignInPage);
        stage.show();
    }

}
