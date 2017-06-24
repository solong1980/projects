package io.renren.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 素材表
 * 
 * @author longlianghua
 * @email solong1980@163.com
 * @date 2017-06-22 20:24:04
 */
public class BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Date fastAquiredDate = new Date();
	static {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				fastAquiredDate = new Date();
			}
		}, 10, 200);
	}
	// key
	private Long id;
	private Long createrId;
	private Date createTime;
	private Long updaterId;
	private Date updateTime;
	
	public static Date getFastDate(){
		return fastAquiredDate;
	}
	
	/**
	 * 设置：key
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：key
	 */
	public Long getId() {
		return id;
	}


	public void setCreaterId(Long createrId) {
		this.createrId = createrId;
	}

	public Long getCreaterId() {
		return createrId;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setUpdaterId(Long updaterId) {
		this.updaterId = updaterId;
	}

	public Long getUpdaterId() {
		return updaterId;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

}
