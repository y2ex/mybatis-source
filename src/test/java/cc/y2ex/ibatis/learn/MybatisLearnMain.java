/**
 *    Copyright 2009-2021 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package cc.y2ex.ibatis.learn;

import cc.y2ex.ibatis.learn.mapper.TUserMapper;
import cc.y2ex.ibatis.learn.model.TUser;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;

/**
 * @author Yanci丶
 * @date 2021-06-24
 */
public class MybatisLearnMain {

    public static void main(String[] args) {

        String resource = "resources/mybatis-config.xml";
        Reader reader;
        try {
            //将XML配置文件构建为Configuration配置类
            reader = Resources.getResourceAsReader(resource);
            // 通过加载配置文件流构建一个SqlSessionFactory  DefaultSqlSessionFactory
            SqlSessionFactory sqlMapper = new SqlSessionFactoryBuilder().build(reader);
            // 数据源 执行器  DefaultSqlSession
            SqlSession session = sqlMapper.openSession();
            try {
                // 执行查询 底层执行jdbc
                TUser user = session.selectOne("cc.y2ex.ibatis.learn.mapper.TUserMapper.selectByPrimaryKey", 1);
                System.out.println("user1 username:" + user.getUsername());

                TUserMapper userMapper = session.getMapper(TUserMapper.class);
                System.out.println(userMapper.getClass());
                TUser user2 = userMapper.selectByPrimaryKey(1);
                System.out.println("user2 username:" + user2.getUsername());
                session.commit();

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                session.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
