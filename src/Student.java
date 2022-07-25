import java.util.Comparator;

public class Student implements Comparator<Student> {
    private String id;
    private String studentName;
    private String semester;
    private String courseName;

    public Student() {
    }


    public Student(String id, String studentName, String semester, String courseName) {
        this.id = id;
        this.studentName = studentName;
        this.semester = semester;
        this.courseName = courseName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void printStudent(){
        System.out.printf("%-10s%-20s%-15s%-15s\n",id, studentName, semester, courseName);
    }

//    @Override
//    public int compareTo(Student o) {
//        return o.studentName.compareTo(this.studentName);
//    }

    @Override
    public int compare(Student o1, Student o2) {
        return o1.getStudentName().compareTo(o2.getStudentName());
    }
}
