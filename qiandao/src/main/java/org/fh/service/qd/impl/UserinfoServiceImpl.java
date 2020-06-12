package org.fh.service.qd.impl;

import java.util.List;

import org.fh.entity.Page;
import org.fh.entity.PageData;
import org.fh.mapper.dsno1.qd.UserinfoMapper;
import org.fh.service.qd.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 说明： 用户签到接口实现类 作者：FH Admin Q313596790 时间：2020-03-30 官网：www.fhadmin.org
 * 
 * @version
 */
@Service
@Transactional // 开启事物
public class UserinfoServiceImpl implements UserinfoService {

	@Autowired
	private UserinfoMapper userinfoMapper;

	/**
	 * 新增
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception {
		userinfoMapper.save(pd);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void delete(PageData pd) throws Exception {
		userinfoMapper.delete(pd);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception {
		userinfoMapper.edit(pd);
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	public List<PageData> list(Page page) throws Exception {
		return userinfoMapper.datalistPage(page);
	}

	/**
	 * 列表(全部)
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public List<PageData> listAll(PageData pd) throws Exception {
		return userinfoMapper.listAll(pd);
	}

	/**
	 * 通过id获取数据
	 * 
	 * @param pd
	 * @throws Exception
	 */
	public PageData findById(PageData pd) throws Exception {
		return userinfoMapper.findById(pd);
	}

	/**
	 * 批量删除
	 * 
	 * @param ArrayDATA_IDS
	 * @throws Exception
	 */
	public void deleteAll(String[] ArrayDATA_IDS) throws Exception {
		userinfoMapper.deleteAll(ArrayDATA_IDS);
	}

	/**
	 * 条件查询签到用户
	 */
	@Override
	public List<PageData> findByParas(PageData userinfo) {
		return userinfoMapper.findByParas(userinfo);
	}

	/**
	 * 获取签到位数
	 */
	@Override
	public int getSeatNo(String ACTIVITY_ID) {
		PageData pd = new PageData();
		pd.put("ACTIVITY_ID", ACTIVITY_ID);
		pd.put("STATE", "1");
		List<PageData> list = userinfoMapper.findByParas(pd);
		// 座位号
		int seatNo = 1;
		if (null != list && 0 != list.size()) {
			seatNo = list.size() + 1;
		}
		return seatNo;
	}

}
