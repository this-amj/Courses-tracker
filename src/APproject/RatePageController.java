/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * FXML Controller class
 *
 * @author Reem Alotmi
 */
public class RatePageController implements Initializable {

    @FXML
    private Button star_1_btn;
    @FXML
    private Button star_2_btn;
    @FXML
    private Button star_3_btn;
    @FXML
    private Button star_4_btn;
    @FXML
    private Button star_5_btn;
    @FXML
    private Label label;
    
    private Student currentSt;
    
    private static double rate;
    
    private String courseName;
    
    private String courseId;
    
    private Course courseUpdate;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         star_1_btn.setGraphic(getEmptyStar());
        star_2_btn.setGraphic(getEmptyStar());
        star_3_btn.setGraphic(getEmptyStar());
        star_4_btn.setGraphic(getEmptyStar());
        star_5_btn.setGraphic(getEmptyStar());
        
    }    

    @FXML
    private void clickStarone(ActionEvent event) {
        star_1_btn.setGraphic(getStar());
        star_2_btn.setGraphic(getEmptyStar());
        star_3_btn.setGraphic(getEmptyStar());
        star_4_btn.setGraphic(getEmptyStar());
        star_5_btn.setGraphic(getEmptyStar());
       label.setText("Very Poor");
       rate=1;
    }

    @FXML
    private void clickStartow(ActionEvent event) {
        star_1_btn.setGraphic(getStar());
        star_2_btn.setGraphic(getStar());
        star_3_btn.setGraphic(getEmptyStar());
        star_4_btn.setGraphic(getEmptyStar());
        star_5_btn.setGraphic(getEmptyStar());
       label.setText("Poor");
       rate=2;
    }

    @FXML
    private void clickStarthree(ActionEvent event) {
        label.setText("Average");
        star_1_btn.setGraphic(getStar());
        star_2_btn.setGraphic(getStar());
        star_3_btn.setGraphic(getStar());
        star_4_btn.setGraphic(getEmptyStar());
        star_5_btn.setGraphic(getEmptyStar());
        rate=3;
    }

    @FXML
    private void clickStarfour(ActionEvent event) {
         label.setText("Good");
        star_1_btn.setGraphic(getStar());
        star_2_btn.setGraphic(getStar());
        star_3_btn.setGraphic(getStar());
        star_4_btn.setGraphic(getStar());
        star_5_btn.setGraphic(getEmptyStar());
        rate=4;
    }

    @FXML
    private void clickStarfive(ActionEvent event) {
        label.setText("Excllent");
        star_1_btn.setGraphic(getStar());
        star_2_btn.setGraphic(getStar());
        star_3_btn.setGraphic(getStar());
        star_4_btn.setGraphic(getStar());
        star_5_btn.setGraphic(getStar());
        rate=5;
    }
    
    public Polygon getStar() {

        Polygon star = new Polygon();

        star.setFill(Color.web("E3BD12"));
        star.setEffect(new DropShadow(1,Color.GRAY));
        star.getPoints().addAll(
                15.0, 0.0,
                25.0, 30.0,
                0.0, 12.5,
                30.0, 12.5,
                5.0, 30.0,
                15.0, 0.0);

        return star;
    }
    
     public Polygon getEmptyStar() {

        Polygon star = new Polygon();

        star.setFill(Color.GRAY);
        star.getPoints().addAll(
                15.0, 0.0,
                25.0, 30.0,
                0.0, 12.5,
                30.0, 12.5,
                5.0, 30.0,
                15.0, 0.0);

        return star;
    }
     
      public void currentUser(Student st) {
        this.currentSt=st;    
    }

    @FXML
    private void sendRate(ActionEvent event) {
        try {
            Session session = HibernateUtil.getSessionFactory().openSession();
            Query course = session.createQuery("from Course");
            List<Course> courselist = course.list();
            session.close();

            for (Course c : courselist) {
                if (c.getId().trim().equalsIgnoreCase(courseId.trim())) {
                    if (c.getRating() == null) {
                        c.setRating(String.valueOf(rate));
                        courseUpdate = c;
                        break;
                    } else {
                        rate = (double) ((Double.parseDouble(c.getRating()) + rate) / 2);
                        c.setRating(String.format("%.2f", rate));
                        courseUpdate = c;
                        break;
                    }
                }
            }
            Session session2 = HibernateUtil.getSessionFactory().openSession();
            session2.beginTransaction();
            session2.update(courseUpdate);
            session2.getTransaction().commit();
            session2.close();

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

    public void setCourseName(String name, String id){
        this.courseName = name;
        this.courseId = id;
    }


    
}
