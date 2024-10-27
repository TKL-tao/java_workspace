package org.apache.hadoop.examples;

import java.io.IOException;
import java.util.StringTokenizer;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

public class WordCount {
  public static class TokenizerMapper extends Mapper<Object, Text, Text, IntWritable> {
    private static final IntWritable one = new IntWritable(1);
    
    private Text word = new Text();
    
    // map函数的设计逻辑是一行一行地处理文本的，所以以下函数内容仅针对于读取一行的逻辑
    public void map(Object key, Text value, Mapper<Object, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
      // key是一行文本的偏移量，value是这一行文本的本身
      StringTokenizer itr = new StringTokenizer(value.toString());  // 这里应该是对一行文本进行分词
      while (itr.hasMoreTokens()) {
        this.word.set(itr.nextToken());
        context.write(this.word, one);
      } 
    }
  }
  
  public static class IntSumReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    private IntWritable result = new IntWritable();
    
    // reduce函数的设计逻辑是针对相同键的，所以以下函数内容仅针对同一个键的处理逻辑
    public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
      int sum = 0;
      for (IntWritable val : values)
        sum += val.get(); 
      this.result.set(sum);
      context.write(key, this.result);
    }
  }
  
  public static void main(String[] args) throws Exception {
    Configuration conf = new Configuration();
    String[] otherArgs = (new GenericOptionsParser(conf, args)).getRemainingArgs();
    if (otherArgs.length < 2) {
      System.err.println("Usage: wordcount <in> [<in>...] <out>");
      System.exit(2);
    } 
    Job job = Job.getInstance(conf, "word count");  // 1
    job.setJarByClass(WordCount.class);  // 2
    job.setMapperClass(TokenizerMapper.class);  // 3
    job.setCombinerClass(IntSumReducer.class);  // 3
    job.setReducerClass(IntSumReducer.class);  // 3
    job.setOutputKeyClass(Text.class); // 4
    job.setOutputValueClass(IntWritable.class);  // 5
    for (int i = 0; i < otherArgs.length - 1; i++)
      FileInputFormat.addInputPath(job, new Path(otherArgs[i]));  // 6
    FileOutputFormat.setOutputPath(job, new Path(otherArgs[otherArgs.length - 1]));  // 7
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
