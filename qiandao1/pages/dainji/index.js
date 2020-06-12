// pages/dainji/index.js
var app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    pageBackgroundColor: '',
    id: '',
    data: '',
    butbackground: '#F7CA6A',
    butcolor: 'rgb(61, 60, 57)'
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var id = options.scene
    console.log(options)
    var that = this;
    // wx.request({
    //   url: app.globalData.url + "/api/activity/findActivity",
    //   method: "get",
    //   data: {
    //     id: id,
    //   },
    //   header: {
    //     "content-type": "application/json"
    //   },
    //   success: function (res) {
    //     console.log(res)
    //     that.setData({
    //       data: res.data.returnMsg,
    //       pageBackgroundColor: res.data.returnMsg.BGIMG
    //     })
    //   }
    // });
    if (id != '' && id != undefined) {

      wx.request({
        url: app.globalData.url + "/api/activity/findActivity",
        method: "get",
        data: {
          id: id,
        },
        header: {
          "content-type": "application/json"
        },
        success: function(res) {
          console.log(res)
          if (res.data.msgType != 0) {
            wx.showModal({
              title: "提示",
              content: res.data.returnMsg,
              showCancel: false,
              confirmColor: "#db3c40"
            });
          } else {

            that.setData({
              id: id,
              data: res.data.returnMsg,
              pageBackgroundColor: res.data.returnMsg.BGIMG,
              butbackground: res.data.returnMsg.BUTTONCOLOR,
              butcolor: res.data.returnMsg.FONTCOLOR,
            })
          }

        }
      });
    }
  },
  dianji() {
    var id = this.data.id
    // if (id == 0 && id == undefined) {
    //   wx.showToast({
    //     title: '请扫码进入',
    //     icon: 'none',
    //   })
    // } else if (app.globalData.shoustatus == 0) {
    //   wx.navigateTo({
    //     url: '/pages/shouquan/index', //跳转到授权页面
    //   })
    // } else {
    //   if (id != 0 && id != undefined) {
    //     wx.navigateTo({
    //       url: '/pages/ziliao/index?id=' + id,
    //     })
    //   } else {
    //     wx.showToast({
    //       title: '请扫码进入',
    //       icon: 'none',
    //     })
    //   }
    // }
    if (id != 0 && id != undefined) {
      wx.navigateTo({
        url: '/pages/ziliao/index?id=' + id,
      })
    } else {
      wx.showToast({
        title: '请扫码进入',
        icon: 'none',
      })
    }
    // wx.getSetting({
    //   success(res) {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称
    //       wx.navigateTo({
    //         url: '/pages/ziliao/index',
    //       })
    //     } else {
    //       wx.navigateTo({
    //         url: '/pages/shouquan/index', //跳转到授权页面
    //       })
    //     }
    //   }
    // })

  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }
})