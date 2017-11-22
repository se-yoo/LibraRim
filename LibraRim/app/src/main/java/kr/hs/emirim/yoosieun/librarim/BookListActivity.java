package kr.hs.emirim.yoosieun.librarim;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Yoosieun on 2017-11-08.
 */


public class BookListActivity extends AppCompatActivity implements View.OnClickListener{

    private String img;
    private String booksgroup;
    private String selectedbooktitle="";

    String image;

    ListView bookList;

    Bitmap bitmap;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseRef;
    private ProgressBar progressBar;
    private TextView nothing;
    final BooksAdapter mBAdapter = new BooksAdapter();

    int bookcnt=0;

    String theWord;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        getWindow().setWindowAnimations(0); //전환효과 없애기

        theWord = null;
        bookList=(ListView)findViewById(R.id.book_listview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        Intent intent=getIntent();

        ImageView groupImg=(ImageView)findViewById(R.id.classification_img);
        ImageView bar=(ImageView)findViewById(R.id.bar);
        TextView search_String=(TextView)findViewById(R.id.search_String);
        nothing=(TextView)findViewById(R.id.nothing);
        img=intent.getStringExtra("group");
        nothing.setVisibility(View.INVISIBLE);
        search_String.setVisibility(View.INVISIBLE);

        final RelativeLayout backButton=(RelativeLayout)findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                setResult(0,intent);
                finish();
            }
        });

        switch (img){
            case "tof":
                booksgroup="00";
                groupImg.setImageResource(R.drawable.group_tof);
                groupImg.invalidate();
                break;
            case "art":
                booksgroup="60";
                groupImg.setImageResource(R.drawable.group_art);
                groupImg.invalidate();
                break;
            case "reli":
                booksgroup="20";
                groupImg.setImageResource(R.drawable.group_rel);
                groupImg.invalidate();
                break;
            case "sosci":
                booksgroup="30";
                groupImg.setImageResource(R.drawable.group_sosci);
                groupImg.invalidate();
                break;
            case "nas":
                booksgroup="40";
                groupImg.setImageResource(R.drawable.group_nasci);
                groupImg.invalidate();
                break;
            case "phil":
                booksgroup="10";
                groupImg.setImageResource(R.drawable.group_phil);
                groupImg.invalidate();
                break;
            case "dessci":
                booksgroup="50";
                groupImg.setImageResource(R.drawable.group_dessci);
                groupImg.invalidate();
                break;
            case "lang":
                booksgroup="70";
                groupImg.setImageResource(R.drawable.group_lang);
                groupImg.invalidate();
                break;
            case "lit":
                booksgroup="80";
                groupImg.setImageResource(R.drawable.group_lit);
                groupImg.invalidate();
                break;
            case "his":
                booksgroup="90";
                groupImg.setImageResource(R.drawable.group_his);
                groupImg.invalidate();
                break;
            case "hot":
                booksgroup="bestbook";
                groupImg.setImageResource(R.drawable.hotbook_2);
                groupImg.invalidate();
                break;
            case "new":
                booksgroup="newbook";
                groupImg.setImageResource(R.drawable.newbook);
                groupImg.invalidate();
                break;
            case "search":
                booksgroup="100";
                search_String.setVisibility(View.VISIBLE);
                groupImg.setVisibility(View.INVISIBLE);
                bar.setImageResource(R.drawable.search_bar_);
                bar.invalidate();

                theWord = intent.getStringExtra("searchStr");
                if(!theWord.equals(""))search_String.setText(intent.getStringExtra("searchStr")+" 의 검색결과 입니다");
                else search_String.setText("전체 책 목록입니다");
                break;
        }
        if(booksgroup=="newbook"){
            databaseRef =  database.getReference("newbook");
        }else if(booksgroup=="bestbook"){
            databaseRef =  database.getReference("bestbook");
        }else{
            databaseRef =  database.getReference("bookInfo");
        }

        dataSetting();
    }
    public void SetBtClick(View v){
        Intent intent=new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
    public void getBookInfoPage(View v){
        Intent intent=new Intent(this, BookInfoActivity.class);
        intent.putExtra("type",booksgroup);
        intent.putExtra("bookName",selectedbooktitle);
        startActivityForResult(intent,3);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent2=new Intent();
        if(requestCode==3&&resultCode==1){
            intent2.putExtra("move",data.getStringExtra("move"));
            setResult(1,intent2);
            finish();
        }
    }

    @Override
    public void onClick(View view) {

    }

    private void dataSetting() {
        Runnable run = new Runnable()
        {
            @Override
            public void run()
            {
                if(booksgroup.equals("newbook")||booksgroup.equals("bestbook")){
                    databaseRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                                bookcnt+=1;
                                image = fileSnapshot.child("img").getValue(String.class);
                                Thread mThread = new Thread() {
                                    public void run() {
                                        try {
                                            URL url = new URL(image);

                                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                            conn.setDoInput(true);
                                            conn.connect();

                                            InputStream is = conn.getInputStream();
                                            bitmap = BitmapFactory.decodeStream(is);

                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                mThread.start();
                                try {
                                    mThread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                String title = fileSnapshot.child("title").getValue(String.class);
                                String writ = fileSnapshot.child("witer").getValue(String.class);
                                String pub = fileSnapshot.child("pub").getValue(String.class);
                                String stat = fileSnapshot.child("status").getValue(String.class);
                                mBAdapter.addItem(bitmap, title, writ, pub, stat);
                                if(bookcnt==60) break;
                            }//for data끝까지
                            if( theWord != null )
                            {
                                mBAdapter.SetWord( theWord );
                            }
                            mBAdapter.notifyDataSetChanged();
                            bookList.setAdapter(mBAdapter);
                            bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView parent, View view, int position, long id) {
                                    selectedbooktitle=mBAdapter.getItem(position).getTitle();
                                    getBookInfoPage(view);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TAG: ", "Failed to read value", databaseError.toException());
                        }
                    });
                }
                else if(booksgroup =="100")
                {
                    for(int i=0;i<10;i++) {
                        booksgroup = ""+i+"0";
                        databaseRef.child(booksgroup).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                                    String title = fileSnapshot.child("title").getValue(String.class);
                                    String group = fileSnapshot.child("group").getValue(String.class);
                                    String writ;
                                    if (group.equals("00")|| group.equals("40") || group.equals("80")) {
                                        writ = fileSnapshot.child("witer").getValue(String.class);
                                    } else{
                                        writ = fileSnapshot.child("writer").getValue(String.class);
                                    }
                                    String pub = fileSnapshot.child("pub").getValue(String.class);
                                    String stat = fileSnapshot.child("status").getValue(String.class);
                                    mBAdapter.addItem2(title, writ, pub, stat,group);
                                }//for data끝까지

                                if( theWord != null )
                                {
                                    mBAdapter.SetWord( theWord );
                                }
                                mBAdapter.notifyDataSetChanged();
                                bookList.setAdapter(mBAdapter);
                                bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView parent, View view, int position, long id) {
                                        selectedbooktitle = mBAdapter.getItem(position).getTitle();
                                        booksgroup=mBAdapter.getItem(position).getGroup();
                                        getBookInfoPage(view);
                                    }
                                });

                                if(booksgroup.equals("90")&&mBAdapter.getCount()==0){
                                    progressBar.setVisibility(View.GONE);
                                    nothing.setVisibility(View.VISIBLE);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("TAG: ", "Failed to read value", databaseError.toException());
                            }
                        });
                    }
                }

                else {
                    databaseRef.child(booksgroup).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                                bookcnt+=1;
                                image = fileSnapshot.child("img").getValue(String.class);
                                Thread mThread = new Thread() {
                                    public void run() {
                                        try {
                                            URL url = new URL(image);

                                            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                            conn.setDoInput(true);
                                            conn.connect();

                                            InputStream is = conn.getInputStream();
                                            bitmap = BitmapFactory.decodeStream(is);

                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                mThread.start();
                                try {
                                    mThread.join();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                String title = fileSnapshot.child("title").getValue(String.class);
                                String writ;
                                if (booksgroup.equals("00") || booksgroup.equals("40") || booksgroup.equals("80")) {
                                    writ = fileSnapshot.child("witer").getValue(String.class);
                                } else {
                                    writ = fileSnapshot.child("writer").getValue(String.class);
                                }
                                String pub = fileSnapshot.child("pub").getValue(String.class);
                                String stat = fileSnapshot.child("status").getValue(String.class);
                                mBAdapter.addItem(bitmap, title, writ, pub, stat);
                                if (bookcnt == 60)
                                    break;
                            }//for data끝까지
                            mBAdapter.notifyDataSetChanged();
                            bookList.setAdapter(mBAdapter);
                            bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView parent, View view, int position, long id) {
                                    selectedbooktitle = mBAdapter.getItem(position).getTitle();
                                    getBookInfoPage(view);
                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("TAG: ", "Failed to read value", databaseError.toException());
                        }
                    });
                }
                // 모든 데이터를 로드하여 적용하였다면 어댑터에 알리고
                // 리스트뷰의 락을 해제합니다.
                mBAdapter.notifyDataSetChanged();
            }
        };

        Handler handler = new Handler();
        handler.postDelayed(run, 1000);
    }
}