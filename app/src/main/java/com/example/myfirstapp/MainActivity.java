package com.example.myfirstapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button btnNext;
    private TextView btnQuit;
    private TextView strNumber;
    private ConstraintLayout layout;
    private final Random rnd = new Random();
    private List<String> lastTenLetters = new ArrayList<>(Arrays.asList("-","-","-","-","-","-","-","-","-","-"));
    final String[] letters = {"A,Á","B","C","CS","D","E,É","F","G","GY","H","I,Í","J","K","L","M","N","NY",
            "O,Ó","Ö,Ő","P","R","S","T","TY","U,Ú","Ü,Ű","V","ZS"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFullscreen();
        initGui();
        initRotatingExitButton();
        initFoggyBackgroundAnimation();
        rollInThisLetter(getOneLetter());
        initListeners();
    }

    private void initListeners() {
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollInThisLetter(getOneLetter());
            }
        });

        btnQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quit();
            }
        });
    }

    private String getOneLetter() {
        String result;
        while(true) {
            int num = rnd.nextInt(letters.length);
            String letter = letters[num];
            if (!lastTenLetters.contains(letter)) {
                lastTenLetters.add(0, letter);
                lastTenLetters.remove(10);
                result = letter;
                break;
            }
        }
        return result;
    }

    private void rollInThisLetter(String letter) {
        strNumber.setText(letter);
        YoYo.with(Techniques.RollIn)
                .duration(500)
                .playOn(strNumber);
    }

    private void initFoggyBackgroundAnimation() {
        AnimationDrawable anim = (AnimationDrawable) layout.getBackground();
        anim.setEnterFadeDuration(2500);
        anim.setExitFadeDuration(2500);
        anim.start();
    }

    private void setFullscreen() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
    }

    private void initRotatingExitButton() {
        RotateAnimation rotate= (RotateAnimation) AnimationUtils.loadAnimation(this,R.anim.rotateanimation);
        btnQuit.setAnimation(rotate);
        rotate.setStartOffset(5000);
        rotate.setRepeatCount(Animation.INFINITE);
    }

    private void quit() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.sym_def_app_icon)
                .setTitle("Quit?")
                .setMessage("Are you sure you want to close this application?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAndRemoveTask();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void initGui() {
        btnNext = findViewById(R.id.btnNext);
        btnQuit = findViewById(R.id.tvQuit);
        strNumber = findViewById(R.id.strNumber);
        strNumber.setTextSize(getResources().getDimension(R.dimen.smallText));
        layout = findViewById(R.id.layout);
    }
}