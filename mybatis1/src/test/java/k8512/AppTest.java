package k8512;

import k8512.mapper.BookMapper;
import k8512.pojo.Book;
import k8512.pojo.BookExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    @Test
    public void create() throws Exception{
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        File configFile = new File("mbg.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }

    SqlSession session=null;
    @Before
    public void init() throws IOException {
        //1、读取加载SqlMapConfig
        Reader reader = Resources.getResourceAsReader("SqlMapConfig.xml");
        //2、创建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        //3、创建sqlSession类
        session = sqlSessionFactory.openSession();
    }
    @After
    public void destory(){
        if (null!=session){
            try{
                session.commit();//提交事务（正常情况）
            }catch (Exception e){
                session.rollback();//回滚（出现异常）
            }finally {
                session.close();//关闭
            }
        }
    }

    //查询全部
    @Test
    public void test1(){
        List<Book> books = session.getMapper(BookMapper.class).selectByExample(null);
        for (Book book:books){
            System.out.println(book);
        }
    }

    //选择性新增
    @Test
    public void test2(){
        Book book = new Book();
        book.setName("斗破苍穹");
        book.setTypeId(5);
        session.getMapper(BookMapper.class).insertSelective(book);

    }
    //单一查询
    @Test
    public void text3(){
        Book book = session.getMapper(BookMapper.class).selectByPrimaryKey(1);
        System.out.println(book);
    }
 //条件查询
    @Test
    public void test4(){
        BookExample example=new BookExample();
        example.createCriteria().andNameLike("斗破苍穹");

        List<Book> books = session.getMapper(BookMapper.class).selectByExample(example);
        for (Book book:books){
            System.out.println(book);
        }
    }



}
