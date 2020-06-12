package org.fh.controller.api;

import org.fh.controller.base.BaseController;
import org.fh.entity.PageData;
import org.fh.msg.ApiReturnMsg;
import org.fh.service.qd.ActivityService;
import org.fh.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 作者 BealHe
 * @date 创建时间：2020年3月31日 上午10:09:22
 * @explain 类说明 ：
 */
@Controller
@RequestMapping("/api/activity")
public class ApiActivityController extends BaseController {

	@Autowired
	private ActivityService activityService;

	/**
	 * 获取活动信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findActivity")
	@ResponseBody
	public ApiReturnMsg findActivity() throws Exception {
		PageData pd = this.getPageData();
		String id = pd.getString("id");
		pd = new PageData();
		pd.put("ACTIVITY_ID", id);
		pd = activityService.findById(pd);

		// 判断活动是否存在
		if(null == pd) {
			return this.returnDefaultMsg("活动已结束！");
		}
		// 活动开始、结束时间
		String start = pd.getString("DAYTIME") + " " + pd.getString("STARTTIME") + ":00";
		String end = pd.getString("DAYTIME") + " " + pd.getString("ENDTIME") + ":00";
		long sTime = DateUtil.str2Date(start).getTime();
		long eTime = DateUtil.str2Date(end).getTime();

		// 判断是否实在活动签到时间之类
		long now = System.currentTimeMillis();
		if (now < sTime) {
			return this.returnDefaultMsg("活动未开始，无法签到！");
		} else if (now > eTime) {
			pd.put("STATE", 2);
			activityService.edit(pd);
			return this.returnDefaultMsg("活动已结束，无法签到！");
		} else {
			pd.put("STATE", 1);
			activityService.edit(pd);
			return this.returnSuccessMsg(pd);
		}
	}
}
