package com.example.onlineshopp.FragmentLayout;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.onlineshopp.ActivityLayout.Activity_login;
import com.example.onlineshopp.R;
import com.example.onlineshopp.temptlA;

public class Fragment_me extends Fragment {

    private FragmentMeViewModel mViewModel;
    public static Fragment_me newInstance() {
        return new Fragment_me();
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FragmentMeViewModel.class);
        // TODO: Use the ViewModel

        if(!temptlA.isLogin){
            Intent i=new Intent(getActivity(), Activity_login.class);
            startActivity(i);
        }else{

        }

    }

}