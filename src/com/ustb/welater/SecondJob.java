package com.ustb.welater;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class SecondJob {
	public static void main(String[] args) {
		Configuration config =new Configuration();
		config.set("fs.defaultFS", "hdfs://node1:8020");
		config.set("yarn.resourcemanager.hostname", "node1");
		try {
			FileSystem fs =FileSystem.get(config);
//			JobConf job =new JobConf(config);
			Job job =Job.getInstance(config);
			job.setJobName("sort");
			job.setJarByClass(SecondJob.class);
			job.setOutputKeyClass(PairWritable.class);
			job.setOutputValueClass(Text.class);
			job.setSortComparatorClass(NumSort.class);
			job.setGroupingComparatorClass(PairWritableGroup.class);
			job.setMapperClass(SecondMap.class);
			job.setReducerClass(SecondReduce.class);
			
			FileInputFormat.addInputPath(job, new Path("/usr/output/weLater1"));
			
			Path path =new Path("/usr/output/weLater2");
			if(fs.exists(path)){
				fs.delete(path, true);
			}
			FileOutputFormat.setOutputPath(job,path);
			
			boolean f= job.waitForCompletion(true);
			if(f){
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
