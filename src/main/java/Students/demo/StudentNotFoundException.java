package Students.demo;

public class StudentNotFoundException extends RuntimeException {
    StudentNotFoundException(Long id) {
        super("Could not find employee " + id);
    }
}
