function addUser(){
    const xhr = new XMLHttpRequest();
    var form=document.getElementById('register-form');
    var params=formToParams(form);
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                Swal.fire({
                    title: 'Hi!',
                    text: 'You have been registered!',
                    icon: 'success',
                    confirmButtonText: 'Ok!'
                }).then(function (){
                    form.reset();
                    displayUserData(JSON.parse(xhr.responseText));
                });
            }else if(xhr.status===402){
                Swal.fire({
                    title: 'Opss!',
                    text: 'There is no company with this account ID',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
                form.reset();
            }else{
                Swal.fire({
                    title: 'Opss!',
                    text: 'Error ' + xhr.status,
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
                form.reset();
            }
        }
    };

    xhr.open('POST', 'AddUser', false);
    xhr.send(params);
}

function getUser(){
    const xhr = new XMLHttpRequest();
    var form=document.getElementById('get-form');
    var params=formToParams(form);
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                Swal.fire({
                    title: 'Hi!',
                    text: 'Account retrieved!',
                    icon: 'success',
                    confirmButtonText: 'Ok!'
                });
                user=JSON.parse(xhr.responseText);
                displayUserData(user);
            }else if(xhr.status===401){
                Swal.fire({
                    title: 'Oops!',
                    text: 'Invalid ID',
                    icon: 'error',
                    confirmButtonText: 'Ok!'
                });
                user=null;
            }else{
                Swal.fire({
                    title: 'Oops!',
                    text: 'Error ' + xhr.status,
                    icon: 'error',
                    confirmButtonText: 'Ok!'
                });
                user=null;
            }
            form.reset();
        }
    };

    xhr.open('GET', 'GetUser?' + params, false);
    xhr.send();
}

function deleteUser(){
    const xhr = new XMLHttpRequest();
    var form=document.getElementById('delete-form');
    var params=formToParams(form);
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                Swal.fire({
                    title: 'Hi!',
                    text: 'Account deleted!',
                    icon: 'success',
                    confirmButtonText: 'Ok!'
                }).then(function (){
                    form.reset();
                    showIndex();
                });
            }else if(xhr.status===401){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Invalid account ID',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
                form.reset();
            }else{
                Swal.fire({
                    title: 'Opss!',
                    text: 'Error ' + xhr.status,
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
                form.reset();
            }
        }
    };

    xhr.open('GET', 'DeleteUser?' + params, false);
    xhr.send();
}

function performPurchase(){
    const xhr = new XMLHttpRequest();
    var params;
    var form=document.getElementById('purchase-form');

    params=formToParams(form);
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                Swal.fire({
                    title: 'Hi!',
                    text: 'Purchase Performed!',
                    icon: 'success',
                    confirmButtonText: 'Ok!'
                });
            }else if(xhr.status===401){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Invalid ID',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else if(xhr.status===403){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Not a dealer!',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else if(xhr.status===405){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Not a customer!',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else if(xhr.status===409){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Can not afford the purchase',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else if(xhr.status===406){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Invalid employee ID',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else{
                Swal.fire({
                    title: 'Opss!',
                    text: 'Error ' + xhr.status,
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }

        }
    };

    xhr.open('POST', 'PerformPurchase', false);
    xhr.send(params);
}

function performReturn(){
    const xhr = new XMLHttpRequest();
    var params;
    var form=document.getElementById('return-form');
    params=formToParams(form);

    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                Swal.fire({
                    title: 'Hi!',
                    text: 'Return Performed!',
                    icon: 'success',
                    confirmButtonText: 'Ok!'
                });
            }else if(xhr.status===401){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Invalid ID',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else if(xhr.status===403){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Not a dealer!',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else if(xhr.status===405){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Not a customer!',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else if(xhr.status===406){
                Swal.fire({
                    title: 'Opss!',
                    text: 'Invalid employee ID',
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }else{
                Swal.fire({
                    title: 'Opss!',
                    text: 'Error ' + xhr.status,
                    icon: 'error',
                    confirmButtonText: 'Try again'
                });
            }
        }
    };

    xhr.open('POST', 'PerformReturn', false);
    xhr.send(params);

}


function payDept(){
    const xhr = new XMLHttpRequest();
    var params;
    var form=document.getElementById('pay-form');
    params=formToParams(form);

    xhr.onload=function(){
        if(xhr.readyState===4){
            form.reset();
            if(xhr.status===200){
                Swal.fire({
                    title: 'Payed!',
                    text: 'Payment completed!',
                    icon: 'success',
                    confirmButtonText: 'Ok!'
                });

            }else if(xhr.status===401) {
                Swal.fire({
                    title: 'Opss!',
                    text: 'Invalid ID!',
                    icon: 'error',
                    confirmButtonText: 'Ok!'
                });
            }
        }
    };

    xhr.open('GET', 'PayDept?' + params, false);
    xhr.send();
}

function getGoodClients(){
    const xhr = new XMLHttpRequest();

    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                users=JSON.parse(xhr.responseText);
            }else if(xhr.status===401){
                Swal.fire({
                    title: 'Opss!',
                    text: 'No users',
                    icon: 'error',
                    confirmButtonText: 'Ok!'
                });
                users=null;
            }else{
                Swal.fire({
                    title: 'Opss!',
                    text: 'Error ' + xhr.status,
                    icon: 'error',
                    confirmButtonText: 'Ok!'
                });
                users=null;
            }
        }
    };

    xhr.open('GET', 'GetGoodClients', false);
    xhr.send();
}


function getBadClients(){
    const xhr = new XMLHttpRequest();

    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                users=JSON.parse(xhr.responseText);
            }else if(xhr.status===401) {
                Swal.fire({
                    title: 'Opss!',
                    text: 'Error ' + xhr.status,
                    icon: 'error',
                    confirmButtonText: 'Ok!'
                });
            }
        }
    };

    xhr.open('GET', 'GetBadClients', false);
    xhr.send();
}


function getTransactions(){
    var form=document.getElementById('transactions-form');
    const xhr = new XMLHttpRequest();
    var params;
    params=formToParams(form);

    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                console.log(xhr.responseText);
                transactions=JSON.parse(xhr.responseText);
                console.log(transactions);
            }else if(xhr.status===411){
                Swal.fire({
                    title: 'Opss!',
                    text: 'No Transactions',
                    icon: 'error',
                    confirmButtonText: 'Ok!'
                });
                transactions=null;
            }else{
                Swal.fire({
                    title: 'Opss!',
                    text: 'Error ' + xhr.status,
                    icon: 'error',
                    confirmButtonText: 'Ok!'
                });
                transactions=null;
            }
            displayTransactions();
        }
    };

    xhr.open('POST', 'GetTransactions', false);
    xhr.send(params);
}


function getDealerOfMonth(){
    const xhr = new XMLHttpRequest();

    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                users=JSON.parse(xhr.responseText);
            }else{
                alert('Error ' + xhr.status);
            }
        }
    };

    xhr.open('GET', 'GetDealerOfMonth', false);
    xhr.send();
}