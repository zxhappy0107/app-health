package cn.anshirui.store.appdevelop.service;

import java.util.Map;

public interface LoginService {

    public Map<String, Object> login(String username, String pass);

    public int register(String phone, String pass) throws Exception;

    public boolean judgeUserExist(String phone);

    public int sendCode(String phone, boolean exe);

}
