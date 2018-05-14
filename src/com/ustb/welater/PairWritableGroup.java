package com.ustb.welater;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class PairWritableGroup extends WritableComparator{
	public PairWritableGroup(){
		super(PairWritable.class,true);
	}
	
	public int compare(WritableComparable a, WritableComparable b) {
		PairWritable o1 =(PairWritable) a;
		PairWritable o2 =(PairWritable) b;
		int r = -Integer.compare(o1.getNum(), o2.getNum());
		if(r==0){
			return o1.getWord().compareTo(o2.getWord());
		}
		return r;
	}
}
