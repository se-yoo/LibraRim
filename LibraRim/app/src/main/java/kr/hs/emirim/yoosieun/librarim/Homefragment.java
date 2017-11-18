package kr.hs.emirim.yoosieun.librarim;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * Created by Yoosieun on 2017-11-08.
 */

public class Homefragment extends Fragment{
    private EditText searchString;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    public String getWord(){
        searchString=(EditText)getView().findViewById(R.id.searchBar);
        return  searchString.getText().toString();
    }
}
