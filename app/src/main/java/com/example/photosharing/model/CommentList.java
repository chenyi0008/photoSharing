package com.example.photosharing.model;

import java.util.List;

public class CommentList {
    private List<Comment> records;
    private int total;
    private int size;
    private int current;

    public List<Comment> getRecords() {
        return records;
    }

    public void setRecords(List<Comment> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CommentList{\n")
                .append("  records=").append(records).append("\n")
                .append("  total=").append(total).append("\n")
                .append("  size=").append(size).append("\n")
                .append("  current=").append(current).append("\n")
                .append("}");
        return sb.toString();
    }
}
