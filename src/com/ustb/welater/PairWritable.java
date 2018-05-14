package com.ustb.welater;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

/**
 * 作为第二个map的输出key用来排序
 * @author 26062
 *
 */
public class PairWritable implements WritableComparable<PairWritable>{
	
	private String word;
	private int num;
	
	@Override
	public void readFields(DataInput in) throws IOException {
		this.word=in.readUTF();
		this.num=in.readInt();
	}
	
	@Override
	public void write(DataOutput out) throws IOException {
		out.writeUTF(word);
		out.writeInt(num);
	}
	
	@Override
	public int compareTo(PairWritable o) {
		int r =this.word.compareTo(o.getWord());
		if(r==0){
			return Integer.compare(this.num, o.getNum());
		}
		return r;
	}

	
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

}
