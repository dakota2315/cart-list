/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jle.todo;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;



/**
 *
 * @author Jus
 */

//Dependency Injection
//remove any RestController that pass or remove data
@Component
public class TodoStore {
    
    //create an object to be saved via 'persistTodos()'
    private static final ObjectMapper mapper = new ObjectMapper();
    //point to the file where the data is going to be stored
    private static final File storeFile = new File("./DataFile.txt");
    //the file name 'store.json' save the data in JSON format ./store.json
    private List<TodoItem> todoItems = new ArrayList<>();
    private int id = 1;

    public List<TodoItem> fetchTodos() {
        return this.todoItems;
    }
    //from previous POST
    public TodoItem createTodo(TodoItem newTodo){
        TodoItem fullTodo = new TodoItem(this.id, newTodo.text,
                newTodo.isComplete, 
                Instant.now().toEpochMilli());
        this.todoItems.add(fullTodo);
        this.id ++;
        this.persistTodos();//save data to a file as it changes
        return fullTodo;
    }
    //from previous DELETE
    public void deleteTodo(int itemId){
        Integer indexToDelete = null;
        for(int i=0; i<this.todoItems.size(); i++){
            if(todoItems.get(i).id.equals(itemId)){
                indexToDelete = i;
                break;
            }    
        }
        if(indexToDelete != null){
            this.todoItems.remove((int)indexToDelete);
        }
        else{
            throw new IllegalArgumentException("Item with ID " + itemId + 
                    " does not exist");
        }
        this.persistTodos();//save data to a file as it changes
    }
    
    //function to read from the file 'store.json' otherwise, the stored
    //data will be overwrited and lost
    //PostConstruct will rebuild the file
    @PostConstruct
    private void reloadTodos() throws IOException{
        //first handle the event that the file does not exist (first time
        //running the program or just the file is lost
        if(storeFile.exists()){
            //now lets read the file
            this.todoItems = mapper.readValue(storeFile, 
                    new TypeReference<List<TodoItem>>(){});
            //now lets display the loaded data
            System.out.println("Loaded " + this.todoItems.size() + " from the "
                    + "backup file");
        }
        else{
            System.out.println("Initializing the database file");
        }
        
        
    }
        private void persistTodos(){
        try {
            mapper.writeValue(storeFile, this.todoItems);
        } catch (IOException ex) {
            ex.printStackTrace();
            //Logger.getLogger(TodoStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
     
    
  



    
    
    /*
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }
    
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latest();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_11;
    }
    */
}
