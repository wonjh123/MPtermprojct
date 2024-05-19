package kr.ac.gachon.recommendate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import kr.ac.gachon.recommendate.databinding.ActivityNaviBinding;


public class NaviActivity extends AppCompatActivity {

    private static final String TAG_CALENDER = "CalenderFragment";
    private static final String TAG_HOME = "HomeFragment";
    private static final String TAG_MY = "MyFragment";

    private ActivityNaviBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNaviBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setFragment(TAG_HOME, new HomeFragment());
        binding.navigationView.setSelectedItemId(R.id.homeFragment);

        binding.navigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.calenderFragment) {
                setFragment(TAG_CALENDER, new CalenderFragment());
            } else if (itemId == R.id.homeFragment) {
                setFragment(TAG_HOME, new HomeFragment());
            } else if (itemId == R.id.myPageFragment) {
                setFragment(TAG_MY, new MyPageFragment());
            }
            return true;
        });
    }

    protected void setFragment(String tag, Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (fragmentManager.findFragmentByTag(tag) == null) {
            fragmentTransaction.add(R.id.mainFrameLayout, fragment, tag);
        }

        Fragment calender = fragmentManager.findFragmentByTag(TAG_CALENDER);
        Fragment home = fragmentManager.findFragmentByTag(TAG_HOME);
        Fragment my = fragmentManager.findFragmentByTag(TAG_MY);

        if (calender != null) {
            fragmentTransaction.hide(calender);
        }

        if (home != null) {
            fragmentTransaction.hide(home);
        }

        if (my != null) {
            fragmentTransaction.hide(my);
        }

        if (tag.equals(TAG_CALENDER)) {
            if (calender != null) {
                fragmentTransaction.show(calender);
            }
        } else if (tag.equals(TAG_HOME)) {
            if (home != null) {
                fragmentTransaction.show(home);
            }
        } else {
            if (my != null) {
                fragmentTransaction.show(my);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
}
