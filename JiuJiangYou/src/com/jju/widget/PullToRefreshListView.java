package com.jju.widget;

import com.jju.ui.R;
import android.content.Context;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class PullToRefreshListView extends ListView implements OnScrollListener {

	private final static String TAG = "PullToRefreshListView";

	// ����ˢ�±�־
	private final static int PULL_To_REFRESH = 0;
	// �ɿ�ˢ�±�־
	private final static int RELEASE_To_REFRESH = 1;
	// ����ˢ�±�־
	private final static int REFRESHING = 2;
	// ˢ����ɱ�־
	private final static int DONE = 3;

	private LayoutInflater inflater;

	private LinearLayout headView;
	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;
	// �������ü�ͷͼ�궯��Ч��
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;

	// ���ڱ�֤startY��ֵ��һ��������touch�¼���ֻ����¼һ��
	private boolean isRecored;
	private int headContentWidth;
	private int headContentHeight;
	private int headContentOriginalTopPadding;
	private int startY;
	private int firstItemIndex;
	private int currentScrollState;
	private int state;
	private boolean isBack;
	public OnRefreshListener refreshListener;

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PullToRefreshListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(100);
		animation.setFillAfter(true);
		
		reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(100);
		reverseAnimation.setFillAfter(true);
		
		inflater = LayoutInflater.from(getContext());
		headView = (LinearLayout) inflater.inflate(R.layout.pull_to_refresh_head, null);
		 
		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMaxHeight(50);
		arrowImageView.setMaxWidth(50);
		
	    progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);  
	    tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);  
	    lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);  
		
	    headContentOriginalTopPadding = headView.getPaddingTop();
		//����headView �� ��
	    measureView(headView);
	    headContentHeight = headView.getMeasuredHeight();
	    headContentWidth  = headView.getMeasuredWidth();
	    headView.setPadding(headView.getPaddingLeft(), -headView.getPaddingTop(), headView.getPaddingLeft(), headView.getPaddingBottom());
	    addHeaderView(headView);
	    setOnScrollListener(this);
	}

	private void measureView(LinearLayout child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if( p == null){
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0+0, p.width);
		int lpHegith  = p.height;
		int childHeightSpec;
		if( lpHegith > 0){
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHegith, MeasureSpec.EXACTLY);
		}else{
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		currentScrollState = scrollState;
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		firstItemIndex = firstVisibleItem;
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if( firstItemIndex == 0 && !isRecored){
				startY = (int)ev.getY();
				isRecored = true;
			}
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		default:
			break;
		}
		
		
		return super.onTouchEvent(ev);
	}
	
	
	public void changeHeadViewByState(){
		switch (state) {
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);  
            tipsTextview.setVisibility(View.VISIBLE);  
            lastUpdatedTextView.setVisibility(View.VISIBLE);  
            arrowImageView.clearAnimation();  
            arrowImageView.setVisibility(View.VISIBLE);  
            if (isBack) {  
                isBack = false;  
                arrowImageView.clearAnimation();  
                arrowImageView.startAnimation(reverseAnimation);  
            } 
            tipsTextview.setText(R.string.pull_to_refresh_pull_label);  
			break;
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);  
            progressBar.setVisibility(View.GONE);  
            tipsTextview.setVisibility(View.VISIBLE);  
            lastUpdatedTextView.setVisibility(View.VISIBLE);  
            arrowImageView.clearAnimation();  
            arrowImageView.startAnimation(animation);  
            tipsTextview.setText(R.string.pull_to_refresh_release_label);
			break;
		case REFRESHING:
			//System.out.println("ˢ��REFRESHING-TopPad��"+headContentOriginalTopPadding);
        	headView.setPadding(headView.getPaddingLeft(), headContentOriginalTopPadding, headView.getPaddingRight(), headView.getPaddingBottom());   
            headView.invalidate();  
  
            progressBar.setVisibility(View.VISIBLE);  
            arrowImageView.clearAnimation();  
            arrowImageView.setVisibility(View.GONE);  
            tipsTextview.setText(R.string.pull_to_refresh_refreshing_label);  
            lastUpdatedTextView.setVisibility(View.GONE);  
			break;
		case DONE:
			//System.out.println("���DONE-TopPad��"+(-1 * headContentHeight));
        	headView.setPadding(headView.getPaddingLeft(), -1 * headContentHeight, headView.getPaddingRight(), headView.getPaddingBottom());  
            headView.invalidate();  
  
            progressBar.setVisibility(View.GONE);  
            arrowImageView.clearAnimation();  
            // �˴�����ͼ��   
            arrowImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);  
  
            tipsTextview.setText(R.string.pull_to_refresh_pull_label);  
            lastUpdatedTextView.setVisibility(View.VISIBLE);  
  
            //Log.v(TAG, "��ǰ״̬��done");  
			break;
		default:
			break;
		}
	}
	
	public void clickRefresh(){
		setSelection(0);
		onRefresh();
	}
	
	private void onRefresh(){
		if( refreshListener != null){
			refreshListener.onRefresh();
		}
	}
	
	public void setRefreshListener(OnRefreshListener reFreshListener){
		this.refreshListener = reFreshListener;
	}

	public interface OnRefreshListener {
		public void onRefresh();
	}

}
