package Students.demo;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class StudentController {
    private final StudentRepository repository;

    StudentController(StudentRepository repository) {
        this.repository = repository;
    }
    //display all students
    @GetMapping("/student")
    public List<Student> displayAllStudents() {
        return repository.findAll();
    }

    @PostMapping("/student")
    public Student addNewStudent(@RequestBody Student newStudent) {
        return repository.save(newStudent);
    }
    // add a single new student

    @GetMapping("/student/{id}")
    public Student fetchById(@PathVariable Long id) {

        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }
    @PutMapping("/student/{id}")
    public Student replaceStudent(@RequestBody Student newStudent, @PathVariable Long id) {

        return repository.findById(id)
                .map(student -> {
                    student.setFirstName(newStudent.getFirstName());
                    student.setSurname(newStudent.getSurname());
                    student.setDateOfBirth(newStudent.getDateOfBirth());
                    student.setGender(newStudent.getGender());
                    student.setSex(newStudent.getSex());
                    student.setPhoneNumber(newStudent.getPhoneNumber());
                    student.setAddress(newStudent.getAddress());
                    return repository.save(student);
                })
                .orElseThrow(() -> new StudentNotFoundException(id));
    }
    @DeleteMapping("/student/{id}")
    public void deleteStudent(@PathVariable Long id) throws StudentNotFoundException {
        repository.deleteById(id);
    }
}
