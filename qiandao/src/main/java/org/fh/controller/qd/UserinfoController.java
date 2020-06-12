package org.fh.controller.qd;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.fh.controller.base.BaseController;
import org.fh.entity.Page;
import org.fh.entity.PageData;
import org.fh.service.qd.UserinfoService;
import org.fh.util.Const;
import org.fh.util.DateUtil;
import org.fh.util.FileDownload;
import org.fh.util.FileUpload;
import org.fh.util.ObjectExcelRead;
import org.fh.util.ObjectExcelView;
import org.fh.util.PathUtil;
import org.fh.util.Tools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * 说明：用户签到 作者：FH Admin QQ313596790 时间：2020-03-30 官网：www.fhadmin.org
 */
@Controller
@RequestMapping("/userinfo")
public class UserinfoController extends BaseController {

	@Autowired
	private UserinfoService userinfoService;
//加人用这个
	/**
	 * 保存
	 * 
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value = "/add")
	@RequiresPermissions("userinfo:add")
	@ResponseBody
	public Object add() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("USERINFO_ID", this.get32UUID()); // 主键
		pd.put("STATE", 0);
		userinfoService.save(pd);
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
	@RequiresPermissions("userinfo:del")
	@ResponseBody
	public Object delete() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		userinfoService.delete(pd);
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
	@RequiresPermissions("userinfo:edit")
	@ResponseBody
	public Object edit() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		userinfoService.edit(pd);
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
	@RequiresPermissions("userinfo:list")
	@ResponseBody
	public Object list(Page page) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String KEYWORDS = pd.getString("KEYWORDS"); // 关键词检索条件
		if (Tools.notEmpty(KEYWORDS))
			pd.put("KEYWORDS", KEYWORDS.trim());
		page.setPd(pd);
		List<PageData> varList = userinfoService.list(page); // 列出Userinfo列表
		for (PageData item : varList) {
			Date QDTIME = (Date) item.get("QDTIME");
			String qdTime = DateUtil.date2Str(QDTIME, "yyyy-MM-dd HH:mm:ss");
			item.put("QDTIME", qdTime);

			Object state = item.get("STATE");
			if (null != state) {
				int s = (Integer) state;
				if (1 == s) {
					item.put("STATE", "已签到");
				} else {
					item.put("STATE", "未签到");
				}
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
	@RequiresPermissions("userinfo:edit")
	@ResponseBody
	public Object goEdit() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = userinfoService.findById(pd); // 根据ID读取
		Object state = pd.get("STATE");
		if (null != state) {
			int s = (Integer) state;
			if (1 == s) {
				pd.put("STATE", "已签到");
			} else {
				pd.put("STATE", "未签到");
			}
		}

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
	@RequiresPermissions("userinfo:del")
	@ResponseBody
	public Object deleteAll() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String errInfo = "success";
		PageData pd = new PageData();
		pd = this.getPageData();
		String DATA_IDS = pd.getString("DATA_IDS");
		if (Tools.notEmpty(DATA_IDS)) {
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			userinfoService.deleteAll(ArrayDATA_IDS);
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
		PageData pd = this.getPageData();

		Map<String, Object> dataMap = new HashMap<String, Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("签到活动"); // 2
		titles.add("姓名"); // 4
		titles.add("电话"); // 5
		titles.add("单位"); // 6
		titles.add("职务"); // 7
		titles.add("签到时间"); // 9
		titles.add("签到位数"); // 10
		titles.add("座位号"); // 11
		dataMap.put("titles", titles);
		List<PageData> varOList = userinfoService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for (int i = 0; i < varOList.size(); i++) {
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).getString("ACTIVITY_NAME")); // 2
			vpd.put("var2", varOList.get(i).getString("NAME")); // 4
			vpd.put("var3", varOList.get(i).getString("PHONE")); // 5
			vpd.put("var4", varOList.get(i).getString("DEPARTMENT")); // 6
			vpd.put("var5", varOList.get(i).getString("POSITIONS")); // 7
			vpd.put("var6",
					null != varOList.get(i).get("QDTIME")
							? DateUtil.date2Str((Date) varOList.get(i).get("QDTIME"), "yyyy-MM-dd HH:mm:ss")
							: ""); // 9
			vpd.put("var7", null != varOList.get(i).get("QDORDER") ? varOList.get(i).get("QDORDER").toString() : ""); // 10
			vpd.put("var8", null != varOList.get(i).get("SEATNO") ? varOList.get(i).get("SEATNO").toString() : ""); // 11
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv, dataMap);
		return mv;
	}

	/**
	 * 下载模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/downExcel")
	public void downExcel(HttpServletResponse response) throws Exception {
		FileDownload.fileDownload(response, PathUtil.getProjectpath() + Const.FILEPATHFILE + "Userinfo.xls",
				"Userinfo.xls");
	}

	/**
	 * 从EXCEL导入到数据库
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/readExcel")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public Object readExcel(@RequestParam(value = "excel", required = false) MultipartFile file, String activityId)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String errInfo = "success";
		PageData pd = new PageData();
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getProjectpath() + Const.FILEPATHFILE; // 文件上传路径
			String fileName = FileUpload.fileUp(file, filePath, "userinfoexcel"); // 执行上传
			@SuppressWarnings("rawtypes")
			List<PageData> listPd = (List) ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0); // 执行读EXCEL操作,读出的数据导入List
																									// 2:从第3行开始；0:从第A列开始；0:第0个sheet
			// 活动
			pd.put("ACTIVITY_ID", activityId);
			// 签到状态
			pd.put("STATE", 0);

			/**
			 * var0 :姓名 var1 :电话 var2 :部门 var3 :职务 var4 :座位号
			 */
			for (int i = 0; i < listPd.size(); i++) {
				pd.put("USERINFO_ID", this.get32UUID()); // ID
				pd.put("NAME", listPd.get(i).getString("var0")); // 姓名
				pd.put("PHONE", listPd.get(i).getString("var1")); // 电话
				pd.put("DEPARTMENT", listPd.get(i).getString("var2")); // 部门
				pd.put("POSITIONS", listPd.get(i).getString("var3")); // 职位
				pd.put("SEATNO", listPd.get(i).getString("var4")); // 座位号

				userinfoService.save(pd);
			}
		}
		map.put("result", errInfo); // 返回结果
		return map;
	}

}
