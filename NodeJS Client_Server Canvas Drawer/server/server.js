var http = require('http')
var url = require('url')
var util = require("util")
var qs = require('querystring')
var fs = require('fs')
var path = require('path')
var baseDirectory = "C:\\Users\\rickk\\Documents\\BracketsProjects\\lab3\\client\\"
// or whatever base directory you want

var port = 9615
var draw = [];
var time = [];

var params = function (req) {
    let q = req.split('?'),
        result = {};
    if (q.length >= 2) {
        q[1].split('&').forEach((item) => {
            try {
                result[item.split('=')[0]] = item.split('=')[1];
            } catch (e) {
                result[item.split('=')[0]] = '';
            }
        })
    }
    return result;
}

http.createServer(function (request, response) {
    try {
        console.log(request.url);
        var requestUrl = url.parse(request.url)
        console.log(requestUrl.pathname);

        if (request.method === 'POST') {
            if (requestUrl.pathname === '/canvas.html') {
                console.log("canvas post");
                var data = "";

                request.on("data", function (chunk) {
                    data += chunk;
                });

                request.on("end", function () {
                    util.log("raw: " + data);
                    draw.push(data);
                    time.push(new Date().getTime());
                    response.writeHead(201);
                    response.end();
                });
            } else {
                console.log("other post " + requestUrl.pathname);
            }
        } else {
            if (requestUrl.pathname === '/update.html') {
                util.log("Update...");
                // Prase query strings
                var p = params(request.url);
                var t = parseInt(p.time);
                var old = parseInt(p.old);

                var send = "";

                for (i = 0; i < time.length; i++) {
                    if (time[i] > old) {
                        send += draw[i];
                    }
                }

                // Write and send to client...
                response.writeHead(200);
                response.end(send);
            } else {
                var fsPath = baseDirectory + path.normalize(requestUrl.pathname)
                console.log(fsPath);
                var fileStream = fs.createReadStream(fsPath)
                fileStream.pipe(response)
                fileStream.on('open', function () {
                    response.writeHead(200)
                })
                fileStream.on('error', function (e) {
                    response.writeHead(404) // assume the file doesn't exist
                    response.end()
                })
            }
        }
    } catch (e) {
        response.writeHead(500)
        response.end() // end the response so browsers don't hang
        console.log(e.stack)
    }
}).listen(port)

console.log("listening on port " + port);
console.log(baseDirectory);
