package com.example.windqq.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.windqq.R;
import com.example.windqq.activity.AudiosActivity;
import com.example.windqq.adapter.UserAdapter;
import com.example.windqq.bean.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LXRFragment extends Fragment {
    private View view;
    private ExpandableListView mExView;
    private ArrayList<String> mgrouplist;
    private List<List<User>> mItemlist;

    public LXRFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate (R.layout.fragment_two, container, false);
        initData();
        initView (inflate);
        return inflate;
    }

    private void initData() {
        mgrouplist = new ArrayList<> ();
        mgrouplist.add("特别关心");
        mgrouplist.add("不畏将来");
        mgrouplist.add("不念过往");
        mgrouplist.add("不乱于心");
        mgrouplist.add("不困于情");
        mgrouplist.add("耶☚");

        //初始化子项数据
        mItemlist = new ArrayList<>();

        //第一个小集合
        List<User> mItemOneList = new ArrayList<>();
        mItemOneList.add(new User(R.mipmap.lxl,"001","[手机在线] 走自己的路！",Long.valueOf ("222222")));
        mItemOneList.add(new User(R.mipmap.lp,"八月","[4G在线]",Long.valueOf ("1362318797")));
        mItemOneList.add(new User(R.mipmap.wd,"焦凤航","[TIM在线]    恋伊",Long.valueOf ("1638253187")));

        //第二个小集合
        List<User> mItemTwoList = new ArrayList<>();
        mItemTwoList.add(new User(R.mipmap.ls_user,"004","[WIFI在线] ",Long.valueOf ("7777777")));
        mItemTwoList.add(new User(R.mipmap.bahv,"005","[4G在线]    再见，坏手机，我们都需…",Long.valueOf ("111111")));
        mItemTwoList.add(new User(R.mipmap.hd_user,"006","[4G在线] 加油，每一天都会更好，相信…",Long.valueOf ("111222333")));
        mItemTwoList.add(new User(R.mipmap.bahv,"007","[4G在线] 大热天的，盘他",Long.valueOf ("123456")));
        mItemTwoList.add(new User(R.mipmap.hr_user,"008","[手机在线] 我梦到我妹理我了",Long.valueOf ("123123123")));

        //第三个小集合
        List<User> mItemThreeList = new ArrayList<>();
        mItemThreeList.add(new User(R.mipmap.wzf,"009","[4G在线] 现在我什么都没有了，可以不说了嘛？",Long.valueOf ("1414141414")));
        mItemThreeList.add(new User(R.mipmap.ll,"0010","[4G在线] 打卡，第一天[表情]",Long.valueOf ("1515151515")));
        mItemThreeList.add(new User(R.mipmap.lxx,"0011","[手机在线] 刚知道这街石头是新乡人!!…",Long.valueOf ("456789")));

        //第四个小集合
        List<User> mItemFourList = new ArrayList<>();
        mItemFourList.add(new User(R.mipmap.ly,"0012","[手机在线]",Long.valueOf ("111111")));
        mItemFourList.add(new User(R.mipmap.mm,"0013","[手机在线]",Long.valueOf ("223344")));
        mItemFourList.add(new User(R.mipmap.yk,"0014","[手机在线]",Long.valueOf ("332211")));
        mItemFourList.add(new User(R.mipmap.nly,"0015","[离线请留言]",Long.valueOf ("11223344")));
   //第五个小集合
        List<User> mItemFiveList = new ArrayList<>();
        mItemFiveList.add(new User(R.mipmap.wd,"0016","[TIM在线]   恋伊❤",Long.valueOf ("1638253187")));
   //第六个小集合
        List<User> mItemSixList = new ArrayList<>();
        mItemSixList.add(new User(R.mipmap.bahv,"0017","[4G在线] 我爱我家",Long.valueOf ("11111111")));
        mItemSixList.add(new User(R.mipmap.lxl,"0018","[手机在线] 走自己的路！",Long.valueOf ("123123123123")));
        mItemSixList.add(new User(R.mipmap.bahv,"0019","[手机在线] 明白什么是吃一堑长一智  可是需…",Long.valueOf ("1975020061")));

        //将小集合添加到子项大集合中
        mItemlist.add(mItemOneList);
        mItemlist.add(mItemTwoList);
        mItemlist.add(mItemThreeList);
        mItemlist.add(mItemFourList);
        mItemlist.add(mItemFiveList);
        mItemlist.add(mItemSixList);
    }

    private void initView(View inflate) {
        mExView = (ExpandableListView) inflate.findViewById (R.id.ex_view);
        //创建适配器
        UserAdapter myAdapter = new UserAdapter (getActivity (), mgrouplist, mItemlist);
        //绑定适配器
        mExView.setAdapter(myAdapter);
        //为组设置点击事件
        mExView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                String s = mgrouplist.get(groupPosition);
                return false;
            }
        });

        //为子项设置点击事件
        mExView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                User user = mItemlist.get(groupPosition).get(childPosition);
                Long id1 = user.getId ();
//                String url = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + id1
//                        + "&card_type=person&source=qrcode";
//                startActivity (new Intent (Intent.ACTION_VIEW, Uri.parse (url)));
                Intent intent = new Intent(getActivity(), AudiosActivity.class);
                intent.putExtra("users","好友");
                intent.putExtra("codes",id1);
                intent.putExtra("types",100);
                intent.putExtra("onlins",1);
                startActivity(intent);
                return false;
            }
        });

        //为二级列表设置组展开的监听事件
        mExView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(getActivity (), "打开", Toast.LENGTH_SHORT).show();
                ///groupPosition  是当前打开组对应的Position

                //当展开当前组的时候，判断其他组有没有被展开的，如果是被展开的我们就关闭
                for (int i = 0; i < mgrouplist.size(); i++) {//0,1,2,3
                    if(i==groupPosition){
                        continue;
                    }
                    //判断某一个组是否被打开
                    if(mExView.isGroupExpanded(i)){
                        //关闭其他展开的组
//                        mExView.collapseGroup(i);
                    }
                }
            }
        });

        //为二级列表设置组收缩的监听事件
        mExView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(getActivity (), "关闭", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
