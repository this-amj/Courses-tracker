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
import org.hibernate.Session;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author imbes
 */
public class SignUpController implements Initializable {

    @FXML
    private Label SignUpNamelb;
    @FXML
    private Label SignUpSurNamelb;
    @FXML
    private Label SignUpEmaillb;
    @FXML
    private Label SignUpPasswordlb;
    @FXML
    private Button CreateAccountbtn;
    @FXML
    private Label label;
    @FXML
    private Label backToSignIn;
    @FXML
    private TextField SignUpNameTF;
    @FXML
    private TextField SignUpSurNameTF;
    @FXML
    private TextField SignUpEmailTF;
    @FXML
    private PasswordField passwordTF;
    @FXML
    private TextField SignUpIdTF;
    @FXML
    private Label SignUpIdlbl;
    @FXML
    private AnchorPane alert;
    @FXML
    private Label msg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alert.setTranslateX(-500);
        
        
    }

    @FXML
    private void CreateActgotoHP(ActionEvent event) throws IOException {
        
        Student new_st = new Student();

        try {
            Session session1 = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session1.beginTransaction();
            Query query = session1.createSQLQuery("SELECT * FROM student WHERE S_id= :hi ").addEntity(Student.class);
            query.setString("hi", SignUpIdTF.getText());
            System.out.println("connection suceeded");
            Student studentID = (Student) query.uniqueResult();
            t.commit();
            session1.close();

            if (SignUpNameTF.getText().isEmpty() && SignUpSurNameTF.getText().isEmpty() && SignUpIdTF.getText().isEmpty() && SignUpEmailTF.getText().isEmpty() && passwordTF.getText().isEmpty()) {
                Audio.sound1();
                trans("Please Enter your Information!");
                return;
            }
            if (SignUpNameTF.getText().isEmpty()) {
                Audio.sound1();
                trans("Please Enter your Name!");
                return;
            }
            if (SignUpSurNameTF.getText().isEmpty()) {
                Audio.sound1();
                trans("Please Enter your  Surname!");
                return;
            }
            if (SignUpEmailTF.getText().isEmpty()) {
                Audio.sound1();
                trans("Please Enter your Email!");
                return;
            }
            if (!(SignUpEmailTF.getText().contains("@"))) {
                Audio.sound1();
                SignUpEmailTF.clear();
                trans("Invalid Email!");
                return;
            }
            if (SignUpIdTF.getText().isEmpty()) {
                Audio.sound1();
                trans("Please Enter your Id!");
                return;
            }
            if (studentID != null) {
                SignUpIdTF.clear();
                Audio.sound1();
                trans("This Id is already taken! try again");
                return;
            }
            if (passwordTF.getText().isEmpty()) {
                Audio.sound1();
                trans("Please Enter a password!");
                return;
            }

            Session session2 = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session2.beginTransaction();

            new_st.setFirstName(SignUpNameTF.getText());
            new_st.setLastName(SignUpSurNameTF.getText());
            new_st.setId(SignUpIdTF.getText());
            new_st.setEmail(SignUpEmailTF.getText());
            new_st.setPassword(passwordTF.getText());

            session2.save(new_st);
            tx.commit();
            session2.close();
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml")); //to get the fxml file to be displayed
        Parent root = loader.load(); //put it as a root 
        
        //connecting current user to all pages
        //home
        HomePageController home = loader.getController(); //to take the methods in this calss
        home.currentUser(new_st);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

        } catch (Exception e) {
            trans(e.toString());
        }
        

    }

    @FXML
    private void backToSignIn(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
        Scene MySignInPage = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(MySignInPage);
        stage.show();
    }

    @FXML
    private void okBtn(ActionEvent event) {
        alert.setTranslateX(-500);
    }
    
    private void trans(String m){
        msg.setText(m);
        TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(alert);
            slide.setToX(0);
            slide.play();
    }

}
