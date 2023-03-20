$('.box-body').hide();
$('#viewTable').on('click',function(){
    $('.box-body').show();
    
    //restTemplate 사용하기
    fetch("http://localhost:8082/page1/table")
        .then((response)=>console.log("response: "+response))
        .catch((error)=>console.log("error: "+error));
});