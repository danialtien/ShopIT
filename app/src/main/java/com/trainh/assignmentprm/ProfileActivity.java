package com.trainh.assignmentprm;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.trainh.assignmentprm.database.Database;
import com.trainh.assignmentprm.entities.Product;

import java.io.IOException;
import java.text.DecimalFormat;

public class ProfileActivity extends AppCompatActivity {

    ImageView avatar;
    TextView userName;
    TextView userEmail;
    TextView userPhone;
    TextView userAddress;

    TextView userPaymentInformation;

    Button bntEditAva;
    Button bntEditProfile;
    Button bntEditBack;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        avatar = findViewById(R.id.imageView2);
        userName = findViewById(R.id.textView);
        userEmail = findViewById(R.id.textView2);
        userPhone = findViewById(R.id.textView4b);
        userAddress = findViewById(R.id.textView4d);
        userAddress = findViewById(R.id.textView4d);
        bntEditAva = findViewById(R.id.btnEdit);
        bntEditProfile = findViewById(R.id.button2);
        bntEditBack = findViewById(R.id.buttonBack);

        // handle the Choose Image button to trigger
        // the image chooser function
        bntEditAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });
    }

    private void imageChooser()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        launchSomeActivity.launch(i);
    }

    ActivityResultLauncher<Intent> launchSomeActivity
            = registerForActivityResult(
            new ActivityResultContracts
                    .StartActivityForResult(),
            result -> {
                if (result.getResultCode()
                        == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    // do your operation from here....
                    if (data != null
                            && data.getData() != null) {
                        Uri selectedImageUri = data.getData();
                        Bitmap selectedImageBitmap;
                        try {
                            selectedImageBitmap
                                    = MediaStore.Images.Media.getBitmap(
                                    this.getContentResolver(),
                                    selectedImageUri);
                        }
                        catch (IOException e) {
                            e.printStackTrace();
                        }
                        avatar.setImageURI(selectedImageUri);
                    }
                }
            });



}