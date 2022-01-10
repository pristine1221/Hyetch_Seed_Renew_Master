package com.example.pristineseed.ui.bootmMainScreen.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.pristineseed.R;

import java.io.File;

public class DisplayActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.activity_display);

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
     //   findViewById(R.id.returnButton).setOnClickListener(this);

        String filepath = getIntent().getExtras().getString("filepath", null);
        File file = new File(filepath);
        Glide.with(this).load(file).into(imageView);
    }

    @Override
    public void onClick(View v) {

    }
}
