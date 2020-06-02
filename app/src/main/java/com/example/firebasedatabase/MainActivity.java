package com.example.firebasedatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    //declare instance variable fpr Firebase database
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase= (DatabaseReference) FirebaseDatabase.getInstance().getReference ("user");
    }

    //Method to write to the database
        public void basicWrite (View view){
        //Write a message to the database
        String email=((EditText)findViewById(R.id.editText_UserEmail)).getText().toString();
        String password=((EditText)findViewById(R.id.editText_Password)).getText().toString();

        mDatabase.child ("email").setValue(email);
        mDatabase.child ("password").setValue(password);
        //[END write_message]
    }
   //Method to read from the database
    public void basicRead (View view){

        mDatabase.addValueEventListener (new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                //This method is called once the initial value and again
                //whenever data at this location is updated.
                if (dataSnapshot.exists()){
                    HashMap<String, Object> dataMap = (HashMap<String, Object>) dataSnapshot.getValue();
                    try{
                        //Read the values
                        String eString= String.valueOf (dataMap.get("email"));
                        String pString= String.valueOf (dataMap.get("password"));
                        Toast.makeText (getApplicationContext(), "Email ="+ eString+"\nPassword = "
                                +pString,Toast.LENGTH_LONG).show();
                    } catch (ClassCastException err){
                        Toast.makeText (getApplicationContext(), "Some Error", Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //failed to read value
            }
        });
        }

    }
