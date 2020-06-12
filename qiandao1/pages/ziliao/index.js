// pages/ziliao/index.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pageBackgroundColor: 'http://ddwap.hkzhtech.com/bj.jpg',
    ACTIVITY_ID: 0,
    status: 0,
    NAME: '',
    PHONE: '',
    DEPARTMENT: '',
    POSITIONS: '',
    butbackground: 'linear-gradient(90deg,rgba(247,202,106,1),rgba(252,169,127,1))',
    butcolor: '#fff'
    //     NAME: 何先生
    // PHONE: 18728447501
    // DEPARTMENT: 开发部
    // POSITIONS: 员工
    // ACTIVITY_ID: 1
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    console.log(options.id)
    var that = this;
    var id = options.id
    this.setData({
      ACTIVITY_ID: id
    })
    wx.request({
      url: app.globalData.url + "/api/activity/findActivity",
      method: "get",
      data: {
        id: id,
      },
      header: {
        "content-type": "application/json"
      },
      success: function (res) {
        console.log(res)
        that.setData({
          data: res.data.returnMsg,
          pageBackgroundColor: res.data.returnMsg.BGIMG,
          status: res.data.returnMsg.SHOWSET,
          butbackground: res.data.returnMsg.BUTTONCOLOR,
          butcolor: res.data.returnMsg.FONTCOLOR,
        })
      }
    });

  },

  tijiao() {
    var that = this
    var status = this.data.status
    var ACTIVITY_ID = this.data.ACTIVITY_ID
    var NAME = this.data.NAME
    var PHONE = this.data.PHONE
    var DEPARTMENT = this.data.DEPARTMENT
    var POSITIONS = this.data.POSITIONS
    if (app.globalData.shoustatus == 0) {
      wx.navigateTo({
        url: '/pages/shouquan/index', //跳转到授权页面
      })
      return false;
    }else if (NAME == '') {
      wx.showModal({
        title: "提示",
        content: "请输入姓名",
        showCancel: false,
        confirmColor: "#db3c40"
      });
      return false;
    } else if (PHONE == '') {
      wx.showModal({
        title: "提示",
        content: "请输入电话",
        showCancel: false,
        confirmColor: "#db3c40"
      });
      return false;
    } else if (!(/^1(3|4|5|7|8|9|6)\d{9}$/.test(PHONE))) {
      wx.showModal({
        title: "提示",
        content: "请输入正确的手机号",
        showCancel: false,
        confirmColor: "#db3c40"
      });
      return false;
    }

    if (status != 0) {
      if (DEPARTMENT == '') {
        wx.showModal({
          title: "提示",
          content: "请输入单位",
          showCancel: false,
          confirmColor: "#db3c40"
        });
        return false;
      } else if (POSITIONS == '') {
        wx.showModal({
          title: "提示",
          content: "请输入职务",
          showCancel: false,
          confirmColor: "#db3c40"
        });
        return false;
      }
    }
    // wx.navigateTo({
    //   url: '/pages/qiandaook/index',
    // })
    var openid = wx.getStorageSync('openId')
    wx.request({
      url: app.globalData.url + "/api/userinfo/fillInUserinfo",
      method: "get",
      data: {
        NAME: NAME,
        PHONE: PHONE,
        DEPARTMENT: DEPARTMENT,
        POSITIONS: POSITIONS,
        ACTIVITY_ID: ACTIVITY_ID,
        openid: openid
      },
      header: {
        "content-type": "application/json"
      },
      success: function (res) {
        console.log(res)

        if (res.data.msgType == 0) {
          res.data.returnMsg.pageBackgroundColor = that.data.pageBackgroundColor
          wx.navigateTo({
            url: '/pages/qiandaook/index?data=' + JSON.stringify(res.data.returnMsg),
          })
        } else {
          wx.showModal({
            title: "提示",
            content: res.data.returnMsg,
            showCancel: false,
            confirmColor: "#db3c40"
          });
        }

      }
    });

  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },
  name(e) {
    console.log(e.detail.value)
    this.setData({
      NAME: e.detail.value
    })
  },
  phone(e) {
    this.setData({
      PHONE: e.detail.value
    })
  },
  danwei(e) {
    this.setData({
      DEPARTMENT: e.detail.value
    })
  },
  zhiwu(e) {
    this.setData({
      POSITIONS: e.detail.value
    })
  },
  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})