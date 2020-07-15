/*
 *  Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.alibaba.fescar.workshop.starter;

import com.alibaba.fescar.workshop.ApplicationKeeper;
import com.alibaba.fescar.workshop.DBType;
import com.alibaba.fescar.workshop.EnvContext;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * The type Dubbo account service starter.
 *
 * @author jimin.jm @alibaba-inc.com
 */
public class DubboAccountServiceStarter {
    /**
     * 2. Account service is ready . A buyer register an account: U100001 on my e-commerce platform
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        ClassPathXmlApplicationContext accountContext = new ClassPathXmlApplicationContext(
            new String[] {"spring/dubbo-account-service.xml"});
        accountContext.getBean("service");
        JdbcTemplate accountJdbcTemplate = (JdbcTemplate)accountContext.getBean("jdbcTemplate");
        accountJdbcTemplate.update("delete from account_tbl where user_id = 'U100001'");
        if (EnvContext.dbType == DBType.MYSQL || EnvContext.dbType == DBType.PGSQL) {
            accountJdbcTemplate.update("insert into account_tbl(user_id, money) values ('U100001', 999)");
        } else if (EnvContext.dbType == DBType.ORACLE) {
            accountJdbcTemplate.update(
                "insert into account_tbl(id,user_id, money) values (ACCOUNT_TBL_SEQ.nextval,'U100001', 999)");
        }

        new ApplicationKeeper(accountContext).keep();
    }
}
