package jay.com.listviewdemo3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

/**
 * Created by Jay on 2015/9/21 0021.
 */
public class MyAdapter extends BaseAdapter {

    private Context mContext;
    private LinkedList<Data> mData;

    public MyAdapter() {
    }

    public MyAdapter(LinkedList<Data> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, parent, false);
            holder = new ViewHolder();
            holder.img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
            holder.txt_content = (TextView) convertView.findViewById(R.id.txt_content);
            holder.sb_normal = (SeekBar) convertView.findViewById(R.id.sb_normal);
            holder.txt_cur=(TextView) convertView.findViewById(R.id.txt_cur);
            holder.sb_normal.setTag(holder.txt_cur);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.img_icon.setImageResource(mData.get(position).getImgId());
        holder.txt_content.setText(mData.get(position).getContent());
        Sbarlistener sblistener = new Sbarlistener(holder.txt_cur,position);
         holder.sb_normal.setOnSeekBarChangeListener(sblistener);
        return convertView;
    }

    //添加一个元素
    public void add(Data data) {
        if (mData == null) {
            mData = new LinkedList<>();
        }
        mData.add(data);
        notifyDataSetChanged();
    }

    //往特定位置，添加一个元素
    public void add(int position,Data data){
        if (mData == null) {
            mData = new LinkedList<>();
        }
        mData.add(position, data);
        notifyDataSetChanged();
    }

    public void remove(Data data) {
        if(mData != null) {
            mData.remove(data);
        }
        notifyDataSetChanged();
    }

    public void remove(int position) {
        if(mData != null) {
            mData.remove(position);
        }
        notifyDataSetChanged();
    }

    public void clear() {
        if(mData != null) {
            mData.clear();
        }
        notifyDataSetChanged();
    }





    private class ViewHolder {
        ImageView img_icon;
        TextView txt_content;
        SeekBar sb_normal;
        TextView txt_cur;
    }
    private class Sbarlistener implements SeekBar.OnSeekBarChangeListener {
        TextView tempview;
        int temppos;
        int p_val;
        public Sbarlistener(TextView tv,int pos){
            tempview=tv;
            temppos=pos;
            p_val=1;
        }
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            //方法一：传递textview进行修改
//            tempview.setText("当前进度:" + progress + "  / 100 ");
            //方法二：得到textview进行修改
               ViewParent pv= seekBar.getParent();
            View  cv = (View)pv;
            ( (TextView) cv.findViewById(R.id.txt_cur)).setText("当前进度:" + progress + "  / 100 ");
            p_val=progress;

//              View  cv = LayoutInflater.from(mContext).inflate(R.layout.item_list, cur_parent, false);
//                ( (TextView) cv.findViewById(R.id.txt_cur)).setText("当前进度:" + progress + "  / 100 ");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
//                    Toast.makeText(mContext, "Touch SeekBar", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
                    Toast.makeText(mContext, "release SeekBar:pos:"+temppos+",value:"+p_val, Toast.LENGTH_SHORT).show();
        }
    }

}
