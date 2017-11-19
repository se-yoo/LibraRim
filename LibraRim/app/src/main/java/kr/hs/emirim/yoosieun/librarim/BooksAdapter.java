package kr.hs.emirim.yoosieun.librarim;

import android.content.Context;
import android.graphics.Bitmap;
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
    private LayoutInflater inflater;
    private ArrayList<BookItem> mBooks = new ArrayList<>();
    private int layout;

    @Override
    public int getCount() {
        return mBooks.size();
    }

    @Override
    public BookItem getItem(int position) {
        return mBooks.get(position);
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
        BookItem bookItem = mBooks.get(position);
        ImageView bookprofile = (ImageView)convertView.findViewById(R.id.bookprofile);
        TextView list_booktitle = (TextView)convertView.findViewById(R.id.list_booktitle); //제목
        TextView list_bookwriter = (TextView)convertView.findViewById(R.id.list_bookwriter); //작가
        TextView list_bookstatus = (TextView)convertView.findViewById(R.id.list_bookstatus); //대출여부
        bookprofile.setImageBitmap(bookItem.getBookImg());
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
    }
}
