package com.example.query_student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.query_student.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 查询
     */
    private Button mBtnQuery;
    private TextView mTvQuery;
    private ArrayList<Student> list;
    private List<Student> student;
    private String home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        initData ();

    }

    private void initView() {
        mBtnQuery = (Button) findViewById (R.id.btn_query);
        mBtnQuery.setOnClickListener (this);
        mTvQuery = (TextView) findViewById (R.id.tv_query);
    }

    private void initData() {
        ArrayList<Student> students = new ArrayList<> ();
        for (int i = 0; students.size () < 10; i++) {
            Student student = new Student ();
            student.setHome ("苏州" + i);
            student.setAge (0 + i);
            student.setName ("胡图图" + i);
            students.add (student);
        }
        Log.e ("tag", students.toString ());
        list = new ArrayList<> ();
        for (Student sss : students) {
            if (sss.getAge ()>5) {
                list.add (sss);
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            default:
                break;
            case R.id.btn_query:
                for (int i = 0; i < list.size (); i++) {
                mTvQuery.setText (list.get (i).getName ());
                Log.e ("tag",list.get (i).getHome ());
                }
                break;
        }
    }
}
