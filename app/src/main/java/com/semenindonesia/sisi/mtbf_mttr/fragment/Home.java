package com.semenindonesia.sisi.mtbf_mttr.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.semenindonesia.sisi.mtbf_mttr.R;
import com.semenindonesia.sisi.mtbf_mttr.activities.MenuActivity;
import com.semenindonesia.sisi.mtbf_mttr.activities.NotificationActivity;
import com.semenindonesia.sisi.mtbf_mttr.database.User;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class Home extends Fragment {

    private Button logout;

    ImageView wc, his, logevent;
    Realm realm = Realm.getDefaultInstance();

    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        //logout = (Button) view.findViewById(R.id.btn_logout);
        /*logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutUser();
            }
        });*/

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        wc = (ImageView) getView().findViewById(R.id.workcenterImg);
        his = (ImageView) getView().findViewById(R.id.historyImg);
        logevent = (ImageView) getView().findViewById(R.id.logevent);

        wc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), NotificationActivity.class));
            }
        });

        /*his.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), HistoryActivity.class));
            }
        });

        logevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), LogActivity.class));
            }
        });*/

    }

    private void logoutUser() {
       /* session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(getActivity(), SmsActivity.class);
        startActivity(intent);
        getActivity().finish();*/

    }


}
