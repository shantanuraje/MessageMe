package com.example.test.messageme;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class InboxActivity extends AppCompatActivity {
    final static String TAG = "Inbox Activity";
    private FirebaseAuth mAuth;
    private FirebaseApp firebaseApp;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.inbox_toolbar);
//        myToolbar.setTitle(R.string.cancel);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_inbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_logout){
            mAuth.signOut();
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivity(loginIntent);
        }else if (item.getItemId() == R.id.action_composeMessage){
            Intent composeMessageIntent = new Intent(this, ComposeMessageActivity.class);
            startActivity(composeMessageIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
