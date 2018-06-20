function sendData(info) {
    'use strict';
    var url = "canvas.html";

    console.log(info);
    var sendString = JSON.stringify(info);
    $.ajax({
        url: 'canvas.html',
        type: 'post',
        dataType: 'json',
        success: function (data) {
            console.log("success");
            $("#result").empty().append("Success: " + info);
        },
        data: sendString
    });
}
var old = new Date().getTime();
var time = new Date().getTime();

// Keep everything in anonymous function, called on window load.
if (window.addEventListener) {
    window.addEventListener('load', function () {
        'use strict';
        var canvas, context, tool;

        function init() {
            // Find the canvas element.
            canvas = document.getElementById('canvas');
            if (!canvas) {
                alert('Error: I cannot find the canvas element!');
                return;
            }

            if (!canvas.getContext) {
                alert('Error: no canvas.getContext!');
                return;
            }

            // Get the 2D canvas context.
            context = canvas.getContext('2d');
            if (!context) {
                alert('Error: failed to getContext!');
                return;
            }

            // Pencil tool instance.
            tool = new tool_pencil();

            // Attach the mousedown, mousemove and mouseup event listeners.
            canvas.addEventListener('mousedown', ev_canvas, false);
            canvas.addEventListener('mousemove', ev_canvas, false);
            canvas.addEventListener('mouseup', ev_canvas, false);
        }

        // This painting tool works like a drawing pencil which tracks the mouse 
        // movements.
        function tool_pencil() {
            var tool = this;
            this.started = false;
            var data = [];

            // This is called when you start holding down the mouse button.
            // This starts the pencil drawing.
            this.mousedown = function (ev) {
                data.length = 0;
                context.beginPath();
                context.moveTo(ev._x, ev._y);
                tool.started = true;
                data.push(ev._x + "." + ev._y);
            };

            // This function is called every time you move the mouse. Obviously, it only 
            // draws if the tool.started state is set to true (when you are holding down 
            // the mouse button).
            this.mousemove = function (ev) {
                if (tool.started) {
                    context.lineTo(ev._x, ev._y);
                    context.stroke();
                    data.push(ev._x + "." + ev._y);
                }
            };

            // This is called when you release the mouse button.
            this.mouseup = function (ev) {
                if (tool.started) {
                    tool.mousemove(ev);
                    tool.started = false;
                    sendData(data);
                }
            };
        }

        // The general-purpose event handler. This function just determines the mouse 
        // position relative to the canvas element.
        function ev_canvas(ev) {
            if (ev.layerX || ev.layerX === 0) { // Firefox
                ev._x = ev.layerX;
                ev._y = ev.layerY;
            } else if (ev.offsetX || ev.offsetX === 0) { // Opera
                ev._x = ev.offsetX;
                ev._y = ev.offsetY;
            }

            // Call the event handler of the tool.
            var func = tool[ev.type];
            if (func) {
                func(ev);
            }
        }

        init();

    }, false);
}

function switchColor() {
    'use strict';
    var val = document.getElementById('color-select').value;
    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');
    if (val === "Black") {
        context.strokeStyle = "#000000";
    } else if (val === "Red") {
        context.strokeStyle = "#FF0000";
    } else if (val === "Green") {
        context.strokeStyle = "#00FF00";
    } else if (val === "Blue") {
        context.strokeStyle = "#0000FF";
    } else if (val === "White") {
        context.strokeStyle = "#FFFFFF";
    }
}

function switchSize() {
    'use strict';
    var val = document.getElementById('size-select').value;
    var canvas = document.getElementById('canvas');
    var context = canvas.getContext('2d');
    if (val === "1") {
        context.lineWidth = 1;
    } else if (val === "2") {
        context.lineWidth = 3;
    } else if (val === "3") {
        context.lineWidth = 5;
    } else if (val === "4") {
        context.lineWidth = 7;
    } else if (val === "5") {
        context.lineWidth = 10;
    }
}

function clearCanvas() {
    'use strict';
    var canvas = document.getElementById('canvas');
    canvas.width = canvas.width;
    switchColor();
    switchSize();
}

function splitDraws(data) {
    var s = data.split(']');
    for (i = 0; i < s.length - 1; i++) {
        s[i] = s[i].substring(1);
    }
    return s;
}

function splitSecond(data) {
    var s = data.split(',');
    for (i = 0; i < s.length; i++) {
        s[i] = s[i].replace(/['"]+/g, '')
    }
    return s;
}

function splitThird(data) {
    var s = data.split('.');
    var d = [];
    d.push(parseInt(s[0]));
    d.push(parseInt(s[1]));
    return d;
}

function update() {
    var sendString = "alive";
    time = new Date().getTime();
    $.get("update.html?time=" + time + "&old=" + old, function (data) {
        $("#result").html(data);
        canvas = document.getElementById('canvas');
        if (!canvas) {
            alert('Error: I cannot find the canvas element!');
            return;
        }

        if (!canvas.getContext) {
            alert('Error: no canvas.getContext!');
            return;
        }

        // Get the 2D canvas context.
        context = canvas.getContext('2d');
        if (!context) {
            alert('Error: failed to getContext!');
            return;
        }
        var s = splitDraws(data);
        var l = s.length - 1;
        console.log("Size: " + l)
        for (z = 0; z < l; z++) {
            var s2 = splitSecond(s[z]);
            context.beginPath();
            s3 = splitThird(s2[0]);
            context.moveTo(s3[0], s3[1]);
            for (j = 1; j < s2.length; j++) {
                s3 = splitThird(s2[j]);
                context.lineTo(s3[0], s3[1]);
                context.stroke();
            }
        }
    });
    old = time;
}
window.setInterval(update, 3000);
update();
