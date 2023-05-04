/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author imbes
 */
public class ProfileController implements Initializable {

    @FXML
    private Label Nameprofilelb;
    @FXML
    private Label Emailprofilelb;
    @FXML
    private Label Idprofilelb;
    @FXML
    private Label Certificateprofilelb;
    @FXML
    private Label Surnameprofilelb;
    @FXML
    private AnchorPane sendPopUp;
    @FXML
    private ComboBox<String> courseCombo;
    @FXML
    private Label error;
    @FXML
    private Button print;
    
    private Student currentSt;

    List<Course> courselist = null;

   private String stdId;
    private String courseName;
    private String date;
    @FXML
    private Button homeBtn;
    @FXML
    private Button courseBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        sendPopUp.setTranslateX(-500);
    }

    private void populateBox() {
        try {
            if(courselist == null) {
            System.out.println(Idprofilelb.getText());
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Course");
            Query q2 = session.createQuery("from Take");
            courselist = q.list();
            List<Take> l = q2.list();
            List<String> course = new ArrayList();
            for(Take t: l){
                for(Course c : courselist){
                    if(t.getStatus().equalsIgnoreCase("confirmed")){
                        if(c.getId().equalsIgnoreCase(t.getCourseId())) {
                            if(t.getStudentId().equalsIgnoreCase(stdId)){
                                if(!course.contains(c.getName()))
                                course.add(c.getName());
                    } 
                }
            }
                }
            }
            session.getTransaction().commit();
            session.close();
            if (!course.isEmpty()) {
                error.setText("");
                this.courseName = course.get(0);
                courseCombo.setValue(this.courseName);
                String[] a = new String[course.size()];
                courseCombo.getItems().addAll(course.toArray(a));
            } else {
                error.setText("No confirmed Courses");
                print.setDisable(true);
                courseCombo.setEditable(false);
                courseCombo.setDisable(true);
            }
                           } 
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @FXML
    private void SendCertificate(ActionEvent event) throws IOException {
        trans();
        populateBox();
    }

    @FXML
    public void getSelected(ActionEvent e) {
        String selected = courseCombo.getValue();
        this.courseName = selected;
        for (Course c : courselist) {
            if (selected.trim().equalsIgnoreCase(c.getName().trim()));
            {
                this.date = c.getDate();
            }
        }
    }

    private void trans() {
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.2));
        slide.setNode(sendPopUp);
        slide.setToX(0);
        slide.play();
    }

    @FXML
    private void sendBtn(ActionEvent event) {
        sendPopUp.setTranslateX(-500);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("certificate.fxml"));
            Parent root = loader.load();
            certificateController c = loader.getController();
            c.setValues(this.currentSt, this.courseName, this.date);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void cnclBtn(ActionEvent event) {
        sendPopUp.setTranslateX(-500);
    }

    @FXML
    private void profileToHomeBtn(ActionEvent event) {
        if(homeBtn.isHover()){
          Audio.sound();
        }
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml")); //to get the fxml file to be displayed
            Parent root = loader.load(); //put it as a root 
            HomePageController home = loader.getController(); //to take the methods in this calss
            home.currentUser(this.currentSt);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @FXML
    private void profileToCourseBtn(ActionEvent event) {
        if(courseBtn.isHover()){
          Audio.sound();
        }
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


    @FXML
    private void profileToHomeIcon(MouseEvent event) {
        if(homeBtn.isHover()){
          Audio.sound();
        }
         try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml")); //to get the fxml file to be displayed
            Parent root = loader.load(); //put it as a root 
            HomePageController home = loader.getController(); //to take the methods in this calss
            home.currentUser(this.currentSt);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    @FXML
    private void profileToCourseIcon(MouseEvent event) {
        if(courseBtn.isHover()){
          Audio.sound();
        }
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


    public void currentUser(Student st) {
        this.stdId = st.getId();
        Nameprofilelb.setText(st.getFirstName());
        Surnameprofilelb.setText(st.getLastName());
        Idprofilelb.setText(st.getId());
        Emailprofilelb.setText(st.getEmail());
        this.currentSt = st;
    }
 

    @FXML
    private void signOut(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("StartPage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
}
