[https://tkl-tao.github.io/Java_workspace/JavaLearning/JavaLearning.html](https://tkl-tao.github.io/Java_workspace/JavaLearning/JavaLearning.html)

[https://tkl-tao.github.io/Java_workspace/SpringLearning/SpringLearning.html](https://tkl-tao.github.io/Java_workspace/SpringLearning/SpringLearning.html)

[https://tkl-tao.github.io/Java_workspace/Git_Usage.html](https://tkl-tao.github.io/Java_workspace/Git_Usage.html)

[https://tkl-tao.github.io/Java_workspace/RedisLearning/RedisLearning.html](https://tkl-tao.github.io/Java_workspace/RedisLearning/RedisLearning.html)

[https://tkl-tao.github.io/Java_workspace/SQLLearning/SQLLearning.html](https://tkl-tao.github.io/Java_workspace/SQLLearning/SQLLearning.html)

[https://tkl-tao.github.io/Java_workspace/DataStructure.html](https://tkl-tao.github.io/Java_workspace/DataStructure.html)

[https://tkl-tao.github.io/Java_workspace/HadoopLearning/HadoopLearning.html](https://tkl-tao.github.io/Java_workspace/HadoopLearning/HadoopLearning.html)


# SQL优化
## 插入数据优化
### 建议使用批量插入而不要使用单条数据插入

```sql
-- 批量插入
INSERT INTO mytable VALUES (1, 'Tom'), (2, 'Jack'), (3, 'Lucas');
```

### 执行一条SQL语句，该语句会被自动提交事务并执行。可采用手动提交事务的方式实现以单条数据插入的方式插入多条数据。

```sql
-- 手动提交事务
START TRANSACTION;
INSERT INTO mytable VALUES (1, 'Tom');
INSERT INTO mytable VALUES (2, 'Jack');
INSERT INTO mytable VALUES (3, 'Lucas');
COMMIT;
```

### 按照主键的顺序插入
由InnoDB的聚集索引特点所决定。

### 大量数据的.sql文件插入用`LOAD`插入

假设下述文件是`sql1.sql`

```text
1, Tom
2, Jack
3, Lucas
```

```bash
## 在客户端连接MySQL服务器时，加上参数--local-infile
mysql --local-infile -u root -p
```

```sql
-- 开启在本从本地加载文件导入数据的开关。通过SELECT @@local_infile可以查看开关状态。
SET GLOBAL local_infile=1;
-- 将.sql文件加载到目标表中
LOAD DATA LOCAL INFILE '/root/sql1.sql' INTO TABLE `mytable` FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n';
```

## 主键优化
1. 主键的长度尽可能小，UID和身份证号等过长不宜作为主键。
2. 插入数据时选择顺序插入，选择使用AUTO_INCREMENT自增主键。
3. 尽量避免对主键的修改。

## ORDER BY 优化
**filesort**单独设立缓存区对查询的所有数据进行排序，这种排序的效率较低。

**index**直接通过字段索引返回了有序的数据，这种排序的效率较高。

```sql
-- 假设对age,phone创建了一个联合索引（默认以升序构建索引）
SELECT id, age, phone ... ORDER BY age;  -- index
SELECT id, age, phone ... ORDER BY age, phone;  -- index
SELECT id, age, phone ... ORDER BY age DESC, phone, DESC;  -- index (backward scan)
SELECT id, age, phone ... ORDER BY age ASC, phone DESC; -- index & filesort：对于age使用index，对于phone使用filesort。
SELECT id, age, phone ... ORDER BY phone, age; -- filesort & index：对于phone使用filesort，对于age使用index
```

对于上述第3条和第4条SQL的差异我暂时不理解。不过对两个联合索引使用顺序一致的查询可以避免filesort。
为了解决第4条SQL的问题，可以创建联合索引并指定构建顺序：`CREATE INDEX idx_mytable_age_phone_ad ON mytable(age ASC, phone DESC);`。

当遇到大数据量排序时，且不可避免要使用filesort时，可以手动通过`SET sort_buffer_size=1048576  -- 1MB`来增加缓冲区大小（默认为256K）。

## GROUP BY 优化

### BFS
```python
from collections import deque

dq = deque()
dq.append(root)
while dq:
    # 处理队列中最左侧的节点，纳入其左右子节点，然后移除该最最左侧的节点。
    popped_node = dq.popleft()
    if popped_node.left is not None:
        dq.append(popped_node.left)
    if popped_node.right is not None:
        dq.append(popped_node.right)
    print(popped_node.val)
```
