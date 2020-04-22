package com.bdqn;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bdqn.dao.EmpMapping;
import com.bdqn.entity.Emp;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class EmpTest {

    @Resource
    private EmpMapping empMapping;

    //普通增加
    @Test
    public void emptest(){
        Emp emp=new Emp();
        emp.setAge(18);
        emp.setEmail("@22222");
        emp.setGender(1);
        emp.setUsername("小王");
        emp.setRemark("2");
        int count=empMapping.insert(emp);
        System.out.println("新增成功"+count);
    }

        //删除根据map集合删除  DELETE FROM t_employee WHERE user_name = ? AND id = ? map.put条件多的话他会自动and拼接条件
        @Test
        public  void testDeleteByMap(){
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("user_name","小王");
            map.put("id",5);
            int count=empMapping.deleteByMap(map);
            System.out.println("删除成功"+count);
        }

        //结构器删除根据 entity 条件，删除记录
        @Test
        public  void testDelete(){
            QueryWrapper<Emp> queryWrapper=new QueryWrapper<Emp>();
            queryWrapper.eq("id",6);
            int count=empMapping.delete(queryWrapper);
            System.out.println("删除成功"+count);
        }

        //删除（根据ID 批量删除）list集合删除
        @Test
        public void testDeleteBatchIds(){
            int count=empMapping.deleteBatchIds(Arrays.asList(5,7));
            System.out.println("删除成功"+count);
        }
        //根据 ID 修改
        @Test
        public void testUpdateById(){
            Emp emp=new Emp();
            emp.setUsername("王五");
            emp.setId(1);
            int count=empMapping.updateById(emp);
            System.out.println("修改成功"+count);
        }
        //根据 whereEntity 条件，更新记录
         @Test
         public void testUpdate(){
             UpdateWrapper<Emp> updateWrapper=new UpdateWrapper<Emp>();
             updateWrapper.eq("id",1);//指定修改条件
             Emp emp=new Emp();
             emp.setUsername("小王");
             int count=empMapping.update(emp,updateWrapper);
             System.out.println("修改成功"+count);
         }

         //根据主键查询
        @Test
        public  void testSelectById(){
            Emp emp = empMapping.selectById(1);
            System.out.println(emp);
        }

        @Test  //根据条件构造器查询方法,条件查询,并且该方法只能返回一条记录
        public  void  testSelectOne(){
            QueryWrapper<Emp> queryWrapper=new QueryWrapper<Emp>();
            queryWrapper.eq("user_name","Jone");
            Emp emp = empMapping.selectOne(queryWrapper);
            System.out.println(emp);
        }

        @Test //     根据 entity 条件，查询全部记录 list集合
    public  void  testSelectList(){
        QueryWrapper<Emp> queryWrapper=new QueryWrapper<Emp>();
        queryWrapper.like("user_name","j");


        List<Emp> emps = empMapping.selectList(queryWrapper);
        for (Emp emp : emps) {
            System.out.println(emp);
        }
    }

        @Test // 动态条件查询
    public  void  testSelectList2(){
        Emp emp=new Emp();
        emp.setUsername("c");//姓名
        emp.setGender(0);//年龄
        QueryWrapper<Emp> queryWrapper=new QueryWrapper<Emp>();
        //参数一是条件判断 第二个是列明 第三个参数是条件值
       queryWrapper.eq(emp.getGender()!=null,"gender",emp.getGender());
       queryWrapper.like(StringUtils.isNotBlank(emp.getUsername()),"user_name",emp.getUsername());


        List<Emp> emps = empMapping.selectList(queryWrapper);
        for (Emp emp1 : emps) {
            System.out.println(emp1);
        }
    }
    @Test//根据id批量查找
    public  void  testselectBatchIds(){
        List<Emp> emps = empMapping.selectBatchIds(Arrays.asList(2,4));
        for (Emp emp : emps) {
            System.out.println(emp);
        }
    }

    @Test//统计所有
    public  void  testselectCount(){
        //也可以按条件统计
        QueryWrapper<Emp> queryWrapper=new QueryWrapper<Emp>();
        queryWrapper.eq("gender",0);
        //统计所有
        Integer integer = empMapping.selectCount(queryWrapper);
        System.out.println(integer);
    }

    @Test//分页查询
    public  void testPage(){
        IPage<Emp> page=new Page<Emp>(1,3); //第一个参数是当前页码 第二个参数是显示条数
        //创建条件构造器对象
        QueryWrapper<Emp> queryWrapper=new QueryWrapper<Emp>();
        queryWrapper.orderByDesc("id");
        //条用分页查询的方法
        IPage<Emp> empIPage = empMapping.selectPage(page,queryWrapper);
        System.out.println("当前页码"+empIPage.getCurrent());
        System.out.println("显示条数"+empIPage.getSize());
        System.out.println("总页数"+empIPage.getPages());
        System.out.println("数据总数量"+empIPage.getTotal());
        //获取数据列表
        List<Emp> list=empIPage.getRecords();
        for (Emp emp : list) {
            System.out.println(emp);
        }
    }
}
