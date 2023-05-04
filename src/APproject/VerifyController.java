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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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
public class VerifyController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private Button signinBtn;
    @FXML
    private Label msgForAttendence;
    @FXML
    private TextArea confirm;
    @FXML
    private AnchorPane alert;
    @FXML
    private Label msg;

    private Student currentSt;
    private String courseName;
    private String courseId;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        alert.setTranslateX(-500);
    }

    @FXML
    private void checkifAttended(ActionEvent event) throws IOException {
        if (confirm.getText().isEmpty()) {
            Audio.sound1();
            trans("Please insert Code");
        }
            else {
            Session session = HibernateUtil.getSessionFactory().openSession();
            String queryString = "from Course";
            Query query = session.createQuery(queryString);
            List<Course> list = query.list();
            session.close();
            for (Course c : list) {
                boolean thisCourse = c.getName().equalsIgnoreCase(courseName);
                boolean correctConfCode = confirm.getText().equalsIgnoreCase(c.getvCode());
                if (!thisCourse || !correctConfCode) {
                    trans("Wrong verificatioin number");
                } else if (thisCourse && correctConfCode) {
                    Session s = HibernateUtil.getSessionFactory().openSession();
                    s.beginTransaction();
                    //String q = "update from Take t set t.status = 'Confirmed' where t.studentId = " + currentSt.getId() + " && t.courseId = " + courseId;
                    String q = "update take \n"
                            + "set status = 'Confirmed'\n"
                            + "where Course_id = '" + courseId  + "' and s_id = '" + currentSt.getId() + "'";
                    Query qu = s.createSQLQuery(q);
                    int result = qu.executeUpdate();
                    s.getTransaction().commit();
                    s.close();
                    gotoConfirmation(event);
                }
            }
        }
    }

    public void gotoConfirmation(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("VerifyConfirmtion.fxml")); //to get the fxml file to be displayed
        Parent root = loader.load(); //put it as a root 
        VerifyConfirmtionController verifyconf = loader.getController();
        verifyconf.currentUser(this.currentSt);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void currentUser(Student st) {
        this.currentSt = st;
    }

    public void setCourseName(String name, String id){
        this.courseName = name;
        this.courseId = id;
    }
    @FXML
    public void okBtn(ActionEvent e){
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

    @FXML
    private void verifyToCourses(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MyCourses.fxml"));
            Parent root = loader.load();
            MyCoursesController courses = loader.getController();
            courses.currentUser(this.currentSt);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}