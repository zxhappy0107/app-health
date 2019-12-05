package cn.anshirui.store.appdevelop.mapper;

import cn.anshirui.store.appdevelop.AppDevelopApplication;
import cn.anshirui.store.appdevelop.common.PassUtils;
import cn.anshirui.store.appdevelop.entity.AdminUserMain;
import cn.anshirui.store.appdevelop.entity.AdminUsers;
import cn.anshirui.store.appdevelop.note.SMSClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *@ClassName
 *@Description TODO
 *@Author zhangxuan
 *@Date 14:38
 *Version 1.0
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppDevelopApplication.class})
public class AdminTest {

    @Autowired
    private  AdminMapper adminMapper;

    @Test
    public void testSelectUserById(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AdminUsers adminUsers = adminMapper.selectUserById(2);
        System.out.println(adminUsers.getUser_account());
        System.out.println(format.format(adminUsers.getUser_starttime()));
    }

    @Test
    public void testUpdateUser(){
        AdminUsers adminUsers = new AdminUsers();
        adminUsers.setUser_id(2);
        adminUsers.setUser_name("小呆瓜2");
        adminMapper.updateUser(adminUsers);
    }

    @Test
    public void testInsert(){
        AdminUserMain adminUserMain = new AdminUserMain();
        adminUserMain.setUser_name("123");
        adminMapper.insertUserMain(adminUserMain);
        System.out.print(adminUserMain.getUmid());
    }

    @Test
    public void testInsertUser(){
        try {
            AdminUserMain adminUserMain = new AdminUserMain();
            adminUserMain.setUser_name("用户_9999");
            adminMapper.insertUserMain(adminUserMain);
            AdminUsers adminUsers = new AdminUsers();
            adminUsers.setUmid(adminUserMain.getUmid());
            adminUsers.setUser_name("小天");
            adminUsers.setUser_account("17791379999");
            adminUsers.setUser_password(PassUtils.getMD5Str("123456"));
            adminUsers.setUser_starttime(new Date());
            adminMapper.insertUser(adminUsers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUpdateUserMain(){
        AdminUserMain adminUserMain = new AdminUserMain();
        adminUserMain.setUmid(449);
        adminUserMain.setUser_birthday("2000-01-01");
        adminMapper.updateUserMain(adminUserMain);
    }

    @Test
    public void testSelectUserId(){
        System.out.println(adminMapper.selectUserIdByPhone("18697293548"));
    }

    @Test
    public void testSend(){
//        String result = SMSClientUtils.SendSMSCode("17791379355");
//        System.out.println(result);

        System.out.println(SMSClientUtils.SendValidSMSCode("231103780638720","291104"));
    }

}
