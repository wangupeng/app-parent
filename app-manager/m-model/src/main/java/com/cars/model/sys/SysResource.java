package com.cars.model.sys;


import com.cars.util.page.Page;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by wangyupeng on 2017/8/18.
 */
@Table(name = "demo_sys_resource")
public class SysResource extends Page {
    @Id
    private String resourceId;//主键:四位，首位字母A表示1级，B表示2级..(A001、B001、C001)
    private String resourceName;//资源名称
    private String resourceUrl;//资源URL
    private String resourceType;//资源类型，1：菜单，2按钮
    private String parentId;//父节点ID
    @Transient
    private String parentName;//上级节点名称
    private String target;//链接目标
    private String icon;//图标
    private String description;//描述
    private Integer displayOrder;//排序
    @Transient
    private int cnt;//子节点数量

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    @Override
    public String toString() {
        return "SysResource{" +
                "resourceId='" + resourceId + '\'' +
                ", resourceName='" + resourceName + '\'' +
                ", resourceUrl='" + resourceUrl + '\'' +
                ", resourceType='" + resourceType + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", target='" + target + '\'' +
                ", icon='" + icon + '\'' +
                ", description='" + description + '\'' +
                ", displayOrder=" + displayOrder +
                ", cnt=" + cnt +
                '}';
    }
}
