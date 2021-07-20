package com.example.windqq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windqq.R;
import com.example.windqq.bean.User;

import java.util.List;

public class UserAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> mGroupList;
    private List<List<User>> mItemList;
    private LayoutInflater mInflater;

    public UserAdapter(Context mContext, List<String> mGroupList, List<List<User>> mItemList) {
        this.mContext = mContext;
        this.mGroupList = mGroupList;
        this.mItemList = mItemList;
        mInflater = LayoutInflater.from (mContext);

    }

    //size
    @Override
    public int getGroupCount() {
        return mGroupList.size ();
    }

    //ItemListgrouppo.size
    @Override
    public int getChildrenCount(int groupPosition) {
        return mItemList.get (groupPosition).size ();
    }

    //grouplist.getgroup
    @Override
    public Object getGroup(int groupPosition) {
        return mGroupList.get (groupPosition);
    }

    //itemlist.getgroup.getchild
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mItemList.get (groupPosition).get (childPosition);
    }

    //返回group
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    //返回child
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    //判断父项设置父项
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate (R.layout.group_item, null);
        }
        // 获取组数据指定位置的元素对象
        String groupString = mGroupList.get (groupPosition);
        // 获取显示组数据的TextView对象
        TextView tv_showGroup = convertView.findViewById (R.id.group_tv);
        // 将数据和视图 绑定
        tv_showGroup.setText (groupString);

        return convertView;
    }

    //判断子项设置子项
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = mInflater.inflate (R.layout.child_item, null);
        }

        // 获取组数据指定位置的集合对象，再获取该组中指定位置的子项数据
        User user = mItemList.get (groupPosition).get (childPosition);

        // 获取组中要显示子项的的TextView对象
        TextView child = convertView.findViewById (R.id.child);
        TextView tv_jianJie = convertView.findViewById (R.id.tv_title);
        ImageView iv_user = convertView.findViewById (R.id.iv_user);
        // 将数据和视图 绑定
        child.setText (user.getName ());
        tv_jianJie.setText (user.getQianming ());
        iv_user.setImageResource (user.getTouxiang ());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
