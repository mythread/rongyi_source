package net.shopxx.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ry_mall")
public class Mall implements Serializable {

    private static final long serialVersionUID = 3617539629626726227L;
    private Integer           id;                                     // ID
    private Date              createDate;                             // 创建日期
    private Date              modifyDate;                             // 修改日期
    private Integer           status;                                 // 状态 0，可用，1，不可用
    private String            name;                                    // 商场名
    private String            pic;                                     // 商场图片
    private String            areaName;                                // 地区名(例如：上海黄浦区)
    private String            areaPath;                                // 地区树路径
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Date getCreateDate() {
        return createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    
    public Date getModifyDate() {
        return modifyDate;
    }
    
    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
    
    public Integer getStatus() {
        return status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPic() {
        return pic;
    }
    
    public void setPic(String pic) {
        this.pic = pic;
    }
    
    public String getAreaName() {
        return areaName;
    }
    
    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    public String getAreaPath() {
        return areaPath;
    }
    
    public void setAreaPath(String areaPath) {
        this.areaPath = areaPath;
    }
    
    
}
