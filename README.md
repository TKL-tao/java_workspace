[https://tkl-tao.github.io/Java_workspace/JavaLearning/JavaLearning.html](https://tkl-tao.github.io/Java_workspace/JavaLearning/JavaLearning.html)

[https://tkl-tao.github.io/Java_workspace/SpringLearning/SpringLearning.html](https://tkl-tao.github.io/Java_workspace/SpringLearning/SpringLearning.html)

[https://tkl-tao.github.io/Java_workspace/Git_Usage.html](https://tkl-tao.github.io/Java_workspace/Git_Usage.html)

[https://tkl-tao.github.io/Java_workspace/RedisLearning/RedisLearning.html](https://tkl-tao.github.io/Java_workspace/RedisLearning/RedisLearning.html)

[https://tkl-tao.github.io/Java_workspace/SQLLearning/SQLLearning.html](https://tkl-tao.github.io/Java_workspace/SQLLearning/SQLLearning.html)

[https://tkl-tao.github.io/Java_workspace/DataStructure.html](https://tkl-tao.github.io/Java_workspace/DataStructure.html)

[https://tkl-tao.github.io/Java_workspace/HadoopLearning/HadoopLearning.html](https://tkl-tao.github.io/Java_workspace/HadoopLearning/HadoopLearning.html)

===Java===
# Java基础知识
## JVM, JDK, JRE, 字节码
JVM是运行Java字节码的虚拟机，可以让Java实现“一次编译，随处可运行”

https://oss.javaguide.cn/github/javaguide/java/basis/jdk-include-jre.png

**.java -> .class -> 机器码**

javac编译器负责Java源代码到字节码的转换.java -> .class

解释器或JIT负责将字节码转换为机器码。

## 移位运算符
```java
Integer.toBinaryString(5)  // 101
Integer.toBinaryString(-5)  // 11111111111111111111111111111011
```

$$5 << 3 = 5\times2^3 = 40$$

$<<$为左移运算符，高位丢弃，低位补零。

$>>$为右移运算符，高位补符号位，低位丢弃。

$>>>$无符号右移，忽略符号位，空位以0补齐。

例：对int类型左移40位的处理过程为：40%32=8，即左移8位。


MySQL学完了以后去学DQE和Dass
===MySQL===
# SQL优化
## 插入数据优化
建议优先使用批量插入而不是单条数据插入
