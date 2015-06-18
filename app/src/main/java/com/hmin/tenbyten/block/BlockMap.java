package com.hmin.tenbyten.block;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.IBinder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.hmin.tenbyten.R;
import com.hmin.tenbyten.common.Const;
import com.hmin.tenbyten.common.TenManager;

public class BlockMap extends View{

	private final static int MAP_ROW = 10;
	private final static int MAP_COL = 10;
	private final static int EMPTY_COLOR = 0xFFCCCCCC;
	
	Context mContext;
	Bitmap mBlockBitmap;
	NinePatchDrawable mDrawable;
	Rect mSrcRect, mDstRect;
	
	int mMappingColor[][] = new int[MAP_ROW][MAP_COL];
	
	float mMargin;
	int mBlockLength;
	int mColor = Color.WHITE;
	
	public BlockMap(Context context){
		super(context);
		init(context);
	}
	
	public BlockMap(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public BlockMap(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	public BlockMap(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}
	
	private void init(Context c){
		mContext = c;		
		mBlockLength = TenManager.getInstance(c).getBlockLength();
		mMargin = Const.BLOCK_MARGIN;
		initializingMap();
		mBlockBitmap = BitmapFactory.decodeResource(c.getResources(), R.drawable.block);
		mDrawable = (NinePatchDrawable)c.getResources().getDrawable(R.drawable.block);
		mDrawable.setBounds(0, 0, mBlockLength, mBlockLength);
		mSrcRect = new Rect(0, 0, mBlockLength, mBlockLength);
		mDstRect = new Rect();
	}
	
	private void initializingMap(){
		for(int i = 0; i < MAP_ROW ; i++ ){
			for(int j = 0 ; j < MAP_COL ; j++){
				mMappingColor[i][j] = EMPTY_COLOR;
			}
		}
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		Paint p = new Paint();
		p.setColor(0xFFCCCCCC);
		for(int i = 0 ; i < MAP_ROW ; i++){
			for(int j = 0 ; j < MAP_COL ; j++){
//				mDstRect.top = (j+1)*mBlockLength;
//				mDstRect.left = (i+1)*mBlockLength;
//				mDstRect.bottom = (j+2)*mBlockLength;
//				mDstRect.right = (i+2)*mBlockLength;
//				canvas.drawBitmap(mBlockBitmap, mSrcRect, mDstRect, p);
				mDrawable.setColorFilter(mMappingColor[i][j], PorterDuff.Mode.MULTIPLY);
				canvas.save();
				canvas.translate((i+mMargin)*mBlockLength, (j+mMargin)*mBlockLength);
				mDrawable.draw(canvas);
				canvas.restore();
			}
		}
//		canvas.drawRect(r, p);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			break;

		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	private boolean isCollosion(int xIdx, int yIdx, int[][] matrix){

		for(int i = 0 ; i < matrix.length ; i++){
			for(int j = 0 ; j < matrix.length ; j++){
				
				if(xIdx+ i >= mMappingColor.length || yIdx + j >= mMappingColor.length){
					if(matrix[i][j] == 1) 
						return true;
					else 
						continue;
				}

				if(xIdx + i < 0 || yIdx +j < 0){
					if(matrix[i][j] == 1) {
						return true;
					}
					else 
						continue;
				}
				if(matrix[i][j] == 1 && mMappingColor[xIdx+i][yIdx+j] != EMPTY_COLOR)
					return true;
			}
		}
		return false;
	}
	
	public boolean checkMap(int x, int y, int[][] shapematrix, int color){
		int dx = (int) (x - (mBlockLength * ((( mMargin * 2) - 1)/ 2 )));
		int dy = (int) (y - (mBlockLength * ((( mMargin * 2) - 1)/ 2 )));

		

		int xIdx = dx/mBlockLength;
		int yIdx = dy/mBlockLength;

		
		if(dx < 0)
			xIdx--;
		if(dy < 0)
			yIdx--;
		Log.d("HMIN"," "+xIdx+" "+yIdx);
		if(isCollosion(xIdx, yIdx, shapematrix))
			return false;
		
		for(int i = 0 ; i < shapematrix.length ; i++){
			for(int j = 0 ; j < shapematrix.length ; j++){
				if(shapematrix[i][j] == 1)
					mMappingColor[xIdx+i][yIdx+j] = color;
			}
		}
		invalidate();
		
		return true;
	}
	
	private class ClearLine{
		private final static int ROW = 0;
		private final static int COLUMN = 1;
		
		int line;
		int index;
		
		public ClearLine(int line, int index){
			this.line = line;
			this.index = index;
		}
	}
	
	
	public int checkLine(){
		int count = 0;
		ArrayList<ClearLine> mClearList = new ArrayList<ClearLine>();
		
		
		for(int i = 0 ; i < mMappingColor.length ; i++){
			for(int j = 0 ; j < mMappingColor.length ; j++){
				if(mMappingColor[i][j] ==EMPTY_COLOR)
					break;
				if(j == mMappingColor.length -1){
					
					mClearList.add(new ClearLine(ClearLine.ROW, i));
//					count++;
//					for(int k = 0 ; k < mMappingColor.length ; k++){
//						mMappingColor[i][k] = EMPTY_COLOR;
//					}
//					invalidate();
				}
			}
		}
		for(int i = 0 ; i < mMappingColor.length ; i++){
			for(int j = 0 ; j < mMappingColor.length ; j++){
				if(mMappingColor[j][i] ==EMPTY_COLOR)
					break;
				if(j == mMappingColor.length -1){

					mClearList.add(new ClearLine(ClearLine.COLUMN, i));
//					count++;
//					for(int k = 0 ; k < mMappingColor.length ; k++){
//						mMappingColor[k][i] = EMPTY_COLOR;
//					}
//					invalidate();
				}
			}
		}
		
		for(ClearLine cLine : mClearList){
			if(cLine.line == ClearLine.COLUMN){
				Log.d("HMIN"," COLUMN "+cLine.index);
				for(int i = 0; i < mMappingColor.length ; i++){
					mMappingColor[i][cLine.index] = EMPTY_COLOR;
				}
			}else{
				Log.d("HMIN"," ROW  "+cLine.index);
				for(int i = 0; i < mMappingColor.length ; i++){
					mMappingColor[cLine.index][i] = EMPTY_COLOR;
				}
				
			}
		}
		
		return mClearList.size();
	}
	
	public void clearMap(){
		for(int i = 0 ; i < mMappingColor.length ; i++){
			for(int j = 0 ; j < mMappingColor.length ; j++){
				mMappingColor[j][i] =EMPTY_COLOR;
				invalidate();
			}
		}
	}
	
}
