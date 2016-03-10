package com.example.app.rxjava.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.app.rxjava.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * create an instance of this fragment.
 */
public class BaseFragment extends Fragment implements BaseViewIA {
    /**
     * 进度条
     */
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("正在加载，请稍后..");
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }
    @Override
    public void hideProgressDialog() {
        mProgressDialog.hide();
    }
    @Override
    public void showError(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
