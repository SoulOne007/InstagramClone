package com.example.instagramclone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    private Button button;
    private EditText username;
    private EditText password;
    private TextView login;
    Boolean signUpMode = true;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.signIn_btn);
        username = findViewById(R.id.usernameEditText);
        password =findViewById(R.id.passwordeditText);
        login = findViewById(R.id.login);
        login.setOnClickListener(this);
        password.setOnKeyListener(this);
        constraintLayout = findViewById(R.id.constraint_layout);
        constraintLayout.setOnClickListener(this);

        if(ParseUser.getCurrentUser() != null){
            showuserlist();

        }






//        ParseUser user = new ParseUser();
//        user.setUsername("Nick");
//        user.setPassword("Chicka");
//
//        user.signUpInBackground(new SignUpCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    Log.i("zka", "I'm done");
//                }else {
//                    e.printStackTrace();
//                }
//            }
//        });

//        ParseUser.logInInBackground("Nick", "Chicka", new LogInCallback() {
//            @Override
//            public void done(ParseUser user, ParseException e) {
//                if(user != null){
//                    Log.i("zkalogin", "Success");
//                }
//            }
//        });

//        ParseUser.logOut();
//
//        if(ParseUser.getCurrentUser() !=null){
//            Log.i("zkasignedIn", ParseUser.getCurrentUser().getUsername());
//        }else {
//            Log.i("zka zz", "Not signed In");
//        }













//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//
//        query.whereGreaterThan("score", "50");
//        query.findInBackground(new FindCallback<ParseObject>() {
//            @Override
//            public void done(List<ParseObject> objects, ParseException e) {
//                if(e == null){
//                    if(objects.size()>0){
//
//                        for (ParseObject object:objects){
//                            Log.i("zka", object.getString("user"));
//                            Log.i("zka", object.getString("score"));
//                        }
//                    }
//                }
//           }
//        });




//        ParseObject score = new ParseObject("Score");
//        score.put("user", "Shaun");
//        score.put("score", "68");
//        score.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//                    Log.i("tag", "Succes");
//                }else{
//                    e.printStackTrace();
//                }
//            }
//        });

//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//        query.getInBackground("zgttQ8EOB0", new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if(e == null && object != null){
//                    Log.i("zkausername", object.getString("user"));
//                    Log.i("zkascore", object.getString("score"));
//                }
//            }
//        });


//        ParseObject Tweet = new ParseObject("Tweet");
//        Tweet.put("user", "Chicha");
//        Tweet.put("tweet", "What's UP?");
//
//        Tweet.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(ParseException e) {
//                if(e == null){
//
//                }
//            }
//        });
//
//        ParseQuery<ParseObject> query = ParseQuery.getQuery("Tweet");
//        query.getInBackground("rRoyE7yKQk", new GetCallback<ParseObject>() {
//            @Override
//            public void done(ParseObject object, ParseException e) {
//                if (object != null && e == null) {
//
//                    object.put("tweet", "Zomo");
//                    object.saveInBackground();
//                Log.i("zka", object.getString("user"));
//                    Log.i("zka", object.getString("tweet"));
//                }
//            }
//        });

        ParseInstallation.getCurrentInstallation().saveInBackground();

    }

    public void signUp(View view) {
        if(username.getText().toString().matches("") || password.getText().toString().matches("")){
            Toast.makeText(this, "Details required", Toast.LENGTH_SHORT).show();
        }else{
            if(signUpMode) {
                ParseUser user = new ParseUser();
                user.put("username", username.getText().toString());
                user.put("password", password.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(MainActivity.this, "Success Buddy", Toast.LENGTH_SHORT).show();
                            showuserlist();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else  {
                ParseUser.logInInBackground(username.getText().toString(), password.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user != null){
                            showuserlist();
                            Toast.makeText(MainActivity.this, "User Found", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "User not Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }

    }


    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login){
            if(signUpMode){
                signUpMode=false;
                button.setText("Login");
                login.setText("SignUp");
            }else{
                signUpMode=true;
                button.setText("SignUp");
                login.setText("Login");
            }

        } else if (view.getId() == R.id.constraint_layout) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
        }
    }

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
            signUp(view);
        }

        return false;
    }

    public void showuserlist(){
        Intent intent = new Intent(getApplicationContext(), UserListActivity.class);
        startActivity(intent);
    }
}
