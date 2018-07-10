package com.yongyida.robot.hardware.test.item.motion;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yongyida.robot.communicate.app.utils.LogHelper;
import com.yongyida.robot.hardware.test.R;
import com.yongyida.robot.hardware.test.data.ModelInfo;
import com.yongyida.robot.hardware.test.item.TestBaseActivity;
import com.yongyida.robot.hardware.test.item.motion.fragment.BaseFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestAllMotionFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestChangeArmIdFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestFootFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestGroupFrameFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestHandFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestHeadFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestMotionSystemFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestSoundLocationFragment;
import com.yongyida.robot.hardware.test.item.motion.fragment.TestUltrasonicFragment;
import com.yongyida.robot.hardware.test.view.HorizontalListView;

import java.util.ArrayList;



/* 
                              _ooOoo_ 
                             o8888888o 
                             88" . "88 
                             (| -_- |) 
                             O\  =  /O 
                          ____/`---'\____ 
                        .'  \\|     |//  `. 
                       /  \\|||  :  |||//  \ 
                      /  _||||| -:- |||||-  \ 
                      |   | \\\  -  /// |   | 
                      | \_|  ''\---/''  |   | 
                      \  .-\__  `-`  ___/-. / 
                    ___`. .'  /--.--\  `. . __ 
                 ."" '<  `.___\_<|>_/___.'  >'"". 
                | | :  `- \`.;`\ _ /`;.`/ - ` : | | 
                \  \ `-.   \_ __\ /__ _/   .-` /  / 
           ======`-.____`-.___\_____/___.-`____.-'====== 
                              `=---=' 
           .............................................  
                    佛祖镇楼                  BUG辟易  
            佛曰:  
                    写字楼里写字间，写字间里程序员；  
                    程序人员写程序，又拿程序换酒钱。  
                    酒醒只在网上坐，酒醉还来网下眠；  
                    酒醉酒醒日复日，网上网下年复年。  
                    但愿老死电脑间，不愿鞠躬老板前；  
                    奔驰宝马贵者趣，公交自行程序员。  
                    别人笑我忒疯癫，我笑自己命太贱；  
                    不见满街漂亮妹，哪个归得程序员？ 
*/

/**
 * Create By HuangXiangXiang 2018/6/12
 */
public class TestMotionActivity extends TestBaseActivity implements AdapterView.OnItemClickListener, ViewPager.OnPageChangeListener {

    private static final String TAG = TestMotionActivity.class.getSimpleName() ;

    private HorizontalListView mFragmentNameHlv;
    private ViewPager mFragmentVpr;

    private ArrayList<BaseFragment> baseFragments = new ArrayList() ;

    /**初始控制*/
    private void initFragment(){

        baseFragments.add(new TestHeadFragment()) ;
        baseFragments.add(new TestHandFragment()) ;
        baseFragments.add(new TestFootFragment()) ;
        baseFragments.add(new TestChangeArmIdFragment()) ;
        baseFragments.add(new TestSoundLocationFragment()) ;
        baseFragments.add(new TestGroupFrameFragment()) ;
        baseFragments.add(new TestUltrasonicFragment()) ;
        baseFragments.add(new TestMotionSystemFragment()) ;
    }

    @Override
    protected View initContentView() {

        initFragment() ;
        View view = mLayoutInflater.inflate(R.layout.activity_test_motion, null);
        initView(view) ;
        return view;
    }

    private void initView( View view) {

        mFragmentNameHlv = (HorizontalListView) view.findViewById(R.id.fragment_name_hlv);
        mFragmentVpr = (ViewPager) view.findViewById(R.id.fragment_vpr);


        mFragmentNameHlv.setAdapter(mFragmentNameAdapter);
        mFragmentVpr.setAdapter(mFragmentAdapter);

        mFragmentNameHlv.setOnItemClickListener(this);
        mFragmentVpr.setOnPageChangeListener(this);
    }

    @Override
    protected void onTouchTitleLeft() {

        AllSteeringControlActivity.statrtActivity(this);

    }

    private int mSelectIndex = 0 ;
    private BaseAdapter mFragmentNameAdapter = new BaseAdapter() {
        @Override
        public int getCount() {

            return baseFragments.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextView textView  ;
            if(convertView == null){

                convertView = mLayoutInflater.inflate(R.layout.item_text, null) ;
                textView = convertView.findViewById(R.id.text_tvw) ;

                convertView.setTag(textView);

            }else {

                textView = (TextView) convertView.getTag();
            }


            if(position == mSelectIndex){

                textView.setBackgroundResource(R.drawable.item_select_bg);

            }else {

                textView.setBackgroundResource(R.drawable.item_bg);
            }

            textView.setText(baseFragments.get(position).getName());

            return convertView;
        }
    };

    private PagerAdapter mFragmentAdapter = new FragmentPagerAdapter(getFragmentManager()) {

        @Override
        public int getCount() {

            return baseFragments.size();
        }

        @Override
        public Fragment getItem(int position) {

            return baseFragments.get(position);
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        this.mSelectIndex = position ;
        mFragmentNameAdapter.notifyDataSetChanged();

        mFragmentVpr.setCurrentItem(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        this.mSelectIndex = position ;
        mFragmentNameAdapter.notifyDataSetChanged();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public abstract class FragmentPagerAdapter extends PagerAdapter {
        private static final boolean DEBUG = true;

        private final FragmentManager mFragmentManager;
        private FragmentTransaction mCurTransaction = null;
        private Fragment mCurrentPrimaryItem = null;

        public FragmentPagerAdapter(FragmentManager fm) {
            mFragmentManager = fm;
        }

        /**
         * Return the Fragment associated with a specified position.
         */
        public abstract Fragment getItem(int position);

        @Override
        public void startUpdate(ViewGroup container) {
            if (container.getId() == View.NO_ID) {
                throw new IllegalStateException("ViewPager with adapter " + this
                        + " requires a view id");
            }
        }

        @SuppressWarnings("ReferenceEquality")
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }

            final long itemId = getItemId(position);

            // Do we already have this fragment?
            String name = makeFragmentName(container.getId(), itemId);
            Fragment fragment = mFragmentManager.findFragmentByTag(name);
            if (fragment != null) {
                if (DEBUG) LogHelper.v(TAG, "Attaching item #" + itemId + ": f=" + fragment);
                mCurTransaction.attach(fragment);
            } else {
                fragment = getItem(position);
                if (DEBUG) LogHelper.v(TAG, "Adding item #" + itemId + ": f=" + fragment);
                mCurTransaction.add(container.getId(), fragment, name);
            }
            if (fragment != mCurrentPrimaryItem) {
                fragment.setMenuVisibility(false);
                fragment.setUserVisibleHint(false);
            }

            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            if (mCurTransaction == null) {
                mCurTransaction = mFragmentManager.beginTransaction();
            }
            if (DEBUG) LogHelper.v(TAG, "Detaching item #" + getItemId(position) + ": f=" + object + " v=" + ((Fragment)object).getView());
            mCurTransaction.detach((Fragment)object);
        }

        @SuppressWarnings("ReferenceEquality")
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            Fragment fragment = (Fragment)object;
            if (fragment != mCurrentPrimaryItem) {
                if (mCurrentPrimaryItem != null) {
                    mCurrentPrimaryItem.setMenuVisibility(false);
                    mCurrentPrimaryItem.setUserVisibleHint(false);
                }
                if (fragment != null) {
                    fragment.setMenuVisibility(true);
                    fragment.setUserVisibleHint(true);
                }
                mCurrentPrimaryItem = fragment;
            }
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            if (mCurTransaction != null) {
                mCurTransaction.commitAllowingStateLoss();
                mCurTransaction = null;
            }
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return ((Fragment)object).getView() == view;
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        /**
         * Return a unique identifier for the item at the given position.
         *
         * <p>The default implementation returns the given position.
         * Subclasses should override this method if the positions of items can change.</p>
         *
         * @param position Position within this adapter
         * @return Unique identifier for the item at position
         */
        public long getItemId(int position) {
            return position;
        }

        private String makeFragmentName(int viewId, long id) {
            return "android:switcher:" + viewId + ":" + id;
        }
    }

}
