package com.zt.mapreduce.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Reducer;

import org.apache.hadoop.io.Text;

/**
 * KEYIN: Text
 * VALUEIN: IntWritable
 * KEYOUT: Text
 * VALUEOUT: IntWritable
 */
public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();

    // 有多少种key, reduce就会调用多少次
    @Override
    protected void reduce(Text keyText, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable value : values) {
            sum += value.get();
        }
        this.result.set(sum);
        context.write(keyText, this.result);
    }
}
