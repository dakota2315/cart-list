/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jle.todo;

/**
 *
 * @author Jus
 */

public class TodoItem {
    public final Integer id;
    public final String text;
    public final boolean isComplete;
    public final long createdDate;
    
    //if the items do not exist at the moment they are called
    //then constructor with empty values
    private TodoItem(){
        this.id = null;
        this.text = null;
        this.isComplete = false;
        this.createdDate = 0;
    }
    
    public TodoItem(Integer id, String text, boolean isComplete, 
            long createdDate){
        this.id = id;
        this.text = text;
        this.isComplete = isComplete;
        this.createdDate = createdDate;
    }
        
}

