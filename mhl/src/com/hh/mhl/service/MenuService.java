package com.hh.mhl.service;

import com.hh.mhl.dao.MenuDAO;
import com.hh.mhl.domain.Menu;
import com.hh.mhl.utils.JDBCUtilByDruid;

import java.util.List;


public class MenuService {
    private MenuDAO menuDAO = new MenuDAO();
    //返回菜单的所有菜品
    public List<Menu> allList() {
        return menuDAO.queryMulti("select * from menu", Menu.class);
    }
    //需要一个方法 返回对象 从而拿到参数（价格）
    public Menu getMenuById(int id) {
        return menuDAO.querySingle("select * from menu where id = " + id, Menu.class);
    }
}
