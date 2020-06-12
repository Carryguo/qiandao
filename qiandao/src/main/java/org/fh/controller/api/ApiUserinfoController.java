package org.fh.controller.api;

import java.util.Date;
import java.util.List;

import org.fh.controller.base.BaseController;
import org.fh.entity.PageData;
import org.fh.msg.ApiReturnMsg;
import org.fh.service.qd.ActivityService;
import org.fh.service.qd.UserinfoService;
import org.fh.util.DateUtil;
import org.fh.util.WXUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 作者 BealHe
 * @date 创建时间：2020年3月31日 上午12:59:20
 * @explain 类说明 ：活动签到用户
 */
@Controller
@RequestMapping("/api/userinfo")
public class ApiUserinfoController extends BaseController {

	@Autowired
	private UserinfoService userinfoService;
	@Autowired
	private ActivityService activityService;

	//签到就用这个
	/**
	 * 填写用户签到信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/fillInUserinfo")
	@ResponseBody
	public ApiReturnMsg fillInUserinfo() throws Exception {
		PageData pd = this.getPageData();

		// 姓名
		String NAME = pd.getString("NAME");
		// 电话
		String PHONE = pd.getString("PHONE");
		// 单位
		String DEPARTMENT = pd.getString("DEPARTMENT");
		// 职位
		String POSITIONS = pd.getString("POSITIONS");
		// 活动ID
		String ACTIVITY_ID = pd.getString("ACTIVITY_ID");
		// openid
		String openid = pd.getString("openid");
		// 根据openid和活动id查询签到记录
		PageData op = new PageData();
		op.put("OPENID", openid);
		op.put("ACTIVITY_ID", ACTIVITY_ID);
		List<PageData> ops = userinfoService.findByParas(op);
		if (null != ops && 0 != ops.size()) {
			return this.returnDefaultMsg("你已签到成功");
		}

		// 查询活动信息
		PageData activity = new PageData();
		activity.put("ACTIVITY_ID", ACTIVITY_ID);
		activity = activityService.findById(pd);
		// 验证方式：0不验证、1验证姓名、2验证电话、3验证姓名和电话
		int CHECKTYPE = null != activity.get("CHECKTYPE") ? (int) activity.get("CHECKTYPE") : 0;

		List<PageData> users;
		PageData user = new PageData();
		user.put("ACTIVITY_ID", ACTIVITY_ID);

		// 是否验证
		if (0 != CHECKTYPE) {
			if (3 == CHECKTYPE) {
				user.put("NAME", NAME);
				user.put("PHONE", PHONE);
			} else if (2 == CHECKTYPE) {
				user.put("PHONE", PHONE);
			} else if (1 == CHECKTYPE) {
				user.put("NAME", NAME);
			}
			users = userinfoService.findByParas(user);
			if (null != users && 0 != users.size()) {
				user = users.get(0);
				// 判断是否已经签到
				int STATE = null != user.get("STATE") ? (int) user.get("STATE") : 0;
				if (0 == STATE) {
					user.put("NAME", NAME);
					user.put("PHONE", PHONE);
					user.put("DEPARTMENT", DEPARTMENT);
					user.put("POSITIONS", POSITIONS);
					user.put("OPENID", openid);
					// 设置签到时间
					Date qdTime = new Date();
					user.put("QDTIME", qdTime);
					// 设置签到状态
					user.put("STATE", 1);

					// 获取签到位数
					int QDORDER = userinfoService.getSeatNo(ACTIVITY_ID);
					user.put("QDORDER", QDORDER);
					userinfoService.edit(user);
				} else {
					user = users.get(0);
				}
				user.put("QDTIME", DateUtil.date2Str((Date) user.get("QDTIME"), "yyyy-MM-dd HH:mm:ss"));
				return this.returnSuccessMsg(user);
			} else {
				return this.returnDefaultMsg("你没有收到活动的邀请");
			}
		} else {
			// 查询是否存在签到信息
			users = userinfoService.findByParas(pd);
			if (null == users || 0 == users.size()) {
				// id
				String USERINFO_ID = this.get32UUID();
				user.put("USERINFO_ID", USERINFO_ID);

				user.put("NAME", NAME);
				user.put("PHONE", PHONE);
				user.put("DEPARTMENT", DEPARTMENT);
				user.put("POSITIONS", POSITIONS);
				user.put("OPENID", openid);
				// 设置签到时间
				Date qdTime = new Date();
				user.put("QDTIME", qdTime);
				// 设置签到状态
				user.put("STATE", 1);

				// 获取签到位数
				int SEATNO = userinfoService.getSeatNo(ACTIVITY_ID);
				user.put("QDORDER", SEATNO);

				// 设置座位号
//				user.put("SEATNO", SEATNO + "号台");
				userinfoService.save(user);
			} else {
				user = users.get(0);
			}
			user.put("QDTIME", DateUtil.date2Str((Date) user.get("QDTIME"), "yyyy-MM-dd HH:mm:ss"));
			return this.returnSuccessMsg(user);
		}
	}

	/**
	 * 获取openid
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getOpenid")
	@ResponseBody
	public ApiReturnMsg getOpenid() {
		PageData pd = this.getPageData();
		// 授权code
		String CODE = pd.getString("code");
		// 获取openid
		String openid = WXUtils.openid(CODE);
		return this.returnSuccessMsg(openid);
	}
}
