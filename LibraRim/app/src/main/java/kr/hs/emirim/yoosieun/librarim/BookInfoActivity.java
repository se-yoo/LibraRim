package kr.hs.emirim.yoosieun.librarim;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by Yoosieun on 2017-11-08.
 */


public class BookInfoActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        getWindow().setWindowAnimations(0); //전환효과 없애기

        final RelativeLayout backButton=(RelativeLayout)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(0,intent);
                finish();
            }
        });
    }

    public void SetButtClick(View v){
        Intent intent=new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void onMenuBtnClick(View v){
        Intent intent=new Intent();
        switch (v.getId()){
            case R.id.home_button:
                intent.putExtra("move","home");
                break;
            case R.id.classification_button:
                intent.putExtra("move","classification");
                break;
            case R.id.application_button:
                intent.putExtra("move","application");
                break;
            case R.id.info_button:
                intent.putExtra("move","info");
                break;
        }
        setResult(1,intent);
        finish();
    }
}