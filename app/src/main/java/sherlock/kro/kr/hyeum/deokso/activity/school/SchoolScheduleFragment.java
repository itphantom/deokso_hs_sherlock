package sherlock.kro.kr.hyeum.deokso.activity.school;

/**
 * Created by KangHeewon on 2017. 4. 10..
 */

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sherlock.kro.kr.hyeum.deokso.R;
import sherlock.kro.kr.hyeum.deokso.tool.RecyclerItemClickListener;

public class SchoolScheduleFragment extends Fragment {

    public static Fragment getInstance(int month) {
        SchoolScheduleFragment mFragment = new SchoolScheduleFragment();

        Bundle args = new Bundle();
        args.putInt("month", month);
        mFragment.setArguments(args);

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recyclerview, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));

        final SchoolScheduleAdapter mAdapter = new SchoolScheduleAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View mView, int position) {
                try {
                    String date = mAdapter.getItemData(position).date;
                    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy.MM.dd (E)", Locale.KOREA);

                    Calendar mCalendar = Calendar.getInstance();
                    long nowTime = mCalendar.getTimeInMillis();

                    mCalendar.setTime(mFormat.parse(date));
                    long touchTime = mCalendar.getTimeInMillis();

                    long diff = (touchTime - nowTime);

                    boolean isPast = false;
                    if (diff < 0) {
                        diff = -diff;
                        isPast = true;
                    }

                    int diffDays = (int) (diff /= 24 * 60 * 60 * 1000);
                    String mText = "";

                    if (diffDays == 0)
                        mText += "오늘 일정입니다.";
                    else if (isPast)
                        mText = "선택하신 날짜는 " + diffDays + "일전 날짜입니다";
                    else
                        mText = "선택하신 날짜까지 " + diffDays + "일 남았습니다";

                    Snackbar.make(mView, mText, Snackbar.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));

        Bundle args = getArguments();
        int month = args.getInt("month");

        switch (month) {
            case 3:
                mAdapter.addItem("2017 Science Festival", "2017.03.20 (월)");
                mAdapter.addItem("SAM SCHOOL 물리 보고서대회", "2017.03.24 (월)");
                mAdapter.addItem("정약용 독후감 한마당", "2017.03.31 (월)");
                mAdapter.addItem("2017 교대독후감 대회", "2017.03.31 (월)");


                break;
            case 4:
                mAdapter.addItem("SAM SCHOOL 종이컵 쌓기 대회", "2017.04.06 (월)");
                mAdapter.addItem("2017 청소년 과학 탐구", "2017.04.10 (월)");
                mAdapter.addItem("SAM SCHOOL 수학 보고서대회", "2017.04.12 (월)");
                mAdapter.addItem("2017 교대독후감 대회", "2017.04.30 (월)");

                break;
            case 5:
                mAdapter.addItem("존경과 사랑을 그리다", "2017.05.01 (월)");
                mAdapter.addItem("슈퍼스터디 마니아 점검", "2017.05.09 (월)");
                mAdapter.addItem("슈퍼스터디 소감문 마감", "2017.05.11 (월)");
                mAdapter.addItem("수학이야기 말하기", "2017.05.11 (월)");
                mAdapter.addItem("독서캠프", "2017.05.12 (월)");
                mAdapter.addItem("흠연 예방 UCC/사진 제작 활동", "2017.05.15 (월)");
                mAdapter.addItem("2017 English Writing Contest", "2017.05.18 (월)");
                mAdapter.addItem("교내 논술 한마당 '논비", "2017.05.19 (월)");
                mAdapter.addItem("우리고장 역사유적 답사교실", "2017.05.20 (월)");
                mAdapter.addItem("소개팅 발표회", "2017.05.22 (월)");
                mAdapter.addItem("인문사회 프레젠테이션", "2017.05.23 (월)");
                mAdapter.addItem("수학통계 포스터만들기", "2017.05.24 (월)");
                mAdapter.addItem("자연과학 프레젠테이션", "2017.05.25 (월)");
                mAdapter.addItem("행복콘서트", "2017.05.26 (월)");
                mAdapter.addItem("SAM SCHOOL 생명과학 보고서", "2017.05.26 (월)");
                mAdapter.addItem("양성 평등 문예상", "2017.05.29 (월)");
                mAdapter.addItem("체험활동 소감문 쓰기", "2017.05.31 (월)");
                mAdapter.addItem("꿈 실현 독후감 발표회", "2017.05.31 (월)");
                break;
            case 6:
                mAdapter.addItem("교내 토론 배틀", "2017.06.08 (월)");
                mAdapter.addItem("슈퍼스터디 1차점검", "2017.06.13 (월)");
                mAdapter.addItem("SAM SCHOOL 태양광 태양열 대회", "2017.06.14 (월)");
                mAdapter.addItem("슈퍼스터디 2차 점검", "2017.06.20 (월)");
                mAdapter.addItem("슈퍼스터디 2차 점검", "2017.06.27 (월)");
                mAdapter.addItem("2017 교내독후감 대회", "2017.06.30 (월)");
                break;
            case 7:
                mAdapter.addItem("2017 우리말 겨루가", "2017.07.30 (월)");
                mAdapter.addItem("2017 생각노트 만들기", "2017.07.30 (월)");
                mAdapter.addItem("2017 English Speaking Contest", "2017.07.30 (월)");
                mAdapter.addItem("2017 보카신", "2017.07.30 (월)");
                mAdapter.addItem("인문,사회 에세이 세미나", "2017.07.30 (월)");
                mAdapter.addItem("2017 지리 오디세이/지리 논술", "2017.07.30 (월)");
                mAdapter.addItem("덕소 철학왕", "2017.07.30 (월)");
                mAdapter.addItem("정약용한마당(과거시험)", "2017.07.30 (월)");
                mAdapter.addItem("정약용한마당(다산 골든벨)", "2017.07.30 (월)");
                mAdapter.addItem("가로세로 Logic Logic", "2017.07.30 (월)");
                mAdapter.addItem("수학의 달인", "2017.07.30 (월)");
                mAdapter.addItem("21일간의 기적", "2017.07.30 (월)");
                mAdapter.addItem("도전! 친구와 함께 200등 올리기", "2017.07.30 (월)");
                mAdapter.addItem("SAM SCHOOL 과학콘서트", "2017.07.30 (월)");
                mAdapter.addItem("SAM SCHOOL 과학논문발표회", "2017.07.30 (월)");
                mAdapter.addItem("예향미술상", "2017.07.30 (월)");
                mAdapter.addItem("시사 카툰 그리기", "2017.07.30 (월)");
                mAdapter.addItem("2017 고내 독후감 발표회", "2017.07.30 (월)");
                mAdapter.addItem("자기소개서 쓰기", "2017.07.30 (월)");
                mAdapter.addItem("행복콘서트", "2017.07.30 (월)");
                mAdapter.addItem("심폐소생술 경연 우수상", "2017.07.30 (월)");

                break;
        }

        return recyclerView;
    }
}