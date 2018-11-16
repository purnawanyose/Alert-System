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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Tonasa extends Fragment {

    private String title;
    private int page;
    private NotificationAdapter mAdapter;
    private List<consEvent> notif;
    RecyclerView tonasa_list;
    SwipeRefreshLayout tonasa;

    public Tonasa() {
        // Required empty public constructor
    }

    // newInstance constructor for creating fragment with arguments
    public static Tonasa newInstance(int page, String title) {
        Tonasa fragmentThird= new Tonasa();
        Bundle args = new Bundle();
        args.putInt("page", page);
        args.putString("title", title);
        fragmentThird.setArguments(args);
        return fragmentThird;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("page", 2);
        title = getArguments().getString("title");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tonasa, container, false);
        tonasa = (SwipeRefreshLayout) view.findViewById(R.id.tonasa);
        tonasa_list = (RecyclerView) view.findViewById(R.id.recycler_view_tonasa);

        // SQLite database handler

        /*mAdapter = new NotificationAdapter(notif);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        tonasa_list.setLayoutManager(mLayoutManager);
        tonasa_list.setItemAnimator(new DefaultItemAnimator());
        tonasa_list.addItemDecoration(new ItemListDivider(getActivity(), LinearLayoutManager.VERTICAL));
        tonasa_list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        tonasa.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                prepareNotificationTonasa();
            }
        });*/

        return view;
    }

    private void prepareNotificationTonasa() {
        tonasa.setRefreshing(true);
        notif.clear();
        //notif = db.getOffTonasa();
        mAdapter = new NotificationAdapter(notif);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        tonasa_list.setLayoutManager(mLayoutManager);
        tonasa_list.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        tonasa.setRefreshing(false);
    }

}
