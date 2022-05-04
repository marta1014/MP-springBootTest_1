package test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.simple.MyApplication;
import com.simple.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

// 开启AR(ActiveRecord) 第一步 pojo实体继承Model<实体>
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
public class TestActiveRecord {
    // 此处虽然没有显式注入UserMapper 但是底层的实现还是需要UserMapper进行操作 故该mapper必须存在
    @Test
    public void testSelectById(){
        User user = new User();
        user.setId(100004);
        User u = user.selectById();
        System.out.println(u);
    }

    @Test // INSERT INTO userlist ( id, name, pwd, addr ) VALUES ( ?, ?, ?, ? )
    public void testInsert() {
        User user = new User();
        user.setName("liuBei");
        user.setPwd("000000");
        user.setAddr("hello liuBei");
        boolean res = user.insert();
        System.out.println(res);
    }

    @Test // 通过id更新 UPDATE userlist SET name=? WHERE id=?
    public void updateInfo() {
        User user = new User();
        user.setId(100001); // 查询条件
        user.setPwd("1014776");
        boolean res = user.updateById();
        System.out.println(res);
    }

    @Test // 根据主键id进行删除 DELETE FROM userlist WHERE id=?
    public void deleteById() {
        User user = new User();
        user.setId(100014);
        user.deleteById(); // or 直接 user.deleteById(100013);
    }

    @Test
    public void testSelectList() {
        User user = new User();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("pwd","4313412");
        List<User> users = user.selectList(wrapper);
        for (User item : users) {
            System.out.println(item);
        }
    }


    // ****乐观锁**** 存在version字段 更新表的时候携带  解决并发更新
    // example update userlist set age = 21,(version = 2) where id = 2 and version = 1
    public void testLock() {
        /**
         *  User user = new User();
         *  User u =user.selectById(100001);
         *  user.setAddr("for version Lock");
         *  u.setVersion(u.getVersion());
         *  user.updateById();
         *
         *  模拟修改age，修改前拿到version，在修改前作为修改条件，解决并发更新引发的不一致问题
         *
         *  仅支持updateById()、update(entity,wrapper)方法 * wrapper不能复用
         *
         */


        /**
         * ****预留****
         * MP
         * sql注入器 性能分析器插件 填充 枚举(properties配置扫描包)
         */

        /**
         * 逻辑删除
         * - 表中加入对应字段标记 example delMark 0 no del 1 del
         * - 实体具备该属性 private Integer delMark; 添加 @TableLogic注解
         * properties 配置
         * # 删除状态的值
         * mybatis-plus.global-config.db-config.logic-delete-value=1
         * # 未删除状态的值
         * mybatis-plus.global-config.db-config.logic-not-delete-value=0
         */


        /**
         * MP 代码生成器
         */
    }

}
