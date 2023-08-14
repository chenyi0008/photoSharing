package com.example.photosharing.model.dto;


import java.util.List;

/**
 * 图文分享列表实体类
 */
public class ImageShareListDto {
    // 当前页
    private int current;

    // 图文分享实体数据列表
    private List<ImageShareItemDto> records;

    // 页面大小
    private int size;

    // 共多少条数据
    private int total;

    @Override
    public String toString() {
        return "ImageShareListDto{" +
                "current=" + current +
                ", records=" + records +
                ", size=" + size +
                ", total=" + total +
                '}';
    }

    // 获取当前页
    public int getCurrent() {
        return current;
    }

    // 设置当前页
    public void setCurrent(int current) {
        this.current = current;
    }

    // 获取图文分享实体数据列表
    public List<ImageShareItemDto> getRecords() {
        return records;
    }

    // 设置图文分享实体数据列表
    public void setRecords(List<ImageShareItemDto> records) {
        this.records = records;
    }

    // 获取页面大小
    public int getSize() {
        return size;
    }

    // 设置页面大小
    public void setSize(int size) {
        this.size = size;
    }

    // 获取共多少条数据
    public int getTotal() {
        return total;
    }

    // 设置共多少条数据
    public void setTotal(int total) {
        this.total = total;
    }
}