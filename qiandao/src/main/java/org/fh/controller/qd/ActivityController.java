package org.fh.controller.qd;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.fh.controller.base.BaseController;
import org.fh.entity.Page;
import org.fh.entity.PageData;
import org.fh.entity.system.User;
import org.fh.msg.MyConst;
import org.fh.service.qd.ActivityService;
import org.fh.util.Const;
import org.fh.util.DateUtil;
import org.fh.util.Jurisdiction;
import org.fh.util.ObjectExcelView;
import org.fh.util.Tools;
import org.fh.util.UuidUtil;
import org.fh.util.WXUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 说明：签到活动 作者：FH Admin QQ313596790 时间：2020-03-30 官网：www.fhadmin.org
 */
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;

	//新建活动
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@RequiresPermissions("activity:add")
	@ResponseBody
	public Object add() throws Exception {
		// 获取当前管理员信息
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String CREATOR = user.getUSER_ID();

		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		System.out.println("查看request::"+pd);

		String ACTIVITY_ID = UuidUtil.getRamdom();
		pd.put("ACTIVITY_ID", ACTIVITY_ID); // 主键
		pd.put("CREATETIME", DateUtil.date2Str(new Date(), "yyyy-MM-dd HH:mm:ss"));
		pd.put("STATE", 0);
		pd.put("CREATOR", CREATOR);

		// 获取小程序二维码
		String scene = ACTIVITY_ID;
		String url = WXUtils.postMiniqrQr(scene, ACTIVITY_ID);
		pd.put("ERCODE", url);
		activityService.save(pd);
		map.put("result", errInfo);
		return map;
	}

	/**
	 * 删除
	 * 
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value = "/delete")
	@RequiresPermissions("activity:del")
	@ResponseBody
	public Object delete() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		activityService.delete(pd);
		map.put("result", errInfo); // 返回结果
		return map;
	}

	/**
	 * 修改
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/edit")
	@RequiresPermissions("activity:edit")
	@ResponseBody
	public Object edit() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		activityService.edit(pd);
		map.put("result", errInfo);
		return map;
	}

	/**
	 * 列表
	 * 
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	@RequiresPermissions("activity:list")
	@ResponseBody
	public Object list(Page page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取当前管理员信息
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		// 角色ID
		String ROLE_ID = user.getROLE_ID();
		if (!MyConst.MANAGER_ROLE_ID.equals(ROLE_ID) && !MyConst.ADMIN_ROLE_ID.equals(ROLE_ID)) {
			// 用户id
			String CREATOR = user.getUSER_ID();
			pd.put("CREATOR", CREATOR);
		}

		String KEYWORDS = pd.getString("KEYWORDS"); // 关键词检索条件
		if (Tools.notEmpty(KEYWORDS))
			pd.put("KEYWORDS", KEYWORDS.trim());
		page.setPd(pd);
		List<PageData> varList = activityService.list(page); // 列出Activity列表

		for (PageData item : varList) {
			Integer SHOWSET = (Integer) item.get("SHOWSET");

			if (0 == SHOWSET) {
				item.put("SHOWSET", "不显示");
			} else {
				item.put("SHOWSET", "显示");
			}

			Integer CHECKTYPE = (Integer) item.get("CHECKTYPE");
			if (1 == CHECKTYPE) {
				item.put("CHECKTYPE", "验证姓名");
			} else if (2 == CHECKTYPE) {
				item.put("CHECKTYPE", "验证电话");
			} else if (3 == CHECKTYPE) {
				item.put("CHECKTYPE", "验证姓名和电话");
			} else {
				item.put("CHECKTYPE", "不验证");
			}
		}

		map.put("varList", varList);
		map.put("page", page);
		map.put("result", errInfo);
		return map;
	}

	/**
	 * 去修改页面获取数据
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/goEdit")
	@RequiresPermissions("activity:edit")
	@ResponseBody
	public Object goEdit() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = activityService.findById(pd); // 根据ID读取

		map.put("pd", pd);
		map.put("result", errInfo);
		return map;
	}

	/**
	 * 批量删除
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/deleteAll")
	@RequiresPermissions("activity:del")
	@ResponseBody
	public Object deleteAll() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (Tools.notEmpty(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			activityService.deleteAll(ArrayDATA_IDS);
			errInfo = "success";
		} else {
			errInfo = "error";
		}
		map.put("result", errInfo); // 返回结果
		return map;
	}

	/**
	 * 导出到excel
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel")
	@RequiresPermissions("toExcel")
	public ModelAndView exportExcel() throws Exception {
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();

		// 获取当前管理员信息
		Session session = Jurisdiction.getSession();
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String CREATOR = user.getUSER_ID();
		pd.put("CREATOR", CREATOR);

		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("活动名"); // 1
		titles.add("开始时间"); // 2
		titles.add("结束时间"); // 3
		titles.add("限制人数"); // 4
		titles.add("活动地址"); // 5
		titles.add("活动描述"); // 6
		titles.add("状态"); // 7
		titles.add("二维码"); // 8
		dataMap.put("titles", titles);
		List<PageData> varOList = activityService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("NAME")); // 1
			vpd.put("var2", varOList.get(i).getString("STARTTIME")); // 2
			vpd.put("var3", varOList.get(i).getString("ENDTIME")); // 3
			vpd.put("var4", varOList.get(i).get("LIMITCOUNT").toString()); // 4
			vpd.put("var5", varOList.get(i).getString("ADDRESS")); // 5
			vpd.put("var6", varOList.get(i).getString("DESCRIBES")); // 6
			vpd.put("var7", varOList.get(i).get("STATE").toString()); // 7
			vpd.put("var8", varOList.get(i).getString("ERCODE")); // 8
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 获取活动列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getActivity")
	@ResponseBody
	public Object getActivity() {
		PageData pd = this.getPageData();
		String errInfo = "success";
		Map<String, Object> reMsg = new HashMap<>();

		// 查询所有未开始活动列表
		List<PageData> activities = activityService.listByParas(pd);

		reMsg.put("activities", activities);
		reMsg.put("result", errInfo);
		return reMsg;
	}

}
