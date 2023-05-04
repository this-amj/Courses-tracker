/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APproject;

/**
 *
 * @author Reem Alotmi
 */
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

 
@Entity
@Table(name="COURSE")

public class Course implements Serializable {
    @Id
     @Column(name="Course_id")
     private String id;
     @Column(name="Name")
     private String name;
     @Column(name="Major")
     private String major;
     @Column(name="Level")
     private String level;
     @Column(name="Date")
     private String date;
     @Column(name="Verify_Code")
     private String vCode;
     @Column(name="Rating")
     private String rating;    

    public Course() {
    }

     
    public Course(String name, String major, String date) {
        this.name = name;
        this.major = major;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Course{" + "id=" + id + ", name=" + name + ", major=" + major + ", level=" + level + ", date=" + date + ", vCode=" + vCode + ", rating=" + rating + '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getvCode() {
        return vCode;
    }

    public void setvCode(String vCode) {
        this.vCode = vCode;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
