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
		COL_UPRMENUID : "uprMenuId",
		COL_URL : "url",
		
		//Id
		ID_INPUT_CHANGE : "#inputForChange",
		
		//URL
		URL_VIEW: "/page2/table",
		URL_UPR: "/page2/uprMenu",
		URL_ADD: "/page2/regi",
		URL_UPDATE: "/page2/update",
		URL_DELETE: "/page2/delete",
		//그냥 update 해도 되지 않을까
		//URL_SORT: "/page2/sort",
		
		//Type
		TYPE_ADD: '등록',
		TYPE_UPDATE: '수정',
	}

	var VIEW = {
		init : function(){										
			VIEW.treeViewInit();
			VIEW.btnInit();
			VIEW.modalInit();
			//VIEW.sidebarInit();
		},
		treeViewInit: function(){
			//처음 띄울 때는 data가 없습니다 띄우고 조회 누르면 보이게 하기
			MENU.makeTreeList(null, ENV_VAL.MENUVIEW, "multiple");

		},
		btnInit: function(){ //버튼 함수들 모음 (초기화 상태)
			//button 이벤트 binding
			$(ENV_VAL.BTN_INIT).click(VIEW.viewRecord);
			$(ENV_VAL.BTN_ADD).click(VIEW.addRecord);
			$(ENV_VAL.BTN_UPDATE).click(VIEW.updateRecord);
			$(ENV_VAL.BTN_DELETE).click(VIEW.deleteRecord);
		},
		modalInit: function(){
			// 모달 닫히기 전 (배경클릭) 확인하기
			//$(ENV_VAL.MODAL).on('hide.bs.modal', VIEW.confirmBeforeClose);
			
			$(ENV_VAL.MODAL_CLOSE).click(VIEW.confirmBeforeClose);
			$(ENV_VAL.MODAL_SUBMIT).click(VIEW.submitModal);	
			$(ENV_VAL.ID_INPUT_CHANGE).prop('readonly',false);
			$(ENV_VAL.ID_INPUT_CHANGE).removeAttr('name');
			$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_MENUID+']').prop('readonly',false);
			$(ENV_VAL.CRUD_FORM).find('input').val('').end();
			//$('.box-body').hide();
		},
		sidebarInit: function(){
			//사이드바는 항상 메뉴가 보여야 하기 때문에 메뉴랑 분리
			MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.SIDEBARVIEW, "none", "treeView");
		},
		
		reFormAndModal: function(type){
			switch(type){
				case "add":
					$(ENV_VAL.MODAL+' .modal-title').text('메뉴 등록');
					$(ENV_VAL.CRUD_FORM).attr('action',ENV_VAL.URL_ADD);
					$(ENV_VAL.ID_INPUT_CHANGE).attr('name',ENV_VAL.COL_REGIUSER);
					$(ENV_VAL.ID_INPUT_CHANGE).val('ADMIN');
					$(ENV_VAL.ID_INPUT_CHANGE).prop('readonly',true);
					
					$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_URL+']').prev().hide();
					$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_URL+']').prop('type', 'hidden');
					
					$(ENV_VAL.ID_INPUT_CHANGE).prev().text('등록자 아이디:');
					break;
				case "update":
					$(ENV_VAL.MODAL+' .modal-title').text('메뉴 수정');
					$(ENV_VAL.CRUD_FORM).attr('action',ENV_VAL.URL_UPDATE);
					$(ENV_VAL.ID_INPUT_CHANGE).attr('name',ENV_VAL.COL_UPDAUSER);
					$(ENV_VAL.ID_INPUT_CHANGE).val('ADMIN');
					$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_MENUID+']').prop('readonly',true);
					
					$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_URL+']').prev().show();
					$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_URL+']').prop('type', 'text');
					
					$(ENV_VAL.ID_INPUT_CHANGE).prop('readonly',true);
					$(ENV_VAL.ID_INPUT_CHANGE).prev().text('수정자 아이디:');
					break;
			}
		},
		addRecord: function(){
			VIEW.reFormAndModal("add");
			if(selectedRows != undefined)
			if(selectedRows.selectedRowKeys.length == 1)
				$(ENV_VAL.CRUD_FORM+' option[value='+selectedRows.selectedRowsData[0].menuId+']').prop('selected', true);
			VIEW.openModal();
		},
		viewRecord: function(){
			MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.MENUVIEW, "multiple", "treeList");
		},
		updateRecord: function(){
			var flag;
			//하나만 선택했는지 확인하는 로직
			if (selectedRows === undefined || selectedRows.selectedRowKeys.length == 0) {
				alert('수정할 메뉴를 선택해주세요!');
				return false;
			} else if (selectedRows.selectedRowKeys.length > 1) {
				alert('한 번에 한 개만 수정할 수 있습니다');
				return false;
			} else {
				selectedRows.selectedRowKeys.forEach(key => key==1 || key==2 || key==3 ? flag=true : flag=false)
				if (flag){
					alert('⛔해당 메뉴는 수정할 수 없습니다⛔');
					return false;
				}
			}
			//input 태그 채워넣기
			var data = selectedRows.selectedRowsData[0];
			VIEW.reFormAndModal("update");
			$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_MENUNM+']').val(data.menuNm);
			//select 에서 상위메뉴 선택해놓기
			$(ENV_VAL.CRUD_FORM+' option[value='+data.uprMenuId+']').prop('selected', true);
			$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_UPDAUSER+']').val(data.updaUser);
			$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_URL+']').val(data.url);
			
			VIEW.openModal();
		},
		deleteRecord: function(){
			if (selectedRows === undefined || selectedRows.selectedRowKeys.length == 0) {
				alert('삭제할 메뉴를 선택해주세요!');
				return false;
			} else {
				var flag;
				selectedRows.selectedRowKeys.forEach((value)=>{ if(value == 1 || value==2 || value==3) flag=-1; });
				if(flag==-1){
					alert('⛔해당 메뉴는 삭제할 수 없습니다⛔');
					return false;
				}
				//n개의 행 삭제하시겠습니까? confirm
				else if (!confirm(selectedRows.selectedRowKeys.length + '개의 메뉴를 삭제하시겠습니까?')) return false;
				else MENU_CRUD_SUBMIT.deleteMenu(selectedRows.selectedRowKeys);
			}
		},
		openModal: function(){
			//modal 열기
			$(ENV_VAL.MODAL).modal('show');
			
		},
		submitModal: function(){
			//매개변수를 못 받으니까 type은 클릭한 모달에 입력된 값을 통해서 확인
			var type = $(this).parent().prev().prev().children('.modal-title').text().split(' ')[1];
			if(type == ENV_VAL.TYPE_ADD) type = 'add';
			else type = 'update';
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
			VIEW.sidebarInit();
		},
		
		
	}


	// 🔔 Spring 과제 2차 🔔
	var MENU = {
		menuColumns: [{
			dataField: "sort",
			caption: "순서",
			sortOrder: "asc",
			visible: false
		},{
			dataField: "menuNm",
			caption: "메뉴이름",
		},{
			dataField: "regiUser",
			caption: "등록자",
		},{
			dataField: "regiDt",
			caption: "등록일",
			dataType: "date",
			format: "yyyy-MM-dd"
		},{
			dataField: "updaUser",
			caption: "수정자",
		},{
			dataField: "updaDt",
			caption: "수정일",
			dataType: "date",
			format: "yyyy-MM-dd"
		}],
		sidebarColumns:[{
			dataField: "menuNm",
			caption: "메뉴이름"
		}],
		makeTreeList: function(data, tagSelector, checkBoxStatus){
			var treeList = $(tagSelector).dxTreeList({
			    dataSource: data,
			    columns: MENU.menuColumns,
			    columnAutoWidth: true,
			    noDataText: "데이터가🔔없습니다",
			    dataStructure: "plain",
			    keyExpr: "menuId",
			    parentIdExpr: "uprMenuId",
			    autoExpandAll: true,
			    //줄무늬 row
			    //rowAlternationEnabled: true,
			    rowDragging:{
					allowReordering: true,
					allowDropInsideItem: true,
					//dragDirection: "both",
					dropFeedBackMode: "indicate",
					showDragIcons: false,
					
					/*
					e.fromIndex: 기존 인덱스번호 (위에서부터 순차적 0~)
					e.toIndex: 옮길 인덱스번호 (드래그 도중에도 변경됨)
					*/
					
/*					onDragChange(e){
				        const visibleRows = treeList.getVisibleRows();
				        /*
				        sourceNode.data.uprMenuId = 부모의 아이디
				        sourceNode.data.sort = 기존 sort 순번
				        sourceNode.data.menuId = 옮기는 대상의 menuId(pk)
				        */
				        //const sourceNode = treeList.getNodeByKey(e.itemData.menuId);
				        /*
				        targetNode.data.uprMenuId = target 노드의 부모의 아이디
				        targetNode.data.sort
				        targetNode.data.menuId
				        */
				        //let targetNode = visibleRows[e.toIndex].node;
						
						
						
						//console.log('targetNode.parent:'+Object.keys(targetNode));
						//console.log('targetNode의 부모id:'+targetNode.data.uprMenuId);
						//console.log('targetNode의 parent.id:'+targetNode.parent.data.menuId);
						//console.log('targetNode의 parent.parent.id:'+targetNode.parent.data.uprMenuId);
						
						//console.log('targetNode의 sort:'+targetNode.data.sort);
						//console.log('targetNode의 id:'+targetNode.data.menuId);
						
/*				        while (targetNode && targetNode.data) {
				          if (targetNode.data.menuId === sourceNode.data.menuId) {
				            e.cancel = true;
				            break;
				          }
				          targetNode = targetNode.parent;
				        }	*/				
						
						
						//e
/*						console.log("❤❤❤❤❤❤❤❤❤❤❤❤❤❤");
						console.log('self:'+self);
						console.log('e.fromIndex:'+e.fromIndex);
						console.log('e.toIndex:'+e.toIndex);
						console.log('e.fromComponent:'+e.fromComponent);
						console.log('e.toComponent:'+e.toComponent);
						console.log('e.fromData:'+e.fromData);
						console.log('e.toData:'+e.toData);
						console.log('visibleRows:'+visibleRows);
						console.log("❤❤❤❤❤❤❤❤❤❤❤❤❤❤");*/
						
						
					//},*/
					
				    onReorder(e){
						console.log("===="+Object.keys(e));
						console.log("이게 되는가"+e.dropInsideItem);
						const visibleRows = treeList.getVisibleRows();
				        /*
				        sourceNode.data.uprMenuId = 부모의 아이디
				        sourceNode.data.sort = 기존 sort 순번
				        sourceNode.data.menuId = 옮기는 대상의 menuId(pk)
				        */
				        var sourceNode = visibleRows[e.fromIndex].node;
				        /*
				        targetNode.data.uprMenuId = target 노드의 부모의 아이디
				        targetNode.data.sort
				        targetNode.data.menuId
				        */
				        var targetNode = visibleRows[e.toIndex].node;
				        var targetUpNode = visibleRows[e.toIndex-1].node;
				        
				        
				        console.log(Object.keys(sourceNode));
				        //sourceNode.data.uprMenuId
				        //sourceNode.data.sort
				        
				        //dropInsideItem: true일 때
						console.log('targetNode의 부모id:'+targetNode.data.uprMenuId);
						console.log('targetNode의 sort:'+targetNode.data.sort);
						console.log('targetNode의 id:'+targetNode.data.menuId);

						//dropInsideItem: false일 때
						console.log('targetUpNode의 id:'+targetUpNode.data.uprMenuId);
						console.log('targetUpNode의 id:'+targetUpNode.data.sort);
						console.log('targetUpNode의 id:'+targetUpNode.data.menuId);
						
						
						if(e.dropInsideItem){
							console.log('targetNode의 부모id:'+sourceNode.data.uprMenuId);
							sourceNode.data.uprMenuId = targetNode.data.menuId;
							console.log('targetNode의 부모id (after):'+sourceNode.data.uprMenuId);
							console.log('before:'+sourceNode.data.sort);
							sourceNode.data.sort = targetNode.data.sort;
							console.log('after:'+sourceNode.data.sort);
							//그리고 targetNode랑 sourceNode랑 sort을... 대대적으로 수정해줘야 하는데...
							sourceNode.data.menuId //를 이용하기
							
						}
						else{
							sourceNode.data.uprMenuId = targetNode.data.uprMenuId;
							//target딴
							//이게 아니라 기존 sort를 받고, 기존 menuId의 sort+=를 가진 애들 sort를 +1해줘야 함
							
							//source딴
							//uprMenuId 하위의 sort들을 전체 개수 가져와서 asc로 정렬하고 sort 전부 새로 부여하기 
							sourceNode.data.sort = targetNode.data.sort-1;
							console.log("댕댕댕댕:"+sourceNode.data.sort);
							console.log("댕댕댕댕:"+targetNode.data.sort);
							
						}
						
					},
					onDragEnd(e){
						
						e.component.refresh();
					}
				},
			    //checkbox
			    selection: {
					allowSelectAll: false,
					mode: checkBoxStatus
				},
			    onSelectionChanged(e){
					console.log(Object.keys(e));
					selectedRows = e;
				},
			}).dxTreeList('instance');
			return treeList;
		},
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
		loadDataSource: function(tagSelector, checkBoxStatus, flag){
			$.ajax({
				url: ENV_VAL.URL_VIEW, 
				type: 'post',
/*				dataType: 'json',*/
				success: function (data, status, xhr) {
					console.log("🏮"+"data: "+data+", status: "+status+", xhr: "+xhr);
					if(flag == 'treeList')
						MENU.makeTreeList(data, tagSelector, checkBoxStatus);
					else if(flag == 'treeView')
						MENU.makeTreeView(data, tagSelector, checkBoxStatus);
					//select 태그의 option들 리셋??
					MENU.setUprMenuInModal(data);
					$('<option value="0">root</option>').prependTo($('select[name='+ENV_VAL.COL_UPRMENUID+']'));
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
					MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.MENUVIEW, "multiple", "treeList");
					VIEW.sidebarInit();
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
/*				dataType: 'text',*/
				contentType: 'application/json', //serializeObject
				success: function (data, status, xhr) {
					MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.MENUVIEW, "multiple", "treeList");
					VIEW.sidebarInit();
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
/*				dataType: 'json',*/
				contentType: "application/x-www-form-urlencoded; charset=UTF-8", //배열을 넘길 거라서
				success: function (data, status, xhr) {
					MENU_CRUD_SUBMIT.loadDataSource(ENV_VAL.MENUVIEW, "multiple", "treeList");
					VIEW.sidebarInit();
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
			console.log('validation 및 '+type+'과정 진행완료');
		}
		, ifBlankReturnFalse: function (type, inputClass, message) {
			if ($('#' + type + 'RecordForm > input[class=' + inputClass + ']').val() == '') {
				alert(message + '을(를) 입력하세요');
				
				return false;
			}
		}
		, openMenuPage: function(url){
			location.href = url;
		}
		, moveMenu: function(e){
			console.log("================")
			console.log(Object.keys(e));
			//console.log(Object.keys(e.toData));
			console.log("================")
		}

	}///MENU_CRUD_SUBMIT


	//초기화
	VIEW.init();


	
});



