package com.example.clothesresell;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignupActivty extends AppCompatActivity {

    public EditText username, fullname, emailId, passwd;
    Button btnSignUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;

    DatabaseReference reference;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.ETusername);
        fullname = findViewById(R.id.ETfullname);
        emailId = findViewById(R.id.ETemail);
        passwd = findViewById(R.id.ETpassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        signIn = findViewById(R.id.TVSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd = new ProgressDialog(SignupActivty.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_username = username.getText().toString();
                String str_fullname = fullname.getText().toString();
                String str_email = emailId.getText().toString();
                String str_passwd = passwd.getText().toString();

                if (str_email.isEmpty()) {
                    emailId.setError("Email field is missing!");
                    emailId.requestFocus();
                } else if (str_passwd.isEmpty()) {
                    passwd.setError("Password field is missing!");
                    passwd.requestFocus();
                } else if (str_username.isEmpty()) {
                    username.setError("Username field is missing!");
                    username.requestFocus();
                } else if (str_fullname.isEmpty()) {
                    fullname.setError("Full Name field is missing!");
                    fullname.requestFocus();
                } else if (str_passwd.length() < 6) {
                    passwd.setError("Password needs to be at least 6 characters long!");
                    passwd.requestFocus();

                } else {
                    singup(str_username, str_fullname, str_email, str_passwd);
                }
            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(SignupActivty.this, LoginActivity.class);
                startActivity(I);
            }
        });
    }


    private void singup(String username, String fullname, String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivty.this, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {

                if (task.isSuccessful()) {
                    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                    String userid = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userid);
                    hashMap.put("username", username.toLowerCase());
                    hashMap.put("fullname", fullname);
                    hashMap.put("description", "");
                    hashMap.put("imageurl", "https://firebasestorage.googleapis.com/v0/b/clothes-resell-b06fa.appspot.com/o/user-ge5a12c801_640.png?alt=media&token=b13480b3-02d7-4796-b6a4-d496b77093c0");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                Intent intent = new Intent(SignupActivty.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });


                } else {
                    pd.dismiss();
                    Toast.makeText(SignupActivty.this.getApplicationContext(),
                            "You can't register with this email or password",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}