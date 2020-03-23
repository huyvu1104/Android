package com.example.module1_drawingnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView ivColorPicker;
    private RadioGroup rgPenSize;
    private ImageView ivSave;
    private int currentColor;
    private int currentPenSize;
    private DrawView drawView;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog=new ProgressDialog(this);
        setContentView(R.layout.activity_draw);
        ivColorPicker=findViewById(R.id.iv_color_picker);
        rgPenSize=findViewById(R.id.rg_pen_size);
        ivSave=findViewById(R.id.iv_save);
        drawView=findViewById(R.id.drawing_view);
        ivColorPicker.setOnClickListener(this);
        ivSave.setOnClickListener(this);
        currentColor= ContextCompat.getColor(DrawActivity.this,R.color.colorAccent);
        ivColorPicker.setColorFilter(currentColor);
        currentPenSize=10;
        rgPenSize.check(R.id.rb_pen_size_normal);
        rgPenSize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_pen_size_thin:
                    break;
                    case R.id.rb_pen_size_normal:
                        break;
                    case R.id.rb_pen_size_strong:
                        break;
                }
                drawView.setCurrentSize(currentPenSize);
                Toast.makeText(DrawActivity.this, "pen size", Toast.LENGTH_SHORT).show();
            }
        });
        drawView.setCurrentColor(currentColor);
        drawView.setCurrentSize(currentPenSize);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_color_picker:
                showColorPickerDialog();
            break;
            case R.id.iv_save:
                saveImage();
            break;
        }
    }

    private void saveImage() {

        progressDialog.setMessage("Saving...");
        progressDialog.show();
        drawView.setDrawingCacheEnabled(true);
        drawView.buildDrawingCache();
        Bitmap bitmap=drawView.getDrawingCache();
        ImageUtils.saveImage(bitmap,DrawActivity.this);

        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progressDialog.dismiss();
    }

    private void showColorPickerDialog() {
        ColorPickerDialogBuilder
            .with(DrawActivity.this)
            .setTitle("Choose color")
            .initialColor(currentColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setOnColorSelectedListener(new OnColorSelectedListener() {
                @Override
                public void onColorSelected(int selectedColor) {
                    Toast.makeText(DrawActivity.this, "coColorSelected", Toast.LENGTH_SHORT).show();
                }
            })
            .setPositiveButton("Ok", new ColorPickerClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                    currentColor=selectedColor;
                    drawView.setCurrentColor(currentColor);
                    ivColorPicker.setColorFilter(currentColor);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            })
            .build()
            .show();
    }
}
