package com.zt.mapreduce.mobiledata;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MobileDataReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    private FlowBean fb = new FlowBean();

    @Override
    protected void reduce(Text keyText, Iterable<FlowBean> valuesfb, Reducer<Text, FlowBean, Text, FlowBean>.Context context)
            throws IOException, InterruptedException {
        long upFlowSum = 0;
        long downFlowSum = 0;
        for (FlowBean valuefb : valuesfb) {
            upFlowSum += valuefb.getUpFlow();
            downFlowSum += valuefb.getDownFlow();
        }
        this.fb.setUpFlow(upFlowSum);
        this.fb.setDownFlow(downFlowSum);
        this.fb.setSumFlow();

        context.write(keyText, this.fb);
    }
}
