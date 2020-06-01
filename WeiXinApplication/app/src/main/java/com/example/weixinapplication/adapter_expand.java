package com.example.weixinapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Map;

public class adapter_expand extends RecyclerView.Adapter<adapter_expand.expandviewholder> {

//    private List<String>list;
    private List<Map<String,String>>data;
    private Context context;
    private View inflater;

    private int expandedPosition = -1;
    private expandviewholder mViewHolder;

    public adapter_expand(Context context, List<Map<String,String>>data) {
        this.context=context;
        //this.list=list;
        this.data=data;
    }

    @Override
    public adapter_expand.expandviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater= LayoutInflater.from(context).inflate(R.layout.item_expand,parent,false);
        return new expandviewholder(inflater);
    }

    @Override
    public void onBindViewHolder(final adapter_expand.expandviewholder holder, int position) {
        //holder.tvTeam.setText(list.get(position));
        //holder.tvTeamChild.setText(list.get(position) + "的联系方式");
        holder.tvTeam.setText(data.get(position).get("name"));
        holder.tvTeamChild.setText("电话："+data.get(position).get("phoneNumber"));

        final boolean isExpanded = position == expandedPosition;
        holder.rlChild.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.rlParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewHolder != null) {
                    mViewHolder.rlChild.setVisibility(View.GONE);
                    notifyItemChanged(expandedPosition);
                }
                expandedPosition = isExpanded ? -1 : holder.getAdapterPosition();
                mViewHolder = isExpanded ? null : holder;
                notifyItemChanged(holder.getAdapterPosition());
            }
        });
    }

//    @Override
//    public int getItemCount() { return list.size();}
    @Override
    public int getItemCount() { return data.size();}

    class expandviewholder extends RecyclerView.ViewHolder{
        RelativeLayout rlParent, rlChild;
        TextView tvTeam, tvTeamChild;
        public expandviewholder(View itemView) {
            super(itemView);
            rlParent = itemView.findViewById(R.id.rl_parent);
            rlChild = itemView.findViewById(R.id.rl_child);
            tvTeam = itemView.findViewById(R.id.tv_team);
            tvTeamChild = itemView.findViewById(R.id.tv_team_child);
        }
    }
}
