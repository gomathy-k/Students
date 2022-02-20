package Students.demo;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.jupiter.api.*;

import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) 
public class TestingWebApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    protected String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    protected <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, JsonMappingException, IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    @Test
    @Order(1)
    public void showAllStudents() throws Exception { //test: retrieve all student records
        this.mockMvc.perform(get("/student"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].firstName", Matchers.is("Jane")));
    }
    @Test
    @Order(2)
    public void getStudentById_goodRecord() throws Exception { //test: retrieve student with supplied ID
        this.mockMvc.perform(get("/student/220003"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", Matchers.is("Jason")));
    }

    @Test
    @Order(3)
    public void createStudent() throws Exception { //test: create a new student from supplied data
        String uri = "/student"; //this for post endpoint
        Student student = new Student("TestFname", "TestSname", "01/01/2020", "Male", "Male", "1234567", "Test Address");
        String inputJson = this.mapToJson(student);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                        .andExpect(jsonPath("$", Matchers.notNullValue()))
                        .andExpect(jsonPath("$.firstName", Matchers.is("TestFname")))
                        .andDo(print())
                        .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    @Order(4)
    public void updateStudent_goodRecord() throws Exception { //test: update an existing student
        String uri = "/student/220003";
        Student student = new Student("Changed", "Changed", "0101200", "Male", "Male", "1234567", "abc");
        String inputJson = this.mapToJson(student);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(inputJson))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", Matchers.is("Changed")))
                    .andExpect(jsonPath("$.surname", Matchers.is("Changed")))
                    .andReturn();
    }
    @Test
    @Order(5)
    public void updateStudent_recordNotFound() throws Exception { //test: update a non-existing student
        String uri = "/student/9";
        Student student = new Student("Changed2", "Changed2", "0101200", "Male", "Male", "1234567", "abc");
        String inputJson = this.mapToJson(student);
        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(inputJson))
                        .andDo(print())
                        .andExpect(status().isNotFound())
                        .andExpect(content().string(containsString("Could not find employee 9")));

    }
    @Test
    @Order(6)
    public void deleteStudent_goodRecord() throws Exception { //test: delete an existing student
        String uri = "/student/220003";
        this.mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andDo(print())
                .andExpect(status().isOk());
    }

 /*   @Test
    public void deleteStudent_recordNotFound() throws Exception { //test: delete a non-existing student

        String uri = "/student/9";
        this.mockMvc.perform(MockMvcRequestBuilders.delete(uri))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException() instanceof StudentNotFoundException))
                .andExpect(result ->
                        assertEquals("Student with ID 9 does not exist.", result.getResolvedException().getMessage()));
    }*/

}





