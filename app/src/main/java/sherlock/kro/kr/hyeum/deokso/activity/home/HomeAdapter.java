package sherlock.kro.kr.hyeum.deokso.activity.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import sherlock.kro.kr.hyeum.deokso.R;


/**
 * Created by whdghks913 on 2015-12-10.
 */
class HomeViewHolder {
    public TextView mTitle;
    public TextView mMessage;
    public TextView mDate;
}

class HomeShowData {
    public String title;
    public String message;
    public String date;
    public String link;
}

public class HomeAdapter extends BaseAdapter {
    private Context mContext = null;
    private ArrayList<HomeShowData> mListData = new ArrayList<HomeShowData>();

    public HomeAdapter(Context mContext) {
        super();

        this.mContext = mContext;
    }

       public void addItem(String title, String message, String date,String link) {
        HomeShowData addItemInfo = new HomeShowData();
        addItemInfo.title = title;
        addItemInfo.message = message;
        addItemInfo.date = date;
        addItemInfo.link = link;

        mListData.add(addItemInfo);
    }


    public void clearData() {
        mListData.clear();
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public HomeShowData getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        HomeViewHolder mHolder;

        if (convertView == null) {
            mHolder = new HomeViewHolder();

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_home_item, null);

            mHolder.mTitle = (TextView) convertView.findViewById(R.id.mTitle2);
            mHolder.mMessage = (TextView) convertView.findViewById(R.id.mMessage2);
            mHolder.mDate = (TextView) convertView.findViewById(R.id.mDate2);

            convertView.setTag(mHolder);
        } else {
            mHolder = (HomeViewHolder) convertView.getTag();
        }
        HomeShowData mData = mListData.get(position);

        String title = mData.title;
        String message = mData.message;
        String date = mData.date;
        String link = mData.link;

        mHolder.mTitle.setText(title);
        mHolder.mMessage.setText(message);
        mHolder.mDate.setText(date);

        return convertView;
    }
}
