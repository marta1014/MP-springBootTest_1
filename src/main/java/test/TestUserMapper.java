package test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.simple.MyApplication;
import com.simple.mapper.UserMapper;
import com.simple.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class TestUserMapper {

    @Autowired
    private UserMapper userMapper;

    @Test // INSERT INTO userlist ( id, name, pwd, addr ) VALUES ( ?, ?, ?, ? )
    public void testInsert() {
        User user = new User();
        user.setName("usernimei");
        user.setPwd("4313412");
        user.setAddr("hello properties");
        int i = userMapper.insert(user);
    }

    @Test // 通过id更新 UPDATE userlist SET name=? WHERE id=?
    public void updateInfo() {
        User user = new User();
        user.setId(100032);
        user.setName("waste");
        int updateById = this.userMapper.updateById(user);
        System.out.println(updateById);
    }

    @Test // 通过QueryWrapper 传递user进行条件更新 UPDATE userlist SET addr=? WHERE (name = ?)
    public void update() {
        User user = new User();
        user.setAddr("shit");
        // this.userMapper.update(实体,包装条件对象);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // example => 条件 name = prop
        wrapper.eq("name","addr");
        int update = this.userMapper.update(user,wrapper);
        System.out.println(update);
    }

    @Test  // 通过UpdateWrapper 传递直接进行条件更新  UPDATE userlist SET addr=?,pwd=? WHERE (name = ?)
    public void update2() {
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("addr","zhuHai").set("pwd","A123456") // 需要更新的内容
        .eq("name","addr");
        int update = this.userMapper.update(null,updateWrapper);
        System.out.println(update);
    }

    @Test // 根据主键id进行删除 DELETE FROM userlist WHERE id=?
    public void deleteById() {
        int info = this.userMapper.deleteById(100003);
    }

    @Test // 根据map进行删除 多条件之间是and关系 DELETE FROM userlist WHERE name = ? AND pwd = ?
    public void deleteByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","waste");
        map.put("pwd","545454");
        this.userMapper.deleteByMap(map);
    }

    @Test // QueryWrapper 多条件 DELETE FROM userlist WHERE (name = ? AND pwd = ?)
    public void deleteByQueryWap() {
        // 用法1
        // QueryWrapper<User> wrapper = new QueryWrapper<>();
        // wrapper.eq("name","user")
        //       .eq("pwd","654266");
        // 用法2 推荐
        User user = new User();
        user.setId(100032);
        user.setName("waste");
        QueryWrapper<User> wrapper = new QueryWrapper<>(user);
        this.userMapper.delete(wrapper);
    }

    @Test // 批量删除 DELETE FROM userlist WHERE id IN ( ? , ? )
    public void deleteByBatchIds() {
        this.userMapper.deleteBatchIds(Arrays.asList(100012,100013));
    }

    @Test
    public void selectSearchById() {
        this.userMapper.selectById(100001);
    }

    @Test // 根据id批量查询 ELECT id,name,pwd,addr FROM userlist WHERE id IN ( ? , ? )
    public void selectSearchByIds() {
        this.userMapper.selectBatchIds(Arrays.asList(100004,100005));
    }

    @Test // 查询超过一条报错 low
    public void selectOne() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","maven1");
        this.userMapper.selectOne(wrapper);
    }

    @Test
    public void selectCount() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("pwd","654266");
        // eq = gt >
        this.userMapper.selectCount(wrapper);
    }

    @Test
    public void selectList() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("pwd","654266");
        this.userMapper.selectList(wrapper);
    }

    @Test // 分页测试 预先配置MybatisConfig插件 详见package com.simple.MybatisConfig
    public void selectPage() {
        Page<User> page = new Page<>(1,1);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.like("pwd","654266");
        Page<User> pages = this.userMapper.selectPage(page, wrapper);
        // pages.getPages(); // 获取总页数
        // pages.getTotal(); // 获取总条数
        // pages.getCurrent(); // 获取当前页
        List<User> records = pages.getRecords();// 获取数据集合
        System.out.println(records);

    }

    @Test // 测试在application.properties 配置mapper.xml读取
    public void selectSearchAllByConfigXML() { // select * from userlist;
        List<User> users = this.userMapper.findAllByConfigXML();
        System.out.println(users);
    }

    @Test // 全匹配 SELECT id,name,pwd,addr FROM userlist WHERE (name = ? AND pwd = ? AND addr = ?)
    public void testAllEq() {
        Map<String,Object> map = new HashMap<>();
        map.put("name","maven");
        map.put("pwd","1234567");
        map.put("addr","game");
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // wrapper.allEq(map);
        // wrapper.allEq(map,false); 无isnull条件
        wrapper.allEq((k,v) -> k.equals("pwd") || k.equals("name") || k.equals("val"),map);
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    /**
     * 比较操作
     *
     * eq   等于
     * ne   不等于
     * gt   大于
     * ge   大于等于
     * lt   小于
     * le   小于等于
     * between  之间
     * notBetween   非之间
     * in
     * notIn
     */

    /**
     *  wrapper.eq("password","admin")
     *          .le("age",44)
     *           .in("name","赵六","尼古拉斯","玉田","王兰");
     *
     */

    /**
     * 模糊查询
     *
     * like
     * ex like("name","牛") => name like '%牛%'
     *
     * notLike
     * ex notLike("name","牛") => name not like '%牛%'
     *
     * likeLeft
     * ex like("name","牛") => name like '%牛'
     *
     * likeRight
     * ex like("name","牛") => name like '牛%'
     */

    /**
     * 排序
     * // wrapper.orderByDesc("age");
     * 按照年龄进行倒序排序
     */

    @Test // SELECT id,name,pwd,addr FROM userlist WHERE (name = ? OR pwd = ?)
    public void testOr() { //
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","admin").or().eq("pwd","7777777");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test // SELECT id,name,pwd FROM userlist WHERE (name = ? OR pwd = ?) select 指定字段
    public void testSelect() { //
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("name","admin").or().eq("pwd","7777777").select("name","pwd");
        List<User> users = this.userMapper.selectList(wrapper);
        for (User user : users) {
            System.out.println(user);
        }
    }
}
