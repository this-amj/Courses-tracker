/**
 *
 * @author Reem Alotmi
 */
package APproject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TAKE")
public class Take implements java.io.Serializable {

    @Id
    @Column(name = "S_id")
    private String studentId;
    @Id
    @Column(name = "Course_id")
    private String courseId;
    @Column(name = "status")
    private String status;

    public Take() {
    }

    public Take(String studentId, String courseId, String status) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Take{" + "studentId=" + studentId + ", courseId=" + courseId + ", status=" + status + '}';
    }
    
    

}
