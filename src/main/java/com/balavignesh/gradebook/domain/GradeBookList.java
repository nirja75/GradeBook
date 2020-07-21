/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.balavignesh.gradebook.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author psubr
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "gradebook"
})
@XmlRootElement(name = "gradebook-list")
public class GradeBookList {

    protected List<GradeBook> gradebook;
  
    public List<GradeBook> getGradebook() {
     
        if (gradebook == null) {
            gradebook = new ArrayList<GradeBook>();
        }
        return this.gradebook;
    }
    
    
}


