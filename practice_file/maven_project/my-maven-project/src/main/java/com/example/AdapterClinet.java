package com.example;

// 目标接口
interface Target {
    void request();
}

// 第三方类（被适配者）
class ThirdPartyClass {
    public void specifiedRequest() {
        // 第三方类的具体实现
    }
}

// 适配器类
class AdapterClass implements Target {
    private ThirdPartyClass thirdPartyClass;

    public AdapterClass(ThirdPartyClass thirdPartyClass) {
        this.thirdPartyClass = thirdPartyClass;
    }

    @Override
    public void request() {
        // 调用被适配者的方法，此处可以添加一些额外的处理
        thirdPartyClass.specifiedRequest();
    }
}

public class AdapterClinet {
    public static void main(String[] args) {
        ThirdPartyClass thirdPartyClass = new ThirdPartyClass();
        Target target = new AdapterClass(thirdPartyClass);
        target.request();
    }
}
