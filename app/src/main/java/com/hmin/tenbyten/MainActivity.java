package com.hmin.tenbyten;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.hmin.tenbyten.block.Block;
import com.hmin.tenbyten.block.BlockMap;
import com.hmin.tenbyten.common.Const;
import com.hmin.tenbyten.common.TenManager;

public class MainActivity extends Activity {

	TenManager mTenManager;	
	
	FrameLayout mMainLayout;	
	BlockMap mBlockMap;
	View mTitleView;
	TextView mScoreView;
	
	GestureDetector gestureDetector;
	
	ArrayList<Block> mBlockList = new ArrayList<Block>();
	
	int mCurrentBlockIndex = 0;
	int mHeight = 0;
	int mTotalScore = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		init();
		setContentView(R.layout.activity_main);
		setupView();
	}
	
	private void init(){
		mTenManager = TenManager.getInstance(getApplicationContext());
		int width = getResources().getDisplayMetrics().widthPixels;
		mTenManager.setBlockLength(width/Const.BLOCK_WEIGHT);
		gestureDetector = new GestureDetector(getApplicationContext(), gestureListener);
	}
	
	private void setupView(){
		
		mMainLayout = (FrameLayout) findViewById(R.id.mainlayout);
	
		mBlockMap = (BlockMap) findViewById(R.id.blockmap);
		
		mTitleView = findViewById(R.id.title);
		mScoreView = (TextView) findViewById(R.id.score);
		Typeface tf = Typeface.createFromAsset(getAssets(), "hanna.ttf");
		mScoreView.setTypeface(tf);
		mScoreView.setText(""+mTotalScore);
		int width = getResources().getDisplayMetrics().widthPixels;
		int space = width / Const.NUM_OF_CANDIATE_BLOCK;
		float spaceB = Const.BLOCK_WEIGHT / (float)Const.NUM_OF_CANDIATE_BLOCK;
//		mHeight = getResources().getDisplayMetrics().heightPixels;
		mBlockList.add(new Block(getApplicationContext(), -1));
		mBlockList.add(new Block(getApplicationContext(), -1));
		mBlockList.add(new Block(getApplicationContext(), -1));
		for(int i = 0 ; i < mBlockList.size() ; i++){
			Block b =mBlockList.get(i);

			mMainLayout.addView(b);
			float margin = (spaceB - b.getTotalLength())/2;
			LayoutParams lp = (LayoutParams) b.getLayoutParams();
			lp.height = lp.width = mTenManager.getBlockLength() * b.getTotalLength();
			lp.leftMargin = (int) ((space * i) + (margin * mTenManager.getBlockLength()));
//			lp.topMargin = (int) ((1 + margin) * mTenManager.getBlockLength() + getResources().getDimension(R.dimen.block_topmargin));
			lp.gravity = Gravity.BOTTOM;
			lp.bottomMargin = (int) ((1 + margin) * mTenManager.getBlockLength());
			b.setLayoutParams(lp);
			
			b.setOnTouchListener(mOnTouchListener);
		}
		mTitleView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				resetGame();
			}
		});
//		mBlock1.setOnTouchListener(mOnTouchListener);
//		
//		mMainLayout.addView(mBlock1);
//		mMainLayout.addView(mBlock2);
//		mMainLayout.addView(mBlock3);
	}
	
	private void reloadBlock(){
	}
	
	private void clearScore(){
		mTotalScore = 0;
		mScoreView.setText(""+mTotalScore);
	}
	
	private void resetGame(){
		mBlockMap.clearMap();
		clearScore();
		for(int i = 0 ; i < Const.NUM_OF_CANDIATE_BLOCK ; i++){
			createBlock(i);
		}
	}
	
	private boolean blockCheck(){
		Block b = mBlockList.get(mCurrentBlockIndex);
		int left = b.getLeft() - mBlockMap.getLeft();
		int top = b.getTop() - mBlockMap.getTop();
		if(mBlockMap.checkMap(left , top, b.getShapeMatrix(), b.getBlockColor())){
			createBlock(mCurrentBlockIndex);
			return false;
		}
		return true;
	}
	
	private void createBlock(int idx){
		mMainLayout.removeView(mBlockList.get(idx));
		mBlockList.set(idx, new Block(getApplicationContext(), -1));// = new Block(getApplicationContext(), -1);
		Block b = mBlockList.get(idx);
		b.setOnTouchListener(mOnTouchListener);
		mMainLayout.addView(b);
		
		int width = getResources().getDisplayMetrics().widthPixels;
		int space = width / Const.NUM_OF_CANDIATE_BLOCK;
		float spaceB = Const.BLOCK_WEIGHT / (float)Const.NUM_OF_CANDIATE_BLOCK;
		
		float marginX = (spaceB - b.getWidthLength())/2 - mTenManager.getBlockLength()*b.getXOffset();
		float marginY = (spaceB - b.getHeightLength())/2;
		
		Log.d("HMIN"," "+b.getTotalLength()+" "+b.getWidthLength()+" "+b.getHeightLength()+" "+b.getXOffset()+" "+b.getYOffset());
		LayoutParams lp = (LayoutParams) b.getLayoutParams();
		lp.height = lp.width = mTenManager.getBlockLength() * b.getTotalLength();
		lp.leftMargin = (int) ((space * idx) + (marginX * mTenManager.getBlockLength()));
//		lp.topMargin = (int) ((1 + margin) * mTenManager.getBlockLength() + getResources().getDimension(R.dimen.block_topmargin));
//		lp.topMargin = (int) ((1 + margin) * mTenManager.getBlockLength()) + mTitleView.getHeight() + mScoreView.getHeight();
		lp.gravity = Gravity.BOTTOM;
		lp.bottomMargin = (int) ((1 + marginY) * mTenManager.getBlockLength());
		b.setLayoutParams(lp);
		b.invalidate();
		
	}
	
	OnGestureListener gestureListener = new OnGestureListener() {
		
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			Log.d("HMIN","onSingleTapUp ");
//			mBlockList.get(mCurrentBlockIndex).rotateBlock();
//			mBlockList.get(mCurrentBlockIndex).invalidate();
			return true;
		}
		
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
				float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			return false;
		}
		
		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}
	};
	
	OnTouchListener mOnTouchListener = new OnTouchListener() {
		
		float xOffset = 0;
		float yOffset = 0;
		
		float preX = 0;
		float preY = 0;
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			LayoutParams lp;			
			if(mHeight == 0 ){
				mHeight = mMainLayout.getHeight();
			}
			gestureDetector.onTouchEvent(event);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				for(int i = 0 ; i < mBlockList.size() ; i++){
					if(mBlockList.get(i).equals(v)){
						mCurrentBlockIndex = i;
					}
				}
//				mCurrentBlock = (Block) v;
				xOffset = v.getLeft();
				yOffset = mHeight - v.getBottom();//Top();
//				yOffset = v.getTop();//mHeight - v.getBottom();//Top();
				preX = event.getX() + v.getLeft();
				preY = event.getY() + v.getTop();
				
				{
					
					lp = (LayoutParams) v.getLayoutParams();
	//				lp.topMargin += dy;
					Log.d("HMIN"," "+" "+yOffset+" "+(v.getHeight() - event.getY()));
					lp.bottomMargin += (v.getHeight()*5/4  - event.getY());//200;
					v.setLayoutParams(lp);
					v.setScaleX(1f);
					v.setScaleY(1f);
				}
				return true;
			case MotionEvent.ACTION_MOVE:
				float x = event.getX() + v.getLeft();
				float y = event.getY() + v.getTop();
				float dx = x - preX;
				float dy = y - preY;
				lp = (LayoutParams) v.getLayoutParams();
				lp.leftMargin += dx;
//				lp.topMargin += dy;
				lp.bottomMargin -= dy;
				v.setLayoutParams(lp);
				preX = x;
				preY = y;
				
				return true;
			case MotionEvent.ACTION_UP:
				if(blockCheck()){
					lp = (LayoutParams) v.getLayoutParams();
					lp.leftMargin = (int) xOffset;

//					lp.topMargin = (int) yOffset;
					lp.bottomMargin = (int) yOffset;
					v.setLayoutParams(lp);
					v.setScaleX(0.8f);
					v.setScaleY(0.8f);
				}else{
					int cnt = mBlockMap.checkLine();
					if(cnt != 0){
						int score = cnt * cnt * 10;
						mTotalScore += score;
						mScoreView.setText(""+mTotalScore);
					}
				}
				return true;
			default:
				break;
			}
			return false;
		}
	};
}
