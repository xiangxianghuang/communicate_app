package com.yongyida.robot.hardware.test.item.hand;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yongyida.robot.hardware.test.R;

/**
 * Created by HuangXiangXiang on 2018/4/20.
 */
public class TestFingerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_finger, null) ;

        return view;
    }
}
