define("ajax/utils",

        ["jquery"],

        function ($) {

          return {
            getHeaders : function() {
              auth = btoa(document.getElementById('username').value + ':' + document.getElementById('password').value);
              return {
                "Authorization": 'Basic ' + auth
                //"Origin": "http://www.sisifo.com",
                //"Access-Control-Request-Method" : "GET",
                //"Access-Control-Request-Headers" : "Authorization"
              }
            },
            onComplete : function(e, xhr, settings){
              if (e.status == 200) {
              } else if (e.status === 401){
                var element = document.getElementById("log-in-message");
                element.innerHTML = "<p>Wrong user id or password</p>";
                element.style.backgroundColor = 'yellow'
              } else if (e.status === 500){
                var element = document.getElementById("log-in-message");
                element.innerHTML = "<p>Fatal error in server</p>";
                element.style.backgroundColor = "rgb(200, 0, 20)"
              } else {
                var element = document.getElementById("log-in-message");
                element.innerHTML = "<p>Unexpected fatal error. Is server up?</p>";
                element.style.backgroundColor = "rgb(200, 0, 20)"
              }
            }
          }

        }
)
