function rightViewUserRoles(array) {
    let string = "";
    const length = array.length;
    for (let index = 0; index < length; index++) {
        string = string + " " + array[index].role;
    }
    return string;
}

$(document).ready(function () {
    reloadTable();
})

function reloadTable() {
    $.ajax("/rest/all", {
        method: "get",
        dataType: "json",
        success: function (msg) {
            $("#mainTableUsers").children().remove();
            allUsers = msg;
            msg.forEach(function (usr) {
                $("#mainTableUsers").append(
                    '<tr id=' + usr.id + '>' +
                    '<td>' + usr.id + '</td>' +
                    '<td>' + usr.name + '</td>' +
                    '<td>' + usr.city + '</td>' +
                    '<td>' + usr.login + '</td>' +
                    '<td>' + rightViewUserRoles(usr.roles) + '</td>' +
                    '<td>' + '<button type="button" name="ButEdit" onclick="loadUserForEdit(this)" data-target="#editModal" value=' + usr.id + ' ' +
                    'class="btn btn-info" data-toggle="modal">' + 'Edit' + '</button>' + '</td>' +
                    '<td>' + '<button type="button" name="ButDelete" onclick="loadUserForDelete(this)" data-target="#deleteModal" value=' + usr.id + ' ' +
                    ' class="btn btn-danger" data-toggle="modal">' +
                    'Delete' + '</button>' + '</td>' +
                    '</tr>');
            });
        }
    })
}

function editUser(id) {
    $.ajax("/rest/edit", {
        method: "put",
        data: {
            user: JSON.stringify(
                {
                    id: $("#editIdUser").val(),
                    name: $("#editUserName").val(),
                    city: $("#editCity").val(),
                    login: $("#editEmail").val(),
                    password: $("#editPassword").val(),
                }),
            roles: JSON.stringify($("#editRole").val())

        },
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            reloadTable();
            $(".modal").modal('hide');

        },
        error: function (e) {
            console.log("ERROR: ", e);
        },
        done: function (e) {
            console.log("DONE");
        }
    })
}

function loadUserForEdit(obj) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/rest/get",
        data: obj.value,
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            var user = JSON.parse(JSON.stringify(data));
            $("#editUserName").val(user.name);
            $("#editCity").val(user.city);
            $("#editEmail").val(user.login);
            $("#editPassword").val(user.password);
            $("#editRole").val(user.role);
            $("#editIdUser").val(user.id);
            $("#editButton").val(user.id);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}

function loadUserForDelete(obj) {
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/rest/get",
        data: obj.value,
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            var user = JSON.parse(JSON.stringify(data));
            $("#deleteUserName").val(user.name);
            $("#deleteCity").val(user.city);
            $("#deleteEmail").val(user.login);
            $("#deletePassword").val(user.password);
            $("#deleteRole").val(user.role);
            $("#deleteIdUser").val(user.id);
            $("#deleteButton").val(user.id);
        },
        error: function (e) {
            console.log("ERROR: ", e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}

function addUser() {
    $.ajax("/rest/add", {
        method: "post",
        data: {
            user: JSON.stringify(
                {
                    name: $("#addName").val(),
                    city: $("#addCity").val(),
                    login: $("#addEmail").val(),
                    password: $("#addPassword").val(),
                }),
            roles: JSON.stringify($("#addRole").val())
        },
        dataType: "json",
        success: function (data) {
            console.log("SUCCESS: ", data);
            reloadTable();
            document.getElementById('addForm').reset();
        },
        error: function (e) {
            console.log("ERROR: ", e);
            $(".modal").modal('hide');
        },
        done: function (e) {
            console.log("DONE");
            $(".modal").modal('hide');
        }
    })
}

function deleteViaAjax(obj) {
    const idForDel = obj.value;
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/rest/delete/" + idForDel,
        data: idForDel,
        dataType: 'json',
        timeout: 100000,
        success: function (data) {
            console.log("SUCCESS: ", data);
            $(".modal").modal('hide');
            reloadTable();
        },
        error: function (e) {
            console.log("ERROR: ", e);
        },
        done: function (e) {
            console.log("DONE");
        }
    });
}