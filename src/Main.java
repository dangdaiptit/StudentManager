import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Student> ls = new ArrayList<>();
//        Validation validation = new Validation();
        ls.add(new Student("1", "Vu Dang Dai", "HKI", "Java"));
        ls.add(new Student("2", "Nguyen Duc Khoi", "HKII", ".net"));
        int count = 2;
        while (true) {
            Manager.menu();
            int choice = Validation.checkInputLimit(1,5);
            switch (choice){
                case 1:
                    Manager.createStudent(count,ls);
                    break;
                case 2:
                    Manager.findAndSort(ls);
                    break;
                case 3:
                    Manager.updateOrDelete(ls,count);
                    for (Student student : ls) {
                        student.printStudent();
                    }
                    break;
                case 4:
                    Manager.report(ls);
                    break;
                case 5:
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }

        }
    }
}
