
// Ajax Function to get a Message from the server.
function getMessage(id) {
    $.ajax({
        type: "GET",
        url: "/api/ReadAPI/GetRead/" + id,
        contentType: "application/html; charset=utf-8",
        dataType: "html",
        success: function (data) {
            //alert(JSON.stringify(data));
            $(".Message").html(data);
            $("#msg-" + id).removeClass("unread");
            getStats();
        }, //End of AJAX Success function
        failure: function (data) {
            console.log(data);
        }, //End of AJAX failure function
        error: function (data) {
            console.log(data);
        } //End of AJAX error function
    })
}

// Ajax Function to update MessagePreviews from the server.
function getPreviews(id) {
    $.ajax({
        type: "GET",
        url: "/api/ReadAPI/GetPreviews/" + id,
        contentType: "application/html; charset=utf-8",
        dataType: "html",
        success: function (data) {
            //alert(JSON.stringify(data));
            $(".msg-previews").html(data);
            document.getElementById('updatePreviews').
                setAttribute("onclick", "getPreviews('" + id + "')");
            getStats();
        }, //End of AJAX Success function
        failure: function (data) {
            console.log(data);
        }, //End of AJAX failure function
        error: function (data) {
            console.log(data);
        } //End of AJAX error function
    })
}

// Ajax Function to update MessagePreviews from the server.
function getSenders() {
    $.ajax({
        type: "GET",
        url: "/api/ReadAPI/GetSenders",
        contentType: "application/html; charset=utf-8",
        dataType: "html",
        success: function (data) {
            //alert(JSON.stringify(data));
            $(".msg-senders").html(data);
            getStats();
        }, //End of AJAX Success function
        failure: function (data) {
            console.log(data);
        }, //End of AJAX failure function
        error: function (data) {
            console.log(data);
        } //End of AJAX error function
    })
}

// Ajax Function to remove message from the server.
function doRemove(id) {
    $.ajax({
        type: "GET",
        url: "/api/ReadAPI/DoRemove/" + id,
        contentType: "application/html; charset=utf-8",
        dataType: "html",
        success: function (data) {
            //alert(JSON.stringify(data));
            $(".Message").html(data);
            var msg = "#msg-" + id;
            $(".msg-previews").find(msg).remove();
            getStats();
        }, //End of AJAX Success function
        failure: function (data) {
            console.log(data);
        }, //End of AJAX failure function
        error: function (data) {
            console.log(data);
        } //End of AJAX error function
    })
}

function getStats() {
    $.ajax({
        type: "GET",
        url: "/api/ReadAPI/GetStats",
        contentType: "application/html; charset=utf-8",
        dataType: "html",
        success: function (data) {
            //alert(JSON.stringify(data));
            $("#MessageStats").html(data);
        }, //End of AJAX Success function
        failure: function (data) {
            console.log(data);
        }, //End of AJAX failure function
        error: function (data) {
            console.log(data);
        } //End of AJAX error function
    })
}