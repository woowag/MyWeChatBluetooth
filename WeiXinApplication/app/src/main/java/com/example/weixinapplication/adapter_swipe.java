package com.example.weixinapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter_swipe extends RecyclerView.Adapter<adapter_swipe.swipeviewholder> implements ItemTouchHelperListener{

    private List<String>list;
    private Context context;
    private View inflater;

    public adapter_swipe(Context context, List<String> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public adapter_swipe.swipeviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater= LayoutInflater.from(context).inflate(R.layout.item_swipe,parent,false);
        swipeviewholder swipeviewholder=new swipeviewholder(inflater);
        return swipeviewholder;
    }

    @Override
    public void onBindViewHolder(adapter_swipe.swipeviewholder holder, int position) {
        holder.tvContent.setText(list.get(position));
    }

    @Override
    public int getItemCount() {return list.size();}

    @Override
    public void onItemDismiss(int position) {
        if (position < 0 || position > getItemCount()) {
            return;
        }

        list.remove(position);
        notifyItemRemoved(position);

        // 解决 RecyclerView 删除 Item 导致位置错乱的问题
        if (position != list.size()) {
            notifyItemRangeChanged(position, list.size() - position);
        }
    }

    public class swipeviewholder extends RecyclerView.ViewHolder{
        TextView tvContent;

        public swipeviewholder(View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}
