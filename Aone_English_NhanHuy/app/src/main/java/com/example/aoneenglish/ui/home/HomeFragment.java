package com.example.aoneenglish.ui.home;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.aoneenglish.Fill_in_the_word.Fill_in_the_word_Activity;
import com.example.aoneenglish.Learning_vocabulary.Learning_Voca_Activity;
import com.example.aoneenglish.Listening.Listening_Activity;
import com.example.aoneenglish.R;
import com.example.aoneenglish.account.RankingActivity;
import com.example.aoneenglish.multiple_choice.Activity_multiple_choice;


public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    CardView cardViewLeanVoca, cardViewTracNghiem, cardViewListening,cardViewDienKhuyet,cardViewRank;
    ImageView imgview;
    final  String DATABASE_NAME = "HocTiengAnh.db";
    SQLiteDatabase database;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        cardViewLeanVoca = root.findViewById(R.id.cardViewHocTuVung);
        cardViewDienKhuyet= root.findViewById(R.id.cardViewDienKhuyet);
        cardViewTracNghiem= root.findViewById(R.id.cardViewTracNghiem);
        cardViewListening = root.findViewById(R.id.cardViewLuyenNghe);
        cardViewRank = root.findViewById(R.id.cardViewXepHang);

        cardViewListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Listening_Activity.class);
                startActivity(intent);
            }
        });

        cardViewLeanVoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Learning_Voca_Activity.class);
                startActivity(intent);
            }
        });

        cardViewDienKhuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Fill_in_the_word_Activity.class);
                startActivity(intent);
            }
        });

        cardViewTracNghiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), Activity_multiple_choice.class);
                startActivity(intent);
            }
        });

        cardViewRank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), RankingActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }



}