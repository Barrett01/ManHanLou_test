package com.hh.mhl.service;

import com.hh.mhl.dao.DiningTableDAO;
import com.hh.mhl.domain.DiningTable;

import java.util.List;

public class DiningTableService {
    private DiningTableDAO diningTableDA= new DiningTableDAO();
    //返回所有餐桌信息
    public List<DiningTable> list(){
        return diningTableDA.queryMulti("select id, state from diningTable ", DiningTable.class);
    }

    //返回预定餐桌
    //根据id 查询对应的餐桌DiningTable对象
    //如果返回为null 表示对应的餐桌不存在
    public DiningTable getDiningTableById(int id){
        return diningTableDA.querySingle("select * from diningTable where id=?", DiningTable.class,id);
    }
    //如果餐厅可以预定 调用方法 对其状态进行更新
    public boolean orderDiningTable(int id,String orderName,String orderTel){
        int update = diningTableDA.update("update diningTable set state='已经预定',orderName=?,orderTel=? where id=?", orderName,orderTel,id);
        return update > 0;
    }
    //根据 更新餐桌状态
    public boolean updateDiningTableState(int id,String state){
        int update = diningTableDA.update("update diningTable set state=? where id=?", state,id);
        return update>0;
    }

    //提供方法 将指定的餐桌设置为空闲状态
    public boolean updateDiningTableToFree(int id,String state){
        int update = diningTableDA.update("update diningTable set state=? ,orderName ='' ,orderTel = '' where id=?", state,id);
        return update>0;
    }
}
