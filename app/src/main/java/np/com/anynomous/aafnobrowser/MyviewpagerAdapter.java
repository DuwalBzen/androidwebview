package np.com.anynomous.aafnobrowser;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyviewpagerAdapter extends FragmentPagerAdapter {
    public MyviewpagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0: return new GoogleFragment();
            case 1: return new Presearch_Fragment();
            case 2: return new Duckduckgo_Fragment();
            case 3: return new Piratebay_Fragment();
            case 4: return new ZenBlogFragment();
            default:
                return new GoogleFragment();
        }
    }

    @Override
    public int getCount() {
        return 5;
    }
}
