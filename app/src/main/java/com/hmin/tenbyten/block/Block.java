package com.hmin.tenbyten.block;

import java.util.Arrays;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

import com.hmin.tenbyten.R;
import com.hmin.tenbyten.common.Const;
import com.hmin.tenbyten.common.TenManager;

public class Block extends View{
	
	NinePatchDrawable mDrawable;

	private int shape = Const.I_SHAPE;
	private int color = 0xFFFFFFFF;
	private int shape_matrix[][] = Const.I_SHAPE_MATRIX; 
	private int mBlockLength;	
	
	public Block(Context context) {
		super(context);
	}
	
	public Block(Context context, int shape) { 
		this(context);
		if(shape == -1){
			 shape = Const.BLOCK_SHAPE_ARRAY[(int) (Math.random()*Const.BLOCK_SHAPE_ARRAY.length)];
//			 shape = Const.BLOCK_SHAPE_ARRAY[3];
		}
		setScaleX(0.8f);
		setScaleY(0.8f);
		this.shape = shape;
		init(context);
	}
	
	public Block(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}
	
	public Block(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}
	
	public Block(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(context);
	}
	
	private void init(Context c){
		mBlockLength = TenManager.getInstance(c).getBlockLength();
		mDrawable = (NinePatchDrawable)c.getResources().getDrawable(R.drawable.block);
		mDrawable.setBounds(0, 0, mBlockLength, mBlockLength);
		
		switch (shape) {
		case Const.I_SHAPE:
			color = Const.I_SHAPE_COLOR;
			shape_matrix =  deepCopy(Const.I_SHAPE_MATRIX);
			break;
		case Const.J_SHAPE:
			color = Const.J_SHAPE_COLOR;
			shape_matrix = deepCopy(Const.J_SHAPE_MATRIX);
			break;
		case Const.L_SHAPE:
			color = Const.L_SHAPE_COLOR;
			shape_matrix = deepCopy(Const.L_SHAPE_MATRIX);
			break;
		case Const.O_SHAPE:
			color = Const.O_SHAPE_COLOR;
			shape_matrix = deepCopy(Const.O_SHAPE_MATRIX);
			break;
		case Const.S_SHAPE:
			color = Const.S_SHAPE_COLOR;
			shape_matrix = deepCopy(Const.S_SHAPE_MATRIX);
			break;
		case Const.T_SHAPE:
			color = Const.T_SHAPE_COLOR;
			shape_matrix = deepCopy(Const.T_SHAPE_MATRIX);
			break;
		case Const.Z_SHAPE:
			color = Const.Z_SHAPE_COLOR;
			shape_matrix = deepCopy(Const.Z_SHAPE_MATRIX);
			break;
		}
		int rand = (int)(Math.random()*4);
		for(int i = 0 ; i < rand ; i++)
			rotateBlock();
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		LayoutParams lp;
		switch (shape) {
		case Const.I_SHAPE:
			lp = (LayoutParams) getLayoutParams();
			lp.width  = mBlockLength * 4;
			lp.height = mBlockLength * 4;
			setLayoutParams(lp);
			break;
		case Const.J_SHAPE:
			lp = (LayoutParams) getLayoutParams();
			lp.width  = mBlockLength * 3;
			lp.height = mBlockLength * 3;
			setLayoutParams(lp);
			break;
		case Const.L_SHAPE:
			lp = (LayoutParams) getLayoutParams();
			lp.width  = mBlockLength * 3;
			lp.height = mBlockLength * 3;
			setLayoutParams(lp);
			break;
		case Const.O_SHAPE:
			lp = (LayoutParams) getLayoutParams();
			lp.width  = mBlockLength * 2;
			lp.height = mBlockLength * 2;
			setLayoutParams(lp);
			break;
		case Const.S_SHAPE:
			lp = (LayoutParams) getLayoutParams();
			lp.width  = mBlockLength * 3;
			lp.height = mBlockLength * 3;
			setLayoutParams(lp);
			break;
		case Const.T_SHAPE:
			lp = (LayoutParams) getLayoutParams();
			lp.width  = mBlockLength * 3;
			lp.height = mBlockLength * 3;
			setLayoutParams(lp);
			break;
		case Const.Z_SHAPE:
			lp = (LayoutParams) getLayoutParams();
			lp.width  = mBlockLength * 3;
			lp.height = mBlockLength * 3;
			setLayoutParams(lp);
			break;
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		Paint p = new Paint();
		for(int i = 0 ; i < shape_matrix.length ; i++){				
			for(int j = 0 ; j < shape_matrix.length ; j++){
				mDrawable.setColorFilter(color, PorterDuff.Mode.MULTIPLY);
				if(shape_matrix[i][j] == 1){
					canvas.save();
					canvas.translate(i*mBlockLength, j*mBlockLength);
					mDrawable.draw(canvas);
					canvas.restore();	
				}
			}
		}
	}
	
	public void rotateBlock(){
		int tempmaxtix[][] = deepCopy(shape_matrix);
		for(int i = 0 ; i < shape_matrix.length ; i++){
			int arr[]  = shape_matrix[i];
			for(int j = 0 ; j < arr.length ; j++){
				shape_matrix[i][j] = tempmaxtix[arr.length - ( j + 1)][i];
			}
		}
	}

	private static int[][] deepCopy(int[][] original) {
	    if (original == null) {
	        return null;
	    }

	    final int[][] result = new int[original.length][];
	    for (int i = 0; i < original.length; i++) {
	        result[i] = Arrays.copyOf(original[i], original[i].length);
	    }
	    return result;
	}
	
	public int getShape() {
		return shape;
	}

	public void setShape(int shape) {
		this.shape = shape;
	}

	public int getBlockColor() {
		return color;
	}

	public void setBlockColor(int color) {
		this.color = color;
	}

	public int[][] getShapeMatrix() {
		return shape_matrix;
	}

	public void setShapeMatrix(int[][] shape_matrix) {
		this.shape_matrix = shape_matrix;
	}
	
	public int getTotalLength(){
		return shape_matrix.length;
//		switch (shape) {
//		case Const.I_SHAPE:
//			return 4;
//		case Const.O_SHAPE:
//			return 2;
//		case Const.J_SHAPE:
//		case Const.L_SHAPE:
//		case Const.S_SHAPE:
//		case Const.T_SHAPE:
//		case Const.Z_SHAPE:
//			return 3;
//		default:
//			return 3;
//		}
	}
	
	public int getHeightLength(){
		int cnt = 0;
		for(int i = 0 ; i < shape_matrix.length ; i++){
			for(int j = 0 ; j < shape_matrix.length ; j++){
				if(shape_matrix[j][i] == 1){
					cnt++;
					break;
				}
			}	
		}
		return cnt;
	}
	
	public int getWidthLength(){
		int cnt = 0;
		for(int i = 0 ; i < shape_matrix.length ; i++){
			for(int j = 0 ; j < shape_matrix.length ; j++){
				if(shape_matrix[i][j] == 1){
					cnt++;
					break;
				}
			}	
		}
		return cnt;
	}
	
	
	public int getXOffset(){
		for(int i = 0 ; i < shape_matrix.length ; i++){
			for(int j = 0 ; j < shape_matrix.length ; j++){
				if(shape_matrix[i][j] == 1){
					return i;
				}
			}	
		}
		return -1;
	}
	
	public int getYOffset(){
		for(int i = 0 ; i < shape_matrix.length ; i++){
			for(int j = 0 ; j < shape_matrix.length ; j++){
				if(shape_matrix[j][i] == 1){
					return i;
				}
			}	
		}
		return -1;
	}
	
}
