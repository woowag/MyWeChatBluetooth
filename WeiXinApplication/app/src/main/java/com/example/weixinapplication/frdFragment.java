package com.example.weixinapplication;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.app.Fragment;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class frdFragment extends Fragment {

    private static final String TAG = frdFragment.class.getSimpleName();

    private RecyclerView recyclerView;
    private TextView tvArea;
    private List<String> mList = new ArrayList<>();
    private List<StickyData> mDataList = new ArrayList<>();
    private Context context;
    private adapter adapter;

    public frdFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.tab02, container, false);

        recyclerView=view.findViewById(R.id.recycleview);
        tvArea=view.findViewById(R.id.tv_sticky_header_view);

        initList();
        initData();
        initView();

        return view;
    }
    private void initList() {
        mList.add("亲友|夏洛特黄蜂");
        mList.add("亲友|迈阿密热火");
        mList.add("亲友|奥兰多魔术");
        mList.add("亲友|华盛顿奇才");
        mList.add("亲友|波士顿凯尔特人");
        mList.add("亲友|布鲁克林篮网");
        mList.add("密友|纽约尼克斯");
        mList.add("密友|费城76人");
        mList.add("密友|多伦多猛龙");
        mList.add("密友|芝加哥公牛");
        mList.add("密友|克里夫兰骑士");
        mList.add("密友|底特律活塞");
        mList.add("密友|印第安纳步行者");
        mList.add("密友|密尔沃基雄鹿");
        mList.add("密友|达拉斯独行侠");
        mList.add("密友|休斯顿火箭");
        mList.add("密友|萨克拉门托国王");
        mList.add("同学|哈哈哈哈哈");
        mList.add("同学|哒哒哒哒哒哒");
        mList.add("同学|嘿嘿嘿嘿嘿");
        mList.add("同学|顶顶顶顶顶的");
        mList.add("同学|啦啦啦啦啦啦");
        mList.add("同学|啧啧啧啧啧啧");
        mList.add("同学|孟菲斯灰熊");
        mList.add("同学|新奥尔良鹈鹕");
        mList.add("同学|圣安东尼奥马刺");
        mList.add("同学|丹佛掘金");
        mList.add("同学|明尼苏达森林狼");
        mList.add("同学|俄克拉荷马城雷霆");
        mList.add("同学|波特兰开拓者");
        mList.add("同学|犹他爵士");
        mList.add("同学|金州勇士");
        mList.add("同学|洛杉矶快船");
        mList.add("同学|洛杉矶湖人");
        mList.add("同学|菲尼克斯太阳");
    }

    private void initData() {
        for (int i = 0; i < mList.size(); i++) {
            StickyData bean = new StickyData();

            String s = mList.get(i);
            // area
            String area = s.substring(0, s.indexOf("|"));
            // team
            String team = s.substring(s.indexOf("|") + 1, s.length());

            bean.setArea(area);
            bean.setTeam(team);

            mDataList.add(bean);
        }

        Log.d(TAG, "initData: " + mDataList.size());
    }

    private void initView() {
        context=this.getActivity();
        adapter=new adapter(context,mDataList);

        LinearLayoutManager manager=new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View stickyInfoView = recyclerView.findChildViewUnder(
                        tvArea.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tvArea.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tvArea.getMeasuredWidth() / 2, tvArea.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tvArea.getMeasuredHeight();

                    if (transViewStatus == adapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tvArea.setTranslationY(dealtY);
                        } else {
                            tvArea.setTranslationY(0);
                        }
                    } else if (transViewStatus == adapter.NONE_STICKY_VIEW) {
                        tvArea.setTranslationY(0);
                    }
                }
            }
        });
    }
}
