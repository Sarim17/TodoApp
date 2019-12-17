package com.example.todoapp;

import android.content.Context;

import java.util.ArrayList;
import java.util.UUID;

public class TodoModel {

    private static TodoModel sTodoModel;

    private ArrayList<Todo> mTodoList;

    public static TodoModel get() {
        if (sTodoModel == null) {
            sTodoModel = new TodoModel();
        }
        return sTodoModel;
    }

    private TodoModel(){
        mTodoList = new ArrayList<>();

        // refactor to pattern for data plugins
        // simulate some data for testing

        for (int i=0; i < 3; i++){
            Todo todo = new Todo();
            todo.setTitle("Todo title " + i);
            todo.setDetail("Detail for task "+ i + todo.getId().toString());
            todo.setComplete(false);

            mTodoList.add(todo);
        }

    }

    //public Todo getTodo(UUID todoId) {
    public Todo getTodo(UUID todoId) {

        for (Todo todo : mTodoList) {
            if (todo.getId().equals(todoId)){
                return todo;
            }
            return todo;
        }

        return null;
    }

    public ArrayList<Todo> getTodos() {

        return mTodoList;

    }

    public void addTodo(Todo todo){

        mTodoList.add(todo);

    }

}