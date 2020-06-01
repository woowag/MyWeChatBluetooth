package com.example.weixinapplication;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.myviewholder> {

    private List<StickyData>list;
    private Context context;
    private View inflater;

    public static final int FIRST_STICKY_VIEW = 1;
    public static final int HAS_STICKY_VIEW = 2;
    public static final int NONE_STICKY_VIEW = 3;

    public adapter(Context context,List<StickyData>list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public adapter.myviewholder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        inflater= LayoutInflater.from(context).inflate(R.layout.item,viewGroup,false);
        myviewholder myviewholder=new myviewholder(inflater);
        return myviewholder;
    }

    @Override
    public void onBindViewHolder(adapter.myviewholder myviewholder, int position) {

        StickyData stickyData=list.get(position);
        myviewholder.tvTeam.setText(stickyData.getTeam());

        if (position == 0) {
            myviewholder.tvArea.setVisibility(View.VISIBLE);
            myviewholder.tvArea.setText(stickyData.area);
            myviewholder.itemView.setTag(FIRST_STICKY_VIEW);
        } else {
            if (!TextUtils.equals(stickyData.area, list.get(position - 1).area)) {
                myviewholder.tvArea.setVisibility(View.VISIBLE);
                myviewholder.tvArea.setText(stickyData.area);
                myviewholder.itemView.setTag(HAS_STICKY_VIEW);
            } else {
                myviewholder.tvArea.setVisibility(View.GONE);
                myviewholder.itemView.setTag(NONE_STICKY_VIEW);
            }
        }
        myviewholder.itemView.setContentDescription(stickyData.area);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myviewholder extends RecyclerView.ViewHolder{
        TextView tvArea,tvTeam;
        public myviewholder(View itemView) {
            super(itemView);
            tvArea=itemView.findViewById(R.id.tv_sticky_header_view);
            tvTeam = itemView.findViewById(R.id.tv_team);
        }
    }
}
