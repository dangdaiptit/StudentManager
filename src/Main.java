import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Student> ls = new ArrayList<>();
        ls.add(new Student("SV1", "Vu Dang Dai", "HKI", "Java"));
        ls.add(new Student("SV2", "Nguyen Duc Khoi", "HKII", ".net"));
        ls.add(new Student("SV1", "Vu Dang Dai", "HKII", ".net"));
        ls.add(new Student("SV2", "Nguyen Duc Khoi", "HKI", ".net"));
        ls.add(new Student("SV3", "Ngo Tuan Anh", "HKI", "c/c++"));
        ls.add(new Student("SV4", "Nguyen Van Toan", "HKI", "c/c++"));
        int count = ls.size();
        while (true) {
            Manager.menu();
            int choice = Validation.checkInputLimit(1, 7);
            switch (choice) {
                case 1:
                    Manager.createStudent(count, ls);
                    break;
                case 2:
                    Manager.findAndSort(ls);
                    break;
                case 3:
                    Manager.updateOrDelete(ls, count);
                    Manager.listStudent(ls);
                    break;
                case 4:
                    Manager.report(ls);
                    break;
                case 5:
                    Manager.menuManagerExcel(ls);
                    break;
                case 6:
                    Manager.listStudent(ls);
                    break;
                case 7:
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }

        }
    }
}
