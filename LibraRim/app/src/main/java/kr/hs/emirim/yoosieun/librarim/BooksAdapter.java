package kr.hs.emirim.yoosieun.librarim;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Student on 2017-11-19.
 */

class BooksAdapter extends BaseAdapter {
    private ArrayList<BookItem> mBooks = new ArrayList<>();
    ImageView bookprofile;

    ArrayList<BookItem> mResults = new ArrayList<>();
    String theWord;
    public void SetWord( String aWord )
    {
        theWord = aWord;
        mResults.clear();
        for( BookItem iter : mBooks )
        {
            if( iter.getTitle().contains( theWord ) == true )
            {
                mResults.add( iter );
            }
        }
    }
    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public BookItem getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_list_item, parent, false);
        }
        BookItem bookItem = mResults.get(position);
        bookprofile = (ImageView)convertView.findViewById(R.id.bookprofile);
        TextView list_booktitle = (TextView)convertView.findViewById(R.id.list_booktitle); //제목
        TextView list_bookwriter = (TextView)convertView.findViewById(R.id.list_bookwriter); //작가
        TextView list_bookstatus = (TextView)convertView.findViewById(R.id.list_bookstatus); //대출여부
        bookprofile.setImageBitmap(bookItem.getBookImg());
        if(bookItem.getBookImg()==null)bookprofile.setVisibility(View.GONE);
        list_booktitle.setText(bookItem.getTitle());
        list_bookwriter.setText(bookItem.getWriter()+"/"+bookItem.getPub());
        list_bookstatus.setText(bookItem.getStatus());
        return convertView;
    }
    public void addItem(Bitmap bitmap, String title, String writer, String pub, String status) {
        BookItem mBook = new BookItem();

        /* MyItem에 아이템을 setting한다. */
        mBook.setBookImg(bitmap);
        mBook.setTitle(title);
        mBook.setWriter(writer);
        mBook.setPub(pub);
        mBook.setStatus(status);

        /* mBooks에 MyItem을 추가한다. */
        mBooks.add(mBook);
        mResults.add( mBook );
    }

    public void addItem2(String title, String writer, String pub, String status,String group) {
        BookItem mBook = new BookItem();

        /* MyItem에 아이템을 setting한다. */
        mBook.setTitle(title);
        mBook.setWriter(writer);
        mBook.setPub(pub);
        mBook.setStatus(status);
        mBook.setGroup(group);

        /* mBooks에 MyItem을 추가한다. */
        mBooks.add(mBook);
        mResults.add( mBook );
    }
}
