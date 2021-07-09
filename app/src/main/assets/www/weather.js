/*var storedCity = localStorage.getItem("city");
if(storedCity!=null && storedCity!=undefined)
{
$("#query").value = storedCity;
}
*/

var unit="";
var unitvalue ="";



$("#getWeatherButton").addEventListener("click",function(){

    var city= $("#query").value;


if(($("#unitC").checked == true))
{
unit="c";
unitvalue="metric";
}
else if(($("#unitF").checked == true))
{
unit="f";
unitvalue="imperial";
}
else
{
unit="c";
unitvalue="metric";
}





    if((city.length==0)||(city.length<3))
    {
        //alert("City Not Entered");//normal javascript
        AndroidNative.toast("City Not Entered");//javascript interface
    }
    /*else
    {
    localStorage.setItem("city",city);
    }
    */
    var request = new XMLHttpRequest();

    var url="http://api.openweathermap.org/data/2.5/weather?q="+city+"&units="+unitvalue+"&appid=eac45c33031a2b0f52cde2e71ac95ee3"
    loading(true);
    request.open("get",url);

    request.addEventListener("load", function() {
        if(request.status==200)
        {
        var data = JSON.parse(request.response);
        if(data.cod==200)
        {
        //city has been found
        console.log(JSON.stringify(data));
        $("#city").innerHTML = data.name;
        $("#description").innerHTML = data.weather[0].description;
        $("#temperature").innerHTML = data.main.temp+" "+unit;
        $("#humidity").innerHTML = "Humidity:" + data.main.humidity+" %";
        loading(false);
        $("#results").style.display = 'block';
        }else{

        }
        }else{
            manageError();
        }



    });

    request.addEventListener("error",function(){

    manageError();
    });

    request.send();
});








function loading(enabled){
    if(enabled)
    {
    $("#loading").style.display = 'block';
    $("#results").style.display = 'none';

    }else{
        $("#loading").style.display = 'none';
    }

}

function manageError(){
    loading(false);
    AndroidNative.toast("Could Not Connect To The Server");

}

function clear(){
 $("#query").value="";
    $("#results").style.display = 'none';
}

function onBack(){
if($("#query").value!="")
{
   clear();

}
else
{
AndroidNative.finish();
}
}
function $(id){
    return document.querySelector(id);
}
