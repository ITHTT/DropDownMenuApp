package com.htt.dropdownmenu.views.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.htt.dropdownmenu.R;
import com.htt.dropdownmenu.modles.GoodInfo;
import com.htt.dropdownmenu.views.widgets.goodcar.OnAddGoodListener;

import java.util.List;

/**
 * Created by HTT on 2016/4/26.
 */
public class GoodListAdapter extends RecyclerView.Adapter<GoodListAdapter.GoodListViewHolder>{
    private List<GoodInfo> goodInfoList;
    private OnAddGoodListener onAddGoodListener;

    public GoodListAdapter(List<GoodInfo>list,OnAddGoodListener onAddGoodListener){
        this.goodInfoList=list;
        this.onAddGoodListener=onAddGoodListener;
    }
    @Override
    public GoodListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_good_item,parent,false);
        return new GoodListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GoodListViewHolder holder, final int position) {
        final GoodInfo good=goodInfoList.get(position);
        holder.ivGoodIcon.setImageResource(good.getGoodIconRes());
        holder.tvGoodName.setText(good.getGoodName());
        holder.btAddGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onAddGoodListener!=null){
                    onAddGoodListener.onAddGood(position,holder.ivGoodIcon);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return goodInfoList.size();
    }

    public static final class GoodListViewHolder extends RecyclerView.ViewHolder{
        ImageView ivGoodIcon;
        TextView tvGoodName;
        Button btAddGood;
        public GoodListViewHolder(View itemView) {
            super(itemView);
            ivGoodIcon= (ImageView) itemView.findViewById(R.id.iv_good_icon);
            tvGoodName=(TextView)itemView.findViewById(R.id.tv_good_name);
            btAddGood=(Button)itemView.findViewById(R.id.bt_add_good);
        }
    }
}
