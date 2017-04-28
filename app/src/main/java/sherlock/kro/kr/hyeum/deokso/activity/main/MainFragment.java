package sherlock.kro.kr.hyeum.deokso.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import sherlock.kro.kr.hyeum.deokso.R;
import sherlock.kro.kr.hyeum.deokso.activity.bap.BapActivity;
import sherlock.kro.kr.hyeum.deokso.activity.exam.ExamTimeActivity;
import sherlock.kro.kr.hyeum.deokso.activity.home.Home2Activity;
import sherlock.kro.kr.hyeum.deokso.activity.home.HomeActivity;
import sherlock.kro.kr.hyeum.deokso.activity.notice.NoticeActivity;
import sherlock.kro.kr.hyeum.deokso.activity.schedule.ScheduleActivity;
import sherlock.kro.kr.hyeum.deokso.activity.timetable.TimeTableActivity;
import sherlock.kro.kr.hyeum.deokso.activity.school.SchoolSchedule;
import sherlock.kro.kr.hyeum.deokso.tool.BapTool;
import sherlock.kro.kr.hyeum.deokso.tool.Preference;
import sherlock.kro.kr.hyeum.deokso.tool.RecyclerItemClickListener;
import sherlock.kro.kr.hyeum.deokso.tool.TimeTableTool;


/**
 * Created by whdghks913 on 2015-11-30.
 */
public class MainFragment extends Fragment {

    public static Fragment getInstance(int code) {
        MainFragment mFragment = new MainFragment();

        Bundle args = new Bundle();
        args.putInt("code", code);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        final MainAdapter mAdapter = new MainAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View mView, int position) {
                boolean isSimple = mAdapter.getItemData(position).isSimple;
                Bundle args = getArguments();
                int code = args.getInt("code");
                if (isSimple) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), BapActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(getActivity(), TimeTableActivity.class));
                            break;
                    }
                }else if(code==2) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), ScheduleActivity.class));
                            break;
                        case 1:
                            //                    Toast.makeText(getActivity(), "2016년 일정 준비중..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), SchoolSchedule.class));
                            break;
                        case 2:
                            startActivity(new Intent(getActivity(), ExamTimeActivity.class));
                            break;
                        case 3:
                            break;
                    }
                }else if(code==3) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), NoticeActivity.class));
                            break;
                        case 1:
                            //                    Toast.makeText(getActivity(), "2016년 일정 준비중..", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), ScheduleActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(getActivity(), ExamTimeActivity.class));
                            break;
                        case 3:
                            break;
                    }
                }else if(code==4) {
                    switch (position) {
                        case 0:
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                            break;
                        case 1:
                            startActivity(new Intent(getActivity(), Home2Activity.class));

                            break;
                        case 2:
                            startActivity(new Intent(getActivity(), Calculate.class));

                            break;
                    }
                }

            }
        }));

        Bundle args = getArguments();
        int code = args.getInt("code");
        Preference mPref = new Preference(getActivity());

        if (code == 1) {
            // SimpleView
            if (mPref.getBoolean("simpleShowBap", true)) {
                BapTool.todayBapData mBapData = BapTool.getTodayBap(getActivity());
                mAdapter.addItem(R.drawable.rice,
                        getString(R.string.title_activity_bap),
                        getString(R.string.message_activity_bap),
                        mBapData.title,
                        mBapData.info);
            } else {
                mAdapter.addItem(R.drawable.rice,
                        getString(R.string.title_activity_bap),
                        getString(R.string.message_activity_bap), true);
            }

            if (mPref.getBoolean("simpleShowTimeTable", true)) {
                TimeTableTool.todayTimeTableData mTimeTableData = TimeTableTool.getTodayTimeTable(getActivity());
                mAdapter.addItem(R.drawable.timetable,
                        getString(R.string.title_activity_time_table),
                        getString(R.string.message_activity_time_table),
                        mTimeTableData.title,
                        mTimeTableData.info);
            } else {
                mAdapter.addItem(R.drawable.timetable,
                        getString(R.string.title_activity_time_table),
                        getString(R.string.message_activity_time_table), true);
            }
        } else if(code==2){
            // DetailedView
            mAdapter.addItem(R.drawable.calendar,
                    getString(R.string.title_activity_schedule),
                    getString(R.string.message_activity_schedule));
            mAdapter.addItem(R.drawable.school,
                    getString(R.string.title_activity_school),
                    getString(R.string.message_activity_school));
            mAdapter.addItem(R.drawable.ic_launcher_big,
                    getString(R.string.title_activity_exam_range),
                    getString(R.string.message_activity_exam_range));
        }else if(code==3){
            // DetailedView
            mAdapter.addItem(R.drawable.notice,
                    getString(R.string.title_activity_notice),
                    getString(R.string.message_activity_notice));
            mAdapter.addItem(R.drawable.list,
                    getString(R.string.title_activity_home),
                    getString(R.string.message_activity_home));
            mAdapter.addItem(R.drawable.article,
                    getString(R.string.title_activity_home2),
                    getString(R.string.message_activity_home2));
        }else if (code==4) {

            mAdapter.addItem(R.drawable.calculator,
                    getString(R.string.title_activity_calculator),
                    getString(R.string.message_activity_calculator));
        }

        return recyclerView;
    }
}
