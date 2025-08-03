package com.hh.mhl.view;

import com.hh.mhl.dao.DiningTableDAO;
import com.hh.mhl.domain.*;
import com.hh.mhl.service.BillService;
import com.hh.mhl.service.DiningTableService;
import com.hh.mhl.service.EmployeeService;
import com.hh.mhl.service.MenuService;
import com.hh.mhl.utils.Utility;

import java.util.List;

/*
这是主界面
 */
public class MHLView {
    private boolean loop = true; //控制是否退出主菜单
    private String key = ""; //接收用户的选择
    private EmployeeService employeeService = new EmployeeService(); //定义属性EmployeeService
    private DiningTableService diningTableService = new DiningTableService();//定义属性
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();

    public static void main(String[] args) {
        MHLView mhlView = new MHLView();
        mhlView.mainMennu();
    }

    //结账
    public void payMoney() {
        System.out.println("==============结账服务=============");

        System.out.println("请选择要结账的餐桌编号（-1退出）：");
        int payDiningTableId = Utility.readInt();
        if (payDiningTableId == -1) {
            System.out.println("=======退出========");
            return;
        }
        if (diningTableService.getDiningTableById(payDiningTableId) == null) {
            System.out.println("========餐桌不存在=========");
            return;
        }
        if (!(billService.hasPayBillByDiningTableId(payDiningTableId))) {
            System.out.println("==========该餐位没有未结账账单==========");
            return;
        }

        System.out.println("结账方式（现金/支付宝/微信）回车表示退出：");
        String payMode = Utility.readString(10, "");//回车就是返回“”
        if ("".equals(payMode)) {
            System.out.println("============取消付款==========");
            return;
        }
        System.out.println("确认是否结账（Y/N）：");
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            boolean b = billService.payBill(payDiningTableId, payMode);
            if (b) {
                System.out.println("==============结账完成=============");
            } else {
                System.out.println("===============结账失败================");
            }
        } else {
            System.out.println("============取消付款==========");
        }

    }

    //查看账单
    public void showOrder() {
        System.out.println("请输入你要查看账单的桌号：");
        int orderDiningTable = Utility.readInt();
        List<Bill> bills = billService.showOrderMenu(orderDiningTable);
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t\t菜品名\t\t价格");
        for (Bill bill : bills) {
            System.out.println(bill);
        }
        System.out.println("===========显示完毕============");
    }

    //查看账单2.0 可以实现多表查询
    public void showOrder2() {
        System.out.println("请输入你要查看账单的桌号：");
        int orderDiningTable = Utility.readInt();
        List<MultiTableBean> multiTableBeans = billService.showOrderMenu2(orderDiningTable);
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态\t\t菜品名\t\t价格");
        for (MultiTableBean bill : multiTableBeans) {
            System.out.println(bill);
        }
        System.out.println("===========显示完毕============");
    }

    //点餐服务
    public void orderMenu() {

        System.out.println("======点餐服务=====");
        //过滤条件
        System.out.println("请选择点餐的桌号（-1退出）：");
        int orderDiningTable = Utility.readInt();
        if (orderDiningTable == -1) {
            System.out.println("==========取消点餐===========");
            return;
        }
        DiningTable orderdiningTableById = diningTableService.getDiningTableById(orderDiningTable);
        if (orderdiningTableById == null) {
            System.out.println("==========该餐桌号不存在============");
            return;
        }

        System.out.println("请选择要点的菜品编号（-1退出）：");
        int orderMenuId = Utility.readInt();
        if (orderMenuId == -1) {
            System.out.println("==========取消点餐===========");
            return;
        }
        Menu menuById = menuService.getMenuById(orderMenuId);
        if (menuById == null) {
            System.out.println("=============没有该菜品===============");
            return;
        }
        System.out.println("请选择菜品的数量（-1）退出");
        int orderNums = Utility.readInt();
        if (orderNums == -1) {
            System.out.println("==========取消点餐===========");
            return;
        }

        System.out.println("确定（Y/N）:");
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            if (billService.orderMenu(orderMenuId, orderNums, orderDiningTable)) {//写入账单表
                System.out.println("========点餐成功=========");
            } else {
                System.out.println("===========点餐失败===========");
            }
        }

    }

    //显示所有的菜品
    public void showMenu() {
        System.out.println("============菜单============");
        List<Menu> menus = menuService.allList();
        System.out.println("\n菜品编号\t\t菜品名\t\t\t类别\t\t\t价格");
        for (Menu menu : menus) {
            System.out.println(menu);
        }
        System.out.println("============更多菜品敬请期待===========");
    }

    //预定的餐桌
    public void orderDiningTable() {
        System.out.println("=====预定餐桌========");
        System.out.println("请选择要预定的餐桌编号（-1退出）：");
        int orderId = Utility.readInt();
        if (orderId == -1) {
            System.out.println("===========取消预定==========");
            return;
        }
        //确认这个卓号吗
        System.out.println("确认这个餐桌（Y/N）？");
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {//要预定
            //根据orderId 返回对应的DiningTable对象 如果为null 说明对象不存在
            DiningTable diningTableById = diningTableService.getDiningTableById(orderId);
            if (diningTableById == null) {
                System.out.println("=========预定餐桌不存在========");
                return;
            }
            //判断该餐桌的状态是否为空
            if (!("空".equals(diningTableById.getState()))) {//已经预约或者就餐中
                System.out.println("=======该餐桌已经预定或者就餐中...========");
                return;
            }
            //有餐桌 且没人预定就餐时
            //接收预定信息
            System.out.println("预定人名字：");
            String orderName = Utility.readString(50);
            System.out.println("预定人电话：");
            String orderTel = Utility.readString(50);
            //更新到餐桌预约系统里面
            if (diningTableService.orderDiningTable(orderId, orderName, orderTel)) {
                System.out.println("===========预约成功=========");
            } else {
                System.out.println("预约失败");
            }

        } else {
            System.out.println("=========取消预定餐桌========");
        }
    }

    //显示餐桌的状态
    public void listDiningTable() {
        List<DiningTable> diningTables = diningTableService.list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for (DiningTable diningTable : diningTables) {
            System.out.println(diningTable);
        }
        System.out.println("===========显示完毕==============");
    }

    //显示主菜单
    public void mainMennu() {
        while (loop) {
            System.out.println("===============满汉楼=================");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入员工号：");
                    String empId = Utility.readString(50);
                    System.out.println("请输入密 码：");
                    String pwd = Utility.readString(50);
                    //判断去数据库拿数据对比
                    Employee employeeByIdAndPwd = employeeService.getEmployeeByIdAndPwd(empId, pwd);
                    if (employeeByIdAndPwd != null) {
                        System.out.println("=======登录成功[" + employeeByIdAndPwd.getName() + "]=========");
                        //进度二级菜单
                        while (loop) {
                            System.out.println("=============满汉楼二级菜单===========");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜品");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 9 退出满汉楼");

                            System.out.println("请输入你的选择：");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    listDiningTable();
                                    break;
                                case "2":
//                                    System.out.println("预定餐桌");
                                    orderDiningTable();
                                    break;
                                case "3":
//                                    System.out.println("显示所有菜品");
                                    showMenu();
                                    break;
                                case "4":
                                    orderMenu();
//                                    System.out.println("点餐服务");
                                    break;
                                case "5":
//                                    showOrder();
                                    showOrder2();
//                                    System.out.println("查看账单");
                                    break;
                                case "6":
//                                    System.out.println("结账");
                                    payMoney();
                                    break;
                                case "9":
//                                    System.out.println("退出满汉楼");
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("你的输入有误请重新输入");

                            }
                        }

                    } else {
                        System.out.println("=========密码错误============");
                    }
                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("你的输入有误，请重新输入");
            }
        }
        System.out.println("你退出了满汉楼");
    }

}
