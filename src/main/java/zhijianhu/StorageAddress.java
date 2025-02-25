package zhijianhu.;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName storage_address
*/
public class StorageAddress implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
    * 地区
    */
    @NotBlank(message="[地区]不能为空")
    @Size(max= 20,message="编码长度不能超过20")
    @ApiModelProperty("地区")
    @Length(max= 20,message="编码长度不能超过20")
    private String area;
    /**
    * 书架编号
    */
    @Size(max= 10,message="编码长度不能超过10")
    @ApiModelProperty("书架编号")
    @Length(max= 10,message="编码长度不能超过10")
    private String shelf;
    /**
    * 楼层
    */
    @ApiModelProperty("楼层")
    private Integer floor;
    /**
    * 书架楼层
    */
    @ApiModelProperty("书架楼层")
    private Integer rackLayer;

    /**
    * 
    */
    private void setId(Integer id){
    this.id = id;
    }

    /**
    * 地区
    */
    private void setArea(String area){
    this.area = area;
    }

    /**
    * 书架编号
    */
    private void setShelf(String shelf){
    this.shelf = shelf;
    }

    /**
    * 楼层
    */
    private void setFloor(Integer floor){
    this.floor = floor;
    }

    /**
    * 书架楼层
    */
    private void setRackLayer(Integer rackLayer){
    this.rackLayer = rackLayer;
    }


    /**
    * 
    */
    private Integer getId(){
    return this.id;
    }

    /**
    * 地区
    */
    private String getArea(){
    return this.area;
    }

    /**
    * 书架编号
    */
    private String getShelf(){
    return this.shelf;
    }

    /**
    * 楼层
    */
    private Integer getFloor(){
    return this.floor;
    }

    /**
    * 书架楼层
    */
    private Integer getRackLayer(){
    return this.rackLayer;
    }

}
