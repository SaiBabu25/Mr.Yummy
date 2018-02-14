package yummy.mryummy.Splash.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import yummy.mryummy.R;
import yummy.mryummy.Splash.Model.Bean;

/**
 * Created by acer on 12/5/2017.
 */

public class MyAdapter extends BaseAdapter {
    private Context mContext;
    private List<Bean> mList;


    public MyAdapter(Context context,List<Bean> list){
        mContext=context;
        mList=list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        //use convertView recycle
        if(convertView==null){
            holder=new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.content_main, parent, false);
            holder.textView= (TextView) convertView.findViewById(R.id.textView);
            holder.imageView= (ImageView) convertView.findViewById(R.id.imageView);
            holder.imageView2 = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.imageView3 = (ImageView) convertView.findViewById(R.id.imageView3);
            holder.imageView4 = (ImageView) convertView.findViewById(R.id.imageView4);
            holder.horizontalScrollView= (HorizontalScrollView) convertView.findViewById(R.id.horizontalScrollView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(mList.get(position).getText());
        Picasso.with(mContext).load(mList.get(position).getUrl()).resize(400,400).into(holder.imageView);
        Picasso.with(mContext).load(mList.get(position).getUrl2()).resize(400,400).into(holder.imageView2);
        Picasso.with(mContext).load(mList.get(position).getUrl3()).resize(400,400).into(holder.imageView3);
        Picasso.with(mContext).load(mList.get(position).getUrl4()).resize(400,400).into(holder.imageView4);


        return convertView;
    }

    class ViewHolder{
        TextView textView;
        ImageView imageView;
        ImageView imageView2;
        ImageView imageView3;
        ImageView imageView4;
        HorizontalScrollView horizontalScrollView;
    }
}
