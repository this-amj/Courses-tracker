/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author أمجاد
 */
public class certificateController implements Initializable {

    @FXML
    private Text body;
    @FXML
    private Label date;
    @FXML
    private Label name;
    @FXML
    private Label courseName;
    @FXML
    private Node pane;
    @FXML
    private ImageView logo;
    @FXML
    private ImageView proLogo;
    @FXML
    private Text proCredit;
    
    
    private Student currentSt;
    @FXML
    private Label label;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            print();
        } catch (Exception ex) {
            Logger.getLogger(certificateController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void print() throws Exception {
        try {
            String data = "";
            File read = new File("file\\c.txt");
            Scanner myReader = new Scanner(read);
            while (myReader.hasNextLine()) {
                data += myReader.nextLine();
            }
            body.setText(data);
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
    
        @FXML
    private void goBackToProfile(MouseEvent event) {
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

    public void setValues(Student st, String cName, String cDate) throws IOException {
        this.date.setText("Issued On: " + cDate);
        this.name.setText(st.getFirstName() + " " + st.getLastName());
        this.courseName.setText(cName);
        this.currentSt = st;

    }
    
    
    @FXML
    public void download() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("png", "*.png"));
        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            SnapshotParameters sp = new SnapshotParameters();
            WritableImage image = pane.snapshot(sp, null);
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
            System.out.println(file.getName());
        }
    }

    @FXML
    private void CertifToProfile(MouseEvent event) {
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

}
