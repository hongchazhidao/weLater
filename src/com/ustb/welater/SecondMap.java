package com.ustb.welater;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SecondMap extends Mapper<LongWritable, Text, PairWritable, Text>{
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		String[] tokens = value.toString().trim().split("\t");
		String word = tokens[0];
		String num = tokens[1];
		PairWritable k =new PairWritable();
		k.setWord(word);  
		k.setNum(Integer.parseInt(num));
		Text v = new Text();
		v.set(word+","+num);
		context.write(k, v);

	}
	
}


