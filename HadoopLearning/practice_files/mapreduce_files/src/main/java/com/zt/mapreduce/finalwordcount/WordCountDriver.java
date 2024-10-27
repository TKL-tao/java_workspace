package com.zt.mapreduce.finalwordcount;

import java.util.Arrays;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class WordCountDriver {

    // tool里面包括了任务提交程序、以及Mapper和reducer。
    private static Tool tool;
    public static void main(String[] args) throws Exception {
        
        Configuration conf = new Configuration();
        
        // jar任务有三个参数：wordcount input_path output_path。根据第一个参数决定所使用的tool
        switch (args[0]) {
            case "wordcount":
                tool = new WordCount();
                break;
            default:
                throw new RuntimeException("No such tool: " + args[0]);
        }

        // 向tool实际传递后两个参数，并运行Tool
        int run = ToolRunner.run(conf, tool, Arrays.copyOfRange(args, 1, args.length));
        
        System.exit(run);
    }
}
