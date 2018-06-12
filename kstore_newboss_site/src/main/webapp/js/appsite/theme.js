var vm = new Vue({
    el: "#layoutThemeCont",
    data: appData,
    components: {},
    methods: {
        //删除模块
        removeModule: function (index) {
            this.floors.splice(index, 1);
        },

        //添加商品
        addPro: function (index) {
            var itemData = {
                img: '',
                name: '商品名称',
                price: '0.00'
            };

            this.items[index].goods.push(itemData);
        },

        //删除商品
        deletePro: function (index, i) {
            this.items[index].goods.splice(i, 1);
        },

        //添加模块1
        addTemplate: function(){
            var _template = {
                "id": "",
                "text": "标题",
                "template": "theme-1",
                "action": "",
                "actionParam": {},
                "ordering": vm.floors.length + 1,
                "themeCode": "theme-1",
                "backgroundColor": '#2587d1',
                secondColor: '#52b0f7',
                "adverts": [
                ]
            };
            this.floors.push(_template);
        },

        //新增一个商品
        addAdvert : function(floorIndex){
            var advertData = {
                "id": null,
                "text": "",
                "img": "",
                "action": "",
                "actionParam": {},
                "ordering": 1,
                "price": 0
            };
            this.floors[floorIndex].adverts.push(advertData);
        },

        //删除图片
        deleteImg: function(index, i){
            this.floors[index].adverts.splice(i, 1);
        },

        //保存模块
        handleSubmit: function (t, index) {
            var flag = true;
            vm.items[index].goods.forEach(function (e, i) {
                if (e.img == "") {
                    flag = false;
                    e.error = true;
                    vm.items[index].goods.$set(i, JSON.parse(JSON.stringify(e)));
                } else {
                    delete e.error;
                    vm.items[index].goods.$set(i, JSON.parse(JSON.stringify(e)));
                }
            });
            if (flag) {
                $(".layout_cont .cont_box:eq(" + (index + 1) + ")").removeClass("editable");
            }
        },

        //添加图片
        addImage: function (index, tmp) {
            var itemData = {
                "id": null,
                "text": "",
                "img": "",
                "action": "",
                "actionParam": {},
                "ordering": 1,
                "price": 0
            };
            if (tmp == "adverts") {
                if (!this.adverts) {
                    this.adverts = [];
                }
                this.adverts.push({
                    "id": null,
                    "img": "",
                    "action": "",
                    "actionParam": {},
                    "ordering": this.adverts.length + 1
                });
            } else {
                if (!this.floors[index].banners) {
                    this.floors[index].banners = [];
                }
                itemData.ordering = this.floors[index].banners.length + 1;
                this.floors[index].banners.push(itemData);
            }
        },

        //模拟弹窗修改数据
        chooseImage: function (dataPath) {
            themeModule.imageChooseWindow(dataPath);
        },

        //修改action
        chooseAction: function (val, dataPath) {
            //action变化时，先清空actionParam
            console.log('---chooseAction--->', val, dataPath);
            this.$set(dataPath + '.actionParam', {});

            if (val === 0) {
                this.$set(dataPath + '.action', '');
            } else if (val === 1) {
                this.$set(dataPath + '.action', 'GoodsList');
                themeModule.catChooseWindow(dataPath);
            } else if (val === 2) {
                this.$set(dataPath + '.action', 'GoodsList');
                themeModule.brandChooseWindow(dataPath);
            } else if (val === 3) {
                this.$set(dataPath + '.action', 'GoodsDetail');
                themeModule.productChooseWindow(dataPath);
            }
        }
    }
});


$(function () {

    /*
     * 各个模块拖动排序
     * */
    var firIndex = "";
    $('#layoutThemeCont').sortable({
        axis: 'y',
        items: 'div.cont_box:not(".ui-state-disabled")',
        opacity: .35,
        activate: function (event, ui) {
            firIndex = $(ui.item).index() - 1;
        },
        out: function (event, ui) {
            var secIndex = $(ui.item).index() - 1;
            if (secIndex > firIndex) {
                vm.items.forEach(function (e, i) {
                    if (e.ordering > firIndex && e.ordering <= secIndex) {
                        e.ordering -= 1;
                    } else if (e.ordering == firIndex) {
                        e.ordering = secIndex;
                    }
                });
            } else if (secIndex < firIndex) {
                vm.items.forEach(function (e, i) {
                    if (e.ordering >= secIndex && e.ordering < firIndex) {
                        e.ordering += 1;
                    } else if (e.ordering == firIndex) {
                        e.ordering = secIndex;
                    }
                });
            }
            ;
        }
    });

    /*
     * 打开/关闭模块的编辑状态
     * */
    $('body').on('click', '.edit_btns a.edit', function () {
        $('.cont_box').removeClass('editable');
        $(this).parents('.cont_box').addClass('editable');
    });

    $('body').on('click', '.edit_area a.close', function () {
        $(this).parents('.cont_box').removeClass('editable');
    });

    /*
     * 显示图片示例
     * */
    $('body').on('click', '.show_sample', function () {
        if (!$(this).next().is(':visible')) {
            $(this).next().slideDown('fast');
            $(this).find('i').attr('class', 'icon-angle-down');
        }
        else {
            $(this).next().slideUp('fast');
            $(this).find('i').attr('class', 'icon-angle-right');
        }
    });


    var swiper = new Swiper('.topic-tabs', {
        slidesPerView: 4,
        spaceBetween: 0,
        freeMode: true
    });
    $("body").on('click', '.add_box, .delete', function () {
        swiper.updateSlidesSize();
    });

    $(window).scroll(function () {
        if ($(window).scrollTop() > 440) {
            $(".topic-tabs").addClass("fixed");
        } else {
            $(".topic-tabs").removeClass("fixed");
        }
    });

    /*
     * 各个模块拖动排序
     * */
    var firIndex = "";
    $('#layoutCont').sortable({
        axis: 'y',
        items: 'div.cont_box:not(".ui-state-disabled")',
        opacity: .35,
        activate: function (event, ui) {
            firIndex = $(ui.item).index() - 1;
        },
        out: function (event, ui) {
            var secIndex = $(ui.item).index() - 1;
            if (secIndex > firIndex) {
                vm.floors.forEach(function (e, i) {
                    if (e.ordering > firIndex && e.ordering <= secIndex) {
                        e.ordering -= 1;
                    } else if (e.ordering == firIndex) {
                        e.ordering = secIndex;
                    }
                });
            } else if (secIndex < firIndex) {
                vm.floors.forEach(function (e, i) {
                    if (e.ordering >= secIndex && e.ordering < firIndex) {
                        e.ordering += 1;
                    } else if (e.ordering == firIndex) {
                        e.ordering = secIndex;
                    }
                });
            }
        }
    });


    var tempData = undefined;
    /*
     * 打开/关闭模块的编辑状态
     * */
    $('body').on('click', '.edit_btns a.edit', function () {
        $('.cont_box').removeClass('editable');
        $(this).parents('.cont_box').addClass('editable');
        $("#saveBtn").prop('disabled', true);
        $('.blocks_item :button').prop('disabled', true);

        tempData = JSON.stringify(vm.$data);
    });

    $('body').on('click', '.edit_area a.close', function () {
        $(this).parents('.cont_box').removeClass('editable');
        if (tempData) {
            vm.$data = JSON.parse(tempData);
        }
        $("#saveBtn").prop('disabled', false);
        $('.blocks_item :button').prop('disabled', false)
    });

    /*
     * 显示图片示例
     * */
    $('body').on('click', '.show_sample', function () {
        if (!$(this).next().is(':visible')) {
            $(this).next().slideDown('fast');
            $(this).find('i').attr('class', 'icon-angle-down');
        }
        else {
            $(this).next().slideUp('fast');
            $(this).find('i').attr('class', 'icon-angle-right');
        }
    });

    //模拟select
    jQuery.fn.isChildAndSelfOf = function (b) {
        return (this.closest(b).length > 0);
    };
    $(document).click(function (event) {
        if (!$(event.target).isChildAndSelfOf('.app-select-bar')) {
            $('.app-select-bar.open').removeClass('open');
        }
    });
    $('body').on('click', '.app-select-bar', function () {
        $(this).parents('li').siblings().find('.open').removeClass('open');
        $(this).toggleClass('open');
    });

    $("#saveBtn").click(function(){
        if(vm.floors){
            var hasErr = false;
            var errMsg = '';
            vm.floors.forEach(function(floor, idx){
                var text = floor.text;
                if(floor.adverts){
                    floor.adverts.forEach(function(e, i){
                        if(!e.img || e.img == "") {
                            hasErr = true;
                            errMsg = '["'+text+'"]楼层广告区不允许存在为空的图片！';
                            return;
                        };
                    });
                }
                if(hasErr){return;}
                if(floor.adverts){
                    floor.adverts.forEach(function(e, i){
                        if(!e.img || e.img == "") {
                            hasErr = true;
                            errMsg = '["'+text+'"]楼层轮播区不允许存在为空的图片！';
                            return;
                        };
                    });
                }
            });
            if(hasErr){
                art.dialog.alert(errMsg);
                return;
            }
        }

        if(vm.floors && vm.floors.length < 1){
            art.dialog.alert('至少需要包含1个楼层');
            return;
        }

        $("#saveBtn").prop('disabled', true);
        art.dialog.confirm('保存后将影响主题页的展现，请确认是否保存？', function(){
            var dataStr = JSON.stringify(vm.$data);
            console.log('-----save ----', dataStr);

            $.ajax({
                url: $('#basePath').val() + 'saveThemeIndex.htm?CSRFToken=' + $('#token').val(),
                context: document.body,
                method: 'post',
                data: dataStr,
                contentType: 'application/json',
                // processData: false,
                // dataType: 'json',
                success:function(resp){
                    console.log('---success-->', resp);
                    if(resp === 'success'){
                        alert('保存成功');
                        window.document.location = "./setThemeHomePage.htm?themeCode='theme-1'";
                    }else{
                        alert('保存失败，请稍后再试！');
                    }
                    $("#saveBtn").prop('disabled', false);
                },
                error: function(resp){
                    console.log('----fail--->', JSON.stringify(resp));
                    alert('保存失败，请稍后再试！');
                    $("#saveBtn").prop('disabled', false);
                }
            });
        }, function(){
            $("#saveBtn").prop('disabled', false);
            // art.dialog.tips('你取消了操作');
        });
    });
});


/**
 * 主题模块
 * @type {{handleSubmit}}
 */
var themeModule = (function () {
    var handleSubmit = function (t, tmp, index) {
        if (tmp == "adverts") {
            $(t).parents(".cont_box").removeClass("editable");
            $("#saveBtn").prop('disabled', false);
            $('.blocks_item :button').prop('disabled', false);
        } else if (tmp == "floors") {
            var flag = true;

            if (vm.$get('floors[' + index + '].adverts')) {
                vm.$get('floors[' + index + '].adverts').forEach(function (e, i) {
                    if (!e.img || e.img == "") {
                        flag = false;
                        e.error = true;
                        vm.$set('floors[' + index + '].adverts[' + i + ']', JSON.parse(JSON.stringify(e)));
                    } else {
                        delete e.error;
                        vm.$set('floors[' + index + '].adverts[' + i + ']', JSON.parse(JSON.stringify(e)));
                    }
                });
            }

            if (vm.$get('floors[' + index + '].banners')) {
                vm.$get('floors[' + index + '].banners').forEach(function (e, i) {
                    if (!e.img || e.img == "") {
                        flag = false;
                        e.error = true;
                        vm.$set('floors[' + index + '].banners[' + i + ']', JSON.parse(JSON.stringify(e)));
                    } else {
                        delete e.error;
                        vm.$set('floors[' + index + '].banners[' + i + ']', JSON.parse(JSON.stringify(e)));
                    }
                });
            }

            if (flag) {
                $(t).parents(".cont_box").removeClass("editable");
                $("#saveBtn").prop('disabled', false);
                $('.blocks_item :button').prop('disabled', false)
            }
        }
    };

    //添加商品
    var addGood = function(floorIndex){
        productChooseWindow('floors['+floorIndex+'].adverts', 10);
    };

    //图片选择window
    var imageChooseWindow = function (sourceId) {
        art.dialog.open($('#basePath').val() + 'queryImageManageByPbAndCidForChoose.htm?CSRFToken=' + $('#token').val(), {
            id: sourceId,
            lock: true,
            width: '800px',
            height: '400px',
            title: '选择图片',
            top: '30%',
            fixed: true
        });
    };

    // 商品选择window
    var productChooseWindow = function (sourceId, maxChoose) {
        maxChoose = maxChoose || 1;
        art.dialog.open($('#basePath').val() + 'queryMobProductForGoods.htm?CSRFToken=' + $('#token').val() + '&size=' + maxChoose, {
            id: sourceId,
            lock: true,
            width: '900px',
            height: '400px',
            title: '选择商品',
            top: '30%',
            fixed: true
        });

    };

    // 分类选择window
    var catChooseWindow = function (sourceId) {
        art.dialog.open($('#basePath').val() + 'queryMobCateBarForChoose.htm?CSRFToken=' + $('#token').val(), {
            id: sourceId,
            lock: true,
            width: '400px',
            height: '400px',
            title: '选择分类',
            top: '30%',
            fixed: true
        });
    };

    // 品牌选择window
    var brandChooseWindow = function(sourceId){
        art.dialog.open($('#basePath').val() + 'queryAllBrandForChoose.htm?CSRFToken=' + $('#token').val(), {
            id: sourceId,
            lock: true,
            width: '800px',
            height: '400px',
            title: '选择品牌',
            top:'30%',
            fixed: true
        });
    };






    return {
        handleSubmit: handleSubmit,
        addGood: addGood,
        imageChooseWindow: imageChooseWindow,
        productChooseWindow: productChooseWindow,
        catChooseWindow: catChooseWindow,
        brandChooseWindow: brandChooseWindow,
    };
})();
