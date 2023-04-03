$(function () {

	//multiple select 전용 변수 초기화
	let selectedRows;

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
			VIEW.treeView_init();
			VIEW.btn_init();
			VIEW.modal_init();
			//VIEW.sidebar_init();
		},
		treeView_init: function(){
			//처음 띄울 때는 data가 없습니다 띄우고 조회 누르면 보이게 하기
			MENU.makeTreeView(null, ENV_VAL.MENUVIEW, "normal");

		},
		btn_init: function(){ //버튼 함수들 모음 (초기화 상태)
			//button 이벤트 binding
			$(ENV_VAL.BTN_INIT).click(VIEW.viewRecord);
			$(ENV_VAL.BTN_ADD).click(VIEW.addRecord);
			$(ENV_VAL.BTN_UPDATE).click(VIEW.updateRecord);
			$(ENV_VAL.BTN_DELETE).click(VIEW.deleteRecord);
		},
		modal_init: function(){
			//$(ENV_VAL.MODAL).click(VIEW.openModal); ///아아아악
			$(ENV_VAL.MODAL_CLOSE).click(VIEW.confirmBeforeClose);
			$(ENV_VAL.MODAL_SUBMIT).click(VIEW.submitModal);	
			
			$(ENV_VAL.ID_INPUT_CHANGE).prop('readonly',false);
			$(ENV_VAL.ID_INPUT_CHANGE).removeAttr('name');
			$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_MENUID+']').prop('readonly',false);
			$(ENV_VAL.CRUD_FORM).find('input').val('').end();
			//$('.box-body').hide();
		},
		sidebar_init: function(){
			//사이드바는 항상 메뉴가 보여야 하기 때문에 메뉴랑 분리
			MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.SIDEBARVIEW, "none");
		},
		addRecord: function(){
			VIEW.openModal();
		},
		viewRecord: function(){
			MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.MENUVIEW, "normal");
		},
		updateRecord: function(){
			//하나만 선택했는지 확인하는 로직
			if (selectedRows === undefined) {
				alert('수정할 회원을 선택해주세요!');
				return false;
			} else if (selectedRows.selectedRowKeys.length > 1) {
				alert('한 번에 한 명만 수정할 수 있습니다');
				return false;
			} else if (selectedRows.selectedRowKeys[0] == 'ADMIN'){
				alert('⛔관리자 계정은 수정할 수 없습니다⛔');
				return false;
			}
			//input 태그 채워넣기
			var data = selectedRows.selectedRowsData[0];
			$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_MENUNM+']').val(data.userNm);
			$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_MENUID+']').val(data.userId); //2개 선택
			//$('input[name='+ENV_VAL.COL_PW+']').val(data.pw);
			$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_UPDAUSER+']').val(data.updaUser);
			
			VIEW.openModal();
		},
		deleteRecord: function(){
			if (selectedRows === undefined) {
				alert('삭제할 메뉴를 선택해주세요!');
				return false;
			} else {
				var flag;
				selectedRows.forEach((value)=>{ if(value == 1 || value==2 || value==3) flag=-1; });
				if(flag==-1){
					alert('⛔해당 메뉴는 삭제할 수 없습니다⛔');
					return false;
				}
				//n개의 행 삭제하시겠습니까? confirm
				else if (!confirm(selectedRows.length + '개의 메뉴를 삭제하시겠습니까?')) return false;
				else MENU_CRUD_SUBMIT.deleteMenu(selectedRows);
			}
		},
		openModal: function(){
			//modal 열기
			$(ENV_VAL.MODAL).modal('show');
			$('<option value="0">root</option>').appendTo($('select[name=uprMenuId]'));
		},
		submitModal: function(){
			//매개변수를 못 받으니까 type은 클릭한 모달에 입력된 값을 통해서 확인
			var type = $(this).parent().prev().prev().children('.modal-title').text().split(' ')[1];
			if(type == ENV_VAL.TYPE_ADD) type = 'add';
			else type = 'update';
			console.log('submitModal 함수 안에 들어옴:'+type);
			MENU_CRUD_SUBMIT.validationAndSubmit(type);
			VIEW.closeModal();			
		},
		confirmBeforeClose: function(){
			var type = $(this).parent().prev().prev().children('.modal-title').text().split(' ')[1];
			if(!confirm(type+'을(를) 취소하시겠습니까?')) {
				return false;	
			}
			else VIEW.closeModal();			
		},
		closeModal: function(){
			$(ENV_VAL.MODAL).find('input').val('').end();
			$(ENV_VAL.MODAL).modal('hide');
			VIEW.sidebar_init();
		},
		
		
	}


	// 🔔 Spring 과제 2차 🔔
	var MENU = {
		makeTreeView: function(data, tagSelector, checkBoxStatus){
			var treeView = $(tagSelector).dxTreeView({
			    items: data,
			    noDataText: "데이터가🔔없습니다",
			    dataStructure: "plain",
			    keyExpr: "menuId",
			    displayExpr: "menuNm",
			    parentIdExpr: "uprMenuId",
			    //itemExpr: "", //하위 메뉴 전달 - plain data라서 필요x
			    
			    //checkbox
			    selectionMode: "multiple",
			    showCheckBoxesMode: checkBoxStatus,
			    			    						   					    
			    width: 300,
			    onSelectionChanged(e){
					selectedRows = e.component._dataAdapter._selectedNodesKeys;
				},
			}).dxTreeView('instance');
			return treeView;
		}
		, setUprMenuInModal: function(data){
			$(ENV_VAL.CRUD_FORM).find('option').remove();
			var select = $('select[name=uprMenuId]');
			data.forEach(function(e){
				var menu = "<option value="+e.menuId+">"+e.menuNm+"</option>"
				$(menu).appendTo(select);
			});
		}
	}

	var MENU_CRUD_SUBMIT = {
		loadDataSource: function(tagSelector, checkBoxStatus){
			$.ajax({
				url: ENV_VAL.URL_VIEW, 
				type: 'post',
				dataType: 'json',
				success: function (data, status, xhr) {
					console.log("🏮"+"data: "+data+", status: "+status+", xhr: "+xhr);
					MENU.makeTreeView(data, tagSelector, checkBoxStatus);
					MENU.setUprMenuInModal(data);
				},
				error: function (request, status, error) {
					alert('데이터 로드중 오류 발생');
					console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
				}
			});
		}

		, addMenu: function (form) {
			//ajax로 db에 데이터 입력 요청
			$.ajax({
				url: ENV_VAL.URL_ADD,
				type: 'post',
				data: form, //form태그에 serialize() + JSON.stringify()
				dataType: 'json',
				//dataType: 'text',
				contentType: 'application/json',
				success: function (data, status, xhr) {
					console.log("🏮"+"data: "+data+", status: "+status+", xhr: "+xhr);
					MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.MENUVIEW, "normal");
					VIEW.sidebar_init();
					alert('정상 입력되었습니다');
					console.log('data: ' + data);
				},
				error: function (request, status, error) {
					alert('입력 중 에러');
					console.log('request: ' + request.status + ', status: ' + status + ', error: ' + error);
				}
			});
		}
		, updateMenu: function (form) {
			//ajax로 db에 데이터 수정 요청
			$.ajax({
				url: ENV_VAL.URL_UPDATE,
				type: 'post',
				data: form,
				//dataType: 'json',
				dataType: 'text',  ///////////?????
				//contentType: "application/x-www-form-urlencoded; charset=UTF-8",        //////////
				contentType: 'application/json', //serializeObject
				success: function (data, status, xhr) {
					MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.MENUVIEW, "normal");
					VIEW.sidebar_init();
					alert('정상 수정되었습니다');
					console.log('data: ' + data);
				},
				error: function (request, status, error) {
					alert('수정 중 에러');
					console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
				}

			});
		}
		, deleteMenu: function (key) {
			//ajax로 db에 데이터 수정 요청
			$.ajax({
				url: ENV_VAL.URL_DELETE,
				type: 'post',
				data: {key: key},
				dataType: 'json',
				contentType: "application/x-www-form-urlencoded; charset=UTF-8", //배열을 넘길 거라서
				success: function (data, status, xhr) {
					MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.MENUVIEW, "normal");
					VIEW.sidebar_init();
					alert('정상 삭제되었습니다');
					console.log('data: ' + data);
				},
				error: function (request, status, error) {
					alert('삭제 중 에러');
					console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
				}

			});
		}
		, validationAndSubmit: function (type) {
			if ($(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_MENUNM+']').val() == '') {
				alert('메뉴 이름을 입력하세요');
				return false;
			}
			else if(type == 'add'){ //그냥 &&로 묶으면 null/undefined에 .val() 읽어오려고 한다고 안된다 할 것 같음
				if ($(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_REGIUSER+']').val() == '') {
					alert('등록자 아이디를 입력하세요');
					return false;
				}
			}
			else if(type== 'update'){
				if ($(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_UPDAUSER+']').val() == '') {
					alert('수정자 아이디를 입력하세요');
					return false;
				}
			}
			
			var obj = {};
			var form = $(ENV_VAL.CRUD_FORM).serializeArray();
			
			$.map(form, function(value, index){
				console.log(index+':'+value);
				obj[value['name']] = value['value'];
			});

			//var formJSON = JSON.stringify(form);
			var formJSON = JSON.stringify(obj);
			console.log("🚨"+formJSON);
			switch (type) {
				case "add": 
					MENU_CRUD_SUBMIT.addMenu(formJSON);
					break;
				case "update": 
					MENU_CRUD_SUBMIT.updateMenu(formJSON); 
					break;
				default:console.log("이상하다 왜 여기로 들어오지?"); break;
				}
			//MENU_MODAL.modalClose(type);
			console.log('validation 및 '+type+'과정 진행완료');
		}
		, ifBlankReturnFalse: function (type, inputClass, message) {
			if ($('#' + type + 'RecordForm > input[class=' + inputClass + ']').val() == '') {
				alert(message + '을(를) 입력하세요');
				
				return false;
			}
		}

	}///MENU_CRUD_SUBMIT


	//초기화
	VIEW.init();


	
});



