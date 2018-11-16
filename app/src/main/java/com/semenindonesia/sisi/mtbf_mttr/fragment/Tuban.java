package com.semenindonesia.sisi.mtbf_mttr.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.semenindonesia.sisi.mtbf_mttr.R;
import com.semenindonesia.sisi.mtbf_mttr.adapter.NotificationAdapter;
import com.semenindonesia.sisi.mtbf_mttr.config.ItemListDivider;
import com.semenindonesia.sisi.mtbf_mttr.constructor.consEvent;
import com.semenindonesia.sisi.mtbf_mttr.database.User;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tuban extends Fragment {
    // Store instance variables
    private String title;
    private int page;
    private NotificationAdapter mAdapter;
    private List<consEvent> notif;
    RecyclerView tuban_list;
    SwipeRefreshLayout tuban;
    Realm realm = Realm.getDefaultInstance();

    public Tuban() {
        // Required empty public constructor
    }

    // newInstance constructor for creating fragment with arguments
    public static Tuban newInstance(int page, String title) {
        Tuban fragmentFirst = new Tuban();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page", 0);
        title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tuban, container, false);
        tuban = (SwipeRefreshLayout) view.findViewById(R.id.tubanan);
        tuban_list = (RecyclerView) view.findViewById(R.id.recycler_view_tubanan);

        // SQLite database handler
        RealmResults<User> results = realm.where(User.class).findAll();
       /* mAdapter = new NotificationAdapter(notif);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        tuban_list.setLayoutManager(mLayoutManager);
        tuban_list.setItemAnimator(new DefaultItemAnimator());
        tuban_list.addItemDecoration(new ItemListDivider(getActivity(), LinearLayoutManager.VERTICAL));
        tuban_list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        tuban.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareNotificationGresik();
            }
        });*/

        return view;
    }

    private void prepareNotificationGresik() {
        tuban.setRefreshing(true);
        notif.clear();

        mAdapter = new NotificationAdapter(notif);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        tuban_list.setLayoutManager(mLayoutManager);
        tuban_list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tuban.setRefreshing(false);
    }


}
