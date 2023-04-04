$(function () {
	
	var ENV_VAL = {
		//Toolbar button
		BTN_INIT : '#viewTable',
		BTN_ADD : '#addRecord',
		BTN_UPDATE : '#updateRecord',
		BTN_DELETE : '#deleteRecord',
		
		//Tree
		MENUVIEW : '#treeViewContainer',
		SIDEBARVIEW : '#sidebarContainer',

		//Modal
		MODAL : '#menuModal',
		MODAL_SUBMIT : '#submitBtn',
		MODAL_CLOSE : '#closeBtn',
		
		//Form
		CRUD_FORM : '#menuForm',
		
		//Column,Name
		COL_MENUID : "menuId",
		COL_MENUNM : "menuNm",
		COL_REGIUSER : "regiUser",
		COL_UPDAUSER : "updaUser",
		
		//URL
		URL_VIEW: "/page2/table",
		URL_UPR: "/page2/uprMenu",
		URL_ADD: "/page2/regi",
		URL_UPDATE: "/page2/update",
		URL_DELETE: "/page2/delete",
		
		//Type
		TYPE_ADD: '등록',
		TYPE_UPDATE: '수정',
	}

	var VIEW = {
		init : function(){										
			VIEW.sidebarInit();
		},
		sidebarInit: function(){
			//사이드바는 항상 메뉴가 보여야 하기 때문에 메뉴랑 분리
			MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.SIDEBARVIEW, "none");
		}	
	}

	// 🔔 MSA 과제 1차 🔔
	var MENU = {
		makeTreeView: function(data, tagSelector, checkBoxStatus){
			console.log("data:"+data);
			var treeView = $(tagSelector).dxTreeView({
			    items: data,
			    noDataText: "데이터가🔔없습니다",
			    dataStructure: "plain",
			    keyExpr: "menuId", //id
			    displayExpr: "menuNm", //text
			    parentIdExpr: "uprMenuId", //categoryId
			    
			    //checkbox
			    selectionMode: "multiple",
			    showCheckBoxesMode: checkBoxStatus,
			    						   					    
			    width: 300,
			    onItemClick(e) {
			    	MENU_CRUD_SUBMIT.openMenuPage(e.itemData.url);
			    },
			}).dxTreeView('instance');
			return treeView;
		}
	}

	var MENU_CRUD_SUBMIT = {
		loadDataSource: function(tagSelector, checkBoxStatus){
			$.ajax({
				url: ENV_VAL.URL_VIEW, 
				type: 'post',
/*				dataType: 'json',*/
				success: function (data, status, xhr) {
					MENU.makeTreeView(data, tagSelector, checkBoxStatus);
				},
				error: function (request, status, error) {
					alert('데이터 로드중 오류 발생:sidebar');
					console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
				}
			});
		}

		, openMenuPage: function(url){
			location.href = url;
		}
	}///MENU_CRUD_SUBMIT


	//초기화
	VIEW.init();


	
});



