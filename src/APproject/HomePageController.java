/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.TranslateTransition;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
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
public class HomePageController implements Initializable {
    

    @FXML
    private Label label;
    @FXML
    private Label UserName;
    //courses selection
    @FXML
    private Button majorbtn;
    @FXML
    private Button advancedbtn;
    @FXML
    private Button beginnerbtn;
    @FXML
    private Button newestbtn;
    @FXML
    private ListView majorlist;
    private Student currentSt;
    @FXML
    private AnchorPane enrollPane;
    @FXML
    private Label courseNamelbl;
    @FXML
    private Label courseDatelbl;
    private List<Course> CourseList;
    private String selected;
    //private Take take;
    @FXML
    private TextField searchTF;
    @FXML
    private Label enrollmsg;
    @FXML
    private Button courseBtn;
    @FXML
    private Button profileBtn;

    public HomePageController() {
        
        
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        enrollPane.setTranslateX(-500);
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Transaction tx= session.beginTransaction();
            Query query2 = session.createQuery("from Course");
            CourseList= query2.list();
            tx.commit();
            session.close();
            
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        
        
        
        // SignUpFXMLController.
        majorlist.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {            
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    selected = majorlist.getSelectionModel().getSelectedItems().toString();
                    if (CourseList!= null) {
                        majorlist.getItems().clear();
                        for (Course i : CourseList) {
                            if (i != null && i.getMajor().equalsIgnoreCase(selected.substring(1, selected.length() - 1))) {
                                majorlist.getItems().add(i.getName());
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            } 
        });
        
        //here
            majorlist.getSelectionModel().selectedItemProperty().addListener((Observable e) ->  {
            selected = majorlist.getSelectionModel().getSelectedItems().toString();
            Take take= new Take();
            if (!CourseList.isEmpty()) {
                for (Course i : CourseList) {
                    if (i != null && i.getName().equalsIgnoreCase(selected.substring(1, selected.length() - 1))) {
                        enrollmsg.setText("");
                        trans(i.getName(),i.getDate());
                    }
                }
            }
            });
        
        
    }



    //COURSES BUTTONS
   @FXML
    private void majorbtnHP(ActionEvent event) {
        try {
            if (!majorlist.getItems().isEmpty()) {
                majorlist.getItems().clear();
            }
            if (CourseList != null) {
                for (Course i : CourseList) {
                    if (!majorlist.getItems().contains(i.getMajor())) {
                        majorlist.getItems().add(i.getMajor());
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.print(e.toString());
        }
    }

    @FXML
    private void newestbtnHP(ActionEvent event) {
        try {
            if (!majorlist.getItems().isEmpty()) {
                majorlist.getItems().clear();
            }
            String[] date = new String[CourseList.size()];
            if (CourseList != null) {
                for (int i = 0; i < CourseList.size(); i++) {
                    date[i] = CourseList.get(i).getDate();
                }
                Arrays.sort(date);

                for (int i = date.length - 1; i >= 0; i--) {
                    for (int j = 0; j < CourseList.size(); j++) {
                        if (date[i].equalsIgnoreCase(CourseList.get(j).getDate()) && !majorlist.getItems().contains(CourseList.get(j).getName())) {
                            majorlist.getItems().add(CourseList.get(j).getName());
                        }
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
    }

    @FXML
    private void advancedbtnHP(ActionEvent event) {
        try {
            if (!majorlist.getItems().isEmpty()) {
                majorlist.getItems().clear();
            }
            if (CourseList != null) {
                for (Course i : CourseList) {
                    if (i.getLevel().equalsIgnoreCase("advanced")  && !(majorlist.getItems().contains(i.getName()))) {
                        majorlist.getItems().add(i.getName());
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    @FXML
    private void beginnerbtnHP(ActionEvent event) {
        try {
            if (!majorlist.getItems().isEmpty()) {
                majorlist.getItems().clear();
            }
            if (CourseList != null) {
                for (Course i : CourseList) {
                    if (i.getLevel().equalsIgnoreCase("beginner")) {
                        majorlist.getItems().add(i.getName());
                    }
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
    }
    
    @FXML
    private void SearchtextfHP(ActionEvent event) {
        try {
            String search = null;
            if (searchTF.getText() != null && CourseList != null) {
                majorlist.getItems().clear();
                search = searchTF.getText();
                for (int i = 0; i < CourseList.size(); i++) {
                    String name = CourseList.get(i).getName().substring(0, search.length());
                    if (name.equalsIgnoreCase(search) && !majorlist.getItems().contains(CourseList.get(i).getName())) {
                        majorlist.getItems().add(CourseList.get(i).getName());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
////////////////////////////////////////////////////////////////////////////////////
    //GO TO PROFILE
    @FXML
    private void homeToPofilePicIcon(MouseEvent event) {
        if(profileBtn.isHover()){
            Audio.sound();
        }
        try {
            FXMLLoader loadProfile = new FXMLLoader(getClass().getResource("Profile.fxml")); //to get the fxml file to be displayed
            Parent root = loadProfile.load(); //put it as a root 
            ProfileController profile = (ProfileController) loadProfile.getController(); //to take the methods in this calss
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
    private void toHomeProfilePicBtn(ActionEvent event) {
        if(profileBtn.isHover()){
            Audio.sound();
        }
        try {
            FXMLLoader loadProfile = new FXMLLoader(getClass().getResource("Profile.fxml")); //to get the fxml file to be displayed
            Parent root = loadProfile.load(); //put it as a root 
            ProfileController profile = (ProfileController) loadProfile.getController(); //to take the methods in this calss
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
    private void homeToProfileIcon(MouseEvent event) {
        if(profileBtn.isHover()){
            Audio.sound();
        }
        try {
            FXMLLoader loadProfile = new FXMLLoader(getClass().getResource("Profile.fxml")); //to get the fxml file to be displayed
            Parent root = loadProfile.load(); //put it as a root 
            ProfileController profile = (ProfileController) loadProfile.getController(); //to take the methods in this calss
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
    private void homeToProfileBtn(ActionEvent event) {
        if(profileBtn.isHover()){
            Audio.sound();
        }
        try {
            FXMLLoader loadProfile = new FXMLLoader(getClass().getResource("Profile.fxml")); //to get the fxml file to be displayed
            Parent root = loadProfile.load(); //put it as a root 
            ProfileController profile = (ProfileController) loadProfile.getController(); //to take the methods in this calss
            profile.currentUser(this.currentSt);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

////////////////////////////////////////////////////////////////////////////////////

    //GO TO COURSES
    @FXML
    private void homeToCoursesIcon(MouseEvent event) {
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
    private void homeToCoursesBtn(ActionEvent event) {
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
    
    ////////////////////////////////////////////////////////////////////////////////

    public void currentUser(Student st) {
        UserName.setText(st.getFirstName());
        this.currentSt=st;
        try {
            if (!majorlist.getItems().isEmpty()) {
                majorlist.getItems().clear();
            }
            if (CourseList != null) {
                for (Course i : CourseList) {
                    majorlist.getItems().add(i.getName());
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.toString());
        }
    }
    
    private void trans(String name, String date){
        courseNamelbl.setText(name);
        courseDatelbl.setText(date);
        TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(enrollPane);
            slide.setToX(0);
            slide.play();
    }

    @FXML
    private void Enroll(ActionEvent event) {
        enrollmsg.setText("");
        try {
            Session session2 = HibernateUtil.getSessionFactory().openSession();
            Transaction tx = session2.beginTransaction();
            Query query2 = session2.createQuery("from Take");
            List<Take> taketabel = query2.list();
            tx.commit();
            session2.close();
            if (!CourseList.isEmpty()) {
                for (Course i : CourseList) {
                    if ((i != null) && i.getName().equalsIgnoreCase(selected.substring(1, selected.length() - 1))) {

                        Take take = new Take();
                        take.setCourseId(i.getId());
                        take.setStatus("unconfirmed");
                        take.setStudentId(currentSt.getId());

                        if (!taketabel.isEmpty()) {
                            for (Take j : taketabel) {
                                if ((j != null) && j.getCourseId().equals(take.getCourseId()) && j.getStudentId().equals(take.getStudentId())) {
                                    enrollmsg.setText("You have already enrolled to this course!");
                                    take=null;
                                    return;
                                } 
                            }
                            Session session3 = HibernateUtil.getSessionFactory().openSession();
                            Transaction transaction = session3.beginTransaction();       
                            session3.save(take);
                            transaction.commit();
                            session3.close();
                        }
                    }
                }
            }
            enrollPane.setTranslateX(-500);
            
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        
        
    }

    @FXML
    private void cancelEnroll(ActionEvent event) {
        enrollPane.setTranslateX(-500);
    }

}
