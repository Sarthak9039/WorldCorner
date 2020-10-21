package com.sarthak.amigo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.AppCompatButton;
import android.support.*;
import android .support.design.* ;
import android.support.v4.widget.ImageViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText userName,userProfName,userStatus,userCountry,userGender,userRelation,userDob;
    private Button UpdateAccountSettingButton;
    private CircleImageView userProfImage;


    private DatabaseReference SettingsuserRef;
    private FirebaseAuth mAuth;
    private String currentUserid;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToolbar = (Toolbar) findViewById(R.id.settings_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Account Settings");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        currentUserid = mAuth.getCurrentUser().getUid();
        SettingsuserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserid);


        userName = (EditText) findViewById(R.id.settings_username);
        userProfName = (EditText) findViewById(R.id.settings_profile_full_name);
        userStatus = (EditText) findViewById(R.id.settings_status);
        userCountry = (EditText) findViewById(R.id.settings_country);
        userGender = (EditText) findViewById(R.id.settings_gender);
        userRelation = (EditText) findViewById(R.id.settings_relationship_status);
        userDob = (EditText) findViewById(R.id.settings_dob);

        UpdateAccountSettingButton = (Button) findViewById(R.id.update_account_settings_buttons);
        userProfImage=(CircleImageView) findViewById(R.id.settings_profile_image);


        SettingsuserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String myProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                    String myUserName = dataSnapshot.child("username").getValue().toString();
                    String myProfileName = dataSnapshot.child("fullname").getValue().toString();
                    String myProfileStatus = dataSnapshot.child("status").getValue().toString();
                    String myDOB = dataSnapshot.child("dob").getValue().toString();
                    String myCountry = dataSnapshot.child("country").getValue().toString();
                    String myGender = dataSnapshot.child("gender").getValue().toString();
                    String myRelationStatus = dataSnapshot.child("relationshipstatus").getValue().toString();


                     Picasso.get().load(myProfileImage).placeholder(R.drawable.profile).into(userProfImage);


                    userName.setText(myUserName);
                    userProfName.setText(myProfileName);
                    userStatus.setText(myProfileStatus);
                    userDob.setText(myDOB);
                    userCountry.setText(myCountry);
                    userGender.setText(myGender);
                    userRelation.setText(myRelationStatus);


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        UpdateAccountSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateAccountinfo();

            }
        });
    }

            private void ValidateAccountinfo()
            {
                String username=userName.getText().toString();
                String profilename=userProfName.getText().toString();
                String status=userStatus.getText().toString();
                String dob=userDob.getText().toString();
                String country=userCountry.getText().toString();
                String gender=userGender.getText().toString();
                String relation=userRelation.getText().toString();
                
                if (TextUtils.isEmpty(username))
                {
                    Toast.makeText(SettingActivity.this, "Please write your username...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(profilename))
                {
                    Toast.makeText(SettingActivity.this, "Please write your profile name...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(status))
                {
                    Toast.makeText(SettingActivity.this, "Please write your status...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(dob))
                {
                    Toast.makeText(SettingActivity.this, "Please write your date of Birth...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(country))
                {
                    Toast.makeText(SettingActivity.this, "Please write your country name...", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(gender))
                {
                    Toast.makeText(SettingActivity.this, "Please write your gender..", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(relation))
                {
                    Toast.makeText(SettingActivity.this, "Please write your relationship status...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    UpdateAccountInfo(username,profilename,status,dob,country,gender,relation);
                }
            }

            private void UpdateAccountInfo(String username, String profilename, String status, String dob, String country, String gender, String relation)
            {
                HashMap userMap=new HashMap();
                userMap.put("username",username);
                userMap.put("fullname",profilename);
                userMap.put("status",status);

                userMap.put("dob",dob);
                userMap.put("country",country);
                userMap.put("gender",gender);
                userMap.put("relationshipstatus",relation);

                SettingsuserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task)
                    {
                        if(task.isSuccessful())
                        {
                            SendUserToMainActivity();
                            Toast.makeText(SettingActivity.this, "Account Setting Updated Successfully... ", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            Toast.makeText(SettingActivity.this, "Error Occured,While Updating Account Info.....", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(SettingActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }





}
