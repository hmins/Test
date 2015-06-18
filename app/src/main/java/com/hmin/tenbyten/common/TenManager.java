package com.hmin.tenbyten.common;

import android.content.Context;
import android.util.Log;

public class TenManager {
	
	Context mContext;
	
	private static TenManager mTenManager;
	
	private int blockLength;
	
	public TenManager(Context c){
		mContext = c; 
	}
	
	public static TenManager getInstance(Context c){
		if(mTenManager == null){
			mTenManager = new TenManager(c);
		}
		return mTenManager;
	}

	public int getBlockLength() {
		return blockLength;
	}

	public void setBlockLength(int blockLength) {
		this.blockLength = blockLength;
	}
	
	
}
