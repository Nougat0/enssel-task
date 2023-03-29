				$(document).ready(function(){						
					alert("🔔 외부 js 파일 읽어왔습니다");
				});
				$(function () {
					console.log("🔔 외부 js 파일 읽어왔습니다");
					
					var VIEW = {
						init: function(){
							VIEW.treeView_init();
						},
						treeView_init: function(){
							MENU.makeTreeView();
						}
					}
					
					var TREE = {
//						menu_data: [{
//						id: '1',
//							text: '전체메뉴',
//							expanded: true,
//							items: [{
//								id: '2',
//								text: '사용자 관리',
//							},{
//								id: '3',
//								text: '메뉴 관리'
//							}]
//						}]
						makeTreeView: function(data){
							  var treeView = $('#menuTreeView').dxTreeView({
							    items: data,
							    keyExpr: "menuId",
							    displayExpr: "menuNm",
							    //itemExpr: "", //하위 메뉴 전달
							    parentIdExpr: "uprMenuId",
							    
							    //checkbox
							    selectionMode: "multiple",
							    showCheckBoxesMode: "normal",
							    
							    width: 300,
							    onItemClick(e) {
							      console.log('key로 뭐가 있나요'+Object.keys(e));
							      //data에 가져와진 url 을 여기서 사용할 수 있나?
							      alert("메뉴:"+e.itemElement)
							    },
							  }).dxTreeView('instance');
							return treeView;
						}
						, showMenu: function(){
							
						}
					}
					
					var TREE_CRUD_SUBMIT = {
						loadDataSource: function(){
							$.ajax({
								url: '/page2/table',
								type: 'post',
								contentType: "application/json; charset=UTF-8",
								success: function(data){
									TREE.makeTreeView(data);
								},
								error: function(request, status, error){
									alert('treeView 데이터 로드 중 에러 발생');
									console.log('request.status: '+request.status+', status: '+status+", error: "+error);
								}
							})
						}
					}
					
					//초기화
					VIEW.init();
					

				//multiple select 전용 변수 초기화
				let selectedRows;

				var ENV_VAL = {
					//Toolbar button
					BTN_INIT : '#viewTable',
					BTN_ADD : '#addRecord',
					BTN_UPDATE : '#updateRecord',
					BTN_DELETE : '#deleteRecord',
					
					//Tree
					GRID : '#gridContainer',
					GRID_GROUPBY : '#groupBy',

					//Modal
					MODAL : '#menuModal',
					MODAL_SUBMIT : '#submitBtn',
					MODAL_CLOSE : '#closeBtn',
					
					//Form
					CRUD_FORM : '#recordForm',
					SEARCH_FORM : '#searchForm',
					
					//Column,Name
					COL_USERID : "userId",
					COL_USERNM : "userNm",
					COL_PW : "pw",
					COL_REGIUSER : "regiUser",
					COL_UPDAUSER : "updaUser",
					//Id
					ID_PW2 : "#pw2",
					ID_INPUT_CHANGE : "#inputForChange",
					
					//URL
					URL_VIEW: "/page1/table",
					URL_ADD: "/page1/regi",
					URL_UPDATE: "/page1/update",
					URL_DELETE: "/page1/delete",
					URL_GROUPBY: "/page1/groupBy",
					
					//Type
					TYPE_ADD: '입력',
					TYPE_UPDATE: '수정',
					
					
					
				}

				var VIEW = {
					init : function(){										
						VIEW.grid_init();
						VIEW.btn_init();
						VIEW.modal_init();
						VIEW.search_init();
						VIEW.groupBy_init();
					},
					grid_init: function(){
						//처음 띄울 때는 data가 없습니다 띄우고 조회 누르면 보이게 하기
						GRID.makeGrid(null, ENV_VAL.GRID, GRID.columns, "userId");
						//GRID_CRUD_SUBMIT.loadDataSource();
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
						$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_USERID+']').prop('readonly',false);
						$(ENV_VAL.CRUD_FORM).find('input').val('').end();
						
						//$('.box-body').hide();
					},
					search_init: function(){
						$(ENV_VAL.CRUD_FORM).find('input').val('').end();
					},
					groupBy_init: function(){
						GRID.makeGrid(null,ENV_VAL.GRID_GROUPBY, GRID.columns_groupBy, "regiDate");
					},
					reFormAndModal: function(type){
						switch(type){
							case "add":
								$(ENV_VAL.MODAL+' .modal-title').text('회원 입력');
								$(ENV_VAL.CRUD_FORM).attr('action',ENV_VAL.URL_ADD);
								$(ENV_VAL.ID_INPUT_CHANGE).attr('name',ENV_VAL.REGIUSER);
								$(ENV_VAL.ID_INPUT_CHANGE).val('ADMIN');
								$(ENV_VAL.ID_INPUT_CHANGE).prop('readonly',true);
								$(ENV_VAL.ID_INPUT_CHANGE).prev().text('등록자 아이디:');
								break;
							case "update":
								$(ENV_VAL.MODAL+' .modal-title').text('회원 수정');
								$(ENV_VAL.CRUD_FORM).attr('action',ENV_VAL.URL_UPDATE);
								$(ENV_VAL.ID_INPUT_CHANGE).attr('name',ENV_VAL.UPDAUSER);
								$(ENV_VAL.ID_INPUT_CHANGE).val('ADMIN');
								$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_USERID+']').prop('readonly',true);
								$(ENV_VAL.ID_INPUT_CHANGE).prop('readonly',true);
								$(ENV_VAL.ID_INPUT_CHANGE).prev().text('수정자 아이디:');
								break;
						}
					},
					viewRecord: function(){
						var flag = 0;
						//Array.from($(ENV_VAL.SEARCH_FORM+' input')).forEach(function(val, idx){
						Array.from(document.querySelector(ENV_VAL.SEARCH_FORM).getElementsByTagName('input')).forEach(function(val, idx){
							console.log(idx+':idx'+val+':val')
							if(val.value !== '') flag++;
						});
						
						if(flag == 0) {
							GRID_CRUD_SUBMIT.loadDataSource(null, ENV_VAL.GRID, GRID.columns, "userId", ENV_VAL.URL_VIEW);
							GRID_CRUD_SUBMIT.loadDataSource(null, ENV_VAL.GRID_GROUPBY, GRID.columns_groupBy, "regiDate", ENV_VAL.URL_GROUPBY);
						}
						else if(flag > 0) {
							VIEW.searchRecord();
							
						}
						
						


												
						//$('.box-body').show();
					},
					searchRecord: function(){
						console.log('궁금해!!!'+$(ENV_VAL.SEARCH_FORM+' input[name='+ENV_VAL.COL_).length);
						
						
						
						
						var obj = {};
						var form = $(ENV_VAL.SEARCH_FORM).serializeArray();
						console.log('form::::'+form)
						
						$.map(form, function(value, index){
							//console.log(index+':'+value);
							obj[value['name']] = value['value'];
						});

						console.log('obj::::'+obj);
						var formJSON = JSON.stringify(obj);
						console.log('formJSON::::'+formJSON);
						//var encodedJSON = encodeURI(formJSON);
						//console.log('flag::::'+encodedJSON);
						GRID_CRUD_SUBMIT.loadDataSource(formJSON, ENV_VAL.GRID, GRID.columns, "userId", ENV_VAL.URL_VIEW);
						GRID_CRUD_SUBMIT.loadDataSource(formJSON, ENV_VAL.GRID_GROUPBY, GRID.columns_groupBy, "regiDate", ENV_VAL.URL_GROUPBY);
					},
					addRecord: function(){
						VIEW.reFormAndModal('add');
						VIEW.openModal();
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
						$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_USERNM+']').val(data.userNm);
						$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_USERID+']').val(data.userId); //2개 선택
						//$('input[name='+ENV_VAL.COL_PW+']').val(data.pw);
						$(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_UPDAUSER+']').val(data.updaUser);
						
						VIEW.reFormAndModal('update');
						VIEW.openModal();
					},
					deleteRecord: function(){
						if (selectedRows === undefined) {
							alert('삭제할 회원을 선택해주세요!');
							return false;
						} else if(selectedRows.selectedRowKeys[0] == 'ADMIN'){
							alert('⛔관리자 계정은 삭제할 수 없습니다⛔');
							return false;
						}
						//n개의 행 삭제하시겠습니까? confirm
						else if (!confirm(selectedRows.selectedRowKeys.length + '명의 정보를 삭제하시겠습니까?')) return false;
						else {
							console.log('삭제할 배열: '+selectedRows.selectedRowKeys);
							console.log('삭제할 배열(type): '+typeof selectedRows.selectedRowKeys);
							console.log('삭제할 배열(type): '+Array.isArray(selectedRows.selectedRowKeys));
							console.log('이게 되나? '+selectedRows.selectedRowKeys.forEach(function(){console.log("forEach가 되니까 배열임!")}))
							
							GRID_CRUD_SUBMIT.deleteMember(selectedRows.selectedRowKeys);
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
						console.log('submitModal 함수 안에 들어옴:'+type);
						GRID_CRUD_SUBMIT.validationAndSubmit(type);
						//GRID_MODAL.modalClose(type);
						//VIEW.confirmBeforeClose(type);
						VIEW.closeModal();					
					},
					confirmBeforeClose: function(){
						var type = $(this).parent().prev().prev().children('.modal-title').text().split(' ')[1];
						if(!confirm(type+'을(를) 취소하시겠습니까?')) {
							console.log("⛔");			
							return false;
							console.log("⛔⛔");			
						}
						else VIEW.closeModal();			
					},
					closeModal: function(){
						//VIEW.modal_init();	
						//modal 닫기
						//document.getElementById('recordModal').
						//$(ENV_VAL.MODAL).hide();
						console.log("modal 여깄슴당:"+'#recordModal');					
						console.log(" 🔔🔔 ");			
						
						//modal 닫히면 input 값들 비우기
						$(ENV_VAL.CRUD_FORM).find('input').val('').end();					
						console.log(" 🔔🔔🔔 ");			
						$(ENV_VAL.MODAL).modal('hide');
					},
					
					
				}


				// 🔔 Spring 과제 2차 🔔
				var MENU = { //grid 를 만드는 데 필요한 속성들 정의
					columns: [{
						dataField: "userNm"
						, caption: "이름"
					}, {
						dataField: "userId"
						, caption: "아이디"
						, fixed: true //엑셀에서 n열까지 고정 기능과 유사. 가로 스크롤이 생길 시 해당 컬럼을 고정해서 항상 보이게 함.
						, sortOrder: "asc" //내림차순 정렬
					}, {
						dataField: "pw"
						, caption: "비밀번호"
						, visible: false
					}, {
						dataField: "regiDt"
						, caption: "등록일"
						, dataType: "date"
						, format: "yyyy-MM-dd"
					}, {
						dataField: "regiUser"
						, caption: "등록자 아이디"
						//dataType: "text"
						//데이터 타입 디폴트는 text, 이 경우에는 명시 안해줘도 됨
					}, {
						dataField: "updaDt"
						, caption: "수정일"
						, dataType: "date"
						, format: "yyyy-MM-dd"
					}, {
						dataField: "updaUser"
						, caption: "수정자 아이디"  //dataField 외에 명시할 속성이 없다면 중괄호 없이 그냥 "컬럼명" 만 추가해도 됨.
					}]
					, 
					columns_groupBy:[
					{
						dataField: "regiDate"
						,caption: "등록일"
						, format: "yyyy-MM-dd"
					},{	
						dataField: "accountSum"
						,caption: "계정 수"
					}]
					, makeGrid: function (data, gridSelector, columns, keyExpr) {
						var grid = $(gridSelector).dxDataGrid({
							dataSource: data //array 전달, 함수 넣어도 됨 (return 값 전달)
							, keyExpr: keyExpr //pk의 필드명 넘기기
							// array 외의 데이터를 넘기고 싶을 경우(JSON) Docs 참고
							, columns: columns//사용하는 컬럼정보 배열로 넘기기 [{컬럼1정보},{컬럼2정보},{컬럼3정보}]
							, noDataText: "데이터가🔔없습니다"
							, allowColumnResizing: false
							, columnAutoWith: true
							, columnFixing: { enabled: true }
							, sorting: { mode: "single" } //정렬 옵션을 단일정렬만 하기. (다중 정렬x)
							, searchPanel: {
								visible: true
								, placeholder: "검색"
							} //검색창 추가
							, selection: { mode: "multiple" } //다중 선택 옵션 on
							, onSelectionChanged: function (e) {
								selectedRows = e;
							}
							, toolbar: {
								items: [
									"searchPanel"
								]
							}
						}).dxDataGrid("instance"); // instance 가져와서 담기
						return grid;
					}
					/*
					, loadDataSource: function () {
						
						$.ajax({
							url: '/page1/table',
							dataType: 'json',
							contentType: "application/json; charset=UTF-8",
							success: function (data, status, xhr) {
								GRID.makeGrid(data);
								//console.log('data.Columns: ' + data.Columns);
								//console.log('GRID.Columns: ' + GRID.Columns);
								//alert('ajax로 새로 데이터를 받아왔어요');
							},
							error: function (request, status, error) {
								alert('데이터 로드중 오류 발생');
								console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
							}
						});
						//console.log('ajax로 새로 데이터를 받아왔어요');
					}
					*/
				}///GRID

				var GRID_CRUD_SUBMIT = {
					loadDataSource: function(searchForm, gridSelector, columns, keyExpr, url){
						
						$.ajax({
							url: url, 
							type: 'post',
							//search 인수 전달 시 data 전달하기... 컨트롤러에서 null 체크하기
							data: searchForm,						
							dataType: 'json',
							contentType: "application/json; charset=UTF-8",
							success: function (data, status, xhr) {
								//GRID.makeGrid(data, ENV_VAL.GRID, GRID.columns, "userId");
								GRID.makeGrid(data, gridSelector, columns, keyExpr);
								//console.log('data.Columns: ' + data.Columns);
								//console.log('GRID.Columns: ' + GRID.Columns);
								//alert('ajax로 새로 데이터를 받아왔어요');
							},
							error: function (request, status, error) {
								alert('데이터 로드중 오류 발생');
								console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
							}
						});
						//console.log('ajax로 새로 데이터를 받아왔어요');
					}
					, loadGroupByDataSource: function(searchForm){
						$.ajax({
							url: ENV_VAL.URL_GROUPBY,
							type: 'post',
							data: searchForm,
							dataType: 'json',
							contentType: "application/json; charset=UTF-8",
							success: function (data, status, xhr) {
								GRID.makeGrid(data, ENV_VAL.GRID_GROUPBY, GRID.columns_groupBy, "regiDate");
								//console.log('data.Columns: ' + data.Columns);
								//console.log('GRID.Columns: ' + GRID.Columns);
								//alert('ajax로 새로 데이터를 받아왔어요');
							},
							error: function (request, status, error) {
								alert('데이터 로드중 오류 발생');
								console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
							}							
						})
					}
					, addMember: function (form) {
						//ajax로 db에 데이터 입력 요청
						$.ajax({
							url: ENV_VAL.URL_ADD,
							type: 'post',
							data: form, //form태그에 serialize() + JSON.stringify()
							//dataType: 'json',
							dataType: 'text',
							//contentType: "application/x-www-form-urlencoded; charset=UTF-8",
							contentType: 'application/json',
							success: function (data, status, xhr) {
								GRID_CRUD_SUBMIT.loadDataSource(null, ENV_VAL.GRID, GRID.columns, "userId", ENV_VAL.URL_VIEW);
								alert('정상 입력되었습니다');
								console.log('data: ' + data);
							},
							error: function (request, status, error) {
								alert('입력 중 에러');
								console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
							}
						});
					}
					, updateMember: function (form) {
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
								GRID_CRUD_SUBMIT.loadDataSource(null, ENV_VAL.GRID, GRID.columns, "userId", ENV_VAL.URL_VIEW);
								alert('정상 수정되었습니다');
								console.log('data: ' + data);
							},
							error: function (request, status, error) {
								alert('수정 중 에러');
								console.log('request.status: ' + request.status + 'status: ' + status + 'error: ' + error);
							}

						});
					}
					, deleteMember: function (key) {
						//ajax로 db에 데이터 수정 요청
						$.ajax({
							url: ENV_VAL.URL_DELETE,
							type: 'post',
							data: {key: key},
							dataType: 'json',
							contentType: "application/x-www-form-urlencoded; charset=UTF-8", //배열을 넘길 거라서
							success: function (data, status, xhr) {
								GRID_CRUD_SUBMIT.loadDataSource(null, ENV_VAL.GRID, GRID.columns, "userId", ENV_VAL.URL_VIEW);
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
						if ($(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_USERNM+']').val() == '') {
							alert('이름을 입력하세요');
							return false;
						}
						else if ($(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_USERID+']').val() == '') {
							alert('아이디를 입력하세요');
							return false;
						}
						else if ($(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_PW+']').val() == '') {
							alert('비밀번호를 입력하세요');
							return false;
						}
						else if ( type=="add" && $(ENV_VAL.CRUD_FORM+' input[name='+ENV_VAL.COL_PW+']').val() != $(ENV_VAL.ID_PW2).val()) {
							alert('비밀번호가 일치하지 않습니다');
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
						switch (type) {
							case "add": 
								GRID_CRUD_SUBMIT.addMember(formJSON);
								break;
							case "update": 
								GRID_CRUD_SUBMIT.updateMember(formJSON); 
								break;
							default:console.log("이상하다 왜 여기로 들어오지?"); break;
							}
						//GRID_MODAL.modalClose(type);
						console.log('validation 및 '+type+'과정 진행완료');
					}
					, ifBlankReturnFalse: function (type, inputClass, message) {
						if ($('#' + type + 'RecordForm > input[class=' + inputClass + ']').val() == '') {
							alert(message + '을(를) 입력하세요');
							
							return false;
						}
					}
				}///GRID_CRUD_SUBMIT


				//초기화
				VIEW.init();


				
			});