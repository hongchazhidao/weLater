package com.ustb.welater;

import java.io.IOException;
import java.io.StringReader;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class FirstMap extends Mapper<LongWritable, Text, Text, IntWritable>{
	protected void map(LongWritable key, Text value,
			Context context)
			throws IOException, InterruptedException {
		String comment =value.toString().trim();
		StringReader sr =new StringReader(comment);
		IKSegmenter ikSegmenter =new IKSegmenter(sr, true);
		Lexeme word=null;
		while( (word=ikSegmenter.next()) !=null ){
			String w= word.getLexemeText();
			context.write(new Text(w), new IntWritable(1));
		}
	}
}
