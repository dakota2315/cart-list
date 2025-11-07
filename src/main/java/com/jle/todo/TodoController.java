/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jle.todo;

import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Jus
 */

//CrossOrigen Allows this server to be accessed by other servers
@RestController
@CrossOrigin(origins = "*") 
public class TodoController {
    
    //create a file to save the data collected (non-volatile) instead
    private final TodoStore store;
    public TodoController(TodoStore store){
        this.store = store;
    }
    //next two lines create and initialize a place to save the data
    //provided by the user (items to Buy)
    //This storage is volatile; which means that will be lost if 
    //the server goes out for any reason
    /*
    private List<TodoItem> todoItems = new ArrayList<>();
    private int id = 1;
    */
    //To see the date ... retrieve and show in browser
    @GetMapping("/todos")
    //create virtual storage by mapping the next lines to get
    //@RequestMapping("/")
    //actually shows data on browser by mapping the next lines to request
    public List fetchTodos(){
        return store.fetchTodos();//fetch in store instead the todoItems
        //return todoItems;
        //return "Esta es una prueba ...";
    }
    
    //To save data (write new data)
    @PostMapping("/todos")//
    public TodoItem createTodo(@RequestBody TodoItem newTodo) throws IOException{
        return this.store.createTodo(newTodo);
    }
    /*//removed to point to store
    public TodoItem createTodo(@RequestBody TodoItem newTodo){
        TodoItem fullTodo = new TodoItem(this.id, newTodo.text,
                newTodo.isComplete, 
                Instant.now().toEpochMilli());
        this.todoItems.add(fullTodo);
        this.id ++;              
        return fullTodo;
    }
    */
    
    //To delete data from the list
    @DeleteMapping("/todos/{itemId}")
    public void deleteTodo(@PathVariable("itemId") int itemId) throws IOException{
        this.store.deleteTodo(itemId);
    }   
        
    /*//removed to point to store
    public void deleteTodo(@PathVariable("itemId") int itemId){
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
    }
    */
}
