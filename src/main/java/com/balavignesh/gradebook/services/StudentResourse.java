/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.balavignesh.gradebook.services;

import com.balavignesh.gradebook.domain.Student;
import com.balavignesh.gradebook.domain.StudentList;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author BalaVignesh
 */
@Path("/student")
public class StudentResourse {
    
    private StudentList studentList = new StudentList();
    
    @POST
    @Path("{name}/grade/{grade}")
    public Response addCustomer(@PathParam("name") String name,@PathParam("grade") String grade){
        Student student = new Student();
        student.setName(name);
        student.setGrade(grade);
        studentList.getStudent().add(student);
        return Response.ok().build();
    }
    
   //GET /customer
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public StudentList getCustomers(){
        return studentList;
    }
    
}
