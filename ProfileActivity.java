package com.sarthak.amigo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity
{

    private TextView userName,userProfName,userStatus,userCountry,userGender,userRelation,userDob;
    private CircleImageView userProfileImage;
    private DatabaseReference profileUserRef;
    private FirebaseAuth mAuth;
    private String CurrentUserid;
    

    public ProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mAuth=FirebaseAuth.getInstance();
        CurrentUserid=mAuth.getCurrentUser().getUid();
        profileUserRef= FirebaseDatabase.getInstance().getReference().child("Users").child(CurrentUserid);


        userName = (TextView) findViewById(R.id.my_username);
        userProfName = (TextView) findViewById(R.id.my_profile_full_name);
        userStatus = (TextView) findViewById(R.id.my_profile_status);
        userCountry = (TextView) findViewById(R.id.my_country);
        userGender = (TextView) findViewById(R.id.my_gender);
        userRelation = (TextView) findViewById(R.id.my_relationship_status);
        userDob = (TextView) findViewById(R.id.my_dob);
        userProfileImage=(CircleImageView) findViewById(R.id.my_profile_pic);



     profileUserRef.addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(DataSnapshot dataSnapshot)
         {
             if(dataSnapshot.exists())
             {

                 String myProfileImage = dataSnapshot.child("profileimage").getValue().toString();
                 String myUserName = dataSnapshot.child("username").getValue().toString();
                 String myProfileName = dataSnapshot.child("fullname").getValue().toString();
                 String myProfileStatus = dataSnapshot.child("status").getValue().toString();
                 String myDOB = dataSnapshot.child("dob").getValue().toString();
                 String myCountry = dataSnapshot.child("country").getValue().toString();
                 String myGender = dataSnapshot.child("gender").getValue().toString();
                 String myRelationStatus = dataSnapshot.child("relationshipstatus").getValue().toString();


                 Picasso.get().load(myProfileImage).placeholder(R.drawable.profile).into(userProfileImage);


                 userName.setText("Username:" + myUserName);
                 userProfName.setText("Name:"+ myProfileName);
                 userStatus.setText(myProfileStatus);
                 userDob.setText("DOB:"+ myDOB);
                 userCountry.setText("Country:"+ myCountry);
                 userGender.setText("Gender:"+ myGender);
                 userRelation.setText("Relationship: "+ myRelationStatus);
             }

         }

         @Override
         public void onCancelled(DatabaseError databaseError)
         {

         }
     });

    }
}
