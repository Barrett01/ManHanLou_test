package com.hh.mhl.service;

import com.hh.mhl.dao.BillDAO;
import com.hh.mhl.dao.MultiTableDAO;
import com.hh.mhl.domain.Bill;
import com.hh.mhl.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

public class BillService {
    private BillDAO billDAO = new BillDAO();
    private MenuService menuService = new MenuService();
    private DiningTableService diningTableService = new DiningTableService();
    private MultiTableDAO multiTableDAO = new MultiTableDAO();
    //实现点餐服务
    public boolean orderMenu(int menuId ,int nums,int diningTable){
        String billId = UUID.randomUUID().toString();
        int update = billDAO.update("insert into bill values(null,?,?,?,?,?,now(),'未付款')",billId, menuId,nums,menuService.getMenuById(menuId).getPrice() *nums,diningTable );
        if (update < 0){//插入失败
            return false;
        }
        //点餐完成 修改状态成“就餐中”
        return diningTableService.updateDiningTableState(diningTable,"就餐中");
    }

    //查看账单
    public List<Bill> showOrderMenu(int orderDiningTableId){
        List<Bill> bills = billDAO.queryMulti("select * from bill where diningTableId = ?", Bill.class, orderDiningTableId);
        return bills;

    }

    //多表查询的形式：查看账单
    public List<MultiTableBean> showOrderMenu2(int orderDiningTableId){
        List<MultiTableBean> multiTableBeans = multiTableDAO.queryMulti("select bill.* ,name, price from menu, bill where bill.menuid = menu.id and diningTableId = ?", MultiTableBean.class, orderDiningTableId);
        return multiTableBeans;
    }

    //查看餐桌是否结账
    public boolean hasPayBillByDiningTableId(int diningTableId){
        Bill bill = billDAO.querySingle("SELECT * FROM bill WHERE diningTableId=? AND state = '未付款' LIMIT 0, 1", Bill.class, diningTableId);
        return bill != null;
    }

    //未付款 结账
    //先改变bill表中的状态
    //再清理diningtable表中的数据
    public boolean payBill(int diningTableId ,String payMode){
        int update = billDAO.update("update bill set state = ? where diningTableId = ? and state = '未付款'", payMode, diningTableId);
        if (update < 0){
            return false;
        }
        if (!(diningTableService.updateDiningTableToFree(diningTableId,"空"))){
            return false;
        }
        return true;
    }
}
