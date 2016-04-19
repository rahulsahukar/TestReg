package registration.rahul.com.registration;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private ScrollView sv;
    private ViewPager viewPager;
    private NestedScrollView nestedScrollView;
    private AppBarLayout appBarLayout;
    private boolean appBarLayoutExpanded = true;
    private boolean isKeyboardDown = true;
    private LinearLayout paddingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        appBarLayout = (AppBarLayout) findViewById(R.id.appBar);
        nestedScrollView = (NestedScrollView) findViewById(R.id.scrollLayout);
        paddingLayout = (LinearLayout)findViewById(R.id.paddinglayout);

        appBarLayout.addOnOffsetChangedListener(this);

        final View activityRootView = findViewById(R.id.root);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int height = appBarLayout.getHeight();
                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
                if (heightDiff > 100) {
                    appBarLayout.setExpanded(true);
                }
                activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                ViewGroup.LayoutParams params = appBarLayout.getLayoutParams();
                params.height = (int)(activityRootView.getHeight()*.3);
                nestedScrollView.setMinimumHeight(activityRootView.getHeight() - height);
                appBarLayout.setLayoutParams(params);

                ViewGroup.LayoutParams paddingLayoutParams = nestedScrollView.getLayoutParams();
                paddingLayoutParams.height = (int)(activityRootView.getRootView().getHeight()*1.3);
                nestedScrollView.setLayoutParams(paddingLayoutParams);
                Log.i("Layout Height", ""+paddingLayoutParams.height);

            }
        });

/*
        nestedScrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.i("activity Height", "" + activityRootView.getHeight());
                nestedScrollView.bringToFront();
                //activityRootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
*/

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentOne(), "ONE");
        adapter.addFragment(new FragmentOne(), "TWO");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            appBarLayoutExpanded = true;
        } else {
            appBarLayoutExpanded = false;
        }
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            if (appBarLayoutExpanded == false) {
//                appBarLayout.setExpanded(true, true);
//            }
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    @Override
//    public void onBackPressed() {
//        if (appBarLayoutExpanded == false) {
//            appBarLayout.setExpanded(true, true);
//        }
//        return;
//    }
}