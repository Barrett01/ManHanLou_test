package com.hh.mhl.domain;

import java.time.LocalDateTime;

public class MultiTableBean {
    private Integer id;
    private String billId;
    private Integer menuId;
    private Integer nums;
    private double money;
    private Integer diningTableId;
    private LocalDateTime billDate;
    private String state;

    private String name;//菜名
    private Double price;//价格
    public MultiTableBean() {
    }

    public MultiTableBean(Integer id, LocalDateTime billDate, Integer menuId, String billId, Integer nums, Integer diningTableId, double money, String name, Double price, String state) {
        this.id = id;
        this.billDate = billDate;
        this.menuId = menuId;
        this.billId = billId;
        this.nums = nums;
        this.diningTableId = diningTableId;
        this.money = money;
        this.name = name;
        this.price = price;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getNums() {
        return nums;
    }

    public void setNums(Integer nums) {
        this.nums = nums;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Integer getDiningTableId() {
        return diningTableId;
    }

    public void setDiningTableId(Integer diningTableId) {
        this.diningTableId = diningTableId;
    }

    public LocalDateTime getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDateTime billDate) {
        this.billDate = billDate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return   id +"\t\t" + menuId+"\t\t\t" + nums+"\t\t\t" + money+"\t" + diningTableId +"\t\t" + billDate+"\t\t\t" + state +"\t\t"+name+"\t\t"+price;
    }
}
