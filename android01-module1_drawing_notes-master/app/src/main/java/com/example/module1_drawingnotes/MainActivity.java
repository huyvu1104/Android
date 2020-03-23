package com.example.module1_drawingnotes;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    int requestCodeWriteStorage = 1001;
    private GridView gridView;
    private ImageAdapter imageAdapter;
    int Selected = -1;
    TextView tvNoImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fbAdd = findViewById(R.id.fb_add);
        tvNoImage = findViewById(R.id.tv_noimage);
        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(intent);
            }
        });

        setupPermission();
        gridView=findViewById(R.id.grid_view);
        imageAdapter=new ImageAdapter();
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Selected = position;
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DialogDelete(ImageUtils.getListImage().get(position).getName(), Selected);
                imageAdapter.notifyDataSetChanged();
                return false;
            }
        });

        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                startActivity(intent);

            }
        });
    }
    public void DialogDelete(String fileName, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(fileName + "\n" + "Do you want to delete this image?");
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "No", Toast.LENGTH_SHORT).show();


            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                ImageUtils.DeleteImage(Selected);
                imageAdapter.notifyDataSetChanged();
                onStart();
            }
        });
        builder.show();
    }
    @Override
    protected void onStart() {
        super.onStart();
        if (ImageUtils.getListImage().isEmpty()) {
            tvNoImage.setText("No image");
            Toast.makeText(this, "No image ", Toast.LENGTH_SHORT).show();


        }
        if (ImageUtils.getListImage().isEmpty() == false) {

            tvNoImage.setText(null);
            gridView.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.onStart();
    }


    private void setupPermission() {
        String[] permissions = new String[1];
        permissions[0] = Manifest.permission.WRITE_EXTERNAL_STORAGE;

        //new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permissions, requestCodeWriteStorage);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == requestCodeWriteStorage) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Warning!")
                        .setMessage("Without permission you can not use this app. Do you want to grant permission?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setupPermission();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity.this.finish();
                            }
                        })
                        .show();
            }
        }
    }
}