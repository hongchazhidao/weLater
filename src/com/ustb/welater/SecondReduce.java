package com.ustb.welater;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;


public class SecondReduce extends Reducer<PairWritable, Text, Text, Text>{
	protected void reduce(PairWritable key, Iterable<Text> values, Context context)
			throws IOException, InterruptedException {
		Text k = new Text();
		Text v = new Text();
		String value = values.iterator().next().toString();
	    String[] pair = value.split(",");
	    String word = pair[0];
	    String num = pair[1];
		k.set(word);
		v.set(num);
		context.write(k, v);
	}
}
