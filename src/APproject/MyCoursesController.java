/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import sun.net.www.content.text.Generic;

/**
 * FXML Controller class
 *
 * @author imbes
 */

public class MyCoursesController implements Initializable {

    @FXML
    private Label label;

    private Student currentSt;
    @FXML
    private TableView<Course> tabel;
    @FXML
    private TableColumn<Course, String> TableCourseName;
    @FXML
    private TableColumn<Course, String> TableDate;
    @FXML
    private TableColumn<Course, String> TableMajor;

    ObservableList<Course> list = FXCollections.observableArrayList();
    @FXML
    private AnchorPane ConfirmPopUp;
    @FXML
    private Label StatusConfirmPopUp;
    @FXML
    private Label CourseNameConfirmPopUp;
    @FXML
    private Button rateConfirmBtn;
    private String selectedCourse;
    private String selectedCourseId;
    @FXML
    private Label MajorConfirmPopUp;
    
    @FXML
    private Button homeBtn;
    @FXML
    private Button profileBtn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       ConfirmPopUp.setTranslateX(-500);
       
       Session session = HibernateUtil.getSessionFactory().openSession();
                Query course = session.createQuery("from Course");
                Query take = session.createQuery("from Take");
                List<Course> courselist = course.list();
                List<Take> takelist = take.list();
                session.close();
                
        tabel.getSelectionModel().selectedItemProperty().addListener((e) -> {
            tabel.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            String selected = tabel.getSelectionModel().getSelectedItems().get(0).getName();
                String courseid = null;
                String courseMajor = null;
                for (Course c : courselist) {
                    if (c.getName().trim().equalsIgnoreCase(selected.trim())) {
                        courseid = c.getId();
                        courseMajor= c.getMajor();
                        break;
                    }
                }
for (Take t : takelist) {
                if (t.getCourseId().trim().equalsIgnoreCase(courseid.trim()) && t.getStudentId().equalsIgnoreCase(currentSt.getId())) {

                    if (t.getStatus().equalsIgnoreCase("confirmed")) {
                        rateConfirmBtn.setText("Rate");
                        selectedCourseId = courseid;
                        selectedCourse = selected;
                        trans(selected, t.getStatus(), courseMajor);
                    } else {
                        rateConfirmBtn.setText("Confirm");
                        selectedCourseId = courseid;
                        selectedCourse = selected;
                        trans(selected, t.getStatus(), courseMajor);
                    }

                    break;
                }
            }
            
        });
       
    }

    public void currentUser(Student st) {
        this.currentSt=st;
         try {
            TableCourseName.setCellValueFactory(new PropertyValueFactory<>("Name"));
            TableMajor.setCellValueFactory(new PropertyValueFactory<>("Major"));
            TableDate.setCellValueFactory(new PropertyValueFactory<>("Date"));

            Session session1 = HibernateUtil.getSessionFactory().openSession();
            Transaction t = session1.beginTransaction();
            Query query = session1.createSQLQuery("SELECT * FROM Course WHERE Course_id in (SELECT Course_id FROM Take WHERE S_id= :hi)").addEntity(Course.class);
            query.setString("hi", currentSt.getId());
            //System.out.println("connection sccccccccccuceeded");
            List<Course> courseList = query.list();

            t.commit();
            session1.close();

            System.out.println("out of session");
            for (Course i : courseList) {
            //    System.out.println(i.toString());
                list.add(new Course(i.getName(), i.getMajor(), i.getDate()));
            }

            tabel.setItems(list);

        } catch (Exception e) {

        }
    }

    @FXML
    private void courseToHomeIcon(MouseEvent event) {
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
    private void courseToHomeBtn(ActionEvent event) {
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
    private void courseToProfileIcon(MouseEvent event) {
        if(profileBtn.isHover()){
          Audio.sound();
        }
         try {
            FXMLLoader loadProfile = new FXMLLoader(getClass().getResource("Profile.fxml")); //to get the fxml file to be displayed
            Parent root = loadProfile.load(); //put it as a root 
            ProfileController profile = (ProfileController) loadProfile.getController(); 
            profile.currentUser(this.currentSt);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void courseToProfileBtn(ActionEvent event) {
        if(profileBtn.isHover()){
          Audio.sound();
        }
        try {
            FXMLLoader loadProfile = new FXMLLoader(getClass().getResource("Profile.fxml")); //to get the fxml file to be displayed
            Parent root = loadProfile.load(); //put it as a root 
            ProfileController profile = (ProfileController) loadProfile.getController();
            profile.currentUser(this.currentSt);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @FXML
    private void table(MouseEvent event) {
    }

    @FXML
    private void ConfirmBtn(ActionEvent event) {
        if (rateConfirmBtn.getText().equals("Rate")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("RatePage.fxml")); //to get the fxml file to be displayed
                Parent root = loader.load(); //put it as a root 
                RatePageController RatePage = loader.getController(); //to take the methods in this calss
                RatePage.setCourseName(this.selectedCourse, this.selectedCourseId);
                RatePage.currentUser(this.currentSt);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Verify.fxml")); //to get the fxml file to be displayed
                Parent root = loader.load(); //put it as a root 
                VerifyController verify = loader.getController();
                verify.setCourseName(this.selectedCourse, this.selectedCourseId); //to take the methods in this calss
                verify.currentUser(this.currentSt);

                Scene scene = new Scene(root);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @FXML
    private void CancelConfirmBtn(ActionEvent event) {
        ConfirmPopUp.setTranslateX(-500);
    }
    
    private void trans(String name, String status, String major){
        CourseNameConfirmPopUp.setText(name);
        StatusConfirmPopUp.setText(status);
        MajorConfirmPopUp.setText(major);
        TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(ConfirmPopUp);
            slide.setToX(0);
            slide.play();
    }


}

