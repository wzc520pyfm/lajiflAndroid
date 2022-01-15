package com.baidu.duer.lajifl.bean.lajifl;

/**
 * 垃圾分类接口返回数据(data部分)
 * @param <T> 垃圾类型的描述
 */
public class Data<T> {
    // 物品名称
    private String name;
    // 所属垃圾类型
    private String type;
    // 所属垃圾类型的描述
    private T description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public T getDescription() {
        return description;
    }

    public void setDescription(T description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Data{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description=" + description +
                '}';
    }
}
