/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.balavignesh.gradebook.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author psubr
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {

})
@XmlRootElement(name = "gradebook")
public class GradeBook {

    @XmlElement(required = true)
    protected String gradeTitle;
    @XmlElement(required = true)
    protected long gradeId;
    @XmlElement(required = true)
    protected Server primary;
    @XmlElement(required = false)
    protected ServerList secondarys;

    public String getGradeTitle() {
        return gradeTitle;
    }

    public void setGradeTitle(String gradeTitle) {
        this.gradeTitle = gradeTitle;
    }

    public long getGradeId() {
        return gradeId;
    }

    public void setGradeId(long gradeId) {
        this.gradeId = gradeId;
    }
    
    public Server getPrimary() {
        return primary;
    }

    public void setPrimary(Server primary) {
        this.primary = primary;
    }
    
    public ServerList getSecondarys() {
        return secondarys;
    }

    public void setSecondarys(ServerList secondarys) {
        this.secondarys = secondarys;
    }
   
    }


