package com.simple.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("userlist")
public class User extends Model<User> {
    // @TableId(type = IdType.AUTO) // 处理id主键生成策略
    private int id;
    private String name;
    private String pwd;
    // @TableField(value = "address") 通过此注解指定数据库字段名 为了处理那些非驼峰之类的与数据库字段 不一致问题
    // @TableField(exist = false) 插入字段数据库不存在导致的异常
    // @TableField(select = false) 查询时不返回该字段的值
    // @TableField(fill = FieldFill.INSERT) 填充 insert\update\insert_update => 需要实现MataObjectHandler
    private String addr;
}
