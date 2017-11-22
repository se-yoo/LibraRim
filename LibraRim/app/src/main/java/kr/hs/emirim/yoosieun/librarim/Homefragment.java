package kr.hs.emirim.yoosieun.librarim;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

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

public class Homefragment extends Fragment{
    private EditText searchString;
    static boolean calledAlready = false;
    ImageView imView;
    String imgUrl;
    Bitmap bmImg;
    String tit;
    String type;
    //String img;
    private static FirebaseDatabase mDatabase;
    final String nBookName[] = new String[4];
    final String bBookName[] = new String[4];
    static Bitmap newB[]= new Bitmap[4];
    static Bitmap bestB[]= new Bitmap[4];
    int x=0; int y=0;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_home,container,false);
        final ImageButton mIv1, mIv2, mIv3, mIv4, mIv5, mIv6, mIv7, mIv8;
        mIv1 = (ImageButton)v.findViewById(R.id.imageView3);
        mIv2 = (ImageButton)v.findViewById(R.id.imageView4);
        mIv3 = (ImageButton)v.findViewById(R.id.imageView5);
        mIv4 = (ImageButton)v.findViewById(R.id.imageView6);
        //신간도서
        mIv5 = (ImageButton)v.findViewById(R.id.imageView7);
        mIv6 = (ImageButton)v.findViewById(R.id.imageView8);
        mIv7 = (ImageButton)v.findViewById(R.id.imageView9);
        mIv8 = (ImageButton)v.findViewById(R.id.imageView10);

        DatabaseReference mDbNew = FirebaseDatabase.getInstance().getReference("newbook");
        DatabaseReference mDbBest = FirebaseDatabase.getInstance().getReference("bestbook");

        mDbBest.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    if (y < 4) {
                        imgUrl = fileSnapshot.child("img").getValue(String.class);
                        Thread mThread = new Thread() {
                            public void run() {
                                try {
                                    URL url = new URL(imgUrl);

                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setDoInput(true);
                                    conn.connect();

                                    InputStream is = conn.getInputStream();
                                    Bitmap bmImg = BitmapFactory.decodeStream(is);

                                    bestB[y] = bmImg;
                                    Log.e("베비트맵스트림", bestB[y].toString());

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };//thread for image!!!!!! 😭
                        mThread.start();
                        try {
                            mThread.join();
                            //imageView.setImageBitmap(bitmap);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tit = fileSnapshot.child("title").getValue(String.class);
                        type = fileSnapshot.child("group").getValue(String.class);

                        bBookName[y] = tit;

                        y++;
                    }
                    else break;
                }//for data끝까지
                mIv1.setImageBitmap(bestB[0]);
                mIv2.setImageBitmap(bestB[1]);
                mIv3.setImageBitmap(bestB[2]);
                mIv4.setImageBitmap(bestB[3]);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        mDbNew.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("newDB", "들어갔나요");
                for (DataSnapshot fileSnapshot : dataSnapshot.getChildren()) {
                    if (x < 4) {
                        // TODO: 2017-11-18 이미지 !!!
                        imgUrl = fileSnapshot.child("img").getValue(String.class);
                        Thread mThread = new Thread() {
                            public void run() {
                                try {
                                    Log.i("image", imgUrl);
                                    URL url = new URL(imgUrl);

                                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                    conn.setDoInput(true);
                                    conn.connect();

                                    InputStream is = conn.getInputStream();
                                    bmImg = BitmapFactory.decodeStream(is);
                                    newB[x] = bmImg;
                                    Log.e("뉴비트맵스트림", newB[x].toString());

                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }catch (OutOfMemoryError e){
                                    Log.e("에러","뭔지몰그겠당");
                                }
                            }
                        };//thread for image!!!!!! 😭
                        mThread.start();
                        try {
                            mThread.join();
                            //imageView.setImageBitmap(bitmap);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        tit = fileSnapshot.child("title").getValue(String.class);
                        nBookName[x]=tit;
                        x++;
                    }
                    else break;
                }//for data끝까지

                mIv5.setImageBitmap(newB[0]);
                mIv6.setImageBitmap(newB[1]);
                mIv7.setImageBitmap(newB[2]);
                mIv8.setImageBitmap(newB[3]);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        }); //mDBnew
        mIv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookName", bBookName[0]);
                intent.putExtra("type", "bestbook");
                startActivity(intent);
            }
        });
        mIv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookName", bBookName[1]);
                intent.putExtra("type", "bestbook");
                startActivity(intent);
            }
        });
        mIv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookName", bBookName[2]);
                intent.putExtra("type", "bestbook");
                startActivity(intent);
            }
        });
        mIv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookName", bBookName[3]);
                intent.putExtra("type", "bestbook");
                startActivity(intent);
            }
        });

        mIv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookName", nBookName[0]);
                intent.putExtra("type", "newbook");
                startActivity(intent);
            }
        });
        mIv6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookName", nBookName[1]);
                intent.putExtra("type", "newbook");
                startActivity(intent);
            }
        });
        mIv7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookName", nBookName[2]);
                intent.putExtra("type", "newbook");
                startActivity(intent);
            }
        });
        mIv8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), BookInfoActivity.class);
                intent.putExtra("bookName", nBookName[3]);
                intent.putExtra("type", "newbook");
                startActivity(intent);
            }
        });

        return v;
    }//oncreateview

    public String getWord(){
        searchString=(EditText)getView().findViewById(R.id.searchBar);
        return  searchString.getText().toString();
    }
}
