package org.fh.controller.utils;

import org.fh.controller.base.BaseController;
import org.fh.msg.ApiReturnMsg;
import org.fh.msg.MyConst;
import org.fh.util.Const;
import org.fh.util.DateUtil;
import org.fh.util.FileUpload;
import org.fh.util.PathUtil;
import org.fh.util.WXUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 作者 BealHe
 * @date 创建时间：2020年4月1日 上午1:08:52
 * @explain 类说明 ：工具类
 */
@Controller
@RequestMapping("/utils/tools")
public class ToolsContoller extends BaseController {

	@RequestMapping(value = "/erCode")
	@ResponseBody
	public ApiReturnMsg erCode() {
		return this.returnSuccessMsg(WXUtils.postMiniqrQr("1234567", "1234567"));
	}
	
	/**
	 * 文件上传
	 * 
	 * @return
	 */
	@RequestMapping(value = "/fileUpload")
	@ResponseBody
	public ApiReturnMsg fileUpload(@RequestParam(value = "file") MultipartFile file) {
		if (null != file && !file.isEmpty()) {
			String ffile = DateUtil.getDays();
			String fileName = "";
			// 文件上传路径
			String filePath = PathUtil.getProjectpath() + Const.FILEPATHIMG + ffile;
			fileName = FileUpload.fileUp(file, filePath, this.get32UUID());
			// 访问路径
			String visPath = MyConst.HTTP_PATH + Const.FILEPATHIMG + ffile + "/" + fileName;

			return this.returnSuccessMsg(visPath);
		} else {
			return this.returnDefaultMsg("请选择文件！");
		}
	}
}
