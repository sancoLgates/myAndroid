package com.example.myapplication;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebasecrudActivity extends AppCompatActivity {

    EditText editTextName, editTextEmail, editTextNumber;
    Button buttonAddUser;
    ListView listViewUsers;

    List<User> Users;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebasecrud);
        //method for find ids of views
        findViews();

        //emailValidate
        final EditText emailValidate = (EditText)findViewById(R.id.editTextEmail);

        final TextView textView = (TextView)findViewById(R.id.textEmail);



        emailValidate.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String email = emailValidate.getText().toString().trim();

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                Log.d("email1", email);
                Log.d("email11", String.valueOf(s));
                Log.d("email2", String.valueOf(s.length()));
                Log.d("email3", String.valueOf(email.matches(emailPattern)));
                if (email.matches(emailPattern) && s.length() > 0)
                {
//                    Toast.makeText(getApplicationContext(),"valid email address",Toast.LENGTH_SHORT).show();
                    // or
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("valid email");
                }
                else
                {
//                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                    //or
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("invalid email");
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        //to maintain click listener of views
        initListener();
    }

    private void findViews() {
        //getReference for user Table
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        listViewUsers = (ListView) findViewById(R.id.listViewUsers);
        buttonAddUser = (Button) findViewById(R.id.buttonAddUser);



        //list for store objects of user
        Users = new ArrayList<>();
    }

    private void initListener() {
        //adding an onclicklistener to button
        buttonAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //calling the method addUser()
                //the method os defined below
                //this method is actually performing the write operation
                addUser();
            }
        });

        //list item click listener
        listViewUsers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                User User = Users.get(i);
                CallUpdateAndDeleteDialog(User.getUserid(), User.getUsername(), User.getUseremail(), User.getUsermobileno());
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //String value = dataSnapshot.getValue(String.class);
                Log.d("sanz ONCHANGE", String.valueOf(dataSnapshot));

                //clearing the previous User list
                Users.clear();
                //getting all nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting User from firebase console

                    User User = postSnapshot.getValue(User.class);
                    //adding User to the list
                    Users.add(User);
                }
                //creating Userlist adapter
                UserList UserAdapter = new UserList(FirebasecrudActivity.this, Users);
                //attaching adapter to the listview
                listViewUsers.setAdapter(UserAdapter);
            }
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void CallUpdateAndDeleteDialog(final String userid, String username, final String email, String monumber) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
//Access Dialog views
        final EditText updateTextname = (EditText) dialogView.findViewById(R.id.updateTextname);
        final EditText updateTextemail = (EditText) dialogView.findViewById(R.id.updateTextemail);
        final EditText updateTextmobileno = (EditText) dialogView.findViewById(R.id.updateTextmobileno);
        updateTextname.setText(username);
        updateTextemail.setText(email);
        updateTextmobileno.setText(monumber);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateUser);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteUser);
//username for set dialog title
        dialogBuilder.setTitle(username);
        final AlertDialog b = dialogBuilder.create();
        b.show();

// Click listener for Update data
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = updateTextname.getText().toString().trim();
                String email = updateTextemail.getText().toString().trim();
                String mobilenumber = updateTextmobileno.getText().toString().trim();
//checking if the value is provided or not Here, you can Add More Validation as you required

                if (!TextUtils.isEmpty(name)) {
                    if (!TextUtils.isEmpty(email)) {
                        if (!TextUtils.isEmpty(mobilenumber)) {
//Method for update data
                            updateUser(userid, name, email, mobilenumber);
                            b.dismiss();
                        }
                    }
                }

            }
        });

// Click listener for Delete data
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//Method for delete data
                deleteUser(userid);
                b.dismiss();
            }
        });
    }

    private boolean updateUser(String id, String name, String email, String mobilenumber) {
//getting the specified User reference
        DatabaseReference UpdateReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
        User User = new User(id, name, email, mobilenumber);
//update User to firebase
        UpdateReference.setValue(User);
        Toast.makeText(getApplicationContext(), "User Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteUser(String id) {
//getting the specified User reference
        DatabaseReference DeleteReference = FirebaseDatabase.getInstance().getReference("Users").child(id);
//removing User
        DeleteReference.removeValue();
        Toast.makeText(getApplicationContext(), "User Deleted", Toast.LENGTH_LONG).show();
        return true;
    }


    private void addUser() {

//getting the values to save
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String mobilenumber = editTextNumber.getText().toString().trim();


//checking if the value is provided or not Here, you can Add More Validation as you required

        if (!TextUtils.isEmpty(name)) {
            if (!TextUtils.isEmpty(email)) {
                if (!TextUtils.isEmpty(mobilenumber)) {

                    // Write a message to the database
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myRef = database.getReference("message");

                    myRef.setValue("Hello, World2!");

//it will create a unique id and we will use it as the Primary Key for our User
                    String id = databaseReference.push().getKey();
//creating an User Object
                    User User = new User(id, name, email, mobilenumber);
//Saving the User
                    Log.d("sanz user", String.valueOf(User));
                    Log.d("sanz user", id);

                    databaseReference.child(id).setValue(User);

//                    editTextName.setText("");
//                    editTextNumber.setText("");
//                    editTextEmail.setText("");
                    Toast.makeText(this, "User added", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "Please enter a mobilenumber", Toast.LENGTH_LONG).show();
                    editTextNumber.setHint("Please enter a mobilenumbers");
                    editTextNumber.setError("Please enter a mobilenumberss");
                }
            } else {
                Toast.makeText(this, "Please enter a Email", Toast.LENGTH_LONG).show();
                editTextEmail.setHint("Please enter a Emails");
                editTextEmail.setError("Please enter a Emailss");
            }
        } else {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_LONG).show();
            editTextName.setHint("Please enter a names");
            editTextName.setError("Please enter a namess");
        }
    }
}