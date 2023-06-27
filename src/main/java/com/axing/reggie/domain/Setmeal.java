package com.axing.reggie.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 套餐
 * </p>
 *
 * @author 阿兴
 * @since 2023-05-26
 */
@ApiModel("套餐")
public class Setmeal implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 菜品分类id
     */
    @ApiModelProperty("菜品分类id")
    private Long categoryId;

    /**
     * 套餐名称
     */
    @ApiModelProperty("套餐名称")
    private String name;

    /**
     * 套餐价格
     */
    @ApiModelProperty("套餐价格")
    private BigDecimal price;

    /**
     * 状态 0:停用 1:启用
     */
    @ApiModelProperty("状态 0:停用 1:启用")
    private Integer status;

    /**
     * 编码
     */
    @ApiModelProperty("编码")
    private String code;

    /**
     * 描述信息
     */
    @ApiModelProperty("描述信息")
    private String description;

    /**
     * 图片
     */
    @ApiModelProperty("图片")
    private String image;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;

    /**
     * 是否删除
     */
    private Integer isDeleted;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Setmeal{" +
        "id=" + id +
        ", categoryId=" + categoryId +
        ", name=" + name +
        ", price=" + price +
        ", status=" + status +
        ", code=" + code +
        ", description=" + description +
        ", image=" + image +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", createUser=" + createUser +
        ", updateUser=" + updateUser +
        ", isDeleted=" + isDeleted +
        "}";
    }
}
