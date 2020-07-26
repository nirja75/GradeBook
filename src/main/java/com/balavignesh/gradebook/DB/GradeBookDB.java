/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.balavignesh.gradebook.DB;

import com.balavignesh.gradebook.connection.SendRequest;
import com.balavignesh.gradebook.domain.GradeBook;
import com.balavignesh.gradebook.domain.GradeBookList;
import com.balavignesh.gradebook.domain.Server;
import com.balavignesh.gradebook.domain.ServerList;
import com.balavignesh.gradebook.domain.Student;
import com.balavignesh.gradebook.domain.StudentList;
import java.io.IOException;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import javax.ws.rs.BadRequestException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @author BalaVignesh
 */
public class GradeBookDB {
    
    private GradeBookList gradeBookList = new GradeBookList();
    private ServerList serverList = new ServerList();
    
    
    private Map<Long, StudentList> gradeWithStudent= new HashMap<Long, StudentList>();
    private static AtomicInteger idCounter = new AtomicInteger();
    
    public void addDefaults(){
        createServer("balavignesh","35.224.65.85","8080","GradeBook");
        createServer("prj2","34.68.29.81","8080","GradeBook");
        //createServer("localhost","104.48.130.146","8080","GradeBook");
    }
    
    public long createGradebook(String title) throws IOException{
        GradeBook gradeBook = new GradeBook();
        idCounter.incrementAndGet();
        gradeBook.setGradeId(idCounter.longValue());
        gradeBook.setGradeTitle(title);
        
        Server server = filterServerByIp(getMyIP());
        gradeBook.setServer(server);
        
       gradeBookList.getGradebook().add(gradeBook);
       return idCounter.longValue();
    }
    
    public void createGradebook(GradeBook gradeBook) throws IOException{
        gradeBookList.getGradebook().add(gradeBook);
        idCounter.set((int)gradeBook.getGradeId());
    }
     
    public void pushToGradeSecondaries(GradeBook gradeBook) throws IOException{
        if(gradeBook!=null && gradeBook.getServerList()!=null && gradeBook.getServerList().getServer()!=null
                && gradeBook.getServerList().getServer().size()>0){
            List<Server> servers = filterServerByNotIp(gradeBook.getServerList().getServer(),getMyIP());
            
            //TODO
            
            
        }
        
    } 
    
    public void pushToAllSecondaries(GradeBook gradeBook) throws IOException{
        if(gradeBook!=null && serverList.getServer().size()>1){
            List<Server> servers = filterServerByNotIp(serverList.getServer(),getMyIP());
            servers.stream().forEach(server->{
                sentMessage(server,"/resources/gradebook","POST",jaxbObjectToXML(gradeBook));
            });
        }
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
    
    public Student filterStudent(StudentList studentList, String name) {
        if(name == null || name.trim().length()==0 || studentList.getStudent()==null || studentList.getStudent().size()==0){
            return null;
        }
        return studentList.getStudent().stream().filter(student->name.equalsIgnoreCase(student.getName()))
                .findFirst().orElse(null);
    }
    
     public Server filterServerByName(String name) {
        if(name == null || name.trim().length()==0 || serverList.getServer()==null || serverList.getServer().size()==0){
            return null;
        }
        return serverList.getServer().stream().filter(student->name.equalsIgnoreCase(student.getName()))
                .findFirst().orElse(null);
    } 
     
    public Server filterServerByIp(String ip) {
        if(ip == null || ip.trim().length()==0 || serverList.getServer()==null || serverList.getServer().size()==0){
            return null;
        }
        return serverList.getServer().stream().filter(student->
                ip.equalsIgnoreCase(student.getIp()))
                .findFirst().orElse(null);
    } 
    
    public List<Server> filterServerByNotIp(List<Server> list ,String ip) {
        if(ip == null || ip.trim().length()==0 || list==null || list.size()==0){
            return null;
        }
        return list.stream().filter(student-> !ip.equalsIgnoreCase(student.getIp()))
                .collect(Collectors.toList()); 
    } 

    public GradeBookList getGradeBookList() {
        return gradeBookList;
    }
    
     public ServerList getServerList() {
        return serverList;
    }
     
    public void createServer(String name,String ip,String port,String contextroot){
        
        Server serverPresent = filterServerByName(name);
        if(serverPresent == null ){
            Server server = new Server();
            server.setName(name);
            server.setIp(ip);
            server.setPort(port);
            server.setContextRoot(contextroot);    
            serverList.getServer().add(server);
        }
        
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
    
    public Student getStudent(long id, String name) {
        return gradeWithStudent.containsKey(id) ? filterStudent(gradeWithStudent.get(id),name):null;
    }
    
    public boolean validGrade(String grade) {
        ArrayList<String> grades = new ArrayList<String>(
                Arrays.asList("A+","A-","B+","B-","C+","C-","D+","D-","A","B","C","D","E","F","I","W","Z"));
        return grades.stream().filter(gr->gr.equalsIgnoreCase(grade)).count()==1;
    }
    
    public String getMyHostName() throws UnknownHostException, SocketException, IOException{
        return InetAddress.getLocalHost().getHostName();
    }
    
    public String getMyIP() throws UnknownHostException, SocketException, IOException{
        String ip = execReadToString("curl https://checkip.amazonaws.com");
        ip = ip.replace("\n", "");
        return ip;
    }
    
    public static String execReadToString(String execCommand) throws IOException {
    try (Scanner s = new Scanner(Runtime.getRuntime().exec(execCommand).getInputStream()).useDelimiter("\\A")) {
        return s.hasNext() ? s.next() : "";
    }
    }
    
    
    public String sentMessage(Server server,String url, String method,String content){
        SendRequest s = new SendRequest();
        
         String str = s.SendRequest("http://"+server.getIp()+":"+server.getPort()+"/"+
                 server.getContextRoot()+url, method,content);
         System.out.println(str);
         return str;
    }
    
    private static String jaxbObjectToXML(GradeBook gradebook) {
    String xmlString = "";
    try {
        JAXBContext context = JAXBContext.newInstance(GradeBook.class);
        Marshaller m = context.createMarshaller();

        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

        StringWriter sw = new StringWriter();
        m.marshal(gradebook, sw);
        xmlString = sw.toString();

    } catch (JAXBException e) {
        e.printStackTrace();
    }

    return xmlString;
    }
    
}
