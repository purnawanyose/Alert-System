package com.semenindonesia.sisi.mtbf_mttr.activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.robertlevonyan.views.chip.Chip;
import com.semenindonesia.sisi.mtbf_mttr.R;
import com.semenindonesia.sisi.mtbf_mttr.adapter.NotificationAdapter;
import com.semenindonesia.sisi.mtbf_mttr.constructor.consEvent;
import com.semenindonesia.sisi.mtbf_mttr.fragment.Tonasa;
import com.semenindonesia.sisi.mtbf_mttr.fragment.Tuban;

import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    final Context context = this;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private ImageView sg,pdg,tn,tlc;
    private Chip chip1,chip2,chip3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        viewPager = (ViewPager) findViewById(R.id.main_viewpager);
        sg = (ImageView) findViewById(R.id.gresik);
        pdg = (ImageView) findViewById(R.id.padang);
        tn = (ImageView) findViewById(R.id.tonasa);
        tlc = (ImageView) findViewById(R.id.tanlong);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("MSO Enhancement");

        chip1 = (Chip) findViewById(R.id.chip);
        chip2 = (Chip) findViewById(R.id.chip2);
        chip3 = (Chip) findViewById(R.id.chip3);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        pdg.setImageResource(R.drawable.pdggray);
                        tn.setImageResource(R.drawable.tngray);
                        tlc.setImageResource(R.drawable.tlgray);
                        sg.setImageResource(R.drawable.sg);
                        break;
                    case 1 :
                        pdg.setImageResource(R.drawable.pdg);
                        tn.setImageResource(R.drawable.tngray);
                        tlc.setImageResource(R.drawable.tlgray);
                        sg.setImageResource(R.drawable.sggray);
                        break;
                    case 2 :
                        pdg.setImageResource(R.drawable.pdggray);
                        tn.setImageResource(R.drawable.tn);
                        tlc.setImageResource(R.drawable.tlgray);
                        sg.setImageResource(R.drawable.sggray);
                        break;
                    case 3 :
                        pdg.setImageResource(R.drawable.pdggray);
                        tn.setImageResource(R.drawable.tngray);
                        tlc.setImageResource(R.drawable.tl);
                        sg.setImageResource(R.drawable.sggray);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        getMenuInflater().inflate(R.menu.activity_main_actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //back button actionbar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            /*case R.id.action_search:
                showStandardDialog();
                return true;*/
        }

        return super.onOptionsItemSelected(item);
    }

    //viewpager adapter
    class ViewPagerAdapter extends FragmentPagerAdapter {

        @Override
        public int getCount() {
            return 4;
        }

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return Tuban.newInstance(0, "Page # 1");
                    //return Tonasa.newInstance(0, "Page # 3");
                    //return null;

                case 1 :
                    //return Padang.newInstance(1, "Page # 2");
                    //return null;
                    //return Tuban.newInstance(1, "Page # 2");
                    return Tonasa.newInstance(1, "Page # 3");

                case 2 :
                    return Tonasa.newInstance(2, "Page # 3");
                    //return null;
                    //return Tuban.newInstance(2, "Page # 3");

                case 3 :
                    //return Thanglong.newInstance(3, "Page # 4");
                    //return null;
                    //return Tuban.newInstance(3, "Page # 4");
                    return Tonasa.newInstance(3, "Page # 3");

                default:
                    //return Tubanan.newInstance(0, "Page # 1");
                    //return null;
                    //return Tuban.newInstance(0, "Page # 1");
                    return Tonasa.newInstance(0, "Page # 3");
            }
        }
    }


}
