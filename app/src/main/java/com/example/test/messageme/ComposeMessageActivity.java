package com.example.test.messageme;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ComposeMessageActivity extends AppCompatActivity {
    final static String TAG = "Compose Message Activity";
    private TextView tv_to;
    private EditText et_message;
    private Button btn_sendMessage;

    private FirebaseAuth mAuth;
    private FirebaseApp firebaseApp;
    private FirebaseUser currentUser;
    private DatabaseReference database;
    private ArrayList<String> userList;
    ImageButton btn_getUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        tv_to = findViewById(R.id.tv_to);
        et_message = findViewById(R.id.et_message);
        btn_sendMessage = findViewById(R.id.btn_sendMessage);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        btn_getUsers = findViewById(R.id.btn_usersList);
        btn_getUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+ userList);
                final String[] users;
                if (userList != null){
                    users = userList.toArray(new String[0]);
                    AlertDialog.Builder usersListDialog = new AlertDialog.Builder(ComposeMessageActivity.this);
                    usersListDialog.setTitle("Users").setItems(users, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.d(TAG, "onClick: "+ which);
                            tv_to.setText("To: " + users[which]);
                        }
                    });
                    usersListDialog.show();

                }else{
                    Toast.makeText(ComposeMessageActivity.this, "Please wait for user list to be retrieved", Toast.LENGTH_LONG);
                }
            }
        });

        database = FirebaseDatabase.getInstance().getReference();
        Log.d(TAG, "onCreate: "+ database);
        DatabaseReference usersDatabase = database.child("users");
        usersDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: " + dataSnapshot.getValue());
                collectUsersList((Map<String,Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void collectUsersList(Map<String, Object> users) {
        userList = new ArrayList<>();
        for (Map.Entry<String, Object> user: users.entrySet()){
//            Log.d(TAG, "collectUsersList: "+ user.getValue());
            userList.add(user.getValue().toString());
        }
    }
}
