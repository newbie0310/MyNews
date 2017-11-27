package com.example.cwidgetutils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class PullToRefreshListView extends ListView implements OnScrollListener, OnClickListener {
	      
	private static final int PULL_TO_REFRESH = 1;         
	private static final int RELEASE_TO_REFRESH = 2;         
	private static final int REFRESHING = 3;             

	private static final String TAG = "PullRefreshListView";

	private OnRefreshListener mOnRefreshListener;

	      
	private OnScrollListener mOnScrollListener;

	      
	private LinearLayout mRefreshView;
	private ImageView mRefreshViewImage;
	private ProgressBar mRefreshViewProgress;
	private TextView mRefreshViewText;
	private TextView mRefreshViewLastUpdated;


	private int mRefreshState;
	private int mCurrentScrollState;

	private RotateAnimation mFlipAnimation;
	private RotateAnimation mReverseFlipAnimation;

	private int mRefreshViewHeight;
	private int mRefreshOriginalTopPadding;
	private int mLastMotionY;

	public PullToRefreshListView(Context context) {
		super(context);
		init(context);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mFlipAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		mFlipAnimation.setDuration(250);
		mFlipAnimation.setFillAfter(true);
		mReverseFlipAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(250);
		mReverseFlipAnimation.setFillAfter(true);

		mRefreshView = (LinearLayout) View.inflate(context, R.layout.pull_to_refresh_header, null);
		mRefreshViewText = (TextView) mRefreshView.findViewById(R.id.pull_to_refresh_text);
		mRefreshViewImage = (ImageView) mRefreshView.findViewById(R.id.pull_to_refresh_image);
		mRefreshViewProgress = (ProgressBar) mRefreshView.findViewById(R.id.pull_to_refresh_progress);
		mRefreshViewLastUpdated = (TextView) mRefreshView.findViewById(R.id.pull_to_refresh_updated_at);

		mRefreshState = PULL_TO_REFRESH;
		mRefreshViewImage.setMinimumHeight(50);       
		
		setFadingEdgeLength(0);
		setHeaderDividersEnabled(false);

		      
		addHeaderView(mRefreshView);
		super.setOnScrollListener(this);
		mRefreshView.setOnClickListener(this);

		mRefreshView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
		mRefreshViewHeight = mRefreshView.getMeasuredHeight();
		mRefreshOriginalTopPadding = -mRefreshViewHeight;
		
		resetHeaderPadding();
	}

	      
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		mOnScrollListener = l;
	}

	      
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		mOnRefreshListener = onRefreshListener;
	}

	       
    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            mRefreshViewLastUpdated.setVisibility(View.VISIBLE);
            mRefreshViewLastUpdated.setText(lastUpdated);
        } else {
            mRefreshViewLastUpdated.setVisibility(View.GONE);
        }
    }

	      
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		final int y = (int) event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int offsetY = (int) event.getY();
			int deltY = Math.round(offsetY - mLastMotionY);
			mLastMotionY = offsetY;

			if (getFirstVisiblePosition() == 0 && mRefreshState != REFRESHING) {
				deltY = deltY / 2;
				mRefreshOriginalTopPadding += deltY;
				if (mRefreshOriginalTopPadding < -mRefreshViewHeight) {
					mRefreshOriginalTopPadding = -mRefreshViewHeight;
				}
				resetHeaderPadding();
			}
			break;
		case MotionEvent.ACTION_UP:
			      
			if (!isVerticalScrollBarEnabled()) {
				setVerticalScrollBarEnabled(true);
			}
			if (getFirstVisiblePosition() == 0 && mRefreshState != REFRESHING) {
				if (mRefreshView.getBottom() >= mRefreshViewHeight 
						&& mRefreshState == RELEASE_TO_REFRESH) {
					      
					prepareForRefresh();
				} else {
					      
                    resetHeader();
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL && mRefreshState != REFRESHING) {
			 if (firstVisibleItem == 0) {
                if ((mRefreshView.getBottom() >= mRefreshViewHeight)
                        && mRefreshState != RELEASE_TO_REFRESH) {
                    mRefreshViewText.setText(R.string.pull_to_refresh_release_label_it);
                    mRefreshViewImage.clearAnimation();
                    mRefreshViewImage.startAnimation(mFlipAnimation);
                    mRefreshState = RELEASE_TO_REFRESH;
                } else if (mRefreshView.getBottom() < mRefreshViewHeight
                        && mRefreshState != PULL_TO_REFRESH) {
                    mRefreshViewText.setText(R.string.pull_to_refresh_pull_label_it);
                    mRefreshViewImage.clearAnimation();
                    mRefreshViewImage.startAnimation(mReverseFlipAnimation);
                    mRefreshState = PULL_TO_REFRESH;
                }
            }
		}
		
		if (mOnScrollListener != null) {
			mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;
		
		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	      
	private void resetHeaderPadding() {
		mRefreshView.setPadding(
                mRefreshView.getPaddingLeft(),
                mRefreshOriginalTopPadding,
                mRefreshView.getPaddingRight(),
                mRefreshView.getPaddingBottom());
	}

	public void prepareForRefresh() {
		if (mRefreshState != REFRESHING) {
			mRefreshState = REFRESHING;
			
			mRefreshOriginalTopPadding = 0;
			resetHeaderPadding();
			
			mRefreshViewImage.clearAnimation();
			mRefreshViewImage.setVisibility(View.GONE);
			mRefreshViewProgress.setVisibility(View.VISIBLE);
			mRefreshViewText.setText(R.string.pull_to_refresh_refreshing_label_it);
			
			onRefresh();
		}
	}

	private void resetHeader() {
		mRefreshState = PULL_TO_REFRESH;
		
		mRefreshOriginalTopPadding = -mRefreshViewHeight;
		resetHeaderPadding();
		
		mRefreshViewImage.clearAnimation();
		mRefreshViewImage.setVisibility(View.VISIBLE);
		mRefreshViewProgress.setVisibility(View.GONE);
		mRefreshViewText.setText(R.string.pull_to_refresh_pull_label_it);
	}

	      
	public void onRefresh() {
		Log.d(TAG, "onRefresh");
		if (mOnRefreshListener != null) {
			mOnRefreshListener.onRefresh();
		}
	}
	
	      
    public void onRefreshComplete() {        
        Log.d(TAG, "onRefreshComplete");

        resetHeader();
    }
	
    @Override
	public void onClick(View v) {
    	Log.d(TAG, "onClick");
	}
    
	      
	public interface OnRefreshListener {
		      
		public void onRefresh();
	}
}
