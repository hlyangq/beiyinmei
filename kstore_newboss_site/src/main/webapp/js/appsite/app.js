
var vueSwipe = VueSwipe.Swipe;
var vueSwipeItem = VueSwipe.SwipeItem;

var vm = new Vue({
  el: "#layoutCont",
  data: appData,
  components: {
    "swipe": vueSwipe,
    "swipe-item": vueSwipeItem
  },
  methods: {

    //删除模块
    removeModule: function(index){
      var _ordering = "";
      if(index) {
        _ordering = vm.floors[index].ordering;
      };
      vm.floors.forEach(function(e, i){
        if(e.ordering > _ordering) {
          e.ordering -= 1;
        };
      });
      this.floors.splice(index, 1);
      $("#saveBtn").prop('disabled', false);
      $('.blocks_item :button').prop('disabled', false);
    },

    //添加图片
    addImage: function(index, tmp){
      var itemData = {
        "id": null,
        "text": "",
        "img": "",
        "action": "",
        "actionParam": {},
        "ordering": 1,
        "price": 0
      };
      if(tmp == "sliders") {
        if(!this.sliders){
          this.sliders=[];
        }
        this.sliders.push({
          "id": null,
          "img": "",
          "action": "",
          "actionParam": {},
          "ordering": this.sliders.length + 1
        });
      } else if(tmp == "adverts") {
        if(!this.adverts){
          this.adverts=[];
        }
        itemData.ordering = this.adverts.length + 1;
        this.adverts.push(itemData);
      } else {
        if(!this.floors[index].banners) {
          this.floors[index].banners = [];
        }
        itemData.ordering = this.floors[index].banners.length + 1;
        this.floors[index].banners.push(itemData);
      }
    },

    //删除图片
    deleteImg: function(index, i, tmp){
      if(tmp == "sliders") {
        if(this.sliders){
          this.sliders.splice(i, 1);  
        }
      } else if(tmp == "adverts") {
        if(this.adverts){
          this.adverts.splice(i, 1);
        }
      } else {
        this.floors[index].banners.splice(i, 1);
      }
    },

    //删除商品
    deleteGood: function(index, i){
      this.floors[index].adverts.splice(i, 1);
    },

    //添加抢购/团购
    addRushGroupBuy: function(){
      var rgbData = {
        "id": "",
        "text": "抢购/团购",
        "banners": null,
        "template": "RushGroupBuy",
        "action": null,
        "actionParam": {},
        "ordering": vm.floors.length + 1,
        "adverts": []
      };
      if($(".special_buying_box").length > 0) {
        alert("该模块已存在!");
      } else {
        this.floors.push(rgbData);
      }
    },

    //添加模块1
    addTemplate01: function(){
      var _template01 = {
        "id": "",
        "text": "标题",
        "banners": [

        ],
        "template": "1",
        "action": "",
        "actionParam": {},
        "ordering": vm.floors.length + 1,
        "adverts": [
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 1,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 2,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 3,
            "price": 0
          }
        ]
      };
      this.floors.push(_template01);
    },

    //添加模块2
    addTemplate02: function(){
      var _template02 = {
        "id": "",
        "text": "标题",
        "banners": [],
        "template": "2",
        "action": null,
        "actionParam": {},
        "ordering": vm.floors.length + 1,
        "adverts": [
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 1,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 2,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 3,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 4,
            "price": 0
          }
        ]
      };
      this.floors.push(_template02);
    },

    //添加模块3
    addTemplate03: function(){
      var _template03 = {
        "id": "",
        "text": "标题",
        "banners": [],
        "template": "3",
        "action": "",
        "actionParam": {},
        "ordering": vm.floors.length + 1,
        "adverts": [
          {
            "id": null,
            "text": "",
            "img": "",
            "action": null,
            "actionParam": {},
            "ordering": 1,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": null,
            "actionParam": {},
            "ordering": 2,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 3,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 4,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 5,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 6,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 7,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 8,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 9,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 10,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 11,
            "price": 0
          }
        ]
      };
      this.floors.push(_template03);
    },

    //添加模块4
    addTemplate04: function(){
      var _template04 = {
        "id": "",
        "text": "标题",
        "banners": null,
        "template": "4",
        "action": "",
        "actionParam": {},
        "ordering": vm.floors.length + 1,
        "adverts": [
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 1,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 2,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 3,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 4,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 5,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 6,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 7,
            "price": 0
          }
        ]
      };
      this.floors.push(_template04);
    },

    //添加模块5
    addTemplate05: function(){
      var _template05 = {
        "id": "",
        "text": "标题",
        "banners": null,
        "template": "5",
        "action": "",
        "actionParam": {},
        "ordering": vm.floors.length + 1,
        "adverts": [
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 1,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 2,
            "price": 0
          },
          {
            "id": null,
            "text": "",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 3,
            "price": 0
          },
          {
            "id": null,
            "text": "商品名称",
            "img": "",
            "action": "",
            "actionParam": {},
            "ordering": 4,
            "price": 0
          }
        ]
      };
      this.floors.push(_template05);
    },

    //模拟弹窗修改数据
    chooseImage: function(dataPath){
      imageChooseWindow(dataPath);
    }
  }
});


//保存
function handleSubmit(t, tmp, index) {
  if(tmp == "sliders") {
    var flag = true;
    if(vm.sliders){
      vm.sliders.forEach(function(e, i){
        if(!e.img || e.img == "") {
          flag = false;
          e.error = true;
          vm.sliders.$set(i, JSON.parse(JSON.stringify(e)));
        } else {
          delete e.error;
          vm.sliders.$set(i, JSON.parse(JSON.stringify(e)));
        }
      });
    }
    if(flag) {
      $(t).parents(".cont_box").removeClass("editable");
      $("#saveBtn").prop('disabled', false);
      $('.blocks_item :button').prop('disabled', false)
    };
  }else if(tmp == "adverts") {
    var flag = true;
    if(vm.adverts){
      vm.adverts.forEach(function(e, i){
        if(!e.img || e.img == ""){
          flag = false;
          e.error = true;
          vm.adverts.$set(i, JSON.parse(JSON.stringify(e)));
        } else {
          delete e.error;
          vm.adverts.$set(i, JSON.parse(JSON.stringify(e)));
        }
      });
    }
    if(flag) {
      $(t).parents(".cont_box").removeClass("editable");
      $("#saveBtn").prop('disabled', false);
      $('.blocks_item :button').prop('disabled', false)
    };
  }else if(tmp == "floors") {
    var flag = true;

    if(vm.$get('floors[' + index + '].adverts')){
      vm.$get('floors[' + index + '].adverts').forEach(function(e, i){
        if(!e.img || e.img == ""){
          flag = false;
          e.error = true;
          vm.$set('floors[' + index + '].adverts[' + i + ']', JSON.parse(JSON.stringify(e)));
        } else {
          delete e.error;
          vm.$set('floors[' + index + '].adverts[' + i + ']', JSON.parse(JSON.stringify(e)));
        }
      });
    }

    if(vm.$get('floors[' + index + '].banners')){
      vm.$get('floors[' + index + '].banners').forEach(function(e, i){
        if(!e.img || e.img == ""){
          flag = false;
          e.error = true;
          vm.$set('floors[' + index + '].banners[' + i + ']', JSON.parse(JSON.stringify(e)));
        } else {
          delete e.error;
          vm.$set('floors[' + index + '].banners[' + i + ']', JSON.parse(JSON.stringify(e)));
        }
      });
    }
    if(flag) {
      $(t).parents(".cont_box").removeClass("editable");
      $("#saveBtn").prop('disabled', false);
      $('.blocks_item :button').prop('disabled', false)
    };
  }
};


$(function(){

  /*
   * 各个模块拖动排序
   * */
  var firIndex = "";
  $('#layoutCont').sortable({
    axis: 'y',
    items: 'div.cont_box:not(".ui-state-disabled")',
    opacity: .35,
    activate: function(event, ui){
      firIndex = $(ui.item).index() - 1;
    },
    out: function(event, ui){
      var secIndex = $(ui.item).index() - 1;
      if(secIndex > firIndex) {
        vm.floors.forEach(function(e, i){
          if(e.ordering > firIndex && e.ordering <= secIndex) {
            e.ordering -= 1;
          } else if(e.ordering == firIndex) {
            e.ordering = secIndex;
          };
        });
      } else if(secIndex < firIndex) {
        vm.floors.forEach(function(e, i){
          if(e.ordering >= secIndex && e.ordering < firIndex) {
            e.ordering += 1;
          } else if(e.ordering == firIndex) {
            e.ordering = secIndex;
          };
        });
      };
    }
  });


  var tempData = undefined;  
  /*
   * 打开/关闭模块的编辑状态
   * */
  $('body').on('click','.edit_btns a.edit',function(){
    $('.cont_box').removeClass('editable');
    $(this).parents('.cont_box').addClass('editable');
    $("#saveBtn").prop('disabled', true);
    $('.blocks_item :button').prop('disabled', true);

    tempData = JSON.stringify(vm.$data);
  });

  $('body').on('click','.edit_area a.close',function(){
    $(this).parents('.cont_box').removeClass('editable');
    if(tempData) {
      vm.$data = JSON.parse(tempData);
    }
    $("#saveBtn").prop('disabled', false);
    $('.blocks_item :button').prop('disabled', false)
  });

  /*
   * 显示图片示例
   * */
  $('body').on('click','.show_sample',function(){
    if(!$(this).next().is(':visible')){
      $(this).next().slideDown('fast');
      $(this).find('i').attr('class','icon-angle-down');
    }
    else{
      $(this).next().slideUp('fast');
      $(this).find('i').attr('class','icon-angle-right');
    }
  });

  //模拟select
  jQuery.fn.isChildAndSelfOf = function(b){ return (this.closest(b).length > 0); };
  $(document).click(function(event){
    if(!$(event.target).isChildAndSelfOf('.app-select-bar')) {
      $('.app-select-bar.open').removeClass('open');
    };
  });
  $('body').on('click', '.app-select-bar', function(){
    $(this).parents('li').siblings().find('.open').removeClass('open');
    $(this).toggleClass('open');
  });

});