// pages/qiandaook/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    heardimg: '',
    pageBackgroundColor: 'http://ddwap.hkzhtech.com/bj.jpg',


  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    wx.getSetting({
      success(res) {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称
          wx.getUserInfo({
            success: function (res) {
              console.log(res.userInfo.avatarUrl)
              that.setData({
                heardimg: res.userInfo.avatarUrl
              })
            }
          })
        } else {
          wx.navigateTo({
            url: '/pages/shouquan/index', //跳转到授权页面
          })
        }
      }
    })
    console.log(JSON.parse(options.data))
    var data = JSON.parse(options.data)
    that.setData({
      NAME: data.NAME,
      QDTIME: data.QDTIME,
      QDORDER: data.QDORDER,
      SEATNO: data.SEATNO,
      pageBackgroundColor: data.pageBackgroundColor,
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

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