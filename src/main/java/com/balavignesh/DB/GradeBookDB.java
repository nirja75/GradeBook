/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.balavignesh.DB;

import com.balavignesh.gradebook.domain.GradeBook;
import com.balavignesh.gradebook.domain.GradeBookList;
import com.balavignesh.gradebook.domain.Student;
import com.balavignesh.gradebook.domain.StudentList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.ws.rs.core.Response;

/**
 *
 * @author BalaVignesh
 */
public class GradeBookDB {
    
    private GradeBookList gradeBookList = new GradeBookList();
    
    private Map<Long, StudentList> gradeWithStudent= new HashMap<Long, StudentList>();
    private static AtomicInteger idCounter = new AtomicInteger();
    
     public long createGradebook(String title){
        GradeBook gradeBook = new GradeBook();
        idCounter.incrementAndGet();
        gradeBook.setGradeId(idCounter.longValue());
        gradeBook.setGradeTitle(title);
     
       gradeBookList.getGradebook().add(gradeBook);
       return idCounter.longValue();
    }
     
     
    public GradeBook filterGradeBookByName(String name) {
        if(name == null || name.trim().length()==0 || gradeBookList.getGradebook()==null || gradeBookList.getGradebook().size()==0){
            return null;
        }
        return gradeBookList.getGradebook().stream().filter(student->name.equalsIgnoreCase(student.getGradeTitle()))
                .findFirst().orElse(null);
    } 
    
    public GradeBook filterGradeBookById(long id) {
        if(id == 0 || gradeBookList.getGradebook()==null || gradeBookList.getGradebook().size()==0){
            return null;
        }
        return gradeBookList.getGradebook().stream().filter(student->id == student.getGradeId())
                .findFirst().orElse(null);
    } 
    
    private Student filterStudent(StudentList studentList, String name) {
        if(name == null || name.trim().length()==0 || studentList.getStudent()==null || studentList.getStudent().size()==0){
            return null;
        }
        return studentList.getStudent().stream().filter(student->name.equalsIgnoreCase(student.getName()))
                .findFirst().orElse(null);
    }

    public GradeBookList getGradeBookList() {
        return gradeBookList;
    }

    public void createStudent(long id, String name, String grade) {
        Student student = new Student();
        student.setName(name);
        student.setGrade(grade);
        
        if(gradeWithStudent.containsKey(id)){
           Student studentSaved = filterStudent(gradeWithStudent.get(id),name);
        
        if(studentSaved == null ){
           
            gradeWithStudent.get(id).getStudent().add(student);
        }else{
            studentSaved.setGrade(grade);
        }
        }else{
            StudentList studentList = new StudentList();
            studentList.getStudent().add(student);
            gradeWithStudent.put(id, studentList);
        }
    }

    public StudentList getAllStudents(long id) {
        return gradeWithStudent.containsKey(id) ? gradeWithStudent.get(id):null;
    }
    
    public boolean validGrade(String grade) {
        ArrayList<String> grades = new ArrayList<String>(
                Arrays.asList("A+","A-","B+","B-","C+","C-","D+","D-","A","B","C","D","E","F","I","W","Z"));
        return grades.stream().filter(gr->gr.equalsIgnoreCase(grade)).count()==1;
    }
     
    
}
