/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.balavignesh.gradebook.services;

import com.balavignesh.DB.GradeBookDB;
import com.balavignesh.gradebook.connection.SendRequest;
import com.balavignesh.gradebook.domain.GradeBook;
import com.balavignesh.gradebook.domain.GradeBookList;
import com.balavignesh.gradebook.domain.Student;
import com.balavignesh.gradebook.domain.StudentList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author BalaVignesh
 */
@Path("/")
public class StudentResourse {
    
    private GradeBookDB gradeBookDb = new GradeBookDB();
    
    
    @GET
    @Path("/show")
    public String showStudent() throws Exception
    {
        try{
        SendRequest s = new SendRequest();
         String str = s.SendRequest("http://gradebook.balavignesh.com:8080/GradeBook/resources/student", "GET");
        System.out.println(str);
        }catch(Exception e){
            return e.getMessage();
        }
        return ("Hi");
        
    }
    
    @GET
    @Path("/gradebook")
    @Produces(MediaType.TEXT_XML+";charset=utf-8")
    public GradeBookList getGradeBookList(){
        return gradeBookDb.getGradeBookList();
    }
    
    @POST
    @Path("/gradebook/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createGradeBooks(@PathParam("name") String name){
         if(name==null || "".equalsIgnoreCase(name)){
            throw new BadRequestException();
        }
        GradeBook gradePresent = gradeBookDb.filterGradeBookByName(name);
       if(gradePresent == null ){
          long gradeId = gradeBookDb.createGradebook(name);
          return Response.status(javax.ws.rs.core.Response.Status.CREATED).entity(gradeId).build();
        }else{
            throw new BadRequestException("the title already exists");
        }
    }
    
    
    @POST
    @Path("/gradebook/{id}/student/{name}/grade/{grade}")
    public Response createStudent(@PathParam("id") long id,@PathParam("name") String name,@PathParam("grade") String grade){
        if(id == 0 || grade==null || "".equalsIgnoreCase(grade) || !gradeBookDb.validGrade(grade) ){
            throw new BadRequestException();
        }
        GradeBook gradePresent = gradeBookDb.filterGradeBookById(id);
       if(gradePresent != null ){
          gradeBookDb.createStudent(id,name,grade);
          return Response.ok().build();
        }else{
            throw new BadRequestException("Grade Not Present");
        }
    }
    
    
    @GET
    @Path("/gradebook/{id}/student")
    @Produces("application/XML")
    public StudentList getAllStudent(@PathParam("id")  long id){
       return  gradeBookDb.getAllStudents(id);
    }
    
   
    
    
    
    
    
    
    /*
    
    
    private StudentList studentList = new StudentList();
    
     
    
    
    @POST
    @Path("{name}/grade/{grade}")
    public Response addStudent(@PathParam("name") String name,@PathParam("grade") String grade){
        return addOrReplaceStudent(name,grade);
    }
    
    @PUT
    @Path("{name}/grade/{grade}")
    public Response modifyStudent(@PathParam("name") String name,@PathParam("grade") String grade){
        return addOrReplaceStudent(name,grade);
    }
    
    @POST
    @Path("{name}/grade")
    public Response addEmptyStudent(@PathParam("name") String name){
        return addOrReplaceStudent(name,"");
    }
    
    @PUT
    @Path("{name}/grade")
    public Response modifyEmptyStudent(@PathParam("name") String name){
        return addOrReplaceStudent(name,"");
    }
    
    private Response addOrReplaceStudent(String name,String grade){
        
        if(grade==null || "".equalsIgnoreCase(grade) || !validGrade(grade)){
            throw new BadRequestException();
        }
        
        Student studentSaved = filterStudent(studentList,name);
        
        if(studentSaved == null ){
           Student student = new Student();
           student.setName(name);
            student.setGrade(grade);
            studentList.getStudent().add(student);
        }else{
            studentSaved.setGrade(grade);
        }
        
        return Response.ok().build();
    }
    
    @GET
    @Produces(MediaType.TEXT_XML+";charset=utf-8")
    public StudentList getStudents(){
        return studentList;
    }
    
    @GET
    @Path("{name}")
    @Produces(MediaType.TEXT_XML+";charset=utf-8")
    public Student getStudent(@PathParam("name") String name){
        Student student = filterStudent(studentList,name);
        if(student==null){
            throw new NotFoundException();
        }
        return student;
    }
    
    @DELETE
    @Path("{name}")
    public Response deleteStudent(@PathParam("name") String name){
        Student student = filterStudent(studentList,name);
        if(student==null){
            throw new NotFoundException();
        }else{
            studentList.getStudent().remove(student);
        }
        return Response.ok().build();
    }

    private Student filterStudent(StudentList studentList, String name) {
        if(name == null || name.trim().length()==0 || studentList.getStudent()==null || studentList.getStudent().size()==0){
            return null;
        }
        return studentList.getStudent().stream().filter(student->name.equalsIgnoreCase(student.getName()))
                .findFirst().orElse(null);
    }

    private boolean validGrade(String grade) {
        ArrayList<String> grades = new ArrayList<String>(
                Arrays.asList("A+","A-","B+","B-","C+","C-","D+","D-","A","B","C","D","E","F","I","W","Z"));
        return grades.stream().filter(gr->gr.equalsIgnoreCase(grade)).count()==1;
    }*/
}
