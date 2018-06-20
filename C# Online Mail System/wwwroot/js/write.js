// Ajax Function to get a Message from the server.
function getU() {
    $.ajax({
        type: "GET",
        url: "/api/ReadAPI/GetU",
        contentType: "application/html; charset=utf-8",
        dataType: "html",
        success: function (data) {
            //alert(JSON.stringify(data));
            $("#user").html(data);
        }, //End of AJAX Success function
        failure: function (data) {
            console.log(data);
        }, //End of AJAX failure function
        error: function (data) {
            console.log(data);
        } //End of AJAX error function
    })
}

// Ajax Function to get a Message from the server.
function getG() {
    $.ajax({
        type: "GET",
        url: "/api/ReadAPI/GetG",
        contentType: "application/html; charset=utf-8",
        dataType: "html",
        success: function (data) {
            //alert(JSON.stringify(data));
            $("#group").html(data);
        }, //End of AJAX Success function
        failure: function (data) {
            console.log(data);
        }, //End of AJAX failure function
        error: function (data) {
            console.log(data);
        } //End of AJAX error function
    })
}

function addRecipient(name) {
    var input = $("#Recipients")
    var value = input.val;
    input.val(input.val() + name + ", " );
    console.log(input.val);
}
