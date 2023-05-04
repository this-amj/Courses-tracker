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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.Query;
import org.hibernate.Session;

/**
 * FXML Controller class
 *
 * @author أمجاد
 */
public class ForgetPasswordController implements Initializable {

    @FXML
    private TextField forgetPR;
    @FXML
    private TextField resetPassword;
    @FXML
    private TextField confirmReset;
    @FXML
    private Label SignInmsg;
    @FXML
    private AnchorPane SignInalert;
    private Student std;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        SignInalert.setTranslateX(-500);
    }

    @FXML
    private void gotoSigninR(ActionEvent e) throws IOException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String qs = "from Student";
        Query q = session.createQuery(qs);
        List<Student> li = q.list();
        boolean found = false;

        for (Student s : li) {
            if (s.getEmail().equalsIgnoreCase(forgetPR.getText())) {
                found = true;
                std = s;
                break;
            }
        }
        if (found) {
            if (resetPassword.getText().trim().equals(confirmReset.getText().trim())) {
                String query = "update student \n"
                        + "set Password = '" + resetPassword.getText() + "'\n"
                        + "where Email = '" + std.getEmail() + "' and s_id = '" + std.getId() + "'";
                Query qu = session.createSQLQuery(query);
                int result = qu.executeUpdate();
                Parent root = FXMLLoader.load(getClass().getResource("SignIn.fxml"));
                Scene MySignInPage = new Scene(root);
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setScene(MySignInPage);
                stage.show();
            } else {
                trans("passwrd fields don't match");
            }
        } else {
            trans("This Email Don't Exist.");
        }
        session.getTransaction().commit();
        session.close();

    }

    private void trans(String m) {
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
}
