package com.example.todoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    //private ArrayList<Todo> mTodos = new ArrayList<Todo>();
    private String[] mTodos;
    private Todo mTodo;
    private int mTodoIndex = 0;

    public static final String TAG = "TodoActivity";

    private static final int IS_SUCCESS = 0;

    /** In case of state change, due to rotating the phone
     * store the mTodoIndex to display the same mTodos element post state change
     * N.B. small amounts of data, typically IDs can be stored as key, value pairs in a Bundle
     * the alternative is to abstract view data to a ViewModel which can be in scope in all
     * Activity states and more suitable for larger amounts of data */

    private static final String TODO_INDEX = "com.example.todoIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /* call the super class onCreate to complete the creation of Activity
           like the view hierarchy */
        super.onCreate(savedInstanceState);

        //mTodo = new Todo();

        Log.d(TAG, " **** Just to say the PC is in onCreate!");

        /* set the user interface layout for this Activity
         the layout file is defined in the project res/layout/activity_todo.xml file */
        setContentView(R.layout.activity_main);

        /* check for saved state due to changes such as rotation or back button
           and restore any saved state such as the todo_index */
        if (savedInstanceState != null){
        mTodoIndex = savedInstanceState.getInt(TODO_INDEX, 0);
        }

        //mTodo = TodoModel.get(MainActivity.this).getTodo();

        /* TODO: Refactor to data layer */
        Resources res = getResources();
        mTodos = res.getStringArray(R.array.todo);

        /* initialize member TextView so we can manipulate it later */
        final TextView textViewTodo;
        textViewTodo = (TextView) findViewById(R.id.textViewTodo);

        setTextViewComplete("");

        /* display the first task from mTodo array in the textViewTodo */
        textViewTodo.setText(mTodos[mTodoIndex]);
        //textViewTodo.setText(mTodo.getTitle());

        Button buttonNext = (Button) findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mTodoIndex = (mTodoIndex + 1) % mTodos.length;
                textViewTodo.setText(mTodos[mTodoIndex]);
                setTextViewComplete("");
            }
        });

        Button buttonPrev = (Button) findViewById(R.id.buttonPrev);
        buttonPrev.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (mTodoIndex > 0){
                    mTodoIndex = mTodoIndex - 1;
                }else{
                    mTodoIndex = mTodos.length - 1;
                }
                textViewTodo.setText(mTodos[mTodoIndex]);
                setTextViewComplete("");
            }
        });

        Button buttonTodoDetail = (Button) findViewById(R.id.buttonTodoDetail);
        buttonTodoDetail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                /* Note, the child class being called has a static method determining the parameter
                   to be passed to it in the intent object */
                Intent intent = DetailActivity.newIntent(MainActivity.this, mTodoIndex);

                /* second param requestCode identifies the call as there could be many "intents" */
                startActivityForResult(intent, IS_SUCCESS);

                /* The result will return through
                   onActivityResult(requestCode, resultCode, Intent) method */

            }
        });



    }

    /* map or name, value pair to be returned in an intent */
    private static final String IS_TODO_COMPLETE = "com.example.isTodoComplete";

    /*
        requestCode is the integer request code originally supplied to startActivityForResult
        resultCode is the integer result code returned by the child activity through its setResult()
        intent date attached with intent "extras"
    */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == IS_SUCCESS ){
            if (intent != null) {
                // data in intent from child activity
                boolean isTodoComplete = intent.getBooleanExtra(IS_TODO_COMPLETE, false);
                updateTodoComplete(isTodoComplete);
            } else {
                Toast.makeText(this, R.string.back_button_pressed, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, R.string.request_code_mismatch,
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void updateTodoComplete(boolean is_todo_complete) {

        final TextView textViewTodo;
        textViewTodo = (TextView) findViewById(R.id.textViewTodo);

        if (is_todo_complete) {
            textViewTodo.setBackgroundColor(
                    ContextCompat.getColor(this, R.color.backgroundSuccess));
            textViewTodo.setTextColor(
                    ContextCompat.getColor(this, R.color.colorSuccess));

            setTextViewComplete("\u2713");
        }

    }

    private void setTextViewComplete( String message ){
        final TextView textViewComplete;
        textViewComplete = (TextView) findViewById(R.id.textViewComplete);

        textViewComplete.setText(message);
    }
}
