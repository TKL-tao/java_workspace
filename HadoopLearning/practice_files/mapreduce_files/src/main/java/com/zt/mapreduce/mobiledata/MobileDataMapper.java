package com.zt.mapreduce.mobiledata;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MobileDataMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    private Text phone = new Text();
    private FlowBean fb = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, FlowBean>.Context context)
            throws IOException, InterruptedException {
        String line = value.toString();
        String[] lineData = line.split(" ");

        this.phone.set(lineData[0]);
        this.fb.setUpFlow(Long.parseLong(lineData[2]));
        this.fb.setDownFlow(Long.parseLong(lineData[3]));
        this.fb.setSumFlow();

        context.write(this.phone, this.fb);
    }
    
}
