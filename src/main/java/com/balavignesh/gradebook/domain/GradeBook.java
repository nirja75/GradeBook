/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.balavignesh.gradebook.domain;

import javax.xml.bind.annotation.*;
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
    @XmlElement(name = "server",required = true)
    protected Server server;
    @XmlElement(name = "server-list",required = false)
    protected ServerList serverList;
   
    @XmlTransient
    protected boolean visible;

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

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
    
    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }
    
    public ServerList getServerList() {
        return serverList;
    }

    public void setServerList(ServerList serverList) {
        this.serverList = serverList;
    }
   
    }


