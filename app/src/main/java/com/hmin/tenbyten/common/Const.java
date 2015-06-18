package com.hmin.tenbyten.common;

public class Const {
	
	public static final int NUM_OF_CANDIATE_BLOCK = 3;
	public static final int BLOCK_WEIGHT = 14;
	public static final float BLOCK_MARGIN = (BLOCK_WEIGHT - 10) / (float) 2; 
	
	public static final int I_SHAPE = 0;
	public static final int J_SHAPE = 1;
	public static final int L_SHAPE = 2;
	public static final int O_SHAPE = 3;
	public static final int Z_SHAPE = 4;
	public static final int T_SHAPE = 5;
	public static final int S_SHAPE = 6; 
	
	public static final int BLOCK_SHAPE_ARRAY[] = {I_SHAPE, J_SHAPE, L_SHAPE, O_SHAPE, Z_SHAPE, T_SHAPE, S_SHAPE};
	
	public static final int I_SHAPE_MATRIX[][] = {{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}};
	public static final int J_SHAPE_MATRIX[][] = {{1, 0 , 0}, {1, 1, 1}, {0, 0, 0}};
	public static final int L_SHAPE_MATRIX[][] = {{0, 0 , 1}, {1, 1, 1}, {0, 0, 0}};
	public static final int O_SHAPE_MATRIX[][] = {{1, 1}, {1, 1}};
	public static final int Z_SHAPE_MATRIX[][] = {{1, 1, 0}, {0, 1, 1}, {0, 0, 0}};	
	public static final int T_SHAPE_MATRIX[][] = {{0, 1, 0}, {1, 1, 1}, {0, 0, 0}};
	public static final int S_SHAPE_MATRIX[][] = {{0, 1, 1}, {1, 1, 0}, {0, 0, 0}};
	
	public static final int I_SHAPE_COLOR = 0xFF99F0F0;
	public static final int J_SHAPE_COLOR = 0xFF9999F0;
	public static final int L_SHAPE_COLOR = 0xFFFFC099;
	public static final int O_SHAPE_COLOR = 0xFFF0F099;
	public static final int Z_SHAPE_COLOR = 0xFFF09999;
	public static final int T_SHAPE_COLOR = 0xFFC099FF;
	public static final int S_SHAPE_COLOR = 0xFF99F099;
}
