var app = getApp();

Page({
  data: {
    userInfo: {},
    hasUserInfo: !1,
    but: !1,
    canIUse: wx.canIUse("button.open-type.getUserInfo")
  },
  errorbtn: function () {
    wx.showToast({
      title: "参数错误,退后后重试",
      icon: "none",
      duration: 2e3
    });
  },
  onShow: function () {
    var that = this
    this.data.intervarID = setInterval(function () {
      var openId = app.globalData.openId
      if (openId == '' | openId == null) {
        that.setData({
          but: false
        })
      } else {
        that.setData({
          but: true
        })
        clearInterval(that.data.intervarID);
      }
    }, 1000)
  },
  jujue() {
    wx.navigateBack({
      delta: 1
    })
  },
  onLoad: function () {
    var url = app.globalData.url;
    if (app.globalData.userInfo) {
      app.globalData.shoustatus = 1
      this.setData({
        userInfo: app.globalData.userInfo,
        // app.globalData.shoustatus:1
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
  },
  getUserInfo: function (e) {
    var url = app.globalData.url
    // console.log(friendopenId);return false;
    wx.getUserInfo({
      success: function (res) {
        console.log(res.rawData)
        wx.navigateBack({
          delta: 1,
        })
        app.globalData.userInfo = res.rawData
        var userInfo = res.rawData;
        // var openId = app.globalData.openId
        var friendopenId = app.globalData.friendopenId
        // if (openId == null) {
        //   console.log('现在没有open值')
        //   return false;
        // }
        // console.log(openId)
        // wx.request({
        //   url: url + '/userapi/User/getUserInfo',
        //   data: {
        //     openId: openId,
        //     friendopenId: friendopenId,
        //     userInfo: userInfo
        //   },
        //   method: "POST",
        //   header: {
        //     'content-type': 'application/json' // 默认值
        //   },
        //   success: function (res) {
        //     wx.setStorageSync('user', res.data.user)
        //     wx.setStorageSync('userInfo', e.detail.userInfo)
        //     wx.setStorageSync('hasUserInfo', true)
        //     wx.navigateBack({
        //       delta: 5
        //     })
        //   }
        // })
      }
    })
  }
});